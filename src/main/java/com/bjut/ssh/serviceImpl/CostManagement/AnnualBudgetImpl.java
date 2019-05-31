package com.bjut.ssh.serviceImpl.CostManagement;
import com.bjut.ssh.dao.CostManagement.AnnualBudgetDao;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.AnnualBudgetService;
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
public class AnnualBudgetImpl implements AnnualBudgetService{
    @Autowired
    private AnnualBudgetDao annualBudgetDao;

    @Override
    public Msg getAnnualBudget(String Level_string, String ItemID){
        return annualBudgetDao.getAnnualBudget(Level_string, ItemID);
    }

    @Override
    public Msg saveAnnualBudgetInfo(String annualBudget, String itemNo){
        return annualBudgetDao.saveAnnualBudgetInfo(annualBudget, itemNo);
    }

    @Override
    public Msg getLevelMax(){
        return annualBudgetDao.getLevelMax();
    }
}
