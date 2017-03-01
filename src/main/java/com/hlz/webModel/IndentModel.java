package com.hlz.webModel;

import java.util.ArrayList;
import java.util.Map;

/**
 *用于添加餐桌，修改餐桌信息，向客户端传送信息
 * 缺少更改style，用新的model属性去接受更改model状态
 * @author Administrator 2017-2-24
 */
public class IndentModel {
    private String table;//桌号
    private Map<String,Integer> reserve;//订菜
    private Map<String,Integer> fulfill;//上菜
    private int remiderNumber;//催单次数
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

    public Map<String,Integer> getReserve() {
        return reserve;
    }

    public void setReserve(Map<String,Integer> reserve) {
        this.reserve = reserve;
    }

    public Map<String,Integer> getFulfill() {
        return fulfill;
    }

    public void setFulfill(Map<String,Integer> fulfill) {
        this.fulfill = fulfill;
    }

    public int getRemiderNumber() {
        return remiderNumber;
    }

    public void setRemiderNumber(int remiderNumber) {
        this.remiderNumber = remiderNumber;
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
