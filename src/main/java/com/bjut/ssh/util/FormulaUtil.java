package com.bjut.ssh.util;

import com.bjut.ssh.entity.FormulaNode;

import java.util.List;


public class FormulaUtil {

    /**
     * 报表金额函数转sql
     * @param nodeStr 公式原子
     * @param yearNow 报表当前年
     * @param monthNow 报表当前月
     * @param rptNo 当前报表编号
     * @return
     */
    public static String BBtoSql(String nodeStr, int yearNow,int monthNow,String rptNo){
        String sql = null;
        String table1 = "Lcdyzd";
        String temp;
        String cellRange;
        String dateNow = yearNow + "-" + monthNow;
        if(monthNow<10)
            dateNow = yearNow + "-0" + monthNow;
        String rptNo_temp=null;
        String date_temp;
        int year;
        int month;
        String pattern1 = "^BB\\([A-Z0-9:]+\\)$";//BB(单元格范围)
        String pattern2 = "^BB\\([0-9]+,[0-9]+,[A-Z0-9:]+\\)$";//BB(年，月，单元格范围)
        String pattern3 = "^BB\\(\\w+,[A-Z0-9:]+\\)$";//BB(报表编号，单元格范围)
        String pattern4 = "^BB\\([0-9]+,[0-9]+,\\w+,[A-Z0-9:]+\\)$";//BB(年，月，报表编号，单元格范围)
        if(nodeStr.matches(pattern1)){//BB(单元格范围)
            cellRange = nodeStr.substring(nodeStr.indexOf('(') + 1, nodeStr.indexOf(')'));
            sql = "select rowInfoNo,colInfoNo,cellContent from " + table1 + " where rptNo='" + rptNo + "' and rptDate='" + dateNow + "'";
            sql = sql + " and " + rangeToSql(getRange(cellRange));
        }else if(nodeStr.matches(pattern2)){//BB(年，月，单元格范围)
            year = Integer.parseInt(nodeStr.substring(nodeStr.indexOf('(') + 1, nodeStr.indexOf(',')));
            temp = nodeStr.substring(nodeStr.indexOf(',') + 1, nodeStr.length());
            month = Integer.parseInt(temp.substring(0,temp.indexOf(',')));
            year = getYear(yearNow,year);
            month = getMonth(monthNow,month);
            if(year!=yearNow)
                table1="Lcdyzd_"+year;
            date_temp=year+"-"+month;
            if(month<10)
                date_temp=year+"-0"+month;
            cellRange = nodeStr.substring(nodeStr.lastIndexOf(',') + 1, nodeStr.indexOf(')'));
            sql = "select rowInfoNo,colInfoNo,cellContent from " + table1 + " where rptNo='" + rptNo + "' and rptDate='" + date_temp + "'";
            sql = sql + " and " + rangeToSql(getRange(cellRange));
        }else if(nodeStr.matches(pattern3)){//BB(报表编号，单元格范围)
            rptNo_temp = nodeStr.substring(nodeStr.indexOf('(') + 1, nodeStr.indexOf(','));
            cellRange = nodeStr.substring(nodeStr.lastIndexOf(',') + 1, nodeStr.indexOf(')'));
            sql = "select rowInfoNo,colInfoNo,cellContent from " + table1 + " where rptNo='" + rptNo_temp + "' and rptDate='" + dateNow + "'";
            sql = sql + " and " + rangeToSql(getRange(cellRange));
        }else if(nodeStr.matches(pattern4)){//BB(年，月，报表编号，单元格范围)
            year = Integer.parseInt(nodeStr.substring(nodeStr.indexOf('(') + 1, nodeStr.indexOf(',')));
            temp = nodeStr.substring(nodeStr.indexOf(',') + 1, nodeStr.lastIndexOf(','));
            month = Integer.parseInt(temp.substring(0,temp.indexOf(',')));
            year = getYear(yearNow,year);
            month = getMonth(monthNow,month);
            if(year!=yearNow)
                table1="Lcdyzd_"+year;
            date_temp=year+"-"+month;
            if(month<10)
                date_temp=year+"-0"+month;
            rptNo_temp = temp.substring(temp.indexOf(',') + 1, temp.length());
            cellRange = nodeStr.substring(nodeStr.lastIndexOf(',') + 1, nodeStr.indexOf(')'));
            sql = "select rowInfoNo,colInfoNo,cellContent from " + table1 + " where rptNo='" + rptNo_temp + "' and rptDate='" + date_temp + "'";
            sql = sql + " and " + rangeToSql(getRange(cellRange));
        }else
            return null;
        return sql;
    }

