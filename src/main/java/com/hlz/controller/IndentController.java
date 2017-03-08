package com.hlz.controller;

import com.hlz.entity.Indent;
import com.hlz.service.IndentService;
import com.hlz.webModel.IndentModel;
import com.hlz.webModel.IndentStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *涉及的主要功能：
 * 1.创建订单；2.增加菜品-总价和数量要更改；3.删除菜品（退菜）-总价和数量要更改；
 * 4.催单；5.结算订单；6.取消订单；7.换桌；8.上菜（注意更改第一次上菜时间，
 * 且不考虑删除菜品所带来的影响）；9.销售分析；10.利润分析（暂无）
 * @author Administrator 2017-3-8
 */
@RestController
public class IndentController {
    @Autowired
    private IndentService service;
    //创建
    @RequestMapping(value="/indent/add",produces="text/plain;charset=UTF-8",method=RequestMethod.POST)
    public String createIndent(IndentModel model){
        boolean sign=service.createIndent(model);
        if(sign){
            return "success";
        }else{
            return "defeat";
        }
    }
    /**
     * 增加菜品--添菜；
     * 删除菜品--退菜；
     * 催单；
     * 上菜--对于第一次上菜时间的控制，交由安卓客户端
     * 换桌
     * @param mdoel
     * @return 
     */
    @RequestMapping(value="/indent/update",produces="text/plain;charset=UTF-8",method=RequestMethod.POST)
    public String updateIndent(IndentModel mdoel){
        boolean sign=service.updateIndent(mdoel);
        if(sign){
            return "success";
        }else{
            return "defeat";
        }
    }
    //结算与取消订单
    @RequestMapping(value="/indent/style",produces="text/plain;charset=UTF-8",method=RequestMethod.POST)
    public String updateIndentStyle(IndentStyle model){
        boolean sign=service.updateIndentStyle(model);
        if(sign){
            return "success";
        }else{
            return "defeat";
        }
    }
    
}
