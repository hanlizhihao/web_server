package com.hlz.service;

import com.hlz.dao.MenuDAO;
import com.hlz.entity.Menu;
import com.hlz.webModel.MenuModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator 2017-3-7
 */
@Service
public class MenuService {
    @Autowired
    private MenuDAO dao;
    @Autowired
    private SimpMessageSendingOperations messaging;
    public boolean addMenu(MenuModel model){
        return dao.addMenu(model);
    }
    public boolean updateMenu(MenuModel model){
        if(dao.updateMenu(model)){
            messaging.convertAndSend("/topic/menu","1");
            return true;
        }else{
            return false;
        }
    }
    public boolean deleteMenu(int id){
        return dao.deleteMenu(id);
    }
    public List<Menu> findAllMenu(){
        return dao.queryMenu();
    }
    public String getVersion(){
        return dao.getVersion();
    }
}