    /**
     * 科目金额函数转sql
     * @param nodeStr 公式原子
     * @param yearNow 报表当前年
     * @param monthNow 报表当前月
     * @param subStruc 科目结构
     * @return sql字符串
     */
    public static String KMJEtoSql(String nodeStr,int yearNow,int monthNow,String subStruc){
        String sql = null;
        String table1 = "Lskmzd";//表名
        String numObj = "";//取数对象
        String kmNo = "";//科目编号
        String numFac = "";//取数条件
        String temp;
        int year;
        int month;
        String pattern1 = "^KMJE\\([0-9:]+,[a-zA-Z]+\\)$";//KMJE(科目编号，取数对象)
        String pattern2 = "^KMJE\\([0-9]+,[0-9]+,[0-9:]+,[a-zA-Z]+\\)$";//KMJE(年，月，科目编号，取数对象)
        String pattern3 = "^KMJE\\([0-9:]+,[a-zA-Z]+,[^,]+\\)$";//KMJE(科目编号，取数对象，取数条件)
        String pattern4 = "^KMJE\\([0-9]+,[0-9]+,[0-9:]+,[a-zA-Z]+,[^,]+\\)$";//KMJE(年，月，科目编号，取数对象，取数条件)
        if(nodeStr.matches(pattern1)){
            numObj = nodeStr.substring(nodeStr.indexOf(',') + 1, nodeStr.lastIndexOf(')'));
            kmNo = nodeStr.substring(nodeStr.indexOf('(') + 1, nodeStr.indexOf(","));
            kmNo = getKmNoStr(kmNo,"t1",subStruc);
            sql = getSqlByNumObj(monthNow,numObj,table1,null,null,null,kmNo,null,null);
        }else if(nodeStr.matches(pattern2)){
            year = Integer.parseInt(nodeStr.substring(nodeStr.indexOf('(') + 1, nodeStr.indexOf(',')));
            temp = nodeStr.substring(nodeStr.indexOf(',') + 1, nodeStr.length());
            month = Integer.parseInt(temp.substring(0,temp.indexOf(',')));
            year = getYear(yearNow,year);
            month = getMonth(monthNow,month);
            if(year!=yearNow)
                table1="Lskmzd_"+year;
            numObj = nodeStr.substring(nodeStr.lastIndexOf(',') + 1, nodeStr.lastIndexOf(')'));
            temp = nodeStr.substring(0, nodeStr.lastIndexOf(","));
            kmNo = temp.substring(temp.lastIndexOf(",")+1, temp.length());
            kmNo = getKmNoStr(kmNo,"t1",subStruc);
            sql = getSqlByNumObj(month,numObj,table1,null,null,null,kmNo,null,null);
        }else if(nodeStr.matches(pattern3)){
            numObj = nodeStr.substring(nodeStr.indexOf(',') + 1, nodeStr.lastIndexOf(','));
            kmNo = nodeStr.substring(nodeStr.indexOf('(') + 1, nodeStr.indexOf(","));
            kmNo = getKmNoStr(kmNo,"t1",subStruc);
            numFac = nodeStr.substring(nodeStr.lastIndexOf(',') + 1, nodeStr.lastIndexOf(")"));
            sql = getSqlByNumObj(monthNow,numObj,table1,null,null,null,kmNo,null,null);
            sql = sql + " and " + getNumFacStr(numFac, monthNow, "t1");
        }else if(nodeStr.matches(pattern4)){
            year = Integer.parseInt(nodeStr.substring(nodeStr.indexOf('(') + 1, nodeStr.indexOf(',')));
            temp = nodeStr.substring(nodeStr.indexOf(',') + 1, nodeStr.length());
            month = Integer.parseInt(temp.substring(0,temp.indexOf(',')));
            year = getYear(yearNow,year);
            month = getMonth(monthNow,month);
            if(year!=yearNow)
                table1="Lskmzd_"+year;
            temp = nodeStr.substring(0, nodeStr.lastIndexOf(','));
            numObj = temp.substring(temp.lastIndexOf(',')+1, temp.length());
            temp = nodeStr.substring(nodeStr.indexOf(',') + 1, nodeStr.lastIndexOf(","));
            kmNo = temp.substring(temp.indexOf(',') + 1, temp.lastIndexOf(","));
            kmNo = getKmNoStr(kmNo,"t1",subStruc);
            numFac = nodeStr.substring(nodeStr.lastIndexOf(',') + 1, nodeStr.lastIndexOf(")"));
            sql = getSqlByNumObj(month,numObj,table1,null,null,null,kmNo,null,null);
            sql = sql + " and " + getNumFacStr(numFac, month, "t1");
        }else 
            return null;
        return sql;
    }

