package com.hlz.controller;

import com.hlz.entity.Indent;
import com.hlz.entity.SellAnalyze;
import com.hlz.service.IndentService;
import com.hlz.webModel.IndentModel;
import com.hlz.webModel.IndentStyle;
import com.hlz.webModel.OrderModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
        if(!sign){
            return "defeat";
        }else{
            return "success";
        }
    }
//     增加菜品--添菜；
//     删除菜品--退菜；
//     催单；
//     上菜--对于第一次上菜时间的控制，交由安卓客户端
//     换桌
    @RequestMapping(value="/indent/update",produces="text/plain;charset=UTF-8",method=RequestMethod.POST)
    public String updateIndent(@RequestParam("id") Integer id,@RequestParam("name[]") String[] names,@RequestParam("count[]") String[] 
            counts,@RequestParam("number[]") String[] numbers,@RequestParam("table") String table,@RequestParam("reminderNumber") Integer 
            reminderNumber,@RequestParam("price") Double price,@RequestParam("time") String firstTime){
        IndentModel model=new IndentModel();
        model.setId(id);
        model.setPrice(price);
        model.setRemiderNumber(reminderNumber);
        model.setTable(table);
        if("".equals(firstTime)){
            model.setTime(0);
        }else{
            model.setTime(Long.valueOf(firstTime));
        }
        Map<String,String> reserve=new HashMap<>();
        Map<String,String> fulfill=new HashMap<>();
        for(int i=0;i<names.length;i++){
            reserve.put(names[i],counts[i]);
            fulfill.put(names[i],numbers[i]);
        }
        model.setFulfill(fulfill);
        model.setReserve(reserve);
        boolean sign=service.updateIndent(model);
        if(!sign){
            return "defeat";
        }else{
            return "success";
        }
    }
    //结算与取消订单，需要再次添加，将手机号作为会员
    @RequestMapping(value="/indent/style",produces="text/plain;charset=UTF-8",method=RequestMethod.POST)
    public String updateIndentStyle(IndentStyle model,@RequestParam("vip") String telephone){
        boolean sign=service.updateIndentStyle(model,telephone);
        if(!sign){
            return "defeat";
        }else{
            return "success";
        }
    }
    //获取所有正在进行的订单，用于响应App请求
    @RequestMapping(value="/underway",produces="application/json;charset=UTF-8",method=RequestMethod.GET)
    public ArrayList<Indent> getAllUnderwayIndent(){
        ArrayList<Indent> result=new ArrayList<>();
        List<Indent> indents=service.findAllUnderwayIndent();
        result.addAll(indents);
        return result;
    }
    //获取分页后的订单，用于响应web请求
    @RequestMapping(value="/underway/{id}",produces="application/json;charset=UTF-8",method=RequestMethod.GET)
    public ArrayList<Indent> getUnderwayIndentOnPage(@PathVariable String id){
        ArrayList<Indent> result=new ArrayList<>();
        List<Indent> indents=service.findUnderwayIndentOnPage(Integer.valueOf(id));
        result.addAll(indents);
        return result;
    }
    @RequestMapping(value="/canceled/{id}",produces="application/json;charset=UTF-8",method=RequestMethod.GET)
    public ArrayList<Indent> getCanceledIndentOnPage(@PathVariable String id){
        ArrayList<Indent> result=new ArrayList<>();
        List<Indent> indents=service.findCanceledIndentOnPage(Integer.valueOf(id));
        result.addAll(indents);
        return result;
    }
    @RequestMapping(value="/finished/{id}",produces="application/json;charset=UTF-8",method=RequestMethod.GET)
    public ArrayList<Indent> getFinishedIndentOnPage(@PathVariable String id){
        ArrayList<Indent> result=new ArrayList<>();
        List<Indent> indents=service.findFinishedIndentOnPage(Integer.valueOf(id));
        result.addAll(indents);
        return result;
    }
    @RequestMapping(value="/analyze",produces="application/json;charset=UTF-8",method=RequestMethod.GET)
    public ArrayList<SellAnalyze> getSellAnalyze(){
        List<SellAnalyze> result=service.findAllSellAnalyze();
        ArrayList<SellAnalyze> analyzes=new ArrayList<>();
        analyzes.addAll(result);
        return analyzes;
    }
    @RequestMapping(value="/indent/{id}",produces="application/json;charset=UTF-8",method=RequestMethod.GET)
    public Indent getIndentOnId(@PathVariable String id){
        Indent indent=service.findIndentOnId(Integer.valueOf(id));
        if(indent!=null){
            return indent;
        }else{
            indent=new Indent();
            indent.setTableId("404");
            return indent;
        }
    }
}
