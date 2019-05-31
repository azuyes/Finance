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
 * @Title: ContactsManagementDao
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/9 13:55
 * @Version: 1.0
 */

@Repository
@Transactional
public class ContactsManagementDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    public void close(Session session){
        if(session != null)
            session.close();
    }

    public List<Lswldw> getContactsConmpany() {
        Session session = null;
        Transaction tx = null;
        List<Lswldw> lswldwList = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Lswldw");
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

    public String getCatNameOfLswlfl(String catNo1){
        Session session = null;
        Transaction tx = null;
        String catName="";
        try {
            session = getSession();
            tx = session.beginTransaction();
            Lswlfl lswlfl = (Lswlfl)session.get(Lswlfl.class,catNo1);
            tx.commit();
            catName = lswlfl.getCatName1();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return catName;
        }
    }


    public List<Lswlfl> getLswlfl(String catNo1){
        Session session = null;
        Transaction tx = null;
        List<Lswlfl> lswlflList= null;
        String catLevel;
        String no;

        try {
            session = getSession();
            tx = session.beginTransaction();
            if(catNo1 == null){
                Query query = session.createQuery("from Lswlfl l where l.catLevel = ? ");
                query.setParameter(0,"1");
                lswlflList = query.list();
            }else{
                Lswlfl lswlfl = (Lswlfl)session.get(Lswlfl.class,catNo1);

                catLevel = lswlfl.getCatLevel();
                String hql = null;
                if (catLevel.equals("3")){
                    lswlflList = null;
                }else{
                    switch (catLevel){
                        case "1":
                            no = catNo1.substring(0,2);
                            hql = "from Lswlfl as l where l.catNo1 like '"+no+"%' and l.catLevel = '2'";
                            break;
                        case "2":
                            no = catNo1.substring(0,4);
                            hql = "from Lswlfl as l where l.catNo1 like '"+no+"%' and l.catLevel = '3'";
                            break;
                        case "3":
                            return null;
                    }
                    Query query3 = session.createQuery(hql);
                    lswlflList = query3.list();
                }

            }

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

    public boolean saveContactsCategory(Lswldw lswldw){
        Session session = null;
        Transaction tx = null;
        List<Lswldw> lswldwList = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Lswldw where companyNo = '" + lswldw.getCompanyNo() + "'");
            lswldwList = query.list();
            if (lswldwList.size() > 0) {
                close(session);
                return false;
            } else {
                session.save(lswldw);
                tx.commit();
                //关闭操作
                close(session);
                return true;
            }
        }catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public Lswldw getContactsCompanyById(String companyNo) {
        Session session = null;
        Transaction tx = null;
        Lswldw lswldw = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            lswldw = (Lswldw) session.get(Lswldw.class, companyNo);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lswldw;
        }
    }

    public boolean updateContactsCompanyById(Lswldw lswldw) {
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();

            Lswldw lswldwBefore = (Lswldw)session.get(Lswldw.class,lswldw.getCompanyNo());
            lswldwBefore.setCompanyName(lswldw.getCompanyName());
            lswldwBefore.setCont(lswldw.getCont());
            lswldwBefore.setTel(lswldw.getTel());
            lswldwBefore.setCompanyAddr(lswldw.getCompanyAddr());
            lswldwBefore.setCompanyPost(lswldw.getCompanyPost());
            lswldwBefore.setBank(lswldw.getBank());
            lswldwBefore.setAccount(lswldw.getAccount());
            lswldwBefore.setCreditStanding(lswldw.getCreditStanding());
            lswldwBefore.setTaxIdNo(lswldw.getTaxIdNo());
            lswldwBefore.setMemo(lswldw.getMemo());
            lswldwBefore.setCatNo1(lswldw.getCatNo1());

            session.update(lswldwBefore);
            tx.commit();
            close(session);
            return true;
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public List<Lswldw> getContactsCompanyOfDefin() {
        Session session = null;
        Transaction tx = null;
        List<Lswldw> lswldwList = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query= session.createQuery("from Lswldw ");
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

    public List<Lskmzd> getSpecialItemOfDefin() {
        Session session = null;
        Transaction tx = null;
        List<Lskmzd> lskmzdList = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query= session.createQuery("from Lskmzd where exchang = 1 and finLevel = 1");
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

    public boolean existOfCompanyAndItem(String companyNo,String itemNo) {
        Session session = null;
        Transaction tx = null;
        Long count = null ;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query= session.createQuery("select  count(*) from Lswlje where companyNo = ? and itemNo = ?");
            query.setParameter(0,companyNo);
            query.setParameter(1,itemNo);
            count = (Long)query.uniqueResult();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return count>0?true:false;
        }
    }

    public void defineOfCompanyAndItem(String companyNo,String itemNo) {
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Lskmzd lskmzd = (Lskmzd) session.get(Lskmzd.class,itemNo);
            if(lskmzd.getAccType().equals("Y")){ //"Y"代表数量账
                Lswlsl lswlsl = new Lswlsl();
                lswlsl.setCompanyNo(companyNo);
                lswlsl.setItemNo(itemNo);
                lswlsl.setLeftQty(0.00);
                lswlsl.setSupQty(0.00);
                lswlsl.setDebitQty(0.00);
                lswlsl.setCreditQty(0.00);
                session.save(lswlsl);
            }
            Lswlje lswlje = new Lswlje();
            lswlje.setCompanyNo(companyNo);
            lswlje.setItemNo(itemNo);
            lswlje.setBalance(0.00);
            lswlje.setSupMoney(0.00);
            lswlje.setDebitMoneyAcm(0.00);
            lswlje.setCreditMoneyAcm(0.00);
            session.save(lswlje);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
        }
    }


    public List<Lswlje> getDefinedOfCompanyAndItem() {
        Session session = null;
        Transaction tx = null;
        List<Lswlje> lswljeList = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query= session.createQuery("from Lswlje");
            lswljeList = query.list();
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
    public boolean judgeUnCheckedCompany(String companyNo){
        Session session = null;
        Transaction tx = null;
        boolean bool = true;
        List<Lswlje> lswljeList;
        List<Lswlsl> lswlslList;
        try {
            session=getSession();
            Query query;
            tx = session.beginTransaction();
            query = session.createQuery("from Lswlje where companyNo = ?");
            query.setParameter(0,companyNo);
            lswljeList = query.list();
            for(Lswlje lswlje : lswljeList){
                if(lswlje.getBalance() == 0 && lswlje.getSupMoney() == 0 && lswlje.getDebitMoneySup() == 0 && lswlje.getCreditMoneySup() ==0){
                    bool =  true;
                }else{
                    bool =  false;
                    break;
                }
            }
            query = session.createQuery("from Lswlsl where companyNo = ?");
            query.setParameter(0,companyNo);
            lswlslList = query.list();
            if(lswlslList.size()>0){
                for(Lswlsl lswlsl : lswlslList){
                    if(lswlsl.getLeftQty() == 0 && lswlsl.getSupQty() == 0 && lswlsl.getDebitQtySup() == 0 && lswlsl.getCreditQtySup() ==0){
                        bool =  true;
                    }else{
                        bool =  false;
                        break;
                    }
                }
            }

            tx.commit();
            close(session);
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally {
            return bool;
        }

    }

    public boolean deleteUnCheckedCompany(String companyNo){
        Session session = null;
        Transaction tx = null;
        boolean bool = true;
        List<Lswlje> lswljeList;
        List<Lswlsl> lswlslList;
        try {
            session=getSession();
            Query query;
            tx = session.beginTransaction();
            query = session.createQuery("from Lswlje where companyNo = ?");
            query.setParameter(0,companyNo);
            lswljeList = query.list();
            for(Lswlje lswlje : lswljeList){
                session.delete(lswlje);
            }
            query = session.createQuery("from Lswlsl where companyNo = ?");
            query.setParameter(0,companyNo);
            lswlslList = query.list();
            if(lswlslList.size()>0){
                for(Lswlsl lswlsl : lswlslList){
                    session.delete(lswlsl);
                }
            }

            tx.commit();
            close(session);
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally {
            return bool;
        }

    }

    public boolean judgeUnCheckedItem(String itemNo){
        Session session = null;
        Transaction tx = null;
        boolean bool = true;
        List<Lswlje> lswljeList;
        List<Lswlsl> lswlslList;
        try {
            session=getSession();
            Query query;
            tx = session.beginTransaction();
            query = session.createQuery("from Lswlje where itemNo = ?");
            query.setParameter(0,itemNo);
            lswljeList = query.list();
            for(Lswlje lswlje : lswljeList){
                if(lswlje.getBalance() == 0 && lswlje.getSupMoney() == 0 && lswlje.getDebitMoneySup() == 0 && lswlje.getCreditMoneySup() ==0){
                    bool =  true;
                }else{
                    bool =  false;
                    break;
                }
            }
            query = session.createQuery("from Lswlsl where itemNo = ?");
            query.setParameter(0,itemNo);
            lswlslList = query.list();
            if(lswlslList.size()>0){
                for(Lswlsl lswlsl : lswlslList){
                    if(lswlsl.getLeftQty() == 0 && lswlsl.getSupQty() == 0 && lswlsl.getDebitQtySup() == 0 && lswlsl.getCreditQtySup() ==0){
                        bool =  true;
                    }else{
                        bool =  false;
                        break;
                    }
                }
            }

            tx.commit();
            close(session);
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally {
            return bool;
        }

    }

    public boolean deleteUnCheckedItem(String itemNo){
        Session session = null;
        Transaction tx = null;
        boolean bool = true;
        List<Lswlje> lswljeList;
        List<Lswlsl> lswlslList;
        try {
            session=getSession();
            Query query;
            tx = session.beginTransaction();
            query = session.createQuery("from Lswlje where itemNo = ?");
            query.setParameter(0,itemNo);
            lswljeList = query.list();
            for(Lswlje lswlje : lswljeList){
                session.delete(lswlje);
            }
            query = session.createQuery("from Lswlsl where itemNo = ?");
            query.setParameter(0,itemNo);
            lswlslList = query.list();
            if(lswlslList.size()>0){
                for(Lswlsl lswlsl : lswlslList){
                    session.delete(lswlsl);
                }
            }

            tx.commit();
            close(session);
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally {
            return bool;
        }

    }

    public boolean deleteContactsCompanyById(String companyNo){
        Session session = null;
        Transaction tx = null;
        boolean bool = true;
        try {
            session=getSession();
            Query query;
            tx = session.beginTransaction();
            query = session.createQuery("from Lswlje where companyNo = ?");
            query.setParameter(0,companyNo);
            List<Lswlje> lswljeList = query.list();

            for (Lswlje lswlje: lswljeList) {
                if(lswlje.getBalance() != 0 || lswlje.getDebitMoney() != 0
                        || lswlje.getCreditMoney() != 0 || lswlje.getSupMoney() != 0
                        || lswlje.getDebitMoneySup() != 0 || lswlje.getCreditMoneySup() != 0
                        || lswlje.getDebitMoneyAcm() != 0 || lswlje.getCreditMoneyAcm() != 0
                        || lswlje.getDebitMoney01() != 0 || lswlje.getCreditMoney01() != 0 || lswlje.getBalance01() != 0
                        || lswlje.getDebitMoney02() != 0 || lswlje.getCreditMoney02() != 0 || lswlje.getBalance02() != 0
                        || lswlje.getDebitMoney03() != 0 || lswlje.getCreditMoney03() != 0 || lswlje.getBalance03() != 0
                        || lswlje.getDebitMoney04() != 0 || lswlje.getCreditMoney04() != 0 || lswlje.getBalance04() != 0
                        || lswlje.getDebitMoney05() != 0 || lswlje.getCreditMoney05() != 0 || lswlje.getBalance05() != 0
                        || lswlje.getDebitMoney06() != 0 || lswlje.getCreditMoney06() != 0 || lswlje.getBalance06() != 0
                        || lswlje.getDebitMoney07() != 0 || lswlje.getCreditMoney07() != 0 || lswlje.getBalance07() != 0
                        || lswlje.getDebitMoney08() != 0 || lswlje.getCreditMoney08() != 0 || lswlje.getBalance08() != 0
                        || lswlje.getDebitMoney09() != 0 || lswlje.getCreditMoney09() != 0 || lswlje.getBalance09() != 0
                        || lswlje.getDebitMoney10() != 0 || lswlje.getCreditMoney10() != 0 || lswlje.getBalance10() != 0
                        || lswlje.getDebitMoney11() != 0 || lswlje.getCreditMoney11() != 0 || lswlje.getBalance11() != 0
                        || lswlje.getDebitMoney12() != 0 || lswlje.getCreditMoney12() != 0 || lswlje.getBalance12() != 0
                        ){
                    bool = false;
                    break;
                }
            }

            if(bool){
                Lswldw lswldw = (Lswldw)session.get(Lswldw.class,companyNo);
                session.delete(lswldw);
                bool = true;
            }

            tx.commit();
            close(session);
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally {
            return bool;
        }

    }


    public List<Lskmzd> getItemNosByItemNo(String itemNo) {
        Session session = null;
        Transaction tx = null;
        List<Lskmzd> lskmzdList = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            String conf = "sub_stru";
            Query query = session.createQuery("from Lsconf where confKey = ?");
            query.setParameter(0,conf);
            List<Lsconf> lsconfs = query.list();
            Lsconf lsconf = lsconfs.get(0);
            int length = lsconf.getConfValue().charAt(0)-'0';

            String no = itemNo.substring(0,length);
            query = session.createQuery("from Lskmzd where itemNo like '"+ no +"%'");
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

}