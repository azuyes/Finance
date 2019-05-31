package com.bjut.ssh.entity;

import java.util.List;

/**
 * @Title: LshsjeOrLshsslQueryVo
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/5/30 9:33
 * @Version: 1.0
 */
public class LshsjeOrLshsslForInit {

    private LskmzdVoForSpecial lskmzdVoForSpecial;

    private List<LshsjeOrLshsslQueryVo> lshsjeOrLshsslQueryVos;

    public LskmzdVoForSpecial getLskmzdVoForSpecial() {
        return lskmzdVoForSpecial;
    }

    public void setLskmzdVoForSpecial(LskmzdVoForSpecial lskmzdVoForSpecial) {
        this.lskmzdVoForSpecial = lskmzdVoForSpecial;
    }

    public List<LshsjeOrLshsslQueryVo> getLshsjeOrLshsslQueryVos() {
        return lshsjeOrLshsslQueryVos;
    }

    public void setLshsjeOrLshsslQueryVos(List<LshsjeOrLshsslQueryVo> lshsjeOrLshsslQueryVos) {
        this.lshsjeOrLshsslQueryVos = lshsjeOrLshsslQueryVos;
    }
}
