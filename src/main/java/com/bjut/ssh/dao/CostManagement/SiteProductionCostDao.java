package com.bjut.ssh.dao.CostManagement;

import com.bjut.ssh.entity.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.transform.Transformers;
import org.hibernate.HibernateException;
import org.hibernate.property.ChainedPropertyAccessor;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.PropertyAccessorFactory;
import org.hibernate.property.Setter;
import javax.transaction.Transactional;
import javax.validation.constraints.Null;
import java.util.List;
/**
 * @Title: SiteProductionCostDao
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/7/17 14:20
 * @Version: 1.0
 */

@Repository
@Transactional
public class SiteProductionCostDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    public void close(Session session){
        if(session != null)
            session.close();
    }

    //部门下拉菜单获取部门
    public Msg getDepartmentSelect(){
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        Query query = session.createQuery("select spName,spNo from Lshszd where catNo = :a and spLevel = :b" );
        query.setString("a","01");
        query.setString("b","1");
        List<Map<String, Object>> list=query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("0","全部");//核算名称
        map.put("1","All");//核算编号
        list.add(0,map);//数字0的意思是把这个map放到list的头部

        if(list.size()>0){
            tx.commit();
            close(session);
            return  Msg.success().add("departmentSelect",list);
        }
        else{
            tx.commit();
            close(session);
            return Msg.fail().add("errorInfo","编号重复，请重新输入");
        }
    }

    // 销售方式下拉菜单获取
    public Msg getSalesMethodSelect(){
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        Query query = session.createQuery("select spName,spNo from Lshszd where catNo = :a and spLevel = :b" );
        query.setString("a","03");
        query.setString("b","1");
        List<Map<String, Object>> list=query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

        if(list.size()>0) {
            tx.commit();
            close(session);
            return Msg.success().add("salesMethodSelect",list);
        }
        else {
            close(session);
            return Msg.fail().add("errorInfo","编号重复，请重新输入");
        }
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

        // 这里由于图标中取自定义字段，所以从表cbqtsz中取
        // 注：可加判断是否还存在科目编号等
        for(String itemId : itemNoList){
            Query queryNam = session.createQuery("select itemName from Cbqtsz where itemNo = :a");
            queryNam.setString("a",itemId);
            itemName = (String) queryNam.uniqueResult();
            itemNameList.add(itemName);
        }

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

    // 图表数据获取
    public Msg getValData(String selectSalesMethod, String selectTimeField, String selectDepartmentValue){
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        String sqlField_Summation = "AnnualCumulative";
        String sqlFiled_Production = "AnnualCumulative";
        String sqlFiled_Department = "All";
        Double production_SpecificMethod = 0.0;
        Double production_Sum = 0.0;

        List<List<String>> value_List = new ArrayList<>();  // 返回数据
        List<List<String>> department = new ArrayList<>(); // 接收部门及部门编号
        List<String> departmentID_List = new ArrayList<>(); // 部门编号
        List<String> name_List = new ArrayList<>(); // 部门名称

        // 根据选择时间进行所需数据选择
        if(selectTimeField.equals(("AnnualCumulative"))){
            sqlField_Summation = "debitMoneyAcm";//注意这里的annualCumulative是和前面的不一样
            sqlFiled_Production = "debitQtyAcm";
        }
        else{
            int len = selectTimeField.length();
            if(len == 1){
                selectTimeField = "0" + selectTimeField;
            }
            sqlFiled_Production = "debitQty" + selectTimeField;
            sqlField_Summation = "debitMoney" + selectTimeField;
        }

        // 部门及部门编号获取
        if(selectDepartmentValue.equals(("All"))){
            sqlFiled_Department = "All";
        }
        else{
            sqlFiled_Department = selectDepartmentValue;
        }
        department = getDepartment(sqlFiled_Department);
        name_List = department.get(0);
        departmentID_List = department.get(1);
        value_List.add(name_List);
        System.out.println(value_List);

        Double productionCoefficient = 0.0;                 // 销售量占比
        Double dataValSum = 0.0;                            // 核算金额计算
        List<String> itemsNo_List = new ArrayList<>();      // 各核算项目ID
        itemsNo_List= getItemsNo();
        List<String> fqItemsNo_List = new ArrayList<>();    // 方气各核算项目
        List<Double> val_List = new ArrayList<>();          // 计算细项

        // 计算各核算项目金额
        for(String item : itemsNo_List){
            List<String> allVal_List = new ArrayList<>();       // 计算结果
            allVal_List.clear();
            System.out.println(allVal_List);
            System.out.println(item);
            if (judgePerGas(item) == 0) {
                for(String departmentID : departmentID_List) {
                    // 获取部门销售方式产品量以及全部产品量
                    dataValSum = 0.0;
                    val_List.clear();
                    System.out.println(selectSalesMethod);
                    production_SpecificMethod = getProduction(sqlFiled_Production, selectSalesMethod, departmentID);
                    production_Sum = getSumProduction(sqlFiled_Production, departmentID);
                    productionCoefficient = calculateData(production_SpecificMethod, production_Sum);
                    val_List = getCustomItemVal(sqlField_Summation, departmentID, item);
                    System.out.println(production_Sum);
                    System.out.println(production_SpecificMethod);
                    System.out.println(productionCoefficient);
                    for (Double val : val_List) {
                        dataValSum += val;
                    }
                    System.out.println(dataValSum);
                    dataValSum = dataValSum * productionCoefficient;
                    allVal_List.add(dataValSum.toString());
                    System.out.println(allVal_List);
                }
                value_List.add(allVal_List);
                System.out.println(value_List);
            }
            else{
                fqItemsNo_List.clear(); // 只有自定义字段是方气的时候才有用
                fqItemsNo_List = getCustomItemsNo(item); // 这时候才能得到有科目编号组成的自定义编号
                for (String departmentID : departmentID_List) {
                    // 获取部门销售方式产品量以及全部产品量
                    dataValSum = 0.0;
                    val_List.clear();
                    production_SpecificMethod = getProduction(sqlFiled_Production, selectSalesMethod, departmentID);
                    production_Sum = getSumProduction(sqlFiled_Production, departmentID);
                    productionCoefficient = calculateData(production_SpecificMethod, production_Sum);
                    System.out.println(production_Sum);
                    System.out.println(production_SpecificMethod);
                    System.out.println(productionCoefficient);
                    for(String fqItem : fqItemsNo_List){
                        val_List.clear();
                        val_List = getCustomItemVal(sqlField_Summation, departmentID, fqItem);
                        for (Double val : val_List) {
                            dataValSum += val;
                        }
                    }
                    System.out.println(dataValSum);
                    dataValSum = dataValSum * productionCoefficient;
                    dataValSum = calculateData(dataValSum, production_SpecificMethod);
                    allVal_List.add(dataValSum.toString());
                    System.out.println(allVal_List);
                }
                value_List.add(allVal_List);
                System.out.println(value_List);
            }
        }

        System.out.println(value_List);

        if(value_List.size()>0){
            tx.commit();
            close(session);
            return  Msg.success().add("SPC_value_List",value_List);
        }
        else{
            tx.commit();
            close(session);
            return Msg.fail().add("errorInfo","编号重复，请重新输入");
        }
    }

    // 获取报表数据
    public Msg getDatagridData(String selectSite, String selectTimeField) {
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        String sqlField_Summation = "AnnualCumulative";
        String sqlFiled_Production = "AnnualCumulative";
        // 根据选择时间进行所需数据选择
        if (selectTimeField.equals(("AnnualCumulative"))) {
            sqlField_Summation = "debitMoneyAcm";//注意这里的annualCumulative是和前面的不一样
            sqlFiled_Production = "debitQtyAcm";
        } else {
            int len = selectTimeField.length();
            if (len == 1) {
                selectTimeField = "0" + selectTimeField;
            }
            sqlFiled_Production = "debitQty" + selectTimeField;
            sqlField_Summation = "debitMoney" + selectTimeField;
        }

        List<String> itemsName_List = new ArrayList<>();    // 指标列名称
        List<String> itemsNo_List = new ArrayList<>();      // 指标列ID
        List<List<String>> departments = new ArrayList<>();      // 核算部门
        List<String> departmentNo_List = new ArrayList<>();      // 部门ID
        List<Map<String, Object>> value_List = new ArrayList<>();

        itemsNo_List = getItemsNo();              // 指标ID
        for (String itemNo : itemsNo_List) {
            String itemName;
            try {
                Query queryNam = session.createQuery("select itemName from Cbqtsz where itemNo = :a");
                queryNam.setString("a", itemNo);
                itemName = (String) queryNam.uniqueResult();
            } catch (Exception e) {
                itemName = "null";
                tx.rollback();
            }
            itemsName_List.add(itemName);
        }

        departments = getDepartment(selectSite);  // 这里是department
        departmentNo_List = departments.get(1);   // department id

        Double allvalSum = 0.0;

        for (int i = 0; i < itemsName_List.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("text", itemsName_List.get(i));//科目名称
            int j = 0;
            for (String department : departmentNo_List) {
                allvalSum = 0.0;
                List<Double> val_List = new ArrayList<>();
                if (judgePerGas(itemsNo_List.get(i)) == 0) {
                    val_List.clear();
                    val_List = getCustomItemVal(sqlField_Summation, department, itemsNo_List.get(i));
//                    System.out.println(val_List);
                    for (Double val : val_List) {
                        allvalSum += val;
                    }
                    map.put("department" + String.valueOf(j+1), allvalSum);
                    j++;
                } else {
                    List<String> fqItemsNo_List = new ArrayList<>();    // 方气各核算项目
                    fqItemsNo_List.clear(); // 只有自定义字段是方气的时候才有用
                    fqItemsNo_List = getCustomItemsNo(itemsNo_List.get(i)); // 这时候才能得到有科目编号组成的自定义编号
                    allvalSum = 0.0;
                    val_List.clear();
                    for (String fqItem : fqItemsNo_List) {
                        val_List.clear();
                        val_List = getCustomItemVal(sqlField_Summation, department, fqItem);
                        for (Double val : val_List) {
                            allvalSum += val;
                        }
                    }
                    allvalSum = calculateData(allvalSum,getSumProduction(sqlFiled_Production,department));
                    map.put("department" + String.valueOf(j+1), allvalSum);
                    j++;
                }
            }
            value_List.add(map);
        }

        System.out.println(value_List);

        if (value_List.size() > 0) {
            tx.commit();
            close(session);
            return Msg.success().add("SPC_datagridValue_List", value_List);
        } else {
            tx.commit();
            close(session);
            return Msg.fail().add("errorInfo", "编号重复，请重新输入");
        }
    }

    // 部门及部门编号读取
    private List<List<String>> getDepartment(String sqlFiled_Department){
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        List<List<String>> department = new ArrayList();
        List<String> departmentID_List = new ArrayList<>(); // 部门编号
        List<String> name_List = new ArrayList<>(); // 部门名称

        // 获取部门编号和名称
        Query query;
        try {
            List<Map<String, Object>> list;
            if (sqlFiled_Department.equals(("All"))) {
                query = session.createQuery("select spName,spNo from Lshszd where catNo = :a and spLevel = :b");
                query.setString("a", "01"); // 核算类别要改的话这里要改01
                query.setInteger("b", 2); // 添加一级分公司了的话这里要改
                list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

                for (Map m : list) {
                    for (Object k : m.keySet()) {
                        if (k.toString().equals("0")) {
                            name_List.add(m.get(k).toString());
                        } else {
                            departmentID_List.add(m.get(k).toString());
                        }
                    }
                }
            } else {
                sqlFiled_Department = sqlFiled_Department.substring(0, 4);
                query = session.createQuery("select spName,spNo from Lshszd where catNo = :a and spLevel = :b and substring(spNo,1,4)=(" + sqlFiled_Department + ")");
                query.setString("a", "01"); // 核算类别要改的话这里要改01
                query.setInteger("b", 2); // 添加一级分公司了的话这里要改
                list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                for (Map m : list) {
                    for (Object k : m.keySet()) {
                        if (k.toString().equals("0")) {
                            name_List.add(m.get(k).toString());
                        } else {
                            departmentID_List.add(m.get(k).toString());
                        }
                    }
                }
            }
        }catch(Exception e){
            name_List.add("Null");
            departmentID_List.add("Null");
        }

        department.add(name_List);
        department.add(departmentID_List);

        tx.commit();
        close(session);
        return department;
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

    // 根据SalesMethodID获取某部门不同销售方式的产量
    private Double getProduction(String sqlFiled_Production, String salesMethodID, String departmentID){
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        Double production = 0.0;

        try {
            Query query_getProduction = session.createQuery("select (" + sqlFiled_Production + ") from Lshssl where spNo1 = :a and spNo2 = :b");
            query_getProduction.setString("a", departmentID);
            query_getProduction.setString("b", salesMethodID);
            production = (double) query_getProduction.uniqueResult();
            tx.commit();
            close(session);
            return production;
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
            queryNo.setString("a", "000015");
            itemNo = (String) queryNo.uniqueResult();
            tx.rollback();
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
            tx.rollback();
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

        Query query_getDataVal;
        for(String item : itemNoList){
            //这里只计算一个核算编号
            try {
                query_getDataVal = session.createQuery("select " + sqlField_Summation + " from Lshsje where itemNo = :a and spNo1 = :b and spNo2 is null ");
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
            }catch(Exception e){
                val.add(0.0);
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
            tx.rollback();
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
        return itemNoList;
    }
}
