package com.hlz.dao;

import com.hlz.entity.Users;
import com.hlz.webModel.UserModel;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        UserDAO dao=new UserDAO();
        UserModel model=new UserModel();
        model.setName("wugq");
        model.setPassword("1q");
        model.setStyle(0);
        model.setUsername("q");
        List<Users> result=dao.queryAllUser();
        for(Users u:result){
            System.out.println(u.getName());
        }
        // TODO code application logic here
    }
    
}
