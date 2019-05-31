package com.bjut.ssh.serviceImpl.CostManagement;

import com.bjut.ssh.dao.CostManagement.ProProductionCostChartDao;
import com.bjut.ssh.dao.CostManagement.SiteProductionCostChartDao;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.ProProductionCostChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Title: ProProductionCostChartServiceImpl
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/9/10 14:20
 * @Version: 1.0
 */
@Service

@Transactional
public class ProProductionCostChartServiceImpl implements ProProductionCostChartService{
    @Autowired
    private ProProductionCostChartDao proProductionCostChartDao;

    @Override
    public Msg getValData(String selectSiteValue){
        return  proProductionCostChartDao.getValData(selectSiteValue);
    }

    @Override
    public Msg getItemsName(){
        return  proProductionCostChartDao.getItemsName();
    }

    @Override
    public Msg getProduct(){
        return proProductionCostChartDao.getProduct();
    }

    @Override
    public Msg getDatagridData(String selectSite){
        return proProductionCostChartDao.getDatagridData(selectSite);
    }
}
