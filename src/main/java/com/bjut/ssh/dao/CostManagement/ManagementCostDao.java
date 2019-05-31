package com.bjut.ssh.dao.CostManagement;

import com.bjut.ssh.entity.Msg;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
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
public class ManagementCostDao {

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
        query.setString("a", "000005");
        String graphTheme = (String) query.uniqueResult();
        themeList.add(graphTheme);

        tx.commit();
        close(session);
        return Msg.success().add("ManagementCost_getEchartsTheme", themeList);
    }

    //根据选择的时间获取管理费用信息
    public Msg getValData(String selectTime) {
        String sqlField_Summation = "AnnualCumulative";
        List<List<String>> ManagementCost_List = new ArrayList<>();
        List<String> ManagementItem_List = new ArrayList<>();//今年的数据
        List<String> ManagementItemLast_List = new ArrayList<>();//去年的数据
        List<String> ManagementName_List = new ArrayList<>();
        //获取科目编号数据
        List<String> itemNoList = getItemNoList();
        //获取自定义字段数据
        List<String> definedItemNoList = getDefinedItemNoList();

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

        //普通科目
        if (itemNoList != null) {
            for (String item1 : itemNoList) {
                //今年数据
                List<Object[]> objects = getItemNoContent(item1, "now", sqlField_Summation);
                for (Object[] object : objects) {
                    ManagementName_List.add((String) object[0]);
                    if (object[1] == null) {      //查询数据为null
                        object[1] = 0.0;
                    }
                    Double itemindex = (double) object[1];
                    String itemIndex = itemindex.toString();
                    ManagementItem_List.add(itemIndex);
                }

                //去年数据
                List<Object[]> objects_last = getItemNoContent(item1, "lastYear", sqlField_Summation);
                for (Object[] object : objects_last) {
                    if (object[1] == null) {      //查询数据为null
                        object[1] = 0.0;
                    }
                    Double itemindex = (double) object[1];
                    String itemIndex = itemindex.toString();
                    ManagementItemLast_List.add(itemIndex);
                }
            }
        }

        //自定义字段
        if (definedItemNoList != null) {
            for (String definedItemNo : definedItemNoList) {
                String definedItemName = getItemNamByItemNo(definedItemNo, "自定义字段");
                ManagementName_List.add(definedItemName);
                //今年数据
                String definedItemNoMoneyNow = getDefinedItemNoMoney(definedItemNo, "now", sqlField_Summation);
                ManagementItem_List.add(definedItemNoMoneyNow);
                //去年数据
                String definedItemNoMoneyLast = getDefinedItemNoMoney(definedItemNo, "last", sqlField_Summation);
                ManagementItemLast_List.add(definedItemNoMoneyLast);
            }
        }

        //将数据从大到小排序
        sortManagementItem_List(ManagementItem_List, ManagementName_List, ManagementItemLast_List);

        ManagementCost_List.add(ManagementItem_List);
        ManagementCost_List.add(ManagementName_List);
        ManagementCost_List.add(ManagementItemLast_List);
        return Msg.success().add("MC_ManagementCost_List", ManagementCost_List);
    }

    //根据选择的时间获取管理费用信息
    public Msg getDatagridData(String selectTime) {
        String sqlField_Summation = "AnnualCumulative";
        List<Map<String, Object>> ManagementCost_List = new ArrayList<>();
        Double sum_managementCost = 0.0d;
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

            //从表CBTBSZ中获取生产人员薪酬
            Query query = session.createQuery("select graphData1 from Cbtbsz where graphNo = :a");
            query.setString("a", "000005");
            String itemNo = (String) query.uniqueResult();

            String[] split_itemNo = itemNo.split("\\;");
            //将数组的每个数据加进list
            for (String item1 : split_itemNo) {
                //获取生产折旧项目的名称、编号以及编号对应的费用
                Query query_productionCost = session.createQuery("select itemName," + sqlField_Summation + " from Lskmzd where item = :aaa and itemNo = :bbb");
                query_productionCost.setString("aaa", "2");
                query_productionCost.setString("bbb", item1);
                List<Object[]> list_productionCost = query_productionCost.list();

                for (Object[] object : list_productionCost) {
                    if (object[1] == null) {      //查询数据为null
                        object[1] = 0.0;
                    }
                    Double itemindex = (double) object[1];
                    String itemIndex = itemindex.toString();
                    sum_managementCost += itemindex;
                    Map<String, Object> map2 = new HashMap<String, Object>();
                    map2.put("text", (String) object[0]);
                    map2.put("managementCost", itemIndex);
                    ManagementCost_List.add(map2);
                }
            }

            //-----------------------最后合计的部分--------------------------------------
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("text", "合计");
            map1.put("managementCost", sum_managementCost);
            ManagementCost_List.add(map1);
            return Msg.success().add("MC_datagridValue_List", ManagementCost_List);
        } catch (Exception e) {
            tx.rollback();
            close(session);
            e.printStackTrace();
        }

        return Msg.fail().add("error", "111");
    }

    //从表CBTBSZ获取科目编号列表
    public List<String> getItemNoList() {
        List<String> ItemNoList = new ArrayList<>();//返回值
        Session session = null;
        Transaction tx = null;

        try {
            session = getSession();
            tx = session.beginTransaction();
            //从表CBTBSZ中获取生产人员薪酬
            Query query = session.createQuery("select graphData1 from Cbtbsz where graphNo = :a");
            query.setString("a", "000005");
            String itemNo = (String) query.uniqueResult();
            if (itemNo != null) {
                String[] split_itemNo = itemNo.split("\\;");
                for (String itemNo1 : split_itemNo) {
                    ItemNoList.add(itemNo1);
                }
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        close(session);
        return ItemNoList;
    }

    //从表CBTBSZ获取自定义字段列表
    public List<String> getDefinedItemNoList() {
        List<String> DefinedItemNoList = new ArrayList<>();//返回值
        Session session = null;
        Transaction tx = null;

        try {
            session = getSession();
            tx = session.beginTransaction();
            //从表CBTBSZ中获取生产人员薪酬
            Query query = session.createQuery("select graphData2 from Cbtbsz where graphNo = :a");
            query.setString("a", "000005");
            String itemNo = (String) query.uniqueResult();
            if (itemNo != null) {
                String[] split_itemNo = itemNo.split("\\;");
                for (String itemNo1 : split_itemNo) {
                    DefinedItemNoList.add(itemNo1);
                }
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        close(session);
        return DefinedItemNoList;
    }

    //根据科目编号从表LSKMZD中获取编号名称和对应的金额
    public List<Object[]> getItemNoContent(String itemNo, String year, String sqlField_Summation) {
        Session session = null;
        Transaction tx = null;
        List<Object[]> ItemNoContent = new ArrayList<>();   //返回值

        try {
            session = getSession();
            tx = session.beginTransaction();

            //判断如果年份是今年
            if (year.equals("now")) {
                Query query = session.createQuery("select itemName," + sqlField_Summation + " from Lskmzd where itemNo = :bbb");
                query.setString("bbb", itemNo);
                ItemNoContent = query.list();
                //判断如果年份是去年
            } else {
                Calendar cal = Calendar.getInstance();
                int yearNow = cal.get(Calendar.YEAR) - 1;
                String FormName = "Lskmzd" + yearNow;
                String sql = "select itemName," + sqlField_Summation + " from " + FormName + " where itemNo = ?";
                Query query = session.createSQLQuery(sql);
                query.setString(0, itemNo);
                ItemNoContent = query.list();
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        close(session);
        return ItemNoContent;
    }

    //根据自定义字段从表LSKMZD中获取对应的金额（自定义中所有科目对应的总和）
    public String getDefinedItemNoMoney(String itemDefinedNo, String year, String sqlField_Summation) {
        String DefinedItemNoMoney = null;   //返回值(包含今年金额和去年金额)
        Double moneyNow = 0.0;
        Double moneyLast = 0.0;
        Session session = null;
        Transaction tx = null;

        try {
            session = getSession();
            tx = session.beginTransaction();

            List<String> itemList = getItemNoByDefined(itemDefinedNo);
            //判断如果年份是今年
            if (year.equals("now")) {
                for (String itemNo : itemList) {
                    Query query = session.createQuery("select " + sqlField_Summation + " from Lskmzd where itemNo = :bbb");
                    query.setString("bbb", itemNo);
                    if (query.uniqueResult() != null) {
                        moneyNow += (double) query.uniqueResult();
                    }
                }
                DefinedItemNoMoney = moneyNow.toString();
                //判断如果年份是去年
            } else {
                Calendar cal = Calendar.getInstance();
                int yearLast = cal.get(Calendar.YEAR) - 1;
                String FormName = "Lskmzd" + yearLast;
                for (String itemNo : itemList) {
                    String sql = "select " + sqlField_Summation + " from " + FormName + " where itemNo = ?";
                    Query query = session.createSQLQuery(sql);
                    query.setString(0, itemNo);
                    if (query.uniqueResult() != null) {
                        moneyLast += (double) query.uniqueResult();
                    }
                }
                DefinedItemNoMoney = moneyLast.toString();
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        close(session);
        return DefinedItemNoMoney;
    }

    //根据自定义科目获取相应的科目编号
    public List<String> getItemNoByDefined(String DefinedItemNo) {
        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();

        List<String> itemNoList = new ArrayList<>();

        Query query = session.createQuery("select itemData1 from Cbqtsz where itemNo = :a");
        query.setString("a", DefinedItemNo);
        String itemNo = (String) query.uniqueResult();

        String[] split_itemNo = itemNo.split("\\;");
        //将数组的每个数据加进list
        for (String item1 : split_itemNo) {
            itemNoList.add(item1);
        }

        System.out.println(itemNoList);
        tx.commit();
        close(session);
        return itemNoList;
    }

    //根据科目编号获取科目的名称(普通科目和自定义字段都可以使用)
    public String getItemNamByItemNo(String itemNo, String itemType) {
        String itemName = null;

        Session session = null;
        Transaction tx = null;
        session = getSession();
        tx = session.beginTransaction();
        if (itemType.equals("科目编号")) {
            Query query = session.createQuery("select itemName from Lskmzd where itemNo = :a");
            query.setString("a", itemNo);
            itemName = (String) query.uniqueResult();
        }
        if (itemType.equals("自定义字段")) {
            Query query = session.createQuery("select itemName from Cbqtsz where itemNo = :a");
            query.setString("a", itemNo);
            itemName = (String) query.uniqueResult();
        }

        tx.commit();
        close(session);
        return itemName;
    }

    //排序(从大到小)
    public void sortManagementItem_List(List<String> ManagementItem_List, List<String> ManagementName_List, List<String> ManagementItemLast_List) {
        for (int j = 0; j < ManagementItem_List.size(); j++) {
            Double itemMax = 0.0;
            int itemMax_place = 0;
            //找到最大值和位置
            for (int i = j; i < ManagementItem_List.size(); i++) {
                String item = ManagementItem_List.get(i);
                if (Double.parseDouble(item) >= itemMax) {
                    itemMax = Double.parseDouble(item);
                    itemMax_place = i;
                }
            }

            //将最大值添加到list的最前面
            ManagementItem_List.add(j, itemMax.toString());
            //把原来的数据删除
            ManagementItem_List.remove(itemMax_place + 1);

            //将ManagementName_List和ManagementItemLast_List对应的调整顺序
            String itemName = ManagementName_List.get(itemMax_place);
            String itemLast = ManagementItemLast_List.get(itemMax_place);
            ManagementName_List.add(j, itemName);
            ManagementName_List.remove(itemMax_place + 1);
            ManagementItemLast_List.add(j, itemLast);
            ManagementItemLast_List.remove(itemMax_place + 1);
        }

    }

}
