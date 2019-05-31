package com.tensquare.qa.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Title: Pl
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/11/16 19:40
 * @Version: 1.0
 */
@Entity
@Table(name="tb_pl")
public class Pl implements Serializable{
    @Id
    private String problemid;
    @Id
    private String labelid;

    public String getProblemid() {
        return problemid;
    }

    public void setProblemid(String problemid) {
        this.problemid = problemid;
    }

    public String getLabelid() {
        return labelid;
    }

    public void setLabelid(String labelid) {
        this.labelid = labelid;
    }

    @Override
    public String toString() {
        return "Pl{" +
                "problemid='" + problemid + '\'' +
                ", labelid='" + labelid + '\'' +
                '}';
    }
}
