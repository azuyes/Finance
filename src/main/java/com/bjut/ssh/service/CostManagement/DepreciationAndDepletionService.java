package com.bjut.ssh.service.CostManagement;

import com.bjut.ssh.entity.Msg;

/**
 * @Title: DepreciationAndDepletionService
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/7/6 11:09
 * @Version: 1.0
 */

public interface DepreciationAndDepletionService {
    public Msg getEchartsTheme();
    public Msg getValData(String selectTime);
    public Msg getDatagridData(String selectTime);
}
