package com.hlz.service;

import com.hlz.dao.SignAndWorkDAO;
import com.hlz.dao.UserDAO;
import com.hlz.entity.Sign;
import com.hlz.entity.Users;
import com.hlz.entity.WorkTime;
import com.hlz.webModel.WorkModel;
import java.util.ArrayList;
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
    public boolean addWork(WorkModel model){
        return dao.addWork(model);
    }
    //允许为空
    public ArrayList<Sign> findSignOnUserId(int id){
        Users user=userdao.querySingleUsers(id);
        ArrayList<Sign> result=new ArrayList<>();
        result.addAll(user.getSignCollection());
        return result;
    }
    //对于工作时间的问题，目前只是单纯的返回静态的数据到前端，不会动态更改
    public ArrayList<WorkTime> findWorkTimeOnUserID(int id){
        Users user=userdao.querySingleUsers(id);
        ArrayList<WorkTime> result=new ArrayList<>();
        result.addAll(user.getWorkTimeCollection());
        return result;
    }
}
