package com.bjut.ssh.controller.CostManagement;

import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.DepartmentBudgetService;
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
@RequestMapping("/DepartmentBudget")
public class DepartmentBudgetController {
    @Autowired
    private DepartmentBudgetService departmentBudgetService;

    @RequestMapping("/DepartmentBudget_getMaxLevel")
    @ResponseBody
    public Msg getMaxLevel(){
        return departmentBudgetService.getMaxLevel();
    }

    @RequestMapping("/DepartmentBudget_getAnnualBudget/{Level_string}/{ItemID}")
    @ResponseBody
    public Msg getAnnualBudget(@PathVariable("Level_string") String Level_string, @PathVariable("ItemID") String ItemID){
        return departmentBudgetService.getAnnualBudget(Level_string,ItemID);
    }

    @RequestMapping("/get_itemSpBudgetInfo/{selectedItemID}")
    @ResponseBody
    public Msg getItemSpBudgetInfo(@PathVariable("selectedItemID") String selectedItemID){
        return departmentBudgetService.getItemSpBudgetInfo(selectedItemID);
    }

    @RequestMapping("/saveSpItemBudgetInfo/{annualBudget}/{itemId}/{spNo1}/{spNo2}")
    @ResponseBody
    public Msg saveSpItemBudgetInfo(@PathVariable("annualBudget") String annualBudget,@PathVariable("itemId") String itemId,@PathVariable("spNo1") String spNo1,@PathVariable("spNo2") String spNo2){
        return departmentBudgetService.saveSpItemBudgetInfo(annualBudget,itemId,spNo1,spNo2);
    }
}
