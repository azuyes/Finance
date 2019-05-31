package com.bjut.ssh.service.CostManagement;

import com.bjut.ssh.entity.Msg;

/**
 * @Title: LaborCostService
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/8/3 11:09
 * @Version: 1.0
 */
public interface LaborCostService {
    public Msg getEchartsTheme();
    public Msg getValData(String selectTime);
    public Msg getDatagridData(String selectTime);
}
