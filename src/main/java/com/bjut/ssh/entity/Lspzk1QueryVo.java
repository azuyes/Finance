package com.bjut.ssh.entity;

import java.util.List;

/**
 * @Title: Lspzk1QueryVo
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/5/4 18:36
 * @Version: 1.0
 */
public class Lspzk1QueryVo {
    private String voucherNo;
    private String entryNo;
    private String itemNo;
    private String inputDate;
    private String lastDate;
    private String nextDate;
    private String level_1;
    private String level_2;
    private String level_3;
    private String level_detail;
    private Double debitMoney;
    private Double creditMoney;
    private String accType;
    private Byte journal;
    private String supAcc1;
    private String supAcc2;
    private Integer acsDocCnt;
    private String voucherType;
    private String summary;
    private String bkpDirection;
    private Double money;
    private Double qty;
    private String preActDoc;
    private String preActNo;
    private String auditor;
    private String auditorNo;
    private List<Lsyspz> lsyspzList;
    private List<LshspzQueryVo> lshspzQueryVoList;

    public String getVoucherNo() {
        return voucherNo;
    }
    public void setVoucherNo(String voucherNo){
        this.voucherNo = voucherNo;
    }

    public String getEntryNo() {
        return entryNo;
    }

    public void setEntryNo(String entryNo){
        this.entryNo = entryNo;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getNextDate() {
        return nextDate;
    }

    public void setNextDate(String nextDate) {
        this.nextDate = nextDate;
    }

    public String getLevel_1() {
        return level_1;
    }

    public void setLevel_1(String level_1) {
        this.level_1 = level_1;
    }

    public String getLevel_2() {
        return level_2;
    }

    public void setLevel_2(String level_2) {
        this.level_2 = level_2;
    }

    public String getLevel_3() {
        return level_3;
    }

    public void setLevel_3(String level_3) {
        this.level_3 = level_3;
    }

    public String getLevel_detail() {
        return level_detail;
    }

    public void setLevel_detail(String level_detail) {
        this.level_detail = level_detail;
    }

    public Double getDebitMoney() {
        return debitMoney;
    }

    public void setDebitMoney(Double debitMoney) {
        this.debitMoney = debitMoney;
    }

    public Double getCreditMoney() {
        return creditMoney;
    }

    public void setCreditMoney(Double creditMoney) {
        this.creditMoney = creditMoney;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType){
        this.accType = accType;
    }

    public Byte getJournal(){
        return journal;
    }

    public void setJournal(Byte journal){
        this.journal = journal;
    }

    public String getSupAcc1(){
        return supAcc1;
    }

    public void setSupAcc1(String supAcc1){
        this.supAcc1 = supAcc1;
    }

    public String getSupAcc2() {
        return supAcc2;
    }

    public void setSupAcc2(String supAcc2) {
        this.supAcc2 = supAcc2;
    }

    public Integer getAcsDocCnt(){
        return acsDocCnt;
    }

    public void setAcsDocCnt(Integer acsDocCnt){
        this.acsDocCnt = acsDocCnt;
    }

    public String getVoucherType(){
        return voucherType;
    }

    public void setVoucherType(String voucherType){
        this.voucherType = voucherType;
    }

    public String getSummary(){
        return summary;
    }

    public void setSummary(String summary){
        this.summary = summary;
    }

    public String getBkpDirection(){
        return bkpDirection;
    }

    public void setBkpDirection(String bkpDirection){
        this.bkpDirection = bkpDirection;
    }

    public String getPreActDoc(){
        return preActDoc;
    }

    public void setPreActDoc(String preActDoc) {
        this.preActDoc = preActDoc;
    }

    public String getPreActNo() {
        return preActNo;
    }

    public void setPreActNo(String preActNo) {
        this.preActNo = preActNo;
    }

    public String getAuditor() {
        return auditor;
    }

    public String getAuditorNo() {
        return auditorNo;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public void setAuditorNo(String auditorNo) {
        this.auditorNo = auditorNo;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public List<Lsyspz> getLsyspzList(){
        return lsyspzList;
    }

    public void addLsyspzList(Lsyspz lsyspz){
        this.lsyspzList.add(lsyspz);
    }

    public void setLsyspzList(List<Lsyspz> lsyspzList){
        this.lsyspzList = lsyspzList;
    }

    public List<LshspzQueryVo> getLshspzQueryVoList(){
        return lshspzQueryVoList;
    }

    public void addLshspzQueryVoList(LshspzQueryVo lshspzQueryVo){
        this.lshspzQueryVoList.add(lshspzQueryVo);
    }

    public void setLshspzQueryVoList(List<LshspzQueryVo> lshspzQueryVoList){
        this.lshspzQueryVoList = lshspzQueryVoList;
    }
}
