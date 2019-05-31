package com.bjut.ssh.serviceImpl.FinanceProcess;

import com.bjut.ssh.dao.FinanceProcess.AccountingVouchersDao;
import com.bjut.ssh.dao.FinanceProcess.CaptionOfAccountDao;
import com.bjut.ssh.dao.FinanceProcess.FinanceSearchDao;
import com.bjut.ssh.entity.*;
import com.bjut.ssh.service.FinanceProcess.FinanceSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: FinanceSearchServiceImpl
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/7/3 10:36
 * @Version: 1.0
 */
@Service
@Transactional
public class FinanceSearchServiceImpl implements FinanceSearchService{
    @Autowired
    private FinanceSearchDao financeSearchDao;

    @Autowired
    private CaptionOfAccountDao captionOfAccountDao;

    @Autowired
    private AccountingVouchersDao accountingVouchersDao;

    @Override
    public Msg getConfigsForSearch(){
        return financeSearchDao.getConfigsForSearch();
    }

    @Override
    public Msg queryCaptionWithSql(String id, String levelFlag, String from, String to, Boolean is_show_all, String sql){
        return financeSearchDao.queryCaptionWithSql(id, levelFlag, from, to, is_show_all, sql);
    }

    @Override
    public Msg queryDetailWithSql(String year_month, String itemNo, String sql){
        return financeSearchDao.queryDetailWithSql(year_month, itemNo, sql);
    }

    @Override
    public Msg queryJournalWithSql(String from, String to, String itemNo, String sql){
        return financeSearchDao.queryJournalWithSql(from, to, itemNo, sql);
    }

    @Override
    public Msg queryVouchersWithSql(int is_detail, String year_month, String sql){
        List<Lspzk1> lspzk1List = new ArrayList<>();
        try {
            lspzk1List = financeSearchDao.queryVouchersWithSql(year_month, sql);
        }
        catch(Exception e){
            return Msg.fail().add("errorInfo", "查询语句无效，请重新输入！");
        }
        try {
            List<Lspzk1VoForSearch> lspzk1VoForSearches = new ArrayList<Lspzk1VoForSearch>();
            if (is_detail == 1) {
                Lspzk1VoForSearch lspzk1VoForSearch = new Lspzk1VoForSearch();
                for (Lspzk1 lspzk1 : lspzk1List) {
                    lspzk1VoForSearch.setLspzk1(lspzk1);
                    //获取该科目的编号
                    String id = lspzk1.getItemNo();

                    //获取该科目全部科目字典数据
                    Lskmzd lskmzd = captionOfAccountDao.getCaptionOfAccount(id);

                    //获取分录对应的原始凭证或核算凭证
                    if (lskmzd.getSupAcc1() == null || lskmzd.getSupAcc1().equals("")) {
                        List<Lsyspz> lsyspzList = accountingVouchersDao.queryOriginalVouchers(lspzk1.getVoucherNo(), lspzk1.getInputDate(), lspzk1.getEntryNo());
                        for (Lsyspz lsyspz : lsyspzList) {
                            Lspzk1VoForSearch save = new Lspzk1VoForSearch();
                            save.setLspzk1(lspzk1);
                            save.setLsyspz(lsyspz);
                            lspzk1VoForSearches.add(save);
                        }
                    } else {
                        List<Lshspz> lshspzList = accountingVouchersDao.querySpVouchers(lspzk1.getVoucherNo(), lspzk1.getInputDate(), lspzk1.getEntryNo());
                        for (Lshspz lshspz : lshspzList) {
                            Lspzk1VoForSearch save = new Lspzk1VoForSearch();
                            save.setLspzk1(lspzk1);
                            save.setLshspz(lshspz);
                            lspzk1VoForSearches.add(save);
                        }
                    }
                }
            } else {
                String voucherNo = "0000";
                Lspzk1VoForSearch lspzk1VoForSearch = new Lspzk1VoForSearch();
                for (Lspzk1 lspzk1 : lspzk1List) {
                    if (lspzk1.getVoucherNo().equals(voucherNo)) {
                        Double money = lspzk1VoForSearch.getMoney();
                        lspzk1VoForSearch.setMoney(money + lspzk1.getMoney());
                    } else {
                        if (!voucherNo.equals("0000")) {
                            lspzk1VoForSearches.add(lspzk1VoForSearch);
                        }
                        voucherNo = lspzk1.getVoucherNo();
                        lspzk1VoForSearch.setLspzk1(lspzk1);
                        lspzk1VoForSearch.setMoney(lspzk1.getMoney());
                    }
                }
                lspzk1VoForSearches.add(lspzk1VoForSearch);
            }
            return Msg.success().add("lspzk1VoForSearches", lspzk1VoForSearches);
        }
        catch(Exception e){
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "未知异常！");
        }
    }

    @Override
    public Double queryMoney(String table_name, String col_name, String condition){
        return financeSearchDao.queryColumn(table_name, col_name, condition);
    }

    @Override
    public List<Lspzk1> queryVoucherWithSql(String from, String to, String itemNo, String sql){
        return financeSearchDao.queryVoucherWithSql(from, to, itemNo, sql);
    }

    @Override
    public List<Lspzk1> queryVoucherByItem(String from, String to, String itemNo){
        return financeSearchDao.queryVoucherByItem(from, to, itemNo);
    }
}
