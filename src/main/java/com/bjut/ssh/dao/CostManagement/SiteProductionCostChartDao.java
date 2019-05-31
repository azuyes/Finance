package com.bjut.ssh.dao.CostManagement;

import com.bjut.ssh.entity.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.beans.Expression;
import java.util.*;

import org.hibernate.transform.Transformers;
import org.hibernate.HibernateException;
import org.hibernate.property.ChainedPropertyAccessor;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.PropertyAccessorFactory;
import org.hibernate.property.Setter;
import javax.transaction.Transactional;

/**
 * @Title: SiteProductionCostChartDao
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/7/21 22:17
 * @Version: 1.0
 */

@Repository
@Transactional
public class SiteProductionCostChartDao {
    @Autowired
    private SessionFactory sessionFactory;
    public Session getSession(){
        return this.sessionFactory.openSession();
    }
    public void close(Session session){
        if(session != null)
            session.close();
    }

    // 图标显示项目获取：存在相关问题需要讨论明确
    public Msg getItemsName(){
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        List<String> itemNameList = new ArrayList<>();
        String itemName;

        List<String> itemNoList = getItemsNo();

        itemNameList.add("商品量");

        // 这里由于图标中取自定义字段，所以从表cbqtsz中取
        // 注：可加判断是否还存在科目编号等
        for(String itemId : itemNoList){
            try {
                Query queryNam = session.createQuery("select itemName from Cbqtsz where itemNo = :a");
                queryNam.setString("a", itemId);
                itemName = (String) queryNam.uniqueResult();
                itemNameList.add(itemName);
            }catch(Exception e){
                itemNameList.add("null");
                tx.rollback();
            }
        }

//        System.out.println(itemNameList);

        if(itemNameList.size()>0) {
            tx.commit();
            close(session);
            return Msg.success().add("itemNam",itemNameList);
        }
        else {
            tx.commit();
            close(session);
            return Msg.fail().add("errorInfo","编号重复，请重新输入");
        }
    }

    // 图表数据
    public Msg getValData(String selectSiteValue){
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        String sqlField_Summation = "AnnualCumulative";
        String sqlFiled_Production = "AnnualCumulative";
        Double production_Sum = 0.0;
        String month = "0";

        Double dataValSum = 0.0;                            // 核算金额计算
        List<String> itemsNo_List = new ArrayList<>();      // 各核算项目ID
        itemsNo_List= getItemsNo();
        List<Double> val_List = new ArrayList<>();          // 计算细项
        List<String> ProVal_List = new ArrayList<>();       // 计算结果

        List<List<String>> value_List = new ArrayList<>();  // 返回结果

        for(int i = 1; i < 13; i++){
            month = String.valueOf(i);
            int len = month.length();
            if(len == 1){
                month = "0" + month;
            }
            sqlFiled_Production = "debitQty" + month;
            production_Sum = getSumProduction(sqlFiled_Production, selectSiteValue);
            ProVal_List.add(production_Sum.toString());
        }
        value_List.add(ProVal_List);
//        System.out.println(value_List);

        // 计算各自定义字段临时核算项目金额
        for(String item : itemsNo_List){
            List<String> allVal_List = new ArrayList<>();       // 计算结果
            allVal_List.clear();
            for(int i = 1; i < 13; i++){
                month = String.valueOf(i);
                int len = month.length();
                if(len == 1){
                    month = "0" + month;
                }
                sqlField_Summation = "debitMoney" + month;
//                System.out.println(allVal_List);
//                System.out.println(item);
                if (judgePerGas(item) == 0) {
                    dataValSum = 0.0;
                    val_List.clear();
                    val_List = getCustomItemVal(sqlField_Summation, selectSiteValue, item);
                    System.out.println(val_List);
                    for (Double val : val_List) {
                        dataValSum += val;
                    }
                    allVal_List.add(dataValSum.toString());
                    System.out.println(dataValSum);
                }
                else{
                    List<String> fqItemsNo_List = new ArrayList<>();    // 方气各核算项目
                    fqItemsNo_List.clear(); // 只有自定义字段是方气的时候才有用
                    fqItemsNo_List = getCustomItemsNo(item); // 这时候才能得到有科目编号组成的自定义编号
                    dataValSum = 0.0;
                    val_List.clear();
                    for(String fqItem : fqItemsNo_List){
                        val_List.clear();
                        val_List = getCustomItemVal(sqlField_Summation, selectSiteValue, fqItem);
                        for (Double val : val_List) {
                            dataValSum += val;
                        }
                    }
                    dataValSum = calculateData(dataValSum,Double.parseDouble(ProVal_List.get(i-1)));
                    allVal_List.add(dataValSum.toString());
                    System.out.println(dataValSum);
                }
            }
            value_List.add(allVal_List);
        }

        System.out.println(value_List);

        if(value_List.size()>0){
            tx.commit();
            close(session);
            return  Msg.success().add("SPCC_value_List",value_List);
        }
        else{
            tx.commit();
            close(session);
            return Msg.fail().add("errorInfo","编号重复，请重新输入");
        }
    }

