package com.hlz.webModel;

import java.sql.Date;

/**
 *
 * @author Administrator 2017-3-2
 */
public class BillModel {
    private String name;
    private Double price;
    private String comment;
    private Date occurrenceTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getOccurrenceTime() {
        return occurrenceTime;
    }

    public void setOccurrenceTime(Date occurrenceTime) {
        this.occurrenceTime = occurrenceTime;
    }
    
}
