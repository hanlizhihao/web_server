package com.hlz.service;

import com.hlz.dao.UserDAO;
import com.hlz.entity.Users;
import com.hlz.webModel.UserAddModel;
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
    public List<Users> findAllUser(){
         return dao.queryAllUser();
    }
    public Users findOneUser(int id){
        return dao.querySingleUsers(id);
    }
}
