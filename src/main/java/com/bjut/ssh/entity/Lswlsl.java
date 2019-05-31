package com.bjut.ssh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Title: Lswlsl
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/11 10:01
 * @Version: 1.0
 */
@Entity
public class Lswlsl {
    private int wlslAutoId;
    private String itemNo;
    private String companyNo;
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
    private Double leftQty08;
    private Double creditQty08;
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
    private Double fDs13;
    private Double fJf13;
    private Double fSl13;

    @Id
    @Column(name = "WLSL_AutoId")
    public int getWlslAutoId() {
        return wlslAutoId;
    }

    public void setWlslAutoId(int wlslAutoId) {
        this.wlslAutoId = wlslAutoId;
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
    @Column(name = "CompanyNo")
    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
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
    @Column(name = "LeftQty08")
    public Double getLeftQty08() {
        return leftQty08;
    }

    public void setLeftQty08(Double leftQty08) {
        this.leftQty08 = leftQty08;
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
    @Column(name = "F_DS13")
    public Double getfDs13() {
        return fDs13;
    }

    public void setfDs13(Double fDs13) {
        this.fDs13 = fDs13;
    }

    @Basic
    @Column(name = "F_JF13")
    public Double getfJf13() {
        return fJf13;
    }

    public void setfJf13(Double fJf13) {
        this.fJf13 = fJf13;
    }

    @Basic
    @Column(name = "F_SL13")
    public Double getfSl13() {
        return fSl13;
    }

    public void setfSl13(Double fSl13) {
        this.fSl13 = fSl13;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lswlsl lswlsl = (Lswlsl) o;

        if (wlslAutoId != lswlsl.wlslAutoId) return false;
        if (itemNo != null ? !itemNo.equals(lswlsl.itemNo) : lswlsl.itemNo != null) return false;
        if (companyNo != null ? !companyNo.equals(lswlsl.companyNo) : lswlsl.companyNo != null) return false;
        if (leftQty != null ? !leftQty.equals(lswlsl.leftQty) : lswlsl.leftQty != null) return false;
        if (unitPrice != null ? !unitPrice.equals(lswlsl.unitPrice) : lswlsl.unitPrice != null) return false;
        if (debitQty != null ? !debitQty.equals(lswlsl.debitQty) : lswlsl.debitQty != null) return false;
        if (creditQty != null ? !creditQty.equals(lswlsl.creditQty) : lswlsl.creditQty != null) return false;
        if (supQty != null ? !supQty.equals(lswlsl.supQty) : lswlsl.supQty != null) return false;
        if (debitQtySup != null ? !debitQtySup.equals(lswlsl.debitQtySup) : lswlsl.debitQtySup != null) return false;
        if (creditQtySup != null ? !creditQtySup.equals(lswlsl.creditQtySup) : lswlsl.creditQtySup != null)
            return false;
        if (debitQtyAcm != null ? !debitQtyAcm.equals(lswlsl.debitQtyAcm) : lswlsl.debitQtyAcm != null) return false;
        if (creditQtyAcm != null ? !creditQtyAcm.equals(lswlsl.creditQtyAcm) : lswlsl.creditQtyAcm != null)
            return false;
        if (debitQty01 != null ? !debitQty01.equals(lswlsl.debitQty01) : lswlsl.debitQty01 != null) return false;
        if (creditQty01 != null ? !creditQty01.equals(lswlsl.creditQty01) : lswlsl.creditQty01 != null) return false;
        if (leftQty01 != null ? !leftQty01.equals(lswlsl.leftQty01) : lswlsl.leftQty01 != null) return false;
        if (debitQty02 != null ? !debitQty02.equals(lswlsl.debitQty02) : lswlsl.debitQty02 != null) return false;
        if (creditQty02 != null ? !creditQty02.equals(lswlsl.creditQty02) : lswlsl.creditQty02 != null) return false;
        if (leftQty02 != null ? !leftQty02.equals(lswlsl.leftQty02) : lswlsl.leftQty02 != null) return false;
        if (debitQty03 != null ? !debitQty03.equals(lswlsl.debitQty03) : lswlsl.debitQty03 != null) return false;
        if (creditQty03 != null ? !creditQty03.equals(lswlsl.creditQty03) : lswlsl.creditQty03 != null) return false;
        if (leftQty03 != null ? !leftQty03.equals(lswlsl.leftQty03) : lswlsl.leftQty03 != null) return false;
        if (debitQty04 != null ? !debitQty04.equals(lswlsl.debitQty04) : lswlsl.debitQty04 != null) return false;
        if (creditQty04 != null ? !creditQty04.equals(lswlsl.creditQty04) : lswlsl.creditQty04 != null) return false;
        if (leftQty04 != null ? !leftQty04.equals(lswlsl.leftQty04) : lswlsl.leftQty04 != null) return false;
        if (debitQty05 != null ? !debitQty05.equals(lswlsl.debitQty05) : lswlsl.debitQty05 != null) return false;
        if (creditQty05 != null ? !creditQty05.equals(lswlsl.creditQty05) : lswlsl.creditQty05 != null) return false;
        if (leftQty05 != null ? !leftQty05.equals(lswlsl.leftQty05) : lswlsl.leftQty05 != null) return false;
        if (debitQty06 != null ? !debitQty06.equals(lswlsl.debitQty06) : lswlsl.debitQty06 != null) return false;
        if (creditQty06 != null ? !creditQty06.equals(lswlsl.creditQty06) : lswlsl.creditQty06 != null) return false;
        if (leftQty06 != null ? !leftQty06.equals(lswlsl.leftQty06) : lswlsl.leftQty06 != null) return false;
        if (debitQty07 != null ? !debitQty07.equals(lswlsl.debitQty07) : lswlsl.debitQty07 != null) return false;
        if (creditQty07 != null ? !creditQty07.equals(lswlsl.creditQty07) : lswlsl.creditQty07 != null) return false;
        if (leftQty07 != null ? !leftQty07.equals(lswlsl.leftQty07) : lswlsl.leftQty07 != null) return false;
        if (debitQty08 != null ? !debitQty08.equals(lswlsl.debitQty08) : lswlsl.debitQty08 != null) return false;
        if (leftQty08 != null ? !leftQty08.equals(lswlsl.leftQty08) : lswlsl.leftQty08 != null) return false;
        if (creditQty08 != null ? !creditQty08.equals(lswlsl.creditQty08) : lswlsl.creditQty08 != null) return false;
        if (debitQty09 != null ? !debitQty09.equals(lswlsl.debitQty09) : lswlsl.debitQty09 != null) return false;
        if (creditQty09 != null ? !creditQty09.equals(lswlsl.creditQty09) : lswlsl.creditQty09 != null) return false;
        if (leftQty09 != null ? !leftQty09.equals(lswlsl.leftQty09) : lswlsl.leftQty09 != null) return false;
        if (debitQty10 != null ? !debitQty10.equals(lswlsl.debitQty10) : lswlsl.debitQty10 != null) return false;
        if (creditQty10 != null ? !creditQty10.equals(lswlsl.creditQty10) : lswlsl.creditQty10 != null) return false;
        if (leftQty10 != null ? !leftQty10.equals(lswlsl.leftQty10) : lswlsl.leftQty10 != null) return false;
        if (debitQty11 != null ? !debitQty11.equals(lswlsl.debitQty11) : lswlsl.debitQty11 != null) return false;
        if (creditQty11 != null ? !creditQty11.equals(lswlsl.creditQty11) : lswlsl.creditQty11 != null) return false;
        if (leftQty11 != null ? !leftQty11.equals(lswlsl.leftQty11) : lswlsl.leftQty11 != null) return false;
        if (debitQty12 != null ? !debitQty12.equals(lswlsl.debitQty12) : lswlsl.debitQty12 != null) return false;
        if (creditQty12 != null ? !creditQty12.equals(lswlsl.creditQty12) : lswlsl.creditQty12 != null) return false;
        if (leftQty12 != null ? !leftQty12.equals(lswlsl.leftQty12) : lswlsl.leftQty12 != null) return false;
        if (fDs13 != null ? !fDs13.equals(lswlsl.fDs13) : lswlsl.fDs13 != null) return false;
        if (fJf13 != null ? !fJf13.equals(lswlsl.fJf13) : lswlsl.fJf13 != null) return false;
        if (fSl13 != null ? !fSl13.equals(lswlsl.fSl13) : lswlsl.fSl13 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = wlslAutoId;
        result = 31 * result + (itemNo != null ? itemNo.hashCode() : 0);
        result = 31 * result + (companyNo != null ? companyNo.hashCode() : 0);
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
        result = 31 * result + (leftQty08 != null ? leftQty08.hashCode() : 0);
        result = 31 * result + (creditQty08 != null ? creditQty08.hashCode() : 0);
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
        result = 31 * result + (fDs13 != null ? fDs13.hashCode() : 0);
        result = 31 * result + (fJf13 != null ? fJf13.hashCode() : 0);
        result = 31 * result + (fSl13 != null ? fSl13.hashCode() : 0);
        return result;
    }
}
