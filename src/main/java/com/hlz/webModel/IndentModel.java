package com.hlz.webModel;

import java.util.Map;

/**
 *用于添加餐桌，修改餐桌信息，向客户端传送信息
 * 缺少更改style，用新的model属性去接受更改model状态
 * @author Administrator 2017-2-24
 */
public class IndentModel {
    private String table;//桌号
    private Map<String,String> reserve;//订菜
    private Map<String,String> fulfill;//上菜
    private int reminderNumber;//催单次数
    private double price;
    private long time;//第一次上菜时间
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Map<String,String> getReserve() {
        return reserve;
    }

    public void setReserve(Map<String,String> reserve) {
        this.reserve = reserve;
    }

    public Map<String,String> getFulfill() {
        return fulfill;
    }

    public void setFulfill(Map<String,String> fulfill) {
        this.fulfill = fulfill;
    }

    public int getRemiderNumber() {
        return reminderNumber;
    }

    public void setRemiderNumber(int reminderNumber) {
        this.reminderNumber = reminderNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    } 
}
