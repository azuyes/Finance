package com.bjut.ssh.service.ReportManagement;

import com.bjut.ssh.entity.*;

import java.util.List;

public interface ReportBaseService {

    //保存或修改Lcbzd报表基本信息,Lcdyzd报表数据
    Msg saveOrUpdateRpt(Lcbzd lcbzd,
                        String[][] cell_id,
                        String[][] cell_data,
                        String[][] cell_formula ,
                        String[][] formula_range ,
                        String[][] cell_merge ,
                        String[][] font_style ,
                        String[][] cell_className ,
                        String[][] cell_type ,
                        String[] cell_height,
                        String[] cell_width);

    //保存或修改Lcbzd报表基本信息,Lcdyzd报表数据
    boolean saveOrUpdateRpt(List<Lcbzd> lcbzdList,List<Lcdyzd> lcdyzdbList);

    //根据报表编号和日期查询报表基本信息
    Lcbzd findRptByNoAndDate(String rptNo,String rptDate);

    //根据月份查询报表
    List<Lcbzd> findRptByYearAndMonth(String year,String month);

    //根据报表编号和日期查询报表数据
    List<Lcdyzd> findRptDataByNoAndDate(String rptNo,String rptDate);

    //根据报表编号和日期删除报表基本信息和数据
    boolean delRptByNoAndDate(String rptNo,String rptDate);

    //根据报表编号数组和日期查询报表基本信息 rptDate格式为2018-01-01
    List<Lcbzd> findRptByNoAndDate(String[] rptNos,String rptDate);

    //根据报表编号数组和日期查询报表数据 rptDate格式为2018-01-01
    List<Lcdyzd> findRptDataByNoAndDate(String[] rptNos,String rptDate);

    //导入dat数据
    boolean importDat(String isExist,String[][] rptInfo,String[][] rptData,String[] rptNos,String rptDate,String[] rptCont);

    //获取报表当前日期
    String getRptDateNow();

    //设置系统财务日期
    void setSystemDate(String sysDate);

    //年结
    boolean yearCopy(String copyYear,String[] fromRptNo,Boolean isClearData);

    //计算公式
    String[][] calculateFormula(String formula,int yearNow,int monthNow,String RptNo,String[][] cell_data);

    //报表日期公式
    String getFormulaDate(String formula,int yearNow,int monthNow);

    //获取所有核算字典记录
    List<Lshszd> findAllHszd();

    //获取所有往来单位记录
    List<Lswldw> findAllWldw();
}
