package com.hlz.dao;

import com.hlz.entity.Vip;
import com.hlz.interf.VipRepository;
import com.hlz.webModel.VipModel;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Administrator 2017-3-2
 */
@Repository
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class VipDAO implements VipRepository{

    @Override
    public boolean addVip(VipModel model) {
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        Session session = sf.openSession();
        Vip vip = new Vip();
        vip.setConsumNumber(model.getConsumNumber());
        vip.setJoinTime(new java.sql.Date(System.currentTimeMillis()));
        vip.setName(model.getName());
        vip.setPhoneNumber(model.getPhoneNumber());
        vip.setTotalConsum(model.getTotalConsum());
        Transaction t = session.beginTransaction();
        try {
            session.save(vip);
            t.commit();// 提交事务
            System.out.print("成功添加一条会员信息");
        } catch (Exception e) {
            t.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public boolean updateVip(Vip vip) {
        SessionFactory sf=SessionFactoryUtil.getSessionFactory();
        Session session=sf.openSession();
        Transaction ts= session.beginTransaction();
        session.update(vip);
        try{
            ts.commit();
        }catch(Exception e){
            e.printStackTrace();
            ts.rollback();
            return false;
        }finally{
            session.close();
        }
        System.out.print("修改会员信息成功");
        return true;
    }

    @Override
    public boolean deleteVip(int id) {
        SessionFactory sf=SessionFactoryUtil.getSessionFactory();
        Session session=sf.openSession();
        Vip a=(Vip)session.get(Vip.class,id);
        Transaction ts= session.beginTransaction();
        session.delete(a);
        try{
            ts.commit();
        }catch(Exception e){
            e.printStackTrace();
            ts.rollback();
            return false;
        }finally{
            session.close();
        }
        System.out.print("删除会员信息成功");
        return true;
    }

    @Override
    public int countVip() {
        SessionFactory sf=SessionFactoryUtil.getSessionFactory();
        Session session=sf.openSession();
        String hql="select count(*) from Vip";//查询总行数
        Query query= session.createQuery(hql);
       int rows=Integer.valueOf(query.getSingleResult().toString());//查询总行数
       System.out.println("会员信息的总数为："+rows);
       session.close();
       return rows;
    }

    @Override
    public List<Vip> queryVipOnPage(int page) {
        String hql="from Vip order by id";
        int pageSize=10;
        SessionFactory sf=SessionFactoryUtil.getSessionFactory();
        Session session=sf.openSession();
        Query query=session.createQuery(hql);
        //增加分页起点
        int from=(page-1)*pageSize;
        query.setFirstResult(from);
        query.setMaxResults(pageSize);
        List<Vip> result=query.getResultList();
        System.out.print("分页查询会员信息成功");
        session.close();
        return result;
    }

    @Override
    public Vip querySingle(int id) {
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        Session session = sf.openSession();
        try {
            Vip a = session.get(Vip.class, id);
            System.out.println("查询一条会员信息成功");
            return a;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

}
