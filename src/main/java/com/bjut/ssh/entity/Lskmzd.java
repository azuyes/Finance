package com.bjut.ssh.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Lskmzd {
    private String item;
    private String itemNo;
    private String itemName;
    private String ele;
    private String departmentId;
    private String accType;
    private String spType;
    private Byte exchang;
    private String supAcc1;
    private String supAcc2;
    private Double budgetMoney;
    private Byte journal;
    private Byte finLevel;
    private Double balance;
    private Double debitMoney;
    private Double creditMoney;
    private Double supMoney;
    private Double debitMoneySup;
    private Double creditMoneySup;
    private Double debitMoneyAcm;
    private Double creditMoneyAcm;
    private Double debitMoney01;
    private Double creditMoney01;
    private Double balance01;
    private Double debitMoney02;
    private Double creditMoney02;
    private Double balance02;
    private Double debitMoney03;
    private Double creditMoney03;
    private Double balance03;
    private Double debitMoney04;
    private Double creditMoney04;
    private Double balance04;
    private Double debitMoney05;
    private Double creditMoney05;
    private Double balance05;
    private Double debitMoney06;
    private Double creditMoney06;
    private Double balance06;
    private Double debitMoney07;
    private Double creditMoney07;
    private Double balance07;
    private Double debitMoney08;
    private Double creditMoney08;
    private Double balance08;
    private Double debitMoney09;
    private Double creditMoney09;
    private Double balance09;
    private Double debitMoney10;
    private Double creditMoney10;
    private Double balance10;
    private Double debitMoney11;
    private Double creditMoney11;
    private Double balance11;
    private Double debitMoney12;
    private Double creditMoney12;
    private Double balance12;
    private String fKzjm;
    private Byte fKjqj;
    private Byte fDz;
    private Byte fDzok;
    private Byte fPzsy;
    private String fDykm;
    private String fPjmc;
    private String fSjly;
    private Double fYssx;
    private Double fYsxx;
    private Double fDf13;
    private Double fJf13;
    private Double fYe13;

    @Basic
    @Column(name = "Item")
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Id
    @Column(name = "ItemNo")
    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    @Basic
    @Column(name = "ItemName")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Basic
    @Column(name = "Ele")
    public String getEle() {
        return ele;
    }

    public void setEle(String ele) {
        this.ele = ele;
    }

    @Basic
    @Column(name = "DepartmentID")
    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
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
    @Column(name = "SpType")
    public String getSpType() {
        return spType;
    }

    public void setSpType(String spType) {
        this.spType = spType;
    }

    @Basic
    @Column(name = "Exchang")
    public Byte getExchang() {
        return exchang;
    }

    public void setExchang(Byte exchang) {
        this.exchang = exchang;
    }

    @Basic
    @Column(name = "SupAcc1")
    public String getSupAcc1() {
        return supAcc1;
    }

    public void setSupAcc1(String supAcc1) {
        this.supAcc1 = supAcc1;
    }

    @Basic
    @Column(name = "SupAcc2")
    public String getSupAcc2() {
        return supAcc2;
    }

    public void setSupAcc2(String supAcc2) {
        this.supAcc2 = supAcc2;
    }

    @Basic
    @Column(name = "BudgetMoney")
    public Double getBudgetMoney() {
        return budgetMoney;
    }

    public void setBudgetMoney(Double budgetMoney) {
        this.budgetMoney = budgetMoney;
    }

    @Basic
    @Column(name = "Journal")
    public Byte getJournal() {
        return journal;
    }

    public void setJournal(Byte journal) {
        this.journal = journal;
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
    @Column(name = "Balance")
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Basic
    @Column(name = "DebitMoney")
    public Double getDebitMoney() {
        return debitMoney;
    }

    public void setDebitMoney(Double debitMoney) {
        this.debitMoney = debitMoney;
    }

    @Basic
    @Column(name = "CreditMoney")
    public Double getCreditMoney() {
        return creditMoney;
    }

    public void setCreditMoney(Double creditMoney) {
        this.creditMoney = creditMoney;
    }

    @Basic
    @Column(name = "SupMoney")
    public Double getSupMoney() {
        return supMoney;
    }

    public void setSupMoney(Double supMoney) {
        this.supMoney = supMoney;
    }

    @Basic
    @Column(name = "DebitMoneySup")
    public Double getDebitMoneySup() {
        return debitMoneySup;
    }

    public void setDebitMoneySup(Double debitMoneySup) {
        this.debitMoneySup = debitMoneySup;
    }

    @Basic
    @Column(name = "CreditMoneySup")
    public Double getCreditMoneySup() {
        return creditMoneySup;
    }

    public void setCreditMoneySup(Double creditMoneySup) {
        this.creditMoneySup = creditMoneySup;
    }

    @Basic
    @Column(name = "DebitMoneyAcm")
    public Double getDebitMoneyAcm() {
        return debitMoneyAcm;
    }

    public void setDebitMoneyAcm(Double debitMoneyAcm) {
        this.debitMoneyAcm = debitMoneyAcm;
    }

    @Basic
    @Column(name = "CreditMoneyAcm")
    public Double getCreditMoneyAcm() {
        return creditMoneyAcm;
    }

    public void setCreditMoneyAcm(Double creditMoneyAcm) {
        this.creditMoneyAcm = creditMoneyAcm;
    }

    @Basic
    @Column(name = "DebitMoney01")
    public Double getDebitMoney01() {
        return debitMoney01;
    }

    public void setDebitMoney01(Double debitMoney01) {
        this.debitMoney01 = debitMoney01;
    }

    @Basic
    @Column(name = "CreditMoney01")
    public Double getCreditMoney01() {
        return creditMoney01;
    }

    public void setCreditMoney01(Double creditMoney01) {
        this.creditMoney01 = creditMoney01;
    }

    @Basic
    @Column(name = "Balance01")
    public Double getBalance01() {
        return balance01;
    }

    public void setBalance01(Double balance01) {
        this.balance01 = balance01;
    }

    @Basic
    @Column(name = "DebitMoney02")
    public Double getDebitMoney02() {
        return debitMoney02;
    }

    public void setDebitMoney02(Double debitMoney02) {
        this.debitMoney02 = debitMoney02;
    }

    @Basic
    @Column(name = "CreditMoney02")
    public Double getCreditMoney02() {
        return creditMoney02;
    }

    public void setCreditMoney02(Double creditMoney02) {
        this.creditMoney02 = creditMoney02;
    }

    @Basic
    @Column(name = "Balance02")
    public Double getBalance02() {
        return balance02;
    }

    public void setBalance02(Double balance02) {
        this.balance02 = balance02;
    }

    @Basic
    @Column(name = "DebitMoney03")
    public Double getDebitMoney03() {
        return debitMoney03;
    }

    public void setDebitMoney03(Double debitMoney03) {
        this.debitMoney03 = debitMoney03;
    }

    @Basic
    @Column(name = "CreditMoney03")
    public Double getCreditMoney03() {
        return creditMoney03;
    }

    public void setCreditMoney03(Double creditMoney03) {
        this.creditMoney03 = creditMoney03;
    }

    @Basic
    @Column(name = "Balance03")
    public Double getBalance03() {
        return balance03;
    }

    public void setBalance03(Double balance03) {
        this.balance03 = balance03;
    }

    @Basic
    @Column(name = "DebitMoney04")
    public Double getDebitMoney04() {
        return debitMoney04;
    }

    public void setDebitMoney04(Double debitMoney04) {
        this.debitMoney04 = debitMoney04;
    }

    @Basic
    @Column(name = "CreditMoney04")
    public Double getCreditMoney04() {
        return creditMoney04;
    }

    public void setCreditMoney04(Double creditMoney04) {
        this.creditMoney04 = creditMoney04;
    }

    @Basic
    @Column(name = "Balance04")
    public Double getBalance04() {
        return balance04;
    }

    public void setBalance04(Double balance04) {
        this.balance04 = balance04;
    }

    @Basic
    @Column(name = "DebitMoney05")
    public Double getDebitMoney05() {
        return debitMoney05;
    }

    public void setDebitMoney05(Double debitMoney05) {
        this.debitMoney05 = debitMoney05;
    }

    @Basic
    @Column(name = "CreditMoney05")
    public Double getCreditMoney05() {
        return creditMoney05;
    }

    public void setCreditMoney05(Double creditMoney05) {
        this.creditMoney05 = creditMoney05;
    }

    @Basic
    @Column(name = "Balance05")
    public Double getBalance05() {
        return balance05;
    }

    public void setBalance05(Double balance05) {
        this.balance05 = balance05;
    }

    @Basic
    @Column(name = "DebitMoney06")
    public Double getDebitMoney06() {
        return debitMoney06;
    }

    public void setDebitMoney06(Double debitMoney06) {
        this.debitMoney06 = debitMoney06;
    }

    @Basic
    @Column(name = "CreditMoney06")
    public Double getCreditMoney06() {
        return creditMoney06;
    }

    public void setCreditMoney06(Double creditMoney06) {
        this.creditMoney06 = creditMoney06;
    }

    @Basic
    @Column(name = "Balance06")
    public Double getBalance06() {
        return balance06;
    }

    public void setBalance06(Double balance06) {
        this.balance06 = balance06;
    }

    @Basic
    @Column(name = "DebitMoney07")
    public Double getDebitMoney07() {
        return debitMoney07;
    }

    public void setDebitMoney07(Double debitMoney07) {
        this.debitMoney07 = debitMoney07;
    }

    @Basic
    @Column(name = "CreditMoney07")
    public Double getCreditMoney07() {
        return creditMoney07;
    }

    public void setCreditMoney07(Double creditMoney07) {
        this.creditMoney07 = creditMoney07;
    }

    @Basic
    @Column(name = "Balance07")
    public Double getBalance07() {
        return balance07;
    }

    public void setBalance07(Double balance07) {
        this.balance07 = balance07;
    }

    @Basic
    @Column(name = "DebitMoney08")
    public Double getDebitMoney08() {
        return debitMoney08;
    }

    public void setDebitMoney08(Double debitMoney08) {
        this.debitMoney08 = debitMoney08;
    }

    @Basic
    @Column(name = "CreditMoney08")
    public Double getCreditMoney08() {
        return creditMoney08;
    }

    public void setCreditMoney08(Double creditMoney08) {
        this.creditMoney08 = creditMoney08;
    }

    @Basic
    @Column(name = "Balance08")
    public Double getBalance08() {
        return balance08;
    }

    public void setBalance08(Double balance08) {
        this.balance08 = balance08;
    }

    @Basic
    @Column(name = "DebitMoney09")
    public Double getDebitMoney09() {
        return debitMoney09;
    }

    public void setDebitMoney09(Double debitMoney09) {
        this.debitMoney09 = debitMoney09;
    }

    @Basic
    @Column(name = "CreditMoney09")
    public Double getCreditMoney09() {
        return creditMoney09;
    }

    public void setCreditMoney09(Double creditMoney09) {
        this.creditMoney09 = creditMoney09;
    }

    @Basic
    @Column(name = "Balance09")
    public Double getBalance09() {
        return balance09;
    }

    public void setBalance09(Double balance09) {
        this.balance09 = balance09;
    }

    @Basic
    @Column(name = "DebitMoney10")
    public Double getDebitMoney10() {
        return debitMoney10;
    }

    public void setDebitMoney10(Double debitMoney10) {
        this.debitMoney10 = debitMoney10;
    }

    @Basic
    @Column(name = "CreditMoney10")
    public Double getCreditMoney10() {
        return creditMoney10;
    }

    public void setCreditMoney10(Double creditMoney10) {
        this.creditMoney10 = creditMoney10;
    }

    @Basic
    @Column(name = "Balance10")
    public Double getBalance10() {
        return balance10;
    }

    public void setBalance10(Double balance10) {
        this.balance10 = balance10;
    }

    @Basic
    @Column(name = "DebitMoney11")
    public Double getDebitMoney11() {
        return debitMoney11;
    }

    public void setDebitMoney11(Double debitMoney11) {
        this.debitMoney11 = debitMoney11;
    }

    @Basic
    @Column(name = "CreditMoney11")
    public Double getCreditMoney11() {
        return creditMoney11;
    }

    public void setCreditMoney11(Double creditMoney11) {
        this.creditMoney11 = creditMoney11;
    }

    @Basic
    @Column(name = "Balance11")
    public Double getBalance11() {
        return balance11;
    }

    public void setBalance11(Double balance11) {
        this.balance11 = balance11;
    }

    @Basic
    @Column(name = "DebitMoney12")
    public Double getDebitMoney12() {
        return debitMoney12;
    }

    public void setDebitMoney12(Double debitMoney12) {
        this.debitMoney12 = debitMoney12;
    }

    @Basic
    @Column(name = "CreditMoney12")
    public Double getCreditMoney12() {
        return creditMoney12;
    }

    public void setCreditMoney12(Double creditMoney12) {
        this.creditMoney12 = creditMoney12;
    }

    @Basic
    @Column(name = "Balance12")
    public Double getBalance12() {
        return balance12;
    }

    public void setBalance12(Double balance12) {
        this.balance12 = balance12;
    }

    @Basic
    @Column(name = "F_KZJM")
    public String getfKzjm() {
        return fKzjm;
    }

    public void setfKzjm(String fKzjm) {
        this.fKzjm = fKzjm;
    }

    @Basic
    @Column(name = "F_KJQJ")
    public Byte getfKjqj() {
        return fKjqj;
    }

    public void setfKjqj(Byte fKjqj) {
        this.fKjqj = fKjqj;
    }

    @Basic
    @Column(name = "F_DZ")
    public Byte getfDz() {
        return fDz;
    }

    public void setfDz(Byte fDz) {
        this.fDz = fDz;
    }

    @Basic
    @Column(name = "F_DZOK")
    public Byte getfDzok() {
        return fDzok;
    }

    public void setfDzok(Byte fDzok) {
        this.fDzok = fDzok;
    }

    @Basic
    @Column(name = "F_PZSY")
    public Byte getfPzsy() {
        return fPzsy;
    }

    public void setfPzsy(Byte fPzsy) {
        this.fPzsy = fPzsy;
    }

    @Basic
    @Column(name = "F_DYKM")
    public String getfDykm() {
        return fDykm;
    }

    public void setfDykm(String fDykm) {
        this.fDykm = fDykm;
    }

    @Basic
    @Column(name = "F_PJMC")
    public String getfPjmc() {
        return fPjmc;
    }

    public void setfPjmc(String fPjmc) {
        this.fPjmc = fPjmc;
    }

    @Basic
    @Column(name = "F_SJLY")
    public String getfSjly() {
        return fSjly;
    }

    public void setfSjly(String fSjly) {
        this.fSjly = fSjly;
    }

    @Basic
    @Column(name = "F_YSSX")
    public Double getfYssx() {
        return fYssx;
    }

    public void setfYssx(Double fYssx) {
        this.fYssx = fYssx;
    }

    @Basic
    @Column(name = "F_YSXX")
    public Double getfYsxx() {
        return fYsxx;
    }

    public void setfYsxx(Double fYsxx) {
        this.fYsxx = fYsxx;
    }

    @Basic
    @Column(name = "F_DF13")
    public Double getfDf13() {
        return fDf13;
    }

    public void setfDf13(Double fDf13) {
        this.fDf13 = fDf13;
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
    @Column(name = "F_YE13")
    public Double getfYe13() {
        return fYe13;
    }

    public void setfYe13(Double fYe13) {
        this.fYe13 = fYe13;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lskmzd lskmzd = (Lskmzd) o;

        if (item != null ? !item.equals(lskmzd.item) : lskmzd.item != null) return false;
        if (itemNo != null ? !itemNo.equals(lskmzd.itemNo) : lskmzd.itemNo != null) return false;
        if (itemName != null ? !itemName.equals(lskmzd.itemName) : lskmzd.itemName != null) return false;
        if (ele != null ? !ele.equals(lskmzd.ele) : lskmzd.ele != null) return false;
        if (departmentId != null ? !departmentId.equals(lskmzd.departmentId) : lskmzd.departmentId != null)
            return false;
        if (accType != null ? !accType.equals(lskmzd.accType) : lskmzd.accType != null) return false;
        if (spType != null ? !spType.equals(lskmzd.spType) : lskmzd.spType != null) return false;
        if (exchang != null ? !exchang.equals(lskmzd.exchang) : lskmzd.exchang != null) return false;
        if (supAcc1 != null ? !supAcc1.equals(lskmzd.supAcc1) : lskmzd.supAcc1 != null) return false;
        if (supAcc2 != null ? !supAcc2.equals(lskmzd.supAcc2) : lskmzd.supAcc2 != null) return false;
        if (budgetMoney != null ? !budgetMoney.equals(lskmzd.budgetMoney) : lskmzd.budgetMoney != null) return false;
        if (journal != null ? !journal.equals(lskmzd.journal) : lskmzd.journal != null) return false;
        if (finLevel != null ? !finLevel.equals(lskmzd.finLevel) : lskmzd.finLevel != null) return false;
        if (balance != null ? !balance.equals(lskmzd.balance) : lskmzd.balance != null) return false;
        if (debitMoney != null ? !debitMoney.equals(lskmzd.debitMoney) : lskmzd.debitMoney != null) return false;
        if (creditMoney != null ? !creditMoney.equals(lskmzd.creditMoney) : lskmzd.creditMoney != null) return false;
        if (supMoney != null ? !supMoney.equals(lskmzd.supMoney) : lskmzd.supMoney != null) return false;
        if (debitMoneySup != null ? !debitMoneySup.equals(lskmzd.debitMoneySup) : lskmzd.debitMoneySup != null)
            return false;
        if (creditMoneySup != null ? !creditMoneySup.equals(lskmzd.creditMoneySup) : lskmzd.creditMoneySup != null)
            return false;
        if (debitMoneyAcm != null ? !debitMoneyAcm.equals(lskmzd.debitMoneyAcm) : lskmzd.debitMoneyAcm != null)
            return false;
        if (creditMoneyAcm != null ? !creditMoneyAcm.equals(lskmzd.creditMoneyAcm) : lskmzd.creditMoneyAcm != null)
            return false;
        if (debitMoney01 != null ? !debitMoney01.equals(lskmzd.debitMoney01) : lskmzd.debitMoney01 != null)
            return false;
        if (creditMoney01 != null ? !creditMoney01.equals(lskmzd.creditMoney01) : lskmzd.creditMoney01 != null)
            return false;
        if (balance01 != null ? !balance01.equals(lskmzd.balance01) : lskmzd.balance01 != null) return false;
        if (debitMoney02 != null ? !debitMoney02.equals(lskmzd.debitMoney02) : lskmzd.debitMoney02 != null)
            return false;
        if (creditMoney02 != null ? !creditMoney02.equals(lskmzd.creditMoney02) : lskmzd.creditMoney02 != null)
            return false;
        if (balance02 != null ? !balance02.equals(lskmzd.balance02) : lskmzd.balance02 != null) return false;
        if (debitMoney03 != null ? !debitMoney03.equals(lskmzd.debitMoney03) : lskmzd.debitMoney03 != null)
            return false;
        if (creditMoney03 != null ? !creditMoney03.equals(lskmzd.creditMoney03) : lskmzd.creditMoney03 != null)
            return false;
        if (balance03 != null ? !balance03.equals(lskmzd.balance03) : lskmzd.balance03 != null) return false;
        if (debitMoney04 != null ? !debitMoney04.equals(lskmzd.debitMoney04) : lskmzd.debitMoney04 != null)
            return false;
        if (creditMoney04 != null ? !creditMoney04.equals(lskmzd.creditMoney04) : lskmzd.creditMoney04 != null)
            return false;
        if (balance04 != null ? !balance04.equals(lskmzd.balance04) : lskmzd.balance04 != null) return false;
        if (debitMoney05 != null ? !debitMoney05.equals(lskmzd.debitMoney05) : lskmzd.debitMoney05 != null)
            return false;
        if (creditMoney05 != null ? !creditMoney05.equals(lskmzd.creditMoney05) : lskmzd.creditMoney05 != null)
            return false;
        if (balance05 != null ? !balance05.equals(lskmzd.balance05) : lskmzd.balance05 != null) return false;
        if (debitMoney06 != null ? !debitMoney06.equals(lskmzd.debitMoney06) : lskmzd.debitMoney06 != null)
            return false;
        if (creditMoney06 != null ? !creditMoney06.equals(lskmzd.creditMoney06) : lskmzd.creditMoney06 != null)
            return false;
        if (balance06 != null ? !balance06.equals(lskmzd.balance06) : lskmzd.balance06 != null) return false;
        if (debitMoney07 != null ? !debitMoney07.equals(lskmzd.debitMoney07) : lskmzd.debitMoney07 != null)
            return false;
        if (creditMoney07 != null ? !creditMoney07.equals(lskmzd.creditMoney07) : lskmzd.creditMoney07 != null)
            return false;
        if (balance07 != null ? !balance07.equals(lskmzd.balance07) : lskmzd.balance07 != null) return false;
        if (debitMoney08 != null ? !debitMoney08.equals(lskmzd.debitMoney08) : lskmzd.debitMoney08 != null)
            return false;
        if (creditMoney08 != null ? !creditMoney08.equals(lskmzd.creditMoney08) : lskmzd.creditMoney08 != null)
            return false;
        if (balance08 != null ? !balance08.equals(lskmzd.balance08) : lskmzd.balance08 != null) return false;
        if (debitMoney09 != null ? !debitMoney09.equals(lskmzd.debitMoney09) : lskmzd.debitMoney09 != null)
            return false;
        if (creditMoney09 != null ? !creditMoney09.equals(lskmzd.creditMoney09) : lskmzd.creditMoney09 != null)
            return false;
        if (balance09 != null ? !balance09.equals(lskmzd.balance09) : lskmzd.balance09 != null) return false;
        if (debitMoney10 != null ? !debitMoney10.equals(lskmzd.debitMoney10) : lskmzd.debitMoney10 != null)
            return false;
        if (creditMoney10 != null ? !creditMoney10.equals(lskmzd.creditMoney10) : lskmzd.creditMoney10 != null)
            return false;
        if (balance10 != null ? !balance10.equals(lskmzd.balance10) : lskmzd.balance10 != null) return false;
        if (debitMoney11 != null ? !debitMoney11.equals(lskmzd.debitMoney11) : lskmzd.debitMoney11 != null)
            return false;
        if (creditMoney11 != null ? !creditMoney11.equals(lskmzd.creditMoney11) : lskmzd.creditMoney11 != null)
            return false;
        if (balance11 != null ? !balance11.equals(lskmzd.balance11) : lskmzd.balance11 != null) return false;
        if (debitMoney12 != null ? !debitMoney12.equals(lskmzd.debitMoney12) : lskmzd.debitMoney12 != null)
            return false;
        if (creditMoney12 != null ? !creditMoney12.equals(lskmzd.creditMoney12) : lskmzd.creditMoney12 != null)
            return false;
        if (balance12 != null ? !balance12.equals(lskmzd.balance12) : lskmzd.balance12 != null) return false;
        if (fKzjm != null ? !fKzjm.equals(lskmzd.fKzjm) : lskmzd.fKzjm != null) return false;
        if (fKjqj != null ? !fKjqj.equals(lskmzd.fKjqj) : lskmzd.fKjqj != null) return false;
        if (fDz != null ? !fDz.equals(lskmzd.fDz) : lskmzd.fDz != null) return false;
        if (fDzok != null ? !fDzok.equals(lskmzd.fDzok) : lskmzd.fDzok != null) return false;
        if (fPzsy != null ? !fPzsy.equals(lskmzd.fPzsy) : lskmzd.fPzsy != null) return false;
        if (fDykm != null ? !fDykm.equals(lskmzd.fDykm) : lskmzd.fDykm != null) return false;
        if (fPjmc != null ? !fPjmc.equals(lskmzd.fPjmc) : lskmzd.fPjmc != null) return false;
        if (fSjly != null ? !fSjly.equals(lskmzd.fSjly) : lskmzd.fSjly != null) return false;
        if (fYssx != null ? !fYssx.equals(lskmzd.fYssx) : lskmzd.fYssx != null) return false;
        if (fYsxx != null ? !fYsxx.equals(lskmzd.fYsxx) : lskmzd.fYsxx != null) return false;
        if (fDf13 != null ? !fDf13.equals(lskmzd.fDf13) : lskmzd.fDf13 != null) return false;
        if (fJf13 != null ? !fJf13.equals(lskmzd.fJf13) : lskmzd.fJf13 != null) return false;
        if (fYe13 != null ? !fYe13.equals(lskmzd.fYe13) : lskmzd.fYe13 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = item != null ? item.hashCode() : 0;
        result = 31 * result + (itemNo != null ? itemNo.hashCode() : 0);
        result = 31 * result + (itemName != null ? itemName.hashCode() : 0);
        result = 31 * result + (ele != null ? ele.hashCode() : 0);
        result = 31 * result + (departmentId != null ? departmentId.hashCode() : 0);
        result = 31 * result + (accType != null ? accType.hashCode() : 0);
        result = 31 * result + (spType != null ? spType.hashCode() : 0);
        result = 31 * result + (exchang != null ? exchang.hashCode() : 0);
        result = 31 * result + (supAcc1 != null ? supAcc1.hashCode() : 0);
        result = 31 * result + (supAcc2 != null ? supAcc2.hashCode() : 0);
        result = 31 * result + (budgetMoney != null ? budgetMoney.hashCode() : 0);
        result = 31 * result + (journal != null ? journal.hashCode() : 0);
        result = 31 * result + (finLevel != null ? finLevel.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (debitMoney != null ? debitMoney.hashCode() : 0);
        result = 31 * result + (creditMoney != null ? creditMoney.hashCode() : 0);
        result = 31 * result + (supMoney != null ? supMoney.hashCode() : 0);
        result = 31 * result + (debitMoneySup != null ? debitMoneySup.hashCode() : 0);
        result = 31 * result + (creditMoneySup != null ? creditMoneySup.hashCode() : 0);
        result = 31 * result + (debitMoneyAcm != null ? debitMoneyAcm.hashCode() : 0);
        result = 31 * result + (creditMoneyAcm != null ? creditMoneyAcm.hashCode() : 0);
        result = 31 * result + (debitMoney01 != null ? debitMoney01.hashCode() : 0);
        result = 31 * result + (creditMoney01 != null ? creditMoney01.hashCode() : 0);
        result = 31 * result + (balance01 != null ? balance01.hashCode() : 0);
        result = 31 * result + (debitMoney02 != null ? debitMoney02.hashCode() : 0);
        result = 31 * result + (creditMoney02 != null ? creditMoney02.hashCode() : 0);
        result = 31 * result + (balance02 != null ? balance02.hashCode() : 0);
        result = 31 * result + (debitMoney03 != null ? debitMoney03.hashCode() : 0);
        result = 31 * result + (creditMoney03 != null ? creditMoney03.hashCode() : 0);
        result = 31 * result + (balance03 != null ? balance03.hashCode() : 0);
        result = 31 * result + (debitMoney04 != null ? debitMoney04.hashCode() : 0);
        result = 31 * result + (creditMoney04 != null ? creditMoney04.hashCode() : 0);
        result = 31 * result + (balance04 != null ? balance04.hashCode() : 0);
        result = 31 * result + (debitMoney05 != null ? debitMoney05.hashCode() : 0);
        result = 31 * result + (creditMoney05 != null ? creditMoney05.hashCode() : 0);
        result = 31 * result + (balance05 != null ? balance05.hashCode() : 0);
        result = 31 * result + (debitMoney06 != null ? debitMoney06.hashCode() : 0);
        result = 31 * result + (creditMoney06 != null ? creditMoney06.hashCode() : 0);
        result = 31 * result + (balance06 != null ? balance06.hashCode() : 0);
        result = 31 * result + (debitMoney07 != null ? debitMoney07.hashCode() : 0);
        result = 31 * result + (creditMoney07 != null ? creditMoney07.hashCode() : 0);
        result = 31 * result + (balance07 != null ? balance07.hashCode() : 0);
        result = 31 * result + (debitMoney08 != null ? debitMoney08.hashCode() : 0);
        result = 31 * result + (creditMoney08 != null ? creditMoney08.hashCode() : 0);
        result = 31 * result + (balance08 != null ? balance08.hashCode() : 0);
        result = 31 * result + (debitMoney09 != null ? debitMoney09.hashCode() : 0);
        result = 31 * result + (creditMoney09 != null ? creditMoney09.hashCode() : 0);
        result = 31 * result + (balance09 != null ? balance09.hashCode() : 0);
        result = 31 * result + (debitMoney10 != null ? debitMoney10.hashCode() : 0);
        result = 31 * result + (creditMoney10 != null ? creditMoney10.hashCode() : 0);
        result = 31 * result + (balance10 != null ? balance10.hashCode() : 0);
        result = 31 * result + (debitMoney11 != null ? debitMoney11.hashCode() : 0);
        result = 31 * result + (creditMoney11 != null ? creditMoney11.hashCode() : 0);
        result = 31 * result + (balance11 != null ? balance11.hashCode() : 0);
        result = 31 * result + (debitMoney12 != null ? debitMoney12.hashCode() : 0);
        result = 31 * result + (creditMoney12 != null ? creditMoney12.hashCode() : 0);
        result = 31 * result + (balance12 != null ? balance12.hashCode() : 0);
        result = 31 * result + (fKzjm != null ? fKzjm.hashCode() : 0);
        result = 31 * result + (fKjqj != null ? fKjqj.hashCode() : 0);
        result = 31 * result + (fDz != null ? fDz.hashCode() : 0);
        result = 31 * result + (fDzok != null ? fDzok.hashCode() : 0);
        result = 31 * result + (fPzsy != null ? fPzsy.hashCode() : 0);
        result = 31 * result + (fDykm != null ? fDykm.hashCode() : 0);
        result = 31 * result + (fPjmc != null ? fPjmc.hashCode() : 0);
        result = 31 * result + (fSjly != null ? fSjly.hashCode() : 0);
        result = 31 * result + (fYssx != null ? fYssx.hashCode() : 0);
        result = 31 * result + (fYsxx != null ? fYsxx.hashCode() : 0);
        result = 31 * result + (fDf13 != null ? fDf13.hashCode() : 0);
        result = 31 * result + (fJf13 != null ? fJf13.hashCode() : 0);
        result = 31 * result + (fYe13 != null ? fYe13.hashCode() : 0);
        return result;
    }
}
