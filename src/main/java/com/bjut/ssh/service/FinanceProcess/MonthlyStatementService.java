package com.bjut.ssh.service.FinanceProcess;

import com.bjut.ssh.entity.Msg;

/**
 * @Title: MonthlyStatementService
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/5/28 13:08
 * @Version: 1.0
 */
public interface MonthlyStatementService {
    public Msg checkCurrentDate();
    public Msg checkReviewedStatement(String year_month);
    public Msg checkLossNProfitBalance(String year_month);
    public Msg yearlyStatement(String year);
    public Msg initNewMonth(String year_month);
    public Msg setCurrentDate(String date);
}
