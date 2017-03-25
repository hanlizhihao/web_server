package com.hlz.dao;

import com.hlz.entity.Sign;
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
        List<Sign> result=dao.getSignByUserID(1);
        for(Sign s:result){
            System.out.println(s.getSignTime());
        }
    }
}
