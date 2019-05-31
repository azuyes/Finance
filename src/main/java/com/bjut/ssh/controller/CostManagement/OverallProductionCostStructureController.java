package com.bjut.ssh.controller.CostManagement;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.BasicOperatingExpensesService;
import com.bjut.ssh.service.CostManagement.CompleteCostService;
import com.bjut.ssh.service.CostManagement.OverallProductionCostStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Title: OverallProductionCostStructureController
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/7/13 14:14
 * @Version: 1.0
 */
@Controller
@RequestMapping("/OverallProductionCostStructure")
public class OverallProductionCostStructureController {
    @Autowired
    private OverallProductionCostStructureService overallProductionCostStructureService;

    @RequestMapping("/getEchartsTheme")
    @ResponseBody
    public Msg getEchartsTheme(){
        return overallProductionCostStructureService.getEchartsTheme();
    }

    @RequestMapping("/getDepartmentSelect.action")
    @ResponseBody
    public Msg getDepartmentSelect(){
        return overallProductionCostStructureService.getDepartmentSelect();
    }

    @RequestMapping("/getProductSelect.action")
    @ResponseBody
    public Msg getProductSelect(){
        return overallProductionCostStructureService.getProductSelect();
    }


    @RequestMapping("/getValData/{selectCompany}/{selectProduct}/{selectTime}")
    @ResponseBody
    public Msg getValData(@PathVariable("selectCompany") String selectCompany, @PathVariable("selectProduct") String selectProduct, @PathVariable("selectTime") String selectTime){
        return overallProductionCostStructureService.getValData(selectCompany,selectProduct,selectTime);
    }

    @RequestMapping("/getDatagridData/{selectCompany}/{selectProduct}/{selectTime}")
    @ResponseBody
    public Msg getDatagridData(@PathVariable("selectCompany") String selectCompany, @PathVariable("selectProduct") String selectProduct, @PathVariable("selectTime") String selectTime){
        return overallProductionCostStructureService.getDatagridData(selectCompany,selectProduct,selectTime);
    }
}
