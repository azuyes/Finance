package com.bjut.ssh.serviceImpl.CostManagement;

import com.bjut.ssh.dao.CostManagement.SiteProductionCostDao;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.SiteProductionCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import javax.transaction.Transactional;


/**
 * @Title: SiteProductionCostServiceImpl
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/7/17 14:20
 * @Version: 1.0
 */

@Service

@Transactional
public class SiteProductionCostServiceImpl implements SiteProductionCostService{
    @Autowired
    private SiteProductionCostDao siteProductionCostDao;

    @Override
    public Msg getDepartmentSelect(){
        return siteProductionCostDao.getDepartmentSelect();
    }

    @Override
    public Msg getSalesMethodSelect(){
        return siteProductionCostDao.getSalesMethodSelect();
    }

    @Override
    public Msg getValData(String selectSalesMethod, String selectTimeField, String selectDepartmentValue){
        return siteProductionCostDao.getValData(selectSalesMethod, selectTimeField, selectDepartmentValue);
    }

    @Override
    public Msg getItemsName(){
        return siteProductionCostDao.getItemsName();
    }

    @Override
    public Msg getDatagridData(String selectSite,String selectTimeField){
        return siteProductionCostDao.getDatagridData(selectSite,selectTimeField);
    }
}
