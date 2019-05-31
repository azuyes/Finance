package com.bjut.ssh.serviceImpl.AssistedManagement;

import com.bjut.ssh.dao.AssistedManagement.ContactsCategoryDao;
import com.bjut.ssh.entity.Lswlfl;
import com.bjut.ssh.entity.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import com.bjut.ssh.service.AssistedManagement.ContactsCategoryService;

/**
 * @Title: ContactsCategoryServiceImpl
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/3/28 16:57
 * @Version: 1.0
 */

@Service
@Transactional
public class ContactsCategoryServiceImpl implements ContactsCategoryService {

    @Autowired
    private ContactsCategoryDao contactsCategoryDao;

    /**
    *@author lz
    *@Description TODO
    *@Date 2018/4/2 9:13
    *@Param []
    *@return java.util.List<com.bjut.ssh.entity.Lswlfl>
    **/
    @Override
    @Transactional
    public List<Lswlfl> getContactsCategory(String id){

        List<Lswlfl> lswlflList = contactsCategoryDao.findContactsCategory(id);
        contactsCategoryDao.dealContactsCategory(lswlflList);
        return lswlflList;
    };

    @Override
    public List<Lswlfl> getContactsCategoryAll(String id){

        List<Lswlfl> lswlflList = contactsCategoryDao.queryContactsCategory();
        contactsCategoryDao.dealContactsCategory(lswlflList);
        return lswlflList;
    };

    /**
    *@author lz
    *@Description TODO
    *@Date 2018/4/2 9:15
    *@Param [lswlfl]
    *@return void
    **/
    @Override
    public Msg saveContactsCategory(Lswlfl lswlfl){

        return contactsCategoryDao.saveContactsCategory(lswlfl);
    }

    /**
    *@author lz
    *@Description TODO
    *@Date 2018/4/2 9:13
    *@Param [id]
    *@return void
    **/
    @Override
    public Msg delContactsCategoryById(String id,String catLevel) {
        return contactsCategoryDao.delContactsCategoryById(id,catLevel);
    }

//    @Override
//    public msg delAll(List<Integer> del_ids) {
//        return contactsCategoryDao.delAll(del_ids);
//    }


    @Override
    public List<Lswlfl> queryContactsCategoryByLevel(String id, String levelFLag) {
        List<Lswlfl> lswlflList = contactsCategoryDao.queryContactsCategoryByLevel(id,levelFLag);
        contactsCategoryDao.dealContactsCategory(lswlflList);
        return lswlflList;
    }



}
