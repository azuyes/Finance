package com.bjut.ssh.serviceImpl.CostManagement;

import com.bjut.ssh.dao.CostManagement.ProProductionCostDao;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.ProProductionCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import javax.transaction.Transactional;


/**
 * @Title: ProProductionCostServiceImpl
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/8/26 14:20
 * @Version: 1.0
 */

@Service

@Transactional

public class ProProductionCostServiceImpl implements ProProductionCostService {
    @Autowired
    private ProProductionCostDao proProductionCostDao;

    @Override
    public Msg getValData(String selectSalesMethod, String selectTimeField){
        return proProductionCostDao.getValData(selectSalesMethod, selectTimeField);
    }

    @Override
    public Msg getItemsName(){
        return proProductionCostDao.getItemsName();
    }

    @Override
    public Msg getDatagridData(String selectTimeField){
        return proProductionCostDao.getDatagridData(selectTimeField);
    }
}
