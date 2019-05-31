package com.bjut.ssh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Title: Lshssl
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/11 10:00
 * @Version: 1.0
 */
@Entity
public class Lshssl {
    private int hsslAutoId;
    private String itemNo;
    private String spNo1;
    private String spNo2;
    private Double budQty;
    private Double leftQty;
    private Double unitPrice;
    private Double debitQty;
    private Double creditQty;
    private Double supQty;
    private Double debitQtySup;
    private Double creditQtySup;
    private Double debitQtyAcm;
    private Double creditQtyAcm;
    private Double debitQty01;
    private Double creditQty01;
    private Double leftQty01;
    private Double debitQty02;
    private Double creditQty02;
    private Double leftQty02;
    private Double debitQty03;
    private Double creditQty03;
    private Double leftQty03;
    private Double debitQty04;
    private Double creditQty04;
    private Double leftQty04;
    private Double debitQty05;
    private Double creditQty05;
    private Double leftQty05;
    private Double debitQty06;
    private Double creditQty06;
    private Double leftQty06;
    private Double debitQty07;
    private Double creditQty07;
    private Double leftQty07;
    private Double debitQty08;
    private Double creditQty08;
    private Double leftQty08;
    private Double debitQty09;
    private Double creditQty09;
    private Double leftQty09;
    private Double debitQty10;
    private Double creditQty10;
    private Double leftQty10;
    private Double debitQty11;
    private Double creditQty11;
    private Double leftQty11;
    private Double debitQty12;
    private Double creditQty12;
    private Double leftQty12;
    private Double fJs13;
    private Double fDs13;
    private Double fSl13;
    private Double fYsxl;
    private Double fYssx;

    @Id
    @Column(name = "HSSL_AutoId")
    public int getHsslAutoId() {
        return hsslAutoId;
    }

