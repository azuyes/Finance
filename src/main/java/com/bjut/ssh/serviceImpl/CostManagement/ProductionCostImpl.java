package com.bjut.ssh.serviceImpl.CostManagement;

import com.bjut.ssh.dao.CostManagement.ProductionCostDao;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.ProductionCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Title: ProductionCostImpl
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/7/6 14:01
 * @Version: 1.0
 */
@Service

@Transactional
public class ProductionCostImpl implements ProductionCostService{
    @Autowired
    private ProductionCostDao productionCostDao;

    @Override
    public Msg getEchartsTheme(){
        return productionCostDao.getEchartsTheme();
    }

    @Override
    public Msg getDepartmentSelect(){
        return productionCostDao.getDepartmentSelect();
    }

    @Override
    public Msg getProductSelect(){
        return productionCostDao.getProductSelect();
    }

    @Override
    public Msg getValData(String selectCompany, String selectProduct, String selectTime){
        return productionCostDao.getValData(selectCompany,selectProduct,selectTime);
    }

    @Override
    public Msg getDatagridData(String selectCompany, String selectProduct, String selectTime){
        return productionCostDao.getDatagridData(selectCompany,selectProduct,selectTime);
    }

    @Override
    public Msg getItemNam(){
        return productionCostDao.getItemNam();
    }
}
