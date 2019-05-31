package com.bjut.ssh.serviceImpl.CostManagement;

import com.bjut.ssh.dao.CostManagement.DepreciationAndDepletionDao;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.DepreciationAndDepletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import javax.transaction.Transactional;

/**
 * @Title: DepreciationAndDepletionServieImp
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/7/6 14:01
 * @Version: 1.0
 */

@Service
@Transactional
public class DepreciationAndDepletionServiceImpl implements DepreciationAndDepletionService{
    @Autowired
    private DepreciationAndDepletionDao depreciationAndDepletionDao;

    @Override
    public Msg getEchartsTheme(){
        return depreciationAndDepletionDao.getEchartsTheme();
    }

    @Override
    public Msg getValData(String selectTime){
        return depreciationAndDepletionDao.getValData(selectTime);
    }

    @Override
    public Msg getDatagridData(String selectTime){
        return depreciationAndDepletionDao.getDatagridData(selectTime);
    }
}
