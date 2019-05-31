package com.bjut.ssh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Title: Lszcdy
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/11 10:02
 * @Version: 1.0
 */
@Entity
public class Lszcdy {
    private int zcdyAutoId;
    private String acontBookNo;
    private String itemNo;
    private String accLevel;
    private String combineEntry;
    private String oppItem;
    private String summary;
    private String daysTge;
    private String listItemLevel;
    private String itemDir;
    private String printUse;
    private Byte fixCol;
    private Byte pageCol;
    private String fHblm;

    @Id
    @Column(name = "ZCDY_AutoId")
    public int getZcdyAutoId() {
        return zcdyAutoId;
    }

    public void setZcdyAutoId(int zcdyAutoId) {
        this.zcdyAutoId = zcdyAutoId;
    }

    @Basic
    @Column(name = "AcontBookNo")
    public String getAcontBookNo() {
        return acontBookNo;
    }

    public void setAcontBookNo(String acontBookNo) {
        this.acontBookNo = acontBookNo;
    }

    @Basic
    @Column(name = "ItemNo")
    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    @Basic
    @Column(name = "AccLevel")
    public String getAccLevel() {
        return accLevel;
    }

    public void setAccLevel(String accLevel) {
        this.accLevel = accLevel;
    }

    @Basic
    @Column(name = "CombineEntry")
    public String getCombineEntry() {
        return combineEntry;
    }

    public void setCombineEntry(String combineEntry) {
        this.combineEntry = combineEntry;
    }

    @Basic
    @Column(name = "OppItem")
    public String getOppItem() {
        return oppItem;
    }

    public void setOppItem(String oppItem) {
        this.oppItem = oppItem;
    }

    @Basic
    @Column(name = "Summary")
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Basic
    @Column(name = "DaysTge")
    public String getDaysTge() {
        return daysTge;
    }

    public void setDaysTge(String daysTge) {
        this.daysTge = daysTge;
    }

    @Basic
    @Column(name = "ListItemLevel")
    public String getListItemLevel() {
        return listItemLevel;
    }

    public void setListItemLevel(String listItemLevel) {
        this.listItemLevel = listItemLevel;
    }

    @Basic
    @Column(name = "ItemDir")
    public String getItemDir() {
        return itemDir;
    }

    public void setItemDir(String itemDir) {
        this.itemDir = itemDir;
    }

    @Basic
    @Column(name = "PrintUse")
    public String getPrintUse() {
        return printUse;
    }

    public void setPrintUse(String printUse) {
        this.printUse = printUse;
    }

    @Basic
    @Column(name = "FixCol")
    public Byte getFixCol() {
        return fixCol;
    }

    public void setFixCol(Byte fixCol) {
        this.fixCol = fixCol;
    }

    @Basic
    @Column(name = "PageCol")
    public Byte getPageCol() {
        return pageCol;
    }

    public void setPageCol(Byte pageCol) {
        this.pageCol = pageCol;
    }

    @Basic
    @Column(name = "F_HBLM")
    public String getfHblm() {
        return fHblm;
    }

    public void setfHblm(String fHblm) {
        this.fHblm = fHblm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lszcdy lszcdy = (Lszcdy) o;

        if (zcdyAutoId != lszcdy.zcdyAutoId) return false;
        if (acontBookNo != null ? !acontBookNo.equals(lszcdy.acontBookNo) : lszcdy.acontBookNo != null) return false;
        if (itemNo != null ? !itemNo.equals(lszcdy.itemNo) : lszcdy.itemNo != null) return false;
        if (accLevel != null ? !accLevel.equals(lszcdy.accLevel) : lszcdy.accLevel != null) return false;
        if (combineEntry != null ? !combineEntry.equals(lszcdy.combineEntry) : lszcdy.combineEntry != null)
            return false;
        if (oppItem != null ? !oppItem.equals(lszcdy.oppItem) : lszcdy.oppItem != null) return false;
        if (summary != null ? !summary.equals(lszcdy.summary) : lszcdy.summary != null) return false;
        if (daysTge != null ? !daysTge.equals(lszcdy.daysTge) : lszcdy.daysTge != null) return false;
        if (listItemLevel != null ? !listItemLevel.equals(lszcdy.listItemLevel) : lszcdy.listItemLevel != null)
            return false;
        if (itemDir != null ? !itemDir.equals(lszcdy.itemDir) : lszcdy.itemDir != null) return false;
        if (printUse != null ? !printUse.equals(lszcdy.printUse) : lszcdy.printUse != null) return false;
        if (fixCol != null ? !fixCol.equals(lszcdy.fixCol) : lszcdy.fixCol != null) return false;
        if (pageCol != null ? !pageCol.equals(lszcdy.pageCol) : lszcdy.pageCol != null) return false;
        if (fHblm != null ? !fHblm.equals(lszcdy.fHblm) : lszcdy.fHblm != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = zcdyAutoId;
        result = 31 * result + (acontBookNo != null ? acontBookNo.hashCode() : 0);
        result = 31 * result + (itemNo != null ? itemNo.hashCode() : 0);
        result = 31 * result + (accLevel != null ? accLevel.hashCode() : 0);
        result = 31 * result + (combineEntry != null ? combineEntry.hashCode() : 0);
        result = 31 * result + (oppItem != null ? oppItem.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (daysTge != null ? daysTge.hashCode() : 0);
        result = 31 * result + (listItemLevel != null ? listItemLevel.hashCode() : 0);
        result = 31 * result + (itemDir != null ? itemDir.hashCode() : 0);
        result = 31 * result + (printUse != null ? printUse.hashCode() : 0);
        result = 31 * result + (fixCol != null ? fixCol.hashCode() : 0);
        result = 31 * result + (pageCol != null ? pageCol.hashCode() : 0);
        result = 31 * result + (fHblm != null ? fHblm.hashCode() : 0);
        return result;
    }
}
