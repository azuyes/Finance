package com.bjut.ssh.dao.FinanceProcess;

import com.bjut.ssh.entity.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Title: AccountBookDao
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/4/24 14:00
 * @Version: 1.0
 */
@Repository
@Transactional
public class AccountBookDao {
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    public void close(Session session){
        if(session != null)
            session.close();
    }

    public Msg getConfigsForBook(){
        Session session = null;
        Transaction tx = null;
        Msg result = Msg.success();
        try {
            session = getSession();
            tx = session.beginTransaction();

            //科目结构
            Lsconf stru = (Lsconf) session.get(Lsconf.class, "sub_stru");
            result.add("sub_stru", stru.getConfValue());
            //帐页行数
            Lsconf rows = (Lsconf) session.get(Lsconf.class, "bill_row");
            result.add("bill_row", rows.getConfValue());
            //摘要名称长度
            Lsconf length = (Lsconf) session.get(Lsconf.class, "abs_length");
            result.add("abs_length", length.getConfValue());

            tx.commit();
            close(session);
            return result;
        }
        catch(Exception e){
            tx.rollback();
            e.printStackTrace();
            return Msg.fail().add("errorInfo","未知异常！");
        }
    }

    /**
    *@author czh
    *@Description TODO
    *@Date 2018/4/24 14:09
    *@Param []
    *@return java.util.List<com.bjut.ssh.entity.Lszczd>
    **/
    public List<Lszczd> queryAccountBook(){
        Session session = null;
        Transaction tx = null;
        List<Lszczd> lszczdList = null;
        String hql;
        try {
            session = getSession();
            tx = session.beginTransaction();
            hql = "from Lszczd";
            Query query = session.createQuery(hql);
            lszczdList= query.list();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lszczdList;
        }
    }

    /**
    *@author czh
    *@Description TODO
    *@Date 2018/4/25 9:04
    *@Param []
    *@return java.util.List<com.bjut.ssh.entity.Lszcdy>
    **/
    public List<Lszcdy> queryAccountBookDef(String AcontBookNo){
        Session session = null;
        Transaction tx = null;
        List<Lszcdy> lszcdyList = null;
        String hql;
        try {
            session = getSession();
            tx = session.beginTransaction();
            hql = "from Lszcdy where acontBookNo = '"+ AcontBookNo + "'";
            Query query = session.createQuery(hql);
            lszcdyList= query.list();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lszcdyList;
        }
    }

    public List<Lsszzd> queryPrintFor(){
        Session session = null;
        Transaction tx = null;
        List<Lsszzd> lsszzdList = null;
        String hql;
        try {
            session = getSession();
            tx = session.beginTransaction();
            hql = "from Lsszzd";
            Query query = session.createQuery(hql);
            lsszzdList= query.list();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lsszzdList;
        }
    }

    /**
    *@author czh
    *@Description TODO
    *@Date 2018/4/25 9:02
    *@Param [lszczd]
    *@return com.bjut.ssh.entity.Msg
    **/
    public Msg saveAccountBook(Lszczd lszczd){
        Session session = null;
        Transaction tx = null;
        try{
            session = getSession();
            tx = session.beginTransaction();
            String hql = "from Lszczd where acontBookNo = '"+ lszczd.getAcontBookNo() + "'";
            Query query = session.createQuery(hql);
            List<Lszczd> lszczdList = query.list();
            if(lszczdList.size()>0){
                close(session);
                return Msg.fail().add("errorInfo","账册编号重复，请重新输入");
            }
            else{
                session.saveOrUpdate(lszczd);
                tx.commit();
                close(session);
            }
            return Msg.success().add("lszczd",lszczd);
        }
        catch(Exception e){
            tx.rollback();
            e.printStackTrace();
            return Msg.fail().add("errorInfo","保存账册发生未知异常！");
        }
    }

    public Msg editAccountBook(Lszczd lszczd){
        Session session = null;
        Transaction tx = null;
        try{
            session = getSession();
            tx = session.beginTransaction();
            String hql = "from Lszczd where acontBookNo = '"+ lszczd.getAcontBookNo() + "'";
            Query query = session.createQuery(hql);
            List<Lszczd> lszczdList = query.list();
            for(Lszczd lszczd1 : lszczdList){
                session.delete(lszczd1);
            }
            session.save(lszczd);
            tx.commit();
            close(session);
            return Msg.success().add("lszczd",lszczd);
        }
        catch(Exception e){
            tx.rollback();
            e.printStackTrace();
            return Msg.fail().add("errorInfo","编辑账册发生未知异常！");
        }
    }

