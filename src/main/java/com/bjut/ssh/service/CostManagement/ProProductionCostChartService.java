package com.bjut.ssh.service.CostManagement;

import com.bjut.ssh.entity.Msg;

/**
 * @Title: ProProductionCostChartService
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/9/10 14:20
 * @Version: 1.0
 */

public interface ProProductionCostChartService {
    //获取图表数据
    public Msg getValData(String selectSiteValue);
    //获取图标显示项目
    public Msg getItemsName();
    //获取产品名称
    public Msg getProduct();
    //获取报表数据
    public Msg getDatagridData(String selectSite);
}
