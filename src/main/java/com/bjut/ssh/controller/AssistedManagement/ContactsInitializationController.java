package com.bjut.ssh.controller.AssistedManagement;

import com.bjut.ssh.entity.Lskmzd;
import com.bjut.ssh.entity.LswljeOrLswlslQueryVo;
import com.bjut.ssh.entity.LswljeQueryVo;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.AssistedManagement.ContactsInitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation.ANONYMOUS.required;

/**
 * @Title: ContactsInitializationController
 * @Description:往来初始化
 * @Author: lz
 * @CreateDate: 2018/4/18 16:23
 * @Version: 1.0
 */
@Controller
@RequestMapping("/ContactsInitialization")
public class ContactsInitializationController {
    @Autowired
    private ContactsInitializationService contactsInitializationService;

    /**
    *@author lz
    *@Description 根据前台输入或者选择的科目编号进行判断是否是明细和往来科目，若不是返回false，否则返回查询lskmzd,lskmsl,lswlsl,lswlje等数据。
    *@Date 2018/4/27 10:01
    *@Param [itemNo]
    *@return com.bjut.ssh.entity.Msg
    **/
    @RequestMapping("/getItemByIdAndCompany/{itemNo}")
    @ResponseBody
    public Msg getItemByIdAndCompany(@PathVariable("itemNo") String itemNo ){
        LswljeOrLswlslQueryVo lswljeOrLswlsl = null;
        lswljeOrLswlsl = contactsInitializationService.getItemByIdAndCompany(itemNo);
        if(lswljeOrLswlsl == null){
            return Msg.fail();
        }
        else{
            return Msg.success().add("LswljeOrLswlsl",lswljeOrLswlsl);
        }
    }


    /**
    *@author lz
    *@Description 更新lswlje表中的数据
    *@Date 2018/4/27 10:02 
    *@Param [lswljeQueryVos]
    *@return com.bjut.ssh.entity.Msg
    **/
    @RequestMapping("/updateContactsInitial")
    @ResponseBody

    public Msg updateContactsInitial(@RequestBody List<LswljeQueryVo> lswljeQueryVos){
        boolean bool = contactsInitializationService.updateContactsInitial(lswljeQueryVos);
        if(bool){
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }

    /**
    *@author lz
    *@Description 更新lswlje,lswlsl中的数据
    *@Date 2018/4/27 10:02
    *@Param [lswljeQueryVos]
    *@return com.bjut.ssh.entity.Msg
    **/
    @RequestMapping("/updateContactsInitialSl")
    @ResponseBody

    public Msg updateContactsInitialSl(@RequestBody List<LswljeQueryVo> lswljeQueryVos){
        boolean bool = contactsInitializationService.updateContactsInitialSl(lswljeQueryVos);
        if(bool){
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }

    @RequestMapping(value = "/queryCaptionOfAccountByLevel/{id}/{levelFlag}",method = RequestMethod.POST)
    @ResponseBody
    public Msg queryCaptionOfAccountByLevel(@PathVariable("id") String id,@PathVariable("levelFlag") String levelFlag){
        List<Lskmzd> lskmzdList = contactsInitializationService.queryCaptionOfAccountByLevel(id, levelFlag);
        return Msg.success().add("lskmzdList",lskmzdList);
    }





}
