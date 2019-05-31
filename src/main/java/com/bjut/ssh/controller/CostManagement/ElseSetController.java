package com.bjut.ssh.controller.CostManagement;

import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.ElseSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Title: QuotaSetController
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/7/13 14:14
 * @Version: 1.0
 */
@Controller
@RequestMapping("/ElseSet")
public class ElseSetController {
    @Autowired
    private ElseSetService elseSetService;

    @RequestMapping("/getInitGrid")
    @ResponseBody
    public Msg getInitGrid(){
        return elseSetService.getInitGrid();
    }

    @RequestMapping("/getDefinedItemGridInfo")
    @ResponseBody
    public Msg getDefinedItemGridInfo(){
        return elseSetService.getDefinedItemGridInfo();
    }

    @RequestMapping("/addDefinedItemInfo/{ItemID}/{ItemName}")
    @ResponseBody
    public Msg addDefinedItemInfo(@PathVariable("ItemID") String ItemNo, @PathVariable("ItemName") String ItemName){
        return elseSetService.addDefinedItemInfo(ItemNo, ItemName);
    }

    @RequestMapping("/deleteDefinedItemInfo/{ItemID}")
    @ResponseBody
    public Msg deleteDefinedItemInfo(@PathVariable("ItemID") String ItemNo){
        return elseSetService.deleteDefinedItemInfo(ItemNo);
    }

    @RequestMapping("/getFixedItemGridInfo")
    @ResponseBody
    public Msg getFixedItemGridInfo(){
        return elseSetService.getFixedItemGridInfo();
    }

    @RequestMapping("/getDefinedItemInfo/{ItemID}")
    @ResponseBody
    public Msg getDefinedItemInfo(@PathVariable("ItemID") String ItemNo){
        return elseSetService.getDefinedItemInfo(ItemNo);
    }

    @RequestMapping("/getItemNameSelect_defined/{selectItemTypeValue}/{itemNo_defined}")
    @ResponseBody
    public Msg getItemNameSelect_defined(@PathVariable("selectItemTypeValue") String selectItemTypeValue, @PathVariable("itemNo_defined") String itemNo_defined){
        return elseSetService.getItemNameSelect_defined(selectItemTypeValue,itemNo_defined);
    }

    @RequestMapping("/addDefinedItemInfo_defined/{itemNo_defined}/{ItemID_defined}/{selectItemTypeValue}")
    @ResponseBody
    public Msg addDefinedItemInfo_defined(@PathVariable("itemNo_defined") String itemNo_defined, @PathVariable("ItemID_defined") String ItemID_defined, @PathVariable("selectItemTypeValue") String selectItemTypeValue){
        return elseSetService.addDefinedItemInfo_defined(itemNo_defined, ItemID_defined, selectItemTypeValue);
    }

    @RequestMapping("/deleteItemInfo_defined/{ItemID}/{itemNo_defined}/{selectItemTypeValue}")
    @ResponseBody
    public Msg deleteItemInfo_defined(@PathVariable("ItemID") String ItemID, @PathVariable("itemNo_defined") String itemNo_defined, @PathVariable("selectItemTypeValue") String selectItemTypeValue){
        return elseSetService.deleteItemInfo_defined(ItemID, itemNo_defined,selectItemTypeValue);
    }

    @RequestMapping("/getRelateItemNameSelect_fixed/{selectRelatedItemType}")
    @ResponseBody
    public Msg getRelateItemNameSelect_fixed(@PathVariable("selectRelatedItemType") String selectRelatedItemType){
        return elseSetService.getRelateItemNameSelect_fixed(selectRelatedItemType);
    }

    @RequestMapping("/getRelateItemNoSelect_fixed/{selectRelatedItemType}")
    @ResponseBody
    public Msg getRelateItemNoSelect_fixed(@PathVariable("selectRelatedItemType") String selectRelatedItemType){
        return elseSetService.getRelateItemNoSelect_fixed(selectRelatedItemType);
    }

    @RequestMapping("/addFixedItemInfo_fixed/{relateItemType_fixed}/{relateItemNo_fixed}/{ItemID_fixed}")
    @ResponseBody
    public Msg addFixedItemInfo_fixed(@PathVariable("relateItemType_fixed") String relateItemType_fixed, @PathVariable("relateItemNo_fixed") String relateItemNo_fixed, @PathVariable("ItemID_fixed") String ItemID_fixed){
        return elseSetService.addFixedItemInfo_fixed(relateItemType_fixed, relateItemNo_fixed, ItemID_fixed);
    }

    @RequestMapping("/getMenuInfo")
    @ResponseBody
    public Msg getMenuInfo(){
        return elseSetService.getMenuInfo();
    }

    @RequestMapping("/addMenuInfo/{MenuName}/{IsShow}")
    @ResponseBody
    public Msg addMenuInfo(@PathVariable("MenuName") String MenuName, @PathVariable("IsShow") String IsShow){
        return elseSetService.addMenuInfo(MenuName, IsShow);
    }

}
