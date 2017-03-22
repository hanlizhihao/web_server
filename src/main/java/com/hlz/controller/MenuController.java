package com.hlz.controller;

import com.hlz.entity.Menu;
import com.hlz.service.MenuService;
import com.hlz.webModel.MenuModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator 2017-3-7
 */
@RestController
public class MenuController {
    @Autowired
    private MenuService service;
    @RequestMapping(value="/menu/add",produces="text/plain;charset=UTF-8",method=RequestMethod.POST)
    public String addMenu(MenuModel model){
        boolean sign=service.addMenu(model);
        if(!sign){
            return "defeat";
        }else{
            return "success";
        }
    }
    @RequestMapping(value="/menu/update",produces="text/plain;charset=UTF-8",method=RequestMethod.POST)
    public String updateMenu(MenuModel model){
        boolean sign=service.updateMenu(model);
        if(!sign){
            return "defeat";
        }else{
            return "success";
        }    
    }
    @RequestMapping(value="/menu/delete/{id}",produces="text/plain;charset=UTF-8",method=RequestMethod.GET)
    public String deleteMenu(@PathVariable String id){
        boolean sign=service.deleteMenu(Integer.valueOf(id));
        if(!sign){
            return "defeat";
        }else{
            return "success";
        }
    }
    @RequestMapping(value="/menus",produces="application/json;charset=UTF-8",method=RequestMethod.GET)
    public List<Menu> getMenus(){
        return service.findAllMenu();
    }
    @RequestMapping(value="/version",produces="test/plain;charset=UTF-8",method=RequestMethod.GET)
    public String getVersion(){
        return service.getVersion();
    }
}
