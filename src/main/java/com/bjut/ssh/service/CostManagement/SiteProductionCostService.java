package com.bjut.ssh.service.CostManagement;

import com.bjut.ssh.entity.Msg;

/**
 * @Title: SiteProductionCostController
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/7/17 14:20
 * @Version: 1.0
 */

public interface SiteProductionCostService {
    //获取销售方式下拉菜单内容
    public Msg getSalesMethodSelect();
    //获取图表数据
    public Msg getValData(String selectSalesMethod, String selectTimeField, String selectDepartmentValue);
    //获取分公司下拉菜单
    public Msg getDepartmentSelect();
    //获取图标显示项目
    public Msg getItemsName();
    //获取报表数据
    public Msg getDatagridData(String selectSite,String selectTimeField);
}
