package com.bjut.ssh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * @Title: Lcbzd
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/11 9:59
 * @Version: 1.0
 */
@Entity
public class Lcbzd {
    private Integer bzdAutoId;
    private String rptOrde;
    private String rptNo;
    private String rptDate;
    private String rptName;
    private Integer titleRules;
    private Integer headRules;
    private Integer bodyRules;
    private Integer totalRules;
    private Integer rptCol;
    private Integer fixRow;
    private Integer fixCol;
    private String reportNo;
    private String bbzdFcbz;
    private String bbzdNb;
    private String bbzdXf;
    private byte[] bbzdGs;
    private String bbzdSbxz;
    private String bbzdLx;
    private String tzzdOede;
    private Integer bbzdFixrn;
    private Integer bbzdFixcn;
    private Integer bbzdSplit;
    private BigInteger bbzdUse;

    @Id
    @Column(name = "BZD_AutoId")
    public Integer getBzdAutoId() {
        return bzdAutoId;
    }

    public void setBzdAutoId(Integer bzdAutoId) {
        this.bzdAutoId = bzdAutoId;
    }

    @Basic
    @Column(name = "RptOrde")
    public String getRptOrde() {
        return rptOrde;
    }

    public void setRptOrde(String rptOrde) {
        this.rptOrde = rptOrde;
    }

    @Basic
    @Column(name = "RptNo")
    public String getRptNo() {
        return rptNo;
    }

    public void setRptNo(String rptNo) {
        this.rptNo = rptNo;
    }

    @Basic
    @Column(name = "RptDate")
    public String getRptDate() {
        return rptDate;
    }

    public void setRptDate(String rptDate) {
        this.rptDate = rptDate;
    }

    @Basic
    @Column(name = "RptName")
    public String getRptName() {
        return rptName;
    }

    public void setRptName(String rptName) {
        this.rptName = rptName;
    }

    @Basic
    @Column(name = "TitleRules")
    public Integer getTitleRules() {
        return titleRules;
    }

    public void setTitleRules(Integer titleRules) {
        this.titleRules = titleRules;
    }

    @Basic
    @Column(name = "HeadRules")
    public Integer getHeadRules() {
        return headRules;
    }

    public void setHeadRules(Integer headRules) {
        this.headRules = headRules;
    }

    @Basic
    @Column(name = "BodyRules")
    public Integer getBodyRules() {
        return bodyRules;
    }

    public void setBodyRules(Integer bodyRules) {
        this.bodyRules = bodyRules;
    }

    @Basic
    @Column(name = "TotalRules")
    public Integer getTotalRules() {
        return totalRules;
    }

    public void setTotalRules(Integer totalRules) {
        this.totalRules = totalRules;
    }

    @Basic
    @Column(name = "RptCol")
    public Integer getRptCol() {
        return rptCol;
    }

    public void setRptCol(Integer rptCol) {
        this.rptCol = rptCol;
    }

    @Basic
    @Column(name = "FixRow")
    public Integer getFixRow() {
        return fixRow;
    }

    public void setFixRow(Integer fixRow) {
        this.fixRow = fixRow;
    }

    @Basic
    @Column(name = "FixCol")
    public Integer getFixCol() {
        return fixCol;
    }

    public void setFixCol(Integer fixCol) {
        this.fixCol = fixCol;
    }

    @Basic
    @Column(name = "ReportNo")
    public String getReportNo() {
        return reportNo;
    }

    public void setReportNo(String reportNo) {
        this.reportNo = reportNo;
    }

    @Basic
    @Column(name = "BBZD_FCBZ")
    public String getBbzdFcbz() {
        return bbzdFcbz;
    }

    public void setBbzdFcbz(String bbzdFcbz) {
        this.bbzdFcbz = bbzdFcbz;
    }

    @Basic
    @Column(name = "BBZD_NB")
    public String getBbzdNb() {
        return bbzdNb;
    }

    public void setBbzdNb(String bbzdNb) {
        this.bbzdNb = bbzdNb;
    }

    @Basic
    @Column(name = "BBZD_XF")
    public String getBbzdXf() {
        return bbzdXf;
    }

    public void setBbzdXf(String bbzdXf) {
        this.bbzdXf = bbzdXf;
    }

    @Basic
    @Column(name = "BBZD_GS")
    public byte[] getBbzdGs() {
        return bbzdGs;
    }

    public void setBbzdGs(byte[] bbzdGs) {
        this.bbzdGs = bbzdGs;
    }

    @Basic
    @Column(name = "BBZD_SBXZ")
    public String getBbzdSbxz() {
        return bbzdSbxz;
    }

    public void setBbzdSbxz(String bbzdSbxz) {
        this.bbzdSbxz = bbzdSbxz;
    }

    @Basic
    @Column(name = "BBZD_LX")
    public String getBbzdLx() {
        return bbzdLx;
    }

    public void setBbzdLx(String bbzdLx) {
        this.bbzdLx = bbzdLx;
    }

    @Basic
    @Column(name = "TZZD_OEDE")
    public String getTzzdOede() {
        return tzzdOede;
    }

    public void setTzzdOede(String tzzdOede) {
        this.tzzdOede = tzzdOede;
    }

    @Basic
    @Column(name = "BBZD_FIXRN")
    public Integer getBbzdFixrn() {
        return bbzdFixrn;
    }

    public void setBbzdFixrn(Integer bbzdFixrn) {
        this.bbzdFixrn = bbzdFixrn;
    }

    @Basic
    @Column(name = "BBZD_FIXCN")
    public Integer getBbzdFixcn() {
        return bbzdFixcn;
    }

    public void setBbzdFixcn(Integer bbzdFixcn) {
        this.bbzdFixcn = bbzdFixcn;
    }

