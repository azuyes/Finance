package com.bjut.ssh.serviceImpl.CostManagement;

import com.bjut.ssh.dao.CostManagement.OverallProductionCostChartDao;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.OverallProductionCostChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Title: CompleteCostImpl
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/7/6 14:01
 * @Version: 1.0
 */
@Service
@Transactional
public class OverallProductionCostChartImpl implements OverallProductionCostChartService{
    @Autowired
    private OverallProductionCostChartDao overallProductionCostChartDao;

    @Override
    public Msg getEchartsTheme(){
        return overallProductionCostChartDao.getEchartsTheme();
    }

    @Override
    public Msg getValData1(String selectItemValue){
        return overallProductionCostChartDao.getValData1(selectItemValue);
    }

    @Override
    public Msg getDatagridData1(){
        return overallProductionCostChartDao.getDatagridData1();
    }

    @Override
    public Msg getItemSelect(){
        return overallProductionCostChartDao.getItemSelect();
    }

}
