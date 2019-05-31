package com.bjut.ssh.controller.CostManagement;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.BasicOperatingExpensesService;
import com.bjut.ssh.service.CostManagement.ElseSetService;
import com.bjut.ssh.service.CostManagement.QuotaSetService;
import com.bjut.ssh.service.CostManagement.ThemeSetService;
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
@RequestMapping("/ThemeSet")
public class ThemeSetController {
    @Autowired
    private ThemeSetService themeSetService;

    @RequestMapping("/getGraphAll")
    @ResponseBody
    public Msg getGraphAll(){
        return themeSetService.getGraphAll();
    }

    @RequestMapping("/addThemeInfo/{chartIDSelect}/{chartThemeSelect}")
    @ResponseBody
    public Msg addThemeInfo(@PathVariable("chartIDSelect") String chartIDSelect, @PathVariable("chartThemeSelect") String chartThemeSelect){
        return themeSetService.addThemeInfo(chartIDSelect, chartThemeSelect);
    }
}
