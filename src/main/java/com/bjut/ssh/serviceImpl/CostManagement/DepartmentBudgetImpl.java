package com.bjut.ssh.serviceImpl.CostManagement;
import com.bjut.ssh.dao.CostManagement.AnnualBudgetDao;
import com.bjut.ssh.dao.CostManagement.DepartmentBudgetDao;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.AnnualBudgetService;
import com.bjut.ssh.service.CostManagement.DepartmentBudgetService;
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
public class DepartmentBudgetImpl implements DepartmentBudgetService{
    @Autowired
    private DepartmentBudgetDao departmentBudgetDao;

    @Override
    public Msg getMaxLevel(){
        return departmentBudgetDao.getMaxLevel();
    }

    @Override
    public Msg getAnnualBudget(String Level_string, String ItemID){
        return departmentBudgetDao.getAnnualBudget(Level_string, ItemID);
    }

    @Override
    public Msg getItemSpBudgetInfo(String selectedItemID){
        return departmentBudgetDao.getItemSpBudgetInfo(selectedItemID);
    }

    @Override
    public Msg saveSpItemBudgetInfo(String annualBudget, String itemId, String spNo1, String spNo2){
        return departmentBudgetDao.saveSpItemBudgetInfo(annualBudget, itemId, spNo1, spNo2);
    }
}
