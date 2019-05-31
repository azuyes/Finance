package com.bjut.ssh.controller.CostManagement;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.OverallProductionCostChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Title: BasicOperatingExpensesController
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/7/13 14:14
 * @Version: 1.0
 */
@Controller
@RequestMapping("/OverallProductionCostChart")
public class OverallProductionCostChartController {
    @Autowired
    private OverallProductionCostChartService overallProductionCostChartService;

    @RequestMapping("/getEchartsTheme")
    @ResponseBody
    public Msg getEchartsTheme(){
        return overallProductionCostChartService.getEchartsTheme();
    }

    @RequestMapping("/getValData1/{selectItemValue}")
    @ResponseBody
    public Msg getValData1(@PathVariable("selectItemValue") String selectItemValue){
        return overallProductionCostChartService.getValData1(selectItemValue);
    }

    @RequestMapping("/getDatagridData1")
    @ResponseBody
    public Msg getDatagridData1(){
        return overallProductionCostChartService.getDatagridData1();
    }

    @RequestMapping("/getItemSelect")
    @ResponseBody
    public Msg getItemSelect(){
        return overallProductionCostChartService.getItemSelect();
    }

}
