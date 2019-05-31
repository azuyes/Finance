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
 * @Title: QuotaSetDao
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/8/18 13:55
 * @Version: 1.0
 */

@Repository
@Transactional
public class DepartmentBudgetDao {
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    public void close(Session session){
        if(session != null)
            session.close();
    }

    //选择科目最大级数
    public Msg getMaxLevel(){
        Session session = null;
        Transaction tx = null;
        List<Map<String,Object>> list1 = new ArrayList<>();
        session = getSession();
        tx = session.beginTransaction();

        //获得
        Query query_getMaxLevel = session.createQuery("select confValue from Lsconf where confKey = :a" );
        query_getMaxLevel.setString("a","sub_stru");
        String maxLevel_String = (String) query_getMaxLevel.uniqueResult();
        char maxLevel_char = maxLevel_String.charAt(0);
        int maxLevel = maxLevel_char - '0';

        tx.commit();
        close(session);
        return Msg.success().add("DepartmentBudget_getMaxLevel",maxLevel);
    }

    //获取所有科目的信息
    public Msg getAnnualBudget(String Level_string, String ItemID){
        Session session = null;
        Transaction tx = null;
        //List<List<Map<String,Object>>> list_all = new ArrayList<>();
        String levelNumString;//lsconf表中存科目结构
        char levelNum;//该级的位数
        int levelNum_int;
        int level_int = Integer.parseInt(Level_string);
        List<Map<String,Object>> list1 = new ArrayList<>();
        session = getSession();
        tx = session.beginTransaction();

        if(Level_string.equals("1")){
            Query query = session.createQuery("select itemNo,itemName from Lskmzd where item = :a" );
            query.setString("a",Level_string);
            List<Object[]> list = query.list();
            for(Object[] object : list){
                String itemNo = (String)object[0];
                String itemName = (String)object[1];
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("ItemID",itemNo);//项目编号
                map.put("ItemName",itemName);//项目名称
                map.put("ItemLevel",Level_string);//项目级数
                list1.add(map);
            }
        }else{
            //先获取科目结构数据
            Query query_getLevelString = session.createQuery("select confValue from Lsconf where confKey = :a" );
            query_getLevelString.setString("a","sub_stru");
            levelNumString =(String) query_getLevelString.uniqueResult();
            int levelNum_int1 = 0;
            for(int i=1;i<level_int;i++){
                levelNum = levelNumString.charAt(i);
                levelNum_int = levelNum - '0';
                levelNum_int1 += levelNum_int;
            }

            String itemNoSubString = ItemID.substring(0,levelNum_int1) + "%";
            Query query = session.createQuery("select itemNo,itemName from Lskmzd where item = :a and itemNo like :b" );
            query.setString("a",Level_string);
            query.setString("b",itemNoSubString);
            List<Object[]> list = query.list();
            for(Object[] object : list){
                String itemNo = (String)object[0];
                String itemName = (String)object[1];
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("ItemID",itemNo);//项目编号
                map.put("ItemName",itemName);//项目名称
                map.put("ItemLevel",Level_string);//项目级数
                list1.add(map);
            }
        }

        System.out.println(list1);
        if(list1.size()>0) {
            tx.commit();
            close(session);
            return Msg.success().add("DepartmentBudget_getAnnualBudgetInfo",list1);
        }
        else {
            tx.commit();
            close(session);
            return Msg.fail().add("errorInfo","编号重复，请重新输入");
        }
    }

    //根据选择的科目，选择相应的核算金额表里的预算内容
    public Msg getItemSpBudgetInfo(String selectedItemID){
        Session session = null;
        Transaction tx = null;
        List<Map<String,Object>> list1 = new ArrayList<>();
        session = getSession();
        tx = session.beginTransaction();

        //获得部门对应的核算分类编号
        Query query_getItemNo = session.createQuery("select itemName from Lskmzd where itemNo = :a" );
        query_getItemNo.setString("a",selectedItemID);
        String itemName = (String) query_getItemNo.uniqueResult();

        Query query = session.createQuery("select spNo1,spNo2,budgetMoney from Lshsje where itemNo = :a" );
        query.setString("a",selectedItemID);
        List<Object[]> list = query.list();
        for(Object[] object : list){
            String spNo1 = (String)object[0];
            String spNo2 = (String)object[1];
            Double budgetMoney = (Double) object[2];
            Query query_getSpName1 = session.createQuery("select spName from Lshszd where spNo = :a" );
            query_getSpName1.setString("a",spNo1);
            String spName1 = (String) query_getSpName1.uniqueResult();
            Query query_getSpName2 = session.createQuery("select spName from Lshszd where spNo = :a" );
            query_getSpName2.setString("a",spNo2);
            String spName2 = (String) query_getSpName2.uniqueResult();
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("ItemID",selectedItemID);//科目编号
            map.put("ItemName",itemName);//科目名称
            map.put("spNo1",spNo1);//核算编号1
            map.put("spName1",spName1);//核算名称1
            map.put("spNo2",spNo2);//核算编号2
            map.put("spName2",spName2);//核算名称2
            map.put("AnnualBudget",budgetMoney);//年度预算
            list1.add(map);
        }

        System.out.println(list1);
        if(list1.size()>0) {
            tx.commit();
            close(session);
            return Msg.success().add("DepartmentBudget_getItemSpBudgetInfo",list1);
        }
        else {
            tx.commit();
            close(session);
            return Msg.fail().add("errorInfo","编号重复，请重新输入");
        }
    }

    //保存预算金额数据到图表中
    public Msg saveSpItemBudgetInfo(String annualBudget, String itemId, String spNo1, String spNo2){
        Session session = null;
        Transaction tx = null;

        List<Map<String,Object>> list1 = new ArrayList<>();
        int setNo = 0;//记录图表数据的有效个数
        Double annualBudget_double = Double.parseDouble(annualBudget);

        session = getSession();
        tx = session.beginTransaction();
        if(spNo2.equals("null")){
            Query query_saveBudget = session.createQuery("update Lshsje set budgetMoney =:a where itemNo = :b and spNo1 = :c and spNo2 is null");
            query_saveBudget.setDouble("a", annualBudget_double);
            query_saveBudget.setString("b", itemId);
            query_saveBudget.setString("c", spNo1);
            query_saveBudget.executeUpdate();
        }else{
            Query query_saveBudget = session.createQuery("update Lshsje set budgetMoney =:a where itemNo = :b and spNo1 = :c and spNo2 = :d");
            query_saveBudget.setDouble("a", annualBudget_double);
            query_saveBudget.setString("b", itemId);
            query_saveBudget.setString("c", spNo1);
            query_saveBudget.setString("d", spNo2);
            query_saveBudget.executeUpdate();
        }



        tx.commit();
        close(session);
        return Msg.success().add("saveSpItemBudgetInfo","成功");
    }
}
