package com.bjut.ssh.dao.CostManagement;

import com.bjut.ssh.entity.Msg;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @Title: ManagementCostDao
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/7/6 13:55
 * @Version: 1.0
 */

@Repository
@Transactional
public class CostBudgetImplementationTableDao {
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        return this.sessionFactory.openSession();
    }

    public void close(Session session) {
        if (session != null)
            session.close();
    }

    //获取图表的主题
    public Msg getEchartsTheme() {
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        List<String> themeList = new ArrayList<>();

        Query query = session.createQuery("select graphTheme from Cbtbsz where graphNo = :a");
        query.setString("a", "000006");
        String graphTheme = (String) query.uniqueResult();
        themeList.add(graphTheme);

        tx.commit();
        close(session);
        return Msg.success().add("CostBudgetImplementationTable_getEchartsTheme", themeList);
    }

    //根据选择的时间获取成本预算执行情况
    public Msg getValData(String selectTime) {
        String sqlField_Summation = "debitMoneyAcm";
        List<List<String>> BudgetImpl_List = new ArrayList<>();//
        List<String> BudgetImplName_List = new ArrayList<>();//
        List<String> BudgetImplCost_List = new ArrayList<>();//完成金额
        List<String> BudgetImplBudget_List = new ArrayList<>();//年度预算金额
        List<String> BudgetImplCostPercent_List = new ArrayList<>();//完成进度预算百分比
        List<String> BudgetImplBudgetPercent_List = new ArrayList<>();//完成年度预算百分比
        String itemNo1 = null;//所有科目字典的编号
        String[] split_itemNo1 = null;//编号数组
        String itemNo2 = null;//所有自定义科目的编号
        String[] split_itemNo2 = null;//编号数组

        Session session = null;
        Transaction tx = null;

        //根据选择的时间不同，对sqlField_Summation(借方金额)，sqlFiled_Production（产量数量）进行赋值
        if (selectTime.equals("AnnualCumulative")) {
            sqlField_Summation = "debitMoneyAcm";//注意这里的annualCumulative是和前面的不一样
        } else {
            int len = selectTime.length();
            if (len == 1) {
                //selectTime = ("0" + selectTime.substring(len - 1, len + 1));     //转换月份的格式，如将“1”转成“01”
                selectTime = "0" + selectTime;
            }
            sqlField_Summation = "debitMoney" + selectTime;//注意，这个与之前的命名不同
        }

        try {
            session = getSession();
            tx = session.beginTransaction();

            //------------------------完成金额-----------------------------------------------------------------------
            //从表CBTBSZ中获取成本预算执行情况的数据1和数据1类型
            itemNo1 = GetGraphData("1");
            split_itemNo1 = itemNo1.split("\\;");//把每个科目编号存进数组中
            String itemType1 = GetGraphType("1");
            //将数组的每个数据加进list
            for (String item1 : split_itemNo1) {
                //根据科目编号从科目字典中获取相应的年度或月份值
                Query query_productionCost = session.createQuery("select itemName," + sqlField_Summation + " from Lskmzd where itemNo = :bbb");
                query_productionCost.setString("bbb", item1);
                List<Object[]> list_productionCost = query_productionCost.list();

                for (Object[] object : list_productionCost) {
                    BudgetImplName_List.add((String) object[0]);
                    if (object[1] == null) {      //查询数据为null
                        object[1] = 0.0;
                    }
                    Double itemindex = (Double) object[1];
                    String itemIndex = itemindex.toString();
                    BudgetImplCost_List.add(itemIndex);
                }
            }

            //从表CBTBSZ中获取成本预算执行情况的数据2和数据2类型
            itemNo2 = GetGraphData("2");
            split_itemNo2 = itemNo2.split("\\;");//把每个科目编号存进数组中
            String itemType2 = GetGraphType("2");
            //将数组的每个数据加进list
            for (String item2 : split_itemNo2) {
                Double itemValueSum = 0.0;//定义一个所有包含科目的金额总数

                //根据科目编号从CBQTSZ（成本其他设置表）中获取相应的项目名称
                Query query_itemName1 = session.createQuery("select itemName from Cbqtsz where itemNo = :bbb");
                query_itemName1.setString("bbb", item2);
                String itemName1 = (String) query_itemName1.uniqueResult();
                BudgetImplName_List.add(itemName1);

                //根据科目编号从CBQTSZ（成本其他设置表）中获取相应的科目编号
                Query query_itemData1 = session.createQuery("select itemData1 from Cbqtsz where itemNo = :bbb");
                query_itemData1.setString("bbb", item2);
                String list_itemData1 = (String) query_itemData1.uniqueResult();
                String[] split_itemData1 = list_itemData1.split("\\;");//把每个科目编号存进数组中
                for (String itemData1 : split_itemData1) {
                    Query query_itemDataSum = session.createQuery("select " + sqlField_Summation + " from Lskmzd where itemNo = :bbb");
                    query_itemDataSum.setString("bbb", itemData1);
                    Double itemValue = (Double) query_itemDataSum.uniqueResult();
                    if (itemValue == null) {
                        itemValue = 0.0;
                    }
                    itemValueSum += itemValue;
                }
                String itemValueSum_String = itemValueSum.toString();
                BudgetImplCost_List.add(itemValueSum_String);
            }

            //-----------------年度预算金额---------------------------------------------------------------------
            //根据每个科目编号获取相应的预算金额
            for (String item1 : split_itemNo1) {
                //根据科目编号从科目字典中获取相应的年度或月份值
                Query query_BudgetMoney1 = session.createQuery("select budgetMoney from Lskmzd where itemNo = :bbb");
                query_BudgetMoney1.setString("bbb", item1);
                Double BudgetMoney1 = (Double) query_BudgetMoney1.uniqueResult();
                //如果时间是月份，那么预算金额要除以12
                if(!selectTime.equals("AnnualCumulative")){
                    BudgetMoney1 = BudgetMoney1/12;
                }
                String BudgetMoney1_String = BudgetMoney1.toString();
                BudgetImplBudget_List.add(BudgetMoney1_String);
            }

            //从表CBTBSZ中获取成本预算执行情况的数据2和数据2类型
            //根据自定义科目中的科目，将他们的预算金额相加
            for (String item2 : split_itemNo2) {
                Double itemValueSum = 0.0;//定义一个所有包含科目的金额总数

                //根据科目编号从CBQTSZ（成本其他设置表）中获取相应的科目编号
                Query query_itemData1 = session.createQuery("select itemData1 from Cbqtsz where itemNo = :bbb");
                query_itemData1.setString("bbb", item2);
                String list_itemData2 = (String) query_itemData1.uniqueResult();
                String[] split_itemData2 = list_itemData2.split("\\;");//把每个科目编号存进数组中
                for (String itemData1 : split_itemData2) {
                    Query query_itemDataSum = session.createQuery("select budgetMoney from Lskmzd where itemNo = :bbb");
                    query_itemDataSum.setString("bbb", itemData1);
                    Double itemValue = (Double) query_itemDataSum.uniqueResult();
                    itemValueSum += itemValue;
                }
                //如果时间是月份，那么预算金额要除以12
                if(selectTime.equals("AnnualCumulative")){
                    itemValueSum = itemValueSum/12;
                }
                String itemValueSum_String = itemValueSum.toString();
                BudgetImplBudget_List.add(itemValueSum_String);
            }

            //---------------------完成年度预算百分比----------------------------------------------------------------------
            for (int i = 0; i < BudgetImplName_List.size(); i++) {
                Double BudgetPercent_Double = 0.0;
                String BudgetPercent_String = null;
                Double BudgetData_Double = 0.0;
                String BudgetData_String = null;
                Double ImplCost_Double = 0.0;
                String ImplCost_String = null;

                ImplCost_String = BudgetImplCost_List.get(i);
                ImplCost_Double = Double.parseDouble(ImplCost_String);
                BudgetData_String = BudgetImplBudget_List.get(i);
                BudgetData_Double = Double.parseDouble(BudgetData_String);

                //BudgetPercent_Double = (double) Math.round(((calculateData(ImplCost_Double, BudgetData_Double)) * 100) / 100);
                //BudgetPercent_Double = calculateData(ImplCost_Double, BudgetData_Double);
                DecimalFormat df = new DecimalFormat("0.00");
                //BudgetPercent_Double = df.format(calculateData(ImplCost_Double, BudgetData_Double));
                BudgetPercent_String = df.format(calculateData(ImplCost_Double, BudgetData_Double) * 100);
                BudgetImplBudgetPercent_List.add(BudgetPercent_String);
            }

            //----------------------完成进度预算百分比---------------------------------------------------------------------------------
            Calendar calendar = Calendar.getInstance();//可以对每个时间域单独修改
            int month = calendar.get(Calendar.MONTH);
            for (int i = 0; i < BudgetImplName_List.size(); i++) {
                Double CostPercent_Double = 0.0;
                String CostPercent_String = null;
                Double BudgetData_Double = 0.0;
                String BudgetData_String = null;
                Double ImplCost_Double = 0.0;
                String ImplCost_String = null;

                ImplCost_String = BudgetImplCost_List.get(i);
                ImplCost_Double = Double.parseDouble(ImplCost_String);
                BudgetData_String = BudgetImplBudget_List.get(i);
                BudgetData_Double = Double.parseDouble(BudgetData_String);
                DecimalFormat df = new DecimalFormat("0.00");//保留小数点后两位
                //CostPercent_Double = (double) Math.round((((calculateData(ImplCost_Double, BudgetData_Double)) * 12 / month) * 100) / 100);
                CostPercent_String = df.format((calculateData(ImplCost_Double, BudgetData_Double) * 12 / month) * 100);
                BudgetImplCostPercent_List.add(CostPercent_String);
            }

            BudgetImpl_List.add(BudgetImplName_List);
            BudgetImpl_List.add(BudgetImplCost_List);
            BudgetImpl_List.add(BudgetImplBudget_List);
            BudgetImpl_List.add(BudgetImplBudgetPercent_List);
            BudgetImpl_List.add(BudgetImplCostPercent_List);
            return Msg.success().add("CBIT_CostBudgetImplement_List", BudgetImpl_List);
        } catch (Exception e) {
            tx.rollback();
            close(session);
            e.printStackTrace();
        }

        return Msg.fail().add("error", "111");
    }

    //报表数据
    public Msg getDatagridData(String selectTime) {
        String sqlField_Summation = "debitMoneyAcm";
        List<Map<String, Object>> BudgetImpl_List = new ArrayList<>();//
        List<String> BudgetImplName_List = new ArrayList<>();//
        List<String> BudgetImplCost_List = new ArrayList<>();//完成金额
        List<String> BudgetImplBudget_List = new ArrayList<>();//年度预算金额
        List<String> BudgetImplCostPercent_List = new ArrayList<>();//完成进度预算百分比
        List<String> BudgetImplBudgetPercent_List = new ArrayList<>();//完成年度预算百分比
        String itemNo1 = null;//所有科目字典的编号
        String[] split_itemNo1 = null;//编号数组
        String itemNo2 = null;//所有自定义科目的编号
        String[] split_itemNo2 = null;//编号数组

        Session session = null;
        Transaction tx = null;

        //根据选择的时间不同，对sqlField_Summation(借方金额)，sqlFiled_Production（产量数量）进行赋值
        if (selectTime.equals("AnnualCumulative")) {
            sqlField_Summation = "debitMoneyAcm";//注意这里的annualCumulative是和前面的不一样
        } else {
            int len = selectTime.length();
            if (len == 1) {
                //selectTime = ("0" + selectTime.substring(len - 1, len + 1));     //转换月份的格式，如将“1”转成“01”
                selectTime = "0" + selectTime;
            }
            sqlField_Summation = "debitMoney" + selectTime;//注意，这个与之前的命名不同
        }

        try {
            session = getSession();
            tx = session.beginTransaction();

            //------------------------完成金额-----------------------------------------------------------------------
            //从表CBTBSZ中获取成本预算执行情况的数据1和数据1类型
            itemNo1 = GetGraphData("1");
            split_itemNo1 = itemNo1.split("\\;");//把每个科目编号存进数组中
            String itemType1 = GetGraphType("1");
            //将数组的每个数据加进list
            for (String item1 : split_itemNo1) {
                //根据科目编号从科目字典中获取相应的年度或月份值
                Query query_productionCost = session.createQuery("select itemName," + sqlField_Summation + " from Lskmzd where itemNo = :bbb");
                query_productionCost.setString("bbb", item1);
                List<Object[]> list_productionCost = query_productionCost.list();

                for (Object[] object : list_productionCost) {
                    BudgetImplName_List.add((String) object[0]);
                    if (object[1] == null) {
                        object[1] = 0.0;
                    }
                    Double itemindex = (Double) object[1];
                    String itemIndex = itemindex.toString();
                    BudgetImplCost_List.add(itemIndex);
                }
            }

            //从表CBTBSZ中获取成本预算执行情况的数据2和数据2类型
            itemNo2 = GetGraphData("2");
            split_itemNo2 = itemNo2.split("\\;");//把每个科目编号存进数组中
            String itemType2 = GetGraphType("2");
            //将数组的每个数据加进list
            for (String item2 : split_itemNo2) {
                Double itemValueSum = 0.0;//定义一个所有包含科目的金额总数

                //根据科目编号从CBQTSZ（成本其他设置表）中获取相应的项目名称
                Query query_itemName1 = session.createQuery("select itemName from Cbqtsz where itemNo = :bbb");
                query_itemName1.setString("bbb", item2);
                String itemName1 = (String) query_itemName1.uniqueResult();
                BudgetImplName_List.add(itemName1);

                //根据科目编号从CBQTSZ（成本其他设置表）中获取相应的科目编号
                Query query_itemData1 = session.createQuery("select itemData1 from Cbqtsz where itemNo = :bbb");
                query_itemData1.setString("bbb", item2);
                String list_itemData1 = (String) query_itemData1.uniqueResult();
                String[] split_itemData1 = list_itemData1.split("\\;");//把每个科目编号存进数组中
                for (String itemData1 : split_itemData1) {
                    Query query_itemDataSum = session.createQuery("select " + sqlField_Summation + " from Lskmzd where itemNo = :bbb");
                    query_itemDataSum.setString("bbb", itemData1);
                    Double itemValue = (Double) query_itemDataSum.uniqueResult();
                    if (itemValue == null) {
                        itemValue = 0.0;
                    }
                    itemValueSum += itemValue;
                }
                String itemValueSum_String = itemValueSum.toString();
                BudgetImplCost_List.add(itemValueSum_String);
            }

            //-----------------年度预算金额---------------------------------------------------------------------
            //根据每个科目编号获取相应的预算金额
            for (String item1 : split_itemNo1) {
                //根据科目编号从科目字典中获取相应的年度或月份值
                Query query_BudgetMoney1 = session.createQuery("select budgetMoney from Lskmzd where itemNo = :bbb");
                query_BudgetMoney1.setString("bbb", item1);
                Double BudgetMoney1 = (Double) query_BudgetMoney1.uniqueResult();
                //如果时间是月份，那么预算金额要除以12
                if(!selectTime.equals("AnnualCumulative")){
                    BudgetMoney1 = BudgetMoney1/12;
                }
                String BudgetMoney1_String = BudgetMoney1.toString();
                BudgetImplBudget_List.add(BudgetMoney1_String);
            }

            //从表CBTBSZ中获取成本预算执行情况的数据2和数据2类型
            //根据自定义科目中的科目，将他们的预算金额相加
            for (String item2 : split_itemNo2) {
                Double itemValueSum = 0.0;//定义一个所有包含科目的金额总数

                //根据科目编号从CBQTSZ（成本其他设置表）中获取相应的科目编号
                Query query_itemData1 = session.createQuery("select itemData1 from Cbqtsz where itemNo = :bbb");
                query_itemData1.setString("bbb", item2);
                String list_itemData2 = (String) query_itemData1.uniqueResult();
                String[] split_itemData2 = list_itemData2.split("\\;");//把每个科目编号存进数组中
                for (String itemData1 : split_itemData2) {
                    Query query_itemDataSum = session.createQuery("select budgetMoney from Lskmzd where itemNo = :bbb");
                    query_itemDataSum.setString("bbb", itemData1);
                    Double itemValue = (Double) query_itemDataSum.uniqueResult();
                    itemValueSum += itemValue;
                }
                //如果时间是月份，那么预算的金额要除以12
                if(!selectTime.equals("AnnualCumulative")){
                    itemValueSum = itemValueSum/12;
                }
                String itemValueSum_String = itemValueSum.toString();
                BudgetImplBudget_List.add(itemValueSum_String);
            }

            //---------------------完成年度预算百分比----------------------------------------------------------------------
            for (int i = 0; i < BudgetImplName_List.size(); i++) {
                Double BudgetPercent_Double = 0.0;
                String BudgetPercent_String = null;
                Double BudgetData_Double = 0.0;
                String BudgetData_String = null;
                Double ImplCost_Double = 0.0;
                String ImplCost_String = null;

                ImplCost_String = BudgetImplCost_List.get(i);
                ImplCost_Double = Double.parseDouble(ImplCost_String);
                BudgetData_String = BudgetImplBudget_List.get(i);
                BudgetData_Double = Double.parseDouble(BudgetData_String);
                DecimalFormat df = new DecimalFormat("0.00");
                BudgetPercent_String = df.format(calculateData(ImplCost_Double, BudgetData_Double)*100);
                BudgetImplBudgetPercent_List.add(BudgetPercent_String);
            }

            //----------------------完成进度预算百分比---------------------------------------------------------------------------------
            Calendar calendar = Calendar.getInstance();//可以对每个时间域单独修改
            int month = calendar.get(Calendar.MONTH);
            for (int i = 0; i < BudgetImplName_List.size(); i++) {
                Double CostPercent_Double = 0.0;
                String CostPercent_String = null;
                Double BudgetData_Double = 0.0;
                String BudgetData_String = null;
                Double ImplCost_Double = 0.0;
                String ImplCost_String = null;

                ImplCost_String = BudgetImplCost_List.get(i);
                ImplCost_Double = Double.parseDouble(ImplCost_String);
                BudgetData_String = BudgetImplBudget_List.get(i);
                BudgetData_Double = Double.parseDouble(BudgetData_String);

                DecimalFormat df = new DecimalFormat("0.00");//保留小数点后两位
                CostPercent_String = df.format((calculateData(ImplCost_Double, BudgetData_Double) * 12 / month)*100);
                BudgetImplCostPercent_List.add(CostPercent_String);
            }

            for (int itemNum = 0; itemNum < BudgetImplName_List.size(); itemNum++) {
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("text", BudgetImplName_List.get(itemNum));
                map1.put("budgetImplBudget", BudgetImplBudget_List.get(itemNum));
                map1.put("budgetImplCost", BudgetImplCost_List.get(itemNum));
                map1.put("budgetImplBudgetPercent", BudgetImplBudgetPercent_List.get(itemNum));
                map1.put("budgetImplCostPercent", BudgetImplCostPercent_List.get(itemNum));
                BudgetImpl_List.add(map1);
            }

            return Msg.success().add("CBIT_datagridValue_List", BudgetImpl_List);
        } catch (Exception e) {
            tx.rollback();
            close(session);
            e.printStackTrace();
        }

        return Msg.fail().add("error", "111");
    }

    //获取成本图表设置表中的数据1或2
    public String GetGraphData(String dataNo) {
        Session session1 = null;
        Transaction tx1 = null;
        String result = null;
        try {
            session1 = getSession();
            tx1 = session1.beginTransaction();

            if (dataNo.equals("1")) {
                Query query_ItemNo1 = session1.createQuery("select graphData1 from Cbtbsz where graphNo = :a");
                query_ItemNo1.setString("a", "000006");
                result = (String) query_ItemNo1.uniqueResult();
            } else {
                Query query_ItemNo1 = session1.createQuery("select graphData2 from Cbtbsz where graphNo = :a");
                query_ItemNo1.setString("a", "000006");
                result = (String) query_ItemNo1.uniqueResult();
            }
            tx1.commit();
            close(session1);
            return result;
        } catch (Exception e) {
            tx1.rollback();
            close(session1);
            e.printStackTrace();
            return null;
        }
    }

    //获取成本图表设置表中的图表类型1或2
    public String GetGraphType(String dataNo) {
        Session session1 = null;
        Transaction tx1 = null;
        String result = null;
        try {
            session1 = getSession();
            tx1 = session1.beginTransaction();

            if (dataNo.equals("1")) {
                Query query_ItemNo1 = session1.createQuery("select dataType1 from Cbtbsz where graphNo = :a");
                query_ItemNo1.setString("a", "000006");
                result = (String) query_ItemNo1.uniqueResult();
            } else {
                Query query_ItemNo1 = session1.createQuery("select dataType2 from Cbtbsz where graphNo = :a");
                query_ItemNo1.setString("a", "000006");
                result = (String) query_ItemNo1.uniqueResult();
            }
            tx1.commit();
            close(session1);
            return result;
        } catch (Exception e) {
            tx1.rollback();
            close(session1);
            e.printStackTrace();
            return null;
        }
    }

    //判断两个数是否可以相除
    static double calculateData(double a, double b) {
        if (b != 0) {
            return a / b;
        } else {
            return 0;
        }
    }
}
