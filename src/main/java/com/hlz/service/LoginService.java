package com.hlz.service;

import com.hlz.dao.UserDAO;
import com.hlz.entity.Users;
import com.hlz.webModel.UserModel;
import com.hlz.webModel.UserPower;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator 2017-3-3
 */
@Service
public class LoginService {
    @Autowired
    private UserDAO dao;
    @Autowired
    private UserPower power;
    public Users login(UserModel userModel) {
        String name = userModel.getUsername();
        String password = userModel.getPassword();
        if(name==null||password==null){
            return null;
        }
        List<Users> result = dao.queryAllUser();
        for (Users u : result) {
            if(name.equals(u.getUsername())&&password.equals(u.getPassword())){
                power.setUser(u);
                return u;
            }
        }
        return null;
    }
}
