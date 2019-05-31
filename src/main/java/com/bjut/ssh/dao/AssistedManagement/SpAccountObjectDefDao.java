package com.bjut.ssh.dao.AssistedManagement;

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
 * @Title: SpAccountObjectDefDao
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/5/7 12:59
 * @Version: 1.0
 */
@Repository
@Transactional
public class SpAccountObjectDefDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    public void close(Session session){
        if(session != null)
            session.close();
    }

    public Lshsfl getSpAccountCategoryById(String catNo){
        Session session = null;
        Transaction tx = null;
        Lshsfl lshsfl = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            lshsfl = (Lshsfl) session.get(Lshsfl.class, catNo);
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lshsfl;
        }
    }

    public List<Lshszd> getLshszd(String catNo){
        Session session = null;
        Transaction tx = null;
        List<Lshszd> lshszds = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Lshszd lshszd where lshszd.catNo = ? and lshszd.spLevel = ?");
            query.setParameter(0,catNo);
            query.setParameter(1,"1");
            lshszds = query.list();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lshszds;
        }
    }

    public List<Lshszd> queryContactsCategoryByLevel(String id,String levelFLag,String catNo){
        Session session = null;
        Transaction tx = null;
        List<Lshszd> lshszdList = null;
        String hql;
        try {
            session = getSession();
            tx = session.beginTransaction();
            if(levelFLag.equals("1")){
                hql = "from Lshszd as s where  s.spLevel = '"+levelFLag+"' and s.catNo = '"+catNo+"'";
            }else{
                hql = "from Lshszd as s where s.spNo like '"+id+"%' and s.spLevel = '"+levelFLag+"' and s.catNo = '"+catNo+"'";
            }
            Query query = session.createQuery(hql);
            lshszdList = query.list();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();

        } finally {
            //关闭操作
            close(session);
            return lshszdList;
        }
    }

    public Msg saveSpAccountObjectDef(Lshszd lshszd){
        Session session = null;
        Transaction tx = null;
        List<Lshszd> lshszdList = null;
        String id;
        try{
            session = getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Lshszd where spNo = '"+lshszd.getSpNo()+"' and catNo = '"+lshszd.getCatNo()+"'");
            lshszdList= query.list();
            if(lshszdList.size()>0){
                close(session);
                return Msg.fail().add("errorInfo","编号重复，请重新输入");
            }else{
                String hql=null;
                lshszd.setFinLevel((byte)1);
                session.save(lshszd);

                switch (lshszd.getSpLevel()){
                    case "1":hql = "from Lshszd as s where s.spLevel = '"+lshszd.getSpLevel()+"' and s.catNo ='"+lshszd.getCatNo()+"'";break;
                    case "2":id = lshszd.getSpNo().substring(0,4);
                        String id1 = id+"00000000";
                        String hql_Update1="update Lshszd as s set s.finLevel= ?  where s.spNo = '"+id1+"' and s.catNo ='"+lshszd.getCatNo()+"'";
                        Query queryupdate1=session.createQuery(hql_Update1);
                        queryupdate1.setParameter(0,(byte)0);
                        queryupdate1.executeUpdate();
                        hql = "from Lshszd as s where s.spNo like '"+id+"%' and s.spLevel = '"+lshszd.getSpLevel()+"' and s.catNo ='"+lshszd.getCatNo()+"'";
                        break;
                    case "3":id = lshszd.getSpNo().substring(0,8);
                        String id2 = id+"0000";
                        String hql_Update2="update Lshszd as s set s.finLevel= ? where s.spNo = '"+id2+"' and s.catNo ='"+lshszd.getCatNo()+"'";
                        Query queryupdate2=session.createQuery(hql_Update2);
                        queryupdate2.setParameter(0,(byte)0);
                        queryupdate2.executeUpdate();
                        hql = "from Lshszd as s where s.spNo like '"+id+"%' and s.spLevel = '"+lshszd.getSpLevel()+"' and s.catNo ='"+lshszd.getCatNo()+"'";
                        break;
                }
                query = session.createQuery(hql);
                lshszdList = query.list();
                tx.commit();
                close(session);
                return Msg.success().add("lshszdList",lshszdList);
            }
        }
        catch(Exception e){
            tx.rollback();
            e.printStackTrace();
            return Msg.fail().add("errorInfo","未知异常！");
        }
    }


    public Msg delSpAccountObjectDef(String id,String spLevel,String catNo ){
        Session session = null;
        Transaction tx = null;
        List<Lshszd> lshszdList = null;
        String hqlQuery="";
        String subSpNo ="";
        boolean flag = true;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query;

            query = session.createQuery("from Lshsje as je where je.spNo1 = ? or je.spNo2 = ?");
            query.setParameter(0,id);
            query.setParameter(1,id);
            List<Lshsje> lshsjeList = query.list();

            String itemNo;
            Lskmzd lskmzd = new Lskmzd();
            for (Lshsje lshsje: lshsjeList) {
                itemNo = lshsje.getItemNo();
                lskmzd = (Lskmzd)session.get(Lskmzd.class,itemNo);
                if(lskmzd.getSupAcc1().equals(catNo) || lskmzd.getSupAcc2().equals(catNo)){
                    if(lshsje.getBalance() != 0 || lshsje.getDebitMoney() != 0
                            || lshsje.getCreditMoney() != 0 || lshsje.getSupMoney() != 0
                            || lshsje.getDebitMoneySup() != 0 || lshsje.getCreditMoneySup() != 0
                            || lshsje.getDebitMoneyAcm() != 0 || lshsje.getCreditMoneyAcm() != 0
                            || lshsje.getDebitMoney01() != 0 || lshsje.getCreditMoney01() != 0 || lshsje.getBalance01() != 0
                            || lshsje.getDebitMoney02() != 0 || lshsje.getCreditMoney02() != 0 || lshsje.getBalance02() != 0
                            || lshsje.getDebitMoney03() != 0 || lshsje.getCreditMoney03() != 0 || lshsje.getBalance03() != 0
                            || lshsje.getDebitMoney04() != 0 || lshsje.getCreditMoney04() != 0 || lshsje.getBalance04() != 0
                            || lshsje.getDebitMoney05() != 0 || lshsje.getCreditMoney05() != 0 || lshsje.getBalance05() != 0
                            || lshsje.getDebitMoney06() != 0 || lshsje.getCreditMoney06() != 0 || lshsje.getBalance06() != 0
                            || lshsje.getDebitMoney07() != 0 || lshsje.getCreditMoney07() != 0 || lshsje.getBalance07() != 0
                            || lshsje.getDebitMoney08() != 0 || lshsje.getCreditMoney08() != 0 || lshsje.getBalance08() != 0
                            || lshsje.getDebitMoney09() != 0 || lshsje.getCreditMoney09() != 0 || lshsje.getBalance09() != 0
                            || lshsje.getDebitMoney10() != 0 || lshsje.getCreditMoney10() != 0 || lshsje.getBalance10() != 0
                            || lshsje.getDebitMoney11() != 0 || lshsje.getCreditMoney11() != 0 || lshsje.getBalance11() != 0
                            || lshsje.getDebitMoney12() != 0 || lshsje.getCreditMoney12() != 0 || lshsje.getBalance12() != 0
                            ){
                        flag = false;
                        break;
                    }
                }
            }
            if(flag){
                query = session.createQuery("from Lshszd as s where  s.spNo = ? and s.catNo = ?");
                query.setParameter(0,id);
                query.setParameter(1,catNo);

                List<Lshszd> lshszds = query.list();

                session.delete(lshszds.get(0));
                switch (spLevel){
                    case "1":hqlQuery = "from Lshszd as s where  s.spLevel = '"+spLevel+"' and s.catNo ='"+catNo+"'";break;
                    case "2":subSpNo = id.substring(0,4);
                        hqlQuery = "from Lshszd as s where s.spNo like '"+subSpNo+"%' and s.spLevel = '"+spLevel+"' and s.catNo ='"+catNo+"'";break;
                    case "3":subSpNo = id.substring(0,8);
                        hqlQuery = "from Lshszd as s where s.spNo like '"+subSpNo+"%' and s.spLevel = '"+spLevel+"' and s.catNo ='"+catNo+"'";break;
                }

                query = session.createQuery(hqlQuery);
                lshszdList = query.list();

                if(lshszdList.size() == 0){
                    switch (spLevel){
                        case "2":subSpNo = id.substring(0,4);
                            String id1 = subSpNo+"00000000";
                            String hqlUpdate="update Lshszd as s set s.finLevel= ? where s.spNo = '"+id1+"'";
                            Query queryupdate=session.createQuery(hqlUpdate);
                            queryupdate.setParameter(0,(byte)1);
                            queryupdate.executeUpdate();
                            break;
                        case "3":subSpNo = id.substring(0,8);
                            String id2 = subSpNo+"0000";
                            String hqlUpdate1="update Lshszd as s set s.finLevel= ? where s.spNo = '"+id2+"'";
                            Query queryupdate1=session.createQuery(hqlUpdate1);
                            queryupdate1.setParameter(0,(byte)1);
                            queryupdate1.executeUpdate();
                            break;
                    }
                }
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();

        } finally {
            //关闭操作
            close(session);
            if(flag){
                return Msg.success().add("lshszds",lshszdList);
            }else{
                return Msg.fail().add("errorInfo","该核算对象还有账，不能删除！");
            }
        }
    }


    public List<Lskmzd> queryCaptionOfAccountByLevel(String id, String levelFlag) {
        Session session = null;
        Transaction tx = null;
        List<Lskmzd> LskmzdList = null;
        String hql;
        try {
            session = getSession();
            tx = session.beginTransaction();
            if(levelFlag.equals("1")){
                hql = "from Lskmzd as s where s.item = '"+levelFlag+"' and s.supAcc1 is not null  and s.supAcc1 != '' ";
            }
            else{
                hql = "from Lskmzd as s where s.itemNo like '"+id+"%' and s.item = '"+levelFlag+"' and s.supAcc1 is not null and s.supAcc1 != '' ";
            }
            Query query = session.createQuery(hql);
            LskmzdList= query.list();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return LskmzdList;
        }
    }


    public List<Lskmzd> queryCaptionOfAccountByLevel1(String id, String levelFlag) {
        Session session = null;
        Transaction tx = null;
        List<Lskmzd> LskmzdList = null;
        String hql;
        try {
            session = getSession();
            tx = session.beginTransaction();
            if(levelFlag.equals("1")){
                hql = "from Lskmzd as s where s.item = '"+levelFlag+"' and s.supAcc1 is not null and s.supAcc2 is not null";
            }
            else{
                hql = "from Lskmzd as s where s.itemNo like '"+id+"%' and s.item = '"+levelFlag+"' and s.supAcc1 is not null and s.supAcc2 is not null  ";
            }
            Query query = session.createQuery(hql);
            LskmzdList= query.list();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return LskmzdList;
        }
    }



}
