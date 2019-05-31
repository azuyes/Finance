package com.bjut.ssh.service.CostManagement;

import com.bjut.ssh.entity.Msg;

/**
 * @Title: SiteProductionCostStructureService
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/7/11 22:03
 * @Version: 1.0
 */

public interface SiteProductionCostStructureService {
    //获取图表数据
    public Msg getValData(String showNo, String selectTimeField, String selectSiteValue);
    //获取站点部门下拉菜单内容
    public Msg getSiteSelect(String selectDepartmentValue);
    //获取图标显示个数下拉菜单
    public Msg getShowNo(String selectSiteValue);
    //获取报表数据
    public Msg getDatagridData(String selectSite);
}