    // 获取报表数据
    public Msg getDatagridData(String selectSite){
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        String sqlField_Summation = "AnnualCumulative";
        String sqlFiled_Production = "AnnualCumulative";
        String month = "0";
        List<String> itemsName_List = new ArrayList<>();    // 指标列名称
        List<String> itemsNo_List = new ArrayList<>();      // 指标列ID
        List<Map<String,Object>> value_List = new ArrayList<>();

        itemsNo_List = getItemsNo();
        for(String itemNo : itemsNo_List){
            String itemName;
            try {
                Query queryNam = session.createQuery("select itemName from Cbqtsz where itemNo = :a");
                queryNam.setString("a", itemNo);
                itemName = (String) queryNam.uniqueResult();
            }catch(Exception e){
                itemName = "null";
                tx.rollback();
            }
            itemsName_List.add(itemName);
        }
        itemsName_List.add(0,"商品量");

        Double allvalSum = 0.0;
        String monthString = "0";

        for(int i = 0; i<itemsName_List.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("text", itemsName_List.get(i));//科目名称
            for(int j = 1; j < 13; j++){
                allvalSum = 0.0;
                month = String.valueOf(j);
                int len = month.length();
                if(len == 1){
                    month = "0" + month;
                }
                sqlField_Summation = "debitMoney" + month;
                sqlFiled_Production = "debitQty" + month;
                monthString = "month" + String.valueOf(j);
//                System.out.println(sqlField_Summation);
                if(i == 0){
                    allvalSum = getSumProduction(sqlFiled_Production, selectSite);
                    map.put(monthString,allvalSum);
                }else {
                    List<Double> val_List = new ArrayList<>();
                    if (judgePerGas(itemsNo_List.get(i - 1)) == 0) {
                        allvalSum = 0.0;
                        val_List.clear();
                        val_List = getCustomItemVal(sqlField_Summation, selectSite, itemsNo_List.get(i - 1));
                        System.out.println(val_List);
                        for (Double val : val_List) {
                            allvalSum += val;
                        }
                        map.put(monthString, allvalSum);
                    }
                    else{
                        List<String> fqItemsNo_List = new ArrayList<>();    // 方气各核算项目
                        fqItemsNo_List.clear(); // 只有自定义字段是方气的时候才有用
                        fqItemsNo_List = getCustomItemsNo(itemsNo_List.get(i - 1)); // 这时候才能得到有科目编号组成的自定义编号
                        allvalSum = 0.0;
                        val_List.clear();
                        for(String fqItem : fqItemsNo_List){
                            val_List.clear();
                            val_List = getCustomItemVal(sqlField_Summation, selectSite, fqItem);
                            for (Double val : val_List) {
                                allvalSum += val;
                            }
                        }
                        allvalSum = calculateData(allvalSum,getSumProduction(sqlFiled_Production,selectSite));
                        map.put(monthString, allvalSum);
                    }
                }
            }
            value_List.add(map);
        }

//        System.out.println(value_List);

        if(value_List.size()>0){
            tx.commit();
            close(session);
            return  Msg.success().add("SPCC_datagridValue_List",value_List);
        }
        else{
            tx.commit();
            close(session);
            return Msg.fail().add("errorInfo","编号重复，请重新输入");
        }
    }

    // 某部门全部产量读取
    private Double getSumProduction(String sqlFiled_Production, String departmentID){
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        Double sumProduction = 0.0;

        try {
            Query query_getProduction = session.createQuery("select sum(" + sqlFiled_Production + ") from Lshssl where spNo1 = :a");
            query_getProduction.setString("a", departmentID);
            sumProduction = (double) query_getProduction.uniqueResult();
//            System.out.println(sumProduction);
            tx.commit();
            close(session);

            return sumProduction;
        }catch (Exception e){
            tx.rollback();
            close(session);
//            e.printStackTrace();
            return 0.0;
        }
    }

    //判断两个数是否可以相除
    static double calculateData(double a, double b){
        if (b != 0)
        {
            return a/b;
        }
        else
        {
            return 0.0;
        }
    }

