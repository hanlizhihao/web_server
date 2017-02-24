package com.hlz.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @Temporal(TemporalType.DATE)
    private Date oprationTime;
    @Column(name = "continue_time")
    private String continueTime;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private Users userId;

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

    public Date getOprationTime() {
        return oprationTime;
    }

    public void setOprationTime(Date oprationTime) {
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
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "dao.WorkTime[ id=" + id + " ]";
    }

}
