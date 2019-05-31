package com.bjut.ssh.serviceImpl.CostManagement;

import com.bjut.ssh.dao.CostManagement.SiteProductionCostStructureDao;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.SiteProductionCostStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Title: SiteProductionCostStructureServiceImpl
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/7/21 22:05
 * @Version: 1.0
 */

@Service

@Transactional
public class SiteProductionCostStructureServiceImpl implements SiteProductionCostStructureService{
    @Autowired
    private SiteProductionCostStructureDao siteProductionCostStructureDao;

    @Override
    public Msg getValData(String showNo, String selectTimeField, String selectSiteValue){
        return siteProductionCostStructureDao.getValData(showNo, selectTimeField, selectSiteValue);
    }

    @Override
    public Msg getSiteSelect(String selectDepartmentValue){
        return siteProductionCostStructureDao.getSiteSelect(selectDepartmentValue);
    }

    @Override
    public Msg getShowNo(String selectSiteValue){
        return siteProductionCostStructureDao.getShowNo(selectSiteValue);
    }

    @Override
    public Msg getDatagridData(String selectSite){
        return siteProductionCostStructureDao.getDatagridData(selectSite);
    }
}
