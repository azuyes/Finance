package com.bjut.ssh.serviceImpl.AssistedManagement;

import com.bjut.ssh.dao.AssistedManagement.ContactsInitializationDao;
import com.bjut.ssh.entity.*;
import com.bjut.ssh.service.AssistedManagement.ContactsInitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Title: ContactsInitializationServiceImpl
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/18 16:24
 * @Version: 1.0
 */
@Service
@Transactional
public class ContactsInitializationServiceImpl implements ContactsInitializationService {

    @Autowired
    private ContactsInitializationDao contactsInitializationDao;

    @Override
    @Transactional
    public LswljeOrLswlslQueryVo getItemByIdAndCompany(String itemNo) {
        LswljeOrLswlslQueryVo lswljeOrLswlsl = new LswljeOrLswlslQueryVo();

        LskmzdVoForContacts lskmzdVoForContacts = contactsInitializationDao.getItemById(itemNo);

        lswljeOrLswlsl.setLskmzdVoForContacts(lskmzdVoForContacts);

        List<LswljeQueryVo> lswljeQueryVos = null;

        String accType = lskmzdVoForContacts.getAccType();
        lswljeQueryVos = contactsInitializationDao.getLswljeOrLswlsl(itemNo,accType);

        lswljeOrLswlsl.setLswljeQueryVoList(lswljeQueryVos);

        return lswljeOrLswlsl;
    }

    @Override
    @Transactional
    public boolean updateContactsInitial(List<LswljeQueryVo> lswljeQueryVos) {
        for(LswljeQueryVo lswljeQueryVo : lswljeQueryVos){
            contactsInitializationDao.updateLswlje(lswljeQueryVo);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean updateContactsInitialSl(List<LswljeQueryVo> lswljeQueryVos) {
        for(LswljeQueryVo lswljeQueryVo : lswljeQueryVos){
            contactsInitializationDao.updateLswljeAndsl(lswljeQueryVo);
        }
        return true;
    }

    @Override
    public List<Lskmzd> queryCaptionOfAccountByLevel(String id, String levelFlag){
        return contactsInitializationDao.queryCaptionOfAccountByLevel(id, levelFlag);
    }
}
