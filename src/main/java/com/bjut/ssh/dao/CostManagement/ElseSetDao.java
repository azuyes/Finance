package com.bjut.ssh.dao.CostManagement;

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
public class ElseSetDao {
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    public void close(Session session){
        if(session != null)
            session.close();
    }

    //其他设置，初始化界面
    public Msg getInitGrid(){
        Session session = null;
        Transaction tx = null;
        //List<List<Map<String,Object>>> list_all = new ArrayList<>();
        List<Map<String,Object>> list1 = new ArrayList<>();
        session = getSession();
        tx = session.beginTransaction();

        Map<String,Object> map1 = new HashMap<String, Object>();
        map1.put("SetName","自定义字段");//图表编号
        list1.add(map1);
        Map<String,Object> map2 = new HashMap<String, Object>();
        map2.put("SetName","字段关联");
        list1.add(map2);
        Map<String,Object> map3 = new HashMap<String, Object>();
        map3.put("SetName","显示设置");
        list1.add(map3);

        System.out.println(list1);
        if(list1.size()>0) {
            tx.commit();
            close(session);
            return Msg.success().add("InitGrid",list1);
        }
        else {
            tx.commit();
            close(session);
            return Msg.fail().add("errorInfo","编号重复，请重新输入");
        }
    }

    //-------------------(自定义字段界面)----------------------------------------------------------
    //获取自定义表格的信息(自定义字段界面)
    public Msg getDefinedItemGridInfo(){
        Session session = null;
        Transaction tx = null;
        List<Map<String,Object>> list1 = new ArrayList<>();
        List<String> itemNameList = new ArrayList<>();
        List<String> itemNoList = new ArrayList<>();//项目编号

        session = getSession();
        tx = session.beginTransaction();

        Query query_getname = session.createQuery("select itemName from Cbqtsz where itemType = :a or itemType = :b");
        query_getname.setString("a","1");
        query_getname.setString("b","3");
        itemNameList = query_getname.list();
        Query query_getno = session.createQuery("select itemNo from Cbqtsz where itemType = :a or itemType = :b");
        query_getno.setString("a","1");
        query_getno.setString("b","3");
        itemNoList = query_getno.list();

        int itemNum = 0;
        for(String itemName : itemNameList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ItemName", itemName);//项目名称
            map.put("ItemID", itemNoList.get(itemNum));//项目编号
            list1.add(map);
            itemNum++;
        }

        tx.commit();
        close(session);
        return Msg.success().add("definedItemGridInfo",list1);
    }

    //将新的自定义数据添加进数据库(自定义字段界面)
    public Msg addDefinedItemInfo(String ItemNo, String ItemName){
        Session session = null;
        Transaction tx = null;

        List<Map<String,Object>> list1 = new ArrayList<>();//传回前端数据
        List<String> itemNo_list = new ArrayList<>();//已有项目编号
        String itemType;
        String itemNo;
        String itemFunction;
        int setNo = 0;//记录图表数据的有效个数

        session = getSession();
        tx = session.beginTransaction();

        //获取已有的自定义项目编号，查找确认是否为重复的编号
        Query query_getItemNo = session.createQuery("select itemNo from Cbqtsz ");
        itemNo_list = query_getItemNo.list();

        for(String itemNoUsed : itemNo_list){
            if(itemNoUsed.equals(ItemNo)){
                tx.commit();
                close(session);
                return Msg.success().add("addDefinedItemInfo","编号重复");
            }
        }

        String sql = "insert into Cbqtsz (itemNo,itemName,ItemType) values (?,?,?)";
        Query query_insertItem = session.createSQLQuery(sql);
        query_insertItem.setString(1,ItemNo);
        query_insertItem.setString(2,ItemName);
        query_insertItem.setString(3,"1");
        query_insertItem.executeUpdate();

        tx.commit();
        close(session);
        return Msg.success().add("addDefinedItemInfo","成功");
    }

