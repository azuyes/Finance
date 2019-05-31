package com.bjut.ssh.dao.CostManagement;

import com.bjut.ssh.entity.Msg;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: ProProductionCostStructureDao
 * @Description: TODO
 * @Author: zf
 * @CreateDate: 2018/8/26 14:20
 * @Version: 1.0
 */
@Repository
@Transactional
public class ProProductionCostStructureDao {
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    public void close(Session session){
        if(session != null)
            session.close();
    }

    public Msg getProduct(){
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        // 获取产品编号和名称
        try {
            Query query;
            List<Map<String, Object>> list;
            query = session.createQuery("select spName,spNo from Lshszd where catNo = :a and spLevel = :b");
            query.setString("a", "02");
            query.setInteger("b", 1);
            list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            System.out.println(list);//测试

            if (list.size() > 0) {
                tx.commit();
                close(session);
                return Msg.success().add("productList", list);
            } else {
                tx.commit();
                close(session);
                return Msg.fail().add("errorInfo", "编号重复，请重新输入");
            }
        }catch(Exception e){
            tx.commit();
            close(session);
            return Msg.fail().add("errorInfo", "编号重复，请重新输入");
        }
    }

    public Msg getShowNo(String selectProductValue){
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        List<List<String>> items = new ArrayList<>();    //科目名称及编号
        List<String> itemID_List = new ArrayList(); // 科目编号
        items = getItemNames(selectProductValue);
        itemID_List = items.get(1);
        int maxNo;
        String text;
        maxNo = itemID_List.size();

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("0","全部");//图表个数名称
        map.put("1","All");//图表个数编号
        list.add(0,map);//数字0的意思是把这个map放到list的头部
        for(int i = maxNo-1; i > 1 ;i--){
            map = new HashMap<String, Object>();
            text = i+"";
            System.out.println(text);
            map.put("0",text);//图表个数名称
            map.put("1",text);//图表个数编号
            list.add(map);
        }

        System.out.println(list);
        if(list.size()>0) {
            tx.commit();
            close(session);
            return Msg.success().add("getShowNo",list);
        }
        else {
            close(session);
            return Msg.fail().add("errorInfo","编号重复，请重新输入");
        }
    }

    public Msg getValData(String showNo, String selectTimeField, String selectProductValue){
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        String sqlField_Summation = "AnnualCumulative";

        List<List<String>> items = new ArrayList<>();   //核算科目名称及编号
        List<String> itemsID_List = new ArrayList<>();  //核算科目编号
        List<String> lsName_List = new ArrayList<>();   //临时核算科目名称
        List<Double> lsVal1_List = new ArrayList<>();   //临时核算科目金额
        List<String> name_List = new ArrayList<>(); // 核算科目名称
        List<String> val1_List = new ArrayList<>(); // 核算科目金额

        // 根据选择时间进行所需数据选择
        if(selectTimeField.equals(("AnnualCumulative"))){
            sqlField_Summation = "debitMoneyAcm";//注意这里的annualCumulative是和前面的不一样
//            System.out.println(sqlField_Summation);//测试
        }
        else{
            int len = selectTimeField.length();
            if(len == 1){
                selectTimeField = "0" + selectTimeField;
            }
            sqlField_Summation = "debitMoney" + selectTimeField;
//            System.out.println(sqlField_Summation);//测试
        }


        items = getItemNames(selectProductValue);
        lsName_List = items.get(0);
        itemsID_List = items.get(1);
        for(String item : itemsID_List){
            lsVal1_List.add(getItemsMoney(sqlField_Summation, selectProductValue, item));
        }
        System.out.println(lsName_List);
        System.out.println(lsVal1_List);

        //由大到小排序
        for(int i = 0; i<lsVal1_List.size(); i++){
            for(int j = i+1; j<lsVal1_List.size(); j++){
                if(lsVal1_List.get(i)<lsVal1_List.get(j)){
                    Double termVal = lsVal1_List.get(i);
                    String termName = lsName_List.get(i);
                    lsVal1_List.set(i,lsVal1_List.get(j));
                    lsName_List.set(i,lsName_List.get(j));
                    lsVal1_List.set(j,termVal);
                    lsName_List.set(j,termName);
                }
            }
        }
        System.out.println(lsName_List);
        System.out.println(lsVal1_List);

        // 将排序后的核算科目按照显示要求计算
        if(showNo.equals("All")){
            name_List = lsName_List;
            for(Double val : lsVal1_List) {
                val1_List.add(val.toString());
            }
        }
        else{
            Double sum = 0.0;
            for(int k = 0; k < Integer.parseInt(showNo) - 1; k++){
                name_List.add(lsName_List.get(k));
                val1_List.add(lsVal1_List.get(k).toString());
            }
            for(int h = Integer.parseInt(showNo)-1; h < lsName_List.size(); h++){
                sum = sum + lsVal1_List.get(h);
            }
            name_List.add("其他");
            val1_List.add(sum.toString());
        }
        System.out.println(name_List);
        System.out.println(val1_List);

        List<List<String>> value_List = new ArrayList<>();
        value_List.add(name_List);
        value_List.add(val1_List);
//        System.out.println(name_List);  // 测试
//        if(selectProductValue.equals("000200000000")){
//            val1_List.add("269351.78");
//            val1_List.add("601011.67");
//            val1_List.add("104700.34");
//        }
        return Msg.success().add("PPCS_value_List",value_List);//命名的前面是这个功能名的缩写
    }