    /**
     * 往来金额函数转sql
     * @param nodeStr 公式原子
     * @param yearNow 报表当前年
     * @param monthNow 报表当前月
     * @param subStruc 科目结构
     * @return
     */
    public static String WLJEtoSql(String nodeStr,int yearNow,int monthNow,String subStruc){
        String sql = null;
        String table1 = "Lswlje";//表名1、t1
        String table2 = "Lswlfl";//表名2、t2
        String table3 = "Lswldw";//表名3、t3
        String table4 = "Lskmzd";//表名4、t4
        String tableRelation = " and t1.companyNo=t3.companyNo and t2.catNo1=t3.catNo1 and t1.itemNo=t4.itemNo";
        String numObj = "";//取数对象
        String kmNo = "";//科目编号
        String wlNo = "";//往来编号
        String numFac = "";//取数条件
        String temp;
        int year;
        int month;
        String pattern1 = "^WLJE\\([0-9:]+,[0-9]+,[a-zA-Z]+\\)$";//WLJE(科目编号，往来单位编号，取数对象)
        String pattern2 = "^WLJE\\([0-9]+,[0-9]+,[0-9:]+,[0-9]+,[a-zA-Z]+\\)$";//WLJE(年，月，科目编号，往来单位编号，取数对象)
        String pattern3 = "^WLJE\\([0-9:]+,[0-9]+,[a-zA-Z]+,[^,]+\\)$";//WLJE(科目编号，往来单位编号，取数对象，取数条件)
        String pattern4 = "^WLJE\\([0-9]+,[0-9]+,[0-9:]+,[0-9]+,[a-zA-Z]+,[^,]+\\)$";//WLJE(年，月，科目编号，往来单位编号，取数对象，取数条件)
        if(nodeStr.matches(pattern1)){//WLJE(科目编号，往来单位编号，取数对象)
            numObj = nodeStr.substring(nodeStr.lastIndexOf(',') + 1, nodeStr.lastIndexOf(')'));
            kmNo = nodeStr.substring(nodeStr.indexOf('(') + 1, nodeStr.indexOf(","));
            kmNo = getKmNoStr(kmNo,"t1",subStruc);
            wlNo = nodeStr.substring(nodeStr.indexOf(',') + 1, nodeStr.lastIndexOf(','));
            sql = getSqlByNumObj(monthNow, numObj, table1, table2, table3, table4, kmNo, null, wlNo);
            sql = sql + tableRelation;
        }else if(nodeStr.matches(pattern2)){//WLJE(年，月，科目编号，往来单位编号，取数对象)
            year = Integer.parseInt(nodeStr.substring(nodeStr.indexOf('(') + 1, nodeStr.indexOf(',')));
            temp = nodeStr.substring(nodeStr.indexOf(',') + 1, nodeStr.length());
            month = Integer.parseInt(temp.substring(0,temp.indexOf(',')));
            year = getYear(yearNow,year);
            month = getMonth(monthNow,month);
            if(year!=yearNow){
                table1="Lswlje_"+year;
                table2="Lswlfl_"+year;
                table3="Lswldw_"+year;
                table4="Lskmzd_"+year;
            }
            numObj = nodeStr.substring(nodeStr.lastIndexOf(',') + 1, nodeStr.lastIndexOf(')'));
            temp = nodeStr.substring(nodeStr.indexOf(',')+1, nodeStr.lastIndexOf(","));
            kmNo = temp.substring(temp.indexOf(",")+1, temp.lastIndexOf(","));
            kmNo = getKmNoStr(kmNo,"t1",subStruc);
            wlNo = temp.substring(temp.lastIndexOf(',') + 1, temp.length());
            sql = getSqlByNumObj(month, numObj, table1, table2, table3, table4, kmNo, null, wlNo);
            sql = sql + tableRelation;
        }else if(nodeStr.matches(pattern3)){//WLJE(科目编号，往来单位编号，取数对象，取数条件)
            temp = nodeStr.substring(nodeStr.indexOf(',') + 1, nodeStr.lastIndexOf(','));
            numObj = temp.substring(temp.indexOf(',') + 1, temp.length());
            kmNo = nodeStr.substring(nodeStr.indexOf('(') + 1, nodeStr.indexOf(","));
            kmNo = getKmNoStr(kmNo,"t1",subStruc);
            wlNo = temp.substring(0, temp.indexOf(','));
            numFac = nodeStr.substring(nodeStr.lastIndexOf(',') + 1, nodeStr.lastIndexOf(")"));
            sql = getSqlByNumObj(monthNow, numObj, table1, table2, table3, table4, kmNo, null, wlNo);
            sql = sql + " and " + getNumFacStr(numFac, monthNow, "t1");
            sql = sql + tableRelation;
        }else if(nodeStr.matches(pattern4)){//WLJE(年，月，科目编号，往来单位编号，取数对象，取数条件)
            year = Integer.parseInt(nodeStr.substring(nodeStr.indexOf('(') + 1, nodeStr.indexOf(',')));
            temp = nodeStr.substring(nodeStr.indexOf(',') + 1, nodeStr.lastIndexOf(','));
            month = Integer.parseInt(temp.substring(0,temp.indexOf(',')));
            year = getYear(yearNow,year);
            month = getMonth(monthNow,month);
            if(year!=yearNow){
                table1="Lswlje_"+year;
                table2="Lswlfl_"+year;
                table3="Lswldw_"+year;
                table4="Lskmzd_"+year;
            }
            numObj = temp.substring(temp.lastIndexOf(',')+1, temp.length());
            temp = temp.substring(temp.indexOf(',') + 1, temp.lastIndexOf(","));
            kmNo = temp.substring(0, temp.indexOf(","));
            kmNo = getKmNoStr(kmNo,"t1",subStruc);
            wlNo = temp.substring(temp.indexOf(','), temp.length());
            numFac = nodeStr.substring(nodeStr.lastIndexOf(',') + 1, nodeStr.lastIndexOf(")"));
            sql = getSqlByNumObj(month, numObj, table1, table2, table3, table4, kmNo, null, wlNo);
            sql = sql + " and " + getNumFacStr(numFac, month, "t1");
            sql = sql + tableRelation;
        }else
            return null;
        return sql;
    }

