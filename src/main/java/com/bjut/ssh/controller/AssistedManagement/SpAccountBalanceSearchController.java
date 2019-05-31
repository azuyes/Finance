package com.bjut.ssh.controller.AssistedManagement;

import com.bjut.ssh.entity.*;
import com.bjut.ssh.service.AssistedManagement.SpAccountBalanceSearchService;
import com.bjut.ssh.util.FormulaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: SpAccountBalanceSearchController
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/9/5 9:20
 * @Version: 1.0
 */
@Controller
@RequestMapping("/SpAccountBalanceSearch")
public class SpAccountBalanceSearchController {
    @Autowired
    private SpAccountBalanceSearchService spAccountBalanceSearchService;

    //第一种查询
    @RequestMapping("/querySpAccountBalance1")
    @ResponseBody
    public Msg querySpAccountBalance1(String year, String month, String itemNo, String spCatNo,String spNo,String spLevel, String searchAccType , String searchOption) {

        List<SpAccountBalanceQueryVo> spAccountBalanceQueryVos =  spAccountBalanceSearchService.querySpAccountBalance1(year,month, itemNo, spCatNo,spNo,spLevel,searchAccType,searchOption);

        return Msg.success().add("spAccountBalanceQueryVos",spAccountBalanceQueryVos);
    }

    //第一种查询的上级下级，根据级数和上级的核算编号进行查询
    @RequestMapping("/querySpInfoByLevel")
    @ResponseBody
    public Msg querySpInfoByLevel(String year, String month, String itemNo, String spCatNo,String spNo, String spLevel,String searchAccType , String searchOption) {

        List<SpAccountBalanceQueryVo> spAccountBalanceQueryVos =  spAccountBalanceSearchService.querySpInfoByLevel(year,month, itemNo, spCatNo,spNo,spLevel,searchAccType,searchOption);

        return Msg.success().add("spAccountBalanceQueryVos",spAccountBalanceQueryVos);
    }


    //第二种查询
    @RequestMapping("/querySpAccountBalance2")
    @ResponseBody
    public Msg querySpAccountBalance2(String year, String month, String itemNo,String item, String spCatNo,String spNo, String searchAccType , String searchOption) {

        List<SpAccountBalanceQueryVo> spAccountBalanceQueryVos =  spAccountBalanceSearchService.querySpAccountBalance2(year,month, itemNo,item, spCatNo,spNo,searchAccType,searchOption);

        return Msg.success().add("spAccountBalanceQueryVos",spAccountBalanceQueryVos);
    }


    //querySpAccountBalance3
    //生成columns
    @RequestMapping(value = "/getAllColumn3/{spCatNo}/{itemNo}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getAllColumn3(HttpServletRequest request, @PathVariable("spCatNo") String spCatNo,@PathVariable("itemNo") String itemNo) {
        return spAccountBalanceSearchService.getAllColumn3(spCatNo,itemNo);

    }


    @RequestMapping(value = "/getAllInfo3")
    @ResponseBody
    public Map<String,Object> getAllInfo3(String year, String month, String itemNo, String spCatNo,String spNo,String spLevel, String searchAccType , String searchOption,String queryDataType) {

        return spAccountBalanceSearchService.getAllInfo3( year,  month,  itemNo,  spCatNo, spNo, spLevel, searchAccType ,  searchOption, queryDataType) ;

    }

    //querySpAccountBalance4
    //生成columns
    @RequestMapping(value = "/getAllColumn4/{spCatNo}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getAllColumn4(HttpServletRequest request, @PathVariable("spCatNo") String spCatNo) {
        return spAccountBalanceSearchService.getAllColumn4(spCatNo);

    }


    @RequestMapping(value = "/getAllInfo4")
    @ResponseBody
    public Map<String,Object> getAllInfo4(String year, String month, String itemNo, String spCatNo,String spNo,String spLevel, String searchAccType , String searchOption,String queryDataType) {

        return spAccountBalanceSearchService.getAllInfo4( year,  month,  itemNo,  spCatNo, spNo, spLevel, searchAccType ,  searchOption, queryDataType) ;

    }


    @RequestMapping("/querySpAccountBalance5")
    @ResponseBody
    public Msg querySpAccountBalance5(String year, String month, String itemNo, String catNo1,String spNo1,String catNo2,String spNo2, String searchAccType , String searchOption) {

        List<SpAccountBalanceQueryVo2> spAccountBalanceQueryVo2s =  spAccountBalanceSearchService.querySpAccountBalance5(year,month, itemNo, catNo1,spNo1,catNo2,spNo2,searchAccType,searchOption);

        return Msg.success().add("spAccountBalanceQueryVo2s",spAccountBalanceQueryVo2s);
    }

    @RequestMapping("/querySpAccountBalance6")
    @ResponseBody
    public Msg querySpAccountBalance6(String year, String month, String itemNo, String item,String catNo1,String spNo1,String catNo2,String spNo2, String searchAccType , String searchOption) {

        List<SpAccountBalanceQueryVo2> spAccountBalanceQueryVo2s =  spAccountBalanceSearchService.querySpAccountBalance6(year,month, itemNo, item,catNo1,spNo1,catNo2,spNo2,searchAccType,searchOption);

        return Msg.success().add("spAccountBalanceQueryVo2s",spAccountBalanceQueryVo2s);
    }

    //第七种查询情况
    //生成columns
    @RequestMapping(value = "/getAllColumn7/{catNo1}/{catNo2}/{itemNo}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getAllColumn7(HttpServletRequest request, @PathVariable("catNo1") String catNo1,@PathVariable("catNo2") String catNo2,@PathVariable("itemNo") String itemNo) {
        return spAccountBalanceSearchService.getAllColumn7(catNo1,catNo2,itemNo);
    }

    @RequestMapping(value = "/getAllInfo7")
    @ResponseBody
    public Map<String,Object> getAllInfo7(String year, String month, String itemNo, String catNo1,String spNo1,String catNo2,String spNo2, String searchAccType , String searchOption,String queryDataType) {
        return spAccountBalanceSearchService.getAllInfo7( year,  month,  itemNo,  catNo1, spNo1, catNo2, spNo2,  searchAccType ,  searchOption, queryDataType) ;
    }

    //第八种查询情况
    //querySpAccountBalance4
    //生成columns
    @RequestMapping(value = "/getAllColumn8/{catNo1}/{catNo2}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getAllColumn8(HttpServletRequest request, @PathVariable("catNo1") String catNo1,@PathVariable("catNo2") String catNo2) {
        return spAccountBalanceSearchService.getAllColumn8(catNo1,catNo2);
    }

    @RequestMapping(value = "/getAllInfo8")
    @ResponseBody
    public Map<String,Object> getAllInfo8(String year, String month, String itemNo, String catNo1,String spNo1,String catNo2,String spNo2, String searchAccType , String searchOption,String queryDataType) {
        return spAccountBalanceSearchService.getAllInfo8( year,  month,  itemNo,  catNo1, spNo1, catNo2, spNo2,  searchAccType ,  searchOption, queryDataType) ;
    }

    @RequestMapping("/queryLshszd")
    @ResponseBody
    public Msg queryLshszd( String catNo,String spNo,String spLevel) {

        List<Lshszd> lshszds =  spAccountBalanceSearchService.queryLshszd(catNo,spNo,spLevel);

        return Msg.success().add("lshszds",lshszds);
    }

}
