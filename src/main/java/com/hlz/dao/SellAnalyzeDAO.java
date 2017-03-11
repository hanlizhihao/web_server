package com.hlz.dao;

import com.hlz.entity.Indent;
import com.hlz.interf.SellAnalyzeRepository;
import com.hlz.webModel.IndentStyle;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import com.hlz.entity.*;
import java.util.ArrayList;
import javax.persistence.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Administrator 2017-2-28
 */
@Repository
public class SellAnalyzeDAO implements SellAnalyzeRepository {

    @Override
    public Indent updateSellAnalyze(IndentStyle style, Indent indent) {
        //只处理将要完成的订单
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        Session session = sf.openSession();
        String reserves = indent.getReserve();
        //除去最后一个e
        String reserves1 = reserves.substring(0, reserves.length() - 1);
        //获取成对的数据
        String[] reserve = reserves1.split("e");
        //根据name去更改指定的统计分析
        //首先应该获取完整菜单
        String hql = "from Menu order by id";
        List<Menu> result;
        Query query = (Query) session.createQuery(hql);
        result = query.getResultList();
        Transaction t = session.beginTransaction();
        for (String a : reserve) {
            String[] name = a.split("a");
            for (Menu m : result) {
                if (name[0].equals(m.getGreensName()))//说明查到了名字相同的
                {
                    int number = Integer.valueOf(name[1]);
                    int id = m.getId();
                    SellAnalyze sell = (SellAnalyze) session.get(SellAnalyze.class, id);
                    sell.setNumber(sell.getNumber() + number);
                    sell.setPrice(sell.getPrice() + m.getPrice() * number);
                    session.save(sell);
                }
            }
        }
        try {
            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
            t.rollback();
            return null;
        } finally {
            session.close();
        }
        return indent;
    }

    @Override
    public ArrayList<SellAnalyze> queryAllSellAnalyze() {
        String hql = "from SellAnalyze order by number";
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        Session session = sf.openSession();
        Query query=session.createQuery(hql);
        List<SellAnalyze> result=query.getResultList();
        ArrayList<SellAnalyze> results=new ArrayList<>();
        results.addAll(result);
        return results;
    }
}
