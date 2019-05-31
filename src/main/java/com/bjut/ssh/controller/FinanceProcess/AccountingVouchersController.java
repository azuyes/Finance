package com.bjut.ssh.controller.FinanceProcess;

import com.bjut.ssh.entity.*;
import com.bjut.ssh.service.FinanceProcess.AccountingVouchersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Title: AccountingVouchers
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/5/4 11:07
 * @Version: 1.0
 */
@Controller
@RequestMapping("/AccountingVouchers")
public class AccountingVouchersController {
    @Autowired
    private AccountingVouchersService accountingVouchersService;

//    @RequestMapping("/addOriginalVoucher")
//    @ResponseBody
//    public Msg addOriginalVoucher(@RequestBody Lsyspz lsyspz){
//        return accountingVouchersService.saveOriginalVoucher(lsyspz, true);
//    }
//
//    @RequestMapping("/updateOriginalVoucher")
//    @ResponseBody
//    public Msg updateOriginalVoucher(@RequestBody Lsyspz lsyspz){
//        return accountingVouchersService.saveOriginalVoucher(lsyspz, false);
//    }
//
//    @RequestMapping("/addEntry")
//    @ResponseBody
//    public Msg addEntry(@RequestBody Lspzk1 lspzk1){
//        return accountingVouchersService.saveEntry(lspzk1, true);
//    }
//
//    @RequestMapping("/updateEntry")
//    @ResponseBody
//    public Msg updateEntry(@RequestBody Lspzk1 lspzk1){
//        return accountingVouchersService.saveEntry(lspzk1, false);
//    }
//
//    @RequestMapping("/saveSpVoucher")
//    @ResponseBody
//    public Msg saveSpVoucher(@RequestBody Lshspz lshspz){
//        return accountingVouchersService.saveSpVoucher(lshspz);
//    }

    @RequestMapping("/saveVoucher")
    @ResponseBody
    public Msg saveVoucher(@RequestBody List<Lspzk1QueryVo> lspzk1QueryVoList){
        return accountingVouchersService.saveVoucher(lspzk1QueryVoList);
    }

    @RequestMapping("/delOriginalVoucher/{inputDate}/{voucherNo}/{entryNo}/{originalNo}")
    @ResponseBody
    public Msg delOriginalVoucher(@PathVariable("inputDate") String inputDate, @PathVariable("voucherNo") String voucherNo, @PathVariable("entryNo") String entryNo, @PathVariable("originalNo") String originalNo){
        return accountingVouchersService.delOriginalVoucher(inputDate, voucherNo, entryNo, originalNo);
    }

    @RequestMapping("/delEntry/{inputDate}/{voucherNo}/{entryNo}/{itemNo}")
    @ResponseBody
    public Msg delEntry(@PathVariable("inputDate") String inputDate, @PathVariable("voucherNo") String voucherNo, @PathVariable("entryNo") String entryNo, @PathVariable("itemNo") String itemNo){
        return accountingVouchersService.delEntry(inputDate, voucherNo, entryNo, itemNo);
    }

    @RequestMapping("/delVoucher/{voucherNo}/{inputDate}")
    @ResponseBody
    public Msg delVoucher(@PathVariable("voucherNo") String voucherNo, @PathVariable("inputDate") String inputDate){
        return accountingVouchersService.delVoucher(voucherNo, inputDate);
    }

    @RequestMapping("/changeBkpDirection/{inputDate}/{voucherNo}/{entryNo}/{itemNo}")
    @ResponseBody
    public Msg changeBkpDirection(@PathVariable("inputDate") String inputDate, @PathVariable("voucherNo") String voucherNo, @PathVariable("entryNo") String entryNo, @PathVariable("itemNo") String itemNo){
        return accountingVouchersService.changeBkpDirection(inputDate, voucherNo, entryNo, itemNo);
    }

    @RequestMapping(value = "/queryVouchersForDatagrid/{voucherNo}/{inputDate}", method = RequestMethod.POST)
    @ResponseBody
    public Msg queryVouchersForDatagrid(@PathVariable("voucherNo") String voucherNo, @PathVariable("inputDate") String inputDate){
        List<Lspzk1QueryVo> lspzk1QueryVoList = accountingVouchersService.queryVouchersForDatagrid(voucherNo, inputDate);
        return Msg.success().add("lspzk1QueryVoList", lspzk1QueryVoList);
    }

    @RequestMapping(value = "/queryOriginalVouchers/{voucherNo}/{inputDate}/{entryNo}")
    @ResponseBody
    public Msg queryOriginalVouchers(@PathVariable("voucherNo") String voucherNo, @PathVariable("inputDate") String inputDate, @PathVariable("entryNo") String entryNo){
        List<Lsyspz> lsyspzList = accountingVouchersService.queryOriginalVouchers(voucherNo, inputDate, entryNo);
        return Msg.success().add("lsyspzList", lsyspzList);
    }

    @RequestMapping(value = "/querySpVouchers/{voucherNo}/{inputDate}/{entryNo}/{itemNo}")
    @ResponseBody
    public Msg querySpVouchers(@PathVariable("voucherNo") String voucherNo, @PathVariable("inputDate") String inputDate, @PathVariable("entryNo") String entryNo, @PathVariable("itemNo") String itemNo){
        List<LshspzQueryVo> lshspzQueryVoList = accountingVouchersService.querySpVouchers(voucherNo, inputDate, entryNo, itemNo);
        return Msg.success().add("lshspzQueryVoList", lshspzQueryVoList);
    }

    @RequestMapping("/updateVoucherData/{is_add}/{year_month}")
    @ResponseBody
    public Msg updateVoucherData(@PathVariable("is_add") int is_add, @PathVariable("year_month") String year_month){
        return accountingVouchersService.updateVoucherData(is_add, year_month);
    }

    @RequestMapping("/getVoucherData/{year_month}")
    @ResponseBody
    public Msg getVoucherData(@PathVariable("year_month") String year_month){
        return accountingVouchersService.getVoucherData(year_month);
    }

    @RequestMapping("/operateReviewByNum/{review}/{inputDate}/{start}/{end}/{name}/{no}")
    @ResponseBody
    public Msg operateReviewByNum(@PathVariable("review") int review, @PathVariable("inputDate") String inputDate, @PathVariable("start") String start, @PathVariable("end") String end, @PathVariable("name") String name, @PathVariable("no") String no){
        Boolean is_review = review == 1;
        return accountingVouchersService.operateReviewByNum(is_review, inputDate, start, end, name, no);
    }

    @RequestMapping("/operateReviewByDate/{review}/{inputDate}/{start}/{end}/{name}/{no}")
    @ResponseBody
    public Msg operateReviewByDate(@PathVariable("review") int review, @PathVariable("inputDate") String inputDate, @PathVariable("start") String start, @PathVariable("end") String end, @PathVariable("name") String name, @PathVariable("no") String no){
        Boolean is_review = review == 1;
        return accountingVouchersService.operateReviewByDate(is_review, inputDate, start, end, name, no);
    }
}
