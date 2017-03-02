package com.hlz.webModel;

/**
 *用于记录工作时间的model
 * @author Administrator 2017-3-1
 */
public class WorkModel {
    private int id;//用户id
    private String time;//设备运行时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
}
