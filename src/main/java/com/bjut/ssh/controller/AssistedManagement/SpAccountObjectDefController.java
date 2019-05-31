package com.bjut.ssh.controller.AssistedManagement;

import com.bjut.ssh.entity.Lshszd;
import com.bjut.ssh.entity.LshszdQueryVo;
import com.bjut.ssh.entity.Lskmzd;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.AssistedManagement.SpAccountObjectDefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Title: SpAccountObjectDefController
 * @Description: 核算对象定义
 * @Author: lz
 * @CreateDate: 2018/5/7 12:29
 * @Version: 1.0
 */
@Controller
@RequestMapping("/SpAccountObjectDef")
public class SpAccountObjectDefController {

    @Autowired
    private SpAccountObjectDefService spAccountObjectDefService;

    /**
    *@author lz
    *@Description 根据分类编号获取对应核算对象
    *@Date 2018/5/8 17:09
    *@Param [catNo] 分类编号
    *@return com.bjut.ssh.entity.Msg
    **/
    @RequestMapping("/getSpAccountCategoryById/{catNo}")
    @ResponseBody
    public Msg getSpAccountCategoryById(@PathVariable("catNo") String catNo ){
        LshszdQueryVo lshszdQueryVo = spAccountObjectDefService.getSpAccountCategoryById(catNo);
        if(lshszdQueryVo == null){
            return Msg.fail();
        }
        else{
            return Msg.success().add("lshszdQueryVo",lshszdQueryVo);
        }
    }

    /**
    *@author lz
    *@Description T
    *@Date 2018/5/8 17:13
    *@Param [id, levelFLag]
    *@return com.bjut.ssh.entity.Msg
    **/
    @RequestMapping(value="/querySpAccountByLevel/{no}/{spLevel}/{catNo}",method = RequestMethod.POST)
    @ResponseBody
    public Msg querySpAccountByLevel(@PathVariable("no") String id ,@PathVariable("spLevel")String levelFLag,@PathVariable("catNo")String catNo){
        List<Lshszd> lshszds = spAccountObjectDefService.queryContactsCategoryByLevel(id,levelFLag,catNo);
        return Msg.success().add("lshszds",lshszds);
    }

    @RequestMapping(value="/saveSpAccountObjectDef")
    @ResponseBody
    public Msg saveSpAccountObjectDef(@RequestBody Lshszd lshszd){
        return  spAccountObjectDefService.saveSpAccountObjectDef(lshszd);
    }

    @RequestMapping("/delSpAccountObjectDef/{ids}/{spLevel}/{catNo}")
    @ResponseBody
    public Msg delSpAccountObjectDef(@PathVariable("ids") String id,@PathVariable("spLevel") String spLevel,@PathVariable("catNo") String catNo){
        return  spAccountObjectDefService.delSpAccountObjectDef(id,spLevel,catNo);
    }


    @RequestMapping(value = "/queryCaptionOfAccountByLevel/{id}/{levelFlag}",method = RequestMethod.POST)
    @ResponseBody
    public Msg queryCaptionOfAccountByLevel(@PathVariable("id") String id,@PathVariable("levelFlag") String levelFlag){
        List<Lskmzd> lskmzdList = spAccountObjectDefService.queryCaptionOfAccountByLevel(id, levelFlag);
        return Msg.success().add("lskmzdList",lskmzdList);
    }


    @RequestMapping(value = "/queryCaptionOfAccountByLevel1/{id}/{levelFlag}",method = RequestMethod.POST)
    @ResponseBody
    public Msg queryCaptionOfAccountByLevel1(@PathVariable("id") String id,@PathVariable("levelFlag") String levelFlag){
        List<Lskmzd> lskmzdList = spAccountObjectDefService.queryCaptionOfAccountByLevel1(id, levelFlag);
        return Msg.success().add("lskmzdList",lskmzdList);
    }



}
