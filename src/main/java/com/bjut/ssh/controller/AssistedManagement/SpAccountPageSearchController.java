package com.bjut.ssh.controller.AssistedManagement;

import com.bjut.ssh.entity.*;
import com.bjut.ssh.service.AssistedManagement.SpAccountPageSearchService;
import com.bjut.ssh.service.FinanceProcess.CaptionOfAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @Title: SpAccountPageSearchController
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/9/5 9:20
 * @Version: 1.0
 */
@Controller
@RequestMapping("/SpAccountPageSearch")
public class SpAccountPageSearchController {
    @Autowired
    private SpAccountPageSearchService spAccountPageSearchService;

    @Autowired
    private CaptionOfAccountService captionOfAccountService;

    //第一种查询:只查询核算编号1，所有查询信息写全
    @RequestMapping("/querySpAccountPage1")
    @ResponseBody
    public Msg querySpAccountPage1(String from, String to, String itemNo, String spCatNo1, String spNo1, String searchOption) {
        List<Lspzk1VoForSearch> lspzk1VoForSearches =  spAccountPageSearchService.querySpAccountPage(from, to, itemNo, spCatNo1, spNo1, null, null, searchOption);
        QueryLskmzdFoSpAccountPageSearch headInfo = spAccountPageSearchService.queryLskmzdForHeadInfo(  itemNo,  spCatNo1,  spNo1,null,null);

        return Msg.success().add("lspzk1VoForSearches",lspzk1VoForSearches).add("headInfo",headInfo);
    }

    //第二种查询:查询核算编号1和2，所有查询信息写全
    @RequestMapping("/querySpAccountPage2")
    @ResponseBody
    public Msg querySpAccountPage2(String from, String to, String itemNo, String spCatNo1, String spNo1, String spCatNo2, String spNo2, String searchOption) {
        List<Lspzk1VoForSearch> lspzk1VoForSearches =  spAccountPageSearchService.querySpAccountPage(from, to, itemNo, spCatNo1, spNo1, spCatNo2, spNo2, searchOption);

        QueryLskmzdFoSpAccountPageSearch headInfo = spAccountPageSearchService.queryLskmzdForHeadInfo(  itemNo,  spCatNo1,  spNo1,spCatNo2,spNo2);
        return Msg.success().add("lspzk1VoForSearches",lspzk1VoForSearches).add("headInfo",headInfo);
    }

