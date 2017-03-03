package com.hlz.interf;

import com.hlz.entity.Users;
import com.hlz.webModel.UserAddModel;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface UserRepository {
    boolean addUser(UserAddModel model);
    boolean updateUser(Users user);
    boolean deleteUser(int id);
    List<Users> queryAllUser();
    Users querySingleUsers(int id);
}
