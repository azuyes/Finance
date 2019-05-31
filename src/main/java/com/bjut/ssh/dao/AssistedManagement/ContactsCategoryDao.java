package com.bjut.ssh.dao.AssistedManagement;

import com.bjut.ssh.entity.Lswldw;
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


@Repository
@Transactional
public class ContactsCategoryDao{

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    public void close(Session session){
        if(session != null)
            session.close();
    }

    public List<Lswlfl> findContactsCategory(String id) {
        Session session = null;
        Transaction tx = null;
        List<Lswlfl> lswlflList = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Lswlfl where catLevel = '"+id+"'");
            lswlflList= query.list();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lswlflList;
        }
    }

    public List<Lswlfl> queryContactsCategory() {
        Session session = null;
        Transaction tx = null;
        List<Lswlfl> lswlflList = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Lswlfl ");
            lswlflList= query.list();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lswlflList;
        }
    }

    public Msg saveContactsCategory(Lswlfl lswlfl){
        Session session = null;
        Transaction tx = null;
        List<Lswlfl>lswlflList = null;
        String id;
        try{
            session = getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Lswlfl where catNo1 = '"+lswlfl.getCatNo1()+"'");
            lswlflList= query.list();
            if(lswlflList.size()>0){
                close(session);
                return Msg.fail().add("errorInfo","编号重复，请重新输入");
            }else{
                String hql=null;
                lswlfl.setFinLevel("1");
                session.save(lswlfl);

                switch (lswlfl.getCatLevel()){
                    case "1":hql = "from Lswlfl as s where s.catLevel = '"+lswlfl.getCatLevel()+"'";break;
                    case "2":id = lswlfl.getCatNo1().substring(0,2);
                        String id1 = id+"0000";
                        String hql_Update1="update Lswlfl as s set s.finLevel= ?  where s.catNo1 = '"+id1+"'";
                        Query queryupdate1=session.createQuery(hql_Update1);
                        queryupdate1.setParameter(0,"0");
                        queryupdate1.executeUpdate();
                        hql = "from Lswlfl as s where s.catNo1 like '"+id+"%' and s.catLevel = '"+lswlfl.getCatLevel()+"'";
                        break;
                    case "3":id = lswlfl.getCatNo1().substring(0,4);
                        String id2 = id+"00";
                        String hql_Update2="update Lswlfl as s set s.finLevel= ? where s.catNo1 = '"+id2+"'";
                        Query queryupdate2=session.createQuery(hql_Update2);
                        queryupdate2.setParameter(0,"0");
                        queryupdate2.executeUpdate();
                        hql = "from Lswlfl as s where s.catNo1 like '"+id+"%' and s.catLevel = '"+lswlfl.getCatLevel()+"'";
                        break;
                }
                query = session.createQuery(hql);
                lswlflList = query.list();
                tx.commit();
                close(session);
                dealContactsCategory(lswlflList);
                return Msg.success().add("lswlflList",lswlflList);
            }
        }
        catch(Exception e){
            tx.rollback();
            e.printStackTrace();
            return Msg.fail().add("errorInfo","未知异常！");
        }
    }


    public Msg delContactsCategoryById(String id,String catLevel ){
        Session session = null;
        Transaction tx = null;
        List<Lswlfl> lswlflList = null;
        String hqlQuery="";
        String subCatNo ="";
        boolean bool = false;
        try {
            session = getSession();
            tx = session.beginTransaction();

            Query query = session.createQuery("from Lswldw where catNo1 = '" + id + "'");
            List<Lswldw> lswldws = query.list();

            if (lswldws.size() > 0) {
                bool = false;
            } else {

                Lswlfl lswlfl = (Lswlfl) session.get(Lswlfl.class,id);
                session.delete(lswlfl);
                switch (catLevel){
                    case "1":hqlQuery = "from Lswlfl as s where  s.catLevel = '"+catLevel+"'";break;
                    case "2":subCatNo = id.substring(0,2);
                        hqlQuery = "from Lswlfl as s where s.catNo1 like '"+subCatNo+"%' and s.catLevel = '"+catLevel+"'";break;
                    case "3":subCatNo = id.substring(0,4);
                        hqlQuery = "from Lswlfl as s where s.catNo1 like '"+subCatNo+"%' and s.catLevel = '"+catLevel+"'";break;
                }

                query = session.createQuery(hqlQuery);
                lswlflList = query.list();

                if(lswlflList.size() == 0){
                    switch (catLevel){
                        case "2":subCatNo = id.substring(0,2);
                            String id1 = subCatNo+"0000";
                            String hqlUpdate="update Lswlfl as s set s.finLevel= ? where s.catNo1 = '"+id1+"'";
                            Query queryupdate=session.createQuery(hqlUpdate);
                            queryupdate.setParameter(0,"1");
                            queryupdate.executeUpdate();
                            break;
                        case "3":subCatNo = id.substring(0,4);
                            String id2 = subCatNo+"00";
                            String hqlUpdate1="update Lswlfl as s set s.finLevel= ? where s.catNo1 = '"+id2+"'";
                            Query queryupdate1=session.createQuery(hqlUpdate1);
                            queryupdate1.setParameter(0,"1");
                            queryupdate1.executeUpdate();
                            break;
                    }
                }
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
                lswlflList = dealContactsCategory(lswlflList);
                return Msg.success().add("lswlflList", lswlflList);
            }else {
                return Msg.fail().add("errorInfo", "该往来分类下有核算对象不能删除！");
            }
        }
    }

    public List<Lswlfl> queryContactsCategoryByLevel(String id,String levelFLag){
        Session session = null;
        Transaction tx = null;
        List<Lswlfl> lswlflList = null;
        String hql;
        try {
            session = getSession();
            tx = session.beginTransaction();
            if(levelFLag.equals("1")){
                 hql = "from Lswlfl as s where  s.catLevel = '"+levelFLag+"'";
            }else{
                 hql = "from Lswlfl as s where s.catNo1 like '"+id+"%' and s.catLevel = '"+levelFLag+"'";
            }
            Query query = session.createQuery(hql);
             lswlflList = query.list();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();

        } finally {
            //关闭操作
            close(session);
            return lswlflList;
        }
    }

    public List<Lswlfl>  dealContactsCategory(List<Lswlfl> lswlflList){
        if(lswlflList.size()>0){
            for (Lswlfl lswlfl : lswlflList ){
                if(lswlfl.getFinLevel().equals("1")){
                    lswlfl.setFinLevel("是");
                }
                else {
                    lswlfl.setFinLevel("否");
                }
            }
        }
        return lswlflList;
    }
}
