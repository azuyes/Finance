package com.bjut.ssh.entity;

/**
 * @Title: LswljeQueryVo
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/23 10:18
 * @Version: 1.0
 */
public class LswljeQueryVo {
//    private Lswlje lswlje;
    private String companyNo;
    private String companyName;
    private String itemNo;

    private double supQty;
    private double debitQtySup;
    private double creditQtySup;
    private double leftQty;

    private double supMoney;
    private double debitMoneySup;
    private double creditMoneySup;
    private double balance;

    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public double getSupQty() {
        return supQty;
    }

    public void setSupQty(double supQty) {
        this.supQty = supQty;
    }

    public double getDebitQtySup() {
        return debitQtySup;
    }

    public void setDebitQtySup(double debitQtySup) {
        this.debitQtySup = debitQtySup;
    }

    public double getCreditQtySup() {
        return creditQtySup;
    }

    public void setCreditQtySup(double creditQtySup) {
        this.creditQtySup = creditQtySup;
    }

    public double getLeftQty() {
        return leftQty;
    }

    public void setLeftQty(double leftQty) {
        this.leftQty = leftQty;
    }

    public double getSupMoney() {
        return supMoney;
    }

    public void setSupMoney(double supMoney) {
        this.supMoney = supMoney;
    }

    public double getDebitMoneySup() {
        return debitMoneySup;
    }

    public void setDebitMoneySup(double debitMoneySup) {
        this.debitMoneySup = debitMoneySup;
    }

    public double getCreditMoneySup() {
        return creditMoneySup;
    }

    public void setCreditMoneySup(double creditMoneySup) {
        this.creditMoneySup = creditMoneySup;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
