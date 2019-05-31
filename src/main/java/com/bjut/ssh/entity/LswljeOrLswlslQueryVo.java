package com.bjut.ssh.entity;

import java.util.List;

/**
 * @Title: LswljeOrLswlsl
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/23 8:57
 * @Version: 1.0
 */
public class LswljeOrLswlslQueryVo {
    private LskmzdVoForContacts lskmzdVoForContacts;

    private List<LswljeQueryVo> lswljeQueryVoList;


    public LskmzdVoForContacts getLskmzdVoForContacts() {
        return lskmzdVoForContacts;
    }

    public void setLskmzdVoForContacts(LskmzdVoForContacts lskmzdVoForContacts) {
        this.lskmzdVoForContacts = lskmzdVoForContacts;
    }

    public List<LswljeQueryVo> getLswljeQueryVoList() {
        return lswljeQueryVoList;
    }

    public void setLswljeQueryVoList(List<LswljeQueryVo> lswljeQueryVoList) {
        this.lswljeQueryVoList = lswljeQueryVoList;
    }


}
