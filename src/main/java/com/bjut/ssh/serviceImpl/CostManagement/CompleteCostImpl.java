package com.bjut.ssh.serviceImpl.CostManagement;

import com.bjut.ssh.dao.CostManagement.CompleteCostDao;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.CompleteCostService;
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
public class CompleteCostImpl implements CompleteCostService{
    @Autowired
    private CompleteCostDao completeCostDao;

    @Override
    public Msg getEchartsTheme(){
        return completeCostDao.getEchartsTheme();
    }

    @Override
    public Msg getDepartmentSelect(){
        return completeCostDao.getDepartmentSelect();
    }

    @Override
    public Msg getProductSelect(){
        return completeCostDao.getProductSelect();
    }

    @Override
    public Msg getValData(String selectCompany, String selectProduct, String selectTime){
        return completeCostDao.getValData(selectCompany,selectProduct,selectTime);
    }

    @Override
    public Msg getDatagridData(String selectCompany, String selectProduct, String selectTime){
        return completeCostDao.getDatagridData(selectCompany,selectProduct,selectTime);
    }

    @Override
    public Msg getItemNam(){
        return completeCostDao.getItemNam();
    }
}
