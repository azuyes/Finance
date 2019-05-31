package com.bjut.ssh.controller.CostManagement;

import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.ProProductionCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Title: ProProductionCostController
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/8/26 14:20
 * @Version: 1.0
 */
@Controller
@RequestMapping("/ProProductionCost")
public class ProProductionCostController {
    @Autowired
    private ProProductionCostService proProductionCostService;

    @RequestMapping("/getValData/{selectSalesMethod}/{selectTimeField}")
    @ResponseBody
    public Msg getChartsData(@PathVariable("selectSalesMethod") String selectSalesMethod, @PathVariable("selectTimeField") String selectTimeField){
        return proProductionCostService.getValData(selectSalesMethod,selectTimeField);
    }

    @RequestMapping("/getItemNam")
    @ResponseBody
    public Msg getItemsName(){
        return proProductionCostService.getItemsName();
    }

    @RequestMapping("/getDatagridData/{selectTimeField}")
    @ResponseBody
    public Msg getDatagridData(@PathVariable("selectTimeField") String selectTimeField){
        return proProductionCostService.getDatagridData(selectTimeField);
    }
}
