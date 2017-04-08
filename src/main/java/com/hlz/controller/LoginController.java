package com.hlz.controller;

import com.hlz.service.LoginService;
import com.hlz.webModel.UserModel;
import com.hlz.webModel.UserPower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Administrator 2017-3-3
 */
@Controller
public class LoginController {
    @Autowired
    private LoginService service;
    @Autowired
    private UserPower user;
    @RequestMapping(value="/login",method=RequestMethod.POST)
    public String login(UserModel user){
        if(service.login(user)){
            return "redirect:/";
        }else{
            return "login";
        }
    }
    @RequestMapping(value={"/","/login"})
    public String index(){
        if(user.getUser()!=null){
            return "main";
        }else{
            return "login";
        }
    }
    @RequestMapping(value="app/login",produces="text/plain;charset=UTF-8",method=RequestMethod.POST)
    @ResponseBody
    public String appLogin(UserModel user){
        if(service.login(user)){
            return "success";
        }else{
            return "defeat";
        }
    }
}
