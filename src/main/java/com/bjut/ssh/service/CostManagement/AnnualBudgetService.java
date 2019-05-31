package com.bjut.ssh.service.CostManagement;
import com.bjut.ssh.entity.Msg;

/**
 * @Title: QuotaSetService
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/7/6 11:09
 * @Version: 1.0
 */
public interface AnnualBudgetService {
    public Msg getAnnualBudget(String Level_string, String ItemID);
    public Msg saveAnnualBudgetInfo(String annualBudget, String itemNo);
    public Msg getLevelMax();
}
