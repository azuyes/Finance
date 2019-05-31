package com.bjut.ssh.service.CostManagement;

import com.bjut.ssh.entity.Msg;

/**
 * @Title: ProProductionCostService
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/8/26 14:20
 * @Version: 1.0
 */

public interface ProProductionCostService {
    //获取图表数据
    public Msg getValData(String selectSalesMethod, String selectTimeField);
    //获取图标显示项目
    public Msg getItemsName();
    //获取报表数据
    public Msg getDatagridData(String selectTimeField);
}
