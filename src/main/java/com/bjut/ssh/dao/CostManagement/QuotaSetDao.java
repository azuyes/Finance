package com.bjut.ssh.dao.CostManagement;

//import antlr.StringUtils;

import com.bjut.ssh.entity.Msg;
import org.apache.commons.lang3.StringUtils;
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
 * @Title: QuotaSetDao
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/8/18 13:55
 * @Version: 1.0
 */

@Repository
@Transactional
public class QuotaSetDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        return this.sessionFactory.openSession();
    }

    public void close(Session session) {
        if (session != null)
            session.close();
    }

    //获取所有图表的信息
    public Msg getGraphAll() {
        Session session = null;
        Transaction tx = null;
        //List<List<Map<String,Object>>> list_all = new ArrayList<>();
        List<Map<String, Object>> list1 = new ArrayList<>();
        session = getSession();
        tx = session.beginTransaction();

        Query query = session.createQuery("select graphNo,graphName from Cbtbsz ");
        List<Object[]> list = query.list();
        for (Object[] object : list) {
            String graphNumber = (String) object[0];
            String graphName = (String) object[1];
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ChartID", graphNumber);//图表编号
            map.put("ChartName", graphName);//图表名称
            list1.add(map);
        }

        System.out.println(list1);
        if (list1.size() > 0) {
            tx.commit();
            close(session);
            return Msg.success().add("graphAll", list1);
        } else {
            tx.commit();
            close(session);
            return Msg.fail().add("errorInfo", "编号重复，请重新输入");
        }
    }

    //获取指定图表的信息
    public Msg getGraphInfo(String gridNo) {
        Session session = null;
        Transaction tx = null;
        //List<List<Map<String,Object>>> list_all = new ArrayList<>();
        List<Map<String, Object>> list1 = new ArrayList<>();
        String itemNo;//项目编号
        String itemName = null;//项目名称
        String itemType;//项目数据类型
        String itemFunction;//项目功能
        int setNo = 0;//记录图表数据的有效个数

        session = getSession();
        tx = session.beginTransaction();

        //获取图表数据的有效个数
        Query query_setNo = session.createQuery("select setNo from Cbtbsz where graphNo = :a");
        query_setNo.setString("a", gridNo);
        setNo = (int) query_setNo.uniqueResult();

        for (int i = 0; i < setNo; i++) {
            int index = i + 1;
            String graphData = "graphData" + index;
            String graphDataType = "dataType" + index;
            String graphDataFunction = "dataFunction" + index;
            System.out.println(graphData);
            //根据图表编号查询图表数据
            Query query_itemNo = session.createQuery("select " + graphData + " from Cbtbsz where graphNo = :a");
            query_itemNo.setString("a", gridNo);
            //query_itemNo.setString("b", graphData);
            itemNo = (String) query_itemNo.uniqueResult();

            if (itemNo != null) {
                //获取每个项目编号
                String[] split_itemNo = itemNo.split("\\;");

                //根据图表编号查询图表数据类型
                Query query_itemType = session.createQuery("select " + graphDataType + " from Cbtbsz where graphNo = :a");
                query_itemType.setString("a", gridNo);
                //query_itemType.setString("b", graphDataType);
                itemType = (String) query_itemType.uniqueResult();
                //根据图表编号查询图表数据备注
                Query query_itemFunction = session.createQuery("select " + graphDataFunction + " from Cbtbsz where graphNo = :a");
                query_itemFunction.setString("a", gridNo);
                //query_itemType.setString("b", graphDataType);
                itemFunction = (String) query_itemFunction.uniqueResult();

                for (int a = 0; a < split_itemNo.length; a++) {
                    if (itemType.indexOf("科目") != -1) {
                        Query query_itemName = session.createQuery("select itemName from Lskmzd where itemNo = :a");
                        query_itemName.setString("a", split_itemNo[a]);
                        itemName = (String) query_itemName.uniqueResult();
                    } else if (itemType.equals("核算编号")) {
                        Query query_itemName = session.createQuery("select spName from Lshszd where spNo = :a");
                        query_itemName.setString("a", split_itemNo[a]);
                        itemName = (String) query_itemName.uniqueResult();
                    } else if (itemType.equals("自定义字段")) {
                        Query query_itemName = session.createQuery("select itemName from Cbqtsz where itemNo = :a");
                        query_itemName.setString("a", split_itemNo[a]);
                        itemName = (String) query_itemName.uniqueResult();
                    }
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("ItemID", split_itemNo[a]);//项目编号
                    map.put("ItemName", itemName);//项目名称
                    map.put("ItemType", itemType);//项目类型
                    map.put("ItemFunction", itemFunction);//项目备注
                    list1.add(map);
                }
            }
        }

//        List<Object[]> list = query.list();
//        for(Object[] object : list){
//
//            Map<String,Object> map = new HashMap<String, Object>();
//            map.put("ChartID",graphNumber);//图表编号
//            map.put("ChartName",graphName);//图表名称
//            list1.add(map);
//        }
        System.out.println(list1);
        if (list1.size() > 0) {
            tx.commit();
            close(session);
            return Msg.success().add("graphInfo", list1);
        } else {
            tx.commit();
            close(session);
            return Msg.fail().add("errorInfo", "编号重复，请重新输入");
        }
    }

    //删除图表的指定数据
    public Msg deleteGraphInfo(String gridNo, String ItemNo, String ItemType) {
        Session session = null;
        Transaction tx = null;

        List<Map<String, Object>> list1 = new ArrayList<>();
        String itemType;
        String itemNo;
        int setNo = 0;//记录图表数据的有效个数

        session = getSession();
        tx = session.beginTransaction();

        //获取图表数据的有效个数
        Query query_setNo = session.createQuery("select setNo from Cbtbsz where graphNo = :a");
        query_setNo.setString("a", gridNo);
        setNo = (int) query_setNo.uniqueResult();

        for (int i = 0; i < setNo; i++) {
            int index = i + 1;
            String graphData = "graphData" + index;
            String graphDataType = "dataType" + index;
            //System.out.println(graphData);

            //根据图表编号查询图表数据类型
            Query query_itemType = session.createQuery("select " + graphDataType + " from Cbtbsz where graphNo = :a");
            query_itemType.setString("a", gridNo);
            //query_itemType.setString("b", graphDataType);
            itemType = (String) query_itemType.uniqueResult();

            //找到该删除的数据属于哪个数据类型
            if (itemType.equals(ItemType)) {
                //根据图表编号查询图表数据
                Query query_itemNo = session.createQuery("select " + graphData + " from Cbtbsz where graphNo = :a");
                query_itemNo.setString("a", gridNo);
                //query_itemNo.setString("b", graphData);
                itemNo = (String) query_itemNo.uniqueResult();

                //获取每个项目编号
                String[] split_itemNo = itemNo.split("\\;");
                System.out.println(split_itemNo);

                int item_index = 0;//记录删除的是第几个
                //找到删除项目的位置
                for (String item1 : split_itemNo) {
                    if (item1.equals(ItemNo)) {
                        break;
                    }
                    item_index++;
                }

                //借用新的数组split_itemNo_new，将元素删除
                String[] split_itemNo_new = new String[split_itemNo.length - 1];
                for (int i1 = 0; i1 < split_itemNo.length - 1; i1++) {
                    if (i1 < item_index) {
                        split_itemNo_new[i1] = split_itemNo[i1];
                    } else {
                        split_itemNo_new[i1] = split_itemNo[i1 + 1];
                    }
                }
                split_itemNo = split_itemNo_new;
                System.out.println(split_itemNo);
                //将新的数组存回数据库中
                String itemNo_join = StringUtils.join(split_itemNo, ";");
                System.out.println(itemNo_join);
                //根据图表编号更新数据
                //Query query_deleteItemNo = session.createQuery("update Cbtbsz set  " + graphData + " = " + itemNo_join + " where graphNo = :a");
                Query query_deleteItemNo = session.createQuery("update Cbtbsz set " + graphData + " =:b where graphNo = :a");
                query_deleteItemNo.setString("a", gridNo);
                query_deleteItemNo.setString("b", itemNo_join);
                query_deleteItemNo.executeUpdate();

                tx.commit();
                close(session);
                return Msg.success().add("graphInfo", list1);
            }
        }

        tx.commit();
        close(session);
        return Msg.fail().add("errorInfo", "编号重复，请重新输入");
    }

    //添加数据到图表中
    public Msg addGraphInfo(String gridNo, String ItemNo, String ItemType) {
        Session session = null;
        Transaction tx = null;

        List<Map<String, Object>> list1 = new ArrayList<>();
        String itemType;
        String itemNo;
        int setNo = 0;//记录图表数据的有效个数

        session = getSession();
        tx = session.beginTransaction();

        //获取图表数据的有效个数
        Query query_setNo = session.createQuery("select setNo from Cbtbsz where graphNo = :a");
        query_setNo.setString("a", gridNo);
        setNo = (int) query_setNo.uniqueResult();

        for (int i = 0; i < setNo; i++) {
            int index = i + 1;
            String graphData = "graphData" + index;
            String graphDataType = "dataType" + index;
            //System.out.println(graphData);

            //根据图表编号查询图表数据类型
            Query query_itemType = session.createQuery("select " + graphDataType + " from Cbtbsz where graphNo = :a");
            query_itemType.setString("a", gridNo);
            //query_itemType.setString("b", graphDataType);
            itemType = (String) query_itemType.uniqueResult();

            //找到该添加的数据属于哪个数据类型
            if (itemType.equals(ItemType)) {
                //根据图表编号查询图表数据
                Query query_itemNo = session.createQuery("select " + graphData + " from Cbtbsz where graphNo = :a");
                query_itemNo.setString("a", gridNo);
                itemNo = (String) query_itemNo.uniqueResult();

                //获取每个项目编号
                String[] split_itemNo = itemNo.split("\\;");
                System.out.println(split_itemNo);

                //检查该编号是否存在在之前的数据库之中
                for (String itemNo1 : split_itemNo) {
                    if (itemNo1.equals(ItemNo)) {
                        return Msg.success().add("addgraphInfo", "编号重复");
                    }
                }

                int itemNo_length = split_itemNo.length;//老数组的长度
                //借用新的数组split_itemNo_new，将元素添加进去
                String[] split_itemNo_new = new String[split_itemNo.length + 1];
                for (int i1 = 0; i1 < split_itemNo.length; i1++) {
                    split_itemNo_new[i1] = split_itemNo[i1];
                }
                split_itemNo_new[itemNo_length] = ItemNo;//将新的值赋给数组

                System.out.println(split_itemNo_new);
                //将新的数组存回数据库中
                String itemNo_join = StringUtils.join(split_itemNo_new, ";");
                System.out.println(itemNo_join);
                //根据图表编号更新数据
                //Query query_deleteItemNo = session.createQuery("update Cbtbsz set  " + graphData + " = " + itemNo_join + " where graphNo = :a");
                Query query_deleteItemNo = session.createQuery("update Cbtbsz set  " + graphData + " =:b where graphNo = :a");
                query_deleteItemNo.setString("a", gridNo);
                query_deleteItemNo.setString("b", itemNo_join);
                query_deleteItemNo.executeUpdate();

                tx.commit();
                close(session);
                return Msg.success().add("addgraphInfo", "成功");
            }
        }

        tx.commit();
        close(session);
        return Msg.fail().add("errorInfo", "编号重复，请重新输入");
    }

    //项目功能下拉菜单，获取指定图表的数据功能信息
    public Msg getItemFunctionSelect(String gridNo) {
        Session session = null;
        Transaction tx = null;
        //List<List<Map<String,Object>>> list_all = new ArrayList<>();
        List<Map<String, Object>> list1 = new ArrayList<>();
        String itemNo;//项目编号
        String itemName;//项目名称
        String itemFunction;//项目数据功能
        List<String> itemFunctionList = new ArrayList<>();
        int setNo = 0;//记录图表数据的有效个数

        session = getSession();
        tx = session.beginTransaction();

        //获取图表数据的有效个数
        Query query_setNo = session.createQuery("select setNo from Cbtbsz where graphNo = :a");
        query_setNo.setString("a", gridNo);
        setNo = (int) query_setNo.uniqueResult();

        for (int i = 0; i < setNo; i++) {
            int index = i + 1;
            int itemFunctionEqual = 0;//判断是否和之前的项目功能相同，0为不同，1为相同
            String graphDataFunction = "dataFunction" + index;

            //根据图表编号查询图表数据类型
            Query query_itemFunction = session.createQuery("select " + graphDataFunction + " from Cbtbsz where graphNo = :a");
            query_itemFunction.setString("a", gridNo);
            //query_itemType.setString("b", graphDataType);
            itemFunction = (String) query_itemFunction.uniqueResult();
            for (String itemfunction : itemFunctionList) {
                if (itemfunction.equals(itemFunction)) {
                    itemFunctionEqual = 1;
                }
            }
            if (itemFunctionEqual == 0) {
                itemFunctionList.add(itemFunction);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("0", itemFunction);//项目功能名称
                map.put("1", i + 1);//编号
                list1.add(map);
            }
        }

        System.out.println(list1);
        if (list1.size() > 0) {
            tx.commit();
            close(session);
            return Msg.success().add("itemFunctionSelect", list1);
        } else {
            tx.commit();
            close(session);
            return Msg.fail().add("errorInfo", "编号重复，请重新输入");
        }
    }

    //根据项目功能获取项目类型数据
    public Msg getItemTypeSelect(String gridNo, String selectItemFunctionValue) {
        Session session = null;
        Transaction tx = null;
        //List<List<Map<String,Object>>> list_all = new ArrayList<>();
        List<Map<String, Object>> list1 = new ArrayList<>();
        String itemNo;//项目编号
        String itemFunction;//项目数据功能
        String itemType;//项目数据类型
        int setNo = 0;//记录图表数据的有效个数

        session = getSession();
        tx = session.beginTransaction();

        //获取图表数据的有效个数
        Query query_setNo = session.createQuery("select setNo from Cbtbsz where graphNo = :a");
        query_setNo.setString("a", gridNo);
        setNo = (int) query_setNo.uniqueResult();

        for (int i = 0; i < setNo; i++) {
            int index = i + 1;
            String graphDataFunction = "dataFunction" + index;
            String graphDataType = "dataType" + index;

            //根据图表编号查询图表数据功能
            Query query_itemFunction = session.createQuery("select " + graphDataFunction + " from Cbtbsz where graphNo = :a");
            query_itemFunction.setString("a", gridNo);
            //query_itemType.setString("b", graphDataType);
            itemFunction = (String) query_itemFunction.uniqueResult();

            if (itemFunction.equals(selectItemFunctionValue)) {
                //根据图表编号查询图表数据类型
                Query query_itemType = session.createQuery("select " + graphDataType + " from Cbtbsz where graphNo = :a");
                query_itemType.setString("a", gridNo);
                //query_itemType.setString("b", graphDataType);
                itemType = (String) query_itemType.uniqueResult();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("0", itemType);//核算名称
                map.put("1", itemType);//核算编号
                list1.add(map);
            }
        }
        tx.commit();
        close(session);
        return Msg.success().add("itemTypeSelect", list1);
    }

    //项目名称下拉菜单，获取指定图表的所有项目名称信息
    public Msg getItemNameSelect(String selectItemTypeValue) {
        Session session = null;
        Transaction tx = null;
        //List<List<Map<String,Object>>> list_all = new ArrayList<>();
        List<Map<String, Object>> list1 = new ArrayList<>();

        session = getSession();
        tx = session.beginTransaction();

        if (selectItemTypeValue.equals("核算编号")) {
            Query query_getItemName = session.createQuery("select spNo,spName from Lshszd where catNo = :a");
            query_getItemName.setString("a", "03");
            List<Object[]> list_itemName = query_getItemName.list();
            for (Object[] object : list_itemName) {
                String itemNo1 = (String) object[0];
                String itemName1 = (String) object[1];
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("0", itemName1);//图表名称，放到显示text下
                map.put("1", itemNo1);//图表编号，放到value里面
                list1.add(map);
            }
        } else if (selectItemTypeValue.equals("科目编号")) {
            Query query_getItemName = session.createQuery("select itemNo,itemName from Lskmzd ");
            List<Object[]> list_itemName = query_getItemName.list();
            for (Object[] object : list_itemName) {
                String itemNo1 = (String) object[0];
                String itemName1 = (String) object[1];
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("0", itemName1);//图表名称，放到显示text下
                map.put("1", itemNo1);//图表编号，放到value里面
                list1.add(map);
            }
        } else if (selectItemTypeValue.equals("自定义字段")) {
            Query query_getItemName = session.createQuery("select itemNo,itemName from Cbqtsz where itemType = :a");
            query_getItemName.setString("a", "1");
            List<Object[]> list_itemName = query_getItemName.list();
            for (Object[] object : list_itemName) {
                String itemNo1 = (String) object[0];
                String itemName1 = (String) object[1];
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("0", itemName1);//图表名称，放到显示text下
                map.put("1", itemNo1);//图表编号，放到value里面
                list1.add(map);
            }
        }
        System.out.println(list1);
        if (list1.size() > 0) {
            tx.commit();
            close(session);
            return Msg.success().add("itemNameSelect", list1);
        } else {
            tx.commit();
            close(session);
            return Msg.fail().add("errorInfo", "编号重复，请重新输入");
        }
    }

    //根据项目名称获取项目编号数据
    public Msg getItemNoSelect(String gridNo, String selectItemNameValue) {
        Session session = null;
        Transaction tx = null;
        //List<List<Map<String,Object>>> list_all = new ArrayList<>();
        List<Map<String, Object>> list1 = new ArrayList<>();
        String itemNo;//项目编号
        String itemFunction;//项目数据功能
        String itemType;//项目数据类型
        int setNo = 0;//记录图表数据的有效个数

        session = getSession();
        tx = session.beginTransaction();

        //获取图表数据的有效个数
        Query query_setNo = session.createQuery("select setNo from Cbtbsz where graphNo = :a");
        query_setNo.setString("a", gridNo);
        setNo = (int) query_setNo.uniqueResult();

        for (int i = 0; i < setNo; i++) {
            int index = i + 1;
            String graphDataFunction = "dataFunction" + index;
            String graphDataType = "dataType" + index;

            //根据图表编号查询图表数据功能
            Query query_itemFunction = session.createQuery("select " + graphDataFunction + " from Cbtbsz where graphNo = :a");
            query_itemFunction.setString("a", gridNo);
            //query_itemType.setString("b", graphDataType);
            itemFunction = (String) query_itemFunction.uniqueResult();

            if (itemFunction.equals(selectItemNameValue)) {
                //根据图表编号查询图表数据类型
                Query query_itemType = session.createQuery("select " + graphDataType + " from Cbtbsz where graphNo = :a");
                query_itemType.setString("a", gridNo);
                //query_itemType.setString("b", graphDataType);
                itemType = (String) query_itemType.uniqueResult();
                System.out.println(itemType);

                tx.commit();
                close(session);
                return Msg.success().add("itemTypeSelect", itemType);
            }
        }

        tx.commit();
        close(session);
        return Msg.fail().add("errorInfo", "编号重复，请重新输入");
    }

}
