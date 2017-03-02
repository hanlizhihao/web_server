package com.hlz.entity;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator 2017-2-16
 */
@Entity
@Table(name = "vip", catalog = "order", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vip.findAll", query = "SELECT v FROM Vip v")
    , @NamedQuery(name = "Vip.findById", query = "SELECT v FROM Vip v WHERE v.id = :id")
    , @NamedQuery(name = "Vip.findByName", query = "SELECT v FROM Vip v WHERE v.name = :name")
    , @NamedQuery(name = "Vip.findByPhoneNumber", query = "SELECT v FROM Vip v WHERE v.phoneNumber = :phoneNumber")
    , @NamedQuery(name = "Vip.findByJoinTime", query = "SELECT v FROM Vip v WHERE v.joinTime = :joinTime")
    , @NamedQuery(name = "Vip.findByConsumNumber", query = "SELECT v FROM Vip v WHERE v.consumNumber = :consumNumber")
    , @NamedQuery(name = "Vip.findByTotalConsum", query = "SELECT v FROM Vip v WHERE v.totalConsum = :totalConsum")})
public class Vip implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "join_time")
    private Date joinTime;
    @Column(name = "consum_number")
    private Integer consumNumber;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total_consum")
    private Double totalConsum;

    public Vip() {
    }

    public Vip(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public Integer getConsumNumber() {
        return consumNumber;
    }

    public void setConsumNumber(Integer consumNumber) {
        this.consumNumber = consumNumber;
    }

    public Double getTotalConsum() {
        return totalConsum;
    }

    public void setTotalConsum(Double totalConsum) {
        this.totalConsum = totalConsum;
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
        if (!(object instanceof Vip)) {
            return false;
        }
        Vip other = (Vip) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.Vip[ id=" + id + " ]";
    }

}
