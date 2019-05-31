package com.bjut.ssh.controller.CostManagement;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.ProductionCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Title: ProductionCostController
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/7/13 14:14
 * @Version: 1.0
 */
@Controller
@RequestMapping("/ProductionCost")
public class ProductionCostController {
    @Autowired
    private ProductionCostService productionCostService;

    @RequestMapping("/getEchartsTheme")
    @ResponseBody
    public Msg getEchartsTheme(){
        return productionCostService.getEchartsTheme();
    }

    @RequestMapping("/getDepartmentSelect.action")
    @ResponseBody
    public Msg getDepartmentSelect(){
        return productionCostService.getDepartmentSelect();
    }

    @RequestMapping("/getProductSelect.action")
    @ResponseBody
    public Msg getProductSelect(){
        return productionCostService.getProductSelect();
    }


    @RequestMapping("/getValData/{selectCompany}/{selectProduct}/{selectTime}")
    @ResponseBody
    public Msg getValData(@PathVariable("selectCompany") String selectCompany, @PathVariable("selectProduct") String selectProduct, @PathVariable("selectTime") String selectTime){
        return productionCostService.getValData(selectCompany,selectProduct,selectTime);
    }

    @RequestMapping("/getDatagridData/{selectCompany}/{selectProduct}/{selectTime}")
    @ResponseBody
    public Msg getDatagridData(@PathVariable("selectCompany") String selectCompany, @PathVariable("selectProduct") String selectProduct, @PathVariable("selectTime") String selectTime){
        return productionCostService.getDatagridData(selectCompany,selectProduct,selectTime);
    }

    @RequestMapping("/getItemNam")
    @ResponseBody
    public Msg getItemNam(){
        return productionCostService.getItemNam();
    }
}
