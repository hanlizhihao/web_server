package com.hlz.dao;

import com.hlz.entity.Indent;
import com.hlz.interf.IndentRepository;
import com.hlz.util.IndentModelUtil;
import com.hlz.webModel.IndentModel;
import com.hlz.webModel.IndentStyle;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator 2017-2-26
 */
@Repository
public class IndentDAO implements IndentRepository{
    @Autowired
    private final SellAnalyzeDAO dao;
    public IndentDAO(){
        this.dao=new SellAnalyzeDAO();
    }
    @Override
    public boolean addIndent(IndentModel model) {
       SessionFactory sf=SessionFactoryUtil.getSessionFactory();
       Session session=sf.openSession();
       Indent indent=new Indent();
       Timestamp beginTime=new Timestamp(System.currentTimeMillis());
       indent.setBeginTime(beginTime);
       Map<String,String> reserves=model.getReserve();
       String reserve="";
       for(Map.Entry<String,String> entry:reserves.entrySet()){
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
    public Indent updateIndent(IndentModel model) {
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
            return null;
        } finally {
            session.close();
        }
        System.out.print("修改餐桌信息成功");
        return indent;
    }
    //Android专用更新Indent
    @Override
    public Indent updateIndent(IndentModel model, String reserves, String fulfill) {
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        Session session = sf.openSession();
        Transaction ts = session.beginTransaction();
        Indent indent = session.get(Indent.class, model.getId());
        int reserveNumber=0;
        int fulfillNumber=0;
        reserves=reserves.substring(0,reserves.length()-1);
        fulfill=fulfill.substring(0,fulfill.length()-1);
        String[] reserveArray=reserves.split("e");
        String[] fulfillArray=fulfill.split("e");
        for(String s:reserveArray){
            String[] singleReserve=s.split("a");
            reserveNumber=reserveNumber+Integer.valueOf(singleReserve[1]);
        }
        for(String s:fulfillArray){
            String[] singleFulfill=s.split("a");
            fulfillNumber=fulfillNumber+Integer.valueOf(singleFulfill[1]);
        }
        indent.setReserveNumber(reserveNumber);
        indent.setFulfillNumber(fulfillNumber);
        indent.setReserve(reserves);
        indent.setFulfill(fulfill);
        indent.setPrice(model.getPrice());
        indent.setReminderNumber(model.getRemiderNumber());
        indent.setTableId(model.getTable());
        //如果为0表示不存在firstTime，还没上菜
        if (model.getTime() == 0) {
            indent.setFirstTime(null);
        } else {
            indent.setFirstTime(new java.sql.Timestamp(model.getTime()));
        }
        session.update(indent);
        try{
            ts.commit();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return indent;
    }
    @Override
    //专门用于更新餐桌状态
    public Indent updateIndent(IndentStyle style) {
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        Session session = sf.openSession();
        Transaction ts = session.beginTransaction();
        Indent indent = session.get(Indent.class, style.getId());
        if(indent==null){
            System.out.println("没有指定的订单id");
            System.out.println(style.getId());
            System.out.println(style.getStyle());
            return null;
        }else{
            indent.setStyle(style.getStyle());
        }
        indent.setEndTime(new Timestamp(System.currentTimeMillis()));
        try {
            session.update(indent);
            ts.commit();
        } catch (Exception e) {
            e.printStackTrace();
            ts.rollback();
            return null;
        } finally {
            session.close();
        }
        if (style.getStyle() == 1) {
            return dao.updateSellAnalyze(style, indent);
        } else {
            System.out.print("取消订单信息成功");
            return indent;
        }
    }
    @Override
    public List<Indent> queryUnderwayIndent(int page) {
        String hql="from Indent i where i.style=:s";
        int pageSize=10;
        SessionFactory sf=SessionFactoryUtil.getSessionFactory();
        List<Indent> result;
        try (Session session = sf.openSession()) {
            Query query=(Query) session.createQuery(hql);
            //增加分页起点
            query.setParameter("s",0);
            query.setParameter(0,"0");
            int from=(page-1)*pageSize;
            query.setFirstResult(from);
            query.setMaxResults(pageSize);
            result = query.getResultList();
            System.out.print("分页查询订单信息成功");
            session.close();
        }
        return result;
    }

    @Override
    public List<Indent> queryFinishedIndent(int page) {
        String hql = "from Indent i where i.style=:s";
        int pageSize = 10;
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        List<Indent> result;
        try (Session session = sf.openSession()) {
            Query query = (Query) session.createQuery(hql);
            //增加分页起点
            query.setParameter("s",1);
            int from = (page - 1) * pageSize;
            query.setFirstResult(from);
            query.setMaxResults(pageSize);
            result = query.getResultList();
            session.close();
            System.out.print("分页查询订单信息成功");
        }
        return result;
    }
    @Override
    public List<Indent> queryCanceledIndent(int page) {
        String hql = "from Indent i where i.style=:s order by i.id";
        int pageSize = 10;
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        List<Indent> result;
        try (Session session = sf.openSession()) {
            Query query = (Query) session.createQuery(hql);
            //增加分页起点
            query.setParameter("s",2);
            int from = (page - 1) * pageSize;
            query.setFirstResult(from);
            query.setMaxResults(pageSize);
            result = query.getResultList();
            session.close(); 
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
            session.close();
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
            String hql="select count(*) from Indent i where i.style=:s";//查询总行数
            Query query= (Query)session.createQuery(hql);
            query.setParameter("s",0);
            rows = Integer.valueOf(query.getSingleResult().toString()); //查询总行数
            System.out.println("正在进行的订单总数为："+rows);
            session.close();
        } //查询总行数
       return rows; 
    }

    @Override
    public long countFinished() {
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        int rows;
        try (Session session = sf.openSession()) {
            String hql = "select count(*) from Indent i where i.style=:s";//查询总行数
            Query query = (Query) session.createQuery(hql);
            query.setParameter("s",1);
            rows = Integer.valueOf(query.getSingleResult().toString()); //查询总行数
            System.out.println("已完成的订单总数为：" + rows);
            session.close(); 
        } 
        return rows;
    }

    @Override
    public long countCanceled() {
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        int rows;
        try (Session session = sf.openSession()) {
            String hql = "select count(*) from Indent i where i.style=:s";//查询总行数
            Query query = (Query) session.createQuery(hql);
            query.setParameter("s",2);
            rows = Integer.valueOf(query.getSingleResult().toString()); //查询总行数
            System.out.println("已经取消的订单总数为：" + rows);
            session.close();
        } //查询总行数
        return rows;
    }

    @Override
    public List<Indent> queryAllUnderwayIndent() {
        String hql="from Indent i where i.style=:s";
        SessionFactory sf=SessionFactoryUtil.getSessionFactory();
        List<Indent> result;
        try (Session session = sf.openSession()) {
            Query query=(Query) session.createQuery(hql);
            query.setParameter("s",0);
            result = query.getResultList();
            System.out.print("查询正在进行订单信息成功");
            session.close();
        }
        return result;
    }
    @Override
    public int countIndent() {
        String hql = "select count(*) from Indent";
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        Session session=sf.openSession();
        Query query=session.createQuery(hql);
        int rows=Integer.valueOf(query.getSingleResult().toString());
        return rows;
    }

    @Override
    public boolean reminder(int id) {
        SessionFactory sf=SessionFactoryUtil.getSessionFactory();
        Session session=sf.openSession();
        Transaction t=session.beginTransaction();
        Indent indent=session.get(Indent.class, id);
        indent.setReminderNumber(indent.getReminderNumber()+1);
        session.update(indent);
        try{
            t.commit();
        }catch(Exception e){
            t.rollback();
            return false;
        }finally{
            session.close();
        }
        return true;
    }
}
