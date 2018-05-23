package com.hlz.dao;

import com.hlz.entity.Sign;
import com.hlz.entity.Users;
import com.hlz.entity.WorkTime;
import com.hlz.interf.UserRepository;
import com.hlz.webModel.UserAddModel;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator 2017-3-1
 */
@Repository
public class UserDAO implements UserRepository{

    @Override
    public boolean addUser(UserAddModel model) {
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
    public Users updateUser(Users user) {
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
            return null;
        }finally{
            session.close();
        }
        System.out.print("修改用户信息成功");
        return userTemp;
    }
    @Override
    public Users deleteUser(int id) {
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
            return null;
        } finally {
            session.close();
        }
        System.out.print("删除账户信息成功");
        return a;
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
    @Override
    public Users findByUsername(String username) {
        String hql="from Users  where username=? order by id";
        SessionFactory sf=SessionFactoryUtil.getSessionFactory();
        Session session=sf.openSession();
        Query query=session.createQuery(hql);
        query.setParameter(0,username);
        Users user=(Users)query.getSingleResult();
        System.out.println("根据username查询User成功");
        session.close();
        return user;
    }

    @Override
    public List<Sign> getSignByUserID(int id) {
        SessionFactory sf=SessionFactoryUtil.getSessionFactory();
        List<Sign> signs;
        try(Session session = sf.openSession()) {
            Transaction t=session.beginTransaction();
            Users user= session.get(Users.class, id);
            signs = user.getSignList();
            Stream<Sign> signStream =  signs.stream().filter(sign -> {
                LocalDateTime localDateTime = LocalDateTime.now();
                LocalDateTime mouth = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), 1,0,0);
                Instant mouthBegin = mouth.atZone(ZoneId.systemDefault()).toInstant();
                return sign.getSignTime().toInstant().isAfter(mouthBegin);
            });
            signs = signStream.collect(Collectors.toList());
            try{
                t.commit();
            }catch(Exception e){
                e.printStackTrace();
                t.rollback();
                return null;
            }
        }
        return signs;
    }

    @Override
    public List<WorkTime> getWorkTimeByUserID(int id) {
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        List<WorkTime> signs;
        try (Session session = sf.openSession()) {
            Transaction t = session.beginTransaction();
            Users user = (Users) session.get(Users.class, id);
            signs = user.getWorkTimeList();
            try {
                t.commit();
            } catch (Exception e) {
                e.printStackTrace();
                t.rollback();
                return null;
            }
            signs.forEach((work) -> {
                System.out.println(work.getContinueTime());
            });
        }
        return signs;
    }
}
