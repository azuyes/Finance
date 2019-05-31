package com.bjut.ssh.controller.CostManagement;

import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.SiteProductionCostStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Title: SiteProductionCostStructureController
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/7/21 22:00
 * @Version: 1.0
 */

@Controller
@RequestMapping("/SiteProductionCostStructure")
public class SiteProductionCostStructureController {
    @Autowired
    private SiteProductionCostStructureService siteProductionCostStructureService;

    @RequestMapping("/getShowNo.action/{selectSiteValue}")
    @ResponseBody
    public Msg getShowNo(@PathVariable("selectSiteValue") String selectSiteValue){
        return siteProductionCostStructureService.getShowNo(selectSiteValue);
    }

    @RequestMapping("/getSiteSelect.action/{selectDepartmentValue}")
    @ResponseBody
    public Msg getSiteSelect(@PathVariable("selectDepartmentValue") String selectDepartmentValue){
        return siteProductionCostStructureService.getSiteSelect(selectDepartmentValue);
    }

    @RequestMapping("/getValData/{showNo}/{selectTimeField}/{selectSiteValue}")
    @ResponseBody
    public Msg getChartsData(@PathVariable("showNo") String showNo, @PathVariable("selectTimeField") String selectTimeField, @PathVariable("selectSiteValue") String selectSiteValue){
        return siteProductionCostStructureService.getValData(showNo,selectTimeField,selectSiteValue);
    }

    @RequestMapping("/getDatagridData/{selectSiteValue}")
    @ResponseBody
    public Msg getDatagridData(@PathVariable("selectSiteValue") String selectSite){
        return siteProductionCostStructureService.getDatagridData(selectSite);
    }
}
