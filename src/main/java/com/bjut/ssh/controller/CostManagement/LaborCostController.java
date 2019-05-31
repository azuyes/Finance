package com.bjut.ssh.controller.CostManagement;

import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.LaborCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Title: DepreciationAndDepletionController
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/7/13 14:14
 * @Version: 1.0
 */
@Controller
@RequestMapping("/LaborCost")
public class LaborCostController {
    @Autowired
    private LaborCostService laborCostService;

    @RequestMapping("/getEchartsTheme")
    @ResponseBody
    public Msg getEchartsTheme(){
        return laborCostService.getEchartsTheme();
    }

    @RequestMapping("/getValData/{selectTime}")
    @ResponseBody
    public Msg getValData(@PathVariable("selectTime") String selectTime){
        return laborCostService.getValData(selectTime);
    }

    @RequestMapping("/getDatagridData/{selectTime}")
    @ResponseBody
    public Msg getDatagridData(@PathVariable("selectTime") String selectTime){
        return laborCostService.getDatagridData(selectTime);
    }
}
