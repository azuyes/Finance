package com.bjut.ssh.service.CostManagement;

import com.bjut.ssh.entity.Msg;

/**
 * @Title: SiteBasicOperatingExpensesService
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/7/19 16:25
 * @Version: 1.0
 */

public interface SiteBasicOperatingExpensesService {
    //获取图表数据部门下拉菜单
    public Msg getValData(String selectSalesMethod, String selectTimeField, String selectDepatmentValue);
    //获取图标显示项目
    public Msg getItemsName();
    //获取报表数据
    public Msg getDatagridData(String selectSite,String selectTimeField);
}
