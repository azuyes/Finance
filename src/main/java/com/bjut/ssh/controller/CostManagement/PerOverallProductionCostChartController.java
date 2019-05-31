package com.bjut.ssh.controller.CostManagement;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.PerOverallProductionCostChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Title: PerOverallProductionCostChartController
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/7/13 14:14
 * @Version: 1.0
 */
@Controller
@RequestMapping("/PerOverallProductionCostChart")
public class PerOverallProductionCostChartController {
    @Autowired
    private PerOverallProductionCostChartService perOverallProductionCostChartService;

    @RequestMapping("/getEchartsTheme")
    @ResponseBody
    public Msg getEchartsTheme(){
        return perOverallProductionCostChartService.getEchartsTheme();
    }

    @RequestMapping("/getValData1/{selectItemValue}")
    @ResponseBody
    public Msg getValData1(@PathVariable("selectItemValue") String selectItemValue){
        return perOverallProductionCostChartService.getValData1(selectItemValue);
    }

    @RequestMapping("/getDatagridData")
    @ResponseBody
    public Msg getDatagridData(){
        return perOverallProductionCostChartService.getDatagridData();
    }

    @RequestMapping("/getItemSelect")
    @ResponseBody
    public Msg getItemSelect(){
        return perOverallProductionCostChartService.getItemSelect();
    }

}
