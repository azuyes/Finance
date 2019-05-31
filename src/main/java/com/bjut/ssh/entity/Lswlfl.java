package com.bjut.ssh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Title: Lswlfl
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/8/3 13:02
 * @Version: 1.0
 */
@Entity
public class Lswlfl {
    private String catNo1;
    private String catName1;
    private String catLevel;
    private String finLevel;

    @Id
    @Column(name = "CatNo1")
    public String getCatNo1() {
        return catNo1;
    }

    public void setCatNo1(String catNo1) {
        this.catNo1 = catNo1;
    }

    @Basic
    @Column(name = "CatName1")
    public String getCatName1() {
        return catName1;
    }

    public void setCatName1(String catName1) {
        this.catName1 = catName1;
    }

    @Basic
    @Column(name = "CatLevel")
    public String getCatLevel() {
        return catLevel;
    }

    public void setCatLevel(String catLevel) {
        this.catLevel = catLevel;
    }

    @Basic
    @Column(name = "FinLevel")
    public String getFinLevel() {
        return finLevel;
    }

    public void setFinLevel(String finLevel) {
        this.finLevel = finLevel;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lswlfl lswlfl = (Lswlfl) o;

        if (catNo1 != null ? !catNo1.equals(lswlfl.catNo1) : lswlfl.catNo1 != null) return false;
        if (catName1 != null ? !catName1.equals(lswlfl.catName1) : lswlfl.catName1 != null) return false;
        if (catLevel != null ? !catLevel.equals(lswlfl.catLevel) : lswlfl.catLevel != null) return false;
        if (finLevel != null ? !finLevel.equals(lswlfl.finLevel) : lswlfl.finLevel != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = catNo1 != null ? catNo1.hashCode() : 0;
        result = 31 * result + (catName1 != null ? catName1.hashCode() : 0);
        result = 31 * result + (catLevel != null ? catLevel.hashCode() : 0);
        result = 31 * result + (finLevel != null ? finLevel.hashCode() : 0);
        return result;
    }
}
