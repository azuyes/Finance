package com.bjut.ssh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Title: Lsgnbh
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/23 10:35
 * @Version: 1.0
 */
@Entity
public class Lsgnbh {
    private String menuNo;
    private String menuName;
    private String subSysNo;
    private String fFpct;
    private String tid;
    private String state;

    @Id
    @Column(name = "MenuNo")
    public String getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(String menuNo) {
        this.menuNo = menuNo;
    }

    @Basic
    @Column(name = "MenuName")
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Basic
    @Column(name = "SubSysNo")
    public String getSubSysNo() {
        return subSysNo;
    }

    public void setSubSysNo(String subSysNo) {
        this.subSysNo = subSysNo;
    }

    @Basic
    @Column(name = "F_FPCT")
    public String getfFpct() {
        return fFpct;
    }

    public void setfFpct(String fFpct) {
        this.fFpct = fFpct;
    }

    @Basic
    @Column(name = "Tid")
    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    @Basic
    @Column(name = "State")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lsgnbh lsgnbh = (Lsgnbh) o;

        if (menuNo != null ? !menuNo.equals(lsgnbh.menuNo) : lsgnbh.menuNo != null) return false;
        if (menuName != null ? !menuName.equals(lsgnbh.menuName) : lsgnbh.menuName != null) return false;
        if (subSysNo != null ? !subSysNo.equals(lsgnbh.subSysNo) : lsgnbh.subSysNo != null) return false;
        if (fFpct != null ? !fFpct.equals(lsgnbh.fFpct) : lsgnbh.fFpct != null) return false;
        if (tid != null ? !tid.equals(lsgnbh.tid) : lsgnbh.tid != null) return false;
        if (state != null ? !state.equals(lsgnbh.state) : lsgnbh.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = menuNo != null ? menuNo.hashCode() : 0;
        result = 31 * result + (menuName != null ? menuName.hashCode() : 0);
        result = 31 * result + (subSysNo != null ? subSysNo.hashCode() : 0);
        result = 31 * result + (fFpct != null ? fFpct.hashCode() : 0);
        result = 31 * result + (tid != null ? tid.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }
}
