package com.hlz.service;

import com.hlz.dao.VipDAO;
import com.hlz.entity.Vip;
import com.hlz.webModel.VipModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator 2017-3-4
 */
@Service
public class VipService {
    @Autowired
    private VipDAO dao;
    public boolean addVip(VipModel model){
        return dao.addVip(model);
    }
    public boolean deleteVip(int id){
        Vip vip=dao.deleteVip(id);
        //如果为空，说明删除Vip失败
        return vip != null;
    }
    public boolean updateVip(Vip vip){
        Vip result=dao.updateVip(vip);
        return result!=null;
    }
    public List<Vip> findVipOnPage(int page){
        return dao.queryVipOnPage(page);
    }
    public Vip findOneVip(int id){
        return dao.querySingle(id);
    }
    //如果验证不通过，则将这条数据存入数据库
    public boolean validateVip(VipModel model){
        boolean sign=dao.validateVip(model.getPhoneNumber());
        if(!sign){
            return false;
        }else{
            return true;
        }
    }
}
