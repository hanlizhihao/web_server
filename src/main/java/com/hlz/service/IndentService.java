package com.hlz.service;

import com.hlz.dao.IndentDAO;
import com.hlz.dao.SellAnalyzeDAO;
import com.hlz.dao.VipDAO;
import com.hlz.entity.Indent;
import com.hlz.entity.SellAnalyze;
import com.hlz.entity.Vip;
import com.hlz.webModel.IndentModel;
import com.hlz.webModel.IndentStyle;
import com.hlz.webModel.VipModel;
import java.util.List;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

//Stomp
/**
 * 需要推送的活动：创建订单，上菜，退菜，加菜，催单，结算，取消，换桌
 * 1.创建订单：成功处理创建订单的逻辑之后，重新从后台查出刚存入数据库中的那条数据的id，赋值给领域模型的IndentModel，然后推送到各个客户端；
 * 2.上菜、退菜、加菜、催单、换桌，只需要等待持久化数据库成功后，推送IndentModel到客户端即可
 * 3.结算、取消要等数据库更改成功后，推送IndentStyle到客户端
 */
@Service
public class IndentService {

    @Autowired
    private IndentDAO dao;
    @Autowired
    private SellAnalyzeDAO analyzedao;
    @Autowired
    private SimpMessageSendingOperations messaging;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private VipDAO vipDAO;
    /**
     * 增，删，改均涉及消息推送
     * @param model 客户端传递过来最新的数据
     * @return 
     */
    public boolean createIndent(IndentModel model) {
        if (!dao.addIndent(model)) {
            return false;
        } else {
            //由于Indent的设计，不会出现删除Indent情况，故表中存储的id最大的，就是最后插入的那一条数据
            int count = dao.countIndent();
            model.setId(count);
            messaging.convertAndSend("/topic/add","1");//stomp推送
            rabbitTemplate.convertAndSend("indent", "1");//rabbitmq推送
            return true;
        }
    }
    public boolean updateIndent(IndentModel model) {
        Indent indent = dao.updateIndent(model);
        if (indent != null) {
            messaging.convertAndSend("/topic/update", indent.getId());
            rabbitTemplate.convertAndSend("indent", "1");
            return true;
        } else {
            return false;
        }
    }
    public boolean updateIndentApp(IndentModel model,String reserve,String fulfill){
        Indent indent=dao.updateIndent(model, reserve, fulfill);
        if (indent != null) {
            messaging.convertAndSend("/topic/update",indent.getId());
            rabbitTemplate.convertAndSend("indent","1");
            return true;
        }else{
            return false;
        }
    }
    /**
     * 结算与取消订单，均涉及推送消息
     * @param model
     * @param telephone
     * @return 
     */
    public boolean updateIndentStyle(IndentStyle model,String telephone){
        Indent indent = dao.updateIndent(model);
        if (vipDAO.validateVip(telephone) && indent != null) {//如果是会员，则更新会员的消费金额和消费次数
            Vip vip = vipDAO.querySingle(telephone);
            vip.setConsumNumber(vip.getConsumNumber() + 1);
            vip.setTotalConsum(vip.getTotalConsum() + indent.getPrice());
            vipDAO.updateVip(vip);
        } else if (!vipDAO.validateVip(telephone) && indent != null) {//若之前不是会员则创建会员
            VipModel vipModel = new VipModel();
            vipModel.setPhoneNumber(telephone);
            vipModel.setTotalConsum(indent.getPrice());
            vipModel.setConsumNumber(1);
            vipDAO.addVip(vipModel);
        } else {
            return false;
        }
        if(model.getStyle()==1){
            messaging.convertAndSend("/topic/style",Integer.toString(model.getId()));
        }
        rabbitTemplate.convertAndSend("indent", "1");
        return true;
    }
    public boolean finishedIndentApp(String id){
        IndentStyle style=new IndentStyle();
        style.setId(Integer.valueOf(id));
        style.setStyle(1);
        Indent indent = dao.updateIndent(style);
        messaging.convertAndSend("/topic/style", id);
        rabbitTemplate.convertAndSend("indent", "1");
        return indent != null;
    }
    public boolean finishedIndentApp(String id,String telephone,String price){
        IndentStyle style = new IndentStyle();
        style.setId(Integer.valueOf(id));
        style.setStyle(1);
        if (dao.updateIndent(Integer.valueOf(id), price)) {
            Indent indent = dao.updateIndent(style);
            if (vipDAO.validateVip(telephone) && indent != null) {//如果是会员，则更新会员的消费金额和消费次数
                Vip vip = vipDAO.querySingle(telephone);
                vip.setConsumNumber(vip.getConsumNumber() + 1);
                vip.setTotalConsum(vip.getTotalConsum() + indent.getPrice());
                vipDAO.updateVip(vip);
            } else if (!vipDAO.validateVip(telephone) && indent != null) {//若之前不是会员则创建会员
                VipModel vipModel = new VipModel();
                vipModel.setPhoneNumber(telephone);
                vipModel.setTotalConsum(indent.getPrice());
                vipModel.setConsumNumber(1);
                vipDAO.addVip(vipModel);
            } else {
                return false;
            }
            messaging.convertAndSend("/topic/style",id);
            rabbitTemplate.convertAndSend("indent", "1");
            return true;
        }
        return false;
    }
    //查询相关
    public List<Indent> findAllUnderwayIndent(){
        List<Indent> indents=dao.queryAllUnderwayIndent();
        return indents;
    }
    public List<Indent> findUnderwayIndentOnPage(int page){
        List<Indent> indents=dao.queryUnderwayIndent(page);
        return indents;
    }
    public List<Indent> findCanceledIndentOnPage(int page){
        List<Indent> indents=dao.queryCanceledIndent(page);
        return indents;
    }
    public List<Indent> findFinishedIndentOnPage(int page){
        List<Indent> indents=dao.queryFinishedIndent(page);
        return indents;
    }
    //用于查询销售分析
    public List<SellAnalyze> findAllSellAnalyze(){
        List<SellAnalyze> analyzes=analyzedao.queryAllSellAnalyze();
        return analyzes;
    }
    public Indent findIndentOnId(int id){
        return dao.findOneIndent(id);
    }
    public boolean reminder(int id){
        return dao.reminder(id);
    }
}
