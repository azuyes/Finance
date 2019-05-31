package com.bjut.ssh.serviceImpl.CostManagement;

import com.bjut.ssh.dao.CostManagement.BasicOperatingExpensesDao;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.BasicOperatingExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Title: BasicOperatingExpensesServieImp
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/7/6 14:01
 * @Version: 1.0
 */
@Service

@Transactional
public class BasicOperatingExpensesServiceImpl implements BasicOperatingExpensesService{
    @Autowired
    private BasicOperatingExpensesDao basicOperatingExpensesDao;

    @Override
    public Msg getDepartmentSelect(){
        return basicOperatingExpensesDao.getDepartmentSelect();
    }

    @Override
    public Msg getProductSelect(){
        return basicOperatingExpensesDao.getProductSelect();
    }

    @Override
    public Msg getValData(String selectCompany, String selectProduct, String selectTime){
        return basicOperatingExpensesDao.getValData(selectCompany,selectProduct,selectTime);
    }

    @Override
    public Msg getDatagridData(String selectCompany, String selectProduct, String selectTime){
        return basicOperatingExpensesDao.getDatagridData(selectCompany,selectProduct,selectTime);
    }

    @Override
    public Msg getItemNam(){
        return basicOperatingExpensesDao.getItemNam();
    }

    @Override
    public Msg getEchartsTheme(){
        return basicOperatingExpensesDao.getEchartsTheme();
    }

    @Override
    public Msg ExportExcel(String bodyData){
        return basicOperatingExpensesDao.ExportExcel(bodyData);
    }
}
