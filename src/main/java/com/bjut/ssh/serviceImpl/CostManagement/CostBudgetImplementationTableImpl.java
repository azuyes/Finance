package com.bjut.ssh.serviceImpl.CostManagement;

import com.bjut.ssh.dao.CostManagement.CostBudgetImplementationTableDao;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.CostBudgetImplementationTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: BasicOperatingExpensesServieImp
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/7/6 14:01
 * @Version: 1.0
 */
@Service

@Transactional
public class CostBudgetImplementationTableImpl implements CostBudgetImplementationTableService{
    @Autowired
    private CostBudgetImplementationTableDao costBudgetImplementationTableDao;

    @Override
    public Msg getEchartsTheme(){
        return costBudgetImplementationTableDao.getEchartsTheme();
    }

    @Override
    public Msg getValData(String selectTime){
        return costBudgetImplementationTableDao.getValData(selectTime);
    }

    @Override
    public Msg getDatagridData(String selectTime){
        return costBudgetImplementationTableDao.getDatagridData(selectTime);
    }
}