    /**
     * 项目金额函数转sql
     * @param nodeStr 公式原子
     * @param yearNow 报表当前年
     * @param monthNow 报表当前月
     * @param subStruc 科目结构
     * @return
     */
    public static String XMJEtoSql(String nodeStr,int yearNow,int monthNow,String subStruc){
        String sql = null;
        String table1 = "Lshsje";//表名1、t1
        String table2 = "Lshszd";//表名2、t2
        String table3 = "Lskmzd";//表名3、t3
        String tableRelation = " and t1.spNo1=t2.spNo and t1.itemNo=t3.itemNo";
        String numObj = "";//取数对象
        String kmNo = "";//科目编号
        String hsNo = "";//核算专项项目编号
        String numFac = "";//取数条件
        String temp;
        int year;
        int month;
        String pattern1 = "^XMJE\\([0-9:]+,[0-9]+,[a-zA-Z]+\\)$";//XMJE(科目编号，专项项目编号，取数对象)
        String pattern2 = "^XMJE\\([0-9]+,[0-9]+,[0-9:]+,[0-9]+,[a-zA-Z]+\\)$";//XMJE(年，月，科目编号，专项项目编号，取数对象)
        String pattern3 = "^XMJE\\([0-9:]+,[0-9]+,[a-zA-Z]+,[^,]+\\)$";//XMJE(科目编号，专项项目编号，取数对象，取数条件)
        String pattern4 = "^XMJE\\([0-9]+,[0-9]+,[0-9:]+,[0-9]+,[a-zA-Z]+,[^,]+\\)$";//XMJE(年，月，科目编号，专项项目编号，取数对象，取数条件)
        if(nodeStr.matches(pattern1)){//XMJE(科目编号，专项项目编号，取数对象)
            numObj = nodeStr.substring(nodeStr.lastIndexOf(',') + 1, nodeStr.lastIndexOf(')'));
            kmNo = nodeStr.substring(nodeStr.indexOf('(') + 1, nodeStr.indexOf(","));
            kmNo = getKmNoStr(kmNo,"t1",subStruc);
            hsNo = nodeStr.substring(nodeStr.indexOf(',') + 1, nodeStr.lastIndexOf(','));
            sql = getSqlByNumObj(monthNow, numObj, table1, table2, table3, null, kmNo, hsNo, null);
            sql = sql + tableRelation;
        }else if(nodeStr.matches(pattern2)){//XMJE(年，月，科目编号，专项项目编号，取数对象)
            year = Integer.parseInt(nodeStr.substring(nodeStr.indexOf('(') + 1, nodeStr.indexOf(',')));
            temp = nodeStr.substring(nodeStr.indexOf(',') + 1, nodeStr.length());
            month = Integer.parseInt(temp.substring(0,temp.indexOf(',')));
            year = getYear(yearNow,year);
            month = getMonth(monthNow,month);
            if(year!=yearNow){
                table1="Lshsje_"+year;
                table2="Lshszd_"+year;
                table3="Lskmzd_"+year;
            }
            numObj = nodeStr.substring(nodeStr.lastIndexOf(',') + 1, nodeStr.lastIndexOf(')'));
            temp = nodeStr.substring(nodeStr.indexOf(',')+1, nodeStr.lastIndexOf(","));
            kmNo = temp.substring(temp.indexOf(",")+1, temp.lastIndexOf(","));
            kmNo = getKmNoStr(kmNo,"t1",subStruc);
            hsNo = temp.substring(temp.lastIndexOf(',') + 1, temp.length());
            sql = getSqlByNumObj(month, numObj, table1, table2, table3, null, kmNo, hsNo, null);
            sql = sql + tableRelation;
        }else if(nodeStr.matches(pattern3)){//XMJE(科目编号，专项项目编号，取数对象，取数条件)
            temp = nodeStr.substring(nodeStr.indexOf(',') + 1, nodeStr.lastIndexOf(','));
            numObj = temp.substring(temp.indexOf(',') + 1, temp.length());
            kmNo = nodeStr.substring(nodeStr.indexOf('(') + 1, nodeStr.indexOf(","));
            kmNo = getKmNoStr(kmNo,"t1",subStruc);
            hsNo = temp.substring(0, temp.indexOf(','));
            numFac = nodeStr.substring(nodeStr.lastIndexOf(',') + 1, nodeStr.lastIndexOf(")"));
            sql = getSqlByNumObj(monthNow, numObj, table1, table2, table3, null, kmNo, hsNo, null);
            sql = sql + " and " + getNumFacStr(numFac, monthNow, "t1");
            sql = sql + tableRelation;
        }else if(nodeStr.matches(pattern4)){//XMJE(年，月，科目编号，专项项目编号，取数对象，取数条件)
            year = Integer.parseInt(nodeStr.substring(nodeStr.indexOf('(') + 1, nodeStr.indexOf(',')));
            temp = nodeStr.substring(nodeStr.indexOf(',') + 1, nodeStr.lastIndexOf(','));
            month = Integer.parseInt(temp.substring(0,temp.indexOf(',')));
            year = getYear(yearNow,year);
            month = getMonth(monthNow,month);
            if(year!=yearNow){
                table1="Lshsje_"+year;
                table2="Lshszd_"+year;
                table3="Lskmzd_"+year;
            }
            numObj = temp.substring(temp.lastIndexOf(',')+1, temp.length());
            temp = temp.substring(temp.indexOf(',') + 1, temp.lastIndexOf(","));
            kmNo = temp.substring(0, temp.indexOf(","));
            kmNo = getKmNoStr(kmNo,"t1",subStruc);
            hsNo = temp.substring(temp.indexOf(','), temp.length());
            numFac = nodeStr.substring(nodeStr.lastIndexOf(',') + 1, nodeStr.lastIndexOf(")"));
            sql = getSqlByNumObj(month, numObj, table1, table2, table3, null, kmNo, hsNo, null);
            sql = sql + " and " + getNumFacStr(numFac, month, "t1");
            sql = sql + tableRelation;
        }else
            return null;
        return sql;
    }

    /**
     * 通过字符串获取公式原子
     * @param str 整体公式字符串
     * @param formulaNodeList 公式原子链表
     */
    public static void addFormularNode(String str, List<FormulaNode> formulaNodeList){
        int[] mark = new int[str.length()];
        makeMark(str,"BB",mark);
        makeMark(str,"KMJE",mark);
        makeMark(str,"XMJE",mark);
        makeMark(str,"WLJE",mark);
        for (int i = 0; i < mark.length; i++) {
            if(mark[i]==1){
                int start = i;
                while(i<mark.length&&mark[i]==1)
                    i++;
                int end = --i;
                FormulaNode fn = new FormulaNode(start,end,str.substring(start,end+1));
                formulaNodeList.add(fn);
            }
        }
    }

    /**
     * 标记字符串公式原子
     * @param str 整体公式字符串
     * @param item 公式名
     * @param mark 标记数组
     */
    public static void makeMark(String str,String item,int[] mark){
        if(str.contains(item)){
            int pos = str.indexOf(item);
            while(pos!=-1){
                int temp = pos + item.length();
                int end = mark.length-1;
                int top=0;
                for (int i = temp; i < mark.length; i++) {
                    if(str.charAt(i)=='(')
                        top++;
                    if(str.charAt(i)==')')
                        top--;
                    if(top==0){
                        end=i;
                        break;
                    }
                }
                for (int i = pos; i <= end; i++) {
                    mark[i] = 1;
                }
                pos=str.indexOf(item,pos+1);
            }

        }
    }

