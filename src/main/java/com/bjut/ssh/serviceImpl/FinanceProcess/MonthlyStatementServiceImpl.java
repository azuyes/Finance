package com.bjut.ssh.serviceImpl.FinanceProcess;

import com.bjut.ssh.dao.FinanceProcess.MonthlyStatementDao;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.FinanceProcess.MonthlyStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Title: MonthlyStatementServiceImpl
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/5/28 13:07
 * @Version: 1.0
 */
@Service
@Transactional
public class MonthlyStatementServiceImpl implements MonthlyStatementService {
    @Autowired
    private MonthlyStatementDao monthlyStatementDao;

    @Override
    public Msg checkCurrentDate(){
        return monthlyStatementDao.checkCurrentDate();
    }

    @Override
    public Msg checkReviewedStatement(String year_month){
        return monthlyStatementDao.checkReviewedStatement(year_month);
    }

    @Override
    public Msg checkLossNProfitBalance(String year_month){
        return monthlyStatementDao.checkLossNProfitBalance(year_month);
    }

    @Override
    public Msg yearlyStatement(String year){
        return monthlyStatementDao.yearlyStatement(year);
    }

    @Override
    @Transactional
    public Msg initNewMonth(String year_month){
        return monthlyStatementDao.initNewMonth(year_month);
    }

    @Override
    public Msg setCurrentDate(String date){
        return monthlyStatementDao.setCurrentDate(date);
    }
}
