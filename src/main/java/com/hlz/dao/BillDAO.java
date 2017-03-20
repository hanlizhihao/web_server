package com.hlz.dao;

import com.hlz.entity.Bill;
import com.hlz.webModel.BillModel;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import javax.persistence.Query;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator 2017-3-2
 */
@Repository
public class BillDAO{
    public List<Bill> queryAll() {
        String hql="from Bill order by id";
        SessionFactory sf=SessionFactoryUtil.getSessionFactory();
        Session session=sf.openSession();
        Query query=session.createQuery(hql);
        List<Bill> result=query.getResultList();
        System.out.print("查询账单信息成功");
        session.close();
        return result;
    }
    public boolean addBill(BillModel model) {
        SessionFactory sf=SessionFactoryUtil.getSessionFactory();
        Session session=sf.openSession();
        Bill bill=new Bill();
        bill.setComment(model.getComment());
        bill.setName(model.getName());
        bill.setOccurrenceTime(model.getOccurrenceTime());
        bill.setPrice(model.getPrice());
        bill.setRecordTime(new java.sql.Date(System.currentTimeMillis()));
        Transaction t=session.beginTransaction();
        try{
            session.save(bill);
            t.commit();
        }catch(Exception e){
            e.printStackTrace();
            t.rollback();
            return false;
        }finally{
            session.close();
        }
        System.out.println("添加账单成功");
        return true;
    }
    public Bill deleteBill(int id) {
        SessionFactory sf=SessionFactoryUtil.getSessionFactory();
        Session session=sf.openSession();
        Bill bill=(Bill)session.get(Bill.class, id);
        Transaction ts= session.beginTransaction();
        session.delete(bill);
        try{
            ts.commit();
        }catch(Exception e){
            e.printStackTrace();
            ts.rollback();
            return null;
        }finally{
            session.close();
        }
        System.out.print("删除账单信息成功");
        return bill;
    }
    public Bill updateBill(Bill bill) {
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        Session session = sf.openSession();
        Transaction ts = session.beginTransaction();
        session.update(bill);
        try {
            ts.commit();
        } catch (Exception e) {
            e.printStackTrace();
            ts.rollback();
            return null;
        } finally {
            session.close();
        }
        System.out.print("修改账单信息成功");
        return bill;
    }
    public Bill queryOne(int id){
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        Session session = sf.openSession();
        Bill bill=session.get(Bill.class, id);
        System.out.println("查询单独账单成功");
        return bill;
    }
}
