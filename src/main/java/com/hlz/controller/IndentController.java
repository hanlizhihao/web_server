package com.hlz.controller;

import com.hlz.entity.Indent;
import com.hlz.entity.SellAnalyze;
import com.hlz.service.IndentService;
import com.hlz.webModel.IndentModel;
import com.hlz.webModel.IndentStyle;
import java.util.ArrayList;
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
    public String createIndent(@RequestParam("table")String table,@RequestParam("price")Double price,@RequestParam("reserve")
            String reserve){
        Map<String,String> reserveMap=new HashMap<>();
        String stringMenu=reserve.substring(1,reserve.length()-1);
        String[] arrayMenu=stringMenu.split(",");
        for(String s:arrayMenu){
            String[] one=s.split(":");
            reserveMap.put(one[0].substring(1,one[0].length()-1),one[1]);
        }
        IndentModel model=new IndentModel();
        model.setTable(table);
        model.setPrice(price);
        model.setReserve(reserveMap);
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
    //需要注意格式问题，由于使用JS不能直接发送对象数组，除非使用JSON，所以采用发送参数数组的方式，接受参数name[]、count[]、number[]都是一一对应
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
    @RequestMapping(value="/indent/update/app",produces="text/plain;charset=UTF-8",method=RequestMethod.POST)
    public String updateIndentString(@RequestParam("id") Integer id,@RequestParam("reserve") String reserve,@RequestParam("fulfill") String 
            fulfill,@RequestParam("table") String table,@RequestParam("reminderNumber") Integer 
            reminderNumber,@RequestParam("price") Double price,@RequestParam("time") String firstTime){
        IndentModel model = new IndentModel();
        model.setId(id);
        model.setPrice(price);
        model.setRemiderNumber(reminderNumber);
        model.setTable(table);
        if ("".equals(firstTime)) {
            model.setTime(0);
        } else {
            model.setTime(Long.valueOf(firstTime));
        }
        if(service.updateIndentApp(model,reserve,fulfill)){
            return "success";
        }else{
            return "defeat";
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
    @RequestMapping(value="/indent/app/notTelephone/finishedIndent/{id}",produces="text/plain;charset=UTF-8",method=RequestMethod.GET)
    public String finishedIndentById(@PathVariable String id){
        boolean sign=service.finishedIndentApp(id);
        if (!sign) {
            return "defeat";
        } else {
            return "success";
        }
    }
    @RequestMapping(value="/indent/app/finishedIndent/{id}",produces="text/plain;charset=UTF-8",method=RequestMethod.POST)
    public String finishedIndentByIdAndTelephone(@PathVariable String id,@RequestParam("telephone") String telephone,
            @RequestParam("price") String price){
        boolean sign = service.finishedIndentApp(id, telephone,price);
        if (!sign) {
            return "defeat";
        } else {
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
    @RequestMapping(value="reminder/{id}",produces="text/plain;charset=UTF-8",method=RequestMethod.GET)
    public String reminder(@PathVariable String id){
        if(service.reminder(Integer.valueOf(id))){
            return "success";
        }else{
            return "defeat";
        }
    }
}
