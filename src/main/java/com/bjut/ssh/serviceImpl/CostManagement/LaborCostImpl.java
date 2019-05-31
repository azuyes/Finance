package com.bjut.ssh.serviceImpl.CostManagement;

import com.bjut.ssh.dao.CostManagement.LaborCostDao;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.LaborCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import javax.transaction.Transactional;

/**
 * @Title: LaborCostServieImp
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/8/3 14:01
 * @Version: 1.0
 */

@Service
@Transactional
public class LaborCostImpl implements LaborCostService{
    @Autowired
    private LaborCostDao laborCostDao;

    @Override
    public Msg getEchartsTheme(){
        return laborCostDao.getEchartsTheme();
    }

    @Override
    public Msg getValData(String selectTime){
        return laborCostDao.getValData(selectTime);
    }

    @Override
    public Msg getDatagridData(String selectTime){
        return laborCostDao.getDatagridData(selectTime);
    }
}
