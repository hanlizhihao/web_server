package com.hlz.dao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * 单例模式获取SessionFacory
 * @author Administrator 2017-1-5
 */
public class SessionFactoryUtil {
    private volatile static SessionFactory instance=null;//volatitle防止出现线程安全问题
    public static SessionFactory getSessionFactory(){
        if(instance==null){
            synchronized(SessionFactoryUtil.class){
                if(instance==null){
                    Configuration configuration = new Configuration();
                    configuration.configure("hibernate.cfg.xml");
                    //在这里添加实体类
                    configuration.addAnnotatedClass(com.hlz.entity.Indent.class);
                    configuration.addAnnotatedClass(com.hlz.entity.Menu.class);
                    configuration.addAnnotatedClass(com.hlz.entity.SellAnalyze.class);
                    configuration.addAnnotatedClass(com.hlz.entity.Sign.class);
                    configuration.addAnnotatedClass(com.hlz.entity.Users.class);
                    configuration.addAnnotatedClass(com.hlz.entity.Vip.class);
                    configuration.addAnnotatedClass(com.hlz.entity.WorkTime.class);
                    configuration.addAnnotatedClass(com.hlz.entity.Bill.class);
                    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
                    instance = configuration.buildSessionFactory(serviceRegistry);
                }
            }
        }
        return instance;
    }
    private SessionFactoryUtil(){
        
    }
}
