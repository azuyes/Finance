package com.bjut.ssh.service.CostManagement;
import com.bjut.ssh.entity.Msg;

/**
 * @Title: QuotaSetService
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/7/6 11:09
 * @Version: 1.0
 */
public interface DepartmentBudgetService {
    public Msg getMaxLevel();
    public Msg getAnnualBudget(String Level_string, String ItemID);
    public Msg getItemSpBudgetInfo(String selectedItemID);
    public Msg saveSpItemBudgetInfo(String annualBudget, String itemId,String spNo1,String spNo2);
}
