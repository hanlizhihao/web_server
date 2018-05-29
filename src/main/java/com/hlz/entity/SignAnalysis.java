package com.hlz.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Administrator
 * @create 2018/5/24
 */

@Entity
@Table(name = "sign_analysis", catalog = "order", schema = "")
@XmlRootElement
public class SignAnalysis implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "time")
    private Timestamp time;
    @Column(name = "sign_in_time")
    private Timestamp signInTime;
    @Column(name = "sign_out_time")
    private Timestamp signOutTime;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private Users userId;
    @Column(name = "work_time_id")
    private Integer workTimeId;

    public Integer getWorkTimeId() {
        return workTimeId;
    }

    public void setWorkTimeId(Integer workTimeId) {
        this.workTimeId = workTimeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignAnalysis that = (SignAnalysis) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(time, that.time) &&
                Objects.equals(signInTime, that.signInTime) &&
                Objects.equals(signOutTime, that.signOutTime) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, time, signInTime, signOutTime, userId);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Timestamp getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(Timestamp signInTime) {
        this.signInTime = signInTime;
    }

    public Timestamp getSignOutTime() {
        return signOutTime;
    }

    public void setSignOutTime(Timestamp signOutTime) {
        this.signOutTime = signOutTime;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }
}
