package com.bjut.ssh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Title: Lcdyzd
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/11 10:00
 * @Version: 1.0
 */
@Entity
public class Lcdyzd {
    private Integer dyzdAutoId;
    private Integer bzdAutoId;
    private String rptNo;//报表编号
    private String rptDate;//报表日期
    private String cellNo;//单元格序号
    private String rowInfoNo;//行字典序号
    private String colInfoNo;//列字典序号
    private String endRow;//合并单元格_最终行
    private String endCol;//合并单元格_最终列
    private String cellType;//单元格类型（数值、汇总、项目列）
    private String cellContent;//单元格内容
    private Double cellData;//数字类型得数据
    private String formulaRange;//公式应用范围
    private String formula;//公式项
    private String cellClassName;//单元格类名(对齐方式等)
    private Integer cellWidth;//单元格宽度
    private Integer cellHeight;//单元格高度
    private String fontStyle;//字体样式(加粗、斜体、下划线)
    private Double fontSize;
    private Byte autoEnter;
    private String dyzdSftz;
    private String dyzdSfbh;
    private Integer dyzdHooffset;
    private Integer dyzdLooffset;
    private String dyzdGsx1;
    private String dyzdGsbz;
    private Integer dyzdGsjb;
    private String dyzdGsx2;
    private String dyzdGsx3;
    private String dyzdGsx4;
    private String dyzdGsx5;
    private String hBzbm;
    private String lBzbm;
    private Integer dyzdDec;

    @Id
    @Column(name = "DYZD_AutoId")
    public Integer getDyzdAutoId() {
        return dyzdAutoId;
    }

    public void setDyzdAutoId(Integer dyzdAutoId) {
        this.dyzdAutoId = dyzdAutoId;
    }

    @Basic
    @Column(name = "BZD_AutoId")
    public Integer getBzdAutoId() {
        return bzdAutoId;
    }

    public void setBzdAutoId(Integer bzdAutoId) {
        this.bzdAutoId = bzdAutoId;
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
    @Column(name = "CellNo")
    public String getCellNo() {
        return cellNo;
    }

    public void setCellNo(String cellNo) {
        this.cellNo = cellNo;
    }

    @Basic
    @Column(name = "RowInfoNo")
    public String getRowInfoNo() {
        return rowInfoNo;
    }

    public void setRowInfoNo(String rowInfoNo) {
        this.rowInfoNo = rowInfoNo;
    }

    @Basic
    @Column(name = "ColInfoNo")
    public String getColInfoNo() {
        return colInfoNo;
    }

    public void setColInfoNo(String colInfoNo) {
        this.colInfoNo = colInfoNo;
    }

    @Basic
    @Column(name = "EndRow")
    public String getEndRow() {return endRow;}

    public void setEndRow(String endRow) {this.endRow = endRow;}

    @Basic
    @Column(name = "EndCol")
    public String getEndCol() {return endCol;}

    public void setEndCol(String endCol) {this.endCol = endCol;}

    @Basic
    @Column(name = "CellType")
    public String getCellType() {
        return cellType;
    }

    public void setCellType(String cellType) {
        this.cellType = cellType;
    }

    @Basic
    @Column(name = "CellContent")
    public String getCellContent() {
        return cellContent;
    }

    public void setCellContent(String cellContent) {
        this.cellContent = cellContent;
    }

    @Basic
    @Column(name = "CellData")
    public Double getCellData() {
        return cellData;
    }

    public void setCellData(Double cellData) {
        this.cellData = cellData;
    }

    @Basic
    @Column(name = "FormulaRange")
    public String getFormulaRange() {
        return formulaRange;
    }

    public void setFormulaRange(String formulaRange) {
        this.formulaRange = formulaRange;
    }

    @Basic
    @Column(name = "Formula")
    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    @Basic
    @Column(name = "CellClassName")
    public String getCellClassName() {
        return cellClassName;
    }

    public void setCellClassName(String cellClassName) {
        this.cellClassName = cellClassName;
    }

    @Basic
    @Column(name = "CellWidth")
    public Integer getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(Integer cellWidth) {
        this.cellWidth = cellWidth;
    }

    @Basic
    @Column(name = "CellHeight")
    public Integer getCellHeight() {
        return cellHeight;
    }

    public void setCellHeight(Integer cellHeight) {
        this.cellHeight = cellHeight;
    }

    @Basic
    @Column(name = "FontStyle")
    public String getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }

