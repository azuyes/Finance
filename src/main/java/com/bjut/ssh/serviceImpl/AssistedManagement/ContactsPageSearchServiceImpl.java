package com.bjut.ssh.serviceImpl.AssistedManagement;

import com.bjut.ssh.dao.AssistedManagement.ContactsPageSearchDao;
import com.bjut.ssh.entity.Lskmzd;
import com.bjut.ssh.entity.Lswldw;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.AssistedManagement.ContactsPageSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Title: ContactsPageSearchServiceImpl
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/7/13 17:28
 * @Version: 1.0
 */
@Service
@Transactional
public class ContactsPageSearchServiceImpl implements ContactsPageSearchService {

    @Autowired
    private ContactsPageSearchDao contactsPageSearchDao;

    @Override
    public Msg queryContactsAccountPage(String dataFrom, String dataTo, String itemNo, String companyNo, String pageNum, String searchOption1, String searchOption2) {
        return contactsPageSearchDao.queryContactsAccountPage(dataFrom,dataTo, itemNo, companyNo,pageNum,searchOption1,searchOption2);
    }

    @Override
    public boolean judgeCompanyConnectItem(String itemNo, String companyNo) {

        return contactsPageSearchDao.judgeCompanyConnectItem(itemNo,companyNo);
    }


    @Override
    public List<Lswldw> queryCompany() {
        return contactsPageSearchDao.queryCompany();
    }

    @Override
    public Lswldw judgeContactsCompanyNo(String companyNo) {
        return contactsPageSearchDao.judgeContactsCompanyNo(companyNo);
    }

    @Override
    public Lskmzd judgeItemNo(String itemNo) {
        return contactsPageSearchDao.judgeItemNo(itemNo);
    }
}
