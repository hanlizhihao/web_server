package com.hlz.dao;

import com.hlz.entity.Indent;
import com.hlz.interf.IndentRepository;
import com.hlz.util.IndentModelUtil;
import com.hlz.webModel.IndentModel;
import com.hlz.webModel.IndentStyle;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Administrator 2017-2-26
 */
@Repository
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class IndentDAO implements IndentRepository{
    @Autowired
    private SellAnalyzeDAO dao;
    @Override
    public boolean addIndent(IndentModel model) {
       SessionFactory sf=SessionFactoryUtil.getSessionFactory();
       Session session=sf.openSession();
       Indent indent=new Indent();
       indent.setBeginTime(new Date(System.currentTimeMillis()));
       Map<String,Integer> reserves=model.getReserve();
       String reserve="";
       for(Map.Entry<String,Integer> entry:reserves.entrySet()){
           reserve=reserve+entry.getKey()+"a"+entry.getValue()+"e";
       }
       indent.setReserve(reserve);
       indent.setReminderNumber(0);
       indent.setEndTime(null);
       indent.setFirstTime(null);
       indent.setFulfill("");
       indent.setFulfillNumber(0);
       indent.setPrice(model.getPrice());
       indent.setReserveNumber(reserves.size());
       indent.setStyle(0);//设置其状态为正在进行
       indent.setTableId(model.getTable());
       Transaction t= session.beginTransaction();
        try {
            session.save(indent);
            t.commit();// 提交事务
            System.out.print("成功添加一条餐桌信息");
            return true;
        } catch (Exception e) {
            t.rollback();
            return false;
        } finally {
            session.close();
        }
    }
    @Override
    public boolean updateIndent(IndentModel model) {
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        Session session = sf.openSession();
        Transaction ts = session.beginTransaction();
        Indent indent=session.get(Indent.class,model.getId());
        indent=IndentModelUtil.TransformModel(model, indent);
        session.update(indent);
        try {
            ts.commit();
        } catch (Exception e) {
            e.printStackTrace();
            ts.rollback();
            return false;
        } finally {
            session.close();
        }
        System.out.print("修改餐桌信息成功");
        return true;
    }
    @Override
    //专门用于更新餐桌状态
    public boolean updateIndent(IndentStyle style) {
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        Session session = sf.openSession();
        Transaction ts = session.beginTransaction();
        Indent indent = session.get(Indent.class, style.getId());
        indent = IndentModelUtil.TransformStyleModel(style, indent);
        session.update(indent);
        try {
            ts.commit();
        } catch (Exception e) {
            e.printStackTrace();
            ts.rollback();
            return false;
        } finally {
            session.close();
        }
        if (style.getStyle() == 1) {
            return dao.updateSellAnalyze(style, indent);
        } else {
            System.out.print("修改餐桌信息成功");
            return true;
        }
    }
    @Override
    public List<Indent> queryUnderwayIndent(int page) {
        String hql="from Indent order by id where style=0";
        int pageSize=10;
        SessionFactory sf=SessionFactoryUtil.getSessionFactory();
        List<Indent> result;
        try (Session session = sf.openSession()) {
            Query query=(Query) session.createQuery(hql);
            //增加分页起点
            int from=(page-1)*pageSize;
            query.setFirstResult(from);
            query.setMaxResults(pageSize);
            result = query.getResultList();
            System.out.print("分页查询订单信息成功");
        }
        return result;
    }

    @Override
    public List<Indent> queryFinishedIndent(int page) {
        String hql = "from Indent order by id where style=1";
        int pageSize = 10;
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        List<Indent> result;
        try (Session session = sf.openSession()) {
            Query query = (Query) session.createQuery(hql);
            //增加分页起点
            int from = (page - 1) * pageSize;
            query.setFirstResult(from);
            query.setMaxResults(pageSize);
            result = query.getResultList();
            System.out.print("分页查询订单信息成功");
        }
        return result;
    }

    @Override
    public List<Indent> queryCanceledIndent(int page) {
        String hql = "from Indent order by id where style=2";
        int pageSize = 10;
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        List<Indent> result;
        try (Session session = sf.openSession()) {
            Query query = (Query) session.createQuery(hql);
            //增加分页起点
            int from = (page - 1) * pageSize;
            query.setFirstResult(from);
            query.setMaxResults(pageSize);
            result = query.getResultList();
            System.out.print("分页查询订单信息成功");
        }
        return result;
    }

    @Override
    public Indent findOneIndent(int id) {
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        try (Session session = sf.openSession()) {
            Indent a = session.get(Indent.class, id);
            System.out.println("查询一个订单成功");
            return a;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long countUnderway() {
        SessionFactory sf=SessionFactoryUtil.getSessionFactory();
        int rows;
        try (Session session = sf.openSession()) {
            String hql="select count(*) from Indent where style=0";//查询总行数
            Query query= (Query)session.createQuery(hql);
            rows = Integer.valueOf(query.getSingleResult().toString()); //查询总行数
            System.out.println("正在进行的订单总数为："+rows);
        } //查询总行数
       return rows; 
    }

    @Override
    public long countFinished() {
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        int rows;
        try (Session session = sf.openSession()) {
            String hql = "select count(*) from Indent where style=1";//查询总行数
            Query query = (Query) session.createQuery(hql);
            rows = Integer.valueOf(query.getSingleResult().toString()); //查询总行数
            System.out.println("已完成的订单总数为：" + rows);
        } //查询总行数
        return rows;
    }

    @Override
    public long countCanceled() {
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        int rows;
        try (Session session = sf.openSession()) {
            String hql = "select count(*) from Indent where style=2";//查询总行数
            Query query = (Query) session.createQuery(hql);
            rows = Integer.valueOf(query.getSingleResult().toString()); //查询总行数
            System.out.println("已经取消的订单总数为：" + rows);
        } //查询总行数
        return rows;
    }
}
