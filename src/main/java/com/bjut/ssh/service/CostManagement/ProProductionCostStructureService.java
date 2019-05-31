package com.bjut.ssh.service.CostManagement;

import com.bjut.ssh.entity.Msg;

/**
 * @Title: ProProductionCostStructureService
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/8/26 22:05
 * @Version: 1.0
 */

public interface ProProductionCostStructureService {
    //获取图表数据
    public Msg getValData(String showNo, String selectTimeField, String selectProductValue);
    //获取科目显示个数
    public Msg getShowNo(String selectProductValue);
    //获取产品名称
    public Msg getProduct();
    //获取报表数据
    public Msg getDatagridData(String selectSite);
}
