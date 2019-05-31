package com.bjut.ssh.controller.CostManagement;

import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.SiteProductionCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Title: SiteProductionCostController
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/7/17 14:20
 * @Version: 1.0
 */
@Controller
@RequestMapping("/SiteProductionCost")
public class SiteProductionCostController {
    @Autowired
    private SiteProductionCostService siteProductionCostService;

    @RequestMapping("/getDepartmentSelect.action")
    @ResponseBody
    public Msg getDepartmentSelect(){
        return  siteProductionCostService.getDepartmentSelect();
    }

    @RequestMapping("/getSalesMethodSelect.action")
    @ResponseBody
    public Msg getSalesMethodSelect(){
        return siteProductionCostService.getSalesMethodSelect();
    }

    @RequestMapping("/getValData/{selectSalesMethod}/{selectTimeField}/{selectDepartmentValue}")
    @ResponseBody
    public Msg getChartsData(@PathVariable("selectSalesMethod") String selectSalesMethod, @PathVariable("selectTimeField") String selectTimeField, @PathVariable("selectDepartmentValue") String selectDepartmentValue){
        return siteProductionCostService.getValData(selectSalesMethod,selectTimeField,selectDepartmentValue);
    }

    @RequestMapping("/getItemNam")
    @ResponseBody
    public Msg getItemsName(){
        return siteProductionCostService.getItemsName();
    }

    @RequestMapping("/getDatagridData/{selectSiteValue}/{selectTimeField}")
    @ResponseBody
    public Msg getDatagridData(@PathVariable("selectSiteValue") String selectSite, @PathVariable("selectTimeField") String selectTimeField){
        return siteProductionCostService.getDatagridData(selectSite, selectTimeField);
    }
}
