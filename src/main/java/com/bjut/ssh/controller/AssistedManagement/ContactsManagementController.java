package com.bjut.ssh.controller.AssistedManagement;

import com.bjut.ssh.entity.*;
import com.bjut.ssh.service.AssistedManagement.ContactsManagementServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Title: ContactsManagementController
 * @Description: 往来单位管理模块
 * @Author: lz
 * @CreateDate: 2018/4/9 13:51
 * @Version: 1.0
 */
@Controller
@RequestMapping("/ContactsManagement")
public class ContactsManagementController {

    @Autowired
    private ContactsManagementServie contactsManagementService;

    /**
    *@author lz
    *@Description 获取往来单位的信息，填充在初始化界面
    *@Date 2018/4/11 15:57contactsMa
    *@Param []
    *@return java.util.List<com.bjut.ssh.entity.LswldwQueryVo>，放回往来单位信息的包装对象
    **/
    @RequestMapping("/getContactsCompany")
    @ResponseBody
    public List<LswldwQueryVo> getContactsCompany(){
        List<LswldwQueryVo> lswldwQueryVos = contactsManagementService.getContactsCompany();
        return lswldwQueryVos;
//        return Msg.success().add("list",list);

    }

    @RequestMapping("/getContactsCategory")
    @ResponseBody
    public List<Lswlfl> getContactsCategory(String id){

        return contactsManagementService.getContactsCategory(id);
    }

    @RequestMapping("/saveContactsCompany")
    @ResponseBody
    public Msg saveContactsCategory(@RequestBody Lswldw lswldw){
       boolean bool = contactsManagementService.saveContactsCategory(lswldw);
       if(bool){
           return Msg.success();
       }else{
         return Msg.fail();
       }
    }

    @RequestMapping("/getContactsCompanyById/{id}")
    @ResponseBody
    public Msg getContactsCompanyById(@PathVariable("id") String companyNo ){
        Lswldw lswldw = contactsManagementService.getContactsCompanyById(companyNo);
        return Msg.success().add("lswldw",lswldw);
    }

    @RequestMapping("/updateContactsCompanyById")
    @ResponseBody
    public Msg updateContactsCompanyById(@RequestBody Lswldw lswldw ){
        Boolean bool = contactsManagementService.updateContactsCompanyById(lswldw);
        if(bool){
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }

    @RequestMapping("/getContactsCompanyOfDefin")
    @ResponseBody
    public List<Lswldw> getContactsCompanyOfDefin(){
        List<Lswldw> lswldw = contactsManagementService.getContactsCompanyOfDefin();
        return lswldw;
    }

    @RequestMapping("/getSpecialItemOfDefin")
    @ResponseBody
    public List<Lskmzd> getSpecialItemOfDefin(){
        List<Lskmzd> lskmzdList = contactsManagementService.getSpecialItemOfDefin();
        return lskmzdList;
    }

    @RequestMapping("/defineOfCompanyAndItem/{companyNos}/{itemNos}")
    @ResponseBody
    public Msg defineOfCompanyAndItem(@PathVariable("companyNos") String[] companyNos ,@PathVariable("itemNos") String[] itemNos){
        boolean bool = contactsManagementService.defineOfCompanyAndItem(companyNos,itemNos);
        if(bool){
            return Msg.success();
        }
        else{
            return Msg.fail();
        }
    }


    @RequestMapping("/getDefinedOfCompanyAndItem")
    @ResponseBody
    public List<Lswlje> getDefinedOfCompanyAndItem(){
        return contactsManagementService.getDefinedOfCompanyAndItem();
    }


    @RequestMapping("/judgeUnCheckedCompany/{companyNo}")
    @ResponseBody
    public Msg judgeUnCheckedCompany(@PathVariable("companyNo")String companyNo){
        boolean bool = contactsManagementService.judgeUnCheckedCompany(companyNo);
        if(bool){
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }

    @RequestMapping("/judgeUnCheckedItem/{itemNo}")
    @ResponseBody
    public Msg judgeUnCheckedItem(@PathVariable("itemNo")String itemNo){
        boolean bool = contactsManagementService.judgeUnCheckedItem(itemNo);
        if(bool){
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }

    /**
    *@author lz
    *@Description 删除往来单位
    *@Date 2018/7/12 10:46
    *@Param [companyNo] 单位编号
    *@return com.bjut.ssh.entity.Msg 返回是否删除成功状态
    **/
    @RequestMapping("/deleteContactsCompanyById/{id}")
    @ResponseBody
    public Msg deleteContactsCompanyById(@PathVariable("id") String companyNo ){
        boolean bool = contactsManagementService.deleteContactsCompanyById(companyNo);
        if(bool){
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }
}
