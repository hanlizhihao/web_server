package com.hlz.dao;

import com.hlz.entity.*;
import com.hlz.interf.SignAndWorkRepository;
import com.hlz.util.TimeUtil;
import com.hlz.webModel.WorkModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;


import javax.persistence.Query;
import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.List;

/**
 *添加签到信息和工作时间的信息是特殊的
 * 对于Service，它只需要调用add函数，由函数自己判断，是调用update还是直接返回true(对于签到)
 * 这种机制，对于重启服务器的情况，将不在适用，因为缓存数据时存储在内存中
 * @author Administrator 2017-3-1
 */
@Repository
public class SignAndWorkDAO implements SignAndWorkRepository{


    @Override
    public boolean addSign(int id) {//id是user的id
        SessionFactory sf=SessionFactoryUtil.getSessionFactory();
        Session session=sf.openSession();
        // 查询是否有当天签到记录
        String hql = "from Sign where signTime >= ? and signTime <= ? and type = 0";
        Query query = session.createQuery(hql);
        query.setParameter(0, getTodayBegin());
        query.setParameter(1, getTodayEnd());
        List signs = query.getResultList();
        if (CollectionUtils.isEmpty(signs)) {
            Users user= session.get(Users.class,id);
            Sign sign=new Sign();
            sign.setUserId(user);
            sign.setSignTime(new java.sql.Timestamp(System.currentTimeMillis()));
            // 签入
            sign.setType(0);
            Transaction t= session.beginTransaction();
            String signAnalysisHql = "from SignAnalysis where time >= ? and time <= ?";
            Query query1 = session.createQuery(signAnalysisHql);
            query1.setParameter(0, getTodayBegin());
            query1.setParameter(1, getTodayEnd());
            List<SignAnalysis> signAnalyses = query1.getResultList();
            if (CollectionUtils.isEmpty(signAnalyses)) {
                SignAnalysis  signAnalysis = new SignAnalysis();
                signAnalysis.setTime(new Timestamp(System.currentTimeMillis()));
                signAnalysis.setUserId(user);
                signAnalysis.setSignInTime(new Timestamp(System.currentTimeMillis()));
                session.save(signAnalysis);
            } else {
                SignAnalysis signAnalysis = signAnalyses.get(0);
                signAnalysis.setSignInTime(new Timestamp(System.currentTimeMillis()));
                session.update(signAnalysis);
            }
            return !executeSession(session, user, sign, t);
        }
        return true;
    }

