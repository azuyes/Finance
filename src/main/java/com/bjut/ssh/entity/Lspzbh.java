package com.bjut.ssh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Title: Lspzbh
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/11 10:01
 * @Version: 1.0
 */
@Entity
public class Lspzbh {
    private int pzbhAutoId;
    private String voucherName;
    private String voucherFirstChar;
    private String voucherNo;
    private String iniDate;
    private String fPzgs;
    private String fPzys;

    @Id
    @Column(name = "PZBH_AutoId")
    public int getPzbhAutoId() {
        return pzbhAutoId;
    }

    public void setPzbhAutoId(int pzbhAutoId) {
        this.pzbhAutoId = pzbhAutoId;
    }

    @Basic
    @Column(name = "VoucherName")
    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    @Basic
    @Column(name = "VoucherFirstChar")
    public String getVoucherFirstChar() {
        return voucherFirstChar;
    }

    public void setVoucherFirstChar(String voucherFirstChar) {
        this.voucherFirstChar = voucherFirstChar;
    }

    @Basic
    @Column(name = "VoucherNo")
    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    @Basic
    @Column(name = "IniDate")
    public String getIniDate() {
        return iniDate;
    }

    public void setIniDate(String iniDate) {
        this.iniDate = iniDate;
    }

    @Basic
    @Column(name = "F_PZGS")
    public String getfPzgs() {
        return fPzgs;
    }

    public void setfPzgs(String fPzgs) {
        this.fPzgs = fPzgs;
    }

    @Basic
    @Column(name = "F_PZYS")
    public String getfPzys() {
        return fPzys;
    }

    public void setfPzys(String fPzys) {
        this.fPzys = fPzys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lspzbh lspzbh = (Lspzbh) o;

        if (pzbhAutoId != lspzbh.pzbhAutoId) return false;
        if (voucherName != null ? !voucherName.equals(lspzbh.voucherName) : lspzbh.voucherName != null) return false;
        if (voucherFirstChar != null ? !voucherFirstChar.equals(lspzbh.voucherFirstChar) : lspzbh.voucherFirstChar != null)
            return false;
        if (voucherNo != null ? !voucherNo.equals(lspzbh.voucherNo) : lspzbh.voucherNo != null) return false;
        if (iniDate != null ? !iniDate.equals(lspzbh.iniDate) : lspzbh.iniDate != null) return false;
        if (fPzgs != null ? !fPzgs.equals(lspzbh.fPzgs) : lspzbh.fPzgs != null) return false;
        if (fPzys != null ? !fPzys.equals(lspzbh.fPzys) : lspzbh.fPzys != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pzbhAutoId;
        result = 31 * result + (voucherName != null ? voucherName.hashCode() : 0);
        result = 31 * result + (voucherFirstChar != null ? voucherFirstChar.hashCode() : 0);
        result = 31 * result + (voucherNo != null ? voucherNo.hashCode() : 0);
        result = 31 * result + (iniDate != null ? iniDate.hashCode() : 0);
        result = 31 * result + (fPzgs != null ? fPzgs.hashCode() : 0);
        result = 31 * result + (fPzys != null ? fPzys.hashCode() : 0);
        return result;
    }
}
