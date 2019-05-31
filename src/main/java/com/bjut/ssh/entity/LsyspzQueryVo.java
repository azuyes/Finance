package com.bjut.ssh.entity;

import javax.persistence.Entity;

/**
 * @Title: LsyspzQueryVo
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/7/24 14:37
 * @Version: 1.0
 */
public class LsyspzQueryVo {
    private Lsyspz lsyspz;
    private String oppoItem;
    private Double money;

    public void setLsyspz(Lsyspz lsyspz) {
        this.lsyspz = lsyspz;
    }

    public Lsyspz getLsyspz() {
        return lsyspz;
    }

    public void setOppoItem(String oppoItem) {
        this.oppoItem = oppoItem;
    }

    public String getOppoItem() {
        return oppoItem;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getMoney() {
        return money;
    }
}
