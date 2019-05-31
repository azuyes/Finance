package com.bjut.ssh.dao.CostManagement;
//import antlr.StringUtils;

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
public class AnnualBudgetDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    public void close(Session session){
        if(session != null)
            session.close();
    }

    //获取所有图表的信息
    public Msg getLevelMax(){
        Session session = null;
        Transaction tx = null;
        //List<List<Map<String,Object>>> list_all = new ArrayList<>();
        String levelNumString;//lsconf表中存科目结构
        char levelNum;//级数的总和
        int levelNum_int;
        session = getSession();
        tx = session.beginTransaction();

        Query query_getLevelString = session.createQuery("select confValue from Lsconf where confKey = :a" );
        query_getLevelString.setString("a","sub_stru");
        levelNumString =(String) query_getLevelString.uniqueResult();
        levelNum = levelNumString.charAt(0);
        levelNum_int = levelNum - '0';

        tx.commit();
        close(session);
        return Msg.success().add("getLevelMax",levelNum_int);
    }

    //获取所有图表的信息
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
            Query query = session.createQuery("select itemNo,itemName,budgetMoney from Lskmzd where item = :a" );
            query.setString("a",Level_string);
            List<Object[]> list = query.list();
            for(Object[] object : list){
                String itemNo = (String)object[0];
                String itemName = (String)object[1];
                Double budgetMoney = (Double) object[2];
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("ItemID",itemNo);//项目编号
                map.put("ItemName",itemName);//项目名称
                map.put("ItemLevel",Level_string);//项目级数
                map.put("AnnualBudget",budgetMoney);//预算金额
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
            Query query = session.createQuery("select itemNo,itemName,budgetMoney from Lskmzd where item = :a and itemNo like :b" );
            query.setString("a",Level_string);
            query.setString("b",itemNoSubString);
            List<Object[]> list = query.list();
            for(Object[] object : list){
                String itemNo = (String)object[0];
                String itemName = (String)object[1];
                Double budgetMoney = (Double) object[2];
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("ItemID",itemNo);//项目编号
                map.put("ItemName",itemName);//项目名称
                map.put("ItemLevel",Level_string);//项目级数
                map.put("AnnualBudget",budgetMoney);//预算金额
                list1.add(map);
            }
        }

        System.out.println(list1);
        if(list1.size()>0) {
            tx.commit();
            close(session);
            return Msg.success().add("getAnnualBudgetInfo",list1);
        }
        else {
            tx.commit();
            close(session);
            return Msg.fail().add("errorInfo","编号重复，请重新输入");
        }
    }

    //保存预算金额数据到图表中
    public Msg saveAnnualBudgetInfo(String annualBudget, String itemNo){
        Session session = null;
        Transaction tx = null;

        List<Map<String,Object>> list1 = new ArrayList<>();
        int setNo = 0;//记录图表数据的有效个数

        session = getSession();
        tx = session.beginTransaction();

        Query query_saveBudget = session.createQuery("update Lskmzd set budgetMoney =:b where itemNo = :a");
        query_saveBudget.setString("a", itemNo);
        query_saveBudget.setString("b", annualBudget);
        query_saveBudget.executeUpdate();

        tx.commit();
        close(session);
        return Msg.success().add("saveAnnualBudgetInfo","成功");
    }
}