    public Msg savePrintFor(Lsszzd lsszzd){
        Session session = null;
        Transaction tx = null;
        try{
            session = getSession();
            tx = session.beginTransaction();
            String hql = "from Lsszzd where setNo = '"+ lsszzd.getSetNo() + "' and printNo = '"+ lsszzd.getPrintNo() + "'";
            Query query = session.createQuery(hql);
            List<Lsszzd> lsszzdList = query.list();
            if(lsszzdList.size()>0){
                close(session);
                return Msg.fail().add("errorInfo","科目编号重复，请重新输入");
            }
            else{
                session.saveOrUpdate(lsszzd);
                tx.commit();
                close(session);
            }
            return Msg.success().add("lszczd",lsszzd);
        }
        catch(Exception e){
            tx.rollback();
            e.printStackTrace();
            return Msg.fail().add("errorInfo","保存打印格式发生未知异常！");
        }
    }

    public Msg editPrintFor(Lsszzd lsszzd){
        Session session = null;
        Transaction tx = null;
        try{
            session = getSession();
            tx = session.beginTransaction();
            String hql = "from Lsszzd where setNo = '"+ lsszzd.getSetNo() + "' and printNo = '"+ lsszzd.getPrintNo() + "'";
            Query query = session.createQuery(hql);
            List<Lsszzd> lszczdList = query.list();
            for(Lsszzd lsszzd1 : lszczdList){
                session.delete(lsszzd1);
            }
            session.save(lsszzd);
            tx.commit();
            close(session);
            return Msg.success().add("lsszzd",lsszzd);
        }
        catch(Exception e){
            tx.rollback();
            e.printStackTrace();
            return Msg.fail().add("errorInfo","编辑打印格式发生未知异常！");
        }
    }

    /**
    *@author czh
    *@Description TODO
    *@Date 2018/4/25 15:34 
    *@Param [lszcdy]
    *@return com.bjut.ssh.entity.Msg
    **/
    public Msg editAccountBookDef(Lszcdy lszcdy){
        Session session = null;
        Transaction tx = null;
        try{
            session = getSession();
            tx = session.beginTransaction();
            String hql = "from Lszcdy where acontBookNo = '"+ lszcdy.getAcontBookNo() + "' and itemNo = '"+ lszcdy.getItemNo() + "'";
            Query query = session.createQuery(hql);
            List<Lszcdy> lszcdyList = query.list();
            for(Lszcdy editLszcdy : lszcdyList){
                session.delete(editLszcdy);
            }
            session.save(lszcdy);
            tx.commit();
            close(session);
            return Msg.success().add("lszcdy",lszcdy);
        }
        catch(Exception e){
            tx.rollback();
            e.printStackTrace();
            return Msg.fail().add("errorInfo","编辑账册定义发生未知异常！");
        }
    }

    public Msg saveAccountBookDef(Lszcdy lszcdy){
        Session session = null;
        Transaction tx = null;
        try{
            session = getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(lszcdy);
            tx.commit();
            close(session);
            return Msg.success().add("lszcdy",lszcdy);
        }
        catch(Exception e){
            tx.rollback();
            e.printStackTrace();
            return Msg.fail().add("errorInfo","保存账册定义发生未知异常！");
        }
    }

    /**
    *@author czh
    *@Description TODO
    *@Date 2018/4/25 9:02
    *@Param [id]
    *@return com.bjut.ssh.entity.Msg
    **/
    public Msg delAccountBook(String id) {
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Lszczd lszczd = (Lszczd) session.get(Lszczd.class, id);//根据主键id查找
            session.delete(lszczd);//删除这条记录
            tx.commit();
            close(session);
            return Msg.success().add("lszczd", lszczd);
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "删除账册发生未知异常！");
        }
    }

    /**
    *@author czh
    *@Description TODO
    *@Date 2018/4/25 15:35 
    *@Param [id]
    *@return com.bjut.ssh.entity.Msg
    **/
    public Msg delAccountBookDef(String id) {
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
//            String hql = "from Lszcdy where acontBookNo = '"+ acontBookNo + "' and itemNo = '"+ itemNo + "'";
//            Query query = session.createQuery(hql);
//            List<Lszcdy> lszcdyList = query.list();
//            for(Lszcdy lszcdy : lszcdyList){
//                session.delete(lszcdy);//删除这条记录
//            }
            Lszcdy lszcdy = (Lszcdy) session.get(Lszcdy.class, Integer.parseInt(id));
            session.delete(lszcdy);
            tx.commit();
            close(session);
            return Msg.success().add("lszcdy", lszcdy);
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "删除账册定义发生未知异常！");
        }
    }

    public Msg delPrintFor(String id) {
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
//            String hql = "from Lsszzd where setNo = '"+ setNo + "' and printNo = '"+ printNo + "'";
//            Query query = session.createQuery(hql);
//            List<Lsszzd> lsszzdList = query.list();
//            for(Lsszzd lsszzd : lsszzdList){
//                session.delete(lsszzd);//删除这条记录
//            }
            Lsszzd lsszzd = (Lsszzd) session.get(Lsszzd.class, Integer.parseInt(id));
            session.delete(lsszzd);
            tx.commit();
            close(session);
            return Msg.success().add("lsszzd", lsszzd);
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "删除打印格式发生未知异常！");
        }
    }
}
