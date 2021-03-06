package com.bjut.ssh.service.CostManagement;

import com.bjut.ssh.entity.Msg;
/**
 * @Title: BasicOperatingExpensesService
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/7/6 11:09
 * @Version: 1.0
 */
public interface BasicOperatingExpensesService {
    //获取部门下拉菜单内容
    public Msg getDepartmentSelect();
    public Msg getValData(String selectCompany, String selectProduct, String selectTime);
    public Msg getDatagridData(String selectCompany, String selectProduct, String selectTime);
    public Msg getProductSelect();
    public Msg getItemNam();
    public Msg getEchartsTheme();
    public Msg ExportExcel(String bodyData);
}
