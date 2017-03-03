package com.hlz.dao;

import com.hlz.webModel.IndentModel;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Administrator
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IndentModel model=new IndentModel();
        IndentDAO dao=new IndentDAO();
        Map<String,Integer> fulfill=new HashMap<>();
        fulfill.put("葱油饼",1);
        fulfill.put("水煮鱼",2);
        model.setReserve(fulfill);
        model.setPrice(50.5);
        model.setRemiderNumber(1);
        model.setTable("a1");
        dao.addIndent(model);
//        UserDAO dao=new UserDAO();
//        UserAddModel model=new UserAddModel();
//        model.setName("wugq");
//        model.setPassword("1q");
//        model.setStyle(0);
//        model.setUsername("q");
//        List<Users> result=dao.queryAllUser();
//        for(Users u:result){
//            System.out.println(u.getName());
//        }
    }
    
}
