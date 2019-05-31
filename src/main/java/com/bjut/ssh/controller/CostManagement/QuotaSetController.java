package com.bjut.ssh.controller.CostManagement;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.BasicOperatingExpensesService;
import com.bjut.ssh.service.CostManagement.QuotaSetService;
import jdk.nashorn.internal.runtime.JSONListAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

/**
 * @Title: QuotaSetController
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/7/13 14:14
 * @Version: 1.0
 */
@Controller
@RequestMapping("/QuotaSet")
public class QuotaSetController {
    @Autowired
    private QuotaSetService quotaSetService;

    @RequestMapping("/getGraphAll")
    @ResponseBody
    public Msg getGraphAll(){
        return quotaSetService.getGraphAll();
    }

    @RequestMapping("/getGraphInfo/{gridNo}")
    @ResponseBody
    public Msg getGraphInfo(@PathVariable("gridNo") String gridNo){
        return quotaSetService.getGraphInfo(gridNo);
    }

    @RequestMapping("/deleteGraphInfo/{gridNo}/{itemID}/{itemType}")
    @ResponseBody
    public Msg deleteGraphInfo(@PathVariable("gridNo") String gridNo,@PathVariable("itemID") String itemNo,@PathVariable("itemType") String itemType){
        return quotaSetService.deleteGraphInfo(gridNo,itemNo,itemType);
    }

    @RequestMapping("/addGraphInfo/{gridNo}/{itemID}/{itemType}/{itemFunction}")
    @ResponseBody
    public Msg addGraphInfo(@PathVariable("gridNo") String gridNo,@PathVariable("itemID") String itemNo,@PathVariable("itemType") String itemType,@PathVariable("itemFunction") String itemFunction){
        return quotaSetService.addGraphInfo(gridNo,itemNo,itemType, itemFunction);
    }

    @RequestMapping("/getItemFunctionSelect/{gridNo}")
    @ResponseBody
    public Msg getItemFunctionSelect(@PathVariable("gridNo") String gridNo){
        return quotaSetService.getItemFunctionSelect(gridNo);
    }

    @RequestMapping("/getItemTypeSelect/{gridNo}/{selectItemFunctionValue}")
    @ResponseBody
    public Msg getItemTypeSelect(@PathVariable("gridNo") String gridNo,@PathVariable("selectItemFunctionValue") String selectItemFunctionValue){
        return quotaSetService.getItemTypeSelect(gridNo,selectItemFunctionValue);
    }

    @RequestMapping("/getItemNameSelect/{selectItemTypeValue}")
    @ResponseBody
    public Msg getItemNameSelect(@PathVariable("selectItemTypeValue") String selectItemTypeValue){
        return quotaSetService.getItemNameSelect(selectItemTypeValue);
    }
}
