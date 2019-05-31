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
 * @Title: ContactsInitializationDao
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/18 16:25
 * @Version: 1.0
 */
@Repository
@Transactional
public class ContactsInitializationDao {
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    public void close(Session session){
        if(session != null)
            session.close();
    }

    public LskmzdVoForContacts getItemById(String itemNo) {
        Session session = null;
        Transaction tx = null;
        Lskmzd lskmzd = null;
        Lskmsl lskmsl = null;
        LskmzdVoForContacts lskmzdVoForContacts = new LskmzdVoForContacts();
        try {
            session = getSession();
            tx = session.beginTransaction();
            lskmzd = (Lskmzd)session.get(Lskmzd.class,itemNo);
            lskmzdVoForContacts.setItemNo(lskmzd.getItemNo());
            lskmzdVoForContacts.setItemName(lskmzd.getItemName());
            lskmzdVoForContacts.setAccType(lskmzd.getAccType());
            lskmzdVoForContacts.setFinLevel(lskmzd.getFinLevel());
            lskmzdVoForContacts.setExchang(lskmzd.getExchang());
            lskmzdVoForContacts.setBalance(lskmzd.getBalance());
            lskmzdVoForContacts.setDebitMoneySup(lskmzd.getDebitMoneySup());
            lskmzdVoForContacts.setCreditMoneySup(lskmzd.getCreditMoneySup());
            lskmzdVoForContacts.setSupMoney(lskmzd.getSupMoney());

            if(lskmzd.getAccType().equals("Y")){
                Query query = session.createQuery("from Lskmsl lskmsl where lskmsl.itemNo = '"+itemNo+"'");
                List<Lskmsl> lskmsls;
                lskmsls = query.list();
                lskmsl = lskmsls.get(0);
                lskmzdVoForContacts.setSupQty(lskmsl.getSupQty());
                lskmzdVoForContacts.setDebitQtySup(lskmsl.getDebitQtySup());
                lskmzdVoForContacts.setCreditQtySup(lskmsl.getCreditQtySup());
                lskmzdVoForContacts.setLeftQty(lskmsl.getLeftQty());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lskmzdVoForContacts;
        }
    }

    public List<LswljeQueryVo> getLswljeOrLswlsl(String itemNo ,String accType ) {
        Session session = null;
        Transaction tx = null;
        List<LswljeQueryVo> lswljeQueryVos = new ArrayList<LswljeQueryVo>();
        List<Lswlsl> lswlsls = null;
        try {
            session = getSession();
            tx = session.beginTransaction();

            if(accType.equals("Y")){
                Query queryY = session.createQuery("from Lswlsl where itemNo = ?");
                queryY.setParameter(0,itemNo);
                lswlsls = queryY.list();
            }

            Query queryJ = session.createQuery("from Lswlje where itemNo = ?");
            queryJ.setParameter(0,itemNo);
            List<Lswlje> lswljeList = queryJ.list();

            String companyNo,companyName;
            Lswldw lswldw = null;
            int i = 0;
            Lswlsl lswlsl;
            for(Lswlje lswlje : lswljeList){
                LswljeQueryVo lswljeQueryVo = new LswljeQueryVo();
                companyNo = lswlje.getCompanyNo();
                lswldw = (Lswldw)session.get(Lswldw.class,companyNo);
                companyName = lswldw.getCompanyName();
                lswljeQueryVo.setCompanyName(companyName);
                lswljeQueryVo.setItemNo(itemNo);

                if(lswlje.getSupMoney()  == null || lswlje.getSupMoney().equals("")){
                    lswljeQueryVo.setSupMoney(0.00);
                }else{
                    lswljeQueryVo.setSupMoney(lswlje.getSupMoney());
                }

                if(lswlje.getDebitMoneySup()  == null || lswlje.getDebitMoneySup().equals("")){
                    lswljeQueryVo.setDebitMoneySup(0.00);
                }else{
                    lswljeQueryVo.setDebitMoneySup(lswlje.getDebitMoneySup());
                }

                if(lswlje.getCreditMoneySup()  == null || lswlje.getCreditMoneySup().equals("")){
                    lswljeQueryVo.setCreditMoneySup(0.00);
                }else{
                    lswljeQueryVo.setCreditMoneySup(lswlje.getCreditMoneySup());
                }

                if(lswlje.getBalance()  == null || lswlje.getBalance().equals("")){
                    lswljeQueryVo.setBalance(0.00);
                }else{
                    lswljeQueryVo.setBalance(lswlje.getBalance());
                }

                lswljeQueryVo.setCompanyNo(lswlje.getCompanyNo());

                if(accType.equals("Y")){
                    lswlsl = lswlsls.get(i);

                    if(lswlsl.getCreditQtySup()  == null || lswlsl.getCreditQtySup().equals("")){
                        lswljeQueryVo.setCreditQtySup(0.00);
                    }else{
                        lswljeQueryVo.setCreditQtySup(lswlsl.getCreditQtySup());
                    }

                    if(lswlsl.getDebitQtySup()  == null || lswlsl.getDebitQtySup().equals("")){
                        lswljeQueryVo.setDebitQtySup(0.00);
                    }else{
                        lswljeQueryVo.setDebitQtySup(lswlsl.getDebitQtySup());
                    }

                    if(lswlsl.getLeftQty()  == null || lswlsl.getLeftQty().equals("")){
                        lswljeQueryVo.setLeftQty(0.00);
                    }else{
                        lswljeQueryVo.setLeftQty(lswlsl.getLeftQty());
                    }

                    if(lswlsl.getSupQty()  == null || lswlsl.getSupQty().equals("")){
                        lswljeQueryVo.setSupQty(0.00);
                    }else{
                        lswljeQueryVo.setSupQty(lswlsl.getSupQty());
                    }

                }

                lswljeQueryVos.add(i,lswljeQueryVo);

                i=i+1;
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lswljeQueryVos;
        }
    }
    public List<Lswlsl> getLswlsl(String itemNo) {
        Session session = null;
        Transaction tx = null;
        List<Lswlsl> lswlsls = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query queryY = session.createQuery("from Lswlsl where itemNo = ?");
            queryY.setParameter(0,itemNo);
            lswlsls = queryY.list();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lswlsls;
        }
    }

    public boolean updateLswlje(LswljeQueryVo lswljeQueryVo) {
        Session session = null;
        Transaction tx = null;
        List<Lswlje> lswljes = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            String itemNo = lswljeQueryVo.getItemNo();
            String companyNo = lswljeQueryVo.getCompanyNo();
            Double supMoney = lswljeQueryVo.getSupMoney();
            Double debitMoneySup = lswljeQueryVo.getDebitMoneySup();
            Double creditMoneySup = lswljeQueryVo.getCreditMoneySup();
            Double balance = lswljeQueryVo.getBalance();

            int m = Integer.parseInt(getBeginMonth()) - 1;
            if(m >0 ){
                String last_month = m < 10 ? ("0" + Integer.toString(m)) : Integer.toString(m);
                String string = "balance" + last_month;

                Query queryupdate=session.createQuery("update Lswlje  set supMoney= "+supMoney+", debitMoneySup = "+debitMoneySup+",creditMoneySup = "+creditMoneySup+",balance = "+balance+","+string+" = "+balance+" where itemNo= '"+itemNo+"' and companyNo='"+companyNo+"'");

                int ret=queryupdate.executeUpdate();
            }else{
                Query queryupdate=session.createQuery("update Lswlje je set je.supMoney= "+supMoney+", je.debitMoneySup = "+debitMoneySup+",je.creditMoneySup = "+creditMoneySup+",je.balance = "+balance+" where je.itemNo= '"+itemNo+"' and je.companyNo='"+companyNo+"'");

                int ret=queryupdate.executeUpdate();
            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return true;
        }
    }


    public boolean updateLswljeAndsl(LswljeQueryVo lswljeQueryVo) {
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            String itemNo = lswljeQueryVo.getItemNo();
            String companyNo = lswljeQueryVo.getCompanyNo();
            Double supMoney = lswljeQueryVo.getSupMoney();
            Double debitMoneySup = lswljeQueryVo.getDebitMoneySup();
            Double creditMoneySup = lswljeQueryVo.getCreditMoneySup();
            Double balance = lswljeQueryVo.getBalance();
            Double supQty = lswljeQueryVo.getSupQty();
            Double debitQtySup  = lswljeQueryVo.getDebitQtySup();
            Double creditQtySup = lswljeQueryVo.getCreditQtySup();
            Double leftQty  = lswljeQueryVo.getLeftQty();

            Query queryupdateJe=session.createQuery("update Lswlje je set je.supMoney= "+supMoney+", je.debitMoneySup = "+debitMoneySup+",je.creditMoneySup = "+creditMoneySup+",je.balance = "+balance+" where je.itemNo= '"+itemNo+"' and je.companyNo='"+companyNo+"'");
            queryupdateJe.executeUpdate();

            Query queryupdateSl=session.createQuery("update Lswlsl sl set sl.supQty= "+supQty+", sl.debitQtySup = "+debitQtySup+",sl.creditQtySup = "+creditQtySup+",sl.leftQty = "+leftQty+" where sl.itemNo= '"+itemNo+"' and sl.companyNo='"+companyNo+"'");
            queryupdateSl.executeUpdate();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return true;
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
            Byte b = 1;
            if(levelFlag.equals("1")){
                hql = "from Lskmzd as s where s.item = '"+levelFlag+"' and s.exchang = "+b+"";
            }
            else{
                hql = "from Lskmzd as s where s.itemNo like '"+id+"%' and s.item = '"+levelFlag+"'and s.exchang = "+b+"";
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

    public String getBeginMonth() {
        String month = getConfig("begin_date") != null ? getConfig("begin_date").substring(4, 6) : "0";
        return month;
    }

    public String getConfig(String key) {
        Session session = null;
        Lsconf lsconf = null;
        String value = null;
        try {
            session = getSession();
            lsconf = (Lsconf) session.get(Lsconf.class, key);//根据主键id查找
            value = lsconf.getConfValue();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return value;
        }
    }

}


