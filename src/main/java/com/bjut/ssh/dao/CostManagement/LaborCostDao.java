package com.bjut.ssh.dao.CostManagement;

import com.bjut.ssh.entity.Msg;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: LaborCostDao
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/8/3 13:55
 * @Version: 1.0
 */

@Repository
@Transactional
public class LaborCostDao {
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    public void close(Session session){
        if(session != null)
            session.close();
    }

    //获取图表的主题
    public Msg getEchartsTheme(){
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        List<String> themeList = new ArrayList<>();

        Query query = session.createQuery("select graphTheme from Cbtbsz where graphNo = :a");
        query.setString("a","000004");
        String graphTheme = (String) query.uniqueResult();
        themeList.add(graphTheme);

        tx.commit();
        close(session);
        return Msg.success().add("LaborCost_getEchartsTheme",themeList);
    }

    //根据选择的时间获取图表的数据
    public Msg getValData(String selectTime) {
        String sqlField_Summation = "AnnualCumulative";
        List<List<Map<String, Object>>> laborCost_List = new ArrayList<>();//管理人员和生产人员总列表
        List<Map<String, Object>> ManagementCost_List = new ArrayList<>();//管理人员薪酬列表
        List<Map<String, Object>> ProductionCost_List = new ArrayList<>();//生产人员薪酬列表
        List<Map<String, Object>> SumCost_List = new ArrayList<>();
        double sum_productionCost = 0;//生产人员费用总数
        double sum_managementCost = 0;//管理人员费用总数

        Session session = null;
        Transaction tx = null;

        //根据选择的时间不同，对sqlField_Summation(借方金额)，sqlFiled_Production（产量数量）进行赋值
        if(selectTime.equals("AnnualCumulative")){
            sqlField_Summation = "debitMoneyAcm";//注意这里的annualCumulative是和前面的不一样
        }else {
            int len = selectTime.length();
            if(len == 1) {
                //selectTime = ("0" + selectTime.substring(len - 1, len + 1));     //转换月份的格式，如将“1”转成“01”
                selectTime = "0" + selectTime;
            }
            sqlField_Summation = "debitMoney" + selectTime;//注意，这个与之前的命名不同
        }

        try {
            session = getSession();
            tx = session.beginTransaction();

            //从表CBTBSZ中获取生产人员薪酬
            Query query = session.createQuery("select graphData1 from Cbtbsz where graphNo = :a");
            query.setString("a","000004");
            String itemNo= (String) query.uniqueResult();

            String[] split_itemNo = itemNo.split("\\;");
            //将数组的每个数据加进list
            for(String item1 : split_itemNo){
                //获取生产折旧项目的名称、编号以及编号对应的费用
                Query query_productionCost = session.createQuery("select itemName," + sqlField_Summation + " from Lskmzd where item = :aaa and itemNo = :bbb" );
                query_productionCost.setString("aaa","4");
                query_productionCost.setString("bbb",item1);
                List<Object[]> list_productionCost=query_productionCost.list();

                for(Object[] object : list_productionCost){
                    Map<String,Object> map = new HashMap<String, Object>();
                    if(object[1] == null){      //查询数据为null
                        object[1] = 0.0;
                    }
                    map.put((String) object[0],(double)object[1]);
                    ProductionCost_List.add(map);
                    sum_productionCost += (double)object[1];
                }
            }

            Map<String,Object> map = new HashMap<String, Object>();
            map.put("生产人员薪酬",sum_productionCost);
            SumCost_List.add(map);
            laborCost_List.add(ProductionCost_List);

            //从表CBTBSZ中获取管理人员薪酬
            Query query1 = session.createQuery("select graphData2 from Cbtbsz where graphNo = :a");
            query1.setString("a","000004");
            String itemNo1= (String) query1.uniqueResult();

            String[] split_itemNo1 = itemNo1.split("\\;");

            for(String item1 : split_itemNo1){
                //获取管理折旧项目的名称、编号以及编号对应的费用
                Query query_managementCost = session.createQuery("select itemName," + sqlField_Summation + " from Lskmzd where item = :aaa and itemNo = :bbb" );
                query_managementCost.setString("aaa","2");
                query_managementCost.setString("bbb",item1);
                List<Object[]> list_managementCost=query_managementCost.list();

                for(Object[] object : list_managementCost){
                    Map<String,Object> map1 = new HashMap<String, Object>();
                    if(object[1] == null){      //查询数据为null
                        object[1] = 0.0;
                    }
                    map1.put((String) object[0],(double)object[1]);
                    ManagementCost_List.add(map1);
                    sum_managementCost += (double)object[1];
                }
            }
            laborCost_List.add(ManagementCost_List);

            Map<String,Object> map1 = new HashMap<String, Object>();
            map1.put("管理人员薪酬",sum_managementCost);
            SumCost_List.add(map1);

            laborCost_List.add(SumCost_List);

            return Msg.success().add("LC_LaborCost_List", laborCost_List);
        }catch (Exception e) {
            tx.rollback();
            close(session);
            e.printStackTrace();
        }

        return Msg.fail().add("error","111");
    }