    //删除图表的指定数据(自定义字段界面)
    public Msg deleteDefinedItemInfo(String ItemNo){
        Session session = null;
        Transaction tx = null;

        List<Map<String,Object>> list1 = new ArrayList<>();
        String itemType;
        String itemNo;
        int setNo = 0;//记录图表数据的有效个数

        session = getSession();
        tx = session.beginTransaction();

        //获取图表数据的有效个数
        //String sql = "delete "
        Query query_setNo = session.createQuery("delete from Cbqtsz where itemNo = :a");
        query_setNo.setString("a", ItemNo);
        query_setNo.executeUpdate();

        tx.commit();
        close(session);
        return Msg.success().add("deleteDefinedItemInfo","删除成功");
    }

    //--------------------(自定义字段修改)-----------------------------------------------------------------
    //获取自定义项目具体包含哪些项目的信息(自定义字段修改)
    public Msg getDefinedItemInfo(String itemNo){
        Session session = null;
        Transaction tx = null;
        List<Map<String,Object>> list1 = new ArrayList<>();
        String itemName_list = null;
        String itemNo_string = null;
        String itemType_string = null;
        List<String> itemNameList = new ArrayList<>();
        List<String> itemNoList = new ArrayList<>();//项目编号

        session = getSession();
        tx = session.beginTransaction();

        //从表CBQTSZ中获取数据1的所有内容---------------------------------------
        Query query_getdataType = session.createQuery("select itemDataType1 from Cbqtsz where itemType = :a and itemNo = :b");
        query_getdataType.setString("a","1");
        query_getdataType.setString("b",itemNo);
        itemType_string = (String) query_getdataType.uniqueResult();
        //从数据库中获取数据字符串
        Query query_getdata = session.createQuery("select itemData1 from Cbqtsz where itemType = :a and itemNo = :b");
        query_getdata.setString("a","1");
        query_getdata.setString("b",itemNo);
        itemNo_string = (String) query_getdata.uniqueResult();
        if(itemType_string != null && !itemType_string.equals("") && itemType_string.equals("科目编号")) {
            if (itemNo_string != null && !itemNo_string.equals("")) {
                String[] split_itemNo = itemNo_string.split("\\;");
                //将串中的每个编号及名称存入返回前端的数据中
                for (String itemNo1 : split_itemNo) {
                    Query query_getname = session.createQuery("select itemName from Lskmzd where itemNo = :a");
                    query_getname.setString("a", itemNo1);
                    String itemName = (String) query_getname.uniqueResult();
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("ItemName", itemName);//项目名称
                    map.put("ItemID", itemNo1);//项目编号
                    map.put("ItemType",itemType_string);//项目类型
                    list1.add(map);
                }
            }
        }else if(itemType_string != null && !itemType_string.equals("") && itemType_string.equals("自定义字段")){
            if (itemNo_string != null && !itemNo_string.equals("")) {
                String[] split_itemNo = itemNo_string.split("\\;");
                //将串中的每个编号及名称存入返回前端的数据中
                for (String itemNo1 : split_itemNo) {
                    Query query_getname = session.createQuery("select itemName from Cbqtsz where itemNo = :a");
                    query_getname.setString("a", itemNo1);
                    String itemName = (String) query_getname.uniqueResult();
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("ItemName", itemName);//项目名称
                    map.put("ItemID", itemNo1);//项目编号
                    map.put("ItemType",itemType_string);//项目类型
                    list1.add(map);
                }
            }
        }

        //从表CBQTSZ中获取数据2的所有内容------------------------------------------------
        Query query_getdataType2 = session.createQuery("select itemDataType2 from Cbqtsz where itemNo = :b");
        query_getdataType2.setString("b",itemNo);
        itemType_string = (String) query_getdataType2.uniqueResult();
        //从数据库中获取数据字符串
        Query query_getdata2 = session.createQuery("select itemData2 from Cbqtsz where itemNo = :b");
        query_getdata2.setString("b",itemNo);
        itemNo_string = (String) query_getdata2.uniqueResult();
        if(itemType_string != null && !itemType_string.equals("") && itemType_string.equals("科目编号")) {
            if (itemNo_string != null && !itemNo_string.equals("")) {
                String[] split_itemNo = itemNo_string.split("\\;");
                //将串中的每个编号及名称存入返回前端的数据中
                for (String itemNo1 : split_itemNo) {
                    Query query_getname = session.createQuery("select itemName from Lskmzd where itemNo = :a");
                    query_getname.setString("a", itemNo1);
                    String itemName = (String) query_getname.uniqueResult();
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("ItemName", itemName);//项目名称
                    map.put("ItemID", itemNo1);//项目编号
                    map.put("ItemType",itemType_string);//项目类型
                    list1.add(map);
                }
            }
        }else if(itemType_string != null && !itemType_string.equals("") && itemType_string.equals("自定义字段")){
            if (itemNo_string != null && !itemNo_string.equals("")) {
                String[] split_itemNo = itemNo_string.split("\\;");
                //将串中的每个编号及名称存入返回前端的数据中
                for (String itemNo1 : split_itemNo) {
                    Query query_getname = session.createQuery("select itemName from Cbqtsz where itemNo = :a");
                    query_getname.setString("a", itemNo1);
                    String itemName = (String) query_getname.uniqueResult();
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("ItemName", itemName);//项目名称
                    map.put("ItemID", itemNo1);//项目编号
                    map.put("ItemType",itemType_string);//项目类型
                    list1.add(map);
                }
            }
        }

        tx.commit();
        close(session);
        return Msg.success().add("definedItemInfo",list1);
    }

