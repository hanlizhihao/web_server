package com.hlz.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator 2017-2-16
 */
@Entity
@Table(name = "sell_analyze", catalog = "order", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SellAnalyze.findAll", query = "SELECT s FROM SellAnalyze s")
    , @NamedQuery(name = "SellAnalyze.findById", query = "SELECT s FROM SellAnalyze s WHERE s.id = :id")
    , @NamedQuery(name = "SellAnalyze.findByName", query = "SELECT s FROM SellAnalyze s WHERE s.name = :name")
    , @NamedQuery(name = "SellAnalyze.findByPrice", query = "SELECT s FROM SellAnalyze s WHERE s.price = :price")
    , @NamedQuery(name = "SellAnalyze.findByNumber", query = "SELECT s FROM SellAnalyze s WHERE s.number = :number")})
public class SellAnalyze implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    //if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private Double price;
    @Column(name = "number")
    private Integer number;

    public SellAnalyze() {
    }

    public SellAnalyze(Integer id) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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
        if (!(object instanceof SellAnalyze)) {
            return false;
        }
        SellAnalyze other = (SellAnalyze) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.SellAnalyze[ id=" + id + " ]";
    }

}
