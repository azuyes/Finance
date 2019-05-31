package com.bjut.ssh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Title: Lszczd
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/11 10:02
 * @Version: 1.0
 */
@Entity
public class Lszczd {
    private String acontBookNo;
    private String accBookName;
    private String accBookType;
    private String accType;
    private Integer pageRules;
    private Byte absWidth;
    private String chaPage;
    private String printFor;
    private String formatNo;
    private String addEmptyRow;
    private Byte fTdyn;

    @Id
    @Column(name = "AcontBookNo")
    public String getAcontBookNo() {
        return acontBookNo;
    }

    public void setAcontBookNo(String acontBookNo) {
        this.acontBookNo = acontBookNo;
    }

    @Basic
    @Column(name = "AccBookName")
    public String getAccBookName() {
        return accBookName;
    }

    public void setAccBookName(String accBookName) {
        this.accBookName = accBookName;
    }

    @Basic
    @Column(name = "AccBookType")
    public String getAccBookType() {
        return accBookType;
    }

    public void setAccBookType(String accBookType) {
        this.accBookType = accBookType;
    }

    @Basic
    @Column(name = "AccType")
    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    @Basic
    @Column(name = "PageRules")
    public Integer getPageRules() {
        return pageRules;
    }

    public void setPageRules(Integer pageRules) {
        this.pageRules = pageRules;
    }

    @Basic
    @Column(name = "AbsWidth")
    public Byte getAbsWidth() {
        return absWidth;
    }

    public void setAbsWidth(Byte absWidth) {
        this.absWidth = absWidth;
    }

    @Basic
    @Column(name = "ChaPage")
    public String getChaPage() {
        return chaPage;
    }

    public void setChaPage(String chaPage) {
        this.chaPage = chaPage;
    }

    @Basic
    @Column(name = "PrintFor")
    public String getPrintFor() {
        return printFor;
    }

    public void setPrintFor(String printFor) {
        this.printFor = printFor;
    }

    @Basic
    @Column(name = "FormatNo")
    public String getFormatNo() {
        return formatNo;
    }

    public void setFormatNo(String formatNo) {
        this.formatNo = formatNo;
    }

    @Basic
    @Column(name = "AddEmptyRow")
    public String getAddEmptyRow() {
        return addEmptyRow;
    }

    public void setAddEmptyRow(String addEmptyRow) {
        this.addEmptyRow = addEmptyRow;
    }

    @Basic
    @Column(name = "F_TDYN")
    public Byte getfTdyn() {
        return fTdyn;
    }

    public void setfTdyn(Byte fTdyn) {
        this.fTdyn = fTdyn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lszczd lszczd = (Lszczd) o;

        if (acontBookNo != null ? !acontBookNo.equals(lszczd.acontBookNo) : lszczd.acontBookNo != null) return false;
        if (accBookName != null ? !accBookName.equals(lszczd.accBookName) : lszczd.accBookName != null) return false;
        if (accBookType != null ? !accBookType.equals(lszczd.accBookType) : lszczd.accBookType != null) return false;
        if (accType != null ? !accType.equals(lszczd.accType) : lszczd.accType != null) return false;
        if (pageRules != null ? !pageRules.equals(lszczd.pageRules) : lszczd.pageRules != null) return false;
        if (absWidth != null ? !absWidth.equals(lszczd.absWidth) : lszczd.absWidth != null) return false;
        if (chaPage != null ? !chaPage.equals(lszczd.chaPage) : lszczd.chaPage != null) return false;
        if (printFor != null ? !printFor.equals(lszczd.printFor) : lszczd.printFor != null) return false;
        if (formatNo != null ? !formatNo.equals(lszczd.formatNo) : lszczd.formatNo != null) return false;
        if (addEmptyRow != null ? !addEmptyRow.equals(lszczd.addEmptyRow) : lszczd.addEmptyRow != null) return false;
        if (fTdyn != null ? !fTdyn.equals(lszczd.fTdyn) : lszczd.fTdyn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = acontBookNo != null ? acontBookNo.hashCode() : 0;
        result = 31 * result + (accBookName != null ? accBookName.hashCode() : 0);
        result = 31 * result + (accBookType != null ? accBookType.hashCode() : 0);
        result = 31 * result + (accType != null ? accType.hashCode() : 0);
        result = 31 * result + (pageRules != null ? pageRules.hashCode() : 0);
        result = 31 * result + (absWidth != null ? absWidth.hashCode() : 0);
        result = 31 * result + (chaPage != null ? chaPage.hashCode() : 0);
        result = 31 * result + (printFor != null ? printFor.hashCode() : 0);
        result = 31 * result + (formatNo != null ? formatNo.hashCode() : 0);
        result = 31 * result + (addEmptyRow != null ? addEmptyRow.hashCode() : 0);
        result = 31 * result + (fTdyn != null ? fTdyn.hashCode() : 0);
        return result;
    }
}
