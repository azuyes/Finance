package com.bjut.ssh.controller.CostManagement;

import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.ProBasicOperatingExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Title: ProBasicOperatingExpensesController
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/8/26 14:20
 * @Version: 1.0
 */
@Controller
@RequestMapping("/ProBasicOperatingExpenses")
public class ProBasicOperatingExpensesController {
    @Autowired
    private ProBasicOperatingExpensesService proBasicOperatingExpensesService;

    @RequestMapping("/getValData/{selectSalesMethod}/{selectTimeField}")
    @ResponseBody
    public Msg getChartsData(@PathVariable("selectSalesMethod") String selectSalesMethod, @PathVariable("selectTimeField") String selectTimeField){
        return proBasicOperatingExpensesService.getValData(selectSalesMethod,selectTimeField);
    }

    @RequestMapping("/getItemNam")
    @ResponseBody
    public Msg getItemsName(){
        return proBasicOperatingExpensesService.getItemsName();
    }

    @RequestMapping("/getDatagridData/{selectTimeField}")
    @ResponseBody
    public Msg getDatagridData(@PathVariable("selectTimeField") String selectTimeField){
        return proBasicOperatingExpensesService.getDatagridData(selectTimeField);
    }
}
