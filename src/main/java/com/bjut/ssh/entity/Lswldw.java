package com.bjut.ssh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Title: Lswldw
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/11 10:01
 * @Version: 1.0
 */
@Entity
public class Lswldw {
    private String companyNo;
    private String catNo1;
    private String companyName;
    private String cont;
    private String tel;
    private String companyAddr;
    private String companyPost;
    private String bank;
    private String account;
    private String creditStanding;
    private String taxIdNo;
    private String memo;
    private String fWbbh;
    private String fBzlb;
    private String fBzbm;

    @Id
    @Column(name = "CompanyNo")
    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    @Basic
    @Column(name = "CatNo1")
    public String getCatNo1() {
        return catNo1;
    }

    public void setCatNo1(String catNo1) {
        this.catNo1 = catNo1;
    }

    @Basic
    @Column(name = "CompanyName")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Basic
    @Column(name = "Cont")
    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    @Basic
    @Column(name = "Tel")
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Basic
    @Column(name = "CompanyAddr")
    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    @Basic
    @Column(name = "CompanyPost")
    public String getCompanyPost() {
        return companyPost;
    }

    public void setCompanyPost(String companyPost) {
        this.companyPost = companyPost;
    }

    @Basic
    @Column(name = "Bank")
    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    @Basic
    @Column(name = "Account")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Basic
    @Column(name = "CreditStanding")
    public String getCreditStanding() {
        return creditStanding;
    }

    public void setCreditStanding(String creditStanding) {
        this.creditStanding = creditStanding;
    }

    @Basic
    @Column(name = "TaxIdNo")
    public String getTaxIdNo() {
        return taxIdNo;
    }

    public void setTaxIdNo(String taxIdNo) {
        this.taxIdNo = taxIdNo;
    }

    @Basic
    @Column(name = "Memo")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Basic
    @Column(name = "F_WBBH")
    public String getfWbbh() {
        return fWbbh;
    }

    public void setfWbbh(String fWbbh) {
        this.fWbbh = fWbbh;
    }

    @Basic
    @Column(name = "F_BZLB")
    public String getfBzlb() {
        return fBzlb;
    }

    public void setfBzlb(String fBzlb) {
        this.fBzlb = fBzlb;
    }

    @Basic
    @Column(name = "F_BZBM")
    public String getfBzbm() {
        return fBzbm;
    }

    public void setfBzbm(String fBzbm) {
        this.fBzbm = fBzbm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lswldw lswldw = (Lswldw) o;

        if (companyNo != null ? !companyNo.equals(lswldw.companyNo) : lswldw.companyNo != null) return false;
        if (catNo1 != null ? !catNo1.equals(lswldw.catNo1) : lswldw.catNo1 != null) return false;
        if (companyName != null ? !companyName.equals(lswldw.companyName) : lswldw.companyName != null) return false;
        if (cont != null ? !cont.equals(lswldw.cont) : lswldw.cont != null) return false;
        if (tel != null ? !tel.equals(lswldw.tel) : lswldw.tel != null) return false;
        if (companyAddr != null ? !companyAddr.equals(lswldw.companyAddr) : lswldw.companyAddr != null) return false;
        if (companyPost != null ? !companyPost.equals(lswldw.companyPost) : lswldw.companyPost != null) return false;
        if (bank != null ? !bank.equals(lswldw.bank) : lswldw.bank != null) return false;
        if (account != null ? !account.equals(lswldw.account) : lswldw.account != null) return false;
        if (creditStanding != null ? !creditStanding.equals(lswldw.creditStanding) : lswldw.creditStanding != null)
            return false;
        if (taxIdNo != null ? !taxIdNo.equals(lswldw.taxIdNo) : lswldw.taxIdNo != null) return false;
        if (memo != null ? !memo.equals(lswldw.memo) : lswldw.memo != null) return false;
        if (fWbbh != null ? !fWbbh.equals(lswldw.fWbbh) : lswldw.fWbbh != null) return false;
        if (fBzlb != null ? !fBzlb.equals(lswldw.fBzlb) : lswldw.fBzlb != null) return false;
        if (fBzbm != null ? !fBzbm.equals(lswldw.fBzbm) : lswldw.fBzbm != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = companyNo != null ? companyNo.hashCode() : 0;
        result = 31 * result + (catNo1 != null ? catNo1.hashCode() : 0);
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (cont != null ? cont.hashCode() : 0);
        result = 31 * result + (tel != null ? tel.hashCode() : 0);
        result = 31 * result + (companyAddr != null ? companyAddr.hashCode() : 0);
        result = 31 * result + (companyPost != null ? companyPost.hashCode() : 0);
        result = 31 * result + (bank != null ? bank.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (creditStanding != null ? creditStanding.hashCode() : 0);
        result = 31 * result + (taxIdNo != null ? taxIdNo.hashCode() : 0);
        result = 31 * result + (memo != null ? memo.hashCode() : 0);
        result = 31 * result + (fWbbh != null ? fWbbh.hashCode() : 0);
        result = 31 * result + (fBzlb != null ? fBzlb.hashCode() : 0);
        result = 31 * result + (fBzbm != null ? fBzbm.hashCode() : 0);
        return result;
    }
}
