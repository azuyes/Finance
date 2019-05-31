package com.bjut.ssh.serviceImpl.CostManagement;

import com.bjut.ssh.dao.CostManagement.ManagementCostDao;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.DepreciationAndDepletionService;
import com.bjut.ssh.service.CostManagement.ManagementCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: ManagementCostServieImp
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/8/3 14:01
 * @Version: 1.0
 */

@Service
@Transactional
public class ManagementCostImpl implements ManagementCostService{
    @Autowired
    private ManagementCostDao managementCostDao;

    @Override
    public Msg getEchartsTheme(){
        return managementCostDao.getEchartsTheme();
    }

    @Override
    public Msg getValData(String selectTime){
        return managementCostDao.getValData(selectTime);
    }

    @Override
    public Msg getDatagridData(String selectTime){
        return managementCostDao.getDatagridData(selectTime);
    }
}
