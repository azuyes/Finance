package com.bjut.ssh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Title: Lshszd
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/11 10:00
 * @Version: 1.0
 */
@Entity
public class Lshszd {
    private int hszdAutoId;
    private String catNo;
    private String spNo;
    private String spLevel;
    private Byte finLevel;
    private String spName;
    private String itemNo;
    private String fWbbh;
    private String fBzlb;
    private String fBzbm;
    private String fSybh;
    private String fFj01;
    private String fFj02;
    private String fFj03;
    private String fFj04;
    private String fFj05;
    private String fFj06;
    private String fFj07;
    private String fFj08;
    private String fFj09;
    private String fFj10;
    private String fFj11;
    private String fFj12;
    private String fFj13;
    private String fFj14;
    private String fFj15;

    @Id
    @Column(name = "HSZD_AutoId")
    public int getHszdAutoId() {
        return hszdAutoId;
    }

    public void setHszdAutoId(int hszdAutoId) {
        this.hszdAutoId = hszdAutoId;
    }

    @Basic
    @Column(name = "CatNo")
    public String getCatNo() {
        return catNo;
    }

    public void setCatNo(String catNo) {
        this.catNo = catNo;
    }

    @Basic
    @Column(name = "SpNo")
    public String getSpNo() {
        return spNo;
    }

    public void setSpNo(String spNo) {
        this.spNo = spNo;
    }

    @Basic
    @Column(name = "SpLevel")
    public String getSpLevel() {
        return spLevel;
    }

    public void setSpLevel(String spLevel) {
        this.spLevel = spLevel;
    }

    @Basic
    @Column(name = "FinLevel")
    public Byte getFinLevel() {
        return finLevel;
    }

    public void setFinLevel(Byte finLevel) {
        this.finLevel = finLevel;
    }

    @Basic
    @Column(name = "SpName")
    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
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
    @Column(name = "F_WBBH")
    public String getfWbbh() {
        return fWbbh;
    }

    public void setfWbbh(String fWbbh) {
        this.fWbbh = fWbbh;
    }

    @Basic
    @Column(name = "F_BZLB")
    public String getfBzlb() {
        return fBzlb;
    }

    public void setfBzlb(String fBzlb) {
        this.fBzlb = fBzlb;
    }

    @Basic
    @Column(name = "F_BZBM")
    public String getfBzbm() {
        return fBzbm;
    }

    public void setfBzbm(String fBzbm) {
        this.fBzbm = fBzbm;
    }

    @Basic
    @Column(name = "F_SYBH")
    public String getfSybh() {
        return fSybh;
    }

    public void setfSybh(String fSybh) {
        this.fSybh = fSybh;
    }

    @Basic
    @Column(name = "F_FJ01")
    public String getfFj01() {
        return fFj01;
    }

    public void setfFj01(String fFj01) {
        this.fFj01 = fFj01;
    }

    @Basic
    @Column(name = "F_FJ02")
    public String getfFj02() {
        return fFj02;
    }

    public void setfFj02(String fFj02) {
        this.fFj02 = fFj02;
    }

    @Basic
    @Column(name = "F_FJ03")
    public String getfFj03() {
        return fFj03;
    }

    public void setfFj03(String fFj03) {
        this.fFj03 = fFj03;
    }

    @Basic
    @Column(name = "F_FJ04")
    public String getfFj04() {
        return fFj04;
    }

    public void setfFj04(String fFj04) {
        this.fFj04 = fFj04;
    }

    @Basic
    @Column(name = "F_FJ05")
    public String getfFj05() {
        return fFj05;
    }

    public void setfFj05(String fFj05) {
        this.fFj05 = fFj05;
    }

    @Basic
    @Column(name = "F_FJ06")
    public String getfFj06() {
        return fFj06;
    }

    public void setfFj06(String fFj06) {
        this.fFj06 = fFj06;
    }

    @Basic
    @Column(name = "F_FJ07")
    public String getfFj07() {
        return fFj07;
    }

    public void setfFj07(String fFj07) {
        this.fFj07 = fFj07;
    }

    @Basic
    @Column(name = "F_FJ08")
    public String getfFj08() {
        return fFj08;
    }

    public void setfFj08(String fFj08) {
        this.fFj08 = fFj08;
    }

    @Basic
    @Column(name = "F_FJ09")
    public String getfFj09() {
        return fFj09;
    }

    public void setfFj09(String fFj09) {
        this.fFj09 = fFj09;
    }

    @Basic
    @Column(name = "F_FJ10")
    public String getfFj10() {
        return fFj10;
    }

    public void setfFj10(String fFj10) {
        this.fFj10 = fFj10;
    }

    @Basic
    @Column(name = "F_FJ11")
    public String getfFj11() {
        return fFj11;
    }

    public void setfFj11(String fFj11) {
        this.fFj11 = fFj11;
    }

    @Basic
    @Column(name = "F_FJ12")
    public String getfFj12() {
        return fFj12;
    }

    public void setfFj12(String fFj12) {
        this.fFj12 = fFj12;
    }

    @Basic
    @Column(name = "F_FJ13")
    public String getfFj13() {
        return fFj13;
    }

    public void setfFj13(String fFj13) {
        this.fFj13 = fFj13;
    }

    @Basic
    @Column(name = "F_FJ14")
    public String getfFj14() {
        return fFj14;
    }

    public void setfFj14(String fFj14) {
        this.fFj14 = fFj14;
    }

