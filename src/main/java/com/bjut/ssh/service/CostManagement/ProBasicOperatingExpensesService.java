package com.bjut.ssh.service.CostManagement;

import com.bjut.ssh.entity.Msg;

/**
 * @Title: ProBasicOperatingExpensesService
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/8/26 22:05
 * @Version: 1.0
 */

public interface ProBasicOperatingExpensesService {
    //获取图表数据
    public Msg getValData(String selectSalesMethod, String selectTimeField);
    //获取图标显示项目
    public Msg getItemsName();
    //获取报表数据
    public Msg getDatagridData(String selectTimeField);
}
