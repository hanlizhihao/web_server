package com.hlz.webModel;

/**
 *用于接受web客户端的对象数组
 * @author Administrator 2017-3-19
 */
public class OrderModel {
    private String id;
    private String table;
    private String reminderNumber;
    private String price;
    private String time;
    private String[] name;//菜名
    private String[] count;
    private String[] number;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getReminderNumber() {
        return reminderNumber;
    }

    public void setReminderNumber(String reminderNumber) {
        this.reminderNumber = reminderNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String[] getName() {
        return name;
    }

    public void setName(String[] name) {
        this.name = name;
    }

    public String[] getCount() {
        return count;
    }

    public void setCount(String[] count) {
        this.count = count;
    }

    public String[] getNumber() {
        return number;
    }

    public void setNumber(String[] number) {
        this.number = number;
    }
    
}
