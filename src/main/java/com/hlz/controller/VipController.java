package com.hlz.controller;

import com.hlz.entity.Vip;
import com.hlz.service.VipService;
import com.hlz.webModel.VipModel;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator 2017-3-5
 */
@RestController
public class VipController {
    @Autowired
    private VipService service;
    //分页显示，若返回值为空，则返回一个Vip，名字为404
    @RequestMapping(value="/vips/{id}",produces="application/json;charset=UTF-8",method=RequestMethod.GET)
    public List<Vip> getVipOnPage(@PathVariable String id){
        ArrayList<Vip> vips=new ArrayList<>();
        List<Vip> result=service.findVipOnPage(Integer.valueOf(id));
        if(result==null||result.isEmpty()){
            Vip vip=new Vip();
            vip.setPhoneNumber("404");
            vips.add(vip);
            return vips;
        }
        vips.addAll(result);
        return vips;
    }
    @RequestMapping(value="/vip/{id}",produces="application/json;charset=UTF-8",method=RequestMethod.GET)
    public Vip getOneVip(@PathVariable String id){
        Vip vip=service.findOneVip(Integer.valueOf(id));
        if(vip==null){
            vip=new Vip();
            vip.setPhoneNumber("404");
            return vip;
        }
        return vip;
    }
    @RequestMapping(value="/vip/add",produces="text/plain;charset=UTF-8",method=RequestMethod.POST)
    public String addVip(VipModel model){
        boolean result=service.addVip(model);
        if(result){
            return "success";
        }else{
            return "defeat";
        }
    }
    @RequestMapping(value="/vip/validate",produces="text/plain;charset=UTF-8",method=RequestMethod.POST)
    public String validateVip(@RequestParam("telephone") String telephone){
        if("".equals(telephone)){
            return "defeat";
        }
        VipModel model=new VipModel();
        model.setPhoneNumber(telephone);
        boolean result=service.validateVip(model);
        if(result){
            return "success";
        }else{
            return "defeat";
        }
    }
    @RequestMapping(value = "/vip/update", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
    public String updateVip(Vip vip) {
        boolean result = service.updateVip(vip);
        if (result) {
            return "success";
        } else {
            return "defeat";
        }
    }
    @RequestMapping(value="/vip/delete/{id}",produces="text/plain;charset=UTF-8")
    public String deleteVip(@PathVariable String id){
        boolean sign=service.deleteVip(Integer.valueOf(id));
        if(sign){
            return "success";
        }else{
            return "defeat";
        }
    }
}
