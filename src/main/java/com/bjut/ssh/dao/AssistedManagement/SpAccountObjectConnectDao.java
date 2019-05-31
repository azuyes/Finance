package com.bjut.ssh.dao.AssistedManagement;

import com.bjut.ssh.entity.*;
import com.bjut.ssh.dao.FinanceProcess.CaptionOfAccountDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Title: SpAccountObjectConnectDao
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/5/14 10:55
 * @Version: 1.0
 */
@Repository
@Transactional
public class SpAccountObjectConnectDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private CaptionOfAccountDao captionOfAccountDao;

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    public void close(Session session){
        if(session != null)
            session.close();
    }

    public List<Lshszd> getSpAccountName(String spNo,String catNo){
        Session session = null;
        Transaction tx = null;
        List<Lshszd> lshszdList= null;
        String spLevel;
        String no;

        try {
            session = getSession();
            tx = session.beginTransaction();
            if(spNo == null){
                Query query = session.createQuery("from Lshszd l where l.spLevel = ? and l.catNo = ?");
                query.setParameter(0,"1");
                query.setParameter(1,catNo);
                lshszdList = query.list();
            }else{
                Query query2 = session.createQuery("from Lshszd l where l.catNo =? and l.spNo = ?");
                query2.setParameter(0,catNo);
                query2.setParameter(1,spNo);
                lshszdList = query2.list();
                Lshszd lshszd = lshszdList.get(0);

                spLevel = lshszd.getSpLevel();
                String hql = null;
                if (spLevel == "3"){
                    lshszdList = null;
                }else{
                    switch (spLevel){
                        case "1":
                            no = spNo.substring(0,4);
                            hql = "from Lshszd as l where l.spNo like '"+no+"%' and l.spLevel = '2' and l.catNo = '"+catNo+"'";
                            break;
                        case "2":
                            no = spNo.substring(0,8);
                            hql = "from Lshszd as l where l.spNo like '"+no+"%' and l.spLevel = '3' and l.catNo = '"+catNo+"'";
                            break;
                        case "3":
                            return null;
                    }
                    Query query3 = session.createQuery(hql);
                    lshszdList = query3.list();
                }

            }

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
    public Lskmzd getSpItemById(String itemNo) {
        Session session = null;
        Transaction tx = null;
        Lskmzd lskmzd = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            lskmzd = (Lskmzd)session.get(Lskmzd.class,itemNo);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lskmzd;
        }
    }

    public List<Lshsfl> getSpAccountCategory() {
        Session session = null;
        Transaction tx = null;
        List<Lshsfl> lshsfls = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Lshsfl ");
            lshsfls =  query.list();
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

//    public String[] getItemNosByItemNo(String itemNo) {
//        Session session = null;
//        Transaction tx = null;
//        String[] itemNos = new String[7];
//        String upperItemNo;
//        int i;
//        try {
//            session = getSession();
//            tx = session.beginTransaction();
//            Query query = session.createQuery("from Lshsfl ");
//            Lskmzd lskmzd = (Lskmzd)session.get(Lskmzd.class,itemNo);
//            int length = Integer.parseInt(lskmzd.getItem())-1;
//            if(Integer.parseInt(lskmzd.getItem())!=1){
//                for(i=0;i<length;i++){
//                    upperItemNo = captionOfAccountDao.getFullNo(captionOfAccountDao.getCatNo(lskmzd.getItemNo(),lskmzd.getItem()),Integer.parseInt(lskmzd.getItem())-1);
//                    lskmzd = (Lskmzd)session.get(Lskmzd.class,upperItemNo);
//                    itemNos[i] = upperItemNo;
//                }
//                itemNos[i] = itemNo;
//            }else{
//                itemNos[0] = itemNo;
//            }
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//            e.printStackTrace();
//        } finally {
//            //关闭操作
//            close(session);
//            return itemNos;
//        }
//
//
//    }


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

    public boolean existSpAccountConnect1(String spNo1,String itemNo) {
        Session session = null;
        Transaction tx = null;
        Long count = null ;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query= session.createQuery("select  count(*) from Lshsje where spNo1 = ? and itemNo = ?");
            query.setParameter(0,spNo1);
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

    public void spAccountConnect1(String spNo1,String itemNo) {
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Lskmzd lskmzd = (Lskmzd) session.get(Lskmzd.class,itemNo);
            if(lskmzd.getAccType().equals("Y")){ //"Y"代表数量账
                Lshssl lshssl = new Lshssl();
                lshssl.setSpNo1(spNo1);
                lshssl.setItemNo(itemNo);
                lshssl.setSupQty(0.0d);
                lshssl.setDebitQtySup(0.0d);
                lshssl.setCreditQtySup(0.0d);
                lshssl.setDebitQtyAcm(0.0d);
                lshssl.setCreditQtyAcm(0.0d);
                lshssl.setLeftQty(0.0d);
                session.save(lshssl);

            }
            Lshsje lshsje = new Lshsje();
            lshsje.setSpNo1(spNo1);
            lshsje.setItemNo(itemNo);
            lshsje.setSupMoney(0.0d);
            lshsje.setDebitMoneySup(0.0d);
            lshsje.setCreditMoneySup(0.0d);
            lshsje.setDebitMoneyAcm(0.0d);
            lshsje.setCreditMoneyAcm(0.0d);
            lshsje.setBalance(0.0d);
            session.save(lshsje);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
        }
    }

    public boolean existSpAccountConnect2(String spNo1,String spNo2,String itemNo) {
        Session session = null;
        Transaction tx = null;
        Long count = null ;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query= session.createQuery("select  count(*) from Lshsje where spNo1 = ? and spNo2 = ? and itemNo = ?");
            query.setParameter(0,spNo1);
            query.setParameter(1,spNo2);
            query.setParameter(2,itemNo);
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

    public void spAccountConnect2(String spNo1,String spNo2,String itemNo) {
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Lskmzd lskmzd = (Lskmzd) session.get(Lskmzd.class,itemNo);
            if(lskmzd.getAccType().equals("Y")){ //"Y"代表数量账
                Lshssl lshssl = new Lshssl();
                lshssl.setSpNo1(spNo1);
                lshssl.setItemNo(itemNo);
                lshssl.setSpNo2(spNo2);
                lshssl.setSupQty(0.0d);
                lshssl.setDebitQtySup(0.0d);
                lshssl.setCreditQtySup(0.0d);
                lshssl.setDebitQtyAcm(0.0d);
                lshssl.setCreditQtyAcm(0.0d);
                lshssl.setLeftQty(0.0d);
                session.save(lshssl);
            }
            Lshsje lshsje = new Lshsje();
            lshsje.setSpNo1(spNo1);
            lshsje.setSpNo2(spNo2);
            lshsje.setItemNo(itemNo);
            lshsje.setSupMoney(0.0d);
            lshsje.setDebitMoneySup(0.0d);
            lshsje.setCreditMoneySup(0.0d);
            lshsje.setDebitMoneyAcm(0.0d);
            lshsje.setCreditMoneyAcm(0.0d);
            lshsje.setBalance(0.0d);
            //lshsje.setS
            session.save(lshsje);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
        }
    }


    public String getAuthority1(String itemNo){
        Session session = null;
        Transaction tx = null;
        String FunctionAuthority1 = "";
        try {
            session=getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Lshsje where itemNo='"+itemNo+"'");
            List<Lshsje> lshsjes = query.list();
            for(Lshsje lshsje : lshsjes){
                String spNo1 = lshsje.getSpNo1();
                if (FunctionAuthority1 != "")
                    FunctionAuthority1 += ",";
                FunctionAuthority1 += spNo1;
            }
            tx.commit();
            close(session);
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally {
            return FunctionAuthority1;
        }

    }

    public String getAuthority2(String itemNo){
        Session session = null;
        Transaction tx = null;
        String FunctionAuthority2 = "";
        try {
            session=getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Lshsje where itemNo='"+itemNo+"'");
            List<Lshsje> lshsjes = query.list();
            for(Lshsje lshsje : lshsjes){
                String spNo2 = lshsje.getSpNo2();
                if (FunctionAuthority2 != "")
                    FunctionAuthority2 += ",";
                FunctionAuthority2 += spNo2;
            }
            tx.commit();
            close(session);
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally {
            return FunctionAuthority2;
        }

    }


    public Lskmzd getItemNo(String itemNo){
        Session session = null;
        Transaction tx = null;
        Lskmzd lskmzd = new Lskmzd();
        try {
            session=getSession();
            tx = session.beginTransaction();
            lskmzd = (Lskmzd)session.get(Lskmzd.class,itemNo);
            tx.commit();
            close(session);
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally {
            return lskmzd;
        }

    }


    public boolean judgeUnChecked1(String spNo,String itemNo,String accType,String supAcc1){
        Session session = null;
        Transaction tx = null;
        boolean bool = true;
        double balance,debitMoneyAcm,creditMoneyAcm,supMoney;
        double leftNo,supQty,debitQtyAcm,creditQtyAcm;
        try {
            session=getSession();
            Query query;
            tx = session.beginTransaction();
            Lshsje lshsje = new Lshsje();
            Lshssl lshssl = new Lshssl();


            lshsje = queryLshsje(session,spNo,itemNo);
            bool = judgeJe1(lshsje);

            if(accType.equals("Y")){
                lshssl = queryLshssl(session,spNo,itemNo);
                bool = judgeSl1(lshssl);

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

    public boolean deleteUnChecked1(String spNo,String itemNo,String accType,String supAcc1){
        Session session = null;
        Transaction tx = null;
        boolean bool = true;
        try {
            session=getSession();
            Query query;
            query = session.createQuery("from Lshszd zd where zd.catNo = '"+supAcc1+"' and zd.spNo ='"+spNo+"'");
            List<Lshszd> lshszds = query.list();
            Lshszd lshszd = lshszds.get(0);

            String level = lshszd.getSpLevel();

            tx = session.beginTransaction();
            Lshsje lshsje = new Lshsje();
            Lshssl lshssl = new Lshssl();

            String spNo1,spNo2;

            switch (level){
                case "1":
                    lshsje = queryLshsje(session,spNo,itemNo);
                    if(judgeJe1(lshsje)){
                        session.delete(lshsje);
                    }
                    break;
                case "2":
                    lshsje = queryLshsje(session,spNo,itemNo);
                    if(judgeJe1(lshsje)){
                        session.delete(lshsje);
                    }
                    spNo1 = spNo.substring(0,4);
                    spNo1 = spNo1+"00000000";
                    lshsje = queryLshsje(session,spNo1,itemNo);
                    if(judgeJe1(lshsje)){
                        session.delete(lshsje);
                    }
                    break;
                case "3":
                    lshsje = queryLshsje(session,spNo,itemNo);
                    if(judgeJe1(lshsje)){
                        session.delete(lshsje);
                    }
                    spNo1 = spNo.substring(0,4);
                    spNo1 = spNo1+"00000000";
                    lshsje = queryLshsje(session,spNo1,itemNo);
                    if(judgeJe1(lshsje)){
                        session.delete(lshsje);
                    }
                    spNo2 = spNo.substring(0,8);
                    spNo2 = spNo2+"0000";
                    lshsje = queryLshsje(session,spNo2,itemNo);
                    if(judgeJe1(lshsje)){
                        session.delete(lshsje);
                    }
                    break;
            }

            if(accType.equals("Y")){

                switch (level){
                    case "1":
                        lshssl = queryLshssl(session,spNo,itemNo);
                        if(judgeSl1(lshssl)){
                            session.delete(lshssl);
                        }
                        break;
                    case "2":
                        lshssl = queryLshssl(session,spNo,itemNo);
                        if(judgeSl1(lshssl)){
                            session.delete(lshssl);
                        }
                        spNo1 = spNo.substring(0,4);
                        spNo1 = spNo1+"00000000";
                        lshssl = queryLshssl(session,spNo1,itemNo);
                        if(judgeSl1(lshssl)){
                            session.delete(lshssl);
                        }
                        break;
                    case "3":
                        lshssl = queryLshssl(session,spNo,itemNo);
                        if(judgeSl1(lshssl)){
                            session.delete(lshssl);
                        }
                        spNo1 = spNo.substring(0,4);
                        spNo1 = spNo1+"00000000";
                        lshssl = queryLshssl(session,spNo1,itemNo);
                        if(judgeSl1(lshssl)){
                            session.delete(lshssl);
                        }
                        spNo2 = spNo.substring(0,8);
                        spNo2 = spNo2+"0000";
                        lshssl = queryLshssl(session,spNo2,itemNo);
                        if(judgeSl1(lshssl)){
                            session.delete(lshssl);
                        }
                        break;
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

    public boolean deleteUnChecked2(String spNo,String itemNo,String accType,String supAcc1){
        Session session = null;
        Transaction tx = null;
        boolean bool = true;
        try {
            session=getSession();
            Query query;
            query = session.createQuery("from Lshszd zd where zd.catNo = '"+supAcc1+"' and zd.spNo ='"+spNo+"'");
            List<Lshszd> lshszds = query.list();
            Lshszd lshszd = lshszds.get(0);

            String level = lshszd.getSpLevel();

            tx = session.beginTransaction();
            List<Lshsje> lshsjes ;
            List<Lshssl> lshssls ;

            String spNo1,spNo2;

            switch (level){
                case "1":
                    lshsjes = queryLshsje2(session,spNo,itemNo);
                    for(Lshsje lshsje : lshsjes){
                        if(judgeJe1(lshsje)){
                            session.delete(lshsje);
                        }
                    }
                    break;
                case "2":
                    lshsjes = queryLshsje2(session,spNo,itemNo);
                    for(Lshsje lshsje : lshsjes){
                        if(judgeJe1(lshsje)){
                            session.delete(lshsje);
                        }
                    }
                    spNo1 = spNo.substring(0,4);
                    spNo1 = spNo1+"00000000";
                    lshsjes = queryLshsje2(session,spNo1,itemNo);
                    for(Lshsje lshsje : lshsjes){
                        if(judgeJe1(lshsje)){
                            session.delete(lshsje);
                        }
                    }
                    break;
                case "3":
                    lshsjes = queryLshsje2(session,spNo,itemNo);
                    for(Lshsje lshsje : lshsjes){
                        if(judgeJe1(lshsje)){
                            session.delete(lshsje);
                        }
                    }
                    spNo1 = spNo.substring(0,4);
                    spNo1 = spNo1+"00000000";
                    lshsjes = queryLshsje2(session,spNo1,itemNo);
                    for(Lshsje lshsje : lshsjes){
                        if(judgeJe1(lshsje)){
                            session.delete(lshsje);
                        }
                    }
                    spNo2 = spNo.substring(0,8);
                    spNo2 = spNo2+"0000";
                    lshsjes = queryLshsje2(session,spNo2,itemNo);
                    for(Lshsje lshsje : lshsjes){
                        if(judgeJe1(lshsje)){
                            session.delete(lshsje);
                        }
                    }
                    break;
            }

            if(accType.equals("Y")){

                switch (level){
                    case "1":
                        lshssls = queryLshssl2(session,spNo,itemNo);
                        for(Lshssl lshssl : lshssls){
                            if(judgeSl1(lshssl)){
                                session.delete(lshssl);
                            }
                        }
                        break;
                    case "2":
                        lshssls = queryLshssl2(session,spNo,itemNo);
                        for(Lshssl lshssl : lshssls){
                            if(judgeSl1(lshssl)){
                                session.delete(lshssl);
                            }
                        }
                        spNo1 = spNo.substring(0,4);
                        spNo1 = spNo1+"00000000";
                        lshssls = queryLshssl2(session,spNo1,itemNo);
                        for(Lshssl lshssl : lshssls){
                            if(judgeSl1(lshssl)){
                                session.delete(lshssl);
                            }
                        }
                        break;
                    case "3":
                        lshssls = queryLshssl2(session,spNo,itemNo);
                        for(Lshssl lshssl : lshssls){
                            if(judgeSl1(lshssl)){
                                session.delete(lshssl);
                            }
                        }
                        spNo1 = spNo.substring(0,4);
                        spNo1 = spNo1+"00000000";
                        lshssls = queryLshssl2(session,spNo1,itemNo);
                        for(Lshssl lshssl : lshssls){
                            if(judgeSl1(lshssl)){
                                session.delete(lshssl);
                            }
                        }
                        spNo2 = spNo.substring(0,8);
                        spNo2 = spNo2+"0000";
                        lshssls = queryLshssl2(session,spNo2,itemNo);
                        for(Lshssl lshssl : lshssls){
                            if(judgeSl1(lshssl)){
                                session.delete(lshssl);
                            }
                        }
                        break;
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




    public boolean judgeUnChecked(String spNo,String itemNo,String accType,String supAcc1){
        Session session = null;
        Transaction tx = null;
        boolean bool = true;
        try {
            session=getSession();
            Query query;
            tx = session.beginTransaction();
            query = session.createQuery("from Lshszd zd where zd.catNo = '"+supAcc1+"' and zd.spNo ='"+spNo+"'");
            List<Lshszd> lshszds = query.list();
            Lshszd lshszd = lshszds.get(0);

            String level = lshszd.getSpLevel();
            Lshsje lshsje = new Lshsje();
            Lshssl lshssl = new Lshssl();

            String spNo1,spNo2;

            switch (level){
                case "1":
                    lshsje = queryLshsje(session,spNo,itemNo);
                    bool = judgeJe1(lshsje);
                    break;
                case "2":
                    lshsje = queryLshsje(session,spNo,itemNo);
                    bool = judgeJe1(lshsje);
                    spNo1 = spNo.substring(0,4);
                    spNo1 = spNo1+"00000000";
                    lshsje = queryLshsje(session,spNo1,itemNo);
                    bool = judgeJe1(lshsje);
                    break;
                case "3":
                    lshsje = queryLshsje(session,spNo,itemNo);
                    bool = judgeJe1(lshsje);
                    spNo1 = spNo.substring(0,4);
                    spNo1 = spNo1+"00000000";
                    lshsje = queryLshsje(session,spNo1,itemNo);
                    bool = judgeJe1(lshsje);
                    spNo2 = spNo.substring(0,8);
                    spNo2 = spNo2+"0000";
                    lshsje = queryLshsje(session,spNo2,itemNo);
                    bool = judgeJe1(lshsje);
                    break;
            }

            if(accType.equals('Y')){

                switch (level){
                    case "1":
                        lshssl = queryLshssl(session,spNo,itemNo);
                        bool = judgeSl1(lshssl);
                        break;
                    case "2":
                        lshssl = queryLshssl(session,spNo,itemNo);
                        bool = judgeSl1(lshssl);
                        spNo1 = spNo.substring(0,4);
                        spNo1 = spNo1+"00000000";
                        lshssl = queryLshssl(session,spNo1,itemNo);
                        bool = judgeSl1(lshssl);
                        break;
                    case "3":
                        lshssl = queryLshssl(session,spNo,itemNo);
                        bool = judgeSl1(lshssl);
                        spNo1 = spNo.substring(0,4);
                        spNo1 = spNo1+"00000000";
                        lshssl = queryLshssl(session,spNo1,itemNo);
                        bool = judgeSl1(lshssl);
                        spNo2 = spNo.substring(0,8);
                        spNo2 = spNo2+"0000";
                        lshssl = queryLshssl(session,spNo2,itemNo);
                        bool = judgeSl1(lshssl);
                        break;
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

    private boolean judgeJe1(Lshsje lshsje){
        double balance,debitMoneyAcm,creditMoneyAcm,supMoney;
        boolean bool = true;
        balance = lshsje.getBalance();
        debitMoneyAcm = lshsje.getDebitMoneyAcm();
        creditMoneyAcm = lshsje.getCreditMoneyAcm();
        supMoney = lshsje.getSupMoney();
        if(balance == 0 && debitMoneyAcm == 0 && creditMoneyAcm == 0 && supMoney ==0){
            bool = true;
        }else{
            bool = false;
        }
        return bool;
    }

    private Lshsje queryLshsje(Session session,String spNo,String itemNo){
        Query query = session.createQuery("from Lshsje je where je.spNo1 = '"+spNo+"' and je.itemNo = '"+itemNo+"'");
        List<Lshsje> lshsjes = query.list();
        Lshsje lshsje = lshsjes.get(0);
        return lshsje;
    }

    private List<Lshsje> queryLshsje2(Session session,String spNo,String itemNo){
        Query query = session.createQuery("from Lshsje je where je.spNo1 = '"+spNo+"' and je.itemNo = '"+itemNo+"'");
        List<Lshsje> lshsjes = query.list();
        return lshsjes;
    }

    private boolean judgeSl1(Lshssl lshssl){
        double leftQty,supQty,debitQtyAcm,creditQtyAcm;
        boolean bool = true;
        leftQty = lshssl.getLeftQty();
        supQty = lshssl.getSupQty();
        debitQtyAcm = lshssl.getDebitQtyAcm();
        creditQtyAcm = lshssl.getCreditQtyAcm();
        if(leftQty == 0 && supQty == 0 && debitQtyAcm == 0 && creditQtyAcm ==0){
            bool = true;
        }else{
            bool = false;
        }
        return bool;
    }

    private Lshssl queryLshssl(Session session,String spNo,String itemNo){
        Query query = session.createQuery("from Lshssl sl where sl.spNo1 = '"+spNo+"' and sl.itemNo = '"+itemNo+"'");
        List<Lshssl> lshssls = query.list();
        Lshssl lshssl = lshssls.get(0);
        return lshssl;
    }

    private List<Lshssl> queryLshssl2(Session session,String spNo,String itemNo){
        Query query = session.createQuery("from Lshssl sl where sl.spNo1 = '"+spNo+"' and sl.itemNo = '"+itemNo+"'");
        List<Lshssl> lshssls = query.list();
        return lshssls;
    }


    public String  getCatNameBySpItemNo(String catNo){
        Session session = null;
        Transaction tx = null;
        String catName=null;
        try {
            session=getSession();
            tx = session.beginTransaction();
            Lshsfl lshsfl = (Lshsfl) session.get(Lshsfl.class,catNo);
            catName = lshsfl.getCatName();
            tx.commit();
            close(session);
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally {
            return catName;
        }

    }

}
