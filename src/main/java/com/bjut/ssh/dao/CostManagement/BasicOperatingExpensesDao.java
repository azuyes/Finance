package com.bjut.ssh.dao.CostManagement;

import com.bjut.ssh.entity.Msg;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @Title: BasicOperatingExpensesDao
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/7/6 13:55
 * @Version: 1.0
 */

@Repository
@Transactional
public class BasicOperatingExpensesDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        return this.sessionFactory.openSession();
    }

    public void close(Session session) {
        if (session != null)
            session.close();
    }

    //获取项目的名称列表
    public Msg getItemNam() {
        List<String> itemNameList = new ArrayList<>();//存放销售方式名称

        //普通科目
        String itemNo1list = getGraphData("1", "000001");
        if (itemNo1list != null) {
            String[] itemNo_split = itemNo1list.split("\\;");
            for (String itemNo : itemNo_split) {
                String itemName = getItemNamByItemNo("1", itemNo);
                itemNameList.add(itemName);
            }
        }
        //自定义科目
        String itemNo2list = getGraphData("2", "000001");
        if (itemNo2list != null) {
            String[] itemNo_split = itemNo2list.split("\\;");
            for (String itemNo : itemNo_split) {
                String itemName = getItemNamByItemNo("2", itemNo);
                itemNameList.add(itemName);
            }
        }

        return Msg.success().add("itemNam", itemNameList);
    }

    //部门下拉菜单获取部门
    public Msg getDepartmentSelect() {
        Session session = null;
        Transaction tx = null;
        //List<Departmentbase> DepartmentbaseList = null;
        String departmentCatNo;

        session = getSession();
        tx = session.beginTransaction();
        Query query_getDepartmentCatNo = session.createQuery("select itemData1 from Cbqtsz where itemNo = :a");
        query_getDepartmentCatNo.setString("a", "000001");
        departmentCatNo = (String) query_getDepartmentCatNo.uniqueResult();

        Query query = session.createQuery("select spName,spNo from Lshszd where catNo = :aaa and spLevel = :bbb");
        query.setString("aaa", departmentCatNo);
        query.setString("bbb", "1");
        List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("0", "全部");//核算名称
        map.put("1", "All");//核算编号

        list.add(0, map);//数字0的意思是把这个map放到list的头部
        System.out.println(list);
        if (list.size() > 0) {
            tx.commit();
            close(session);
            return Msg.success().add("departmentSelect", list);
        } else {
            tx.commit();
            close(session);
            return Msg.fail().add("errorInfo", "编号重复，请重新输入");
        }

    }

    //产品下拉菜单获取产品
    public Msg getProductSelect() {
        Session session = null;
        Transaction tx = null;
        String productCatNo;
        session = getSession();
        tx = session.beginTransaction();
        //查找对应的
        Query query_getProductCatNo = session.createQuery("select itemData1 from Cbqtsz where itemNo = :a");
        query_getProductCatNo.setString("a", "000002");
        productCatNo = (String) query_getProductCatNo.uniqueResult();

        Query query = session.createQuery("select spName,spNo from Lshszd where catNo = :aaa and spLevel = :bbb");
        query.setString("aaa", productCatNo);
        query.setString("bbb", "1");
        List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("0", "全部");//核算名称
        map.put("1", "All");//核算编号
        list.add(0, map);//数字0的意思是把这个map放到list的头部
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("0", "不选择");//核算名称
        map1.put("1", "null");//核算编号
        list.add(0, map1);//数字0的意思是把这个map放到list的头部
        System.out.println(list);
        if (list.size() > 0) {
            tx.commit();
            close(session);
            return Msg.success().add("productSelect", list);
        } else {
            tx.commit();
            close(session);
            return Msg.fail().add("errorInfo", "编号重复，请重新输入");
        }
    }

    //获取图表的主题
    public Msg getEchartsTheme() {
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        List<String> themeList = new ArrayList<>();

        Query query = session.createQuery("select graphTheme from Cbtbsz where graphNo = :a");
        query.setString("a", "000001");
        String graphTheme = (String) query.uniqueResult();
        themeList.add(graphTheme);
        Query query1 = session.createQuery("select graphTheme from Cbtbsz where graphNo = :a");
        query1.setString("a", "000002");
        String graphTheme1 = (String) query1.uniqueResult();
        themeList.add(graphTheme1);

        tx.commit();
        close(session);
        return Msg.success().add("getEchartsTheme", themeList);
    }

    //根据选择的公司和时间获取图表的数据
    public Msg getValData(String selectCompany, String selectProduct, String selectTime) {
        String sqlField_Summation = "AnnualCumulative";
        String sqlFiled_Production = "AnnualCumulative";
        List<List<Double>> value_List = new ArrayList<>();
        //获取普通科目
        List<String> ItemNo_List = getItemNo("1");
        int itemNoListNumber = ItemNo_List.size();
        //获取自定义字段
        List<String> DefinedItemNo_List = getItemNo("2");
        int definedItemNoListNumber = DefinedItemNo_List.size();

        //根据选择的时间不同，对sqlField_Summation(借方金额)，sqlFiled_Production（产量数量）进行赋值
        if (selectTime.equals("AnnualCumulative")) {
            sqlField_Summation = "debitMoneyAcm";//注意这里的annualCumulative是和前面的不一样
            sqlFiled_Production = "debitQtyAcm";
        } else {
            int len = selectTime.length();
            if (len == 1) {
                //selectTime = ("0" + selectTime.substring(len - 1, len + 1));     //转换月份的格式，如将“1”转成“01”
                selectTime = "0" + selectTime;
            }
            sqlFiled_Production = "debitQty" + selectTime;
            sqlField_Summation = "debitMoney" + selectTime;//注意，这个与之前的命名不同
        }

        if (selectCompany.equals("All")) {    //核算1栏选择全部
            if (selectProduct.equals("All")) {    //核算2栏也选择全部

            } else {
                List<String> sp1List = getSp1Id();//获取核算1的所有核算编号
                //普通科目
                for (int i = 0; i < itemNoListNumber; i++) {
                    List<Double> value1_List = new ArrayList<>();
                    String itemNo = ItemNo_List.get(i);
                    for (String sp1No : sp1List) {
                        //获取相应的金额数
                        value1_List.add(getMoney(sp1No, selectProduct, sqlField_Summation, itemNo));
                    }
                    value_List.add(value1_List);
                }
                //自定义字段
                for (int i = 0; i < definedItemNoListNumber; i++) {
                    List<Double> value1_List = new ArrayList<>();
                    String definedItemNo = DefinedItemNo_List.get(i);
                    for (String sp1No : sp1List) {
                        //获取相应的金额数
                        value1_List.add(getMoney_defined(sp1No,selectProduct,sqlField_Summation,definedItemNo));
                    }
                    value_List.add(value1_List);
                }
                //方气（普通科目）
                for (int i = 0; i < itemNoListNumber; i++) {
                    List<Double> value1_List = new ArrayList<>();
                    String itemNo = ItemNo_List.get(i);
                    for (String sp1No : sp1List) {
                        //获取相应的金额数
                        double perMoney = (double) Math.round(calculateData(getMoney(sp1No, selectProduct, sqlField_Summation, itemNo), getProduction(sp1No, selectProduct, sqlFiled_Production, itemNo)) * 100) / 100;
                        value1_List.add(perMoney);
                    }
                    value_List.add(value1_List);
                }

                //方气（自定义字段）
                for (int i = 0; i < definedItemNoListNumber; i++) {
                    List<Double> value1_List = new ArrayList<>();
                    String definedItemNo = DefinedItemNo_List.get(i);
                    for (String sp1No : sp1List) {
                        //获取相应的金额数
                        double perMoney = (double) Math.round(calculateData(getMoney_defined(sp1No, selectProduct, sqlField_Summation, definedItemNo), getProduction_defined(sp1No, selectProduct, sqlFiled_Production, definedItemNo)) * 100) / 100;
                        value1_List.add(perMoney);
                    }
                    value_List.add(value1_List);
                }
            }
        } else {
            if (selectProduct.equals("All")) {
                List<String> sp2List = getSp2Id();//获取核算1的所有核算编号
                for (int i = 0; i < itemNoListNumber; i++) {
                    List<Double> value1_List = new ArrayList<>();
                    String itemNo = ItemNo_List.get(i);
                    for (String sp2No : sp2List) {
                        //获取相应的金额数
                        value1_List.add(getMoney(selectCompany, sp2No, sqlField_Summation, itemNo));
                    }
                    value_List.add(value1_List);
                }
                //自定义字段
                for (int i = 0; i < definedItemNoListNumber; i++) {
                    List<Double> value1_List = new ArrayList<>();
                    String definedItemNo = DefinedItemNo_List.get(i);
                    for (String sp2No : sp2List) {
                        //获取相应的金额数
                        value1_List.add(getMoney_defined(selectCompany,sp2No,sqlField_Summation,definedItemNo));
                    }
                    value_List.add(value1_List);
                }
                //方气
                for (int i = 0; i < itemNoListNumber; i++) {
                    List<Double> value1_List = new ArrayList<>();
                    String itemNo = ItemNo_List.get(i);
                    for (String sp2No : sp2List) {
                        //获取相应的金额数
                        double perMoney = (double) Math.round(calculateData(getMoney(selectCompany, sp2No, sqlField_Summation, itemNo), getProduction(selectCompany, sp2No, sqlFiled_Production, itemNo)) * 100) / 100;
                        value1_List.add(perMoney);
                    }
                    value_List.add(value1_List);
                }
                //方气（自定义字段）
                for (int i = 0; i < definedItemNoListNumber; i++) {
                    List<Double> value1_List = new ArrayList<>();
                    String definedItemNo = DefinedItemNo_List.get(i);
                    for (String sp2No : sp2List) {
                        //获取相应的金额数
                        double perMoney = (double) Math.round(calculateData(getMoney_defined(selectCompany, sp2No, sqlField_Summation, definedItemNo), getProduction_defined(selectCompany, sp2No, sqlFiled_Production, definedItemNo)) * 100) / 100;
                        value1_List.add(perMoney);
                    }
                    value_List.add(value1_List);
                }
            } else {
                for (int i = 0; i < itemNoListNumber; i++) {
                    List<Double> value1_List = new ArrayList<>();
                    String itemNo = ItemNo_List.get(i);
                    value1_List.add(getMoney(selectCompany, selectProduct, sqlField_Summation, itemNo));
                    value_List.add(value1_List);
                }
                //自定义字段
                for (int i = 0; i < definedItemNoListNumber; i++) {
                    List<Double> value1_List = new ArrayList<>();
                    String definedItemNo = DefinedItemNo_List.get(i);
                    value1_List.add(getMoney_defined(selectCompany,selectProduct,sqlField_Summation,definedItemNo));
                    value_List.add(value1_List);
                }
                //方气
                for (int i = 0; i < itemNoListNumber; i++) {
                    List<Double> value1_List = new ArrayList<>();
                    String itemNo = ItemNo_List.get(i);
                    //获取相应的金额数
                    double perMoney = (double) Math.round(calculateData(getMoney(selectCompany, selectProduct, sqlField_Summation, itemNo), getProduction(selectCompany, selectProduct, sqlFiled_Production, itemNo)) * 100) / 100;
                    value1_List.add(perMoney);
                    value_List.add(value1_List);
                }
                //方气（自定义字段）
                for (int i = 0; i < definedItemNoListNumber; i++) {
                    List<Double> value1_List = new ArrayList<>();
                    String definedItemNo = DefinedItemNo_List.get(i);
                    double perMoney = (double) Math.round(calculateData(getMoney_defined(selectCompany, selectProduct, sqlField_Summation, definedItemNo), getProduction_defined(selectCompany, selectProduct, sqlFiled_Production, definedItemNo)) * 100) / 100;
                    value1_List.add(perMoney);
                    value_List.add(value1_List);
                }
            }
        }
        return Msg.success().add("BOE_value_List", value_List);//命名的前面是这个功能名的缩写
    }

    //获取报表数据
    public Msg getDatagridData(String selectCompany, String selectProduct, String selectTime) {
        String sqlField_Summation = "AnnualCumulative";
        String sqlFiled_Production = "AnnualCumulative";
        List<Map<String, Object>> value_List = new ArrayList<>();
        //获取普通科目
        List<String> ItemNo_List = getItemNo("1");
        int itemNoListNumber = ItemNo_List.size();
        //获取自定义字段
        List<String> DefinedItemNo_List = getItemNo("2");
        int definedItemNoListNumber = DefinedItemNo_List.size();

        //根据选择的时间不同，对sqlField_Summation(借方金额)，sqlFiled_Production（产量数量）进行赋值
        if (selectTime.equals("AnnualCumulative")) {
            sqlField_Summation = "debitMoneyAcm";//注意这里的annualCumulative是和前面的不一样
            sqlFiled_Production = "debitQtyAcm";
        } else {
            int len = selectTime.length();
            if (len == 1) {
                //selectTime = ("0" + selectTime.substring(len - 1, len + 1));     //转换月份的格式，如将“1”转成“01”
                selectTime = "0" + selectTime;
            }
            sqlFiled_Production = "debitQty" + selectTime;
            sqlField_Summation = "debitMoney" + selectTime;//注意，这个与之前的命名不同
        }

        if (selectCompany.equals("All")) {    //核算1栏选择全部
            if (selectProduct.equals("All")) {    //核算2栏也选择全部

            } else {
                List<String> sp1List = getSp1Id();//获取核算1的所有核算编号
                //普通科目
                for (int i = 0; i < itemNoListNumber; i++) {
                    String itemNo = ItemNo_List.get(i);
                    String itemName = getItemNamByItemNo("1", itemNo);
                    for (String sp1No : sp1List) {
                        //获取相应的金额数
                        double Money = getMoney(sp1No, selectProduct, sqlField_Summation, itemNo);
                        double perMoney = (double) Math.round(calculateData(getMoney(sp1No, selectProduct, sqlField_Summation, itemNo), getProduction(sp1No, selectProduct, sqlFiled_Production, itemNo)) * 100) / 100;
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("text", itemName);//科目名称
                        map.put("spNo1", sp1No);//核算1
                        map.put("spNo2", selectProduct);//核算2
                        map.put("itemMoney", Money);//科目金额
                        map.put("perItemMoney", perMoney);//方气成本
                        value_List.add(map);
                    }
                }
                //自定义字段
                for (int i = 0; i < definedItemNoListNumber; i++) {
                    String definedItemNo = DefinedItemNo_List.get(i);
                    String itemName = getItemNamByItemNo("2", definedItemNo);
                    for (String sp1No : sp1List) {
                        //获取相应的金额数
                        double Money = getMoney_defined(sp1No, selectProduct, sqlField_Summation, definedItemNo);
                        double perMoney = (double) Math.round(calculateData(getMoney_defined(sp1No, selectProduct, sqlField_Summation, definedItemNo), getProduction_defined(sp1No, selectProduct, sqlFiled_Production, definedItemNo)) * 100) / 100;
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("text", itemName);//科目名称
                        map.put("spNo1", sp1No);//核算1
                        map.put("spNo2", selectProduct);//核算2
                        map.put("itemMoney", Money);//科目金额
                        map.put("perItemMoney", perMoney);//方气成本
                        value_List.add(map);
                    }
                }
            }
        } else {
            if (selectProduct.equals("All")) {
                List<String> sp2List = getSp2Id();//获取核算1的所有核算编号
                //普通科目
                for (int i = 0; i < itemNoListNumber; i++) {
                    String itemNo = ItemNo_List.get(i);
                    String itemName = getItemNamByItemNo("1", itemNo);
                    for (String sp2No : sp2List) {
                        //获取相应的金额数
                        double Money = getMoney(selectCompany, sp2No, sqlField_Summation, itemNo);
                        double perMoney = (double) Math.round(calculateData(getMoney(selectCompany, sp2No, sqlField_Summation, itemNo), getProduction(selectCompany, sp2No, sqlFiled_Production, itemNo)) * 100) / 100;
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("text", itemName);//科目名称
                        map.put("spNo1", selectCompany);//核算1
                        map.put("spNo2", sp2No);//核算2
                        map.put("itemMoney", Money);//科目金额
                        map.put("perItemMoney", perMoney);//方气成本
                        value_List.add(map);
                    }
                }
                //自定义字段
                for (int i = 0; i < definedItemNoListNumber; i++) {
                    String definedItemNo = DefinedItemNo_List.get(i);
                    String itemName = getItemNamByItemNo("2", definedItemNo);
                    for (String sp2No : sp2List) {
                        //获取相应的金额数
                        double Money = getMoney_defined(selectCompany, sp2No, sqlField_Summation, definedItemNo);
                        double perMoney = (double) Math.round(calculateData(getMoney_defined(selectCompany, sp2No, sqlField_Summation, definedItemNo), getProduction_defined(selectCompany, sp2No, sqlFiled_Production, definedItemNo)) * 100) / 100;
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("text", itemName);//科目名称
                        map.put("spNo1", selectCompany);//核算1
                        map.put("spNo2", sp2No);//核算2
                        map.put("itemMoney", Money);//科目金额
                        map.put("perItemMoney", perMoney);//方气成本
                        value_List.add(map);
                    }
                }
            } else {
                //普通科目
                for (int i = 0; i < itemNoListNumber; i++) {
                    String itemNo = ItemNo_List.get(i);
                    String itemName = getItemNamByItemNo("1", itemNo);
                    double Money = getMoney(selectCompany, selectProduct, sqlField_Summation, itemNo);
                    double perMoney = (double) Math.round(calculateData(getMoney(selectCompany, selectProduct, sqlField_Summation, itemNo), getProduction(selectCompany, selectProduct, sqlFiled_Production, itemNo)) * 100) / 100;
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("text", itemName);//科目名称
                    map.put("spNo1", selectCompany);//核算1
                    map.put("spNo2", selectProduct);//核算2
                    map.put("itemMoney", Money);//科目金额
                    map.put("perItemMoney", perMoney);//方气成本
                    value_List.add(map);
                }
                //自定义字段
                for (int i = 0; i < definedItemNoListNumber; i++) {
                    String definedItemNo = DefinedItemNo_List.get(i);
                    String itemName = getItemNamByItemNo("2", definedItemNo);
                    double Money = getMoney_defined(selectCompany, selectProduct, sqlField_Summation, definedItemNo);
                    double perMoney = (double) Math.round(calculateData(getMoney_defined(selectCompany, selectProduct, sqlField_Summation, definedItemNo), getProduction_defined(selectCompany, selectProduct, sqlFiled_Production, definedItemNo)) * 100) / 100;
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("text", itemName);//科目名称
                    map.put("spNo1", selectCompany);//核算1
                    map.put("spNo2", selectProduct);//核算2
                    map.put("itemMoney", Money);//科目金额
                    map.put("perItemMoney", perMoney);//方气成本
                    value_List.add(map);
                }
            }
        }
        return Msg.success().add("BOE_datagridValue_List", value_List);//命名的前面是这个功能名的缩写
    }

    //根据核算1编号、核算2编号和科目编号从lshsje表中获取金额
    public double getMoney(String sp1No, String sp2No, String sqlField_Summation, String itemNo) {
        double money = 0;
        Session session1 = null;
        Transaction tx1 = null;
        try {
            session1 = getSession();
            tx1 = session1.beginTransaction();

            if (sp2No.equals("null")) {//这里要判断一下核算2是否为空，若是为空那么查询语句有所不同
                Query query_getMoney = session1.createQuery("select sum(" + sqlField_Summation + ") from Lshsje where spNo1 = :a and spNo2 is null and itemNo = :c");
                query_getMoney.setString("a", sp1No);
                query_getMoney.setString("c", itemNo);
                money = (double) query_getMoney.uniqueResult();
            } else {
                Query query_getMoney = session1.createQuery("select sum(" + sqlField_Summation + ") from Lshsje where spNo1 = :a and spNo2 = :b and itemNo = :c");
                query_getMoney.setString("a", sp1No);
                query_getMoney.setString("b", sp2No);
                query_getMoney.setString("c", itemNo);
                money = (double) query_getMoney.uniqueResult();
            }
        } catch (Exception e) {
            tx1.rollback();
            close(session1);
            e.printStackTrace();
            return 0.0;
        }
        tx1.commit();
        close(session1);
        return money;
    }

    //获取金额（自定义字段）
    public double getMoney_defined(String sp1No, String sp2No, String sqlField_Summation, String definedItemNo) {
        List<String> itemList_defined = getItemNoByDefined(definedItemNo);
        double moneyAll_defined = 0.0;
        if (itemList_defined != null) {
            for (String itemNo_defined : itemList_defined) {
                moneyAll_defined += getMoney(sp1No, sp2No, sqlField_Summation, itemNo_defined);
            }
        }
        return moneyAll_defined;
    }

    //通过核算1和核算2从lshssl表中获取产量
    public double getProduction(String sp1No, String sp2No, String sqlFiled_Production, String itemNo) {
        double production = 0;
        Session session1 = null;
        Transaction tx1 = null;
        try {
            session1 = getSession();
            tx1 = session1.beginTransaction();
            if (sp2No.equals("null")) {
                Query query_getProduction = session1.createQuery("select sum(" + sqlFiled_Production + ") from Lshssl where spNo1 = :a  and itemNo = :c and spNo2 is null or spNo1 = :a  and itemNo = :c and spNo2 = :b");
                query_getProduction.setString("a", sp1No);
                query_getProduction.setString("b", "");
                query_getProduction.setString("c", itemNo);
                production = (double) query_getProduction.uniqueResult();
            } else {
                Query query_getProduction = session1.createQuery("select sum(" + sqlFiled_Production + ") from Lshssl where spNo1 = :a and spNo2 = :b and itemNo = :c");
                query_getProduction.setString("a", sp1No);
                query_getProduction.setString("b", sp2No);
                query_getProduction.setString("c", itemNo);
                production = (double) query_getProduction.uniqueResult();
            }

            tx1.commit();
            close(session1);
            return production;
        } catch (Exception e) {
            tx1.rollback();
            close(session1);
            e.printStackTrace();
            return 0.0;
        }
    }

    //获取产量（自定义字段）
    public double getProduction_defined(String sp1No, String sp2No, String sqlFiled_Production, String definedItemNo) {
        List<String> itemList_defined = getItemNoByDefined(definedItemNo);
        double productionAll_defined = 0.0;
        if (itemList_defined != null) {
            for (String itemNo_defined : itemList_defined) {
                productionAll_defined += getProduction(sp1No, sp2No, sqlFiled_Production, itemNo_defined);
            }
        }
        return productionAll_defined;
    }

    //获取Cbtbsz里的graphData1或graphData2
    public String getGraphData(String dataType, String graphNo) {
        Session session1 = null;
        Transaction tx1 = null;
        String result = null;
        try {
            session1 = getSession();
            tx1 = session1.beginTransaction();

            if (dataType.equals("1")) {
                Query query_ItemNo1 = session1.createQuery("select graphData1 from Cbtbsz where graphNo = :a");
                query_ItemNo1.setString("a", graphNo);
                result = (String) query_ItemNo1.uniqueResult();
            } else {
                Query query_ItemNo1 = session1.createQuery("select graphData2 from Cbtbsz where graphNo = :a");
                query_ItemNo1.setString("a", graphNo);
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

    //获取核算编号1的所有核算科目编号
    public List<String> getSp1Id() {
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        Query query_getDepartmentCatNo = session.createQuery("select itemData1 from Cbqtsz where itemNo = :a");
        query_getDepartmentCatNo.setString("a", "000001");
        String Sp1CatNo = (String) query_getDepartmentCatNo.uniqueResult();

        Query query = session.createQuery("select spNo from Lshszd where catNo = :aaa and spLevel = :bbb");
        query.setString("aaa", Sp1CatNo);
        query.setString("bbb", "1");
        List<String> spNo1List = query.list();

        tx.commit();
        close(session);
        return spNo1List;
    }

    //获取核算编号2的所有核算科目编号
    public List<String> getSp2Id() {
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        Query query_getDepartmentCatNo = session.createQuery("select itemData1 from Cbqtsz where itemNo = :a");
        query_getDepartmentCatNo.setString("a", "000002");
        String Sp2CatNo = (String) query_getDepartmentCatNo.uniqueResult();

        Query query = session.createQuery("select spNo from Lshszd where catNo = :aaa and spLevel = :bbb");
        query.setString("aaa", Sp2CatNo);
        query.setString("bbb", "1");
        List<String> spNo2List = query.list();

        tx.commit();
        close(session);
        return spNo2List;
    }

    //获取图表中的科目编号(普通科目：1，自定义字段：2)
    public List<String> getItemNo(String itemType) {
        List<String> itemNoList = new ArrayList<>();

        //普通科目
        if (itemType.equals("1")) {
            String itemNolist1 = getGraphData("1", "000001");
            if (itemNolist1 != null && !itemNolist1.equals("")) {
                String[] split_itemNo = itemNolist1.split("\\;");
                //将数组的每个数据加进list
                for (String item1 : split_itemNo) {
                    itemNoList.add(item1);
                }
            }
        }
        //自定义字段
        if (itemType.equals("2")) {
            String itemNolist2 = getGraphData("2", "000001");
            if (itemNolist2 != null && !itemNolist2.equals("")) {
                String[] split_itemNo = itemNolist2.split("\\;");
                //将数组的每个数据加进list
                for (String item2 : split_itemNo) {
                    itemNoList.add(item2);
                }
            }
        }
        return itemNoList;
    }

    //根据自定义科目获取相应的科目编号
    public List<String> getItemNoByDefined(String DefinedItemNo) {
        Session session1 = null;
        Transaction tx1 = null;
        session1 = getSession();
        tx1 = session1.beginTransaction();
        List<String> itemNoList = new ArrayList<>();

        Query query = session1.createQuery("select itemData1 from Cbqtsz where itemNo = :a");
        query.setString("a", DefinedItemNo);
        String itemNo = (String) query.uniqueResult();
        if (itemNo != null) {
            String[] split_itemNo = itemNo.split("\\;");
            //将数组的每个数据加进list
            for (String item1 : split_itemNo) {
                itemNoList.add(item1);
            }
        }
        tx1.commit();
        close(session1);
        return itemNoList;
    }

    //判断两个数是否可以相除
    static double calculateData(double a, double b) {
        if (b != 0) {
            return a / b;
        } else {
            return 0;
        }
    }

    //根据科目编号获取科目的名称(普通科目：1; 自定义字段：2)
    public String getItemNamByItemNo(String itemType, String itemNo) {
        String itemName = null;
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        //普通科目
        if (itemType.equals("1")) {
            Query query = session.createQuery("select itemName from Lskmzd where itemNo = :a");
            query.setString("a", itemNo);
            itemName = (String) query.uniqueResult();
        }
        //自定义字段
        if (itemType.equals("2")) {
            Query query = session.createQuery("select itemName from Cbqtsz where itemNo = :a");
            query.setString("a", itemNo);
            itemName = (String) query.uniqueResult();
        }
        tx.commit();
        close(session);
        return itemName;
    }

    public Msg ExportExcel(String bodyData) {
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("成绩表");
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);
        //设置单元格内容
        cell.setCellValue("学员考试成绩一览表");
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
        //在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(1);
        //创建单元格并设置单元格内容
        row2.createCell(0).setCellValue("姓名");
        row2.createCell(1).setCellValue("班级");
        row2.createCell(2).setCellValue("笔试成绩");
        row2.createCell(3).setCellValue("机试成绩");
        //在sheet里创建第三行
        HSSFRow row3 = sheet.createRow(2);
        row3.createCell(0).setCellValue("李明");
        row3.createCell(1).setCellValue("As178");
        row3.createCell(2).setCellValue(87);
        row3.createCell(3).setCellValue(78);
        //.....省略部分代码

        //输出Excel文件
        FileOutputStream output = null;
        try {
            output = new FileOutputStream("d:\\workbook.xls");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            wb.write(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Msg.success().add("itemNam", "chenggong");
    }
}