    // 图标中所显示项目编号（自定义字段）:存在相关问题需要讨论明确
    private List<String> getItemsNo(){
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        List<String> itemNoList = new ArrayList<>();
        String itemNo;

        // 注：判断DataType可在getDataVal中/单写函数
        try {
            Query queryNo = session.createQuery("select graphData1 from Cbtbsz where graphNo = :a");
            queryNo.setString("a", "000017");
            itemNo = (String) queryNo.uniqueResult();
            tx.commit();
            close(session);
        }catch(Exception e)
        {
            tx.rollback();
            close(session);
//            e.printStackTrace();
            itemNo = "null";
        }

        String[] split_itemNo = itemNo.split("\\;");
        //将数组的每个数据加进list
        for(String item1 : split_itemNo){
            itemNoList.add(item1);
        }

        return itemNoList;
    }

    //判断某显示项目数据类型是否为自定义字段（方气待讨论/暂时固定编号
    private int judgePerGas(String itemNo){
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        String dataType;

        try {
            Query queryNo = session.createQuery("select itemDataType1 from Cbqtsz where itemNo = :a");
            queryNo.setString("a", itemNo);
            dataType = (String) queryNo.uniqueResult();
            tx.commit();
            close(session);
        }catch(Exception e)
        {
            tx.rollback();
            close(session);
//            e.printStackTrace();
            dataType = "null";
        }

        if (dataType.equals("自定义字段")){
            System.out.println("1");
            return 1;   // 1方气
        }
        else{
            System.out.println("0");
            return 0;   // 0非方气
        }
    }

    //获取自定义字段lshsje细项
    private List<Double> getCustomItemVal(String sqlField_Summation, String departmentID, String itemNo){
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        List<Double> val = new ArrayList<>();
        List<String> itemNoList = new ArrayList<>();
        Double itemVal = 0.0;

        itemNoList = getCustomItemsNo(itemNo);

//        System.out.println(sqlField_Summation);
        Query query_getDataVal;
        for(String item : itemNoList){
            //这里只计算一个核算编号
            try {
                query_getDataVal = session.createQuery("select (" + sqlField_Summation + ") from Lshsje where itemNo = :a and spNo1 = :b and spNo2 is null ");
                //↓计算核算编号2
//          query_getDataVal = session.createQuery("select "+ sqlField_Summation +" from Lshsje where itemNo = :a and spNo2 = :b");
                query_getDataVal.setString("a", item);
                query_getDataVal.setString("b", departmentID);
                if (query_getDataVal.uniqueResult() == null) {
                    val.add(0.0);
                } else {
                    itemVal = (double) query_getDataVal.uniqueResult();
                    val.add(itemVal);
                }
//                System.out.println("try" + val + itemVal);
            }catch(Exception e){
                val.add(0.0);
                tx.rollback();
                System.out.println("catch" + val);
            }
        }
        tx.commit();
        close(session);
        return val;
    }

    //获取自定义字段编号细项科目编号
    private List<String> getCustomItemsNo(String itemNo){
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        List<String> itemNoList = new ArrayList<>();
        String items;

        // 注：判断DataType可在getDataVal中/单写函数
        try {
            Query queryNo = session.createQuery("select itemData1 from Cbqtsz where itemNo = :a");
            queryNo.setString("a", itemNo);
            items = (String) queryNo.uniqueResult();
            tx.commit();
            close(session);
        }catch(Exception e){
            tx.rollback();
            close(session);
//            e.printStackTrace();
            items = "null";
        }

        String[] split_itemNo = items.split("\\;");
        //将数组的每个数据加进list
        for(String item : split_itemNo){
            itemNoList.add(item);
        }
//        System.out.println(itemNoList);
        return itemNoList;
    }

    //获取科目编号lshsje细项
    private Double getItemVal(String sqlField_Summation, String departmentID, String itemNo){
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        Double val = 0.0;

        Query query_getDataVal;
        try {
            query_getDataVal = session.createQuery("select " + sqlField_Summation + " from Lshsje where itemNo = :a and spNo1 = :b and spNo2 is null ");
            //↓计算核算编号2
//          query_getDataVal = session.createQuery("select "+ sqlField_Summation +" from Lshsje where itemNo = :a and spNo2 = :b");
            query_getDataVal.setString("a", itemNo);
            query_getDataVal.setString("b", departmentID);
            if(query_getDataVal.uniqueResult() == null) {
                val = 0.0;
            }
            else{
                val = (double) query_getDataVal.uniqueResult();
            }
            tx.commit();
            close(session);
        }catch(Exception e) {
            val = 0.0;
            tx.rollback();
            close(session);
        }
        return val;
    }
}
