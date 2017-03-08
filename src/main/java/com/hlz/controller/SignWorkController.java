package com.hlz.controller;

import com.hlz.service.SignAndWorkService;
import com.hlz.webModel.WorkModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.hlz.entity.Sign;
import com.hlz.entity.WorkTime;
import java.util.ArrayList;
/**
 *只有添加签到和工作，没有更新和删除
 * @author Administrator 2017-3-7
 */
@RestController
public class SignWorkController {
    @Autowired
    private SignAndWorkService service;
    //id为user的id
    @RequestMapping(value="/addsign/{id}",produces="text/plain;charset=UTF-8",method=RequestMethod.GET)
    public String addSign(@PathVariable String id){
        boolean sign=service.addSign(Integer.valueOf(id));
        if(sign){
            return "success";
        }else{
            return "defeat";
        }
    }
    @RequestMapping(value="/addwork",produces="text/plain;charset=UTF-8",method=RequestMethod.POST)
    public String addWork(WorkModel model){
        boolean sign=service.addWork(model);
        if(sign){
            return "success";
        }else{
            return "defeat";
        }
    }
    @RequestMapping(value="/signs/{id}",produces="application/json;charset=UTF-8",method=RequestMethod.GET)
    public List<Sign> getSigns(@PathVariable String id){
        ArrayList<Sign> signs=service.findSignOnUserId(Integer.valueOf(id));
        return signs;
    }
    @RequestMapping(value="/works/{id}",produces="application/json;charset=UTF-8",method=RequestMethod.GET)
    public List<WorkTime> getWorks(@PathVariable String id){
        ArrayList<WorkTime> works=service.findWorkTimeOnUserID(Integer.valueOf(id));
        return works;
    }
}