    @Basic
    @Column(name = "FontSize")
    public Double getFontSize() {
        return fontSize;
    }

    public void setFontSize(Double fontSize) {
        this.fontSize = fontSize;
    }

    @Basic
    @Column(name = "AutoEnter")
    public Byte getAutoEnter() {
        return autoEnter;
    }

    public void setAutoEnter(Byte autoEnter) {
        this.autoEnter = autoEnter;
    }

    @Basic
    @Column(name = "DYZD_SFTZ")
    public String getDyzdSftz() {
        return dyzdSftz;
    }

    public void setDyzdSftz(String dyzdSftz) {
        this.dyzdSftz = dyzdSftz;
    }

    @Basic
    @Column(name = "DYZD_SFBH")
    public String getDyzdSfbh() {
        return dyzdSfbh;
    }

    public void setDyzdSfbh(String dyzdSfbh) {
        this.dyzdSfbh = dyzdSfbh;
    }

    @Basic
    @Column(name = "DYZD_HOOFFSET")
    public Integer getDyzdHooffset() {
        return dyzdHooffset;
    }

    public void setDyzdHooffset(Integer dyzdHooffset) {
        this.dyzdHooffset = dyzdHooffset;
    }

    @Basic
    @Column(name = "DYZD_LOOFFSET")
    public Integer getDyzdLooffset() {
        return dyzdLooffset;
    }

    public void setDyzdLooffset(Integer dyzdLooffset) {
        this.dyzdLooffset = dyzdLooffset;
    }

    @Basic
    @Column(name = "DYZD_GSX1")
    public String getDyzdGsx1() {
        return dyzdGsx1;
    }

    public void setDyzdGsx1(String dyzdGsx1) {
        this.dyzdGsx1 = dyzdGsx1;
    }

    @Basic
    @Column(name = "DYZD_GSBZ")
    public String getDyzdGsbz() {
        return dyzdGsbz;
    }

    public void setDyzdGsbz(String dyzdGsbz) {
        this.dyzdGsbz = dyzdGsbz;
    }

    @Basic
    @Column(name = "DYZD_GSJB")
    public Integer getDyzdGsjb() {
        return dyzdGsjb;
    }

    public void setDyzdGsjb(Integer dyzdGsjb) {
        this.dyzdGsjb = dyzdGsjb;
    }

    @Basic
    @Column(name = "DYZD_GSX2")
    public String getDyzdGsx2() {
        return dyzdGsx2;
    }

    public void setDyzdGsx2(String dyzdGsx2) {
        this.dyzdGsx2 = dyzdGsx2;
    }

    @Basic
    @Column(name = "DYZD_GSX3")
    public String getDyzdGsx3() {
        return dyzdGsx3;
    }

    public void setDyzdGsx3(String dyzdGsx3) {
        this.dyzdGsx3 = dyzdGsx3;
    }

    @Basic
    @Column(name = "DYZD_GSX4")
    public String getDyzdGsx4() {
        return dyzdGsx4;
    }

    public void setDyzdGsx4(String dyzdGsx4) {
        this.dyzdGsx4 = dyzdGsx4;
    }

    @Basic
    @Column(name = "DYZD_GSX5")
    public String getDyzdGsx5() {
        return dyzdGsx5;
    }

    public void setDyzdGsx5(String dyzdGsx5) {
        this.dyzdGsx5 = dyzdGsx5;
    }

    @Basic
    @Column(name = "H_BZBM")
    public String gethBzbm() {
        return hBzbm;
    }

    public void sethBzbm(String hBzbm) {
        this.hBzbm = hBzbm;
    }

    @Basic
    @Column(name = "L_BZBM")
    public String getlBzbm() {
        return lBzbm;
    }

    public void setlBzbm(String lBzbm) {
        this.lBzbm = lBzbm;
    }

