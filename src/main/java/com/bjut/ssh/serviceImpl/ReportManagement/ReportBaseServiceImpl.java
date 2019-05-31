package com.bjut.ssh.serviceImpl.ReportManagement;

import com.bjut.ssh.dao.ReportManagement.ReportBaseDao;
import com.bjut.ssh.entity.*;
import com.bjut.ssh.service.ReportManagement.ReportBaseService;
import com.bjut.ssh.util.Calculator;
import com.bjut.ssh.util.FormulaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class ReportBaseServiceImpl implements ReportBaseService {

    @Autowired
    private ReportBaseDao reportBaseDao;

    @Override
    @Transactional
    public Msg saveOrUpdateRpt(Lcbzd lcbzd,
                               String[][] cell_id,
                               String[][] cell_data,
                               String[][] cell_formula,
                               String[][] formula_range,
                               String[][] cell_merge,
                               String[][] font_style,
                               String[][] cell_className,
                               String[][] cell_type,
                               String[] cell_height,
                               String[] cell_width){
        List<Lcdyzd> lcdyzdList=new ArrayList<Lcdyzd>();
        System.out.println("=================service"+lcbzd.getRptNo());
        for(int i=0;i<cell_data.length;i++){
            for(int j=0;j<cell_data[i].length;j++){
                Lcdyzd lcdyzd = new Lcdyzd();
                lcdyzd.setRptNo(lcbzd.getRptNo());
                lcdyzd.setRptDate(lcbzd.getRptDate());
                lcdyzd.setRowInfoNo(i+"");
                lcdyzd.setColInfoNo(j+"");
                if(i<cell_id.length && j<cell_id[i].length && cell_id[i][j]!=null && (!cell_id[i][j].equals("")) )
                    lcdyzd.setDyzdAutoId(Integer.parseInt(cell_id[i][j]));
                if(cell_data[i][j]!=null){
                    if((!cell_data[i][j].equals(""))&&cell_data[i][j].charAt(0)=='`')//去除"`"字符
                        cell_data[i][j]=cell_data[i][j].substring(1);
                    lcdyzd.setCellContent(cell_data[i][j]);
                }
                if(cell_formula[i][j]!=null)
                    lcdyzd.setFormula(cell_formula[i][j]);
                if(formula_range[i][j]!=null)
                    lcdyzd.setFormulaRange(formula_range[i][j]);
                if(font_style[i][j]!=null && (!font_style[i][j].equals("")) )
                    lcdyzd.setFontStyle(font_style[i][j]);
                if(cell_className[i][j]!=null && (!cell_className[i][j].equals("")) )
                    lcdyzd.setCellClassName(cell_className[i][j]);
                if(cell_type[i][j]!=null && (!cell_type[i][j].equals("")) )
                    lcdyzd.setCellType(cell_type[i][j]);
                if(cell_height[i]!=null)
                    lcdyzd.setCellHeight(Integer.parseInt(cell_height[i]));
                if(cell_width[j]!=null)
                    lcdyzd.setCellWidth(Integer.parseInt(cell_width[j]));
                lcdyzdList.add(lcdyzd);
            }
        }
        //合并--设置endRow、endCol
        for (int i = 0; i < cell_merge.length-1; i++) {
            if(cell_merge[i].length==1)
                break;
            int rowTemp = Integer.parseInt(cell_merge[i][0]);
            int colTemp = Integer.parseInt(cell_merge[i][1]);
            int pos = rowTemp*cell_data[0].length+colTemp;
            lcdyzdList.get(pos).setEndRow(cell_merge[i][2]);
            lcdyzdList.get(pos).setEndCol(cell_merge[i][3]);
        }
        reportBaseDao.deleteUnUseRptData(lcbzd);
        return reportBaseDao.saveOrUpdateReport(lcbzd,lcdyzdList);
    }

    @Override
    @Transactional
    public boolean saveOrUpdateRpt(List<Lcbzd> lcbzdList,List<Lcdyzd> lcdyzdList){
        return reportBaseDao.saveOrUpdateReport(lcbzdList,lcdyzdList);
    }

    @Override
    @Transactional
    public Lcbzd findRptByNoAndDate(String rptNo,String rptDate){
        return reportBaseDao.findRptByNoAndDate(rptNo,rptDate);
    }

    @Override
    @Transactional
    public List<Lcbzd> findRptByNoAndDate(String[] rptNos,String rptDate){
        return reportBaseDao.findRptByNoAndDate(rptNos,rptDate);
    }

    @Override
    @Transactional
    public List<Lcbzd> findRptByYearAndMonth(String year,String month){
        return reportBaseDao.findReportByYearAndMonth(year,month);
    }

    @Override
    @Transactional
    public List<Lcdyzd> findRptDataByNoAndDate(String rptNo,String rptDate){
        return reportBaseDao.findRptDataByNoAndDate(rptNo,rptDate);
    }

    @Override
    @Transactional
    public List<Lcdyzd> findRptDataByNoAndDate(String[] rptNos,String rptDate){
        return reportBaseDao.findRptDataByNoAndDate(rptNos,rptDate);
    }

    @Override
    @Transactional
    public boolean delRptByNoAndDate(String rptNo,String rptDate){
        return reportBaseDao.deleteRpt(rptNo,rptDate);
    }

    @Override
    @Transactional
    public boolean importDat(String isExist,String[][] rptInfo,String[][] rptData,String[] rptNos,String rptDate,String[] rptCont){
        //删除已存在报表
        if(null!=isExist && !isExist.equals("")){
            String[] delRptNos = isExist.split("[ ]");
            for(int i=0;i<delRptNos.length;i++){
                String delRptNo = rptNos[i];
                reportBaseDao.deleteRpt(delRptNo,rptDate);
            }
        }
        //初始化选中报表字符串
        String rptNosStr = "";
        for (int i = 0; i < rptNos.length; i++) {
            rptNosStr = rptNosStr + rptNos[i] + " ";
        }
        //导入数据
        List<Lcbzd> lcbzdList = new ArrayList<Lcbzd>();
        List<Lcdyzd> lcdyzdList = new ArrayList<Lcdyzd>();
        for (int i = 0; i < rptInfo.length-1; i++) {
            if(rptNosStr.indexOf(rptInfo[i][1])!=-1){//只导入存在于选中报表字符串中的数据
                Lcbzd lcbzd = new Lcbzd();
                //lcbzd.setBzdAutoId(Integer.parseInt(rptInfo[i][0]));
                lcbzd.setRptNo(rptInfo[i][1]);
                //lcbzd.setRptDate(rptInfo[i][2]);
                lcbzd.setRptDate(rptDate);
                lcbzd.setRptName(rptInfo[i][3]);
                lcbzd.setTitleRules(Integer.parseInt(rptInfo[i][4]));
                lcbzd.setHeadRules(Integer.parseInt(rptInfo[i][5]));
                lcbzd.setBodyRules(Integer.parseInt(rptInfo[i][6]));
                lcbzd.setTotalRules(Integer.parseInt(rptInfo[i][7]));
                lcbzd.setRptCol(Integer.parseInt(rptInfo[i][8]));
                lcbzdList.add(lcbzd);
            }
        }
        for (int i = 0; i < rptData.length-1; i++) {
            if(rptNosStr.indexOf(rptData[i][1])!=-1){
                Lcdyzd lcdyzd = new Lcdyzd();
                //lcdyzd.setDyzdAutoId(Integer.parseInt(rptData[i][0]));
                lcdyzd.setRptNo(rptData[i][1]);
                //lcdyzd.setRptDate(rptData[i][2]);
                lcdyzd.setRptDate(rptDate);
                lcdyzd.setRowInfoNo(rptData[i][3]);
                lcdyzd.setColInfoNo(rptData[i][4]);
                if( rptData[i][5]!=null && !rptData[i][5].equals(""))
                    lcdyzd.setEndRow(rptData[i][5]);
                if( rptData[i][6]!=null && !rptData[i][6].equals(""))
                    lcdyzd.setEndCol(rptData[i][6]);
                if( rptData[i][7]!=null && !rptData[i][7].equals(""))
                    lcdyzd.setCellType(rptData[i][7]);
                if (rptData[i][8]!=null && !rptData[i][8].equals("") &&
                        (lcdyzd.getCellType() == null || lcdyzd.getCellType().indexOf("number") == -1)) {
                    lcdyzd.setCellContent(rptData[i][8]);//报表数据可以赋值并且不是数值列
                }
                for (int j = 0; j < rptCont.length; j++) {
                    switch (rptCont[j]) {
                        case "bbsj":
                            if( rptData[i][8]!=null && !rptData[i][8].equals(""))
                                lcdyzd.setCellContent(rptData[i][8]);
                            break;
                        case "jsgs":
                            if( rptData[i][9]!=null && !rptData[i][9].equals(""))
                                lcdyzd.setFormula(rptData[i][9]);
                            break;
                    }
                }

                if( rptData[i][10]!=null && !rptData[i][10].equals(""))
                    lcdyzd.setCellClassName(rptData[i][10]);
                if( rptData[i][11]!=null && !rptData[i][11].equals(""))
                    lcdyzd.setCellWidth(Integer.parseInt(rptData[i][11]));
                if( rptData[i][12]!=null && !rptData[i][12].equals(""))
                    lcdyzd.setCellHeight(Integer.parseInt(rptData[i][12]));
                if( rptData[i][13]!=null && !rptData[i][13].equals(""))
                    lcdyzd.setFontStyle(rptData[i][13]);
                if( rptData[i][14]!=null && !rptData[i][14].equals(""))
                    lcdyzd.setFormulaRange(rptData[i][14]);
                lcdyzdList.add(lcdyzd);
            }
        }
        if(reportBaseDao.saveOrUpdateReport(lcbzdList,lcdyzdList)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    @Transactional
    public String getRptDateNow() {
        Lsconf lsconf_year = reportBaseDao.getLsconfData("Rpt_Year_Now");
        Lsconf lsconf_month = reportBaseDao.getLsconfData("Rpt_Month_Now");
        if(lsconf_year==null||lsconf_month==null||lsconf_year.equals("")||lsconf_month.equals(""))
            return null;
        else
            return lsconf_year.getConfValue()+"-"+lsconf_month.getConfValue();
    }

    @Override
    @Transactional
    public void setSystemDate(String sysDate) {
        reportBaseDao.setLsconfData("Rpt_Year_Now",sysDate.substring(0,4));
        reportBaseDao.setLsconfData("Rpt_Month_Now",sysDate.substring(5,7));
    }

    @Override
    @Transactional
    public boolean yearCopy(String copyYear,String[] fromRptNo,Boolean isClearData){
        List<Lcbzd> lcbzdList = reportBaseDao.findRptByNoAndDate(fromRptNo, copyYear + "-12");
        List<Lcdyzd> lcdyzdList = reportBaseDao.findRptDataByNoAndDate(fromRptNo,copyYear+"-12");
        String toYear = (Integer.parseInt(copyYear)+1)+"";
        for (int i = 0; lcbzdList!=null && i < lcbzdList.size(); i++) {
            lcbzdList.get(i).setBzdAutoId(null);
            lcbzdList.get(i).setRptDate(toYear+"-01");
        }
        for (int i = 0; lcdyzdList!=null && i < lcdyzdList.size(); i++) {
            lcdyzdList.get(i).setDyzdAutoId(null);
            lcdyzdList.get(i).setRptDate(toYear+"-01");
            if(isClearData&&lcdyzdList.get(i).getCellType()!=null&&lcdyzdList.get(i).getCellType().indexOf("number")!=-1){
                lcdyzdList.get(i).setCellContent(null);
                lcdyzdList.get(i).setFormula(null);
            }
        }
        reportBaseDao.yearCopy(copyYear);//新建数据库表，复制数据，删除原表(Lcbzd、Lcdyzd)数据
        reportBaseDao.saveOrUpdateReport(lcbzdList, lcdyzdList);//添加12月份数据至原表
        reportBaseDao.setLsconfData("Rpt_Year_Now",toYear);//修改Lsconf年份
        reportBaseDao.setLsconfData("Rpt_Month_Now","01");//修改Lsconf月份
        return true;
    }

    @Override
    @Transactional
    public String[][] calculateFormula(String formula,int yearNow,int monthNow,String RptNo,String[][] cell_data){
        String[][] resultArray = null;
        String[][] resultArray_temp = null;
        List<FormulaNode> formulaNodeList = new ArrayList<FormulaNode>();
        FormulaUtil.addFormularNode(formula,formulaNodeList);
        String subStruc = reportBaseDao.getSubjectStructure();
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//格式化设置
        if(formula.indexOf("BB")!=-1){//含有报表函数的公式
            int row_length = 1;
            int col_length = 1;
            //设置公式原子结果数组
            for (int i = 0; i < formulaNodeList.size(); i++) {
                FormulaNode formulaNode = formulaNodeList.get(i);
                resultArray_temp = null;
                if(formulaNode.getNodeStr().indexOf(',')==-1){//本表取报表数据
                    int[] rangeArray = FormulaUtil.getRange(formulaNode.getNodeStr().substring(formulaNode.getNodeStr().indexOf('(')+1, formulaNode.getNodeStr().indexOf(')')));
                    if (rangeArray[0] <= rangeArray[2] && rangeArray[1] <= rangeArray[3]) {
                        //判断是否超过报表范围
                        if (rangeArray[2] > (cell_data.length - 1)) {
                            rangeArray[2] = (cell_data.length - 1);
                        }
                        if (rangeArray[3] > (cell_data[0].length - 1)) {
                            rangeArray[3] = (cell_data[0].length - 1);
                        }
                        //给公式原子结果数组赋值
                        resultArray_temp = new String[rangeArray[2]-rangeArray[0]+1][rangeArray[3]-rangeArray[1]+1];
                        for (int j = 0; j < resultArray_temp.length; j++) {
                            for (int k = 0; k < resultArray_temp[j].length; k++) {
                                resultArray_temp[j][k]=cell_data[rangeArray[0]+j][rangeArray[1]+k];
                            }
                        }
                    }else
                        continue;
                }else{//数据库取报表数据
                    int[] rangeArray = FormulaUtil.getRange(formulaNode.getNodeStr().substring(formulaNode.getNodeStr().lastIndexOf(',')+1, formulaNode.getNodeStr().indexOf(')')));
                    String sql = FormulaUtil.BBtoSql(formulaNode.getNodeStr(), yearNow, monthNow,RptNo);
                    if (sql != null && !sql.equals("") && rangeArray[0] <= rangeArray[2] && rangeArray[1] <= rangeArray[3]) {
                        List<Object[]> list_temp = reportBaseDao.getFormulaResults(sql);
                        //给公式原子结果数组赋值
                        resultArray_temp = new String[rangeArray[2]-rangeArray[0]+1][rangeArray[3]-rangeArray[1]+1];
                        for (int j = 0; j < list_temp.size(); j++) {
                            int x_temp = Integer.parseInt(list_temp.get(j)[0].toString());
                            int y_temp = Integer.parseInt(list_temp.get(j)[1].toString());
                            resultArray_temp[x_temp-rangeArray[0]][y_temp-rangeArray[1]] = list_temp.get(j)[2].toString();
                        }
                    }else
                        continue;
                }
                //判断是否--SUM函数
                if (formulaNode.getStart() > 4 && formula.substring(formulaNode.getStart() - 4, formulaNode.getStart() - 1).equals("SUM")) {
                    String[][] sum_array = new String[1][1];
                    double sum = 0;
                    for (int j = 0; j < resultArray_temp.length; j++) {
                        for (int k = 0; k < resultArray_temp[j].length; k++) {
                            if (resultArray_temp[j][k] != null && (!resultArray_temp[j][k].trim().equals("")) && resultArray_temp[j][k].trim().matches("^(\\-|\\+)?\\d+(\\.\\d+)?$")) {
                                sum += Double.parseDouble(resultArray_temp[j][k].trim());
                            }
                        }
                    }
                    sum_array[0][0] = sum + "";
                    formulaNode.setNodeStr(formula.substring(formulaNode.getStart()-4,formulaNode.getEnd()+2));
                    formulaNode.setStart(formulaNode.getStart()-4);
                    formulaNode.setEnd(formulaNode.getEnd()+1);
                    formulaNode.setResultArray(sum_array);
                } else {
                    //确定最终返回结果数组范围
                    if (row_length < resultArray_temp.length)
                        row_length = resultArray_temp.length;
                    if (col_length < resultArray_temp[0].length)
                        col_length = resultArray_temp[0].length;
                    formulaNode.setResultArray(resultArray_temp);
                }
            }
            resultArray = new String[row_length][col_length];
            for (int i = 0; i < resultArray.length; i++) {
                for (int j = 0; j < resultArray[i].length; j++) {
                    String formula_temp = formula.toString().trim();
                    for (int k = 0; k < formulaNodeList.size(); k++) {
                        FormulaNode formulaNode = formulaNodeList.get(k);
                        if (formulaNode.getResultArray() != null && formulaNode.getResultArray().length > i && formulaNode.getResultArray()[i].length > j) {
                            if (!formulaNode.getResultArray()[i][j].trim().equals("")) {
                                formula_temp = formula_temp.replace(formulaNode.getNodeStr(), formulaNode.getResultArray()[i][j].trim());
                            }else{
                                formula_temp = formula_temp.replace(formulaNode.getNodeStr(), "0");
                            }
                        }else{
                            formula_temp = formula_temp.replace(formulaNode.getNodeStr(), "0");
                        }
                    }
                    System.out.println(formula_temp);
                    if(formula_temp.substring(1).trim().matches("[\\u4E00-\\u9FA5A-Za-z_]+")){
                        resultArray[i][j] = formula_temp.substring(1);
                    }else{
                        resultArray[i][j] = decimalFormat.format(Calculator.conversion(formula_temp.substring(1)+"+0"));
                    }
                }
            }
        }else{//其他函数公式
            int max_length = 1;
            boolean num_flag = false;//数字_数据类型标记
            boolean str_flag = false;//字符_数据类型标记
            //设置公式原子结果链表
            for (int i = 0; i < formulaNodeList.size(); i++) {
                FormulaNode formulaNode = formulaNodeList.get(i);
                String sql = null;
                switch (formulaNode.getNodeStr().substring(0, 4)) {
                    case "KMJE":
                        sql = FormulaUtil.KMJEtoSql(formulaNode.getNodeStr(), yearNow, monthNow,subStruc);
                        break;
                    case "XMJE":
                        sql = FormulaUtil.XMJEtoSql(formulaNode.getNodeStr(), yearNow, monthNow,subStruc);
                        break;
                    case "WLJE":
                        sql = FormulaUtil.WLJEtoSql(formulaNode.getNodeStr(), yearNow, monthNow,subStruc);
                        break;
                }
                if (sql != null && !sql.equals("")) {
                    List<Object> list_temp = reportBaseDao.getFormulaResult(sql);
                    if (list_temp != null && list_temp.size() > 0) {
                        if(list_temp.get(0).toString().matches("^(\\-|\\+)?\\d+(\\.\\d+)?$"))
                            num_flag = true;
                        else
                            str_flag = true;
                    }
                    //判断是否--SUM函数
                    if (formulaNode.getStart() > 4 && formula.substring(formulaNode.getStart() - 4, formulaNode.getStart() - 1).equals("SUM")) {
                        List<Object> sum_list = new ArrayList<Object>();
                        double sum = 0;
                        for (int j = 0; j < list_temp.size(); j++) {
                            if (list_temp.get(j) != null
                                    && (!list_temp.get(j).toString().trim().equals(""))
                                    && list_temp.get(j).toString().trim().matches("^[+-]?\\d+(\\.\\d+)?([Ee][+-]?[\\d]+)?$")) {
                                System.out.println(list_temp.get(j).toString()+"====");
                                sum += Double.parseDouble(list_temp.get(j).toString().trim());
                            }
                        }
                        sum_list.add(sum);
                        formulaNode.setNodeStr(formula.substring(formulaNode.getStart()-4,formulaNode.getEnd()+2));
                        formulaNode.setStart(formulaNode.getStart()-4);
                        formulaNode.setEnd(formulaNode.getEnd()+1);
                        formulaNode.setResultList(sum_list);
                    } else {
                        if(max_length<list_temp.size())
                            max_length = list_temp.size();
                        formulaNode.setResultList(list_temp);
                    }
                }else
                    continue;
            }
            if(num_flag==true&&str_flag==true){
                return null;
            }else if(num_flag==true){
                resultArray = new String[max_length][1];
                for (int i = 0; i < resultArray.length; i++) {
                    String formula_temp = formula.toString().trim();
                    for (int j = 0; j < formulaNodeList.size(); j++) {
                        FormulaNode formulaNode = formulaNodeList.get(j);
                        if (formulaNode.getResultList() != null && formulaNode.getResultList().size() > i) {
                            if (!formulaNode.getResultList().get(i).toString().trim().equals("")) {
                                formula_temp = formula_temp.replace(formulaNode.getNodeStr(), formulaNode.getResultList().get(i).toString().trim());
                            }else{
                                formula_temp = formula_temp.replace(formulaNode.getNodeStr(), "0");
                            }
                        }else{
                            formula_temp = formula_temp.replace(formulaNode.getNodeStr(), "0");
                        }
                    }
                    System.out.println(formula_temp);
                    resultArray[i][0] = decimalFormat.format(Calculator.conversion(formula_temp.substring(1)+"+0"));
                }
            }else if(str_flag==true){
                List<Object> list_temp = formulaNodeList.get(0).getResultList();
                resultArray = new String[list_temp.size()][1];
                for (int i = 0; i < list_temp.size(); i++) {
                    resultArray[i][0]=list_temp.get(i).toString();
                }
            }
        }
        return resultArray;
    }

    @Override
    @Transactional
    public String getFormulaDate(String formula,int yearNow,int monthNow){
        String result = null;
        Calendar cal=Calendar.getInstance();
        if(formula.contains("DATE(YYYY-MM-DD)")){
            result = yearNow + "年" + monthNow + "月" + cal.get(Calendar.DATE) + "日";
        }else if(formula.contains("DATE(YYYY-MM)")){
            result = yearNow + "年" + monthNow + "月";
        }else if(formula.contains("DATE(MM-DD)")){
            result = monthNow + "月" + cal.get(Calendar.DATE) + "日";
        }else if(formula.contains("DATE(YYYY)")){
            result = yearNow + "年";
        }else if(formula.contains("DATE(MM)")){
            result = monthNow + "月";
        }else if(formula.contains("DATE(DD)")){
            result = cal.get(Calendar.DATE) + "日";
        }else{
            result = "";
        }
        return result;
    }

    //获取所有核算字典记录
    public List<Lshszd> findAllHszd(){
        return reportBaseDao.getAllHszd();
    }

    //获取所有往来单位记录
    public List<Lswldw> findAllWldw(){
        return reportBaseDao.getAllWldw();
    }

}
