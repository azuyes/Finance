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
public class ThemeSetDao {
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    public void close(Session session){
        if(session != null)
            session.close();
    }

    //获取所有图表的主题信息
    public Msg getGraphAll(){
        Session session = null;
        Transaction tx = null;
        //List<List<Map<String,Object>>> list_all = new ArrayList<>();
        List<Map<String,Object>> list1 = new ArrayList<>();
        session = getSession();
        tx = session.beginTransaction();

        Query query = session.createQuery("select graphNo,graphName,graphTheme from Cbtbsz " );
        List<Object[]> list = query.list();
        for(Object[] object : list){
            String graphNumber = (String)object[0];
            String graphName = (String)object[1];
            String graphTheme = (String)object[2];
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("ChartID",graphNumber);//图表编号
            map.put("ChartName",graphName);//图表名称
            map.put("ChartTheme",graphTheme);//图表主题
            list1.add(map);
        }

        System.out.println(list1);
        if(list1.size()>0) {
            tx.commit();
            close(session);
            return Msg.success().add("graphAll_theme",list1);
        }
        else {
            tx.commit();
            close(session);
            return Msg.fail().add("errorInfo","编号重复，请重新输入");
        }
    }

    //添加数据到图表中
    public Msg addThemeInfo(String chartIDSelect, String chartThemeSelect){
        Session session = null;
        Transaction tx = null;

        List<Map<String,Object>> list1 = new ArrayList<>();
        String itemType;
        String itemNo;
        String itemFunction;
        int setNo = 0;//记录图表数据的有效个数

        session = getSession();
        tx = session.beginTransaction();

        Query query_deleteItemNo = session.createQuery("update Cbtbsz set graphTheme =:b where graphNo = :a");
        query_deleteItemNo.setString("a", chartIDSelect);
        query_deleteItemNo.setString("b", chartThemeSelect);
        query_deleteItemNo.executeUpdate();

        tx.commit();
        close(session);
        return Msg.success().add("addThemeInfo","成功");
    }
}