    //项目名称下拉菜单，获取指定图表的所有项目名称信息(自定义字段修改)
    public Msg getItemNameSelect_defined(String selectItemTypeValue, String itemNo_defined){
        Session session = null;
        Transaction tx = null;
        //List<List<Object>> list_all = new ArrayList<>();
        List<Map<String,Object>> list1 = new ArrayList<>();
        List<String> itemNameList = new ArrayList<>();
        List<String> itemNoList = new ArrayList<>();//项目编号
        String itemFunction;//项目数据功能
        String itemType;//项目数据类型
        int setNo = 0;//记录图表数据的有效个数

        session = getSession();
        tx = session.beginTransaction();

        if(selectItemTypeValue.equals("科目编号")){
            Query query_getname = session.createQuery("select itemName from Lskmzd");
            itemNameList = query_getname.list();
            Query query_getno = session.createQuery("select itemNo from Lskmzd");
            itemNoList = query_getno.list();
        }else if(selectItemTypeValue.equals("核算编号")){
            Query query_getname = session.createQuery("select spName from Lshszd");
            itemNameList = query_getname.list();
            Query query_getno = session.createQuery("select spNo from Lshszd ");
            itemNoList = query_getno.list();
        }else if(selectItemTypeValue.equals("自定义字段")){
            Query query_getno = session.createQuery("select itemNo from Cbqtsz where itemType = :a or itemType = :b ");
            query_getno.setString("a","1");
            query_getno.setString("b","3");
            itemNoList = query_getno.list();
            for(int index=0; index<itemNoList.size();index++){
                if(itemNoList.get(index).equals(itemNo_defined)){
                    itemNoList.remove(index);
                }
            }

            for(String itemNo1 : itemNoList){
                Query query_getname = session.createQuery("select itemName from Cbqtsz where itemNo = :a");
                query_getname.setString("a",itemNo1);
                String itemName_index = (String) query_getname.uniqueResult();
                itemNameList.add(itemName_index);
            }
        }

        int itemNum = 0;
        for(String itemName : itemNameList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("0", itemName);//项目名称
            map.put("1", itemNoList.get(itemNum));//项目编号
            list1.add(map);
            itemNum++;
        }

        tx.commit();
        close(session);
        return Msg.success().add("itemNameSelect_defined",list1);
    }

