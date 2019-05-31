package com.bjut.ssh.entity;

/**
 * @Title: LskmzdNLskmslQueryVo
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/7/3 18:10
 * @Version: 1.0
 */
public class LskmzdNLskmslQueryVo {
    private String itemNo;

    private Lskmzd lskmzd;

    private Lskmsl lskmsl;

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getItemNo() {
        return itemNo;
    }

    public Lskmsl getLskmsl() {
        return lskmsl;
    }

    public void setLskmsl(Lskmsl lskmsl) {
        this.lskmsl = lskmsl;
    }

    public Lskmzd getLskmzd() {
        return lskmzd;
    }

    public void setLskmzd(Lskmzd lskmzd) {
        this.lskmzd = lskmzd;
    }
}
