package com.bjut.ssh.controller.FinanceProcess;

import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.FinanceProcess.MonthlyStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Title: MonthlyStatementController
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/5/28 13:21
 * @Version: 1.0
 */
@Controller
@RequestMapping("/MonthlyStatement")
public class MonthlyStatementController {
    @Autowired
    private MonthlyStatementService monthlyStatementService;

    @RequestMapping("/checkCurrentDate")
    @ResponseBody
    public Msg checkCurrentDate(){
        return monthlyStatementService.checkCurrentDate();
    }

    @RequestMapping("/checkReviewedStatement/{year_month}")
    @ResponseBody
    public Msg checkReviewedStatement(@PathVariable("year_month") String year_month){
        return monthlyStatementService.checkReviewedStatement(year_month);
    }

    @RequestMapping("/checkLossNProfitBalance/{year_month}")
    @ResponseBody
    public Msg checkLossNProfitBalance(@PathVariable("year_month") String year_month){
        return monthlyStatementService.checkLossNProfitBalance(year_month);
    }

    @RequestMapping("/yearlyStatement/{year}")
    @ResponseBody
    public Msg yearlyStatement(@PathVariable("year") String year){
        return monthlyStatementService.yearlyStatement(year);
    }

    @RequestMapping("/initNewMonth/{year_month}")
    @ResponseBody
    public Msg initNewMonth(@PathVariable("year_month") String year_month){
        return monthlyStatementService.initNewMonth(year_month);
    }

    @RequestMapping("/setCurrentDate/{date}")
    @ResponseBody
    public Msg setCurrentDate(@PathVariable("date") String date){
        return monthlyStatementService.setCurrentDate(date);
    }
}
