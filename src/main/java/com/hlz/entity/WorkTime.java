package com.hlz.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator 2017-3-25
 */
@Entity
@Table(name = "work_time", catalog = "order", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WorkTime.findAll", query = "SELECT w FROM WorkTime w")
    , @NamedQuery(name = "WorkTime.findById", query = "SELECT w FROM WorkTime w WHERE w.id = :id")
    , @NamedQuery(name = "WorkTime.findByOprationTime", query = "SELECT w FROM WorkTime w WHERE w.oprationTime = :oprationTime")
    , @NamedQuery(name = "WorkTime.findByContinueTime", query = "SELECT w FROM WorkTime w WHERE w.continueTime = :continueTime")})
public class WorkTime implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "opration_time")
    private Timestamp oprationTime;
    @Column(name = "over_time_number")
    private Integer overTimeNumber;
    @Size(max = 255)
    @Column(name = "continue_time")
    private String continueTime;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private Users userId;

    @OneToMany(mappedBy = "workTimeId")
    private List<AppLeaveTime> appLeaveTimes;

    public List<AppLeaveTime> getAppLeaveTimes() {
        return appLeaveTimes;
    }

    public void setAppLeaveTimes(List<AppLeaveTime> appLeaveTimes) {
        this.appLeaveTimes = appLeaveTimes;
    }

    public Integer getOverTimeNumber() {
        return overTimeNumber;
    }

    public void setOverTimeNumber(Integer overTimeNumber) {
        this.overTimeNumber = overTimeNumber;
    }

    public WorkTime() {
    }

    public WorkTime(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getOprationTime() {
        return oprationTime;
    }

    public void setOprationTime(Timestamp oprationTime) {
        this.oprationTime = oprationTime;
    }

    public String getContinueTime() {
        return continueTime;
    }

    public void setContinueTime(String continueTime) {
        this.continueTime = continueTime;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
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
        if (!(object instanceof WorkTime)) {
            return false;
        }
        WorkTime other = (WorkTime) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hlz.entity.WorkTime[ id=" + id + " ]";
    }

}