    //生成columns
    @RequestMapping(value = "/getAllColumn")
    @ResponseBody
    public Map<String,Object> getAllColumn(String id, String catNo1, String catNo2) {
        List<FieldBean> beans1 = new ArrayList<FieldBean>();
        List<FieldBean> beans2 = new ArrayList<FieldBean>();
        Map<String,Object> result = new HashMap<String,Object>();

        FieldBean beanYear = new FieldBean();
        beanYear.setTitle("年");
        beanYear.setField("year");
        beanYear.setAlign("center");
        beanYear.setWidth(40);
        beanYear.setColspan(2);
        beans1.add(beanYear);

        FieldBean bean3 = new FieldBean();
        bean3.setTitle("凭证编号");
        bean3.setField("voucherNo");
        bean3.setAlign("center");
        bean3.setWidth(80);
        bean3.setRowspan(2);
        beans1.add(bean3);

        FieldBean bean4 = new FieldBean();
        bean4.setTitle("摘要");
        bean4.setField("summary");
        bean4.setAlign("center");
        bean4.setWidth(170);
        bean4.setRowspan(2);
        beans1.add(bean4);

        //查找核算科目作为动态列
        List<Lskmzd> list = spAccountPageSearchService.querySpAccByCatNo(id, catNo1, catNo2);
        int size = list.size();

        FieldBean bean5 = new FieldBean();
        bean5.setTitle("借方发生");
        bean5.setField("debitMoney");
        bean5.setAlign("center");
        bean5.setColspan(size + 1);
        bean5.setWidth(80 * (size + 1));
        beans1.add(bean5);

        FieldBean bean6 = new FieldBean();
        bean6.setTitle("贷方发生");
        bean6.setField("creditMoney");
        bean6.setAlign("center");
        bean6.setRowspan(2);
        bean6.setWidth(80);
        beans1.add(bean6);

        FieldBean bean7 = new FieldBean();
        bean7.setTitle("借贷方向");
        bean7.setField("bkpDirection");
        bean7.setAlign("center");
        bean7.setRowspan(2);
        bean7.setWidth(40);
        beans1.add(bean7);

        FieldBean bean8 = new FieldBean();
        bean8.setTitle("余额");
        bean8.setField("money");
        bean8.setAlign("center");
        bean8.setRowspan(2);
        bean8.setWidth(80);
        beans1.add(bean8);

        FieldBean beanMonth = new FieldBean();
        beanMonth.setTitle("月");
        beanMonth.setField("month");
        beanMonth.setAlign("center");
        beanMonth.setWidth(20);
        beans2.add(beanMonth);

        FieldBean beanDay = new FieldBean();
        beanDay.setTitle("日");
        beanDay.setField("day");
        beanDay.setAlign("center");
        beanDay.setWidth(20);
        beans2.add(beanDay);

        FieldBean bean_total = new FieldBean();
        bean_total.setTitle("合计");
        bean_total.setField("total");
        bean_total.setAlign("center");
        bean_total.setWidth(80);
        beans2.add(bean_total);

        for(Lskmzd item : list){
            FieldBean bean = new FieldBean();
            bean.setTitle(item.getItemName());
            bean.setField(item.getItemNo());
            bean.setAlign("center");
            bean.setWidth(80);
            beans2.add(bean);
        }
        result.put("columns1", beans1);
        result.put("columns2", beans2);
        return result;
    }

//TODO
    //查询有通配符情况下的核算凭证
    @RequestMapping("/querySpAccountPageDefault")
    @ResponseBody
    public Msg querySpAccountPageDefault(String from, String to, String itemNo, String spCatNo1, String spNo1, String spCatNo2, String spNo2, String searchOption) {
        List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
        try {
            List<Lspzk1VoForSearch> lspzk1VoForSearches = spAccountPageSearchService.querySpAccountPage(from, to, itemNo, spCatNo1, spNo1, spCatNo2, spNo2, searchOption);

//            //计算上期结转
//            String supMoney_str;
//            int last_month = Integer.parseInt(from.substring(4, 6)) - 1;
//            //如果当前为1月，则上期结转就是上年结转
//            if (last_month == 0) {
//                supMoney_str = "supMoney";
//            }
//            //如果是其他月份，则上期结转就是上月余额
//            else {
//                String month_str = last_month < 10 ? "0" + Integer.toString(last_month) : Integer.toString(last_month);
//                supMoney_str = "balance" + month_str;
//            }
//            String condition = "itemNo = '" + itemNo + "'";
//            Double sup = spAccountPageSearchService.queryColumn("Lshsje", supMoney_str, condition);
//            Double supMoney = sup == null ? 0.0 : sup;

            //添加上期结转行
            Map<String, Object> sup_row = new HashMap<String, Object>();
            sup_row.put("summary", lspzk1VoForSearches.get(0).getLspzk1().getSummary());
            sup_row.put("month", from.substring(4, 6));
            sup_row.put("money", lspzk1VoForSearches.get(0).getMoney());
            datas.add(sup_row);

            //TODO:条件查询
//        //查找出符合条件的行
//        List<Lspzk1> cond_lspzk1List = new ArrayList<>();
//        if(!sql.equals("0")){
//            cond_lspzk1List = financeSearchService.queryVoucherWithSql(from, to, itemNo, sql);
//        }

            Double balance = lspzk1VoForSearches.get(0).getMoney();//当前余额
            for (int i = 1; i < lspzk1VoForSearches.size(); i++) {
                Lspzk1VoForSearch lspzk1VoForSearch = lspzk1VoForSearches.get(i);
                Map<String, Object> data = new HashMap<String, Object>();

                String inputDate = lspzk1VoForSearch.getLspzk1().getInputDate();
                data.put("month", inputDate.substring(4, 6));
                data.put("day", inputDate.substring(6, 8));
                data.put("voucherNo", lspzk1VoForSearch.getLspzk1().getVoucherNo());
                data.put("summary", lspzk1VoForSearch.getLspzk1().getSummary());

                double creditMoney = 0;
                double debitMoneyTotal = 0;
                while (true) {
                    Lspzk1VoForSearch current = lspzk1VoForSearches.get(i);
                    Lspzk1VoForSearch next = i + 1 >= lspzk1VoForSearches.size() ? null : lspzk1VoForSearches.get(i + 1);

                    if (current.getLspzk1().getBkpDirection().equals("J")) {
                        data.put(current.getLspzk1().getItemNo(), current.getLspzk1().getMoney());
                        data.put("itemNo",current.getLspzk1().getItemNo());
                        debitMoneyTotal += current.getLspzk1().getMoney();
                    } else {
                        data.put(current.getLspzk1().getItemNo(),-current.getLspzk1().getMoney());
                        data.put("itemNo",current.getLspzk1().getItemNo());
                        debitMoneyTotal -= current.getLspzk1().getMoney();
                    }

//                    //借方发生额则增加余额
//                    if (lspzk1VoForSearch.getLspzk1().getBkpDirection().equals("J")) {
//                        balance += lspzk1VoForSearch.getMoney() == null ? 0.0 : lspzk1VoForSearch.getMoney();
//                    }
//                    //贷方发生额则减少余额
//                    else {
//                        balance -= lspzk1VoForSearch.getMoney() == null ? 0.0 : lspzk1VoForSearch.getMoney();
//                    }
//                    data.put("money", balance);

                    if (next == null || !next.getLspzk1().getVoucherNo().equals(current.getLspzk1().getVoucherNo()) || !next.getLspzk1().getInputDate().equals(current.getLspzk1().getInputDate())) {
                        data.put("total", debitMoneyTotal);
                        data.put("money", current.getMoney());
                        data.put("creditMoney", null);
                        String bkpDirection = "借";
//                        if(debitMoney > creditMoney){
//                            bkpDirection = "借";
//                        }
//                        else if(debitMoney < creditMoney){
//                            bkpDirection = "贷";
//                        }
//                        else{
//                            bkpDirection = "平";
//                        }
                        data.put("bkpDirection", bkpDirection);
                        break;
                    }
                    else{
                        i++;
                    }

                }

                datas.add(data);

//            //筛选出符合条件的行
//            if(sql.equals("0")){
//                datas.add(data);
//            }
//            else {
//                if (lspzk1.getPzk1AutoId() == cond_lspzk1List.get(j).getPzk1AutoId()) {
//                    datas.add(data);
//                    j++;
//                }
//            }
            }

            //生成列对应的行数据
            Map<String, Object> result = new HashMap<String, Object>();

            List<Lskmzd> list = spAccountPageSearchService.querySpAccByCatNo(itemNo, spCatNo1, spCatNo2);
            Map<String, Double> maps = new HashMap<>();
            for (Lskmzd lskmzd : list) {
                maps.put(lskmzd.getItemNo(), 0.00);
            }

            int fromInt = Integer.parseInt(from.substring(4,6));
            int toInt = Integer.parseInt(to.substring(4,6));

            Double totalMonth = 0.00;
            Double creditMoney = 0.00;
            Double balanceMoney = 0.00;
            String bkpDirection = "借";

            if(datas.size() == 1){
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("summary", "本月合计");
                datas.add(data);
            }else{
                for(int i=1;i<datas.size();i++){
                    Map<String,Object> mapDatasI= datas.get(i);
                    String month = (String)mapDatasI.get("month");
                    int monthInt = Integer.parseInt(month);
                    if(monthInt != fromInt ){
                        Map<String, Object> data = new HashMap<String, Object>();
                        data.put("summary", "本月合计");
                        data.put("total", totalMonth);
                        Set<String> keys = maps.keySet();
                        for(String key:keys){
                           data.put(key,maps.get(key));
                        }
                        data.put("creditMoney",null);
                        data.put("money",balanceMoney);
                        data.put("bkpDirection", bkpDirection);
                        datas.add(i,data);
                        fromInt +=1;
                        totalMonth = 0.00;
                        creditMoney = 0.00;
                        balanceMoney = 0.00;
                        Set<String> keys1= maps.keySet();
                        for(String key:keys1){
                            maps.put(key,0.00);
                        }

                    }else{
                        totalMonth += (Double)mapDatasI.get("total");
                        if(mapDatasI.get("itemNo") != null){
                            Double debitMoney  = maps.get(mapDatasI.get("itemNo")) + (Double) mapDatasI.get(mapDatasI.get("itemNo"));
                            maps.put((String)mapDatasI.get("itemNo"),debitMoney);
                        }

                    }
                    balanceMoney =(Double) mapDatasI.get("money");

                }
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("summary", "本月合计");
                data.put("total", totalMonth);
                Set<String> keys = maps.keySet();
                for(String key:keys){
                    data.put(key,maps.get(key));
                }
                data.put("creditMoney",null);
                data.put("money",balanceMoney);
                data.put("bkpDirection", bkpDirection);
                datas.add(data);
            }

            result.put("rows", datas);

            result.put("total", datas.size());

            QueryLskmzdFoSpAccountPageSearch headInfo = spAccountPageSearchService.queryLskmzdForHeadInfo(  itemNo,  spCatNo1,  spNo1,spCatNo2,spNo2);

            return Msg.success().add("result",result).add("headInfo",headInfo);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