    /**
     * 获取hql语句
     * @param monthNow 当前月
     * @param numObj 取数对象
     * @param tableName1 表名1
     * @param tableName2 表名2
     * @param tableName3 表名3
     * @param tableName4 表名4
     * @param kmNo 科目编号
     * @param hsNo 核算编号
     * @param wlNo 往来编号
     * @return
     */
    public static String getSqlByNumObj(int monthNow,String numObj,
                                        String tableName1,String tableName2,
                                        String tableName3,String tableName4,
                                        String kmNo,String hsNo,String wlNo){
        String str = null;
        String temp = null;
        String tableName = tableName1.toString()+" t1";
        if(tableName2!=null&&!tableName2.equals(""))
            tableName =tableName +","+tableName2+" t2";
        if(tableName3!=null&&!tableName3.equals(""))
            tableName =tableName+","+ tableName3+" t3";
        if(tableName4!=null&&!tableName4.equals(""))
            tableName =tableName+","+ tableName4+" t4";
        int preMonth = monthNow - 1;
        switch (numObj){
            case "NCJF":
                str = "select t1.supMoney from "+tableName+" where "+kmNo;
                break;
            case "NCDF":
                str = "select -t1.supMoney from "+tableName+" where "+kmNo;
                break;
            case "JFLJ":
                str = "select t1.debitMoney01";
                for (int i = 2; i <= monthNow; i++) {
                    if(i<10)
                        str = str + "+t1.debitMoney0" + i;
                    else
                        str = str + "+t1.debitMoney" + i;
                }
                str = str + " from "+tableName+" where "+kmNo;
                break;
            case "DFLJ":
                str = "select t1.creditMoney01";
                for (int i = 2; i <= monthNow; i++) {
                    if(i<10)
                        str = str + "+t1.creditMoney0" + i;
                    else
                        str = str + "+t1.creditMoney" + i;
                }
                str = str + " from "+tableName+" where "+kmNo;
                break;
            case "YCJF":
                str = "select ";
                if(preMonth>0){
                    if(preMonth<10)
                        str = str + "t1.balance0"+preMonth;
                    else
                        str = str + "t1.balance"+preMonth;
                }else{
                    return null;
                }
                str = str + " from "+tableName+" where "+kmNo;
                break;
            case "YCDF":
                str = "select ";
                if(preMonth>0){
                    if(preMonth<10)
                        str = str + "-t1.balance0"+preMonth;
                    else
                        str = str + "-t1.balance"+preMonth;
                }else{
                    return null;
                }
                str = str + " from "+tableName+" where "+kmNo;
                break;
            case "JFFS":
                str = "select ";
                if(monthNow>0){
                    if(monthNow<10)
                        str = str + "t1.debitMoney0"+monthNow;
                    else
                        str = str + "t1.debitMoney"+monthNow;
                }
                str = str + " from "+tableName+" where "+kmNo;
                break;
            case "DFFS":
                str = "select ";
                if(monthNow>0){
                    if(monthNow<10)
                        str = str + "t1.creditMoney0"+monthNow;
                    else
                        str = str + "t1.creditMoney"+monthNow;
                }
                str = str + " from "+tableName+" where "+kmNo;
                break;
            case "JFYE":
                str = "select ";
                if(monthNow>0){
                    if(monthNow<10)
                        str = str + "t1.balance0"+monthNow;
                    else
                        str = str + "t1.balance"+monthNow;
                }
                str = str + " from "+tableName+" where "+kmNo;
                break;
            case "DFYE":
                str = "select ";
                if(monthNow>0){
                    if(monthNow<10)
                        str = str + "-t1.balance0"+monthNow;
                    else
                        str = str + "-t1.balance"+monthNow;
                }
                str = str + " from "+tableName+" where "+kmNo;
                break;
            case "KMMC":
                str = "select itemName from Lskmzd t1 where "+kmNo;
                return str;
            case "XMMC":
                str = "select spName from Lshsje t1,Lshszd t2,Lskmzd t3 where t1.spNo1='"+hsNo+"'";
                return str;
            case "WLMC":
                str = "select companyName from Lswlje t1,Lswlfl t2,Lswldw t3,Lskmzd t4 where t1.CompanyNo='" +wlNo+"'";
                return str;
            case "DWMC":
                str = "select confValue form Lsconf where confKey='unit_name'";//!!!Danger : Lsconf表查询单位名称
                break;
            case "BNJD":
                str = "select t1.debitMoney01";
                for (int i = 2; i <= monthNow; i++) {
                    if(i<10)
                        str = str + "+t1.debitMoney0" + i;
                    else
                        str = str + "+t1.debitMoney" + i;
                }
                str = str + "-t1.creditMoney01";
                for (int i = 2; i <= monthNow; i++) {
                    if(i<10)
                        str = str + "-t1.creditMoney0" + i;
                    else
                        str = str + "-t1.creditMoney" + i;
                }
                str = str + " from "+tableName+" where "+kmNo;
                break;
            case "BNDJ":
                str = "select t1.creditMoney01";
                for (int i = 2; i <= monthNow; i++) {
                    if(i<10)
                        str = str + "+t1.creditMoney0" + i;
                    else
                        str = str + "+t1.creditMoney" + i;
                }
                str = str + "-t1.debitMoney01";
                for (int i = 2; i <= monthNow; i++) {
                    if(i<10)
                        str = str + "-t1.debitMoney0" + i;
                    else
                        str = str + "-t1.debitMoney" + i;
                }
                str = str + " from "+tableName+" where "+kmNo;
                break;
            case "BYJD":
                str = "select ";
                if(monthNow>0){
                    if(monthNow<10)
                        str = str + "t1.debitMoney0" + monthNow + "-t1.creditMoney0" + monthNow;
                    else
                        str = str + "t1.debitMoney" + monthNow + "-t1.creditMoney" + monthNow;
                }
                str = str + " from "+tableName+" where "+kmNo;
                break;
            case "BYDJ":
                str = "select ";
                if(monthNow>0){
                    if(monthNow<10)
                        str = str + "t1.creditMoney0" + monthNow + "-t1.debitMoney0" + monthNow;
                    else
                        str = str + "t1.creditMoney" + monthNow + "-t1.debitMoney" + monthNow;
                }
                str = str + " from "+tableName+" where "+kmNo;
                break;
            case "NJFX":
                str = "select t1.supMoney from "+tableName+" where "+kmNo+" and supMoney>0";
                break;
            case "NDFX":
                str = "select -t1.supMoney from "+tableName+" where "+kmNo+" and supMoney<0";
                break;
            case "YJFX":
                if(preMonth<10)
                    temp = "t1.balance0"+preMonth;
                else
                    temp = "t1.balance"+preMonth;
                if(preMonth>0){
                    str ="select " + temp + " from " +tableName+" where "+kmNo+" and "+ temp + ">0";
                }else
                    return null;
                break;
            case "YDFX":
                if(preMonth<10)
                    temp = "-t1.balance0"+preMonth;
                else
                    temp = "-t1.balance"+preMonth;
                if(preMonth>0){
                    str ="select " + temp + " from " +tableName+" where "+kmNo+" and "+ temp.substring(1) + "<0";
                }else
                    return null;
                break;
        }
        if(hsNo!=null)
            str = str + " and t1.spNo1='" + hsNo + "'";
        if(wlNo!=null)
            str = str + " and t1.companyNo='" + wlNo + "'";
        return str;
    }

