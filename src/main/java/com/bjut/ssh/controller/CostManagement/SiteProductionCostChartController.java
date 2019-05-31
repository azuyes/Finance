package com.bjut.ssh.controller.CostManagement;

import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.SiteProductionCostChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Title: SiteProductionCostStructureController
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/7/21 22:19
 * @Version: 1.0
 */

@Controller
@RequestMapping("/SiteProductionCostChart")
public class SiteProductionCostChartController {
    @Autowired
    private SiteProductionCostChartService siteProductionCostChartService;

    @RequestMapping("/getValData/{selectSiteValue}")
    @ResponseBody
    public Msg getChartsData(@PathVariable("selectSiteValue") String selectSiteValue){
        return siteProductionCostChartService.getValData(selectSiteValue);
    }

    @RequestMapping("/getItemNam")
    @ResponseBody
    public Msg getItemsName(){
        return siteProductionCostChartService.getItemsName();
    }

    @RequestMapping("/getDatagridData/{selectSiteValue}")
    @ResponseBody
    public Msg getDatagridData(@PathVariable("selectSiteValue") String selectSite){
        return siteProductionCostChartService.getDatagridData(selectSite);
    }
}
