
package com.hlz.interf;

import com.hlz.webModel.MenuModel;

/**
 *
 * @author Administrator
 */
public interface MenuRepository {
    boolean addMenu(MenuModel model);
    int count();//总数
    boolean updateMenu(MenuModel model);
    boolean deleteMenu(int id);
}
