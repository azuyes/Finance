package com.bjut.ssh.controller.CostManagement;

import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.CompleteCostService;
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
@RequestMapping("/CompleteCost")
public class CompleteCostController {
    @Autowired
    private CompleteCostService completeCostService;

    @RequestMapping("/getEchartsTheme")
    @ResponseBody
    public Msg getEchartsTheme(){
        return completeCostService.getEchartsTheme();
    }

    @RequestMapping("/getDepartmentSelect.action")
    @ResponseBody
    public Msg getDepartmentSelect(){
        return completeCostService.getDepartmentSelect();
    }

    @RequestMapping("/getProductSelect.action")
    @ResponseBody
    public Msg getProductSelect(){
        return completeCostService.getProductSelect();
    }


    @RequestMapping("/getValData/{selectCompany}/{selectProduct}/{selectTime}")
    @ResponseBody
    public Msg getValData(@PathVariable("selectCompany") String selectCompany, @PathVariable("selectProduct") String selectProduct, @PathVariable("selectTime") String selectTime){
        return completeCostService.getValData(selectCompany,selectProduct,selectTime);
    }

    @RequestMapping("/getDatagridData/{selectCompany}/{selectProduct}/{selectTime}")
    @ResponseBody
    public Msg getDatagridData(@PathVariable("selectCompany") String selectCompany, @PathVariable("selectProduct") String selectProduct, @PathVariable("selectTime") String selectTime){
        return completeCostService.getDatagridData(selectCompany,selectProduct,selectTime);
    }

    @RequestMapping("/getItemNam")
    @ResponseBody
    public Msg getItemNam(){
        return completeCostService.getItemNam();
    }
}