    /**
     * 获取取数条件sql片段
     * @param numFac 取数条件字符串
     * @param monthNow 当前月
     * @param tableName 表_别名
     * @return
     */
    public static String getNumFacStr(String numFac,int monthNow,String tableName){
        int preMonth = monthNow - 1;
        String str = numFac;
        String[][] data=new String[17][2];
        data[0][0] = "KMBH";//科目编号
        data[0][1] = tableName + ".itemNo";
        data[1][0] = "KMJS";//科目级数--------------------------------
        data[1][1] = "item";
        data[2][0] = "KMMX";//科目明细
        data[2][1] = "finLevel=1";
        data[3][0] = "XMBH";//项目编号
        data[3][1] = tableName + ".spNo";
        data[4][0] = "XMJS";//项目级数--------------------------------
        data[4][1] = "spLevel";
        data[5][0] = "WLLB";//往来类别--------------------------------
        data[5][1] = "catName1";
        data[6][0] = "WLBH";//往来编号
        data[6][1] = tableName + ".companyNo";
        data[7][0] = "JFYE";//借方余额
        if(monthNow>0){
            if(monthNow<10)
                data[7][1] = tableName + ".balance0" + monthNow;
            else
                data[7][1] = tableName + ".balance"+monthNow;
        }
        data[8][0] = "DFYE";//贷方余额
        if(monthNow>0){
            if(monthNow<10)
                data[8][1] = "-"+tableName+".balance0"+monthNow;
            else
                data[8][1] = "-"+tableName+".balance"+monthNow;
        }
        data[9][0] = "JFFS";//借方发生
        if(monthNow>0){
            if(monthNow<10)
                data[9][1] = tableName + ".debitMoney0"+monthNow;
            else
                data[9][1] = tableName + ".debitMoney"+monthNow;
        }
        data[10][0] = "DFFS";//贷方发生
        if(monthNow>0){
            if(monthNow<10)
                data[10][1] = tableName + ".creditMoney0"+monthNow;
            else
                data[10][1] = tableName + ".creditMoney"+monthNow;
        }
        data[11][0] = "NCJF";//年初借方
        data[11][1] = tableName + ".supMoney";
        data[12][0] = "NCDF";//年初贷方
        data[12][1] = "-"+tableName+".supMoney";
        data[13][0] = "JFLJ";//借方累计
        data[13][1] = "(" + tableName + ".debitMoney01";
        for (int i = 2; i <= monthNow; i++) {
            if(i<10)
                data[13][1] = data[13][1] + "+"+tableName+".debitMoney0" + i;
            else
                data[13][1] = data[13][1] + "+"+tableName+".debitMoney" + i;
        }
        data[13][1] = data[13][1] + ")";
        data[14][0] = "DFLJ";//贷方累计
        data[14][1] = "(" + tableName+".creditMoney01";
        for (int i = 2; i <= monthNow; i++) {
            if(i<10)
                data[14][1] = data[14][1] + "+"+tableName+".creditMoney0" + i;
            else
                data[14][1] = data[14][1] + "+"+tableName+".creditMoney" + i;
        }
        data[14][1] = data[14][1] + ")";
        data[15][0] = "YCJF";//月初借方
        if(preMonth>0){
            if(preMonth<10)
                data[15][1] = tableName + ".balance0"+preMonth;
            else
                data[15][1] = tableName + ".balance"+preMonth;
        }else{
            data[15][1] = tableName + ".balance01";
        }
        data[16][0] = "YCDF";//月初贷方
        if(preMonth>0){
            if(preMonth<10)
                data[16][1] = "-"+tableName+".balance0"+preMonth;
            else
                data[16][1] = "-"+tableName+".balance"+preMonth;
        }else{
            data[16][1] = "-"+tableName+".balance01";
        }
        for (int i = 0; i < data.length; i++) {
            if (str.contains(data[i][0].toString())) {
                str = str.replaceAll(data[i][0].toString(), data[i][1].toString());
            }
        }
        return str;
    }

