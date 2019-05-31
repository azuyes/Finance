package com.bjut.ssh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Title: Lsusgn
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/11 10:01
 * @Version: 1.0
 */
@Entity
public class Lsusgn {
    private int usgnAutoId;
    private String userNo;
    private String menuNo;

    @Id
    @Column(name = "USGN_AutoId")
    public int getUsgnAutoId() {
        return usgnAutoId;
    }

    public void setUsgnAutoId(int usgnAutoId) {
        this.usgnAutoId = usgnAutoId;
    }

    @Basic
    @Column(name = "UserNo")
    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @Basic
    @Column(name = "MenuNo")
    public String getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(String menuNo) {
        this.menuNo = menuNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lsusgn lsusgn = (Lsusgn) o;

        if (usgnAutoId != lsusgn.usgnAutoId) return false;
        if (userNo != null ? !userNo.equals(lsusgn.userNo) : lsusgn.userNo != null) return false;
        if (menuNo != null ? !menuNo.equals(lsusgn.menuNo) : lsusgn.menuNo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = usgnAutoId;
        result = 31 * result + (userNo != null ? userNo.hashCode() : 0);
        result = 31 * result + (menuNo != null ? menuNo.hashCode() : 0);
        return result;
    }
}
