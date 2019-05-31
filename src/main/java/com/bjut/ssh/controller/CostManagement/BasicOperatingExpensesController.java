package com.bjut.ssh.controller.CostManagement;

import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.BasicOperatingExpensesService;
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
@RequestMapping("/BasicOperatingExpenses")
public class BasicOperatingExpensesController {
    @Autowired
    private BasicOperatingExpensesService basicOperatingExpensesService;

    @RequestMapping("/getDepartmentSelect.action")
    @ResponseBody
    public Msg getDepartmentSelect(){
        return basicOperatingExpensesService.getDepartmentSelect();
    }

    @RequestMapping("/getProductSelect.action")
    @ResponseBody
    public Msg getProductSelect(){
        return basicOperatingExpensesService.getProductSelect();
    }

    @RequestMapping("/getValData/{selectCompany}/{selectProduct}/{selectTime}")
    @ResponseBody
    public Msg getValData(@PathVariable("selectCompany") String selectCompany, @PathVariable("selectProduct") String selectProduct, @PathVariable("selectTime") String selectTime){
        return basicOperatingExpensesService.getValData(selectCompany,selectProduct,selectTime);
    }

    @RequestMapping("/getDatagridData/{selectCompany}/{selectProduct}/{selectTime}")
    @ResponseBody
    public Msg getDatagridData(@PathVariable("selectCompany") String selectCompany, @PathVariable("selectProduct") String selectProduct, @PathVariable("selectTime") String selectTime){
        return basicOperatingExpensesService.getDatagridData(selectCompany,selectProduct,selectTime);
    }

    @RequestMapping("/getItemNam")
    @ResponseBody
    public Msg getItemNam(){
        return basicOperatingExpensesService.getItemNam();
    }

    @RequestMapping("/getEchartsTheme")
    @ResponseBody
    public Msg getEchartsTheme(){
        return basicOperatingExpensesService.getEchartsTheme();
    }

    @RequestMapping("/ExportExcel/{bodyData}")
    @ResponseBody
    public Msg ExportExcel(@PathVariable("bodyData") String bodyData){
        return basicOperatingExpensesService.ExportExcel(bodyData);
    }
}
