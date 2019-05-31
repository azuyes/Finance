package com.bjut.ssh.controller.FinanceProcess;

import com.bjut.ssh.entity.Lsconf;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.FinanceProcess.OtherEnvironmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Title: OtherEnvironmentController
 * @Description: 其他环境设置
 * @Author: czh
 * @CreateDate: 2018/3/29 10:04
 * @Version: 1.0
 */
@Controller
@RequestMapping("/OtherEnvironment")
public class OtherEnvironmentController {
    @Autowired
    private OtherEnvironmentService otherEnvironmentService;

    private List<Lsconf> configs = new LinkedList<Lsconf>();

//    @RequestMapping("/setOtherEnvironment")
//    public void setOtherEnvironment(@RequestBody Lsconf config) {
//        otherEnvironmentService.setOtherEnvironment(config);
//    }
    @RequestMapping("/setOtherEnvironment")
    public @ResponseBody
    Msg setOtherEnvironment(@RequestBody List<Lsconf> lsconfList) {
        return otherEnvironmentService.setOtherEnvironment(lsconfList);
    }

    @RequestMapping("/getConfigs")
    public @ResponseBody Msg getConfigs(@RequestBody List<Lsconf> lsconfList){
        List<String> config_list = new ArrayList<>();
        for(Lsconf lsconf : lsconfList){
            config_list.add(lsconf.getConfKey());
        }
        return otherEnvironmentService.getConfigs(config_list);
    }
}
