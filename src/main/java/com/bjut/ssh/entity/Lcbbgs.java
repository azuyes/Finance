package com.bjut.ssh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Arrays;

/**
 * @Title: Lcbbgs
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/6/5 10:30
 * @Version: 1.0
 */
@Entity
public class Lcbbgs {
    private int bbgsAutoId;
    private byte[] bbzdGs;
    private Integer bbzdXh;
    private String rptDate;
    private String rptNo;

    @Id
    @Column(name = "BBGS_AutoId")
    public int getBbgsAutoId() {
        return bbgsAutoId;
    }

    public void setBbgsAutoId(int bbgsAutoId) {
        this.bbgsAutoId = bbgsAutoId;
    }

    @Basic
    @Column(name = "BBZD_GS")
    public byte[] getBbzdGs() {
        return bbzdGs;
    }

    public void setBbzdGs(byte[] bbzdGs) {
        this.bbzdGs = bbzdGs;
    }

    @Basic
    @Column(name = "BBZD_XH")
    public Integer getBbzdXh() {
        return bbzdXh;
    }

    public void setBbzdXh(Integer bbzdXh) {
        this.bbzdXh = bbzdXh;
    }

    @Basic
    @Column(name = "RptDate")
    public String getRptDate() {
        return rptDate;
    }

    public void setRptDate(String rptDate) {
        this.rptDate = rptDate;
    }

    @Basic
    @Column(name = "RptNo")
    public String getRptNo() {
        return rptNo;
    }

    public void setRptNo(String rptNo) {
        this.rptNo = rptNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lcbbgs lcbbgs = (Lcbbgs) o;

        if (bbgsAutoId != lcbbgs.bbgsAutoId) return false;
        if (!Arrays.equals(bbzdGs, lcbbgs.bbzdGs)) return false;
        if (bbzdXh != null ? !bbzdXh.equals(lcbbgs.bbzdXh) : lcbbgs.bbzdXh != null) return false;
        if (rptDate != null ? !rptDate.equals(lcbbgs.rptDate) : lcbbgs.rptDate != null) return false;
        if (rptNo != null ? !rptNo.equals(lcbbgs.rptNo) : lcbbgs.rptNo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bbgsAutoId;
        result = 31 * result + Arrays.hashCode(bbzdGs);
        result = 31 * result + (bbzdXh != null ? bbzdXh.hashCode() : 0);
        result = 31 * result + (rptDate != null ? rptDate.hashCode() : 0);
        result = 31 * result + (rptNo != null ? rptNo.hashCode() : 0);
        return result;
    }
}
