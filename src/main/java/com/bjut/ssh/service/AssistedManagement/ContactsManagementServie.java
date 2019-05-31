package com.bjut.ssh.service.AssistedManagement;

import com.bjut.ssh.entity.*;

import java.util.List;

/**
 * @Title: ContactsManagementServie
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/9 13:58
 * @Version: 1.0
 */
public interface ContactsManagementServie {

    //获取往来的基本信息以及往来分类
    List<LswldwQueryVo> getContactsCompany();

    //根据分类的catNo1获取分类信息
    List<Lswlfl> getContactsCategory(String catNo1);

    //保存新增的往来单位
    boolean saveContactsCategory(Lswldw lswldw);

    //根据往来单位的companyNo获取往来单位的详细信息
    Lswldw getContactsCompanyById(String companyNo);

    //保存往来单位的更新信息
    boolean updateContactsCompanyById(Lswldw lswldw);

    //批量定义，获取往来单位信息
    List<Lswldw> getContactsCompanyOfDefin();

    //批量定义，获取定义为往来的科目信息
    List<Lskmzd> getSpecialItemOfDefin();

    boolean defineOfCompanyAndItem(String[] companyNos,String[] itemNos);

    List<Lswlje> getDefinedOfCompanyAndItem();

    boolean judgeUnCheckedCompany(String companyNo);

    boolean judgeUnCheckedItem(String itemNo);

    boolean deleteContactsCompanyById(String companyNo);

}