    public void setHsslAutoId(int hsslAutoId) {
        this.hsslAutoId = hsslAutoId;
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
    @Column(name = "SpNo1")
    public String getSpNo1() {
        return spNo1;
    }

    public void setSpNo1(String spNo1) {
        this.spNo1 = spNo1;
    }

    @Basic
    @Column(name = "SpNo2")
    public String getSpNo2() {
        return spNo2;
    }

    public void setSpNo2(String spNo2) {
        this.spNo2 = spNo2;
    }

    @Basic
    @Column(name = "BudQty")
    public Double getBudQty() {
        return budQty;
    }

    public void setBudQty(Double budQty) {
        this.budQty = budQty;
    }

    @Basic
    @Column(name = "LeftQty")
    public Double getLeftQty() {
        return leftQty;
    }

    public void setLeftQty(Double leftQty) {
        this.leftQty = leftQty;
    }

    @Basic
    @Column(name = "UnitPrice")
    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Basic
    @Column(name = "DebitQty")
    public Double getDebitQty() {
        return debitQty;
    }

    public void setDebitQty(Double debitQty) {
        this.debitQty = debitQty;
    }

    @Basic
    @Column(name = "CreditQty")
    public Double getCreditQty() {
        return creditQty;
    }

    public void setCreditQty(Double creditQty) {
        this.creditQty = creditQty;
    }

    @Basic
    @Column(name = "SupQty")
    public Double getSupQty() {
        return supQty;
    }

    public void setSupQty(Double supQty) {
        this.supQty = supQty;
    }

    @Basic
    @Column(name = "DebitQtySup")
    public Double getDebitQtySup() {
        return debitQtySup;
    }

    public void setDebitQtySup(Double debitQtySup) {
        this.debitQtySup = debitQtySup;
    }

    @Basic
    @Column(name = "CreditQtySup")
    public Double getCreditQtySup() {
        return creditQtySup;
    }

    public void setCreditQtySup(Double creditQtySup) {
        this.creditQtySup = creditQtySup;
    }

    @Basic
    @Column(name = "DebitQtyAcm")
    public Double getDebitQtyAcm() {
        return debitQtyAcm;
    }

    public void setDebitQtyAcm(Double debitQtyAcm) {
        this.debitQtyAcm = debitQtyAcm;
    }

    @Basic
    @Column(name = "CreditQtyAcm")
    public Double getCreditQtyAcm() {
        return creditQtyAcm;
    }

    public void setCreditQtyAcm(Double creditQtyAcm) {
        this.creditQtyAcm = creditQtyAcm;
    }

    @Basic
    @Column(name = "DebitQty01")
    public Double getDebitQty01() {
        return debitQty01;
    }

    public void setDebitQty01(Double debitQty01) {
        this.debitQty01 = debitQty01;
    }

    @Basic
    @Column(name = "CreditQty01")
    public Double getCreditQty01() {
        return creditQty01;
    }

    public void setCreditQty01(Double creditQty01) {
        this.creditQty01 = creditQty01;
    }

    @Basic
    @Column(name = "LeftQty01")
    public Double getLeftQty01() {
        return leftQty01;
    }

    public void setLeftQty01(Double leftQty01) {
        this.leftQty01 = leftQty01;
    }

    @Basic
    @Column(name = "DebitQty02")
    public Double getDebitQty02() {
        return debitQty02;
    }

    public void setDebitQty02(Double debitQty02) {
        this.debitQty02 = debitQty02;
    }

    @Basic
    @Column(name = "CreditQty02")
    public Double getCreditQty02() {
        return creditQty02;
    }

    public void setCreditQty02(Double creditQty02) {
        this.creditQty02 = creditQty02;
    }

    @Basic
    @Column(name = "LeftQty02")
    public Double getLeftQty02() {
        return leftQty02;
    }

    public void setLeftQty02(Double leftQty02) {
        this.leftQty02 = leftQty02;
    }

    @Basic
    @Column(name = "DebitQty03")
    public Double getDebitQty03() {
        return debitQty03;
    }

    public void setDebitQty03(Double debitQty03) {
        this.debitQty03 = debitQty03;
    }

    @Basic
    @Column(name = "CreditQty03")
    public Double getCreditQty03() {
        return creditQty03;
    }

    public void setCreditQty03(Double creditQty03) {
        this.creditQty03 = creditQty03;
    }

    @Basic
    @Column(name = "LeftQty03")
    public Double getLeftQty03() {
        return leftQty03;
    }

    public void setLeftQty03(Double leftQty03) {
        this.leftQty03 = leftQty03;
    }

    @Basic
    @Column(name = "DebitQty04")
    public Double getDebitQty04() {
        return debitQty04;
    }

    public void setDebitQty04(Double debitQty04) {
        this.debitQty04 = debitQty04;
    }

    @Basic
    @Column(name = "CreditQty04")
    public Double getCreditQty04() {
        return creditQty04;
    }

    public void setCreditQty04(Double creditQty04) {
        this.creditQty04 = creditQty04;
    }

    @Basic
    @Column(name = "LeftQty04")
    public Double getLeftQty04() {
        return leftQty04;
    }

    public void setLeftQty04(Double leftQty04) {
        this.leftQty04 = leftQty04;
    }

    @Basic
    @Column(name = "DebitQty05")
    public Double getDebitQty05() {
        return debitQty05;
    }

    public void setDebitQty05(Double debitQty05) {
        this.debitQty05 = debitQty05;
    }

    @Basic
    @Column(name = "CreditQty05")
    public Double getCreditQty05() {
        return creditQty05;
    }

    public void setCreditQty05(Double creditQty05) {
        this.creditQty05 = creditQty05;
    }

    @Basic
    @Column(name = "LeftQty05")
    public Double getLeftQty05() {
        return leftQty05;
    }

    public void setLeftQty05(Double leftQty05) {
        this.leftQty05 = leftQty05;
    }

    @Basic
    @Column(name = "DebitQty06")
    public Double getDebitQty06() {
        return debitQty06;
    }

    public void setDebitQty06(Double debitQty06) {
        this.debitQty06 = debitQty06;
    }

    @Basic
    @Column(name = "CreditQty06")
    public Double getCreditQty06() {
        return creditQty06;
    }

    public void setCreditQty06(Double creditQty06) {
        this.creditQty06 = creditQty06;
    }

    @Basic
    @Column(name = "LeftQty06")
    public Double getLeftQty06() {
        return leftQty06;
    }

    public void setLeftQty06(Double leftQty06) {
        this.leftQty06 = leftQty06;
    }

    @Basic
    @Column(name = "DebitQty07")
    public Double getDebitQty07() {
        return debitQty07;
    }

    public void setDebitQty07(Double debitQty07) {
        this.debitQty07 = debitQty07;
    }

    @Basic
    @Column(name = "CreditQty07")
    public Double getCreditQty07() {
        return creditQty07;
    }

    public void setCreditQty07(Double creditQty07) {
        this.creditQty07 = creditQty07;
    }

    @Basic
    @Column(name = "LeftQty07")
    public Double getLeftQty07() {
        return leftQty07;
    }

    public void setLeftQty07(Double leftQty07) {
        this.leftQty07 = leftQty07;
    }

    @Basic
    @Column(name = "DebitQty08")
    public Double getDebitQty08() {
        return debitQty08;
    }

    public void setDebitQty08(Double debitQty08) {
        this.debitQty08 = debitQty08;
    }

    @Basic
    @Column(name = "CreditQty08")
    public Double getCreditQty08() {
        return creditQty08;
    }

    public void setCreditQty08(Double creditQty08) {
        this.creditQty08 = creditQty08;
    }

    @Basic
    @Column(name = "LeftQty08")
    public Double getLeftQty08() {
        return leftQty08;
    }

    public void setLeftQty08(Double leftQty08) {
        this.leftQty08 = leftQty08;
    }

    @Basic
    @Column(name = "DebitQty09")
    public Double getDebitQty09() {
        return debitQty09;
    }

    public void setDebitQty09(Double debitQty09) {
        this.debitQty09 = debitQty09;
    }

    @Basic
    @Column(name = "CreditQty09")
    public Double getCreditQty09() {
        return creditQty09;
    }

    public void setCreditQty09(Double creditQty09) {
        this.creditQty09 = creditQty09;
    }

    @Basic
    @Column(name = "LeftQty09")
    public Double getLeftQty09() {
        return leftQty09;
    }

    public void setLeftQty09(Double leftQty09) {
        this.leftQty09 = leftQty09;
    }

    @Basic
    @Column(name = "DebitQty10")
    public Double getDebitQty10() {
        return debitQty10;
    }

    public void setDebitQty10(Double debitQty10) {
        this.debitQty10 = debitQty10;
    }

    @Basic
    @Column(name = "CreditQty10")
    public Double getCreditQty10() {
        return creditQty10;
    }

    public void setCreditQty10(Double creditQty10) {
        this.creditQty10 = creditQty10;
    }

    @Basic
    @Column(name = "LeftQty10")
    public Double getLeftQty10() {
        return leftQty10;
    }

    public void setLeftQty10(Double leftQty10) {
        this.leftQty10 = leftQty10;
    }

    @Basic
    @Column(name = "DebitQty11")
    public Double getDebitQty11() {
        return debitQty11;
    }

    public void setDebitQty11(Double debitQty11) {
        this.debitQty11 = debitQty11;
    }

    @Basic
    @Column(name = "CreditQty11")
    public Double getCreditQty11() {
        return creditQty11;
    }

    public void setCreditQty11(Double creditQty11) {
        this.creditQty11 = creditQty11;
    }

    @Basic
    @Column(name = "LeftQty11")
    public Double getLeftQty11() {
        return leftQty11;
    }

    public void setLeftQty11(Double leftQty11) {
        this.leftQty11 = leftQty11;
    }

    @Basic
    @Column(name = "DebitQty12")
    public Double getDebitQty12() {
        return debitQty12;
    }

    public void setDebitQty12(Double debitQty12) {
        this.debitQty12 = debitQty12;
    }

    @Basic
    @Column(name = "CreditQty12")
    public Double getCreditQty12() {
        return creditQty12;
    }

    public void setCreditQty12(Double creditQty12) {
        this.creditQty12 = creditQty12;
    }

    @Basic
    @Column(name = "LeftQty12")
    public Double getLeftQty12() {
        return leftQty12;
    }

    public void setLeftQty12(Double leftQty12) {
        this.leftQty12 = leftQty12;
    }

    @Basic
    @Column(name = "F_JS13")
    public Double getfJs13() {
        return fJs13;
    }

    public void setfJs13(Double fJs13) {
        this.fJs13 = fJs13;
    }

    @Basic
    @Column(name = "F_DS13")
    public Double getfDs13() {
        return fDs13;
    }

    public void setfDs13(Double fDs13) {
        this.fDs13 = fDs13;
    }

    @Basic
    @Column(name = "F_SL13")
    public Double getfSl13() {
        return fSl13;
    }

    public void setfSl13(Double fSl13) {
        this.fSl13 = fSl13;
    }

    @Basic
    @Column(name = "F_YSXL")
    public Double getfYsxl() {
        return fYsxl;
    }

    public void setfYsxl(Double fYsxl) {
        this.fYsxl = fYsxl;
    }

    @Basic
    @Column(name = "F_YSSX")
    public Double getfYssx() {
        return fYssx;
    }

    public void setfYssx(Double fYssx) {
        this.fYssx = fYssx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lshssl lshssl = (Lshssl) o;

        if (hsslAutoId != lshssl.hsslAutoId) return false;
        if (itemNo != null ? !itemNo.equals(lshssl.itemNo) : lshssl.itemNo != null) return false;
        if (spNo1 != null ? !spNo1.equals(lshssl.spNo1) : lshssl.spNo1 != null) return false;
        if (spNo2 != null ? !spNo2.equals(lshssl.spNo2) : lshssl.spNo2 != null) return false;
        if (budQty != null ? !budQty.equals(lshssl.budQty) : lshssl.budQty != null) return false;
        if (leftQty != null ? !leftQty.equals(lshssl.leftQty) : lshssl.leftQty != null) return false;
        if (unitPrice != null ? !unitPrice.equals(lshssl.unitPrice) : lshssl.unitPrice != null) return false;
        if (debitQty != null ? !debitQty.equals(lshssl.debitQty) : lshssl.debitQty != null) return false;
        if (creditQty != null ? !creditQty.equals(lshssl.creditQty) : lshssl.creditQty != null) return false;
        if (supQty != null ? !supQty.equals(lshssl.supQty) : lshssl.supQty != null) return false;
        if (debitQtySup != null ? !debitQtySup.equals(lshssl.debitQtySup) : lshssl.debitQtySup != null) return false;
        if (creditQtySup != null ? !creditQtySup.equals(lshssl.creditQtySup) : lshssl.creditQtySup != null)
            return false;
        if (debitQtyAcm != null ? !debitQtyAcm.equals(lshssl.debitQtyAcm) : lshssl.debitQtyAcm != null) return false;
        if (creditQtyAcm != null ? !creditQtyAcm.equals(lshssl.creditQtyAcm) : lshssl.creditQtyAcm != null)
            return false;
        if (debitQty01 != null ? !debitQty01.equals(lshssl.debitQty01) : lshssl.debitQty01 != null) return false;
        if (creditQty01 != null ? !creditQty01.equals(lshssl.creditQty01) : lshssl.creditQty01 != null) return false;
        if (leftQty01 != null ? !leftQty01.equals(lshssl.leftQty01) : lshssl.leftQty01 != null) return false;
        if (debitQty02 != null ? !debitQty02.equals(lshssl.debitQty02) : lshssl.debitQty02 != null) return false;
        if (creditQty02 != null ? !creditQty02.equals(lshssl.creditQty02) : lshssl.creditQty02 != null) return false;
        if (leftQty02 != null ? !leftQty02.equals(lshssl.leftQty02) : lshssl.leftQty02 != null) return false;
        if (debitQty03 != null ? !debitQty03.equals(lshssl.debitQty03) : lshssl.debitQty03 != null) return false;
        if (creditQty03 != null ? !creditQty03.equals(lshssl.creditQty03) : lshssl.creditQty03 != null) return false;
        if (leftQty03 != null ? !leftQty03.equals(lshssl.leftQty03) : lshssl.leftQty03 != null) return false;
        if (debitQty04 != null ? !debitQty04.equals(lshssl.debitQty04) : lshssl.debitQty04 != null) return false;
        if (creditQty04 != null ? !creditQty04.equals(lshssl.creditQty04) : lshssl.creditQty04 != null) return false;
        if (leftQty04 != null ? !leftQty04.equals(lshssl.leftQty04) : lshssl.leftQty04 != null) return false;
        if (debitQty05 != null ? !debitQty05.equals(lshssl.debitQty05) : lshssl.debitQty05 != null) return false;
        if (creditQty05 != null ? !creditQty05.equals(lshssl.creditQty05) : lshssl.creditQty05 != null) return false;
        if (leftQty05 != null ? !leftQty05.equals(lshssl.leftQty05) : lshssl.leftQty05 != null) return false;
        if (debitQty06 != null ? !debitQty06.equals(lshssl.debitQty06) : lshssl.debitQty06 != null) return false;
        if (creditQty06 != null ? !creditQty06.equals(lshssl.creditQty06) : lshssl.creditQty06 != null) return false;
        if (leftQty06 != null ? !leftQty06.equals(lshssl.leftQty06) : lshssl.leftQty06 != null) return false;
        if (debitQty07 != null ? !debitQty07.equals(lshssl.debitQty07) : lshssl.debitQty07 != null) return false;
        if (creditQty07 != null ? !creditQty07.equals(lshssl.creditQty07) : lshssl.creditQty07 != null) return false;
        if (leftQty07 != null ? !leftQty07.equals(lshssl.leftQty07) : lshssl.leftQty07 != null) return false;
        if (debitQty08 != null ? !debitQty08.equals(lshssl.debitQty08) : lshssl.debitQty08 != null) return false;
        if (creditQty08 != null ? !creditQty08.equals(lshssl.creditQty08) : lshssl.creditQty08 != null) return false;
        if (leftQty08 != null ? !leftQty08.equals(lshssl.leftQty08) : lshssl.leftQty08 != null) return false;
        if (debitQty09 != null ? !debitQty09.equals(lshssl.debitQty09) : lshssl.debitQty09 != null) return false;
        if (creditQty09 != null ? !creditQty09.equals(lshssl.creditQty09) : lshssl.creditQty09 != null) return false;
        if (leftQty09 != null ? !leftQty09.equals(lshssl.leftQty09) : lshssl.leftQty09 != null) return false;
        if (debitQty10 != null ? !debitQty10.equals(lshssl.debitQty10) : lshssl.debitQty10 != null) return false;
        if (creditQty10 != null ? !creditQty10.equals(lshssl.creditQty10) : lshssl.creditQty10 != null) return false;
        if (leftQty10 != null ? !leftQty10.equals(lshssl.leftQty10) : lshssl.leftQty10 != null) return false;
        if (debitQty11 != null ? !debitQty11.equals(lshssl.debitQty11) : lshssl.debitQty11 != null) return false;
        if (creditQty11 != null ? !creditQty11.equals(lshssl.creditQty11) : lshssl.creditQty11 != null) return false;
        if (leftQty11 != null ? !leftQty11.equals(lshssl.leftQty11) : lshssl.leftQty11 != null) return false;
        if (debitQty12 != null ? !debitQty12.equals(lshssl.debitQty12) : lshssl.debitQty12 != null) return false;
        if (creditQty12 != null ? !creditQty12.equals(lshssl.creditQty12) : lshssl.creditQty12 != null) return false;
        if (leftQty12 != null ? !leftQty12.equals(lshssl.leftQty12) : lshssl.leftQty12 != null) return false;
        if (fJs13 != null ? !fJs13.equals(lshssl.fJs13) : lshssl.fJs13 != null) return false;
        if (fDs13 != null ? !fDs13.equals(lshssl.fDs13) : lshssl.fDs13 != null) return false;
        if (fSl13 != null ? !fSl13.equals(lshssl.fSl13) : lshssl.fSl13 != null) return false;
        if (fYsxl != null ? !fYsxl.equals(lshssl.fYsxl) : lshssl.fYsxl != null) return false;
        if (fYssx != null ? !fYssx.equals(lshssl.fYssx) : lshssl.fYssx != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = hsslAutoId;
        result = 31 * result + (itemNo != null ? itemNo.hashCode() : 0);
        result = 31 * result + (spNo1 != null ? spNo1.hashCode() : 0);
        result = 31 * result + (spNo2 != null ? spNo2.hashCode() : 0);
        result = 31 * result + (budQty != null ? budQty.hashCode() : 0);
        result = 31 * result + (leftQty != null ? leftQty.hashCode() : 0);
        result = 31 * result + (unitPrice != null ? unitPrice.hashCode() : 0);
        result = 31 * result + (debitQty != null ? debitQty.hashCode() : 0);
        result = 31 * result + (creditQty != null ? creditQty.hashCode() : 0);
        result = 31 * result + (supQty != null ? supQty.hashCode() : 0);
        result = 31 * result + (debitQtySup != null ? debitQtySup.hashCode() : 0);
        result = 31 * result + (creditQtySup != null ? creditQtySup.hashCode() : 0);
        result = 31 * result + (debitQtyAcm != null ? debitQtyAcm.hashCode() : 0);
        result = 31 * result + (creditQtyAcm != null ? creditQtyAcm.hashCode() : 0);
        result = 31 * result + (debitQty01 != null ? debitQty01.hashCode() : 0);
        result = 31 * result + (creditQty01 != null ? creditQty01.hashCode() : 0);
        result = 31 * result + (leftQty01 != null ? leftQty01.hashCode() : 0);
        result = 31 * result + (debitQty02 != null ? debitQty02.hashCode() : 0);
        result = 31 * result + (creditQty02 != null ? creditQty02.hashCode() : 0);
        result = 31 * result + (leftQty02 != null ? leftQty02.hashCode() : 0);
        result = 31 * result + (debitQty03 != null ? debitQty03.hashCode() : 0);
        result = 31 * result + (creditQty03 != null ? creditQty03.hashCode() : 0);
        result = 31 * result + (leftQty03 != null ? leftQty03.hashCode() : 0);
        result = 31 * result + (debitQty04 != null ? debitQty04.hashCode() : 0);
        result = 31 * result + (creditQty04 != null ? creditQty04.hashCode() : 0);
        result = 31 * result + (leftQty04 != null ? leftQty04.hashCode() : 0);
        result = 31 * result + (debitQty05 != null ? debitQty05.hashCode() : 0);
        result = 31 * result + (creditQty05 != null ? creditQty05.hashCode() : 0);
        result = 31 * result + (leftQty05 != null ? leftQty05.hashCode() : 0);
        result = 31 * result + (debitQty06 != null ? debitQty06.hashCode() : 0);
        result = 31 * result + (creditQty06 != null ? creditQty06.hashCode() : 0);
        result = 31 * result + (leftQty06 != null ? leftQty06.hashCode() : 0);
        result = 31 * result + (debitQty07 != null ? debitQty07.hashCode() : 0);
        result = 31 * result + (creditQty07 != null ? creditQty07.hashCode() : 0);
        result = 31 * result + (leftQty07 != null ? leftQty07.hashCode() : 0);
        result = 31 * result + (debitQty08 != null ? debitQty08.hashCode() : 0);
        result = 31 * result + (creditQty08 != null ? creditQty08.hashCode() : 0);
        result = 31 * result + (leftQty08 != null ? leftQty08.hashCode() : 0);
        result = 31 * result + (debitQty09 != null ? debitQty09.hashCode() : 0);
        result = 31 * result + (creditQty09 != null ? creditQty09.hashCode() : 0);
        result = 31 * result + (leftQty09 != null ? leftQty09.hashCode() : 0);
        result = 31 * result + (debitQty10 != null ? debitQty10.hashCode() : 0);
        result = 31 * result + (creditQty10 != null ? creditQty10.hashCode() : 0);
        result = 31 * result + (leftQty10 != null ? leftQty10.hashCode() : 0);
        result = 31 * result + (debitQty11 != null ? debitQty11.hashCode() : 0);
        result = 31 * result + (creditQty11 != null ? creditQty11.hashCode() : 0);
        result = 31 * result + (leftQty11 != null ? leftQty11.hashCode() : 0);
        result = 31 * result + (debitQty12 != null ? debitQty12.hashCode() : 0);
        result = 31 * result + (creditQty12 != null ? creditQty12.hashCode() : 0);
        result = 31 * result + (leftQty12 != null ? leftQty12.hashCode() : 0);
        result = 31 * result + (fJs13 != null ? fJs13.hashCode() : 0);
        result = 31 * result + (fDs13 != null ? fDs13.hashCode() : 0);
        result = 31 * result + (fSl13 != null ? fSl13.hashCode() : 0);
        result = 31 * result + (fYsxl != null ? fYsxl.hashCode() : 0);
        result = 31 * result + (fYssx != null ? fYssx.hashCode() : 0);
        return result;
    }
}
