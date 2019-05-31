package com.bjut.ssh.service.AssistedManagement;

import com.bjut.ssh.entity.Lskmzd;
import com.bjut.ssh.entity.Lswldw;
import com.bjut.ssh.entity.Msg;

import java.util.List;

/**
 * @Title: contactsPageSearchService
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/7/13 17:20
 * @Version: 1.0
 */
public interface ContactsPageSearchService {

    public abstract Msg queryContactsAccountPage(String dataFrom, String dataTo, String itemNo, String companyNo, String pageNum, String searchOption1, String searchOption2);

    public abstract boolean judgeCompanyConnectItem(String itemNo, String companyNo);

    public abstract List<Lswldw> queryCompany();

    public abstract Lswldw judgeContactsCompanyNo(String companyNo);

    public abstract Lskmzd judgeItemNo(String itemNo);
}
