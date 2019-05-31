package com.bjut.ssh.controller.CostManagement;

import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.CostBudgetImplementationTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Title:  CostBudgetImplementationTableController
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/8/23 14:14
 * @Version: 1.0
 */
@Controller
@RequestMapping("/CostBudgetImplementationTable")
public class CostBudgetImplementationTableController {
    @Autowired
    private CostBudgetImplementationTableService costBudgetImplementationTableService;

    @RequestMapping("/getEchartsTheme/")
    @ResponseBody
    public Msg getEchartsTheme(){
        return costBudgetImplementationTableService.getEchartsTheme();
    }

    @RequestMapping("/getValData/{selectTime}")
    @ResponseBody
    public Msg getValData(@PathVariable("selectTime") String selectTime){
        return costBudgetImplementationTableService.getValData(selectTime);
    }

    @RequestMapping("/getDatagridData/{selectTime}")
    @ResponseBody
    public Msg getDatagridData(@PathVariable("selectTime") String selectTime){
        return costBudgetImplementationTableService.getDatagridData(selectTime);
    }
}
