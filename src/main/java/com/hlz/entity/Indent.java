package com.hlz.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator 2017-2-16
 */
@Entity
@Table(name = "indent", catalog = "order", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Indent.findAll", query = "SELECT i FROM Indent i")
    , @NamedQuery(name = "Indent.findById", query = "SELECT i FROM Indent i WHERE i.id = :id")
    , @NamedQuery(name = "Indent.findByTableId", query = "SELECT i FROM Indent i WHERE i.tableId = :tableId")
    , @NamedQuery(name = "Indent.findByReserveNumber", query = "SELECT i FROM Indent i WHERE i.reserveNumber = :reserveNumber")
    , @NamedQuery(name = "Indent.findByFulfillNumber", query = "SELECT i FROM Indent i WHERE i.fulfillNumber = :fulfillNumber")
    , @NamedQuery(name = "Indent.findByReminderNumber", query = "SELECT i FROM Indent i WHERE i.reminderNumber = :reminderNumber")
    , @NamedQuery(name = "Indent.findByBeginTime", query = "SELECT i FROM Indent i WHERE i.beginTime = :beginTime")
    , @NamedQuery(name = "Indent.findByEndTime", query = "SELECT i FROM Indent i WHERE i.endTime = :endTime")
    , @NamedQuery(name = "Indent.findByFirstTime", query = "SELECT i FROM Indent i WHERE i.firstTime = :firstTime")
    , @NamedQuery(name = "Indent.findByStyle", query = "SELECT i FROM Indent i WHERE i.style = :style")
    , @NamedQuery(name = "Indent.findByPrice", query = "SELECT i FROM Indent i WHERE i.price = :price")})
public class Indent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "table_id")
    private String tableId;
    @Lob
    @Column(name = "reserve")
    private String reserve;
    @Column(name = "reserve_number")
    private Integer reserveNumber;
    @Lob
    @Column(name = "fulfill")
    private String fulfill;
    @Column(name = "fulfill_number")
    private Integer fulfillNumber;
    @Column(name = "reminder_number")
    private Integer reminderNumber;
    @Column(name = "begin_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date beginTime;
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @Column(name = "first_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firstTime;
    @Column(name = "style")
    private Integer style;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private Double price;

    public Indent() {
    }

    public Indent(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }

    public Integer getReserveNumber() {
        return reserveNumber;
    }

    public void setReserveNumber(Integer reserveNumber) {
        this.reserveNumber = reserveNumber;
    }

    public String getFulfill() {
        return fulfill;
    }

    public void setFulfill(String fulfill) {
        this.fulfill = fulfill;
    }

    public Integer getFulfillNumber() {
        return fulfillNumber;
    }

    public void setFulfillNumber(Integer fulfillNumber) {
        this.fulfillNumber = fulfillNumber;
    }

    public Integer getReminderNumber() {
        return reminderNumber;
    }

    public void setReminderNumber(Integer reminderNumber) {
        this.reminderNumber = reminderNumber;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Date firstTime) {
        this.firstTime = firstTime;
    }

    public Integer getStyle() {
        return style;
    }

    public void setStyle(Integer style) {
        this.style = style;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Indent)) {
            return false;
        }
        Indent other = (Indent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.Indent[ id=" + id + " ]";
    }

}
