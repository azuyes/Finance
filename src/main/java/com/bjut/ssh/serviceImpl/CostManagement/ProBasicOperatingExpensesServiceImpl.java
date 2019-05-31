package com.bjut.ssh.serviceImpl.CostManagement;

import com.bjut.ssh.dao.CostManagement.ProBasicOperatingExpensesDao;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.ProBasicOperatingExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Title: ProBasicOperatingExpensesServiceImpl
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/8/26 22:05
 * @Version: 1.0
 */

@Service

@Transactional

public class ProBasicOperatingExpensesServiceImpl implements ProBasicOperatingExpensesService{
    @Autowired
    private ProBasicOperatingExpensesDao proBasicOperatingExpensesDao;

    @Override
    public Msg getValData(String selectSalesMethod, String selectTimeField){
        return proBasicOperatingExpensesDao.getValData(selectSalesMethod, selectTimeField);
    }

    @Override
    public Msg getItemsName(){
        return proBasicOperatingExpensesDao.getItemsName();
    }

    @Override
    public Msg getDatagridData(String selectTimeField){
        return proBasicOperatingExpensesDao.getDatagridData(selectTimeField);
    }
}