    @Basic
    @Column(name = "DYZD_DEC")
    public Integer getDyzdDec() {
        return dyzdDec;
    }

    public void setDyzdDec(Integer dyzdDec) {
        this.dyzdDec = dyzdDec;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lcdyzd lcdyzd = (Lcdyzd) o;

        if (dyzdAutoId != lcdyzd.dyzdAutoId) return false;
        if (bzdAutoId != null ? !bzdAutoId.equals(lcdyzd.bzdAutoId) : lcdyzd.bzdAutoId != null) return false;
        if (rptNo != null ? !rptNo.equals(lcdyzd.rptNo) : lcdyzd.rptNo != null) return false;
        if (rptDate != null ? !rptDate.equals(lcdyzd.rptDate) : lcdyzd.rptDate != null) return false;
        if (cellNo != null ? !cellNo.equals(lcdyzd.cellNo) : lcdyzd.cellNo != null) return false;
        if (rowInfoNo != null ? !rowInfoNo.equals(lcdyzd.rowInfoNo) : lcdyzd.rowInfoNo != null) return false;
        if (colInfoNo != null ? !colInfoNo.equals(lcdyzd.colInfoNo) : lcdyzd.colInfoNo != null) return false;
        if (endRow != null ? !endRow.equals(lcdyzd.endRow) : lcdyzd.endRow != null) return false;
        if (endCol != null ? !endCol.equals(lcdyzd.endCol) : lcdyzd.endCol != null) return false;
        if (cellType != null ? !cellType.equals(lcdyzd.cellType) : lcdyzd.cellType != null) return false;
        if (cellContent != null ? !cellContent.equals(lcdyzd.cellContent) : lcdyzd.cellContent != null) return false;
        if (cellData != null ? !cellData.equals(lcdyzd.cellData) : lcdyzd.cellData != null) return false;
        if (formulaRange != null ? !formulaRange.equals(lcdyzd.formulaRange) : lcdyzd.formulaRange != null) return false;
        if (formula != null ? !formula.equals(lcdyzd.formula) : lcdyzd.formula != null) return false;
        if (cellClassName != null ? !cellClassName.equals(lcdyzd.cellClassName) : lcdyzd.cellClassName != null)
            return false;
        if (cellWidth != null ? !cellWidth.equals(lcdyzd.cellWidth) : lcdyzd.cellWidth != null) return false;
        if (cellHeight != null ? !cellHeight.equals(lcdyzd.cellHeight) : lcdyzd.cellHeight != null) return false;
        if (fontStyle != null ? !fontStyle.equals(lcdyzd.fontStyle) : lcdyzd.fontStyle != null) return false;
        if (fontSize != null ? !fontSize.equals(lcdyzd.fontSize) : lcdyzd.fontSize != null) return false;
        if (autoEnter != null ? !autoEnter.equals(lcdyzd.autoEnter) : lcdyzd.autoEnter != null) return false;
        if (dyzdSftz != null ? !dyzdSftz.equals(lcdyzd.dyzdSftz) : lcdyzd.dyzdSftz != null) return false;
        if (dyzdSfbh != null ? !dyzdSfbh.equals(lcdyzd.dyzdSfbh) : lcdyzd.dyzdSfbh != null) return false;
        if (dyzdHooffset != null ? !dyzdHooffset.equals(lcdyzd.dyzdHooffset) : lcdyzd.dyzdHooffset != null)
            return false;
        if (dyzdLooffset != null ? !dyzdLooffset.equals(lcdyzd.dyzdLooffset) : lcdyzd.dyzdLooffset != null)
            return false;
        if (dyzdGsx1 != null ? !dyzdGsx1.equals(lcdyzd.dyzdGsx1) : lcdyzd.dyzdGsx1 != null) return false;
        if (dyzdGsbz != null ? !dyzdGsbz.equals(lcdyzd.dyzdGsbz) : lcdyzd.dyzdGsbz != null) return false;
        if (dyzdGsjb != null ? !dyzdGsjb.equals(lcdyzd.dyzdGsjb) : lcdyzd.dyzdGsjb != null) return false;
        if (dyzdGsx2 != null ? !dyzdGsx2.equals(lcdyzd.dyzdGsx2) : lcdyzd.dyzdGsx2 != null) return false;
        if (dyzdGsx3 != null ? !dyzdGsx3.equals(lcdyzd.dyzdGsx3) : lcdyzd.dyzdGsx3 != null) return false;
        if (dyzdGsx4 != null ? !dyzdGsx4.equals(lcdyzd.dyzdGsx4) : lcdyzd.dyzdGsx4 != null) return false;
        if (dyzdGsx5 != null ? !dyzdGsx5.equals(lcdyzd.dyzdGsx5) : lcdyzd.dyzdGsx5 != null) return false;
        if (hBzbm != null ? !hBzbm.equals(lcdyzd.hBzbm) : lcdyzd.hBzbm != null) return false;
        if (lBzbm != null ? !lBzbm.equals(lcdyzd.lBzbm) : lcdyzd.lBzbm != null) return false;
        if (dyzdDec != null ? !dyzdDec.equals(lcdyzd.dyzdDec) : lcdyzd.dyzdDec != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dyzdAutoId;
        result = 31 * result + (bzdAutoId != null ? bzdAutoId.hashCode() : 0);
        result = 31 * result + (rptNo != null ? rptNo.hashCode() : 0);
        result = 31 * result + (rptDate != null ? rptDate.hashCode() : 0);
        result = 31 * result + (cellNo != null ? cellNo.hashCode() : 0);
        result = 31 * result + (rowInfoNo != null ? rowInfoNo.hashCode() : 0);
        result = 31 * result + (colInfoNo != null ? colInfoNo.hashCode() : 0);
        result = 31 * result + (endRow != null ? endRow.hashCode() : 0);
        result = 31 * result + (endCol != null ? endCol.hashCode() : 0);
        result = 31 * result + (cellType != null ? cellType.hashCode() : 0);
        result = 31 * result + (cellContent != null ? cellContent.hashCode() : 0);
        result = 31 * result + (cellData != null ? cellData.hashCode() : 0);
        result = 31 * result + (formulaRange != null ? formulaRange.hashCode() : 0);
        result = 31 * result + (formula != null ? formula.hashCode() : 0);
        result = 31 * result + (cellClassName != null ? cellClassName.hashCode() : 0);
        result = 31 * result + (cellWidth != null ? cellWidth.hashCode() : 0);
        result = 31 * result + (cellHeight != null ? cellHeight.hashCode() : 0);
        result = 31 * result + (fontStyle != null ? fontStyle.hashCode() : 0);
        result = 31 * result + (fontSize != null ? fontSize.hashCode() : 0);
        result = 31 * result + (autoEnter != null ? autoEnter.hashCode() : 0);
        result = 31 * result + (dyzdSftz != null ? dyzdSftz.hashCode() : 0);
        result = 31 * result + (dyzdSfbh != null ? dyzdSfbh.hashCode() : 0);
        result = 31 * result + (dyzdHooffset != null ? dyzdHooffset.hashCode() : 0);
        result = 31 * result + (dyzdLooffset != null ? dyzdLooffset.hashCode() : 0);
        result = 31 * result + (dyzdGsx1 != null ? dyzdGsx1.hashCode() : 0);
        result = 31 * result + (dyzdGsbz != null ? dyzdGsbz.hashCode() : 0);
        result = 31 * result + (dyzdGsjb != null ? dyzdGsjb.hashCode() : 0);
        result = 31 * result + (dyzdGsx2 != null ? dyzdGsx2.hashCode() : 0);
        result = 31 * result + (dyzdGsx3 != null ? dyzdGsx3.hashCode() : 0);
        result = 31 * result + (dyzdGsx4 != null ? dyzdGsx4.hashCode() : 0);
        result = 31 * result + (dyzdGsx5 != null ? dyzdGsx5.hashCode() : 0);
        result = 31 * result + (hBzbm != null ? hBzbm.hashCode() : 0);
        result = 31 * result + (lBzbm != null ? lBzbm.hashCode() : 0);
        result = 31 * result + (dyzdDec != null ? dyzdDec.hashCode() : 0);
        return result;
    }
}