    //根据选择的时间获取图表的数据
    public Msg getDatagridData(String selectTime) {
        String sqlField_Summation = "AnnualCumulative";
        List<Map<String, Object>> laborCost_List = new ArrayList<>();//管理人员和生产人员总列表
        List<Map<String, Object>> ManagementCost_List = new ArrayList<>();//管理人员薪酬列表
        List<Map<String, Object>> ProductionCost_List = new ArrayList<>();//生产人员薪酬列表
        List<Map<String, Object>> SumCost_List = new ArrayList<>();
        double sum_productionCost = 0;//生产人员费用总数
        double sum_managementCost = 0;//管理人员费用总数

        Session session = null;
        Transaction tx = null;

        //根据选择的时间不同，对sqlField_Summation(借方金额)，sqlFiled_Production（产量数量）进行赋值
        if(selectTime.equals("AnnualCumulative")){
            sqlField_Summation = "debitMoneyAcm";//注意这里的annualCumulative是和前面的不一样
        }else {
            int len = selectTime.length();
            if(len == 1) {
                //selectTime = ("0" + selectTime.substring(len - 1, len + 1));     //转换月份的格式，如将“1”转成“01”
                selectTime = "0" + selectTime;
            }
            sqlField_Summation = "debitMoney" + selectTime;//注意，这个与之前的命名不同
        }

        try {
            session = getSession();
            tx = session.beginTransaction();

            //--------------------------从表CBTBSZ中获取生产人员薪酬------------------------------------------------
            Query query = session.createQuery("select graphData1 from Cbtbsz where graphNo = :a");
            query.setString("a","000004");
            String itemNo= (String) query.uniqueResult();

            String[] split_itemNo = itemNo.split("\\;");
            //将数组的每个数据加进list
            for(String item1 : split_itemNo){
                //获取生产折旧项目的名称、编号以及编号对应的费用
                Query query_productionCost = session.createQuery("select itemName," + sqlField_Summation + " from Lskmzd where item = :aaa and itemNo = :bbb" );
                query_productionCost.setString("aaa","4");
                query_productionCost.setString("bbb",item1);
                List<Object[]> list_productionCost=query_productionCost.list();

                for(Object[] object : list_productionCost){
                    if(object[1] == null){      //查询数据为null
                        object[1] = 0.0;
                    }
                    sum_productionCost += (double)object[1];
                    Double itemMoney_double = (double) object[1];
                    String itemMoney_String = itemMoney_double.toString();
                    Map<String, Object> map2 = new HashMap<String, Object>();
                    map2.put("text", (String) object[0]);
                    map2.put("productionLaborCost", itemMoney_String);
                    laborCost_List.add(map2);
                }
            }



            //---------从表CBTBSZ中获取管理人员薪酬----------------------------------------------------
            Query query1 = session.createQuery("select graphData2 from Cbtbsz where graphNo = :a");
            query1.setString("a","000004");
            String itemNo1= (String) query1.uniqueResult();

            String[] split_itemNo1 = itemNo1.split("\\;");

            for(String item1 : split_itemNo1){
                //获取管理折旧项目的名称、编号以及编号对应的费用
                Query query_managementCost = session.createQuery("select itemName," + sqlField_Summation + " from Lskmzd where item = :aaa and itemNo = :bbb" );
                query_managementCost.setString("aaa","2");
                query_managementCost.setString("bbb",item1);
                List<Object[]> list_managementCost=query_managementCost.list();

                for(Object[] object : list_managementCost){
                    if(object[1] == null){      //查询数据为null
                        object[1] = 0.0;
                    }
                    sum_managementCost += (double)object[1];
                    Double itemMoney_double = (double) object[1];
                    String itemMoney_String = itemMoney_double.toString();
                    Map<String, Object> map2 = new HashMap<String, Object>();
                    map2.put("text", (String) object[0]);
                    map2.put("managementLaborCost", itemMoney_String);
                    laborCost_List.add(map2);
                }
            }

            //-----------------------最后合计的部分--------------------------------------
            Map<String,Object> map1 = new HashMap<String, Object>();
            map1.put("text","合计");
            map1.put("productionLaborCost",sum_productionCost);
            map1.put("managementLaborCost",sum_managementCost);
            laborCost_List.add(map1);

            tx.commit();
            close(session);
            return Msg.success().add("LC_datagridValue_List", laborCost_List);
        }catch (Exception e) {
            tx.rollback();
            close(session);
            e.printStackTrace();
        }
        return Msg.fail().add("error","111");
    }
}
