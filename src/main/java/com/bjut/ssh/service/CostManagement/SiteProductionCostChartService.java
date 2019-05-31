package com.bjut.ssh.service.CostManagement;

import com.bjut.ssh.entity.Msg;

/**
 * @Title: SiteProductionCostChartService
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/7/11 22:22
 * @Version: 1.0
 */

public interface SiteProductionCostChartService {
    //获取图表数据
    public Msg getValData(String selectSiteValue);
    //获取图标显示项目
    public Msg getItemsName();
    //获取报表数据
    public Msg getDatagridData(String selectSite);
}
