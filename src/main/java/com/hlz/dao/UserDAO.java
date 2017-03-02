package com.hlz.dao;

import com.hlz.entity.Users;
import com.hlz.interf.UserRepository;
import com.hlz.webModel.UserModel;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Administrator 2017-3-1
 */
public class UserDAO implements UserRepository{

    @Override
    public boolean addUser(UserModel model) {
        SessionFactory sf=SessionFactoryUtil.getSessionFactory();
        Session session=sf.openSession();
        Users user=new Users();
        user.setJoinTime(new java.sql.Date(System.currentTimeMillis()));
        user.setName(model.getName());
        user.setPassword(model.getPassword());
        user.setStyle(model.getStyle());
        user.setUsername(model.getUsername());
        Transaction t= session.beginTransaction();
        try{
            session.save(user);
            t.commit();
            System.out.println("成功添加一个用户");
        }catch(Exception e){
            t.rollback();
            e.printStackTrace();
            return false;
        }finally{
          session.close();
        }
        return true;
    }

    @Override
    public boolean updateUser(Users user) {
        SessionFactory sf=SessionFactoryUtil.getSessionFactory();
        Session session=sf.openSession();
        Users userTemp=(Users)session.get(Users.class,user.getId());
        userTemp.setJoinTime(user.getJoinTime());
        userTemp.setName(user.getName());
        userTemp.setPassword(user.getPassword());
        userTemp.setStyle(user.getStyle());
        userTemp.setUsername(user.getUsername());
        session.clear();
        Transaction ts = session.beginTransaction();
        try {
            session.update(user);
            ts.commit();
        }catch(Exception e){
            e.printStackTrace();
            ts.rollback();
            return false;
        }finally{
            session.close();
        }
        System.out.print("修改用户信息成功");
        return true;
    }

    @Override
    public boolean deleteUser(int id) {
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        Session session = sf.openSession();
        Users a = (Users) session.get(Users.class, id);
        Transaction ts = session.beginTransaction();
        session.delete(a);
        try {
            ts.commit();
        } catch (Exception e) {
            e.printStackTrace();
            ts.rollback();
            return false;
        } finally {
            session.close();
        }
        System.out.print("删除账户信息成功");
        return true;
    }

    @Override
    public List<Users> queryAllUser() {
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        List<Users> result;
        try (Session session = sf.openSession()) {
            String hql="from Users order by id";
            Query query= session.createQuery(hql);
            result = query.getResultList();
            System.out.println("成功查询所有用户信息");
        }
        return result;  
    }

    @Override
    public Users querySingleUsers(int id) {
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        Users user;
        try (Session session = sf.openSession()) {
            user = (Users)session.get(Users.class, id);
        }
        return user;
    }

}
