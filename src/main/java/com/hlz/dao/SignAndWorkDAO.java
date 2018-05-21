package com.hlz.dao;

import com.hlz.entity.Sign;
import com.hlz.entity.Users;
import com.hlz.entity.WorkTime;
import com.hlz.interf.SignAndWorkRepository;
import com.hlz.webModel.WorkModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

/**
 *添加签到信息和工作时间的信息是特殊的
 * 对于Service，它只需要调用add函数，由函数自己判断，是调用update还是直接返回true(对于签到)
 * 这种机制，对于重启服务器的情况，将不在适用，因为缓存数据时存储在内存中
 * @author Administrator 2017-3-1
 */
@Repository
public class SignAndWorkDAO implements SignAndWorkRepository{
    private final Set<Integer> userSign;
    private final Set<Integer> userWork;
    public SignAndWorkDAO(){
        this.userSign=new HashSet();
        this.userWork=new HashSet();
    }
    @Override
    public boolean addSign(int id) {//id是user的id
        SessionFactory sf=SessionFactoryUtil.getSessionFactory();
        Session session=sf.openSession();
        if(userSign.contains(id)){
            session.close();
            return true;
        }
        Users user=(Users)session.get(Users.class,id);
        Sign sign=new Sign();
        sign.setUserId(user);
        sign.setSignTime(new java.sql.Timestamp(System.currentTimeMillis()));
        Transaction t= session.beginTransaction();
        try{
            session.save(sign);
            session.saveOrUpdate(user);
            t.commit();
            System.out.println("成功添加一个签到信息");
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
    /**
     * model中只带有user的id，故通过user的id查找出本次要修改的工作时间，这是通
     * 过比对user的所有工作时间的操作时间是否是今天来判断的
     * @param model 需要更新的WorkModel
     * @return 更新是否成功
     * 这个方法能返回正常结果的前提是一个人的工作时间的操作时间每天只有一个
     */
    @Override
    public boolean updateWork(WorkModel model) {
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        Session session = sf.openSession();
        UserDAO dao=new UserDAO();
        List<WorkTime> works =dao.getWorkTimeByUserID(model.getId());
        Transaction t = session.beginTransaction();
        for (WorkTime work : works) {
            //判断workTime的记录时间是不是当天
            if (work.getOprationTime().compareTo(new java.sql.Date(
                    System.currentTimeMillis())) == 0) {
                work.setContinueTime(model.getTime());//将最新的工作时间更新给
                //这个workTime
                session.update(work);
                break;
            }
        }
        try {
            t.commit();
            System.out.println("更新工作信息成功");
        } catch (Exception e) {
            e.printStackTrace();
            t.rollback();
            return false;
        } finally {
            session.close();
        }
        return true;
    }
}
