package com.bjut.ssh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Title: Accrecord
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/10/31 11:06
 * @Version: 1.0
 */
@Entity
public class Accrecord {
    private String id;
    private String name;
    private Integer flag;

    @Id
    @Column(name = "id", nullable = false, length = 3)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 10)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "flag", nullable = true)
    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Accrecord accrecord = (Accrecord) o;

        if (id != null ? !id.equals(accrecord.id) : accrecord.id != null) return false;
        if (name != null ? !name.equals(accrecord.name) : accrecord.name != null) return false;
        if (flag != null ? !flag.equals(accrecord.flag) : accrecord.flag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (flag != null ? flag.hashCode() : 0);
        return result;
    }
}
