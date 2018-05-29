package com.hlz.webModel;

import java.sql.Timestamp;

/**
 * @author Administrator
 * @create 2018/5/29
 */
public class SignModel {

    private Timestamp time;

    private Timestamp signInTime;

    private Timestamp signOutTime;

    private Integer overTimeNumber;

    private String continueTime;

    private Integer workTimeId;

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Timestamp getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(Timestamp signInTime) {
        this.signInTime = signInTime;
    }

    public Timestamp getSignOutTime() {
        return signOutTime;
    }

    public void setSignOutTime(Timestamp signOutTime) {
        this.signOutTime = signOutTime;
    }

    public Integer getOverTimeNumber() {
        return overTimeNumber;
    }

    public void setOverTimeNumber(Integer overTimeNumber) {
        this.overTimeNumber = overTimeNumber;
    }

    public String getContinueTime() {
        return continueTime;
    }

    public void setContinueTime(String continueTime) {
        this.continueTime = continueTime;
    }

    public Integer getWorkTimeId() {
        return workTimeId;
    }

    public void setWorkTimeId(Integer workTimeId) {
        this.workTimeId = workTimeId;
    }
}
