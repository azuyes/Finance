package com.bjut.ssh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Title: Lsyspz
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/11 10:02
 * @Version: 1.0
 */
@Entity
public class Lsyspz {
    private int yspzAutoId;
    private String itemNo;
    private String inputDate;
    private String voucherNo;
    private String entryNo;
    private String summary;
    private String bkpDirection;
    private Double money;
    private Double qty;
    private Double unitPrice;
    private Double fWb;
    private Double fQt;
    private Double fHl;
    private Double fQtwb;
    private Double fQthl;
    private String originalNo;

    @Id
    @Column(name = "YSPZ_AutoId", nullable = false)
    public int getYspzAutoId() {
        return yspzAutoId;
    }

    public void setYspzAutoId(int yspzAutoId) {
        this.yspzAutoId = yspzAutoId;
    }

    @Basic
    @Column(name = "ItemNo", nullable = true, length = 30)
    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    @Basic
    @Column(name = "InputDate", nullable = true, length = 8)
    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    @Basic
    @Column(name = "VoucherNo", nullable = true, length = 5)
    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    @Basic
    @Column(name = "EntryNo", nullable = true, length = 4)
    public String getEntryNo() {
        return entryNo;
    }

    public void setEntryNo(String entryNo) {
        this.entryNo = entryNo;
    }

    @Basic
    @Column(name = "Summary", nullable = true, length = 60)
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Basic
    @Column(name = "BkpDirection", nullable = true, length = 1)
    public String getBkpDirection() {
        return bkpDirection;
    }

    public void setBkpDirection(String bkpDirection) {
        this.bkpDirection = bkpDirection;
    }

    @Basic
    @Column(name = "Money", nullable = true, precision = 0)
    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Basic
    @Column(name = "Qty", nullable = true, precision = 0)
    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    @Basic
    @Column(name = "UnitPrice", nullable = true, precision = 0)
    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Basic
    @Column(name = "F_WB", nullable = true, precision = 0)
    public Double getfWb() {
        return fWb;
    }

    public void setfWb(Double fWb) {
        this.fWb = fWb;
    }

    @Basic
    @Column(name = "F_QT", nullable = true, precision = 0)
    public Double getfQt() {
        return fQt;
    }

    public void setfQt(Double fQt) {
        this.fQt = fQt;
    }

    @Basic
    @Column(name = "F_HL", nullable = true, precision = 0)
    public Double getfHl() {
        return fHl;
    }

    public void setfHl(Double fHl) {
        this.fHl = fHl;
    }

    @Basic
    @Column(name = "F_QTWB", nullable = true, precision = 0)
    public Double getfQtwb() {
        return fQtwb;
    }

    public void setfQtwb(Double fQtwb) {
        this.fQtwb = fQtwb;
    }

    @Basic
    @Column(name = "F_QTHL", nullable = true, precision = 0)
    public Double getfQthl() {
        return fQthl;
    }

    public void setfQthl(Double fQthl) {
        this.fQthl = fQthl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lsyspz lsyspz = (Lsyspz) o;

        if (yspzAutoId != lsyspz.yspzAutoId) return false;
        if (itemNo != null ? !itemNo.equals(lsyspz.itemNo) : lsyspz.itemNo != null) return false;
        if (inputDate != null ? !inputDate.equals(lsyspz.inputDate) : lsyspz.inputDate != null) return false;
        if (voucherNo != null ? !voucherNo.equals(lsyspz.voucherNo) : lsyspz.voucherNo != null) return false;
        if (entryNo != null ? !entryNo.equals(lsyspz.entryNo) : lsyspz.entryNo != null) return false;
        if (summary != null ? !summary.equals(lsyspz.summary) : lsyspz.summary != null) return false;
        if (bkpDirection != null ? !bkpDirection.equals(lsyspz.bkpDirection) : lsyspz.bkpDirection != null)
            return false;
        if (money != null ? !money.equals(lsyspz.money) : lsyspz.money != null) return false;
        if (qty != null ? !qty.equals(lsyspz.qty) : lsyspz.qty != null) return false;
        if (unitPrice != null ? !unitPrice.equals(lsyspz.unitPrice) : lsyspz.unitPrice != null) return false;
        if (fWb != null ? !fWb.equals(lsyspz.fWb) : lsyspz.fWb != null) return false;
        if (fQt != null ? !fQt.equals(lsyspz.fQt) : lsyspz.fQt != null) return false;
        if (fHl != null ? !fHl.equals(lsyspz.fHl) : lsyspz.fHl != null) return false;
        if (fQtwb != null ? !fQtwb.equals(lsyspz.fQtwb) : lsyspz.fQtwb != null) return false;
        if (fQthl != null ? !fQthl.equals(lsyspz.fQthl) : lsyspz.fQthl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = yspzAutoId;
        result = 31 * result + (itemNo != null ? itemNo.hashCode() : 0);
        result = 31 * result + (inputDate != null ? inputDate.hashCode() : 0);
        result = 31 * result + (voucherNo != null ? voucherNo.hashCode() : 0);
        result = 31 * result + (entryNo != null ? entryNo.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (bkpDirection != null ? bkpDirection.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        result = 31 * result + (qty != null ? qty.hashCode() : 0);
        result = 31 * result + (unitPrice != null ? unitPrice.hashCode() : 0);
        result = 31 * result + (fWb != null ? fWb.hashCode() : 0);
        result = 31 * result + (fQt != null ? fQt.hashCode() : 0);
        result = 31 * result + (fHl != null ? fHl.hashCode() : 0);
        result = 31 * result + (fQtwb != null ? fQtwb.hashCode() : 0);
        result = 31 * result + (fQthl != null ? fQthl.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "OriginalNo", nullable = true, length = 3)
    public String getOriginalNo() {
        return originalNo;
    }

    public void setOriginalNo(String originalNo) {
        this.originalNo = originalNo;
    }
}