    @Basic
    @Column(name = "BBZD_SPLIT")
    public Integer getBbzdSplit() {
        return bbzdSplit;
    }

    public void setBbzdSplit(Integer bbzdSplit) {
        this.bbzdSplit = bbzdSplit;
    }

    @Basic
    @Column(name = "BBZD_USE")
    public BigInteger getBbzdUse() {
        return bbzdUse;
    }

    public void setBbzdUse(BigInteger bbzdUse) {
        this.bbzdUse = bbzdUse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lcbzd lcbzd = (Lcbzd) o;

        if (bzdAutoId != lcbzd.bzdAutoId) return false;
        if (rptOrde != null ? !rptOrde.equals(lcbzd.rptOrde) : lcbzd.rptOrde != null) return false;
        if (rptNo != null ? !rptNo.equals(lcbzd.rptNo) : lcbzd.rptNo != null) return false;
        if (rptDate != null ? !rptDate.equals(lcbzd.rptDate) : lcbzd.rptDate != null) return false;
        if (rptName != null ? !rptName.equals(lcbzd.rptName) : lcbzd.rptName != null) return false;
        if (titleRules != null ? !titleRules.equals(lcbzd.titleRules) : lcbzd.titleRules != null) return false;
        if (headRules != null ? !headRules.equals(lcbzd.headRules) : lcbzd.headRules != null) return false;
        if (bodyRules != null ? !bodyRules.equals(lcbzd.bodyRules) : lcbzd.bodyRules != null) return false;
        if (totalRules != null ? !totalRules.equals(lcbzd.totalRules) : lcbzd.totalRules != null) return false;
        if (rptCol != null ? !rptCol.equals(lcbzd.rptCol) : lcbzd.rptCol != null) return false;
        if (fixRow != null ? !fixRow.equals(lcbzd.fixRow) : lcbzd.fixRow != null) return false;
        if (fixCol != null ? !fixCol.equals(lcbzd.fixCol) : lcbzd.fixCol != null) return false;
        if (reportNo != null ? !reportNo.equals(lcbzd.reportNo) : lcbzd.reportNo != null) return false;
        if (bbzdFcbz != null ? !bbzdFcbz.equals(lcbzd.bbzdFcbz) : lcbzd.bbzdFcbz != null) return false;
        if (bbzdNb != null ? !bbzdNb.equals(lcbzd.bbzdNb) : lcbzd.bbzdNb != null) return false;
        if (bbzdXf != null ? !bbzdXf.equals(lcbzd.bbzdXf) : lcbzd.bbzdXf != null) return false;
        if (!Arrays.equals(bbzdGs, lcbzd.bbzdGs)) return false;
        if (bbzdSbxz != null ? !bbzdSbxz.equals(lcbzd.bbzdSbxz) : lcbzd.bbzdSbxz != null) return false;
        if (bbzdLx != null ? !bbzdLx.equals(lcbzd.bbzdLx) : lcbzd.bbzdLx != null) return false;
        if (tzzdOede != null ? !tzzdOede.equals(lcbzd.tzzdOede) : lcbzd.tzzdOede != null) return false;
        if (bbzdFixrn != null ? !bbzdFixrn.equals(lcbzd.bbzdFixrn) : lcbzd.bbzdFixrn != null) return false;
        if (bbzdFixcn != null ? !bbzdFixcn.equals(lcbzd.bbzdFixcn) : lcbzd.bbzdFixcn != null) return false;
        if (bbzdSplit != null ? !bbzdSplit.equals(lcbzd.bbzdSplit) : lcbzd.bbzdSplit != null) return false;
        if (bbzdUse != null ? !bbzdUse.equals(lcbzd.bbzdUse) : lcbzd.bbzdUse != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bzdAutoId;
        result = 31 * result + (rptOrde != null ? rptOrde.hashCode() : 0);
        result = 31 * result + (rptNo != null ? rptNo.hashCode() : 0);
        result = 31 * result + (rptDate != null ? rptDate.hashCode() : 0);
        result = 31 * result + (rptName != null ? rptName.hashCode() : 0);
        result = 31 * result + (titleRules != null ? titleRules.hashCode() : 0);
        result = 31 * result + (headRules != null ? headRules.hashCode() : 0);
        result = 31 * result + (bodyRules != null ? bodyRules.hashCode() : 0);
        result = 31 * result + (totalRules != null ? totalRules.hashCode() : 0);
        result = 31 * result + (rptCol != null ? rptCol.hashCode() : 0);
        result = 31 * result + (fixRow != null ? fixRow.hashCode() : 0);
        result = 31 * result + (fixCol != null ? fixCol.hashCode() : 0);
        result = 31 * result + (reportNo != null ? reportNo.hashCode() : 0);
        result = 31 * result + (bbzdFcbz != null ? bbzdFcbz.hashCode() : 0);
        result = 31 * result + (bbzdNb != null ? bbzdNb.hashCode() : 0);
        result = 31 * result + (bbzdXf != null ? bbzdXf.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(bbzdGs);
        result = 31 * result + (bbzdSbxz != null ? bbzdSbxz.hashCode() : 0);
        result = 31 * result + (bbzdLx != null ? bbzdLx.hashCode() : 0);
        result = 31 * result + (tzzdOede != null ? tzzdOede.hashCode() : 0);
        result = 31 * result + (bbzdFixrn != null ? bbzdFixrn.hashCode() : 0);
        result = 31 * result + (bbzdFixcn != null ? bbzdFixcn.hashCode() : 0);
        result = 31 * result + (bbzdSplit != null ? bbzdSplit.hashCode() : 0);
        result = 31 * result + (bbzdUse != null ? bbzdUse.hashCode() : 0);
        return result;
    }
}