    //将自定义项目中组成的项目添加进数据库(自定义字段修改)
    public Msg addDefinedItemInfo_defined(String itemNo_defined, String ItemID_defined, String selectItemTypeValue){
        Session session = null;
        Transaction tx = null;

        List<Map<String,Object>> list1 = new ArrayList<>();//传回前端数据
        List<String> itemNo_list = new ArrayList<>();//已有项目编号
        String itemType1;//数据1的数据类型
        String itemType2;//数据2的数据类型
        String itemNo_string;
        String itemFunction;
        int setNo = 0;//记录图表数据的有效个数

        session = getSession();
        tx = session.beginTransaction();

        //获取项目数据类型1--------------------------------------------------------
        Query query_getItemType = session.createQuery("select itemDataType1 from Cbqtsz where itemNo = :a");
        query_getItemType.setString("a",itemNo_defined);
        itemType1 = (String) query_getItemType.uniqueResult();
        if(selectItemTypeValue.equals(itemType1)) {
            //获取已有的自定义项目编号，查找确认是否为重复的编号
            Query query_getItemNo = session.createQuery("select itemData1 from Cbqtsz where itemNo = :a");
            query_getItemNo.setString("a", itemNo_defined);
            itemNo_string = (String) query_getItemNo.uniqueResult();
            if (itemNo_string != null && !itemNo_string.equals("")) {
                String[] split_itemNo = itemNo_string.split("\\;");
                for (String itemNoUsed : split_itemNo) {
                    if (itemNoUsed.equals(ItemID_defined)) {
                        tx.commit();
                        close(session);
                        return Msg.success().add("addDefinedItemInfo_defined", "编号重复");
                    }
                }
                String[] split_itemNo_new = new String[split_itemNo.length + 1];
                for (int i1 = 0; i1 < split_itemNo.length; i1++) {
                    split_itemNo_new[i1] = split_itemNo[i1];
                }
                split_itemNo_new[split_itemNo.length] = ItemID_defined;//将新的值赋给数组
                String itemNo_join = StringUtils.join(split_itemNo_new, ";");
                Query query_insertItem = session.createQuery("update Cbqtsz set itemData1 = :a where itemNo = :b");
                query_insertItem.setString("a", itemNo_join);
                query_insertItem.setString("b", itemNo_defined);
                query_insertItem.executeUpdate();
            } else {
                Query query_insertItem = session.createQuery("update Cbqtsz set itemData1 = :a where itemNo = :b");
                query_insertItem.setString("a", ItemID_defined);
                query_insertItem.setString("b", itemNo_defined);
                query_insertItem.executeUpdate();
            }
            tx.commit();
            close(session);
            return Msg.success().add("addDefinedItemInfo_defined", "成功");
        }
        if(itemType1 == null){//如果数据1类型为空，说明这是一个新加的自定义数据，需要从数据1开始添加数据类型等信息
            Query query_insertItemType1 = session.createQuery("update Cbqtsz set itemDataType1 = :a where itemNo = :b");
            query_insertItemType1.setString("a", selectItemTypeValue);
            query_insertItemType1.setString("b", itemNo_defined);
            query_insertItemType1.executeUpdate();
            Query query_insertItem = session.createQuery("update Cbqtsz set itemData1 = :a where itemNo = :b");
            query_insertItem.setString("a", ItemID_defined);
            query_insertItem.setString("b", itemNo_defined);
            query_insertItem.executeUpdate();
            tx.commit();
            close(session);
            return Msg.success().add("addDefinedItemInfo_defined", "成功");
        }
        //获取项目数据类型2--------------------------------------------------------------
        Query query_getItemType1 = session.createQuery("select itemDataType2 from Cbqtsz where itemNo = :a");
        query_getItemType1.setString("a",itemNo_defined);
        itemType2 = (String) query_getItemType1.uniqueResult();
        if(selectItemTypeValue.equals(itemType2)){
            //获取已有的自定义项目编号，查找确认是否为重复的编号
            Query query_getItemNo = session.createQuery("select itemData2 from Cbqtsz where itemNo = :a");
            query_getItemNo.setString("a", itemNo_defined);
            itemNo_string = (String) query_getItemNo.uniqueResult();
            if (itemNo_string != null && !itemNo_string.equals("")) {
                String[] split_itemNo = itemNo_string.split("\\;");
                for (String itemNoUsed : split_itemNo) {
                    if (itemNoUsed.equals(ItemID_defined)) {
                        tx.commit();
                        close(session);
                        return Msg.success().add("addDefinedItemInfo_defined", "编号重复");
                    }
                }
                String[] split_itemNo_new = new String[split_itemNo.length + 1];
                for (int i1 = 0; i1 < split_itemNo.length; i1++) {
                    split_itemNo_new[i1] = split_itemNo[i1];
                }
                split_itemNo_new[split_itemNo.length] = ItemID_defined;//将新的值赋给数组
                String itemNo_join = StringUtils.join(split_itemNo_new, ";");
                Query query_insertItem = session.createQuery("update Cbqtsz set itemData2 = :a where itemNo = :b");
                query_insertItem.setString("a", itemNo_join);
                query_insertItem.setString("b", itemNo_defined);
                query_insertItem.executeUpdate();
            } else {
                Query query_insertItem = session.createQuery("update Cbqtsz set itemData2 = :a where itemNo = :b");
                query_insertItem.setString("a", ItemID_defined);
                query_insertItem.setString("b", itemNo_defined);
                query_insertItem.executeUpdate();
            }
            tx.commit();
            close(session);
            return Msg.success().add("addDefinedItemInfo_defined", "成功");
        }
        if(itemType2 == null){//如果数据2类型为空，说明该自定义数据，数据1的类型不对应，需要从数据2中添加新的数据类型
            Query query_insertItemType1 = session.createQuery("update Cbqtsz set itemDataType2 = :a where itemNo = :b");
            query_insertItemType1.setString("a", selectItemTypeValue);
            query_insertItemType1.setString("b", itemNo_defined);
            query_insertItemType1.executeUpdate();
            Query query_insertItem = session.createQuery("update Cbqtsz set itemData2 = :a where itemNo = :b");
            query_insertItem.setString("a", ItemID_defined);
            query_insertItem.setString("b", itemNo_defined);
            query_insertItem.executeUpdate();
            tx.commit();
            close(session);
            return Msg.success().add("addDefinedItemInfo_defined", "成功");
        }
        tx.commit();
        close(session);
        return Msg.success().add("addDefinedItemInfo_defined", "失败");
    }

