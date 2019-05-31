package com.bjut.ssh.entity;

import javax.persistence.*;

/**
 * @Title: Lspzk1
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/11 10:01
 * @Version: 1.0
 */
@Entity
public class Lspzk1 {
    private int pzk1AutoId;
    private String itemNo;
    private String inputDate;
    private String voucherNo;
    private String entryNo;
    private Integer acsDocCnt;
    private String voucherType;
    private String summary;
    private String bkpDirection;
    private Double money;
    private Double qty;
    private String preActDoc;
    private String preActNo;
    private String auditor;
    private String auditorNo;
    private String directorName;
    private Byte auditSign;
    private String companyNo;
    private String userNo;
    private Double fWb;
    private String fJzxm;
    private Byte fHz;
    private Byte fJz;
    private Byte fDz;
    private String fTzbh;
    private Integer fCczs;
    private String fJwdw;
    private Double fQtwb;
    private String fDewb;

    @Id
    @Column(name = "PZK1_AutoId")
    public int getPzk1AutoId() {
        return pzk1AutoId;
    }

    public void setPzk1AutoId(int pzk1AutoId) {
        this.pzk1AutoId = pzk1AutoId;
    }

    @Basic
    @Column(name = "ItemNo")
    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    @Basic
    @Column(name = "InputDate")
    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
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
    @Column(name = "EntryNo")
    public String getEntryNo() {
        return entryNo;
    }

    public void setEntryNo(String entryNo) {
        this.entryNo = entryNo;
    }

    @Basic
    @Column(name = "AcsDocCnt")
    public Integer getAcsDocCnt() {
        return acsDocCnt;
    }

    public void setAcsDocCnt(Integer acsDocCnt) {
        this.acsDocCnt = acsDocCnt;
    }

    @Basic
    @Column(name = "VoucherType")
    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    @Basic
    @Column(name = "Summary")
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Basic
    @Column(name = "BkpDirection")
    public String getBkpDirection() {
        return bkpDirection;
    }

    public void setBkpDirection(String bkpDirection) {
        this.bkpDirection = bkpDirection;
    }

    @Basic
    @Column(name = "Money")
    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Basic
    @Column(name = "Qty")
    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    @Basic
    @Column(name = "PreActDoc")
    public String getPreActDoc() {
        return preActDoc;
    }

    public void setPreActDoc(String preActDoc) {
        this.preActDoc = preActDoc;
    }

    @Basic
    @Column(name = "PreActNo")
    public String getPreActNo() {
        return preActNo;
    }

    public void setPreActNo(String preActNo) {
        this.preActNo = preActNo;
    }

    @Basic
    @Column(name = "Auditor")
    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    @Basic
    @Column(name = "AuditorNo")
    public String getAuditorNo() {
        return auditorNo;
    }

    public void setAuditorNo(String auditorNo) {
        this.auditorNo = auditorNo;
    }

    @Basic
    @Column(name = "DirectorName")
    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    @Basic
    @Column(name = "AuditSign")
    public Byte getAuditSign() {
        return auditSign;
    }

    public void setAuditSign(Byte auditSign) {
        this.auditSign = auditSign;
    }

    @Basic
    @Column(name = "CompanyNo")
    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
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
    @Column(name = "F_WB")
    public Double getfWb() {
        return fWb;
    }

    public void setfWb(Double fWb) {
        this.fWb = fWb;
    }

    @Basic
    @Column(name = "F_JZXM")
    public String getfJzxm() {
        return fJzxm;
    }

    public void setfJzxm(String fJzxm) {
        this.fJzxm = fJzxm;
    }

    @Basic
    @Column(name = "F_HZ")
    public Byte getfHz() {
        return fHz;
    }

    public void setfHz(Byte fHz) {
        this.fHz = fHz;
    }

    @Basic
    @Column(name = "F_JZ")
    public Byte getfJz() {
        return fJz;
    }

    public void setfJz(Byte fJz) {
        this.fJz = fJz;
    }

    @Basic
    @Column(name = "F_DZ")
    public Byte getfDz() {
        return fDz;
    }

    public void setfDz(Byte fDz) {
        this.fDz = fDz;
    }

    @Basic
    @Column(name = "F_TZBH")
    public String getfTzbh() {
        return fTzbh;
    }

    public void setfTzbh(String fTzbh) {
        this.fTzbh = fTzbh;
    }

    @Basic
    @Column(name = "F_CCZS")
    public Integer getfCczs() {
        return fCczs;
    }

    public void setfCczs(Integer fCczs) {
        this.fCczs = fCczs;
    }

    @Basic
    @Column(name = "F_JWDW")
    public String getfJwdw() {
        return fJwdw;
    }

    public void setfJwdw(String fJwdw) {
        this.fJwdw = fJwdw;
    }

    @Basic
    @Column(name = "F_QTWB")
    public Double getfQtwb() {
        return fQtwb;
    }

    public void setfQtwb(Double fQtwb) {
        this.fQtwb = fQtwb;
    }

    @Basic
    @Column(name = "F_DEWB")
    public String getfDewb() {
        return fDewb;
    }

