package com.hlz.service;

import com.hlz.dao.SignAndWorkDAO;
import com.hlz.dao.UserDAO;
import com.hlz.entity.Sign;
import com.hlz.entity.SignAnalysis;
import com.hlz.entity.WorkTime;
import com.hlz.webModel.WorkModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *签到与工作时间只可以添加，暂时不支持删除
 * @author Administrator 2017-3-7
 */
@Service
public class SignAndWorkService {
    @Autowired
    private SignAndWorkDAO dao;
    @Autowired
    private UserDAO userdao;
    //id为签到用户的id
    public boolean addSign(int id){
        return dao.addSign(id);
    }
    public boolean signOut(int id) {
        return dao.signOut(id);
    }
    public boolean addWork(WorkModel model){
        return dao.addWork(model);
    }
    //允许为空
    public List<Sign> findSignOnUserId(int id){
        List<Sign> signs=userdao.getSignByUserID(id);
        return signs;
    }
    // 对于工作时间的问题，目前只是单纯的返回静态的数据到前端，不会动态更改
    public List<WorkTime> findWorkTimeOnUserID(int id){
        List<WorkTime> works=userdao.getWorkTimeByUserID(id);
        return works;
    }

    public List<SignAnalysis> getSignAnalysisByUserId(int id) {
        return dao.getSignAnalysis(id);
    }
}
