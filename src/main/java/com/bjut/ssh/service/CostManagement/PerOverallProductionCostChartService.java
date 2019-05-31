package com.bjut.ssh.service.CostManagement;
import com.bjut.ssh.entity.Msg;
/**
 * @Title: CompleteCostService
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/7/6 11:09
 * @Version: 1.0
 */
public interface PerOverallProductionCostChartService {
    public Msg getEchartsTheme();
    public Msg getValData1(String selectItemValue);
    public Msg getDatagridData();
    public Msg getItemSelect();
}
