package com.hlz.webModel;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Administrator 2017-3-25
 */
public class SignOutput {
    /**
     * 签到信息的日期
     */
    private Date time;
    private Timestamp signOutTime;
    private Timestamp signInTime;
    private Boolean isNull;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Timestamp getSignOutTime() {
        return signOutTime;
    }

    public void setSignOutTime(Timestamp signOutTime) {
        this.signOutTime = signOutTime;
    }

    public Timestamp getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(Timestamp signInTime) {
        this.signInTime = signInTime;
    }

    public Boolean getNull() {
        return isNull;
    }

    public void setNull(Boolean aNull) {
        isNull = aNull;
    }
}
