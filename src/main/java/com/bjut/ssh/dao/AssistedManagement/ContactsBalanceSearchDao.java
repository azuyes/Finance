package com.bjut.ssh.dao.AssistedManagement;

import com.bjut.ssh.entity.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: ContactsBalanceSearchDao
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/7/13 17:28
 * @Version: 1.0
 */
@Repository
@Transactional
public class ContactsBalanceSearchDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    public void close(Session session){
        if(session != null)
            session.close();
    }


    public List<Lskmzd> queryContactsItem() {
        Session session = null;
        Transaction tx = null;
        List<Lskmzd> lskmzdList = new ArrayList<Lskmzd>();
        try {
            session = getSession();
            tx = session.beginTransaction();

            Query query = session.createQuery("from Lskmzd where exchang = ? and item = ?");
            query.setParameter(0,(byte)1);
            query.setParameter(1,"1");
            lskmzdList = query.list();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lskmzdList;
        }
    }


    public List<ContactsBalanceQueryVo> queryContactsBalance4(String year, String month, String itemNo, String companyNo, String searchAccType, String searchOption,String queryDataType) {
        Session session = null;
        Transaction tx = null;
        List<ContactsBalanceQueryVo> contactsBalanceQueryVoList = new ArrayList<ContactsBalanceQueryVo>();

        try {
            session = getSession();
            tx = session.beginTransaction();

            //财务日期
            //Lsconf begin = (Lsconf) session.get(Lsconf.class, "current_date");
            //result.add("current_date", begin.getConfValue());

            Lskmzd lskmzd = (Lskmzd)session.get(Lskmzd.class,itemNo);

            Query query = session.createQuery("from Lswlje je where je.itemNo = ? and je.companyNo = ?");
            query.setParameter(0,itemNo);
            query.setParameter(1,companyNo);
            List<Lswlje> lswljeList = query.list();

            ContactsBalanceQueryVo contactsBalanceQueryVo = new ContactsBalanceQueryVo();

            if(lswljeList.size()>0) {
                Lswlje lswlje = lswljeList.get(0);

                Lswldw lswldw = (Lswldw) session.get(Lswldw.class, lswlje.getCompanyNo());

                if (searchAccType.equals("S")) {
                    query = session.createQuery("from Lswlsl as sl where sl.itemNo = ? and sl.companyNo = ?");
                    query.setParameter(0, itemNo);
                    query.setParameter(1, lswldw.getCompanyNo());
                    List<Lswlsl> lswlslList = query.list();
                    if (lswlslList.size() > 0) {
                        Lswlsl lswlsl = lswlslList.get(0);
                        contactsBalanceQueryVo.setLswlsl(lswlsl);
                    } else {
                        contactsBalanceQueryVo.setLswlsl(null);
                    }
                } else {
                    contactsBalanceQueryVo.setLswlsl(null);
                }

                contactsBalanceQueryVo.setLskmzd(lskmzd);
                contactsBalanceQueryVo.setLswldw(lswldw);
                contactsBalanceQueryVo.setLswlje(lswlje);

                contactsBalanceQueryVoList.add(0, contactsBalanceQueryVo);
            }else{
                contactsBalanceQueryVo.setLswlsl(null);
                contactsBalanceQueryVo.setLskmzd(null);
                contactsBalanceQueryVo.setLswldw(null);
                contactsBalanceQueryVo.setLswlje(null);
                contactsBalanceQueryVoList.add(0, contactsBalanceQueryVo);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return contactsBalanceQueryVoList;
        }
    }

    public List<ContactsBalanceQueryVo> queryContactsBalance3(String year, String month, String itemNo, String companyNo, String searchAccType, String searchOption) {
        Session session = null;
        Transaction tx = null;
        List<ContactsBalanceQueryVo> contactsBalanceQueryVoList = new ArrayList<ContactsBalanceQueryVo>();

        try {
            session = getSession();
            tx = session.beginTransaction();

            //财务日期
            //Lsconf begin = (Lsconf) session.get(Lsconf.class, "current_date");
            //result.add("current_date", begin.getConfValue());

            Lskmzd lskmzd = (Lskmzd)session.get(Lskmzd.class,itemNo);

            Query query = session.createQuery("from Lswlje je where je.itemNo = ? and je.companyNo = ?");
            query.setParameter(0,itemNo);
            query.setParameter(1,companyNo);
            List<Lswlje> lswljeList = query.list();

            ContactsBalanceQueryVo contactsBalanceQueryVo = new ContactsBalanceQueryVo();

            if(lswljeList.size()>0) {
                Lswlje lswlje = lswljeList.get(0);

                Lswldw lswldw = (Lswldw) session.get(Lswldw.class, lswlje.getCompanyNo());

                if (searchAccType.equals("S")) {
                    query = session.createQuery("from Lswlsl as sl where sl.itemNo = ? and sl.companyNo = ?");
                    query.setParameter(0, itemNo);
                    query.setParameter(1, lswldw.getCompanyNo());
                    List<Lswlsl> lswlslList = query.list();
                    if (lswlslList.size() > 0) {
                        Lswlsl lswlsl = lswlslList.get(0);
                        contactsBalanceQueryVo.setLswlsl(lswlsl);
                    } else {
                        contactsBalanceQueryVo.setLswlsl(null);
                    }
                } else {
                    contactsBalanceQueryVo.setLswlsl(null);
                }

                contactsBalanceQueryVo.setLskmzd(lskmzd);
                contactsBalanceQueryVo.setLswldw(lswldw);
                contactsBalanceQueryVo.setLswlje(lswlje);

                contactsBalanceQueryVoList.add(0, contactsBalanceQueryVo);
            }else{
                contactsBalanceQueryVo.setLswlsl(null);
                contactsBalanceQueryVo.setLskmzd(null);
                contactsBalanceQueryVo.setLswldw(null);
                contactsBalanceQueryVo.setLswlje(null);
                contactsBalanceQueryVoList.add(0, contactsBalanceQueryVo);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return contactsBalanceQueryVoList;
        }
    }


    public List<ContactsBalanceQueryVo> queryContactsBalance2(String year, String month, String itemNo, String companyNo, String searchAccType, String searchOption) {
        Session session = null;
        Transaction tx = null;
        List<ContactsBalanceQueryVo> contactsBalanceQueryVoList = new ArrayList<ContactsBalanceQueryVo>();

        try {
            session = getSession();
            tx = session.beginTransaction();

            //财务日期
            //Lsconf begin = (Lsconf) session.get(Lsconf.class, "current_date");
            //result.add("current_date", begin.getConfValue());

            Lswldw lswldw = (Lswldw)session.get(Lswldw.class,companyNo);

            Query query = session.createQuery("from Lswlje je where je.companyNo = ?");
            query.setParameter(0,companyNo);
            List<Lswlje> lswljeList = query.list();
            Lskmzd lskmzd;
            int i = 0;
            for(Lswlje lswlje : lswljeList){

                ContactsBalanceQueryVo contactsBalanceQueryVo = new ContactsBalanceQueryVo();

                lskmzd = (Lskmzd)session.get(Lskmzd.class,lswlje.getItemNo());

                if(searchAccType.equals("S")){
                    query = session.createQuery("from Lswlsl as sl where sl.itemNo = ? and sl.companyNo = ?");
                    query.setParameter(0,lskmzd.getItemNo());
                    query.setParameter(1,companyNo);
                    List<Lswlsl> lswlslList = query.list();
                    if(lswlslList.size()>0) {
                        Lswlsl lswlsl = lswlslList.get(0);
                        contactsBalanceQueryVo.setLswlsl(lswlsl);
                    }else{
                        contactsBalanceQueryVo.setLswlsl(null);
                    }
                }else{
                    contactsBalanceQueryVo.setLswlsl(null);
                }

                contactsBalanceQueryVo.setLskmzd(lskmzd);
                contactsBalanceQueryVo.setLswldw(lswldw);
                contactsBalanceQueryVo.setLswlje(lswlje);

                contactsBalanceQueryVoList.add(i,contactsBalanceQueryVo);
                i = i+1;

            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return contactsBalanceQueryVoList;
        }
    }



    public List<ContactsBalanceQueryVo> queryContactsBalance1(String year, String month, String itemNo, String companyNo, String searchAccType, String searchOption) {
        Session session = null;
        Transaction tx = null;
        List<ContactsBalanceQueryVo> contactsBalanceQueryVoList = new ArrayList<ContactsBalanceQueryVo>();

        try {
            session = getSession();
            tx = session.beginTransaction();

            //财务日期
            //Lsconf begin = (Lsconf) session.get(Lsconf.class, "current_date");
            //result.add("current_date", begin.getConfValue());

            Lskmzd lskmzd = (Lskmzd)session.get(Lskmzd.class,itemNo);

            Query query = session.createQuery("from Lswlje je where je.itemNo = ?");
            query.setParameter(0,itemNo);
            List<Lswlje> lswljeList = query.list();
            Lswldw lswldw ;
            int i = 0;
            for(Lswlje lswlje : lswljeList){

                ContactsBalanceQueryVo contactsBalanceQueryVo = new ContactsBalanceQueryVo();

                lswldw = (Lswldw)session.get(Lswldw.class,lswlje.getCompanyNo());

                if(searchAccType.equals("S")){
                    query = session.createQuery("from Lswlsl as sl where sl.itemNo = ? and sl.companyNo = ?");
                    query.setParameter(0,itemNo);
                    query.setParameter(1,lswldw.getCompanyNo());
                    List<Lswlsl> lswlslList = query.list();
                    if(lswlslList.size()>0) {
                        Lswlsl lswlsl = lswlslList.get(0);
                        contactsBalanceQueryVo.setLswlsl(lswlsl);
                    }else{
                        contactsBalanceQueryVo.setLswlsl(null);
                    }
                }else{
                    contactsBalanceQueryVo.setLswlsl(null);
                }

                contactsBalanceQueryVo.setLskmzd(lskmzd);
                contactsBalanceQueryVo.setLswldw(lswldw);
                contactsBalanceQueryVo.setLswlje(lswlje);

                contactsBalanceQueryVoList.add(i,contactsBalanceQueryVo);
                i = i+1;

            }

//            Query query = session.createQuery("from Lswlfl");
//            List<Lswlfl> lswlflList = query.list();
//
//           // Double supMoney,debitMoneyAcm,creditMoneyAcm,balance,supQty,debitQtyAcm,creditQtyAcm,leftQty;
//            int i = 0;
//            for(Lswlfl lswlfl : lswlflList){
//                List<Lswldw> lswldwList = null;
//                query = session.createQuery("from Lswldw where catNo1 = ?");
//                query.setParameter(0,lswlfl.getCatNo1());
//                lswldwList = query.list();
//                for(Lswldw lswldw:lswldwList){
//                    query = session.createQuery("from Lswlje as je where je.itemNo = ? and je.companyNo = ?");
//                    query.setParameter(0,itemNo);
//                    query.setParameter(1,lswldw.getCompanyNo());
//                    Lswlje lswlje = (Lswlje)query.list().get(0);
//
//                    if(searchAccType.equals("S")){
//                        query = session.createQuery("from Lswlsl as sl where sl.itemNo = ? and sl.companyNo = ?");
//                        query.setParameter(0,itemNo);
//                        query.setParameter(1,lswldw.getCompanyNo());
//                        Lswlsl lswlsl = (Lswlsl)query.list().get(0);
//                        contactsBalanceQueryVo.setLswlsl(lswlsl);
//                    }else{
//                        contactsBalanceQueryVo.setLswlsl(null);
//                    }
//
//                    contactsBalanceQueryVo.setLskmzd(lskmzd);
//                    contactsBalanceQueryVo.setLswldw(lswldw);
//                    contactsBalanceQueryVo.setLswlje(lswlje);
//
//                    contactsBalanceQueryVoList.add(i,contactsBalanceQueryVo);
//                    i++;
//
//                }
//            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return contactsBalanceQueryVoList;
        }
    }

    public List<ContactsBalanceQueryVo> queryContactsBalanceQueryVo() {
        Session session = null;
        Transaction tx = null;
        List<ContactsBalanceQueryVo> contactsBalanceQueryVoList = new ArrayList<ContactsBalanceQueryVo>();

        try {
            session = getSession();
            tx = session.beginTransaction();

            Query query = session.createQuery("from Lswlje je");
            List<Lswlje> lswljeList = query.list();
            Lswldw lswldw ;
            Lskmzd lskmzd;
            int i = 0;
            for(Lswlje lswlje : lswljeList){

                ContactsBalanceQueryVo contactsBalanceQueryVo = new ContactsBalanceQueryVo();

                lswldw = (Lswldw)session.get(Lswldw.class,lswlje.getCompanyNo());
                lskmzd = (Lskmzd)session.get(Lskmzd.class,lswlje.getItemNo());

                contactsBalanceQueryVo.setLswlsl(null);

                contactsBalanceQueryVo.setLskmzd(lskmzd);
                contactsBalanceQueryVo.setLswldw(lswldw);
                contactsBalanceQueryVo.setLswlje(lswlje);

                contactsBalanceQueryVoList.add(i,contactsBalanceQueryVo);
                i = i+1;

            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return contactsBalanceQueryVoList;
        }
    }

    public List<Lswlje> queryLswlje() {
        Session session = null;
        Transaction tx = null;
        List<Lswlje> lswljeList = new ArrayList<Lswlje>();
        List<Lswlje> lswljes = new ArrayList<Lswlje>();
        try {
            session = getSession();
            tx = session.beginTransaction();

            Query query = session.createQuery("from Lswlje  ");
            //query.setParameter(0,companyNo);
            lswljeList = query.list();
//            for(Lswlje lswlje:lswljeList){
//                if(judgeLswlje(lswlje.getItemNo())){
//                    lswljes.add(lswlje);
//                }
//            }



            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lswljeList;
        }
    }

    public List<Lswldw> queryLswldw() {
        Session session = null;
        Transaction tx = null;
        List<Lswldw> lswldwList = new ArrayList<Lswldw>();

        try {
            session = getSession();
            tx = session.beginTransaction();

            Query query = session.createQuery("from Lswldw order by companyNo");
            lswldwList = query.list();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lswldwList;
        }
    }

    public boolean judgeLswlje(String itemNo) {
        Session session = null;
        Transaction tx = null;
        boolean bool = true;

        try {
            session = getSession();
            tx = session.beginTransaction();

            Lskmzd lskmzd = (Lskmzd)session.get(Lskmzd.class,itemNo);

            if(lskmzd.getItem().equals("1")){
                bool = true;
            }else{
                bool = false;
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return bool;
        }
    }

    public List<Object> getFormulaResult(String hql){
        List<Object> result = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery(hql);
            result= query.list();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            if(result!=null && result.size()>0)
                return result;
            else
                return null;
        }
    }






}
