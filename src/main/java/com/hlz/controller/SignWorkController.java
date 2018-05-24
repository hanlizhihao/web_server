package com.hlz.controller;

import com.hlz.entity.Sign;
import com.hlz.entity.WorkTime;
import com.hlz.service.SignAndWorkService;
import com.hlz.util.SignEnum;
import com.hlz.util.TimeUtil;
import com.hlz.webModel.SignOutput;
import com.hlz.webModel.WorkModel;
import com.hlz.webModel.WorkTimeOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 *只有添加签到和工作，没有更新和删除
 * @author Administrator 2017-3-7
 */
@RestController
public class SignWorkController {
    @Autowired
    private SignAndWorkService service;
    /**
     *
     * @param id user的id
     * @return 成功失败
     */
    @RequestMapping(value="/addsign/{id}",produces="text/plain;charset=UTF-8",method=RequestMethod.GET)
    public String addSign(@PathVariable String id){
        boolean sign=service.addSign(Integer.valueOf(id));
        if(sign){
            return "success";
        }else{
            return "defeat";
        }
    }
    @RequestMapping(value = "/signOut/{id}", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    public String signOut(@PathVariable String id) {
        if (service.signOut(Integer.valueOf(id))) {
            return "success";
        }else {
            return "defeat";
        }
    }
    @RequestMapping(value="/addwork",produces="text/plain;charset=UTF-8",method=RequestMethod.POST)
    public String addWork(WorkModel model){
        boolean sign=service.addWork(model);
        if(sign){
            return "success";
        }else{
            return "defeat";
        }
    }
    @RequestMapping(value="/signs/{id}",produces="application/json;charset=UTF-8",method=RequestMethod.GET)
    public ArrayList<SignOutput> getSigns(@PathVariable String id){
        List<Sign> signs=service.findSignOnUserId(Integer.valueOf(id));
        signs.sort(Comparator.comparing(Sign::getSignTime));
        /**
         * 将签到数据转化为 日期：签到时间，签退时间 格式
         */
        ArrayList<SignOutput> result=new ArrayList<>();
        Instant mouthBegin = TimeUtil.getMouthBegin();
        Instant now = Instant.now();
        do {
            Stream<Sign> signsStream = signs.parallelStream().filter(sign -> {
                Instant signTime = sign.getSignTime().toInstant();
                return mouthBegin.isBefore(signTime) && mouthBegin.plus(Duration.ofDays(1)).isAfter(signTime);
            });
            SignOutput signOutput = new SignOutput();
            if (signsStream.count() != 0) {
                signOutput.setTime(Date.from(mouthBegin));
                signsStream.forEach(sign -> {
                    if (SignEnum.SIGN_IN.getValue().equals(sign.getType())) {
                        signOutput.setSignInTime(sign.getSignTime());
                    } else if (SignEnum.SIGN_OUT.getValue().equals(sign.getType())) {
                        signOutput.setSignOutTime(sign.getSignTime());
                    }
                    signOutput.setNull(false);
                });
            } else {
                signOutput.setTime(Date.from(mouthBegin));
                signOutput.setNull(true);
            }
            mouthBegin.plus(Duration.ofDays(1));
        } while (!now.isBefore(mouthBegin));
        return result;
    }

    /**
     * 月累计签到次数
     * @param id userId
     * @return 签到数量
     */
    @RequestMapping(value = "sign/number/{id}", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    public String getSignNumber(@PathVariable String id) {
        List<Sign> signs = service.findSignOnUserId(Integer.valueOf(id));
        return Integer.toString(signs.size());
    }
    @RequestMapping(value="/works/{id}",produces="application/json;charset=UTF-8",method=RequestMethod.GET)
    public ArrayList<WorkTimeOutput> getWorks(@PathVariable String id){
        List<WorkTime> works=service.findWorkTimeOnUserID(Integer.valueOf(id));
        ArrayList<WorkTimeOutput> result=new ArrayList<>();
        for(WorkTime w:works){
            WorkTimeOutput work=new WorkTimeOutput();
            work.setContinueTime(w.getContinueTime());
            work.setOprationTime(new java.sql.Date(w.getOprationTime().getTime()));
            result.add(work);
        }
        return result;
    }
}
