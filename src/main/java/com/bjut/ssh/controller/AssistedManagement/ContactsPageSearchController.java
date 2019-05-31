package com.bjut.ssh.controller.AssistedManagement;

import com.bjut.ssh.entity.*;
import com.bjut.ssh.service.AssistedManagement.ContactsManagementServie;
import com.bjut.ssh.service.AssistedManagement.ContactsPageSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Title: ContactsPageSearchController
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/7/13 17:14
 * @Version: 1.0
 */
@Controller
@RequestMapping("/ContactsPageSearch")
public class ContactsPageSearchController {

    @Autowired
    private ContactsPageSearchService contactsPageSearchService;

    @RequestMapping("/queryContactsAccountPage")
    @ResponseBody
    public Msg queryContactsAccountPage(String dataFrom, String dataTo, String itemNo, String companyNo, String pageNum, String searchOption1, String searchOption2) {

        return contactsPageSearchService.queryContactsAccountPage(dataFrom, dataTo, itemNo, companyNo, pageNum, searchOption1, searchOption2);
//

    }

    @RequestMapping("/judgeCompanyConnectItem/{itemNo}/{companyNo}")
    @ResponseBody
    public Msg judgeCompanyConnectItem(@PathVariable("itemNo") String itemNo,@PathVariable("companyNo") String companyNo){
        boolean bool = contactsPageSearchService.judgeCompanyConnectItem(itemNo, companyNo);
        if(bool){
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }

    @RequestMapping("/queryCompany")
    @ResponseBody
    public List<Lswldw> queryCompany(){
        return  contactsPageSearchService.queryCompany();

    }

    @RequestMapping("/judgeContactsCompanyNo/{companyNo}")
    @ResponseBody
    public Msg judgeContactsCompanyNo(@PathVariable("companyNo")String companyNo){
        Lswldw lswldw = contactsPageSearchService.judgeContactsCompanyNo(companyNo);
        return Msg.success().add("lswldw",lswldw);
    }

    @RequestMapping("/judgeItemNo/{itemNo}")
    @ResponseBody
    public Msg judgeItemNo(@PathVariable("itemNo")String itemNo){
        Lskmzd lskmzd = contactsPageSearchService.judgeItemNo(itemNo);
        return Msg.success().add("lskmzd",lskmzd);
    }









}
