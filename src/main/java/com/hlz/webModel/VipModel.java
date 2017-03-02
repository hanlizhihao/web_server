package com.hlz.webModel;

/**
 *
 * @author Administrator 2017-3-2
 */
public class VipModel {
    private String name;
    private String phoneNumber;
    private int consumNumber;
    private double totalConsum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getConsumNumber() {
        return consumNumber;
    }

    public void setConsumNumber(int consumNumber) {
        this.consumNumber = consumNumber;
    }

    public double getTotalConsum() {
        return totalConsum;
    }

    public void setTotalConsum(double totalConsum) {
        this.totalConsum = totalConsum;
    }
    
}
