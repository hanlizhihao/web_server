package com.hlz.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Administrator
 * @create 2018/5/24
 */
@Entity
@Table(name = "app_leave_time", catalog = "order", schema = "")
@XmlRootElement
public class AppLeaveTime implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "leave_end_time")
    private Timestamp leaveEndTime;
    @Column(name = "leave_begin_time")
    private Timestamp leaveBeginTime;
    @Size(max = 100)
    @Column(name = "leave_time_duration")
    private String leaveTimeDuration;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private Users userId;

    @JoinColumn(name = "work_time_id", referencedColumnName = "id")
    @ManyToOne
    private WorkTime workTimeId;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppLeaveTime that = (AppLeaveTime) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(leaveEndTime, that.leaveEndTime) &&
                Objects.equals(leaveBeginTime, that.leaveBeginTime) &&
                Objects.equals(leaveTimeDuration, that.leaveTimeDuration) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(workTimeId, that.workTimeId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, leaveEndTime, leaveBeginTime, leaveTimeDuration, userId, workTimeId);
    }

    public static long getSerialVersionUID() {

        return serialVersionUID;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getLeaveTimeDuration() {
        return leaveTimeDuration;
    }

    public void setLeaveTimeDuration(String leaveTimeDuration) {
        this.leaveTimeDuration = leaveTimeDuration;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public Timestamp getLeaveEndTime() {
        return leaveEndTime;
    }

    public void setLeaveEndTime(Timestamp leaveEndTime) {
        this.leaveEndTime = leaveEndTime;
    }

    public Timestamp getLeaveBeginTime() {
        return leaveBeginTime;
    }

    public void setLeaveBeginTime(Timestamp leaveBeginTime) {
        this.leaveBeginTime = leaveBeginTime;
    }

    public WorkTime getWorkTimeId() {
        return workTimeId;
    }

    public void setWorkTimeId(WorkTime workTimeId) {
        this.workTimeId = workTimeId;
    }

}
