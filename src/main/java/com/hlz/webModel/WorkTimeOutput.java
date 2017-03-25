package com.hlz.webModel;

import java.sql.Date;

/**
 *
 * @author Administrator 2017-3-25
 */
public class WorkTimeOutput {
    private Date oprationTime;
    private String continueTime;

    public Date getOprationTime() {
        return oprationTime;
    }

    public void setOprationTime(Date oprationTime) {
        this.oprationTime = oprationTime;
    }

    public String getContinueTime() {
        return continueTime;
    }

    public void setContinueTime(String continueTime) {
        this.continueTime = continueTime;
    }
    
}
