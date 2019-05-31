package com.bjut.ssh.entity;

import javax.persistence.Entity;

/**
 * @Title: Lspzk1VoForSearch
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/7/13 10:14
 * @Version: 1.0
 */
public class Lspzk1VoForSearch {
    private Lspzk1 lspzk1;
    private Lsyspz lsyspz;
    private Lshspz lshspz;
    private Double money;
    private Double qty;
    private Double moneyD;
    private Double moneyJ;
    private Double qtyD ;
    private Double qtyJ;

    public Lspzk1 getLspzk1() {
        return lspzk1;
    }

    public void setLspzk1(Lspzk1 lspzk1) {
        this.lspzk1 = lspzk1;
    }

    public Lshspz getLshspz() {
        return lshspz;
    }

    public void setLshspz(Lshspz lshspz) {
        this.lshspz = lshspz;
    }

    public Lsyspz getLsyspz() {
        return lsyspz;
    }

    public void setLsyspz(Lsyspz lsyspz) {
        this.lsyspz = lsyspz;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getMoneyD() {
        return moneyD;
    }

    public void setMoneyD(Double moneyD) {
        this.moneyD = moneyD;
    }

    public Double getMoneyJ() {
        return moneyJ;
    }

    public void setMoneyJ(Double moneyJ) {
        this.moneyJ = moneyJ;
    }

    public Double getQtyD() {
        return qtyD;
    }

    public void setQtyD(Double qtyD) {
        this.qtyD = qtyD;
    }

    public Double getQtyJ() {
        return qtyJ;
    }

    public void setQtyJ(Double qtyJ) {
        this.qtyJ = qtyJ;
    }
}

