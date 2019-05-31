package com.bjut.ssh.serviceImpl.AssistedManagement;

import com.bjut.ssh.dao.AssistedManagement.ContactsBalanceSearchDao;
import com.bjut.ssh.entity.ContactsBalanceQueryVo;
import com.bjut.ssh.entity.Lskmzd;
import com.bjut.ssh.entity.Lswldw;
import com.bjut.ssh.entity.Lswlje;
import com.bjut.ssh.service.AssistedManagement.ContactsBalanceSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Title: ContactsBalanceSearchServiceImpl
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/7/13 17:27
 * @Version: 1.0
 */
@Service
@Transactional
public class ContactsBalanceSearchServiceImpl implements ContactsBalanceSearchService {

    @Autowired
    private ContactsBalanceSearchDao contactsBalanceSearchDao;

    @Override
    public List<ContactsBalanceQueryVo> queryContactsBalance1(String year, String month, String itemNo, String companyNo, String searchAccType, String searchOption) {
        if(companyNo.equals("...")){
            return contactsBalanceSearchDao.queryContactsBalance1(year,month, itemNo, companyNo,searchAccType,searchOption);
        }else{
            return contactsBalanceSearchDao.queryContactsBalance3(year,month, itemNo, companyNo,searchAccType,searchOption);
        }
    }

    @Override
    public List<ContactsBalanceQueryVo> queryContactsBalance2(String year, String month, String itemNo, String companyNo, String searchAccType, String searchOption) {
        return contactsBalanceSearchDao.queryContactsBalance2(year,month, itemNo, companyNo,searchAccType,searchOption);
    }

    @Override
    public List<ContactsBalanceQueryVo> queryContactsBalance4(String year, String month, String itemNo, String companyNo, String searchAccType, String searchOption, String queryDataType) {
        return contactsBalanceSearchDao.queryContactsBalance4(year,month, itemNo, companyNo,searchAccType,searchOption,queryDataType);
    }

    @Override
    public List<Lskmzd> queryContactsItem() {
        return contactsBalanceSearchDao.queryContactsItem();
    }

    @Override
    public List<ContactsBalanceQueryVo> queryContactsBalanceQueryVo() {
        return contactsBalanceSearchDao.queryContactsBalanceQueryVo();
    }

    @Override
    public List<Lswlje> queryLswlje() {
        return contactsBalanceSearchDao.queryLswlje();
    }

    @Override
    public List<Lswldw> queryLswldw() {
        return contactsBalanceSearchDao.queryLswldw();
    }

    @Override
    public boolean judgeLswlje(String itemNo) {
        return contactsBalanceSearchDao.judgeLswlje(itemNo);
    }

    @Override
    public List<Object> getFormulaResult(String sql) {
        return contactsBalanceSearchDao.getFormulaResult(sql);
    }
}
