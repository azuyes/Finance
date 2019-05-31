package com.bjut.ssh.entity;

import java.util.List;

/**
 * @Title: LshszdQueryVo
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/5/7 13:15
 * @Version: 1.0
 */
public class LshszdQueryVo {
    private Lshsfl lshsfl;
    private List<Lshszd> lshszds;

    public Lshsfl getLshsfl() {
        return lshsfl;
    }

    public void setLshsfl(Lshsfl lshsfl) {
        this.lshsfl = lshsfl;
    }

    public List<Lshszd> getLshszds() {
        return lshszds;
    }

    public void setLshszds(List<Lshszd> lshszds) {
        this.lshszds = lshszds;
    }
}
