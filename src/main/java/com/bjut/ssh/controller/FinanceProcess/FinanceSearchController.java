package com.bjut.ssh.controller.FinanceProcess;

import com.bjut.ssh.entity.*;
import com.bjut.ssh.service.FinanceProcess.CaptionOfAccountService;
import com.bjut.ssh.service.FinanceProcess.FinanceSearchService;
import com.bjut.ssh.util.FormulaUtil;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: FinanceSearchController
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/7/3 10:45
 * @Version: 1.0
 *
**/
@Controller
@RequestMapping("/FinanceSearch")
public class FinanceSearchController {
    @Autowired
    private FinanceSearchService financeSearchService;

    @Autowired
    private CaptionOfAccountService captionOfAccountService;

    @RequestMapping("/getConfigsForSearch")
    @ResponseBody
    public Msg getConfigsForSearch(){
        return financeSearchService.getConfigsForSearch();
    }

    @RequestMapping("/queryCaption/{id}/{levelFlag}/{from}/{to}/{is_show_all}")
    @ResponseBody
    public Msg queryCaptionWithSql(@PathVariable("id") String id, @PathVariable("levelFlag") String levelFlag, @PathVariable("from") String from, @PathVariable("to") String to, @PathVariable("is_show_all") Boolean is_show_all, @RequestBody Map map){
        String sql = map.get("sql").toString();
        return financeSearchService.queryCaptionWithSql(id, levelFlag, from, to, is_show_all, sql);
    }

    @RequestMapping("/queryVouchersWithSql/{is_detail}/{year_month}")
    @ResponseBody
    public Msg queryVouchersWithSql(@PathVariable("is_detail") int is_detail, @PathVariable("year_month") String year_month, @RequestBody Map map){
        String sql = map.get("sql").toString();
        return financeSearchService.queryVouchersWithSql(is_detail, year_month, sql);
    }

    @RequestMapping("/queryDetailWithSql/{year_month}/{itemNo}")
    @ResponseBody
    public Msg queryDetailWithSql(@PathVariable("year_month") String year_month,  @PathVariable("itemNo") String itemNo, @RequestBody Map map){
        String sql = map.get("sql").toString();
        return financeSearchService.queryDetailWithSql(year_month, itemNo, sql);
    }

    @RequestMapping("/queryJournalWithSql/{from}/{to}/{itemNo}")
    @ResponseBody
    public Msg queryJournalWithSql(@PathVariable("from") String from, @PathVariable("to") String to, @PathVariable("itemNo") String itemNo, @RequestBody Map map){
        String sql = map.get("sql").toString();
        return financeSearchService.queryJournalWithSql(from, to, itemNo, sql);
    }

    //生成columns
    @RequestMapping(value = "/getAllColumn/{id}/{level}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getAllColumn(@PathVariable("id") String id, @PathVariable("level") String level) {
        List<FieldBean> beans1 = new ArrayList<FieldBean>();
        List<FieldBean> beans2 = new ArrayList<FieldBean>();
        Map<String,Object> result = new HashMap<String,Object>();

        FieldBean bean1 = new FieldBean();
        bean1.setTitle("月");
        bean1.setField("month");
        bean1.setAlign("center");
        bean1.setWidth(20);
        bean1.setRowspan(2);
        beans1.add(bean1);

        FieldBean bean2 = new FieldBean();
        bean2.setTitle("日");
        bean2.setField("day");
        bean2.setAlign("center");
        bean2.setWidth(20);
        bean2.setRowspan(2);
        beans1.add(bean2);

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

        List<Lskmzd> list = captionOfAccountService.queryCaptionOfAccountByLevel(id, level);
        int size = list.size();

        FieldBean bean5 = new FieldBean();
        bean5.setTitle("借方发生");
        bean5.setField("debitMoney");
        bean5.setAlign("center");
        bean5.setColspan(size);
        bean5.setWidth(80 * size);
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
        bean7.setField("creditMoney");
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


    @RequestMapping(value = "/queryMutiColWithSql")
    @ResponseBody
    public Map<String,Object> queryMutiColWithSql(String from, String to, String itemNo, String sql) {
        List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();

        List<Lspzk1> lspzk1List = financeSearchService.queryVoucherByItem(from, to, itemNo);

        //计算上期结转
        String supMoney_str = "";
        int last_month = Integer.parseInt(from.substring(4, 6)) - 1;
        //如果当前为1月，则上期结转就是上年结转
        if(last_month == 0){
            supMoney_str = "supMoney";
        }
        //如果是其他月份，则上期结转就是上月余额
        else{
            String month_str = last_month < 10 ? "0" + Integer.toString(last_month) : Integer.toString(last_month);
            supMoney_str = "balance" + month_str;
        }
        String condition = "itemNo = '" + itemNo + "'";
        Double sup = financeSearchService.queryMoney("Lskmzd", supMoney_str, condition);
        Double supMoney = sup == null ? 0.0 : sup;

        //添加上期结转行
        Map<String,Object> sup_row = new HashMap<String,Object>();
        sup_row.put("summary", "上期结转");
        sup_row.put("month", from.substring(3, 5));
        sup_row.put("money", supMoney);
        datas.add(sup_row);

        //查找出符合条件的行
        List<Lspzk1> cond_lspzk1List = new ArrayList<>();
        if(!sql.equals("0")){
            cond_lspzk1List = financeSearchService.queryVoucherWithSql(from, to, itemNo, sql);
        }

        Double balance = 0.0;//当前余额
        for(int i = 1, j = i; i < lspzk1List.size(); i++){
            Lspzk1 lspzk1 = lspzk1List.get(i);
            Map<String,Object> data = new HashMap<String,Object>();

            String inputDate = lspzk1.getInputDate();
            data.put("month", inputDate.substring(3, 5));
            data.put("day", inputDate.substring(5, 7));
            data.put("voucherNo", lspzk1.getVoucherNo());

            data.put("summary", lspzk1.getSummary());

            data.put("money", balance);
            double creditMoney = 0;
            while(true) {
                Lspzk1 current = lspzk1List.get(i);
                Lspzk1 next = i + 1 >= lspzk1List.size() ? null : lspzk1List.get(i + 1);


                if (current.getBkpDirection().equals("J")) {
                    data.put(current.getItemNo(), current.getMoney());
                } else {
                    creditMoney += current.getMoney();
                }

                //借方发生额则增加余额
                if(lspzk1.getBkpDirection().equals("J")){
                    balance += lspzk1.getMoney() == null ? 0.0 : lspzk1.getMoney();
                }
                //贷方发生额则减少余额
                else{
                    balance -= lspzk1.getMoney() == null ? 0.0 : lspzk1.getMoney();
                }


                if(next != null || !next.getVoucherNo().equals(current.getVoucherNo()) || !next.getInputDate().equals(current.getInputDate())){
                    data.put("creditMoney", creditMoney);
                    break;
                }
                else{
                    i++;
                }
            }

            //筛选出符合条件的行
            if(sql.equals("0")){
                datas.add(data);
            }
            else {
                if (lspzk1.getPzk1AutoId() == cond_lspzk1List.get(j).getPzk1AutoId()) {
                    datas.add(data);
                    j++;
                }
            }
        }

        //生成列对应的行数据
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("rows", datas);
        result.put("total", datas.size());

        return result;
    }

}
