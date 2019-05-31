package com.bjut.ssh.controller.AssistedManagement;

import com.bjut.ssh.entity.*;
import com.bjut.ssh.service.AssistedManagement.ContactsBalanceSearchService;
import com.bjut.ssh.util.FormulaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Title: ContactsBalanceSearchController
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/7/13 17:25
 * @Version: 1.0
 */
@Controller
@RequestMapping("/ContactsBalanceSearch")
public class ContactsBalanceSearchController {
    @Autowired
    private ContactsBalanceSearchService contactsBalanceSearchService;

    @RequestMapping("/queryContactsBalance")
    @ResponseBody

//    public void queryContactsBalance(@RequestBody Map<String,Object> params){
//        String year = params.get("year").toString();
//        String month = params.get("month").toString();
//    }
    //public void queryContactsBalance(@RequestParam("year") String year,@RequestParam("month") String month,@RequestParam("itemNo") String itemNo,@RequestParam("companyNo") String companyNo,@RequestParam("searchOption") String searchOption) {
    public Msg queryContactsBalance1(String year,String month, String itemNo, String companyNo,String searchAccType ,String searchOption) {

        List<ContactsBalanceQueryVo> contactsBalanceQueryVoList =  contactsBalanceSearchService.queryContactsBalance1(year,month, itemNo, companyNo,searchAccType,searchOption);

        return Msg.success().add("contactsBalanceQueryVoList",contactsBalanceQueryVoList);
    }

    @RequestMapping("/queryContactsBalance2")
    @ResponseBody
    public Msg queryContactsBalance2(String year,String month, String itemNo, String companyNo,String searchAccType ,String searchOption) {

        List<ContactsBalanceQueryVo> contactsBalanceQueryVoList =  contactsBalanceSearchService.queryContactsBalance2(year,month, itemNo, companyNo,searchAccType,searchOption);

        return Msg.success().add("contactsBalanceQueryVoList",contactsBalanceQueryVoList);
    }

    @RequestMapping("/queryContactsBalance4")
    @ResponseBody
    public Msg queryContactsBalance4(String year,String month, String itemNo, String companyNo,String searchAccType ,String searchOption,String queryDataType) {

        List<ContactsBalanceQueryVo> contactsBalanceQueryVoList =  contactsBalanceSearchService.queryContactsBalance4(year,month, itemNo, companyNo,searchAccType,searchOption,queryDataType);

        return Msg.success().add("contactsBalanceQueryVoList",contactsBalanceQueryVoList);
    }


    //生成columns
    @RequestMapping(value = "/getAllColumn", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getAllColumn(HttpServletRequest request) {
        List<FieldBean> beans = new ArrayList<FieldBean>();
        Map<String,Object> result = new HashMap<String,Object>();

        FieldBean bean1 = new FieldBean();
        bean1.setTitle("单位编号");
        bean1.setField("companyNo");
        bean1.setAlign("center");
        bean1.setWidth(80);
        beans.add(bean1);

        FieldBean bean2 = new FieldBean();
        bean2.setTitle("单位名称");
        bean2.setField("companyName");
        bean2.setAlign("center");
        bean2.setWidth(170);
        beans.add(bean2);

        List<Lskmzd> lskmzdList = contactsBalanceSearchService.queryContactsItem();
        int size = lskmzdList.size();
        for(Lskmzd lskmzd:lskmzdList){

            FieldBean bean = new FieldBean();
            bean.setTitle(lskmzd.getItemName());
            bean.setField(lskmzd.getItemNo());
            bean.setAlign("center");
            bean.setWidth(80);
            beans.add(bean);
        }
        result.put("columns", beans);
        return result;
    }


    @RequestMapping(value = "/getAllInfo")
    @ResponseBody
    public Map<String,Object> getAllInfo(String year,String month, String itemNo, String companyNo,String searchAccType ,String searchOption,String queryDataType) {
        List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();

        List<Lskmzd> lskmzdList = contactsBalanceSearchService.queryContactsItem();

        List<Lswldw>  lswldwList = contactsBalanceSearchService.queryLswldw();

        List<Lswlje> lswljeList = contactsBalanceSearchService.queryLswlje();

        for(Lswldw lswldw:lswldwList){
            Map<String,Object> data = new HashMap<String,Object>();
            companyNo = lswldw.getCompanyNo();

            data.put("companyNo",companyNo);
            data.put("companyName",lswldw.getCompanyName());

            boolean flag = false;
            for(Lskmzd lskmzd:lskmzdList){
                for(Lswlje lswlje:lswljeList){
                    if(lswldw.getCompanyNo().equals(lswlje.getCompanyNo())&&lskmzd.getItemNo().equals(lswlje.getItemNo())) {
                        flag = true;
                        String itemNo1 = "t1.itemNo='" + lskmzd.getItemNo() + "'";
                        String sql, result;
                        switch (queryDataType) {
                            case "1":
                                sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month), "JFFS", "Lswlje", null, null, null, itemNo1, null, companyNo);
                                result = contactsBalanceSearchService.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;
                            case "2":
                                data.put(lskmzd.getItemNo(), lswlje.getSupMoney());
                                break;
                            case "3":
                                sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month), "BYJD", "Lswlje", null, null, null, itemNo1, null, companyNo);
                                result = contactsBalanceSearchService.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;

                            case "4":
                                sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month), "DFFS", "Lswlje", null, null, null, itemNo1, null, companyNo);
                                result = contactsBalanceSearchService.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;
                            case "5":
                                sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month), "JFLJ", "Lswlje", null, null, null, itemNo1, null, companyNo);
                                result = contactsBalanceSearchService.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;
                            case "6":
                                sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month), "BYDJ", "Lswlje", null, null, null, itemNo1, null, companyNo);
                                result = contactsBalanceSearchService.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;
                            case "7":
                                sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month)+1, "YCJF", "Lswlje", null, null, null, itemNo1, null, companyNo);
                                result = contactsBalanceSearchService.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;
                            case "8":
                                sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month), "DFLJ", "Lswlje", null, null, null, itemNo1, null, companyNo);
                                result = contactsBalanceSearchService.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;
                            case "9":
                                sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month), "BNJD", "Lswlje", null, null, null, itemNo1, null, companyNo);
                                result = contactsBalanceSearchService.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;
                            case "10":
                                sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month), "BNDJ", "Lswlje", null, null, null, itemNo1, null, companyNo);
                                result = contactsBalanceSearchService.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;
                        }

                    }else {
                        flag = false;

                    }
                    if(flag){
                        break;
                    }
                }
                if(!flag){
                    data.put(lskmzd.getItemNo(), null);
                }

            }
            datas.add(data);
        }

        //生成列对应的行数据


        Map<String,Object> result = new HashMap<String,Object>();
        result.put("rows", datas);
        result.put("total", datas.size());

        return result;
    }





}