    /**
     * 获取科目编号sql片段
     * @param kmNo 科目编号
     * @param tableName 表_别名
     * @param subStruc 科目结构
     * @return
     */
    public static String getKmNoStr(String kmNo,String tableName,String subStruc){
        String str=null;
        char[] subStrucArry = subStruc.toCharArray();
        int[] subStcArry = new int[subStrucArry.length];
        int sum = 0;
        int level = 0;
        int levelNum = 0;
        for (int i = 0; i < subStcArry.length; i++) {
            subStcArry[i] = Integer.parseInt(subStrucArry[i]+"");
            sum += subStcArry[i];
        }
        if(kmNo.indexOf(':')!=-1){
            String[] kmNos = kmNo.split("[:]");
            //确认科目级数
            for (int i = 0; i < subStcArry.length; i++) {
                levelNum += subStcArry[i];
                if(levelNum==kmNos[0].length()){
                    level = i+1;
                    break;
                }
            }
            //补零
            for (int i = 0; i < kmNos.length; i++) {
                for (int j = kmNos[i].length(); j < sum; j++) {
                    kmNos[i]+="0";
                }
            }
            if (kmNo.indexOf(':') == 0) {
                if(level==1)
                    str = tableName+".itemNo like '%"+kmNos[0].substring(levelNum,sum)+"' and ";
                else
                    str = tableName+".itemNo like '"+kmNos[0].substring(0,levelNum-subStcArry[level-1])+"%' and ";
                str = str + tableName + ".itemNo <= '" + kmNos[0] + "'";
            } else if (kmNo.indexOf(':') == (kmNo.length() - 1)) {
                if(level==1)
                    str = tableName+".itemNo like '%"+kmNos[0].substring(levelNum,sum)+"' and ";
                else
                    str = tableName+".itemNo like '"+kmNos[0].substring(0,levelNum-subStcArry[level-1])+"%' and ";
                str = str + tableName + ".itemNo >= '" + kmNos[0] + "'";
            } else {
                str = tableName + ".itemNo >= '" + kmNos[0] + "' and "+ tableName +".itemNo <= '"+kmNos[1]+"'";
            }
        }else{
            //补零
            for (int i = kmNo.length(); i < sum; i++) {
                kmNo+="0";
            }
            str = tableName+".itemNo='"+kmNo+"'";
        }
        return str;
    }

    /**
     * 获取年份
     * @param yearNow 当前年份
     * @param year 输入框年份
     * @return
     */
    public static int getYear(int yearNow,int year){
        if(year>-100&&year<100)//相对年份
            return (yearNow+year);
        else//绝对年份
            return year;
    }

    /**
     * 获取月份
     * @param monthNow 当前月
     * @param month 输入框月份 -12<month<12
     * @return
     */
    public static int getMonth(int monthNow,int month){
        if(month<1)
            return ((monthNow + month + 12) % 12);
        else
            return monthNow;
    }

    /**
     * 获取单元格范围数组
     * @param range 单元格范围字符串
     * @return 长度为4的数组，fromX,fromY,toX,toY（包括fromX,fromY,toX,toY。从 0 开始）
     */
    public static int[] getRange(String range) {
        int[] cellRange = new int[4];
        int fromX,fromY,toX,toY;
        if(range.matches("^[A-Z]\\d{1,3}$")){
            fromX = Integer.parseInt(range.substring(1))-1;//行
            fromY = range.charAt(0)-'A';//列
            toX = fromX;
            toY = fromY;
        }else if(range.matches("^[A-Z]\\d{1,3}:[A-Z]\\d{1,3}$")){
            fromX = Integer.parseInt(range.substring(1,range.indexOf(':')))-1;
            fromY = range.charAt(0)-'A';
            toX = Integer.parseInt(range.substring(range.indexOf(':')+2))-1;
            toY = range.charAt(range.indexOf(':')+1)-'A';
        }else
            return null;
        cellRange[0]=fromX;
        cellRange[1]=fromY;
        cellRange[2]=toX;
        cellRange[3]=toY;
        return cellRange;
    }

    /**
     * 单元格范围数组转sql
     * @param range 单元格范围数组
     * @return 单元格范围sql
     */
    public static String rangeToSql(int[] range){
        String sql="";
        if(range.length>0){
            if(range[0]==range[2]&&range[1]==range[3]){
                sql = "rowInfoNo='"+range[0]+"' and colInfoNo='"+range[1]+"'";
            }else{
                sql = "rowInfoNo>='"+range[0]+"' and colInfoNo>='"+range[1]+"' and rowInfoNo<='"+range[2]+"' and colInfoNo<='"+range[3]+"'";
            }
        }else
            return null;
        return sql;
    }


