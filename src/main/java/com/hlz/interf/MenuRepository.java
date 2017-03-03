
package com.hlz.interf;

import com.hlz.webModel.MenuModel;
import java.util.List;
import com.hlz.entity.Menu;

/**
 *
 * @author Administrator
 */
public interface MenuRepository {
    boolean addMenu(MenuModel model);
    int count();//总数
    boolean updateMenu(MenuModel model);
    boolean deleteMenu(int id);
    List<Menu> queryMenu();
}
