package com.bjut.ssh.controller.CostManagement;

import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.ProBasicOperatingExpensesService;
import com.bjut.ssh.service.CostManagement.ProProductionCostChartService;
import com.bjut.ssh.service.CostManagement.SiteProductionCostChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Title: ProProductionCostChartController
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/9/10 14:20
 * @Version: 1.0
 */

@Controller
@RequestMapping("/ProProductionCostChart")
public class ProProductionCostChartController {
    @Autowired
    private ProProductionCostChartService proProductionCostChartService;

    @RequestMapping("/getValData/{selectSiteValue}")
    @ResponseBody
    public Msg getChartsData(@PathVariable("selectSiteValue") String selectSiteValue){
        return proProductionCostChartService.getValData(selectSiteValue);
    }

    @RequestMapping("/getItemNam")
    @ResponseBody
    public Msg getItemsName(){
        return proProductionCostChartService.getItemsName();
    }

    @RequestMapping("/getProductSelect.action")
    @ResponseBody
    public Msg productSelect(){
        return proProductionCostChartService.getProduct();
    }

    @RequestMapping("/getDatagridData/{selectSiteValue}")
    @ResponseBody
    public Msg getDatagridData(@PathVariable("selectSiteValue") String selectSite){
        return proProductionCostChartService.getDatagridData(selectSite);
    }
}
