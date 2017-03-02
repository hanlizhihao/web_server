package com.hlz.dao;

import com.hlz.entity.Sign;
import com.hlz.entity.Users;
import com.hlz.entity.WorkTime;
import com.hlz.interf.SignAndWorkRepository;
import com.hlz.webModel.WorkModel;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.WebApplicationContext;

/**
 *添加签到信息和工作时间的信息是特殊的
 * 对于Service，它只需要调用add函数，由函数自己判断，是调用update还是直接返回true(对于签到)
 * @author Administrator 2017-3-1
 */
@Repository
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class SignAndWorkDAO implements SignAndWorkRepository{
    private Set<Integer> userSign;
    private Set<Integer> userWork;
    public SignAndWorkDAO(){
        this.userSign=new HashSet();
        this.userWork=new HashSet();
    }
    @Override
    public boolean addSign(int id) {//id是user的id
        SessionFactory sf=SessionFactoryUtil.getSessionFactory();
        Session session=sf.openSession();
        if(userSign.contains(id)){
            return true;
        }
        Users user=(Users)session.get(Users.class,id);
        Sign sign=new Sign();
        sign.setSignTime(new java.sql.Date(System.currentTimeMillis()));
        Transaction t= session.beginTransaction();
        try{
            session.save(sign);
            session.saveOrUpdate(user);
            t.commit();
            System.out.println("成功添加一个用户");
        }catch(Exception e){
            t.rollback();
            e.printStackTrace();
            return false;
        }finally{
          session.close();
        }
        userSign.add(id);
        return true;
    }

    @Override
    public boolean addWork(WorkModel model) {
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        Session session = sf.openSession();
        if(userWork.contains(model.getId())){
            session.close();
            return updateWork(model);
        }
        Users user=(Users)session.get(Users.class,model.getId());
        WorkTime work=new WorkTime();
        work.setContinueTime(model.getTime());
        work.setOprationTime(new java.sql.Date(System.currentTimeMillis()));
        Transaction t= session.beginTransaction();
        try{
            session.save(work);
            session.saveOrUpdate(user);
            t.commit();
            System.out.println("成功添加一个工作时间信息");
        }catch(Exception e){
            t.rollback();
            e.printStackTrace();
            return false;
        }finally{
          session.close();
        }
        userWork.add(model.getId());
        return true;
    }

    @Override
    public boolean deleteSign(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteWork(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateWork(WorkModel model) {
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        Session session = sf.openSession();
        String hql="select count(*) from WorkTime";
        Query query=(Query)session.createQuery(hql);
        int rows = Integer.valueOf(query.getSingleResult().toString());//查询总行数
        WorkTime work=(WorkTime)session.get(WorkTime.class, rows);
        work.setContinueTime(model.getTime());
        Transaction t=session.beginTransaction();
        try{
            t.commit();
            System.out.println("添加工作信息成功");
        }catch(Exception e){
            e.printStackTrace();
            t.rollback();
            return false;
        }finally{
            session.close();
        }
        return true;
    }

}
