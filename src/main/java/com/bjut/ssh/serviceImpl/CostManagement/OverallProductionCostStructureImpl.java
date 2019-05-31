package com.bjut.ssh.serviceImpl.CostManagement;
import com.bjut.ssh.dao.CostManagement.CompleteCostDao;
import com.bjut.ssh.dao.CostManagement.OverallProductionCostStructureDao;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.CompleteCostService;
import com.bjut.ssh.service.CostManagement.OverallProductionCostStructureService;
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
public class OverallProductionCostStructureImpl implements OverallProductionCostStructureService{
    @Autowired
    private OverallProductionCostStructureDao overallProductionCostStructureDao;

    @Override
    public Msg getEchartsTheme(){
        return overallProductionCostStructureDao.getEchartsTheme();
    }

    @Override
    public Msg getDepartmentSelect(){
        return overallProductionCostStructureDao.getDepartmentSelect();
    }

    @Override
    public Msg getProductSelect(){
        return overallProductionCostStructureDao.getProductSelect();
    }

    @Override
    public Msg getValData(String selectCompany, String selectProduct, String selectTime){
        return overallProductionCostStructureDao.getValData(selectCompany,selectProduct,selectTime);
    }

    @Override
    public Msg getDatagridData(String selectCompany, String selectProduct, String selectTime){
        return overallProductionCostStructureDao.getDatagridData(selectCompany,selectProduct,selectTime);
    }
}
