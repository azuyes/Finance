package com.bjut.ssh.controller.CostManagement;

import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.AnnualBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Title: QuotaSetController
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/7/13 14:14
 * @Version: 1.0
 */
@Controller
@RequestMapping("/AnnualBudget")
public class AnnualBudgetController {
    @Autowired
    private AnnualBudgetService annualBudgetService;

    @RequestMapping("/getAnnualBudget/{Level_string}/{ItemID}")
    @ResponseBody
    public Msg getAnnualBudget(@PathVariable("Level_string") String Level_string, @PathVariable("ItemID") String ItemID){
        return annualBudgetService.getAnnualBudget(Level_string,ItemID);
    }

    @RequestMapping("/saveAnnualBudgetInfo/{annualBudget}/{itemID}")
    @ResponseBody
    public Msg saveAnnualBudgetInfo(@PathVariable("annualBudget") String annualBudget, @PathVariable("itemID") String itemNo){
        return annualBudgetService.saveAnnualBudgetInfo(annualBudget,itemNo);
    }

    @RequestMapping("/getLevelMax")
    @ResponseBody
    public Msg getLevelMax(){
        return annualBudgetService.getLevelMax();
    }
}
