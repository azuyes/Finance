package com.bjut.ssh.serviceImpl.FinanceProcess;

import com.bjut.ssh.dao.FinanceProcess.CaptionOfAccountDao;
import com.bjut.ssh.entity.*;
import com.bjut.ssh.service.FinanceProcess.AccountBookService;
import com.bjut.ssh.dao.FinanceProcess.AccountingVouchersDao;
import com.bjut.ssh.service.FinanceProcess.AccountingVouchersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: AccountingVouchersServiceImpl
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/5/4 11:14
 * @Version: 1.0
 */
@Service
@Transactional
public class AccountingVouchersServiceImpl implements AccountingVouchersService{
    @Autowired
    private AccountingVouchersDao accountingVouchersDao;

    @Autowired
    private CaptionOfAccountDao captionOfAccountDao;

    @Override
    public Msg saveVoucher(List<Lspzk1QueryVo> lspzk1QueryVoList){
        return accountingVouchersDao.saveVoucher(lspzk1QueryVoList);
    }

//    @Override
//    public Msg saveOriginalVoucher(Lsyspz lsyspz, Boolean is_first_save){
//        return accountingVouchersDao.saveOriginalVoucher(lsyspz, is_first_save);
//    }
//
//
//    @Override
//    public Msg saveEntry(Lspzk1 lspzk1, Boolean is_first_save){
//        return accountingVouchersDao.saveEntry(lspzk1, is_first_save);
//    }
//
//    @Override
//    public Msg saveSpVoucher(Lshspz lshspz){
//        return accountingVouchersDao.saveSpVoucher(lshspz);
//    }

    @Override
    public Msg delOriginalVoucher(String inputDate, String voucherNo, String entryNo, String originalNo){
        return accountingVouchersDao.delOriginalVoucher(inputDate, voucherNo, entryNo, originalNo);
    }

    @Override
    public Msg delEntry(String inputDate, String voucherNo, String entryNo, String itemNo){
        return accountingVouchersDao.delEntry(inputDate, voucherNo, entryNo, itemNo);
    }

    @Override
    public Msg delVoucher(String voucherNo, String inputDate){
        return accountingVouchersDao.delVoucher(voucherNo, inputDate);
    }

    @Override
    public Msg changeBkpDirection(String inputDate, String voucherNo, String entryNo, String itemNo){
        return accountingVouchersDao.changeBkpDirection(inputDate, voucherNo, entryNo, itemNo);
    }

