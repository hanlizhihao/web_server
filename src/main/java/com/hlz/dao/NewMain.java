package com.hlz.dao;

import com.hlz.entity.Users;
import com.hlz.webModel.UserAddModel;
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
        UserAddModel model=new UserAddModel();
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
