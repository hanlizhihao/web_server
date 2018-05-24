package com.hlz.service;

import com.hlz.dao.UserDAO;
import com.hlz.entity.Users;
import com.hlz.webModel.UserAddModel;
import com.hlz.webModel.UserOutput;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator 2017-3-5
 */
@Service
public class UserService {
    @Autowired
    private UserDAO dao;

    @Autowired
    private SignAndWorkService signAndWorkService;

    public boolean addUser(UserAddModel model){
        return dao.addUser(model);
    }
    public boolean updateUser(Users user){
        Users b=dao.updateUser(user);
        return b != null;
    }
    public boolean deleteUser(int id){
        Users b=dao.deleteUser(id);
        return b!=null;
    }
    public ArrayList<UserOutput> findAllUser(){
         List<Users> users=dao.queryAllUser();
         ArrayList<UserOutput> result=new ArrayList<>();
        for (Users user : users) {
            UserOutput userOutput=new UserOutput();
            userOutput.setId(user.getId());
            userOutput.setJoinTime(user.getJoinTime());
            userOutput.setName(user.getName());
            userOutput.setPassword(user.getPassword());
            userOutput.setStyle(user.getStyle());
            userOutput.setUsername(user.getUsername());
            userOutput.setSignNumber(signAndWorkService.findSignOnUserId(user.getId()).size());
            result.add(userOutput);
        }
        return result;
    }
    public Users findOneUser(int id){
        return dao.querySingleUsers(id);
    }
}
