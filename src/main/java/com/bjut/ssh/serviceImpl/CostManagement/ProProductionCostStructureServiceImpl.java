package com.bjut.ssh.serviceImpl.CostManagement;

import com.bjut.ssh.dao.CostManagement.ProProductionCostStructureDao;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.ProProductionCostStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Title: ProProductionCostStructureServiceImpl
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/8/26 22:05
 * @Version: 1.0
 */
@Service
@Transactional
public class ProProductionCostStructureServiceImpl implements ProProductionCostStructureService{
    @Autowired
    private ProProductionCostStructureDao proProductionCostStructureDao;

    @Override
    public Msg getValData(String showNo, String selectTimeField, String selectProductValue){
        return proProductionCostStructureDao.getValData(showNo, selectTimeField, selectProductValue);
    }

    @Override
    public Msg getShowNo(String selectProductValue){
        return proProductionCostStructureDao.getShowNo(selectProductValue);
    }

    @Override
    public Msg getProduct(){
        return proProductionCostStructureDao.getProduct();
    }

    @Override
    public Msg getDatagridData(String selectSite){
        return proProductionCostStructureDao.getDatagridData(selectSite);
    }
}