    // 科目名称及编号获取
    private List<List<String>> getItemNames(String selectSiteValue){
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        List<List<String>> items = new ArrayList<>();    //科目名称及编号
        List<String> itemName_List = new ArrayList<>(); // 科目名称
        List<String> itemID_List = new ArrayList(); // 科目编号
        String itemIDs;
        List<Map<String, Object>> list;

//        System.out.println(selectSiteValue);

        // 获取科目编号和名称
        try {
            Query query, query1;
            if (selectSiteValue.equals(("Null"))) {
                return items;
            } else {
//                System.out.println("getItemNames()");//测试
                query = session.createQuery("select graphData1 from Cbtbsz where graphNo = :a");
                query.setString("a", "000022");
                itemIDs = (String) query.uniqueResult();
                System.out.println(itemIDs);//测试
            }
            String[] split_itemNo = itemIDs.split("\\;");
            //将数组的每个数据加进list
            for(String item1 : split_itemNo){
                itemID_List.add(item1);
            }
            for (String no : itemID_List) {
//                System.out.println(no);
                query1 = session.createQuery("select itemName from Lskmzd where itemNo = :a");
                query1.setString("a",no);
                itemName_List.add(query1.uniqueResult().toString());
            }
        }catch(Exception e){
            tx.rollback();
            close(session);
//            e.printStackTrace();
        }
        items.add(itemName_List);
        items.add(itemID_List);

        tx.commit();
        close(session);
        return items;
    }

    // 获取报表数据
    public Msg getDatagridData(String selectSite){
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        String sqlField_Summation = "AnnualCumulative";
        String month = "0";
        List<String> itemsName_List = new ArrayList<>();    // 指标列名称
        List<String> itemsNo_List = new ArrayList<>();      // 指标列ID
        List<List<String>> items = new ArrayList<>();   //核算科目名称及编号
        List<Map<String,Object>> value_List = new ArrayList<>();

        items = getItemNames(selectSite);
        itemsName_List = items.get(0);
        itemsNo_List = items.get(1);

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
                monthString = "month" + String.valueOf(j);
                allvalSum = getItemVal(sqlField_Summation, selectSite,itemsNo_List.get(i));
                map.put(monthString, allvalSum);
            }
            value_List.add(map);
        }

//        System.out.println(value_List);

        if(value_List.size()>0){
            tx.commit();
            close(session);
            return  Msg.success().add("PPCS_datagridValue_List",value_List);
        }
        else{
            tx.commit();
            close(session);
            return Msg.fail().add("errorInfo","编号重复，请重新输入");
        }
    }

    // 各科目金额获取
    private Double getItemsMoney(String sqlField_Summation, String selectSiteValue, String itemID){
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        Double itemMoney = 0.0;
        try{
            Query query = session.createQuery("select "+ sqlField_Summation +" from Lshsje where itemNo = :a and spNo1 = :b and spNo2 is null ");
            query.setString("a", itemID);
            query.setString("b", selectSiteValue);
            if(query.uniqueResult() == null){
                itemMoney = 0.0;
//                System.out.println((double)query.uniqueResult());
            }
            else{
                itemMoney = (double)query.uniqueResult();
//                System.out.println((double)query.uniqueResult());
            }
            tx.commit();
            close(session);
            return itemMoney;
        }catch(Exception e){
            tx.rollback();
            close(session);
//            e.printStackTrace();
            return itemMoney;
        }
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
