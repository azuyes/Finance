package com.bjut.ssh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Title: Lsszzd
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/11 10:01
 * @Version: 1.0
 */
@Entity
public class Lsszzd {
    private int szzdAutoId;
    private String printNo;
    private String setNo;
    private String setNote;
    private String titleFont;
    private String titleSize;
    private String bodyFont;
    private String bodySize;
    private Byte horZoom;
    private Byte verZoom;
    private String printQua;
    private String outputType;
    private String pageSize;
    private String leftMargin;
    private String rightMargin;
    private String topMargin;
    private String bottomMargin;
    private Byte defaulTuse;

    @Id
    @Column(name = "SZZD_AutoId")
    public int getSzzdAutoId() {
        return szzdAutoId;
    }

    public void setSzzdAutoId(int szzdAutoId) {
        this.szzdAutoId = szzdAutoId;
    }

    @Basic
    @Column(name = "PrintNo")
    public String getPrintNo() {
        return printNo;
    }

    public void setPrintNo(String printNo) {
        this.printNo = printNo;
    }

    @Basic
    @Column(name = "SetNo")
    public String getSetNo() {
        return setNo;
    }

    public void setSetNo(String setNo) {
        this.setNo = setNo;
    }

    @Basic
    @Column(name = "SetNote")
    public String getSetNote() {
        return setNote;
    }

    public void setSetNote(String setNote) {
        this.setNote = setNote;
    }

    @Basic
    @Column(name = "TitleFont")
    public String getTitleFont() {
        return titleFont;
    }

    public void setTitleFont(String titleFont) {
        this.titleFont = titleFont;
    }

    @Basic
    @Column(name = "TitleSize")
    public String getTitleSize() {
        return titleSize;
    }

    public void setTitleSize(String titleSize) {
        this.titleSize = titleSize;
    }

    @Basic
    @Column(name = "BodyFont")
    public String getBodyFont() {
        return bodyFont;
    }

    public void setBodyFont(String bodyFont) {
        this.bodyFont = bodyFont;
    }

    @Basic
    @Column(name = "BodySize")
    public String getBodySize() {
        return bodySize;
    }

    public void setBodySize(String bodySize) {
        this.bodySize = bodySize;
    }

    @Basic
    @Column(name = "HorZoom")
    public Byte getHorZoom() {
        return horZoom;
    }

    public void setHorZoom(Byte horZoom) {
        this.horZoom = horZoom;
    }

    @Basic
    @Column(name = "VerZoom")
    public Byte getVerZoom() {
        return verZoom;
    }

    public void setVerZoom(Byte verZoom) {
        this.verZoom = verZoom;
    }

    @Basic
    @Column(name = "PrintQua")
    public String getPrintQua() {
        return printQua;
    }

    public void setPrintQua(String printQua) {
        this.printQua = printQua;
    }

    @Basic
    @Column(name = "OutputType")
    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    @Basic
    @Column(name = "PageSize")
    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    @Basic
    @Column(name = "LeftMargin")
    public String getLeftMargin() {
        return leftMargin;
    }

    public void setLeftMargin(String leftMargin) {
        this.leftMargin = leftMargin;
    }

    @Basic
    @Column(name = "RightMargin")
    public String getRightMargin() {
        return rightMargin;
    }

    public void setRightMargin(String rightMargin) {
        this.rightMargin = rightMargin;
    }

    @Basic
    @Column(name = "TopMargin")
    public String getTopMargin() {
        return topMargin;
    }

    public void setTopMargin(String topMargin) {
        this.topMargin = topMargin;
    }

    @Basic
    @Column(name = "BottomMargin")
    public String getBottomMargin() {
        return bottomMargin;
    }

    public void setBottomMargin(String bottomMargin) {
        this.bottomMargin = bottomMargin;
    }

    @Basic
    @Column(name = "DefaulTuse")
    public Byte getDefaulTuse() {
        return defaulTuse;
    }

    public void setDefaulTuse(Byte defaulTuse) {
        this.defaulTuse = defaulTuse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lsszzd lsszzd = (Lsszzd) o;

        if (szzdAutoId != lsszzd.szzdAutoId) return false;
        if (printNo != null ? !printNo.equals(lsszzd.printNo) : lsszzd.printNo != null) return false;
        if (setNo != null ? !setNo.equals(lsszzd.setNo) : lsszzd.setNo != null) return false;
        if (setNote != null ? !setNote.equals(lsszzd.setNote) : lsszzd.setNote != null) return false;
        if (titleFont != null ? !titleFont.equals(lsszzd.titleFont) : lsszzd.titleFont != null) return false;
        if (titleSize != null ? !titleSize.equals(lsszzd.titleSize) : lsszzd.titleSize != null) return false;
        if (bodyFont != null ? !bodyFont.equals(lsszzd.bodyFont) : lsszzd.bodyFont != null) return false;
        if (bodySize != null ? !bodySize.equals(lsszzd.bodySize) : lsszzd.bodySize != null) return false;
        if (horZoom != null ? !horZoom.equals(lsszzd.horZoom) : lsszzd.horZoom != null) return false;
        if (verZoom != null ? !verZoom.equals(lsszzd.verZoom) : lsszzd.verZoom != null) return false;
        if (printQua != null ? !printQua.equals(lsszzd.printQua) : lsszzd.printQua != null) return false;
        if (outputType != null ? !outputType.equals(lsszzd.outputType) : lsszzd.outputType != null) return false;
        if (pageSize != null ? !pageSize.equals(lsszzd.pageSize) : lsszzd.pageSize != null) return false;
        if (leftMargin != null ? !leftMargin.equals(lsszzd.leftMargin) : lsszzd.leftMargin != null) return false;
        if (rightMargin != null ? !rightMargin.equals(lsszzd.rightMargin) : lsszzd.rightMargin != null) return false;
        if (topMargin != null ? !topMargin.equals(lsszzd.topMargin) : lsszzd.topMargin != null) return false;
        if (bottomMargin != null ? !bottomMargin.equals(lsszzd.bottomMargin) : lsszzd.bottomMargin != null)
            return false;
        if (defaulTuse != null ? !defaulTuse.equals(lsszzd.defaulTuse) : lsszzd.defaulTuse != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = szzdAutoId;
        result = 31 * result + (printNo != null ? printNo.hashCode() : 0);
        result = 31 * result + (setNo != null ? setNo.hashCode() : 0);
        result = 31 * result + (setNote != null ? setNote.hashCode() : 0);
        result = 31 * result + (titleFont != null ? titleFont.hashCode() : 0);
        result = 31 * result + (titleSize != null ? titleSize.hashCode() : 0);
        result = 31 * result + (bodyFont != null ? bodyFont.hashCode() : 0);
        result = 31 * result + (bodySize != null ? bodySize.hashCode() : 0);
        result = 31 * result + (horZoom != null ? horZoom.hashCode() : 0);
        result = 31 * result + (verZoom != null ? verZoom.hashCode() : 0);
        result = 31 * result + (printQua != null ? printQua.hashCode() : 0);
        result = 31 * result + (outputType != null ? outputType.hashCode() : 0);
        result = 31 * result + (pageSize != null ? pageSize.hashCode() : 0);
        result = 31 * result + (leftMargin != null ? leftMargin.hashCode() : 0);
        result = 31 * result + (rightMargin != null ? rightMargin.hashCode() : 0);
        result = 31 * result + (topMargin != null ? topMargin.hashCode() : 0);
        result = 31 * result + (bottomMargin != null ? bottomMargin.hashCode() : 0);
        result = 31 * result + (defaulTuse != null ? defaulTuse.hashCode() : 0);
        return result;
    }
}
