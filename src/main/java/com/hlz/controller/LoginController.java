package com.hlz.controller;

import com.hlz.entity.Users;
import com.hlz.service.LoginService;
import com.hlz.webModel.UserModel;
import com.hlz.webModel.UserOutput;
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
        if(service.login(user)!=null){
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
    @RequestMapping(value="app/login",produces="application/json;charset=UTF-8",method=RequestMethod.POST)
    @ResponseBody
    public UserOutput appLogin(UserModel user){
        Users u=service.login(user);
        UserOutput us=new UserOutput();
        us.setId(u.getId());
        us.setJoinTime(u.getJoinTime());
        us.setName(u.getName());
        us.setPassword(u.getPassword());
        us.setStyle(u.getStyle());
        us.setUsername(u.getUsername());
        if(u!=null){
            return us;
        }else{
            u.setName("");
            return us;
        }
    }
    @RequestMapping(value="/exitLogin",produces="text/plain;charset=UTF-8",method=RequestMethod.GET)
    public String exitLogin(){
        Users exitUser=user.getUser();
        user.setUser(null);
        System.out.println(exitUser.getName()+exitUser.getId());
        return "login";
    }
}
