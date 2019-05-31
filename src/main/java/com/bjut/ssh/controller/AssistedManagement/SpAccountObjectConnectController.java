package com.bjut.ssh.controller.AssistedManagement;

import com.bjut.ssh.entity.*;
import com.bjut.ssh.service.AssistedManagement.SpAccountObjectConnectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Title: SpAccountObjectConnectController
 * @Description: 核算对象关联
 * @Author: lz
 * @CreateDate: 2018/5/14 10:55
 * @Version: 1.0
 */
@Controller
@RequestMapping("/SpAccountObjectConnect")
public class SpAccountObjectConnectController {

    @Autowired
    private SpAccountObjectConnectService spAccountObjectConnectService;

    /**
    *@author lz
    *@Description TODO
    *@Date 2018/5/18 16:38 
    *@Param [id, type]
    *@return java.util.List<com.bjut.ssh.entity.Lshszd>
    **/
    @RequestMapping("/getSpAccountName")
    @ResponseBody
    public List<Lshszd> getSpAccountName(String id,String type){

        return spAccountObjectConnectService.getSpAccountName(id,type);
    }

    /**
    *@author lz
    *@Description TODO
    *@Date 2018/5/18 16:38 
    *@Param [itemNo]
    *@return com.bjut.ssh.entity.Msg
    **/
    @RequestMapping("/getSpItemById/{itemNo}")
    @ResponseBody
    public Msg getSpItemById(@PathVariable("itemNo") String itemNo ){
        Lskmzd lskmzd = null;
        lskmzd = spAccountObjectConnectService.getSpItemById(itemNo);
        String catName1,catName2;
        if(lskmzd.getSupAcc1() == null){
            catName1 = null;
        }else{
            catName1 = spAccountObjectConnectService.getCatNameBySpItemNo(lskmzd.getSupAcc1());
        }
        if(lskmzd.getSupAcc2() == null){
            catName2 = null;
        }else{
            catName2 = spAccountObjectConnectService.getCatNameBySpItemNo(lskmzd.getSupAcc2());
        }
        if(lskmzd == null){
            return Msg.fail();
        }
        else{
            return Msg.success().add("lskmzd",lskmzd).add("catName1",catName1).add("catName2",catName2);
        }
    }

    /**
    *@author lz
    *@Description TODO
    *@Date 2018/5/18 16:38
    *@Param []
    *@return java.util.List<com.bjut.ssh.entity.Lshsfl>
    **/
    @RequestMapping("/getSpAccountCategory")
    @ResponseBody
    public List<Lshsfl> getSpAccountCategory(){
        return spAccountObjectConnectService.getSpAccountCategory();
    }

    /**
    *@author lz
    *@Description 关联科目和专项核算对象1
    *@Date 2018/5/18 16:38 
    *@Param [spNos1, itemNo]
    *@return com.bjut.ssh.entity.Msg
    **/
    @RequestMapping("/spAccountConnect1/{spNos1}/{itemNo}")
    @ResponseBody
    public Msg spAccountConnect1(@PathVariable("spNos1") String[] spNos1 ,@PathVariable("itemNo") String itemNo){
        boolean bool = spAccountObjectConnectService.spAccountConnect1(spNos1,itemNo);
        if(bool){
            return Msg.success();
        }
        else{
            return Msg.fail();
        }
    }

    /**
    *@author lz
    *@Description 关联科目和专项核算对象1，专项核算对象2
    *@Date 2018/5/18 16:38
    *@Param [spNos1, spNos2, itemNo]
    *@return com.bjut.ssh.entity.Msg
    **/
    @RequestMapping("/spAccountConnect2/{spNos1}/{spNos2}/{itemNo}")
    @ResponseBody
    public Msg spAccountConnect2(@PathVariable("spNos1") String[] spNos1 ,@PathVariable("spNos2") String[] spNos2,@PathVariable("itemNo") String itemNo){
        boolean bool = spAccountObjectConnectService.spAccountConnect2(spNos1,spNos2,itemNo);
        if(bool){
            return Msg.success();
        }
        else{
            return Msg.fail();
        }
    }

    @RequestMapping("/getAuthority1/{itemNo}")
    @ResponseBody
    public String getAuthority1(@PathVariable("itemNo")String itemNo){
        return spAccountObjectConnectService.getAuthority1(itemNo);
    }

    @RequestMapping("/getAuthority2/{itemNo}")
    @ResponseBody
    public String getAuthority2(@PathVariable("itemNo")String itemNo){
        return spAccountObjectConnectService.getAuthority2(itemNo);
    }

    @RequestMapping("/judgeUnChecked/{spNo}/{itemNo}")
    @ResponseBody
    public Msg judgeUnChecked(@PathVariable("spNo")String spNo,@PathVariable("itemNo")String itemNo){
        boolean bool = spAccountObjectConnectService.judgeUnChecked(spNo,itemNo);
        if(bool){
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }



}
