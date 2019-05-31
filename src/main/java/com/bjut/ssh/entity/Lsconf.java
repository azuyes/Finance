package com.bjut.ssh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Title: Lsconf
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/11 10:00
 * @Version: 1.0
 */
@Entity
public class Lsconf {
    private String confKey;
    private String confValue;
    private String confNote;

    @Id
    @Column(name = "Conf_Key")
    public String getConfKey() {
        return confKey;
    }

    public void setConfKey(String confKey) {
        this.confKey = confKey;
    }

    @Basic
    @Column(name = "Conf_Value")
    public String getConfValue() {
        return confValue;
    }

    public void setConfValue(String confValue) {
        this.confValue = confValue;
    }

    @Basic
    @Column(name = "Conf_Note")
    public String getConfNote() {
        return confNote;
    }

    public void setConfNote(String confNote) {
        this.confNote = confNote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lsconf lsconf = (Lsconf) o;

        if (confKey != null ? !confKey.equals(lsconf.confKey) : lsconf.confKey != null) return false;
        if (confValue != null ? !confValue.equals(lsconf.confValue) : lsconf.confValue != null) return false;
        if (confNote != null ? !confNote.equals(lsconf.confNote) : lsconf.confNote != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = confKey != null ? confKey.hashCode() : 0;
        result = 31 * result + (confValue != null ? confValue.hashCode() : 0);
        result = 31 * result + (confNote != null ? confNote.hashCode() : 0);
        return result;
    }
}
