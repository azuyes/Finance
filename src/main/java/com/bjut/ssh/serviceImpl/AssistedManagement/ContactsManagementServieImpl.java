package com.bjut.ssh.serviceImpl.AssistedManagement;

import com.bjut.ssh.dao.AssistedManagement.ContactsManagementDao;
import com.bjut.ssh.entity.*;
import com.bjut.ssh.service.AssistedManagement.ContactsManagementServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Title: ContactsManagementServieImp
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/9 14:01
 * @Version: 1.0
 */
@Service

@Transactional
public class ContactsManagementServieImpl implements ContactsManagementServie {
    @Autowired
    private ContactsManagementDao contactsManagementDao;

    @Override
    @Transactional
    public List<LswldwQueryVo> getContactsCompany() {
        List<LswldwQueryVo> lswldwQueryVos = new ArrayList<LswldwQueryVo>();
        List<Lswldw> lswldws = contactsManagementDao.getContactsConmpany();
        int i=0;
        if(lswldws.size()>0){
            for(Lswldw lswldw : lswldws){
                String catNo1 = lswldw.getCatNo1();
                String catName = contactsManagementDao.getCatNameOfLswlfl(catNo1);
                LswldwQueryVo lswldwQueryVo = new LswldwQueryVo();
                lswldwQueryVo.setCatName1(catName);
                lswldwQueryVo.setLswldw(lswldw);

                lswldwQueryVos.add(i,lswldwQueryVo);
                i=i+1;
            }
        }else{
            return null;
        }
        return lswldwQueryVos;
    }

    @Override
    public List<Lswlfl> getContactsCategory(String catNo1) {
        List<Lswlfl> lswlflList = contactsManagementDao.getLswlfl(catNo1);
        //转换成树形json，待完成
        return lswlflList;
    }

    @Override
    public boolean saveContactsCategory(Lswldw lswldw) {
        return contactsManagementDao.saveContactsCategory(lswldw);
    }

    @Override
    public Lswldw getContactsCompanyById(String companyNo) {
        return contactsManagementDao.getContactsCompanyById(companyNo);
    }

    @Override
    public boolean updateContactsCompanyById(Lswldw lswldw) {
        return contactsManagementDao.updateContactsCompanyById(lswldw);
    }

    @Override
    public List<Lswldw> getContactsCompanyOfDefin() {
        return contactsManagementDao.getContactsCompanyOfDefin();
    }

    @Override
    public List<Lskmzd> getSpecialItemOfDefin() {
        return contactsManagementDao.getSpecialItemOfDefin();
    }

    @Override
    public boolean defineOfCompanyAndItem(String[] companyNos,String[] itemNos) {
        try{
            String companyNo,itemNo;
            for(int i = 0;i<companyNos.length;i++){
                for(int j = 0;j<itemNos.length;j++){
                    companyNo = companyNos[i];
                    itemNo = itemNos[j];
                    List<Lskmzd> lskmzdList =contactsManagementDao.getItemNosByItemNo(itemNo);
                    for(Lskmzd lskmzd:lskmzdList){
                        String itemNoTemp = lskmzd.getItemNo();
                        if(!contactsManagementDao.existOfCompanyAndItem(companyNo,itemNoTemp)){
                            contactsManagementDao.defineOfCompanyAndItem(companyNo,itemNoTemp);
                        }
                    }

                }
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<Lswlje> getDefinedOfCompanyAndItem() {
        return  contactsManagementDao.getDefinedOfCompanyAndItem();
    }


    @Override
    public boolean judgeUnCheckedCompany(String companyNo) {

            //Lswldw lswldw = contactsManagementDao.getContactsCompanyById(companyNo);

            boolean bool = true;

            bool = contactsManagementDao.judgeUnCheckedCompany(companyNo);

            if(bool){
                bool = contactsManagementDao.deleteUnCheckedCompany(companyNo);

            }

            return bool;
    }

    @Override
    public boolean judgeUnCheckedItem(String itemNo) {
        boolean bool = true;

        bool = contactsManagementDao.judgeUnCheckedItem(itemNo);

        if(bool){
            bool = contactsManagementDao.deleteUnCheckedItem(itemNo);

        }

        return bool;
    }

    @Override
    public boolean deleteContactsCompanyById(String companyNo) {
        return contactsManagementDao.deleteContactsCompanyById(companyNo);
    }
}