    @Override
    public boolean addWork(WorkModel model) {
        SessionFactory sf = SessionFactoryUtil.getSessionFactory();
        Session session = sf.openSession();
        Users user=session.get(Users.class,model.getId());
        // 获取当天记录
        List<WorkTime> workTimeList = user.getWorkTimeList();
        Instant todayBegin = TimeUtil.getTodayBegin().toInstant();
        Instant todayEnd = TimeUtil.getTodayEnd().toInstant();
        WorkTime targetWorkTime = null;
        for (WorkTime workTime: workTimeList) {
            if (todayBegin.isBefore(workTime.getOprationTime().toInstant()) && todayEnd.isAfter(workTime.getOprationTime().toInstant())) {
                targetWorkTime = workTime;
                break;
            }
        }
        if (targetWorkTime != null) {
            targetWorkTime.setOverTimeNumber(targetWorkTime.getOverTimeNumber() + 1);
            targetWorkTime.setContinueTime(model.getTime());
            AppLeaveTime appLeaveTime = new AppLeaveTime();
            appLeaveTime.setUserId(user);
            appLeaveTime.setWorkTimeId(targetWorkTime);
            appLeaveTime.setLeaveBeginTime(new Timestamp(model.getLeaveBeginTime().getTime()));
            appLeaveTime.setLeaveEndTime(new Timestamp(model.getLeaveEndTime().getTime()));
            appLeaveTime.setLeaveTimeDuration(model.getDuration());
            Transaction transaction = session.beginTransaction();
            if (executeTransaction(session, user, targetWorkTime, appLeaveTime, transaction)) return false;
        } else {
            WorkTime work=new WorkTime();
            work.setContinueTime(model.getTime());
            work.setOprationTime(new java.sql.Timestamp(System.currentTimeMillis()));
            work.setOverTimeNumber(1);
            work.setUserId(user);
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
            }
            AppLeaveTime appLeaveTime = new AppLeaveTime();
            appLeaveTime.setUserId(user);
            appLeaveTime.setWorkTimeId(work);
            appLeaveTime.setLeaveBeginTime(new Timestamp(model.getLeaveBeginTime().getTime()));
            appLeaveTime.setLeaveEndTime(new Timestamp(model.getLeaveEndTime().getTime()));
            appLeaveTime.setLeaveTimeDuration(model.getDuration());
            Transaction transaction = session.beginTransaction();
            return !executeTransaction(session, user, targetWorkTime, appLeaveTime, transaction);
        }
        return true;
    }

    private boolean executeTransaction(Session session, Users user, WorkTime targetWorkTime, AppLeaveTime appLeaveTime, Transaction transaction) {
        try {
            session.save(appLeaveTime);
            session.saveOrUpdate(targetWorkTime);
            session.saveOrUpdate(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return true;
        } finally {
            session.close();
        }
        return false;
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

    @Override
    public boolean signOut(int id) {
        SessionFactory sf=SessionFactoryUtil.getSessionFactory();
        Session session=sf.openSession();
        // 查询是否有当天签到记录
        String hql = "from Sign where signTime >= ? and signTime <= ? and type = 1";
        Query query = session.createQuery(hql);
        query.setParameter(0, getTodayBegin());
        query.setParameter(1, getTodayEnd());
        List signs = query.getResultList();
        if (CollectionUtils.isEmpty(signs)) {
            Users user= session.get(Users.class,id);
            Sign sign=new Sign();
            sign.setUserId(user);
            sign.setSignTime(new java.sql.Timestamp(System.currentTimeMillis()));
            // 签出
            sign.setType(1);
            Transaction t= session.beginTransaction();
            String signAnalysisHql = "from SignAnalysis where time >= ? and time <= ?";
            Query query1 = session.createQuery(signAnalysisHql);
            query1.setParameter(0, getTodayBegin());
            query1.setParameter(1, getTodayEnd());
            List<SignAnalysis> signAnalyses = query1.getResultList();
            if (CollectionUtils.isEmpty(signAnalyses)) {
                SignAnalysis  signAnalysis = new SignAnalysis();
                signAnalysis.setTime(new Timestamp(System.currentTimeMillis()));
                signAnalysis.setUserId(user);
                signAnalysis.setSignOutTime(new Timestamp(System.currentTimeMillis()));
                session.save(signAnalysis);
            } else {
                SignAnalysis signAnalysis = signAnalyses.get(0);
                signAnalysis.setSignOutTime(new Timestamp(System.currentTimeMillis()));
                session.update(signAnalysis);
            }
            return !executeSession(session, user, sign, t);
        } else {
            Sign sign = (Sign)signs.get(signs.size() - 1);
            sign.setSignTime(new Timestamp(System.currentTimeMillis()));
            String signAnalysisHql = "from SignAnalysis where time >= ? and time <= ?";
            Query query1 = session.createQuery(signAnalysisHql);
            query1.setParameter(0, getTodayBegin());
            query1.setParameter(1, getTodayEnd());
            List<SignAnalysis> signAnalyses = query1.getResultList();
            SignAnalysis signAnalysis = signAnalyses.get(0);
            signAnalysis.setSignOutTime(new Timestamp(System.currentTimeMillis()));
            Transaction t = session.beginTransaction();
            session.update(sign);
            session.update(signAnalysis);
            try {
                t.commit();
            } catch (Exception e) {
                t.rollback();
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    @Override
    public List<SignAnalysis> getSignAnalysis(int id) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "from SignAnalysis where time >= ? order by time desc ";
        Query query = session.createQuery(hql);
        query.setParameter(0, Date.from(TimeUtil.getMouthBegin()));
        return (List<SignAnalysis>) query.getResultList();
    }

    private boolean executeSession(Session session, Users user, Sign sign, Transaction t) {
        try{
            session.save(sign);
            session.saveOrUpdate(user);
            t.commit();
            System.out.println("成功添加一个签到信息");
        }catch(Exception e){
            t.rollback();
            e.printStackTrace();
            return true;
        }finally{
            session.close();
        }
        return false;
    }

    public Date getTodayBegin() {
        int zeroToNowSecond = LocalTime.now().toSecondOfDay();
        Date todayBegin =  Date.from(Instant.now().minusSeconds(zeroToNowSecond));
        return todayBegin;
    }

    public Date getTodayEnd() {
        Instant instant = getTodayBegin().toInstant();
        instant = instant.plusSeconds(24*3600);
        return Date.from(instant);
    }

}