    @Override
    public List<Lspzk1QueryVo> queryVouchersForDatagrid(String voucherNo, String inputDate){
        List<Lspzk1QueryVo> lspzk1QueryVoList = new ArrayList<>();
        try {
            List<Lspzk1> lspzk1List = accountingVouchersDao.queryVouchers(voucherNo, inputDate);
            for (Lspzk1 lspzk1 : lspzk1List) {
                Lspzk1QueryVo lspzk1QueryVo = new Lspzk1QueryVo();
                lspzk1QueryVo.setVoucherNo(voucherNo);
                lspzk1QueryVo.setItemNo(lspzk1.getItemNo());
                lspzk1QueryVo.setEntryNo(lspzk1.getEntryNo());
                lspzk1QueryVo.setInputDate(lspzk1.getInputDate());
                lspzk1QueryVo.setAcsDocCnt(lspzk1.getAcsDocCnt());
                lspzk1QueryVo.setVoucherType(lspzk1.getVoucherType());
                lspzk1QueryVo.setSummary(lspzk1.getSummary());
                lspzk1QueryVo.setBkpDirection(lspzk1.getBkpDirection());
                lspzk1QueryVo.setPreActDoc(lspzk1.getPreActDoc());
                lspzk1QueryVo.setPreActDoc(lspzk1.getPreActDoc());
                lspzk1QueryVo.setPreActNo(lspzk1.getPreActNo());
                lspzk1QueryVo.setAuditor(lspzk1.getAuditor());
                lspzk1QueryVo.setAuditorNo(lspzk1.getAuditorNo());
                lspzk1QueryVo.setQty(lspzk1.getQty());
                lspzk1QueryVo.setMoney(lspzk1.getMoney());
                lspzk1QueryVo.setLastDate(accountingVouchersDao.queryVoucherDate(voucherNo, inputDate, 0));
                lspzk1QueryVo.setNextDate(accountingVouchersDao.queryVoucherDate(voucherNo, inputDate, 1));
                if (lspzk1.getBkpDirection().equals("J")) {
                    lspzk1QueryVo.setDebitMoney(lspzk1.getMoney());
                } else {
                    lspzk1QueryVo.setCreditMoney(lspzk1.getMoney());
                }

                //获取该科目的编号
                String id = lspzk1.getItemNo();

                //获取该科目全部科目字典数据
                Lskmzd lskmzd = captionOfAccountDao.getCaptionOfAccount(id);
                lspzk1QueryVo.setAccType(lskmzd.getAccType());
                lspzk1QueryVo.setJournal(lskmzd.getJournal());
                lspzk1QueryVo.setSupAcc1(lskmzd.getSupAcc1());
                lspzk1QueryVo.setSupAcc2(lskmzd.getSupAcc2());

                //获取分录对应的原始凭证或核算凭证
                if (lskmzd.getSupAcc1() == null || lskmzd.getSupAcc1().equals("")) {
                    List<Lsyspz> lsyspzList = accountingVouchersDao.queryOriginalVouchers(voucherNo, lspzk1.getInputDate(), lspzk1.getEntryNo());
                    lspzk1QueryVo.setLsyspzList(lsyspzList);
                } else {
                    List<LshspzQueryVo> lshspzQueryVoList = querySpVouchers(voucherNo, lspzk1.getInputDate(), lspzk1.getEntryNo(), lskmzd.getItemNo());
                    lspzk1QueryVo.setLshspzQueryVoList(lshspzQueryVoList);
                }

                //获取该科目的级数
                int level = Integer.parseInt(lskmzd.getItem());

                //设置总账科目名
                String level1_id = captionOfAccountDao.getFullNo(captionOfAccountDao.getCatNo(id, "2"), 1);
                String level1_name = captionOfAccountDao.getCaptionOfAccount(level1_id).getItemName();
                lspzk1QueryVo.setLevel_1(level1_name);

                //如果科目不止1级，设置二级科目名
                if (level > 1) {
                    String level2_id = captionOfAccountDao.getFullNo(captionOfAccountDao.getCatNo(id, "3"), 2);
                    String level2_name = captionOfAccountDao.getCaptionOfAccount(level2_id).getItemName();
                    lspzk1QueryVo.setLevel_2(level2_name);
                }

                //如果科目不止2级，设置三级科目名
                if (level > 2) {
                    String level3_id = captionOfAccountDao.getFullNo(captionOfAccountDao.getCatNo(id, "4"), 3);
                    String level3_name = captionOfAccountDao.getCaptionOfAccount(level3_id).getItemName();
                    lspzk1QueryVo.setLevel_3(level3_name);
                }

                //如果科目不止3级，设置明细科目名
                String level_detail_name = "";
                for (int current_level = 4; level > 3; level--, current_level++) {
                    String level_detail_id = captionOfAccountDao.getFullNo(captionOfAccountDao.getCatNo(id, Integer.toString(current_level + 1)), current_level);
                    level_detail_name = level_detail_name + "/" + captionOfAccountDao.getCaptionOfAccount(level_detail_id).getItemName();
                }
                lspzk1QueryVo.setLevel_detail(level_detail_name);
                lspzk1QueryVoList.add(lspzk1QueryVo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return lspzk1QueryVoList;
        }
    }

    @Override
    public List<Lsyspz> queryOriginalVouchers(String voucherNo, String inputDate, String entryNo){
        return accountingVouchersDao.queryOriginalVouchers(voucherNo, inputDate, entryNo);
    }

    @Override
    public List<LshspzQueryVo> querySpVouchers(String voucherNo, String inputDate, String entryNo, String itemNo){
        return accountingVouchersDao.queryTotalSpVouchers(itemNo, voucherNo, inputDate, entryNo);
//        List<LshspzQueryVo> result = new ArrayList<>();
//        List<Lshszd> sp1_list = new ArrayList<>();
//        List<Lshszd> sp2_list = new ArrayList<>();
//        sp1_list = accountingVouchersDao.querySpDict(itemNo, supAcc1);
//        if(supAcc2 != null) {
//            sp2_list = accountingVouchersDao.querySpDict(itemNo, supAcc2);
//        }
//        for(Lshszd sp1 : sp1_list){
//            LshspzQueryVo sub_res1 = new LshspzQueryVo();
//            sub_res1.setSpNo1(sp1.getSpNo());
//            sub_res1.setSpName1(sp1.getSpName());
//            if(supAcc2 != null && !supAcc2.equals("")){
//                for(Lshszd sp2 : sp2_list){
//                    LshspzQueryVo sub_res2 = new LshspzQueryVo();
//                    sub_res2.setSpNo1(sp1.getSpNo());
//                    sub_res2.setSpName1(sp1.getSpName());
//                    sub_res2.setSpNo2(sp2.getSpNo());
//                    sub_res2.setSpName2(sp2.getSpName());
//                    result.add(sub_res2);
//                }
//            }
//            else{
//                result.add(sub_res1);
//            }
//        }
//        for(LshspzQueryVo res : result){
//            Lshspz lshspz = accountingVouchersDao.querySpVoucher(voucherNo, inputDate, entryNo, itemNo, res.getSpNo1(), res.getSpNo2());
//            if(lshspz != null){
//                res.setEntryNo(lshspz.getEntryNo());
//                res.setVoucherNo(lshspz.getVoucherNo());
//                res.setInputDate(lshspz.getInputDate());
//                res.setItemNo(lshspz.getItemNo());
//                res.setBkpDirection(lshspz.getBkpDirection());
//                res.setMoney(lshspz.getMoney());
//                res.setQty(lshspz.getQty());
//            }
//        }
//        return result;
    }

    @Override
    public Msg updateVoucherData(int is_add, String year_month){
        return accountingVouchersDao.updateVoucherData(is_add, year_month);
    }

    @Override
    public Msg getVoucherData(String year_month){
        return accountingVouchersDao.getVoucherData(year_month);
    }

    @Override
    public Msg operateReviewByNum(Boolean is_review, String inputDate, String start, String end, String name, String no){
        return accountingVouchersDao.operateReviewByNum(is_review, inputDate, start, end, name, no);
    }

    @Override
    public Msg operateReviewByDate(Boolean is_review, String inputDate, String start, String end, String name, String no){
        return accountingVouchersDao.operateReviewByDate(is_review, inputDate, start, end, name, no);
    }
}
