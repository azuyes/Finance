package com.bjut.ssh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Title: Lshsfl
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/11 10:00
 * @Version: 1.0
 */
@Entity
public class Lshsfl {
    private String catNo;
    private String catName;

    @Id
    @Column(name = "CatNo")
    public String getCatNo() {
        return catNo;
    }

    public void setCatNo(String catNo) {
        this.catNo = catNo;
    }

    @Basic
    @Column(name = "CatName")
    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lshsfl lshsfl = (Lshsfl) o;

        if (catNo != null ? !catNo.equals(lshsfl.catNo) : lshsfl.catNo != null) return false;
        if (catName != null ? !catName.equals(lshsfl.catName) : lshsfl.catName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = catNo != null ? catNo.hashCode() : 0;
        result = 31 * result + (catName != null ? catName.hashCode() : 0);
        return result;
    }
}
