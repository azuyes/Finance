package com.bjut.ssh.service.FinanceProcess;

import com.bjut.ssh.entity.*;

import java.util.List;

/**
 * @Title: AccountingVouchersService
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/5/4 11:12
 * @Version: 1.0
 */
public interface AccountingVouchersService {
//    public Msg saveOriginalVoucher(Lsyspz lsyspz, Boolean is_first_save);
//    public Msg saveSpVoucher(Lshspz lshspz);
//    public Msg saveEntry(Lspzk1 lspzk1, Boolean is_first_save);
    public Msg saveVoucher(List<Lspzk1QueryVo> lspzk1QueryVoList);
    public Msg updateVoucherData(int is_add, String year_month);
    public List<Lspzk1QueryVo> queryVouchersForDatagrid(String voucherNo, String inputDate);
    public List<Lsyspz> queryOriginalVouchers(String voucherNo, String inputDate, String entryNo);
    public List<LshspzQueryVo> querySpVouchers(String voucherNo, String inputDate, String entryNo, String itemNo);
    //public List<LshspzQueryVo> querySpVouchers(String voucherNo, String inputDate, String entryNo, String itemNo);
    public Msg getVoucherData(String year_month);
    public Msg delOriginalVoucher(String inputDate, String voucherNo, String entryNo, String originalNo);
    public Msg delEntry(String inputDate, String voucherNo, String entryNo, String itemNo);
    public Msg delVoucher(String voucherNo, String inputDate);
    public Msg changeBkpDirection(String inputDate, String voucherNo, String entryNo, String itemNo);
    public Msg operateReviewByNum(Boolean is_review, String inputDate, String start, String end, String name, String no);
    public Msg operateReviewByDate(Boolean is_review, String inputDate, String start, String end, String name, String no);
}
