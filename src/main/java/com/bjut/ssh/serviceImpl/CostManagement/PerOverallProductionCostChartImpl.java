package com.bjut.ssh.serviceImpl.CostManagement;
import com.bjut.ssh.dao.CostManagement.PerOverallProductionCostChartDao;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.PerOverallProductionCostChartService;
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
public class PerOverallProductionCostChartImpl implements PerOverallProductionCostChartService{
    @Autowired
    private PerOverallProductionCostChartDao perOverallProductionCostChartDao;

    @Override
    public Msg getEchartsTheme(){
        return perOverallProductionCostChartDao.getEchartsTheme();
    }

    @Override
    public Msg getValData1(String selectItemValue){
        return perOverallProductionCostChartDao.getValData1(selectItemValue);
    }

    @Override
    public Msg getDatagridData(){
        return perOverallProductionCostChartDao.getDatagridData();
    }

    @Override
    public Msg getItemSelect(){
        return perOverallProductionCostChartDao.getItemSelect();
    }

}
