
package com.hlz.interf;

import com.hlz.entity.Users;
import com.hlz.webModel.UserModel;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface UserRepository {
    boolean addUser(UserModel model);
    boolean updateUser(Users user);
    boolean deleteUser(int id);
    List<Users> queryAllUser();
    Users querySingleUsers(int id);
}
