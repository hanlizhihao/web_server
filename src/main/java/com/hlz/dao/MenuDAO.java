package com.hlz.dao;

import com.hlz.interf.MenuRepository;
import com.hlz.webModel.MenuModel;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import com.hlz.entity.*;
import java.util.List;
import org.hibernate.Transaction;

/**
 *对菜单更改时，也要对sell_analyze进行更改
 * @author Administrator 2017-2-28
 */
@Repository
public class MenuDAO implements MenuRepository{
    //分为两种情况，一种是第一次添加，初始化版本
    //另一种是非第一次添加，更改版本号
    @Override
    public boolean addMenu(MenuModel model) {
        int row=count();
        SessionFactory sf=SessionFactoryUtil.getSessionFactory();
        Session session=sf.openSession();
        Transaction t=session.beginTransaction();
        if(row==0){//菜单第一条数据需要进行初始化
            Menu menu=new Menu();
            SellAnalyze sell=new SellAnalyze();//用于对初始化对销售进行分析
            sell.setName(model.getName());
            sell.setNumber(0);
            sell.setPrice(0.0);
            menu.setDbVersion("1");
            menu.setGreensName(model.getName());
            menu.setPrice(model.getPrice());
            try{
                session.save(menu);
                session.save(sell);
                t.commit();// 提交事务
                System.out.print("成功添加一条菜单信息");
                return true;
            }catch(Exception e){
                e.printStackTrace();
                t.rollback();
                return false;
            }finally{
                session.close();
            }
        }else{
            Menu menu=session.get(Menu.class,1);
            SellAnalyze sell = new SellAnalyze();//用于初始化销售分析
            sell.setName(model.getName());
            sell.setNumber(0);
            sell.setPrice(0.0);
            int version=Integer.valueOf(menu.getDbVersion());
            version=version+1;
            menu.setDbVersion(Integer.toString(version));
            Menu newMenu=new Menu();
            newMenu.setDbVersion("");
            newMenu.setGreensName(model.getName());
            newMenu.setPrice(model.getPrice());
            try{
                session.save(newMenu);
                session.update(menu);
                session.save(sell);
                t.commit();
                return true;
            }catch(Exception e){
                e.printStackTrace();
                t.rollback();
                return false;
            }finally{
                session.close();
            }
        }
    }

    @Override
    public int count() {
        SessionFactory sf=SessionFactoryUtil.getSessionFactory();
        int rows;
        try (Session session = sf.openSession()) {
            String hql = "select count(*) from Menu";//查询总行数
            Query query = (Query) session.createQuery(hql);
            rows = Integer.valueOf(query.getSingleResult().toString()); //查询总行数
            System.out.println("菜单菜品总数为：" + rows);
        } //查询总行数
        return rows;
    }

    @Override
    public boolean updateMenu(MenuModel model) {
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        Session session = sf.openSession();
        Transaction ts = session.beginTransaction();
        Menu menu=(Menu)session.get(Menu.class,model.getId());
        //用于更新销售分析
        SellAnalyze sell=(SellAnalyze)session.get(SellAnalyze.class,model.getId());
        sell.setName(model.getName());
        sell.setPrice(model.getPrice());
        menu.setGreensName(model.getName());
        menu.setPrice(model.getPrice());
        //更改版本号
        Menu temp = session.get(Menu.class, 1);
        int version = Integer.valueOf(temp.getDbVersion());
        version = version + 1;
        temp.setDbVersion(Integer.toString(version));
        try{
            session.update(sell);
            session.update(menu);
            session.update(temp);
            ts.commit();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            ts.rollback();
            return false;
        }finally{
            session.close();
        }
    }

    @Override
    public boolean deleteMenu(int id) {
        SessionFactory sf=SessionFactoryUtil.getSessionFactory();
        Session session=sf.openSession();
        Menu a=(Menu)session.get(Menu.class,id);
        //用于删除销售分析中的数据
        SellAnalyze sell=(SellAnalyze)session.get(SellAnalyze.class,id);
        Transaction ts= session.beginTransaction();
        session.delete(a);
        session.delete(sell);
        Menu temp = session.get(Menu.class, 1);
        int version = Integer.valueOf(temp.getDbVersion());
        version = version + 1;
        temp.setDbVersion(Integer.toString(version));
        session.update(temp);
        try{
            ts.commit();
        }catch(Exception e){
            e.printStackTrace();
            ts.rollback();
            return false;
        }finally{
            session.close();
        }
        System.out.print("删除菜单信息成功");
        return true;
    }
    @Override
    public List<Menu> queryMenu() {
        SessionFactory sf=SessionFactoryUtil.getSessionFactory();
        Session session=sf.openSession();
        String hql="from Menu order by id";
        Query query=session.createQuery(hql);
        List<Menu> result=query.getResultList();
        System.out.println("查询菜单成功");
        session.close();
        return result;
    }

    @Override
    public String getVersion() {
        SessionFactory sf=SessionFactoryUtil.getSessionFactory();
        Session session=sf.openSession();
        Menu menu=session.get(Menu.class,1);
        return menu.getDbVersion();
    }
}
