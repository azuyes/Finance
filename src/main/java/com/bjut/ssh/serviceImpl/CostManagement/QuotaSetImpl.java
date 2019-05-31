package com.bjut.ssh.serviceImpl.CostManagement;
import com.bjut.ssh.dao.CostManagement.QuotaSetDao;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.QuotaSetService;
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
public class QuotaSetImpl implements QuotaSetService{
    @Autowired
    private QuotaSetDao quotaSetDao;

    @Override
    public Msg getGraphAll(){
        return quotaSetDao.getGraphAll();
    }

    @Override
    public Msg getGraphInfo(String gridNo){
        return quotaSetDao.getGraphInfo(gridNo);
    }

    @Override
    public Msg deleteGraphInfo(String gridNo, String ItemNo, String ItemType){
        return quotaSetDao.deleteGraphInfo(gridNo, ItemNo, ItemType);
    }

    @Override
    public Msg addGraphInfo(String gridNo, String ItemNo, String ItemType, String ItemFunction){
        return quotaSetDao.addGraphInfo(gridNo, ItemNo, ItemType);
    }

    @Override
    public Msg getItemFunctionSelect(String gridNo){
        return quotaSetDao.getItemFunctionSelect(gridNo);
    }

    @Override
    public Msg getItemTypeSelect(String gridNo, String selectItemFunctionValue){
        return quotaSetDao.getItemTypeSelect(gridNo,selectItemFunctionValue);
    }

    @Override
    public Msg getItemNameSelect(String selectItemTypeValue){
        return quotaSetDao.getItemNameSelect(selectItemTypeValue);
    }
}
