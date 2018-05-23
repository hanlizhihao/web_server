package com.hlz.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator 2017-3-25
 */
@Entity
@Table(name = "sign", catalog = "order", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sign.findAll", query = "SELECT s FROM Sign s")
    , @NamedQuery(name = "Sign.findById", query = "SELECT s FROM Sign s WHERE s.id = :id")
    , @NamedQuery(name = "Sign.findBySignTime", query = "SELECT s FROM Sign s WHERE s.signTime = :signTime")
    , @NamedQuery(name = "Sign.findByType", query = "SELECT s FROM Sign s WHERE s.type = :type")})
public class Sign implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "sign_time")
    private Timestamp signTime;
    @Column(name = "type")
    private Integer type;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private Users userId;

    public Sign() {
    }

    public Sign(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getSignTime() {
        return signTime;
    }

    public void setSignTime(Timestamp signTime) {
        this.signTime = signTime;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sign sign = (Sign) o;
        return Objects.equals(id, sign.id) &&
                Objects.equals(signTime, sign.signTime) &&
                Objects.equals(type, sign.type) &&
                Objects.equals(userId, sign.userId);
    }

    @Override
    public String toString() {
        return "Sign{" +
                "id=" + id +
                ", signTime=" + signTime +
                ", type=" + type +
                ", userId=" + userId +
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