    /**
     * 获取hql语句
     * @param monthNow 当前月
     * @param numObj 取数对象
     * @param tableName1 表名1
     * @param tableName2 表名2
     * @param tableName3 表名3
     * @param tableName4 表名4
     * @param kmNo 科目编号
     * @param hsNo1 核算编号1
     * @param hsNo2 核算编号2
     * @return
     */
    public static String getSqlByNumObjForSpAccountBalanceSearch2(int monthNow,String numObj,
                                        String tableName1,String tableName2,
                                        String tableName3,String tableName4,
                                        String kmNo,String hsNo1,String hsNo2){
        String str = null;
        String temp = null;
        String tableName = tableName1.toString()+" t1";
        if(tableName2!=null&&!tableName2.equals(""))
            tableName =tableName +","+tableName2+" t2";
        if(tableName3!=null&&!tableName3.equals(""))
            tableName =tableName+","+ tableName3+" t3";
        if(tableName4!=null&&!tableName4.equals(""))
            tableName =tableName+","+ tableName4+" t4";
        int preMonth = monthNow - 1;
        switch (numObj){
            case "NCJF":
                str = "select t1.supMoney from "+tableName+" where "+kmNo;
                break;
            case "NCDF":
                str = "select -t1.supMoney from "+tableName+" where "+kmNo;
                break;
            case "JFLJ":
                str = "select t1.debitMoney01";
                for (int i = 2; i <= monthNow; i++) {
                    if(i<10)
                        str = str + "+t1.debitMoney0" + i;
                    else
                        str = str + "+t1.debitMoney" + i;
                }
                str = str + " from "+tableName+" where "+kmNo;
                break;
            case "DFLJ":
                str = "select t1.creditMoney01";
                for (int i = 2; i <= monthNow; i++) {
                    if(i<10)
                        str = str + "+t1.creditMoney0" + i;
                    else
                        str = str + "+t1.creditMoney" + i;
                }
                str = str + " from "+tableName+" where "+kmNo;
                break;
            case "YCJF":
                str = "select ";
                if(preMonth>0){
                    if(preMonth<10)
                        str = str + "t1.balance0"+preMonth;
                    else
                        str = str + "t1.balance"+preMonth;
                }else{
                    return null;
                }
                str = str + " from "+tableName+" where "+kmNo;
                break;
            case "YCDF":
                str = "select ";
                if(preMonth>0){
                    if(preMonth<10)
                        str = str + "-t1.balance0"+preMonth;
                    else
                        str = str + "-t1.balance"+preMonth;
                }else{
                    return null;
                }
                str = str + " from "+tableName+" where "+kmNo;
                break;
            case "JFFS":
                str = "select ";
                if(monthNow>0){
                    if(monthNow<10)
                        str = str + "t1.debitMoney0"+monthNow;
                    else
                        str = str + "t1.debitMoney"+monthNow;
                }
                str = str + " from "+tableName+" where "+kmNo;
                break;
            case "DFFS":
                str = "select ";
                if(monthNow>0){
                    if(monthNow<10)
                        str = str + "t1.creditMoney0"+monthNow;
                    else
                        str = str + "t1.creditMoney"+monthNow;
                }
                str = str + " from "+tableName+" where "+kmNo;
                break;
            case "JFYE":
                str = "select ";
                if(monthNow>0){
                    if(monthNow<10)
                        str = str + "t1.balance0"+monthNow;
                    else
                        str = str + "t1.balance"+monthNow;
                }
                str = str + " from "+tableName+" where "+kmNo;
                break;
            case "DFYE":
                str = "select ";
                if(monthNow>0){
                    if(monthNow<10)
                        str = str + "-t1.balance0"+monthNow;
                    else
                        str = str + "-t1.balance"+monthNow;
                }
                str = str + " from "+tableName+" where "+kmNo;
                break;

            case "BNJD":
                str = "select t1.debitMoney01";
                for (int i = 2; i <= monthNow; i++) {
                    if(i<10)
                        str = str + "+t1.debitMoney0" + i;
                    else
                        str = str + "+t1.debitMoney" + i;
                }
                str = str + "-t1.creditMoney01";
                for (int i = 2; i <= monthNow; i++) {
                    if(i<10)
                        str = str + "-t1.creditMoney0" + i;
                    else
                        str = str + "-t1.creditMoney" + i;
                }
                str = str + " from "+tableName+" where "+kmNo;
                break;
            case "BNDJ":
                str = "select t1.creditMoney01";
                for (int i = 2; i <= monthNow; i++) {
                    if(i<10)
                        str = str + "+t1.creditMoney0" + i;
                    else
                        str = str + "+t1.creditMoney" + i;
                }
                str = str + "-t1.debitMoney01";
                for (int i = 2; i <= monthNow; i++) {
                    if(i<10)
                        str = str + "-t1.debitMoney0" + i;
                    else
                        str = str + "-t1.debitMoney" + i;
                }
                str = str + " from "+tableName+" where "+kmNo;
                break;
            case "BYJD":
                str = "select ";
                if(monthNow>0){
                    if(monthNow<10)
                        str = str + "t1.debitMoney0" + monthNow + "-t1.creditMoney0" + monthNow;
                    else
                        str = str + "t1.debitMoney" + monthNow + "-t1.creditMoney" + monthNow;
                }
                str = str + " from "+tableName+" where "+kmNo;
                break;
            case "BYDJ":
                str = "select ";
                if(monthNow>0){
                    if(monthNow<10)
                        str = str + "t1.creditMoney0" + monthNow + "-t1.debitMoney0" + monthNow;
                    else
                        str = str + "t1.creditMoney" + monthNow + "-t1.debitMoney" + monthNow;
                }
                str = str + " from "+tableName+" where "+kmNo;
                break;
            case "NJFX":
                str = "select t1.supMoney from "+tableName+" where "+kmNo+" and supMoney>0";
                break;
            case "NDFX":
                str = "select -t1.supMoney from "+tableName+" where "+kmNo+" and supMoney<0";
                break;
            case "YJFX":
                if(preMonth<10)
                    temp = "t1.balance0"+preMonth;
                else
                    temp = "t1.balance"+preMonth;
                if(preMonth>0){
                    str ="select " + temp + " from " +tableName+" where "+kmNo+" and "+ temp + ">0";
                }else
                    return null;
                break;
            case "YDFX":
                if(preMonth<10)
                    temp = "-t1.balance0"+preMonth;
                else
                    temp = "-t1.balance"+preMonth;
                if(preMonth>0){
                    str ="select " + temp + " from " +tableName+" where "+kmNo+" and "+ temp.substring(1) + "<0";
                }else
                    return null;
                break;
        }

        str = str + " and t1.spNo1='" + hsNo1 + "' and t1.spNo2='" + hsNo2 + "'";
        return str;
    }

}
