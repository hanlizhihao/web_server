package com.hlz.controller;

import com.hlz.entity.Users;
import com.hlz.service.UserService;
import com.hlz.webModel.UserAddModel;
import com.hlz.webModel.UserOutput;
import java.sql.Date;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator 2017-3-6
 */
@RestController
public class UserController {
    @Autowired
    private UserService service;
    @RequestMapping(value="/users",produces="application/json;charset=UTF-8",method=RequestMethod.GET)
    public ArrayList<UserOutput> getUsers(){
        ArrayList<UserOutput> result=service.findAllUser();
        if(result==null||result.isEmpty()){
            result=new ArrayList<>();
            UserOutput user=new UserOutput();
            user.setName("404");
            result.add(user);
            return result;
        }else{
            return result;
        }
    }
    @RequestMapping(value="/user/{id}",produces="application/json;charset=UTF-8",method=RequestMethod.GET)
    public Users getOneUser(@PathVariable String id){
        Users user=service.findOneUser(Integer.valueOf(id));
        if(user==null){
            user=new Users();
            user.setName("404");
            return user;
        }else{
            return user;
        }
    }
    @RequestMapping(value="/user/add",produces="text/plain;charset=UTF-8",method=RequestMethod.POST)
    public String addUser(UserAddModel mdoel){
        boolean sign=service.addUser(mdoel);
        if(sign){
            return "success";
        }else{
            return "defeat";
        }
    }
    @RequestMapping(value="/user/update",produces="text/plain;charset=UTF-8",method=RequestMethod.POST)
    public String updateUser(@RequestParam("id") Integer id,@RequestParam("name") String name,@RequestParam("username") String username,
            @RequestParam("password") String password,@RequestParam("style") Integer style,@RequestParam("joinTime") Date joinTime){
        Users user=new Users();
        user.setId(style);
        user.setJoinTime(joinTime);
        user.setName(name);
        user.setPassword(password);
        user.setStyle(style);
        user.setUsername(username);
        boolean sign=service.updateUser(user);
        if(sign){
            return "success";
        }else{
            return "defeat";
        }
    }
    @RequestMapping(value="/user/delete/{id}",produces="text/plain;charset=UTF-8")
    public String deleteUser(@PathVariable String id){
        boolean sign=service.deleteUser(Integer.valueOf(id));
        if(sign){
            return "success";
        }else{
            return "defeat";
        }
    }
}