    public void setfDewb(String fDewb) {
        this.fDewb = fDewb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lspzk1 lspzk1 = (Lspzk1) o;

        if (pzk1AutoId != lspzk1.pzk1AutoId) return false;
        if (itemNo != null ? !itemNo.equals(lspzk1.itemNo) : lspzk1.itemNo != null) return false;
        if (inputDate != null ? !inputDate.equals(lspzk1.inputDate) : lspzk1.inputDate != null) return false;
        if (voucherNo != null ? !voucherNo.equals(lspzk1.voucherNo) : lspzk1.voucherNo != null) return false;
        if (entryNo != null ? !entryNo.equals(lspzk1.entryNo) : lspzk1.entryNo != null) return false;
        if (acsDocCnt != null ? !acsDocCnt.equals(lspzk1.acsDocCnt) : lspzk1.acsDocCnt != null) return false;
        if (voucherType != null ? !voucherType.equals(lspzk1.voucherType) : lspzk1.voucherType != null) return false;
        if (summary != null ? !summary.equals(lspzk1.summary) : lspzk1.summary != null) return false;
        if (bkpDirection != null ? !bkpDirection.equals(lspzk1.bkpDirection) : lspzk1.bkpDirection != null)
            return false;
        if (money != null ? !money.equals(lspzk1.money) : lspzk1.money != null) return false;
        if (qty != null ? !qty.equals(lspzk1.qty) : lspzk1.qty != null) return false;
        if (preActDoc != null ? !preActDoc.equals(lspzk1.preActDoc) : lspzk1.preActDoc != null) return false;
        if (preActNo != null ? !preActNo.equals(lspzk1.preActNo) : lspzk1.preActNo != null) return false;
        if (auditor != null ? !auditor.equals(lspzk1.auditor) : lspzk1.auditor != null) return false;
        if (auditorNo != null ? !auditorNo.equals(lspzk1.auditorNo) : lspzk1.auditorNo != null) return false;
        if (directorName != null ? !directorName.equals(lspzk1.directorName) : lspzk1.directorName != null)
            return false;
        if (auditSign != null ? !auditSign.equals(lspzk1.auditSign) : lspzk1.auditSign != null) return false;
        if (companyNo != null ? !companyNo.equals(lspzk1.companyNo) : lspzk1.companyNo != null) return false;
        if (userNo != null ? !userNo.equals(lspzk1.userNo) : lspzk1.userNo != null) return false;
        if (fWb != null ? !fWb.equals(lspzk1.fWb) : lspzk1.fWb != null) return false;
        if (fJzxm != null ? !fJzxm.equals(lspzk1.fJzxm) : lspzk1.fJzxm != null) return false;
        if (fHz != null ? !fHz.equals(lspzk1.fHz) : lspzk1.fHz != null) return false;
        if (fJz != null ? !fJz.equals(lspzk1.fJz) : lspzk1.fJz != null) return false;
        if (fDz != null ? !fDz.equals(lspzk1.fDz) : lspzk1.fDz != null) return false;
        if (fTzbh != null ? !fTzbh.equals(lspzk1.fTzbh) : lspzk1.fTzbh != null) return false;
        if (fCczs != null ? !fCczs.equals(lspzk1.fCczs) : lspzk1.fCczs != null) return false;
        if (fJwdw != null ? !fJwdw.equals(lspzk1.fJwdw) : lspzk1.fJwdw != null) return false;
        if (fQtwb != null ? !fQtwb.equals(lspzk1.fQtwb) : lspzk1.fQtwb != null) return false;
        if (fDewb != null ? !fDewb.equals(lspzk1.fDewb) : lspzk1.fDewb != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pzk1AutoId;
        result = 31 * result + (itemNo != null ? itemNo.hashCode() : 0);
        result = 31 * result + (inputDate != null ? inputDate.hashCode() : 0);
        result = 31 * result + (voucherNo != null ? voucherNo.hashCode() : 0);
        result = 31 * result + (entryNo != null ? entryNo.hashCode() : 0);
        result = 31 * result + (acsDocCnt != null ? acsDocCnt.hashCode() : 0);
        result = 31 * result + (voucherType != null ? voucherType.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (bkpDirection != null ? bkpDirection.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        result = 31 * result + (qty != null ? qty.hashCode() : 0);
        result = 31 * result + (preActDoc != null ? preActDoc.hashCode() : 0);
        result = 31 * result + (preActNo != null ? preActNo.hashCode() : 0);
        result = 31 * result + (auditor != null ? auditor.hashCode() : 0);
        result = 31 * result + (auditorNo != null ? auditorNo.hashCode() : 0);
        result = 31 * result + (directorName != null ? directorName.hashCode() : 0);
        result = 31 * result + (auditSign != null ? auditSign.hashCode() : 0);
        result = 31 * result + (companyNo != null ? companyNo.hashCode() : 0);
        result = 31 * result + (userNo != null ? userNo.hashCode() : 0);
        result = 31 * result + (fWb != null ? fWb.hashCode() : 0);
        result = 31 * result + (fJzxm != null ? fJzxm.hashCode() : 0);
        result = 31 * result + (fHz != null ? fHz.hashCode() : 0);
        result = 31 * result + (fJz != null ? fJz.hashCode() : 0);
        result = 31 * result + (fDz != null ? fDz.hashCode() : 0);
        result = 31 * result + (fTzbh != null ? fTzbh.hashCode() : 0);
        result = 31 * result + (fCczs != null ? fCczs.hashCode() : 0);
        result = 31 * result + (fJwdw != null ? fJwdw.hashCode() : 0);
        result = 31 * result + (fQtwb != null ? fQtwb.hashCode() : 0);
        result = 31 * result + (fDewb != null ? fDewb.hashCode() : 0);
        return result;
    }
}
