package com.bjut.ssh.serviceImpl.CostManagement;

import com.bjut.ssh.dao.CostManagement.SiteBasicOperatingExpensesDao;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.SiteBasicOperatingExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Title: SiteBasicOperatingExpensesServiceImpl
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/7/19 16:25
 * @Version: 1.0
 */

@Service

@Transactional
public class SiteBasicOperatingExpensesServiceImpl implements SiteBasicOperatingExpensesService{
    @Autowired
    private SiteBasicOperatingExpensesDao siteBasicOperatingExpensesDao;

    @Override
    public Msg getValData(String selectSalesMethod, String selectTimeField, String selectDepatmentValue){
        return siteBasicOperatingExpensesDao.getValData(selectSalesMethod, selectTimeField, selectDepatmentValue);
    }

    @Override
    public Msg getItemsName(){
        return siteBasicOperatingExpensesDao.getItemsName();
    }

    @Override
    public Msg getDatagridData(String selectSite,String selectTimeField){
        return siteBasicOperatingExpensesDao.getDatagridData(selectSite,selectTimeField);
    }
}
