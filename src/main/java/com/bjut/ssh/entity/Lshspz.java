package com.bjut.ssh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Title: Lshspz
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/11 10:00
 * @Version: 1.0
 */
@Entity
public class Lshspz {
    private int hspzAutoId;
    private String inputDate;
    private String voucherNo;
    private String entryNo;
    private String itemNo;
    private String spNo1;
    private String spNo2;
    private String bkpDirection;
    private Double money;
    private Double qty;
    private Double fWb;

    @Id
    @Column(name = "HSPZ_AutoId")
    public int getHspzAutoId() {
        return hspzAutoId;
    }

    public void setHspzAutoId(int hspzAutoId) {
        this.hspzAutoId = hspzAutoId;
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
    @Column(name = "ItemNo")
    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    @Basic
    @Column(name = "SpNo1")
    public String getSpNo1() {
        return spNo1;
    }

    public void setSpNo1(String spNo1) {
        this.spNo1 = spNo1;
    }

    @Basic
    @Column(name = "SpNo2")
    public String getSpNo2() {
        return spNo2;
    }

    public void setSpNo2(String spNo2) {
        this.spNo2 = spNo2;
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
    @Column(name = "F_WB")
    public Double getfWb() {
        return fWb;
    }

    public void setfWb(Double fWb) {
        this.fWb = fWb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lshspz lshspz = (Lshspz) o;

        if (hspzAutoId != lshspz.hspzAutoId) return false;
        if (inputDate != null ? !inputDate.equals(lshspz.inputDate) : lshspz.inputDate != null) return false;
        if (voucherNo != null ? !voucherNo.equals(lshspz.voucherNo) : lshspz.voucherNo != null) return false;
        if (entryNo != null ? !entryNo.equals(lshspz.entryNo) : lshspz.entryNo != null) return false;
        if (itemNo != null ? !itemNo.equals(lshspz.itemNo) : lshspz.itemNo != null) return false;
        if (spNo1 != null ? !spNo1.equals(lshspz.spNo1) : lshspz.spNo1 != null) return false;
        if (spNo2 != null ? !spNo2.equals(lshspz.spNo2) : lshspz.spNo2 != null) return false;
        if (bkpDirection != null ? !bkpDirection.equals(lshspz.bkpDirection) : lshspz.bkpDirection != null)
            return false;
        if (money != null ? !money.equals(lshspz.money) : lshspz.money != null) return false;
        if (qty != null ? !qty.equals(lshspz.qty) : lshspz.qty != null) return false;
        if (fWb != null ? !fWb.equals(lshspz.fWb) : lshspz.fWb != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = hspzAutoId;
        result = 31 * result + (inputDate != null ? inputDate.hashCode() : 0);
        result = 31 * result + (voucherNo != null ? voucherNo.hashCode() : 0);
        result = 31 * result + (entryNo != null ? entryNo.hashCode() : 0);
        result = 31 * result + (itemNo != null ? itemNo.hashCode() : 0);
        result = 31 * result + (spNo1 != null ? spNo1.hashCode() : 0);
        result = 31 * result + (spNo2 != null ? spNo2.hashCode() : 0);
        result = 31 * result + (bkpDirection != null ? bkpDirection.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        result = 31 * result + (qty != null ? qty.hashCode() : 0);
        result = 31 * result + (fWb != null ? fWb.hashCode() : 0);
        return result;
    }
}
