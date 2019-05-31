package com.bjut.ssh.service.FinanceProcess;

import com.bjut.ssh.entity.Lspzk1;
import com.bjut.ssh.entity.Msg;

import java.util.List;

/**
 * @Title: FinanceSearchService
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/7/3 10:33
 * @Version: 1.0
 */
public interface FinanceSearchService {
    public Msg getConfigsForSearch();
    public Msg queryCaptionWithSql(String id, String levelFlag, String from, String to, Boolean is_show_all, String sql);
    public Msg queryVouchersWithSql(int is_detail, String year_month, String sql);
    public Msg queryDetailWithSql(String year_month, String itemNo, String sql);
    public Msg queryJournalWithSql(String from, String to, String itemNo, String sql);
    public List<Lspzk1> queryVoucherWithSql(String from, String to, String itemNo, String sql);
    public List<Lspzk1> queryVoucherByItem(String from, String to, String itemNo);
    public Double queryMoney(String table_name, String col_name, String condition);
}
