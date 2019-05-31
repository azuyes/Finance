package com.bjut.ssh.controller.FinanceProcess;

import com.bjut.ssh.entity.*;
import com.bjut.ssh.service.FinanceProcess.CaptionOfAccountService;
import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Title: CaptionOfAccountController
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/4/10 13:52
 * @Version: 1.0
 */
@Controller
@RequestMapping("/CaptionOfAccount")
public class CaptionOfAccountController {
    @Autowired
    private CaptionOfAccountService captionOfAccountService;

    @RequestMapping("/getConfigsForCap")
    @ResponseBody
    public Msg getConfigsForCap(){
        return captionOfAccountService.getConfigsForCap();
    }
    
    /**
    *@author czh
    *@Description TODO
    *@Date 2018/4/10 13:56 
    *@Param []
    *@return java.lang.String
    **/
    @RequestMapping("/getSubjectStructure")
    @ResponseBody
    public String getSubjectStructure(){
        return captionOfAccountService.getSubjectStructure();
    }

    /**
    *@author czh
    *@Description TODO
    *@Date 2018/4/12 17:23 
    *@Param []
    *@return java.lang.String
    **/
    @RequestMapping("/getBeginMonth")
    @ResponseBody
    public String getBeginMonth(){
        return captionOfAccountService.getBeginMonth();
    }

    @RequestMapping("/getPrecisions")
    @ResponseBody
    public Msg getPrecisions(){
        List<String> prec_list = captionOfAccountService.getPrecisions();
        return Msg.success().add("prec_list",prec_list);
    }

    @RequestMapping(value = "/queryAllCaptionOfAccountByLevel/{id}/{levelFlag}",method = RequestMethod.POST)
    @ResponseBody
    public Msg queryAllCaptionOfAccountByLevel(@PathVariable("id") String id,@PathVariable("levelFlag") String levelFlag){
        return captionOfAccountService.queryAllCaptionOfAccountByLevel(id, levelFlag);
    }
    
    /**
    *@author czh
    *@Description TODO
    *@Date 2018/4/10 16:27
    *@Param [id, levelFlag]
    *@return java.util.List<com.bjut.ssh.entity.Lskmzd>
    **/
    @RequestMapping(value = "/queryCaptionOfAccountByLevel/{id}/{levelFlag}",method = RequestMethod.POST)
    @ResponseBody
    public Msg queryCaptionOfAccountByLevel(@PathVariable("id") String id,@PathVariable("levelFlag") String levelFlag){
        List<Lskmzd> lskmzdList = captionOfAccountService.queryCaptionOfAccountByLevel(id, levelFlag);
        return Msg.success().add("lskmzdList",lskmzdList);
    }

    @RequestMapping(value="/addAllCaptionOfAccount")
    @ResponseBody
    public Msg addAllCaptionOfAccount(@RequestBody LskmzdNLskmslQueryVo lskmzdNLskmslQueryVo){
        return captionOfAccountService.addAllCaptionOfAccount(lskmzdNLskmslQueryVo);
    }
    
    /**
    *@author czh
    *@Description TODO
    *@Date 2018/4/11 15:15 
    *@Param [lskmzd]
    *@return com.bjut.ssh.entity.Msg
    **/
    @RequestMapping(value="/addCaptionOfAccount")
    @ResponseBody
    public Msg addCaptionOfAccount(@RequestBody Lskmzd lskmzd){
        return captionOfAccountService.addCaptionOfAccount(lskmzd);
    }

    /**
    *@author czh
    *@Description TODO
    *@Date 2018/4/16 16:43 
    *@Param [lskmsl]
    *@return com.bjut.ssh.entity.Msg
    **/
    @RequestMapping(value="/addCaptionOfAccountQuantity")
    @ResponseBody
    public Msg addCaptionOfAccountQuantity(@RequestBody Lskmsl lskmsl){
        return captionOfAccountService.addCaptionOfAccountQuantity(lskmsl);
    }

    /**
    *@author czh
    *@Description TODO
    *@Date 2018/4/18 9:26 
    *@Param [itemNo, level, is_quan]
    *@return com.bjut.ssh.entity.Msg
    **/
    @RequestMapping(value="/delCaptionOfAccount/{id}/{level}/{is_quan}",method = RequestMethod.POST)
    @ResponseBody
    public Msg delCaptionOfAccount(@PathVariable("id") String itemNo, @PathVariable("level") String level, @PathVariable("is_quan") String is_quan){
        return captionOfAccountService.delCaptionOfAccount(itemNo, level, is_quan);
    }
    
    /**
    *@author czh
    *@Description TODO
    *@Date 2018/4/18 9:28 
    *@Param []
    *@return java.util.List<com.bjut.ssh.entity.Lshsfl>
    **/
    @RequestMapping(value="/querySpecialAcc")
    @ResponseBody
    public List<Lshsfl> querySpecialAcc(){
        return captionOfAccountService.querySpecialAcc();
    }

    @RequestMapping(value="/editAllCaptionOfAccount")
    @ResponseBody
    public Msg editAllCaptionOfAccount(@RequestBody LskmzdNLskmslQueryVo lskmzdNLskmslQueryVo){
        return captionOfAccountService.editAllCaptionOfAccount(lskmzdNLskmslQueryVo);
    }

    /**
    *@author czh
    *@Description TODO
    *@Date 2018/4/19 14:12
    *@Param [lskmzd]
    *@return com.bjut.ssh.entity.Msg
    **/
    @RequestMapping(value="/editCaptionOfAccount")
    @ResponseBody
    public Msg editCaptionOfAccount(@RequestBody Lskmzd lskmzd){
        return captionOfAccountService.editCaptionOfAccount(lskmzd);
    }

    @RequestMapping(value="/editCaptionOfAccountQty")
    @ResponseBody
    public Msg editCaptionOfAccountQty(@RequestBody Lskmsl lskmsl){
        return captionOfAccountService.editCaptionOfAccountQty(lskmsl);
    }

    @RequestMapping(value="/getCaptionWithSpAcc/{id}/{levelFlag}",method = RequestMethod.POST)
    @ResponseBody
    public Msg getCaptionWithSpAcc(@PathVariable("id") String id,@PathVariable("levelFlag") String levelFlag){
        List<LskmzdQueryVo> lskmzdQueryVos = captionOfAccountService.getCaptionWithSpAcc(id,levelFlag);
        return Msg.success().add("lskmzdQueryVos",lskmzdQueryVos);
    }

}
