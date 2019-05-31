package com.bjut.ssh.controller.FinanceProcess;

import com.bjut.ssh.entity.Lsszzd;
import com.bjut.ssh.entity.Lszcdy;
import com.bjut.ssh.entity.Lszczd;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.FinanceProcess.AccountBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Title: AccountBookController
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/4/24 14:14
 * @Version: 1.0
 */
@Controller
@RequestMapping("/AccountBook")
public class AccountBookController {
    @Autowired
    private AccountBookService accountBookService;

    @RequestMapping("/getConfigsForBook")
    @ResponseBody
    public Msg getConfigsForBook(){
        return accountBookService.getConfigsForBook();
    }

    @RequestMapping("/queryAccountBook")
    @ResponseBody
    public List<Lszczd> queryAccountBook(){
        return accountBookService.queryAccountBook();
    }

    @RequestMapping(value = "/queryAccountBookDef/{AcontBookNo}", method = RequestMethod.POST)
    @ResponseBody
    public Msg queryAccountBookDef(@PathVariable("AcontBookNo") String AcontBookNo){
        List<Lszcdy> lszcdyList = accountBookService.queryAccountBookDef(AcontBookNo);
        return Msg.success().add("lszcdyList",lszcdyList);
    }

    @RequestMapping("/queryPrintFor")
    @ResponseBody
    public List<Lsszzd> queryPrintFor(){
        return accountBookService.queryPrintFor();
    }

    @RequestMapping("/saveAccountBook")
    @ResponseBody
    public Msg saveAccountBook(@RequestBody Lszczd lszczd){
        return accountBookService.saveAccountBook(lszczd);
    }

    @RequestMapping("/savePrintFor")
    @ResponseBody
    public Msg savePrintFor(@RequestBody Lsszzd lsszzd){
        return accountBookService.savePrintFor(lsszzd);
    }

    @RequestMapping("/saveAccountBookDef")
    @ResponseBody
    public Msg saveAccountBookDef(@RequestBody Lszcdy lszcdy){
        return accountBookService.saveAccountBookDef(lszcdy);
    }

    @RequestMapping("/editAccountBook")
    @ResponseBody
    public Msg editAccountBook(@RequestBody Lszczd lszczd){
        return accountBookService.editAccountBook(lszczd);
    }

    @RequestMapping("/editAccountBookDef")
    @ResponseBody
    public Msg editAccountBookDef(@RequestBody Lszcdy lszcdy){
        return accountBookService.editAccountBookDef(lszcdy);
    }

    @RequestMapping("/editPrintFor")
    @ResponseBody
    public Msg editPrintFor(@RequestBody Lsszzd lsszzd){
        return accountBookService.editPrintFor(lsszzd);
    }

    @RequestMapping(value="/delAccountBook/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Msg delAccountBook(@PathVariable("id") String id){
        return accountBookService.delAccountBook(id);
    }

    @RequestMapping(value="/delAccountBookDef/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Msg delAccountBookDef(@PathVariable("id") String id){
        return accountBookService.delAccountBookDef(id);
    }

    @RequestMapping(value="/delPrintFor/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Msg delPrintFor(@PathVariable("id") String id){
        return accountBookService.delPrintFor(id);
    }
}
