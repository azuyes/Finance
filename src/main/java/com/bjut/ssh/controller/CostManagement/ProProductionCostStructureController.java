package com.bjut.ssh.controller.CostManagement;

import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.ProProductionCostStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Title: ProProductionCostStructureController
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/8/26 14:20
 * @Version: 1.0
 */
@Controller
@RequestMapping("/ProProductionCostStructure")
public class ProProductionCostStructureController {
    @Autowired
    public ProProductionCostStructureService proProductionCostStructureService;

    @RequestMapping("/getValData/{showNo}/{selectTimeField}/{selectProductValue}")
    @ResponseBody
    public Msg getChartsData(@PathVariable("showNo") String showNo, @PathVariable("selectTimeField") String selectTimeField, @PathVariable("selectProductValue") String selectProductValue){
        return proProductionCostStructureService.getValData(showNo, selectTimeField, selectProductValue);
    }

    @RequestMapping("/getProductSelect.action")
    @ResponseBody
    public Msg productSelect(){
        return proProductionCostStructureService.getProduct();
    }

    @RequestMapping("/getShowNo.action/{selectProductValue}")
    @ResponseBody
    public Msg getShowNo(@PathVariable("selectProductValue") String selectSiteValue){
        return proProductionCostStructureService.getShowNo(selectSiteValue);
    }

    @RequestMapping("/getDatagridData/{selectTimeField}")
    @ResponseBody
    public Msg getDatagridData(@PathVariable("selectTimeField") String selectTimeField){
        return proProductionCostStructureService.getDatagridData(selectTimeField);
    }
}