    //删除图表的指定数据(自定义字段修改)
    public Msg deleteItemInfo_defined(String ItemID, String itemNo_defined, String selectItemTypeValue){
        Session session = null;
        Transaction tx = null;

        List<Map<String,Object>> list1 = new ArrayList<>();
        String itemType1;
        String itemNo_string;
        int setNo = 0;//记录图表数据的有效个数

        session = getSession();
        tx = session.beginTransaction();

        //获取数据1类型
        Query query_getItemType1 = session.createQuery("select itemDataType1 from Cbqtsz where itemNo = :a");
        query_getItemType1.setString("a",itemNo_defined);
        itemType1 = (String) query_getItemType1.uniqueResult();
        //比较与所要更改的数据类型是否与数据1的相同
        if(selectItemTypeValue.equals(itemType1)) {
            Query query_getItemNo = session.createQuery("select itemData1 from Cbqtsz where itemNo = :a");
            query_getItemNo.setString("a", itemNo_defined);
            itemNo_string = (String) query_getItemNo.uniqueResult();
            String[] split_itemNo = itemNo_string.split("\\;");

            String[] split_itemNo_new = new String[split_itemNo.length - 1];
            int i1_new = 0;
            for (int i1 = 0; i1 < split_itemNo.length; i1++) {
                if (split_itemNo[i1].equals(ItemID)) {

                } else {
                    split_itemNo_new[i1_new] = split_itemNo[i1];
                    i1_new++;
                }
            }
            String itemNo_join = StringUtils.join(split_itemNo_new, ";");
            Query query_deleteItem = session.createQuery("update Cbqtsz set itemData1 = :a where itemNo = :b");
            query_deleteItem.setString("a", itemNo_join);
            query_deleteItem.setString("b", itemNo_defined);
            query_deleteItem.executeUpdate();

            tx.commit();
            close(session);
            return Msg.success().add("deleteItemInfo_defined", "删除成功");
        }

        //获取数据2类型
        Query query_getItemType2 = session.createQuery("select itemDataType2 from Cbqtsz where itemNo = :a");
        query_getItemType2.setString("a",itemNo_defined);
        itemType1 = (String) query_getItemType2.uniqueResult();
        //比较与所要更改的数据类型是否与数据1的相同
        if(selectItemTypeValue.equals(itemType1)) {
            Query query_getItemNo = session.createQuery("select itemData2 from Cbqtsz where itemNo = :a");
            query_getItemNo.setString("a", itemNo_defined);
            itemNo_string = (String) query_getItemNo.uniqueResult();
            String[] split_itemNo = itemNo_string.split("\\;");

            String[] split_itemNo_new = new String[split_itemNo.length - 1];
            int i1_new = 0;
            for (int i1 = 0; i1 < split_itemNo.length; i1++) {
                if (split_itemNo[i1].equals(ItemID)) {

                } else {
                    split_itemNo_new[i1_new] = split_itemNo[i1];
                    i1_new++;
                }
            }
            String itemNo_join = StringUtils.join(split_itemNo_new, ";");
            Query query_deleteItem = session.createQuery("update Cbqtsz set itemData2 = :a where itemNo = :b");
            query_deleteItem.setString("a", itemNo_join);
            query_deleteItem.setString("b", itemNo_defined);
            query_deleteItem.executeUpdate();

            tx.commit();
            close(session);
            return Msg.success().add("deleteItemInfo_defined", "删除成功");
        }

        tx.commit();
        close(session);
        return Msg.success().add("deleteItemInfo_defined", "删除失败");
    }

