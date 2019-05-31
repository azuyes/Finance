package com.bjut.ssh.service.CostManagement;
import com.bjut.ssh.entity.Msg;
/**
 * @Title: OverallProductionCostStructureService
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/7/6 11:09
 * @Version: 1.0
 */
public interface OverallProductionCostStructureService {
    public Msg getEchartsTheme();
    public Msg getDepartmentSelect();
    public Msg getValData(String selectCompany, String selectProduct, String selectTime);
    public Msg getDatagridData(String selectCompany, String selectProduct, String selectTime);
    public Msg getProductSelect();
}
