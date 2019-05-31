package com.bjut.ssh.dao.AssistedManagement;

import com.bjut.ssh.entity.Lshsfl;
import com.bjut.ssh.entity.Lshszd;
import com.bjut.ssh.entity.Lswlfl;
import com.bjut.ssh.entity.Msg;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Title: SpAccountCategoryDao
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/5/2 14:35
 * @Version: 1.0
 */
@Repository
@Transactional
public class SpAccountCategoryDao {
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    public void close(Session session){
        if(session != null)
            session.close();
    }
    
    /**
    *@author lz
    *@Description 获取lshsfl表中的数据
    *@Date  8:30
    *@Param []
    *@return java.util.List<com.bjut.ssh.entity.Lshsfl>
    **/
    public List<Lshsfl> getSpAccountCategory() {
        Session session = null;
        Transaction tx = null;
        List<Lshsfl> lshsfls = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Lshsfl");
            lshsfls= query.list();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lshsfls;
        }
    }

    /**
    *@author lz
    *@Description 保存核算分类
    *@Date 2018/5/3 8:30
    *@Param [lshsfl]
    *@return com.bjut.ssh.entity.Msg
    **/
    public Msg saveSpAccountCategory(Lshsfl lshsfl){
        Session session = null;
        Transaction tx = null;
        List<Lshsfl>lshsfls = null;
        try{
            session = getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Lshsfl where catNo = '"+lshsfl.getCatNo()+"'");
            lshsfls= query.list();
            if(lshsfls.size()>0){
                tx.commit();
                close(session);
                return Msg.fail().add("errorInfo","编号重复，请重新输入");
            }else{
                session.save(lshsfl);
            }
            query = session.createQuery("from Lshsfl");
            lshsfls = query.list();
            tx.commit();
            close(session);
            return Msg.success().add("lshsfls",lshsfls);
        }
        catch(Exception e){
            tx.rollback();
            e.printStackTrace();
            return Msg.fail().add("errorInfo","未知异常,请重新操作！");
        }
    }

    /**
    *@author lz
    *@Description 根据id删除核算分类
    *@Date 2018/5/3 8:31
    *@Param [id]
    *@return java.util.List<com.bjut.ssh.entity.Lshsfl>
    **/
    public Msg delSpAccountCategoryById(String id) {
        Session session = null;
        Transaction tx = null;
        List<Lshsfl> lshsfls = null;
        boolean bool = false;
        try {
            session = getSession();
            tx = session.beginTransaction();

            Query query = session.createQuery("from Lshszd where catNo = '" + id + "'");
            List<Lshszd> lshszds = query.list();

            if (lshszds.size() > 0) {
                bool = false;
            } else {
                Lshsfl lshsfl = (Lshsfl) session.get(Lshsfl.class, id);
                session.delete(lshsfl);
                query = session.createQuery("from Lshsfl");
                lshsfls = query.list();
                bool = true;

            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();

        } finally {
            //关闭操作
            close(session);
            if(bool){
                return Msg.success().add("lshsfls", lshsfls);
            }else {
                return Msg.fail().add("errorInfo", "该核算分类下有核算对象不能删除！");
            }
        }
    }
}