    //----------------------(固定字段界面)--------------------------------------------
    //获取固定项目的信息(固定字段界面)
    public Msg getFixedItemGridInfo(){
        Session session = null;
        Transaction tx = null;
        List<Map<String,Object>> list1 = new ArrayList<>();
        List<String> itemNameList = new ArrayList<>();
        List<String> itemNoList = new ArrayList<>();//项目编号
        List<String> relateItemNoList = new ArrayList<>();//关联项目编号
        List<String> relateItemNameList = new ArrayList<>();//关联项目名称
        List<String> relateItemTypeList = new ArrayList<>();//关联项目类型
        String relateItemNo;
        String relateItemName = null;
        String relateItemType = null;

        session = getSession();
        tx = session.beginTransaction();

        Query query_getname = session.createQuery("select itemName from Cbqtsz where itemType = :a");
        query_getname.setString("a","2");
        itemNameList = query_getname.list();
        Query query_getno = session.createQuery("select itemNo from Cbqtsz where itemType = :a");
        query_getno.setString("a","2");
        itemNoList = query_getno.list();
        Query query_getrelateNo = session.createQuery("select itemData1 from Cbqtsz where itemType = :a");
        query_getrelateNo.setString("a","2");
        relateItemNoList = query_getrelateNo.list();
        Query query_getrelateType = session.createQuery("select itemDataType1 from Cbqtsz where itemType = :a");
        query_getrelateType.setString("a","2");
        relateItemTypeList = query_getrelateType.list();


        int itemNum = 0;
        for(String itemName : itemNameList) {
            relateItemType = relateItemTypeList.get(itemNum);
            relateItemNo = relateItemNoList.get(itemNum);
            if(relateItemType.equals("核算类别")){
                Query query_getrelateName = session.createQuery("select catName from Lshsfl where catNo = :a");
                query_getrelateName.setString("a",relateItemNo);
                relateItemName = (String)query_getrelateName.uniqueResult();
            }else if(relateItemType.equals("核算编号")){
                Query query_getrelateName = session.createQuery("select spName from Lshszd where spNo = :a");
                query_getrelateName.setString("a",relateItemNo);
                relateItemName = (String)query_getrelateName.uniqueResult();
            }else if(relateItemType.equals("科目编号")){
                Query query_getrelateName = session.createQuery("select itemName from Lskmzd where itemNo = :a");
                query_getrelateName.setString("a",relateItemNo);
                relateItemName = (String)query_getrelateName.uniqueResult();
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ItemName", itemName);//项目名称
            map.put("ItemID", itemNoList.get(itemNum));//项目编号
            map.put("relateItemNo",relateItemNo);
            map.put("relateItemName",relateItemName);
            map.put("relateItemType",relateItemType);
            list1.add(map);
            itemNum++;
        }

        tx.commit();
        close(session);
        return Msg.success().add("fixedItemGridInfo",list1);
    }

    //固定项目关联字段名称下拉菜单(固定字段界面)
    public Msg getRelateItemNameSelect_fixed(String selectRelatedItemType){
        Session session = null;
        Transaction tx = null;
        //List<List<Object>> list_all = new ArrayList<>();
        List<Map<String,Object>> list1 = new ArrayList<>();
        List<String> itemNameList = new ArrayList<>();
        List<String> itemNoList = new ArrayList<>();//项目编号
        String itemFunction;//项目数据功能
        String itemType;//项目数据类型
        int setNo = 0;//记录图表数据的有效个数

        session = getSession();
        tx = session.beginTransaction();

        if(selectRelatedItemType.equals("科目编号")){
            Query query_getname = session.createQuery("select itemName from Lskmzd");
            itemNameList = query_getname.list();
            Query query_getno = session.createQuery("select itemNo from Lskmzd");
            itemNoList = query_getno.list();
        }else if(selectRelatedItemType.equals("核算编号")){
            Query query_getname = session.createQuery("select spName from Lshszd ");
            itemNameList = query_getname.list();
            Query query_getno = session.createQuery("select spNo from Lshszd ");
            itemNoList = query_getno.list();
        }else if(selectRelatedItemType.equals("核算类别")){
            Query query_getname = session.createQuery("select catName from Lshsfl ");
            itemNameList = query_getname.list();
            Query query_getno = session.createQuery("select catNo from Lshsfl ");
            itemNoList = query_getno.list();
        }

        int itemNum = 0;
        for(String itemName : itemNameList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", itemNoList.get(itemNum));//项目编号
            map.put("text", itemName);//项目名称
            list1.add(map);
            itemNum++;
        }

        tx.commit();
        close(session);
        return Msg.success().add("relateItemNameSelect_fixed",list1);
    }

    //固定项目关联字段编号下拉菜单(固定字段界面)
    public Msg getRelateItemNoSelect_fixed(String selectRelatedItemType){
        Session session = null;
        Transaction tx = null;
        //List<List<Object>> list_all = new ArrayList<>();
        List<Map<String,Object>> list1 = new ArrayList<>();
        List<String> itemNameList = new ArrayList<>();
        List<String> itemNoList = new ArrayList<>();//项目编号
        String itemFunction;//项目数据功能
        String itemType;//项目数据类型
        int setNo = 0;//记录图表数据的有效个数

        session = getSession();
        tx = session.beginTransaction();

        if(selectRelatedItemType.equals("科目编号")){
            Query query_getname = session.createQuery("select itemName from Lskmzd");
            itemNameList = query_getname.list();
            Query query_getno = session.createQuery("select itemNo from Lskmzd");
            itemNoList = query_getno.list();
        }else if(selectRelatedItemType.equals("核算编号")){
            Query query_getname = session.createQuery("select spName from Lshszd ");
            itemNameList = query_getname.list();
            Query query_getno = session.createQuery("select spNo from Lshszd ");
            itemNoList = query_getno.list();
        }else if(selectRelatedItemType.equals("核算类别")){
            Query query_getname = session.createQuery("select catName from Lshsfl ");
            itemNameList = query_getname.list();
            Query query_getno = session.createQuery("select catNo from Lshsfl ");
            itemNoList = query_getno.list();
        }

        int itemNum = 0;
        for(String itemName : itemNameList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", itemName);//项目名称
            map.put("text", itemNoList.get(itemNum));//项目编号
            list1.add(map);
            itemNum++;
        }

        tx.commit();
        close(session);
        return Msg.success().add("relateItemNoSelect_fixed",list1);
    }

    //固定项目中修改的内容存进数据库(固定字段界面)
    public Msg addFixedItemInfo_fixed(String relateItemType_fixed, String relateItemNo_fixed, String ItemID_fixed){
        Session session = null;
        Transaction tx = null;

        List<Map<String,Object>> list1 = new ArrayList<>();//传回前端数据
        List<String> itemNo_list = new ArrayList<>();//已有项目编号
        String itemType;
        String itemNo_string;
        String itemFunction;
        int setNo = 0;//记录图表数据的有效个数

        session = getSession();
        tx = session.beginTransaction();

        Query query_insertItem = session.createQuery("update Cbqtsz set itemData1 = :a,itemDataType1 = :b where itemNo = :c");
        query_insertItem.setString("a", relateItemNo_fixed);
        query_insertItem.setString("b", relateItemType_fixed);
        query_insertItem.setString("c", ItemID_fixed);
        query_insertItem.executeUpdate();

        tx.commit();
        close(session);
        return Msg.success().add("addDefinedItemInfo_defined","成功");
    }

    //-------------------(界面设置界面)----------------------------------------------------------
    //获取设置界面的信息(界面设置界面)
    public Msg getMenuInfo(){
        Session session = null;
        Transaction tx = null;
        List<Map<String,Object>> list1 = new ArrayList<>();
        List<String> itemNameList = new ArrayList<>();
        List<String> itemData1List = new ArrayList<>();//项目编号

        session = getSession();
        tx = session.beginTransaction();

        Query query_getname = session.createQuery("select itemName from Cbqtsz where itemType = :a");
        query_getname.setString("a","0");
        itemNameList = query_getname.list();
        Query query_getno = session.createQuery("select itemData1 from Cbqtsz where itemType = :a");
        query_getno.setString("a","0");
        itemData1List = query_getno.list();

        int itemNum = 0;
        for(String itemName : itemNameList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("MenuName", itemName);//项目名称
            map.put("IsShow", itemData1List.get(itemNum));//项目编号
            list1.add(map);
            itemNum++;
        }

        tx.commit();
        close(session);
        return Msg.success().add("menuInfo",list1);
    }

    //固定项目中修改的内容存进数据库(固定字段界面)
    public Msg addMenuInfo(String MenuName, String IsShow){
        Session session = null;
        Transaction tx = null;

        session = getSession();
        tx = session.beginTransaction();

        Query query_insertItem = session.createQuery("update Cbqtsz set itemData1 = :a where itemName = :b and itemType = :c");
        query_insertItem.setString("a", IsShow);
        query_insertItem.setString("b", MenuName);
        query_insertItem.setString("c", "0");
        query_insertItem.executeUpdate();

        tx.commit();
        close(session);
        return Msg.success().add("addMenuInfo","成功");
    }

    //获取菜单栏显示的信息
    public Msg getMenuShowInfo(){
        Session session = null;
        Transaction tx = null;
        List<List<String>> list1 = new ArrayList<>();
        List<String> itemNameList = new ArrayList<>();
        List<String> itemData1List = new ArrayList<>();//项目编号

        session = getSession();
        tx = session.beginTransaction();

        Query query_getname = session.createQuery("select itemName from Cbqtsz where itemType = :a");
        query_getname.setString("a","0");
        itemNameList = query_getname.list();
        Query query_getdata1 = session.createQuery("select itemData1 from Cbqtsz where itemType = :a");
        query_getdata1.setString("a","0");
        itemData1List = query_getdata1.list();

//        int itemNum = 0;
//        for(String itemName : itemNameList) {
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("MenuName", itemName);//项目名称
//            map.put("IsShow", itemData1List.get(itemNum));//项目编号
//            list1.add(map);
//            itemNum++;
//        }


        tx.commit();
        close(session);
        return Msg.success().add("menuInfo",list1);
    }
}