    @Basic
    @Column(name = "F_FJ15")
    public String getfFj15() {
        return fFj15;
    }

    public void setfFj15(String fFj15) {
        this.fFj15 = fFj15;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lshszd lshszd = (Lshszd) o;

        if (hszdAutoId != lshszd.hszdAutoId) return false;
        if (catNo != null ? !catNo.equals(lshszd.catNo) : lshszd.catNo != null) return false;
        if (spNo != null ? !spNo.equals(lshszd.spNo) : lshszd.spNo != null) return false;
        if (spLevel != null ? !spLevel.equals(lshszd.spLevel) : lshszd.spLevel != null) return false;
        if (finLevel != null ? !finLevel.equals(lshszd.finLevel) : lshszd.finLevel != null) return false;
        if (spName != null ? !spName.equals(lshszd.spName) : lshszd.spName != null) return false;
        if (itemNo != null ? !itemNo.equals(lshszd.itemNo) : lshszd.itemNo != null) return false;
        if (fWbbh != null ? !fWbbh.equals(lshszd.fWbbh) : lshszd.fWbbh != null) return false;
        if (fBzlb != null ? !fBzlb.equals(lshszd.fBzlb) : lshszd.fBzlb != null) return false;
        if (fBzbm != null ? !fBzbm.equals(lshszd.fBzbm) : lshszd.fBzbm != null) return false;
        if (fSybh != null ? !fSybh.equals(lshszd.fSybh) : lshszd.fSybh != null) return false;
        if (fFj01 != null ? !fFj01.equals(lshszd.fFj01) : lshszd.fFj01 != null) return false;
        if (fFj02 != null ? !fFj02.equals(lshszd.fFj02) : lshszd.fFj02 != null) return false;
        if (fFj03 != null ? !fFj03.equals(lshszd.fFj03) : lshszd.fFj03 != null) return false;
        if (fFj04 != null ? !fFj04.equals(lshszd.fFj04) : lshszd.fFj04 != null) return false;
        if (fFj05 != null ? !fFj05.equals(lshszd.fFj05) : lshszd.fFj05 != null) return false;
        if (fFj06 != null ? !fFj06.equals(lshszd.fFj06) : lshszd.fFj06 != null) return false;
        if (fFj07 != null ? !fFj07.equals(lshszd.fFj07) : lshszd.fFj07 != null) return false;
        if (fFj08 != null ? !fFj08.equals(lshszd.fFj08) : lshszd.fFj08 != null) return false;
        if (fFj09 != null ? !fFj09.equals(lshszd.fFj09) : lshszd.fFj09 != null) return false;
        if (fFj10 != null ? !fFj10.equals(lshszd.fFj10) : lshszd.fFj10 != null) return false;
        if (fFj11 != null ? !fFj11.equals(lshszd.fFj11) : lshszd.fFj11 != null) return false;
        if (fFj12 != null ? !fFj12.equals(lshszd.fFj12) : lshszd.fFj12 != null) return false;
        if (fFj13 != null ? !fFj13.equals(lshszd.fFj13) : lshszd.fFj13 != null) return false;
        if (fFj14 != null ? !fFj14.equals(lshszd.fFj14) : lshszd.fFj14 != null) return false;
        if (fFj15 != null ? !fFj15.equals(lshszd.fFj15) : lshszd.fFj15 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = hszdAutoId;
        result = 31 * result + (catNo != null ? catNo.hashCode() : 0);
        result = 31 * result + (spNo != null ? spNo.hashCode() : 0);
        result = 31 * result + (spLevel != null ? spLevel.hashCode() : 0);
        result = 31 * result + (finLevel != null ? finLevel.hashCode() : 0);
        result = 31 * result + (spName != null ? spName.hashCode() : 0);
        result = 31 * result + (itemNo != null ? itemNo.hashCode() : 0);
        result = 31 * result + (fWbbh != null ? fWbbh.hashCode() : 0);
        result = 31 * result + (fBzlb != null ? fBzlb.hashCode() : 0);
        result = 31 * result + (fBzbm != null ? fBzbm.hashCode() : 0);
        result = 31 * result + (fSybh != null ? fSybh.hashCode() : 0);
        result = 31 * result + (fFj01 != null ? fFj01.hashCode() : 0);
        result = 31 * result + (fFj02 != null ? fFj02.hashCode() : 0);
        result = 31 * result + (fFj03 != null ? fFj03.hashCode() : 0);
        result = 31 * result + (fFj04 != null ? fFj04.hashCode() : 0);
        result = 31 * result + (fFj05 != null ? fFj05.hashCode() : 0);
        result = 31 * result + (fFj06 != null ? fFj06.hashCode() : 0);
        result = 31 * result + (fFj07 != null ? fFj07.hashCode() : 0);
        result = 31 * result + (fFj08 != null ? fFj08.hashCode() : 0);
        result = 31 * result + (fFj09 != null ? fFj09.hashCode() : 0);
        result = 31 * result + (fFj10 != null ? fFj10.hashCode() : 0);
        result = 31 * result + (fFj11 != null ? fFj11.hashCode() : 0);
        result = 31 * result + (fFj12 != null ? fFj12.hashCode() : 0);
        result = 31 * result + (fFj13 != null ? fFj13.hashCode() : 0);
        result = 31 * result + (fFj14 != null ? fFj14.hashCode() : 0);
        result = 31 * result + (fFj15 != null ? fFj15.hashCode() : 0);
        return result;
    }
}
