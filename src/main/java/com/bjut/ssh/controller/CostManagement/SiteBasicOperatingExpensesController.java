package com.bjut.ssh.controller.CostManagement;

import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.SiteBasicOperatingExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * @Title: SiteBasicOperatingExpensesController
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/7/19 16:25
 * @Version: 1.0
 */
@Controller
@RequestMapping("/SiteBasicOperatingExpenses")
public class SiteBasicOperatingExpensesController {
    @Autowired
    private SiteBasicOperatingExpensesService siteBasicOperatingExpensesService;

    @RequestMapping("/getValData/{selectSalesMethod}/{selectTimeField}/{selectDepatmentValue}")
    @ResponseBody
    public Msg getChartsData(@PathVariable("selectSalesMethod") String selectSalesMethod, @PathVariable("selectTimeField") String selectTimeField, @PathVariable("selectDepatmentValue") String selectDepatmentValue){
        return siteBasicOperatingExpensesService.getValData(selectSalesMethod,selectTimeField,selectDepatmentValue);
    }

    @RequestMapping("/getItemNam")
    @ResponseBody
    public Msg getItemsName(){
        return siteBasicOperatingExpensesService.getItemsName();
    }

    @RequestMapping("/getDatagridData/{selectSiteValue}/{selectTimeField}")
    @ResponseBody
    public Msg getDatagridData(@PathVariable("selectSiteValue") String selectSite, @PathVariable("selectTimeField") String selectTimeField){
        return siteBasicOperatingExpensesService.getDatagridData(selectSite, selectTimeField);
    }
}
