package com.bjut.ssh.controller.AssistedManagement;

import com.bjut.ssh.entity.*;
import com.bjut.ssh.service.AssistedManagement.SpAccountInitializationService;
import com.bjut.ssh.service.AssistedManagement.SpAccountObjectConnectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Title: SpAccountInitializationController
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/5/28 9:43
 * @Version: 1.0
 */
@Controller
@RequestMapping("/SpAccountInitialization")
public class SpAccountInitializationController {

    @Autowired
    private SpAccountInitializationService spAccountInitializationService;

    @RequestMapping(value="/getCaptionOfSpAccount/{id}/{levelFlag}",method = RequestMethod.POST)
    @ResponseBody
    public Msg getCaptionOfSpAccount(@PathVariable("id") String id, @PathVariable("levelFlag") String levelFlag){
        List<LskmzdQueryVo> lskmzdQueryVos = spAccountInitializationService.getCaptionOfSpAccount(id,levelFlag);
        return Msg.success().add("lskmzdQueryVos",lskmzdQueryVos);
    }

    @RequestMapping("/getItemByIdAndSpecial1/{itemNo}/{spLevel}")
    @ResponseBody
    public Msg getItemByIdAndSpecial1(@PathVariable("itemNo") String itemNo,@PathVariable("spLevel") String spLevel ){
        LshsjeOrLshsslForInit lshsjeOrLshsslForInit = null;
        lshsjeOrLshsslForInit = spAccountInitializationService.getItemByIdAndSpecial1(itemNo,spLevel);
        return Msg.success().add("lshsjeOrLshsslForInit",lshsjeOrLshsslForInit);
    }

    @RequestMapping(value="/querySpAccountByLevel/{no}/{spLevel}/{itemNo}",method = RequestMethod.POST)
    @ResponseBody
    public Msg querySpAccountByLevel(@PathVariable("no") String id ,@PathVariable("spLevel")String levelFLag,@PathVariable("itemNo")String itemNo){
        List<LshsjeOrLshsslQueryVo> lshsjeOrLshsslQueryVos = spAccountInitializationService.queryContactsCategoryByLevel(id,levelFLag,itemNo);
        return Msg.success().add("lshsjeOrLshsslQueryVos",lshsjeOrLshsslQueryVos);
    }



    @RequestMapping("/updateSpCount1/{itemNo}")
    @ResponseBody

    public Msg updateSpCount1(@RequestBody List<LshsjeOrLshsslQueryVo> lshsjeOrLshsslQueryVos,@PathVariable("itemNo") String itemNo){
        boolean bool = spAccountInitializationService.updateSpCount1(lshsjeOrLshsslQueryVos,itemNo);
        if(bool){
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }

    @RequestMapping("/getItemByIdAndSpecial2/{itemNo}")
    @ResponseBody
    public Msg getItemByIdAndSpecial2(@PathVariable("itemNo") String itemNo){
        LshsjeOrLshsslForInit lshsjeOrLshsslForInit = null;
        lshsjeOrLshsslForInit = spAccountInitializationService.getItemByIdAndSpecial2(itemNo);
        return Msg.success().add("lshsjeOrLshsslForInit",lshsjeOrLshsslForInit);
    }


    @RequestMapping("/updateSpCount2/{itemNo}")
    @ResponseBody

    public Msg updateSpCount2(@RequestBody List<LshsjeOrLshsslQueryVo> lshsjeOrLshsslQueryVos,@PathVariable("itemNo") String itemNo){
        boolean bool = spAccountInitializationService.updateSpCount2(lshsjeOrLshsslQueryVos,itemNo);
        if(bool){
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }




}
