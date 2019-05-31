package com.bjut.ssh.serviceImpl.CostManagement;

import com.bjut.ssh.dao.CostManagement.SiteBasicOperatingExpensesDao;
import com.bjut.ssh.dao.CostManagement.SiteProductionCostChartDao;
import com.bjut.ssh.dao.CostManagement.SiteProductionCostStructureDao;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.SiteBasicOperatingExpensesService;
import com.bjut.ssh.service.CostManagement.SiteProductionCostChartService;
import com.bjut.ssh.service.CostManagement.SiteProductionCostStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Title: SiteProductionCostChartServiceImpl
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/7/21 22:23
 * @Version: 1.0
 */

@Service

@Transactional
public class SiteProductionCostChartServiceImpl implements SiteProductionCostChartService{
    @Autowired
    private SiteProductionCostChartDao siteProductionCostChartDao;

    @Override
    public Msg getValData(String selectSiteValue){
        return siteProductionCostChartDao.getValData(selectSiteValue);
    }

    @Override
    public Msg getItemsName(){
        return siteProductionCostChartDao.getItemsName();
    }

    @Override
    public Msg getDatagridData(String selectSite){
        return siteProductionCostChartDao.getDatagridData(selectSite);
    }
}
