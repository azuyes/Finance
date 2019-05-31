package com.bjut.ssh.entity;

/**
 * @Title: LshspzQueryVo
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/5/24 11:17
 * @Version: 1.0
 */
public class LshspzQueryVo {
    private String inputDate;
    private String voucherNo;
    private String entryNo;
    private String itemNo;
    private String bkpDirection;
    private String spNo1;
    private String spName1;
    private String spNo2;
    private String spName2;
    private String upperName1;
    private String upperName2;
    private Double money;
    private Double qty;

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getEntryNo() {
        return entryNo;
    }

    public void setEntryNo(String entryNo) {
        this.entryNo = entryNo;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getBkpDirection() {
        return bkpDirection;
    }

    public void setBkpDirection(String bkpDirection) {
        this.bkpDirection = bkpDirection;
    }

    public void setSpNo1(String spNo1) {
        this.spNo1 = spNo1;
    }

    public String getSpNo1() {
        return spNo1;
    }

    public void setSpNo2(String spNo2) {
        this.spNo2 = spNo2;
    }

    public String getSpNo2() {
        return spNo2;
    }

    public void setSpName1(String spName1) {
        this.spName1 = spName1;
    }

    public void setSpName2(String spName2) {
        this.spName2 = spName2;
    }

    public String getUpperName1() {
        return upperName1;
    }

    public void setUpperName1(String upperName1) {
        this.upperName1 = upperName1;
    }

    public String getUpperName2() {
        return upperName2;
    }

    public void setUpperName2(String upperName2) {
        this.upperName2 = upperName2;
    }

    public String getSpName1() {
        return spName1;
    }

    public String getSpName2() {
        return spName2;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getMoney() {
        return money;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getQty() {
        return qty;
    }
}
