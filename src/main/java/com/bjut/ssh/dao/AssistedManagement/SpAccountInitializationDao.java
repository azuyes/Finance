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
 * @Title: SpAccountInitializationDao
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/5/28 9:44
 * @Version: 1.0
 */
@Repository
@Transactional
public class SpAccountInitializationDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    public void close(Session session){
        if(session != null)
            session.close();
    }

    public List<Lskmzd> queryAccountByLevel(String id, String levelFlag) {
        Session session = null;
        Transaction tx = null;
        List<Lskmzd> LskmzdList = null;
        String hql;
        try {
            session = getSession();
            tx = session.beginTransaction();
            if(levelFlag.equals("1")){
                hql = "from Lskmzd as s where  s.item = '"+levelFlag+"' and s.supAcc1 is not null and s.supAcc1 <> ''";
            }
            else{
                hql = "from Lskmzd as s where s.itemNo like '"+id+"%' and s.item = '"+levelFlag+"' and s.supAcc1 is not null and s.supAcc1 <> ''";
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

    public LskmzdVoForSpecial getItemById(String itemNo) {
        Session session = null;
        Transaction tx = null;
        Lskmzd lskmzd = null;
        Lskmsl lskmsl = null;
        LskmzdVoForSpecial lskmzdVoForSpecial = new LskmzdVoForSpecial();
        try {
            session = getSession();
            tx = session.beginTransaction();
            lskmzd = (Lskmzd)session.get(Lskmzd.class,itemNo);
            lskmzdVoForSpecial.setItemNo(lskmzd.getItemNo());
            lskmzdVoForSpecial.setItemName(lskmzd.getItemName());
            lskmzdVoForSpecial.setItem(lskmzd.getItem());
            lskmzdVoForSpecial.setAccType(lskmzd.getAccType());
            lskmzdVoForSpecial.setFinLevel(lskmzd.getFinLevel());
            lskmzdVoForSpecial.setSupAcc1(lskmzd.getSupAcc1());
            lskmzdVoForSpecial.setSupAcc2(lskmzd.getSupAcc2());
            lskmzdVoForSpecial.setBalance(lskmzd.getBalance());
            lskmzdVoForSpecial.setDebitMoneySup(lskmzd.getDebitMoneySup());
            lskmzdVoForSpecial.setCreditMoneySup(lskmzd.getCreditMoneySup());
            lskmzdVoForSpecial.setSupMoney(lskmzd.getSupMoney());

            if(lskmzd.getAccType().equals("Y")){
                Query query = session.createQuery("from Lskmsl lskmsl where lskmsl.itemNo = '"+itemNo+"'");
                List<Lskmsl> lskmsls = query.list();
                lskmsl = lskmsls.get(0);
                lskmzdVoForSpecial.setSupQty(lskmsl.getSupQty());
                lskmzdVoForSpecial.setDebitQtySup(lskmsl.getDebitQtySup());
                lskmzdVoForSpecial.setCreditQtySup(lskmsl.getCreditQtySup());
                lskmzdVoForSpecial.setLeftQty(lskmsl.getLeftQty());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lskmzdVoForSpecial;
        }
    }

    public List<LshsjeOrLshsslQueryVo> getLshsjeOrLshssl(String itemNo , String accType , String spLevel ,String catNo1 ,String catNo2) {
        Session session = null;
        Transaction tx = null;
        List<LshsjeOrLshsslQueryVo> lshsjeOrLshsslQueryVos = new ArrayList<LshsjeOrLshsslQueryVo>();
        List<Lshssl> lshssls = null;
        try {
            session = getSession();
            tx = session.beginTransaction();

            if(accType.equals("Y")){
                Query queryY = session.createQuery("from Lshssl where itemNo = ?");
                queryY.setParameter(0,itemNo);
                lshssls = queryY.list();
            }

            Query queryJ = session.createQuery("from Lshsje where itemNo = ?");
            queryJ.setParameter(0,itemNo);
            List<Lshsje> lshsjes = queryJ.list();

            String spNo1,spName1,spNo2,spName2;
            int i = 0;
            for(Lshsje lshsje : lshsjes){

                Query query = session.createQuery("from Lshszd lshszd where lshszd.spNo = '"+lshsje.getSpNo1()+"' and lshszd.catNo = '"+catNo1+"'");
                List<Lshszd> lshszds = query.list();
                Lshszd lshszd1 = lshszds.get(0);
                String level = lshszd1.getSpLevel();
                if(spLevel.equals(level)){
                    LshsjeOrLshsslQueryVo lshsjeOrLshsslQueryVo = new LshsjeOrLshsslQueryVo();

                    spNo1 = lshsje.getSpNo1();
                    spNo2 = lshsje.getSpNo2();
                    lshsjeOrLshsslQueryVo.setSpNo1(spNo1);
                    lshsjeOrLshsslQueryVo.setSpNo2(spNo2);

                    spName1 = lshszd1.getSpName();
                    lshsjeOrLshsslQueryVo.setSpName1(spName1);
                    lshsjeOrLshsslQueryVo.setFinLevel(lshszd1.getFinLevel());


                    lshsjeOrLshsslQueryVo.setSupMoney(lshsje.getSupMoney());
                    lshsjeOrLshsslQueryVo.setDebitMoneySup(lshsje.getDebitMoneySup());
                    lshsjeOrLshsslQueryVo.setCreditMoneySup(lshsje.getCreditMoneySup());
                    lshsjeOrLshsslQueryVo.setBalance(lshsje.getBalance());

                    if(accType.equals("Y")){
                        Query query3 =session.createQuery("from Lshssl lshssl where lshssl.spNo1 = '"+spNo1+"' and lshssl.itemNo = '"+itemNo+"'");
                        List<Lshssl> lshssls1 = query3.list();
                        Lshssl lshssl = lshssls1.get(0);


                        lshsjeOrLshsslQueryVo.setCreditQtySup(lshssl.getCreditQtySup());
                        lshsjeOrLshsslQueryVo.setDebitQtySup(lshssl.getDebitQtySup());
                        lshsjeOrLshsslQueryVo.setLeftQty(lshssl.getLeftQty());
                        lshsjeOrLshsslQueryVo.setSupQty(lshssl.getSupQty());
                    }

                    lshsjeOrLshsslQueryVos.add(i,lshsjeOrLshsslQueryVo);
                    i=i+1;
                }
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lshsjeOrLshsslQueryVos;
        }
    }

    public List<LshsjeOrLshsslQueryVo> queryContactsCategoryByLevel(String id,String accType,String levelFlag,String itemNo,String catNo1){
        Session session = null;
        Transaction tx = null;
        List<Lshsje> lshsjes = null;
        String hql;
        int i = 0;
        List<LshsjeOrLshsslQueryVo> lshsjeOrLshsslQueryVos = new ArrayList<LshsjeOrLshsslQueryVo>();
        try {
            session = getSession();
            tx = session.beginTransaction();

            String spNo1,spName1,spNo2,spName2;

            Query query;

                hql = "from Lshsje as s where s.spNo1 like '"+id+"%' and s.itemNo = '"+itemNo+"'";
                query = session.createQuery(hql);
                lshsjes = query.list();
                for(Lshsje lshsje : lshsjes){
                    query = session.createQuery("from Lshszd lshszd where lshszd.spNo = '"+lshsje.getSpNo1()+"' and lshszd.catNo = '"+catNo1+"'");
                    List<Lshszd> lshszds = query.list();
                    Lshszd lshszd1 = lshszds.get(0);
                    String level = lshszd1.getSpLevel();
                    if(levelFlag.equals(level)) {

                        LshsjeOrLshsslQueryVo lshsjeOrLshsslQueryVo = new LshsjeOrLshsslQueryVo();

                        spNo1 = lshsje.getSpNo1();
                        lshsjeOrLshsslQueryVo.setSpNo1(spNo1);

                        spName1 = lshszd1.getSpName();
                        lshsjeOrLshsslQueryVo.setSpName1(spName1);
                        lshsjeOrLshsslQueryVo.setFinLevel(lshszd1.getFinLevel());



                        lshsjeOrLshsslQueryVo.setSupMoney(lshsje.getSupMoney());
                        lshsjeOrLshsslQueryVo.setDebitMoneySup(lshsje.getDebitMoneySup());
                        lshsjeOrLshsslQueryVo.setCreditMoneySup(lshsje.getCreditMoneySup());
                        lshsjeOrLshsslQueryVo.setBalance(lshsje.getBalance());
                        if(accType.equals("Y")){
                            Query query3 =session.createQuery("from Lshssl lshssl where lshssl.spNo1 = '"+lshsje.getSpNo1()+"' and lshssl.itemNo = '"+itemNo+"'");
                            List<Lshssl> lshssls1 = query3.list();
                            Lshssl lshssl = lshssls1.get(0);

                            lshsjeOrLshsslQueryVo.setCreditQtySup(lshssl.getCreditQtySup());
                            lshsjeOrLshsslQueryVo.setDebitQtySup(lshssl.getDebitQtySup());
                            lshsjeOrLshsslQueryVo.setLeftQty(lshssl.getLeftQty());
                            lshsjeOrLshsslQueryVo.setSupQty(lshssl.getSupQty());
                        }
                        lshsjeOrLshsslQueryVos.add(i,lshsjeOrLshsslQueryVo);
                        i=i+1;
                    }

                }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();

        } finally {
            //关闭操作
            close(session);
            return lshsjeOrLshsslQueryVos;
        }
    }

    public boolean updateSpCount1(LshsjeOrLshsslQueryVo lshsjeOrLshsslQueryVo,String itemNo,String accType,String catNo1) {
        Session session = null;
        Transaction tx = null;
        List<Lswlje> lswljes = null;
        List<Lshssl> lshssls = null;
        try {
            session = getSession();
            tx = session.beginTransaction();

            //将修改好的数据更新到数据库
            String spNo1 = lshsjeOrLshsslQueryVo.getSpNo1();  //获取当前修改的级数

            Double supMoney = lshsjeOrLshsslQueryVo.getSupMoney();
            Double debitMoney = lshsjeOrLshsslQueryVo.getDebitMoneySup();
            Double creditMoney = lshsjeOrLshsslQueryVo.getCreditMoneySup();
            Double balance = lshsjeOrLshsslQueryVo.getBalance();

            Double supQty = 0.0d,debitQtySup = 0.0d,creditQtySup = 0.0d,leftQty = 0.0d;
            if(accType.equals("Y")){
                supQty = lshsjeOrLshsslQueryVo.getSupQty();
                debitQtySup  = lshsjeOrLshsslQueryVo.getDebitQtySup();
                creditQtySup = lshsjeOrLshsslQueryVo.getCreditQtySup();
                leftQty  = lshsjeOrLshsslQueryVo.getLeftQty();
            }

            Query queryupdateJe,queryupdateSl;
            queryupdateJe=session.createQuery("update Lshsje je set je.supMoney= "+supMoney+", je.debitMoney = "+debitMoney+",je.creditMoney = "+creditMoney+",je.balance = "+balance+" where je.itemNo= '"+itemNo+"' and je.spNo1='"+spNo1+"'");
            queryupdateJe.executeUpdate(); //更新lshsje表
            if(accType.equals("Y")){
                queryupdateSl=session.createQuery("update Lshssl sl set sl.supQty= "+supQty+", sl.debitQtySup = "+debitQtySup+",sl.creditQtySup = "+creditQtySup+",sl.leftQty = "+leftQty+" where sl.itemNo= '"+itemNo+"' and sl.spNo1='"+spNo1+"'");
                queryupdateSl.executeUpdate();//更新lshssl表
            }

            Query query = session.createQuery("from Lshszd lshszd where lshszd.spNo = '"+spNo1+"' and lshszd.catNo = '"+catNo1+"'");
            List<Lshszd> lshszds = query.list();
            Lshszd lshszd1 = lshszds.get(0);
            String  levelFlag= lshszd1.getSpLevel();
            String level;

            double countSupMoney = 0.0d,countDebitMoney = 0.0d,countCreditMoney = 0.0d,countBalance = 0.0d;
            double countSupQty = 0.0d,countDebitQtySup = 0.0d,countCreditQtySup = 0.0d,countLeftQty = 0.0d;

            //当变动为数据为第三级时进行的操作
            String spNoByLevel2;
            if(levelFlag.equals("3")){
                //统计第三级的数据之和，汇总到第二级
                spNoByLevel2 = spNo1.substring(0,8);
                spNoByLevel2 = spNoByLevel2+"0000";
                query = session.createQuery("from Lshsje je where je.spNo1 like '"+spNo1.substring(0,8)+"%' and je.itemNo =?");
                query.setParameter(0,itemNo);
                List<Lshsje> lshsjes = query.list();

                for (Lshsje lshsje : lshsjes) {

                    query = session.createQuery("from Lshszd lshszd where lshszd.spNo = '"+lshsje.getSpNo1()+"' and lshszd.catNo = '"+catNo1+"'");
                    lshszds = query.list();
                    lshszd1 = lshszds.get(0);
                    level = lshszd1.getSpLevel();

                    if(level.equals("3")){
                        countSupMoney += lshsje.getSupMoney();
                        countDebitMoney += lshsje.getDebitMoney();
                        countCreditMoney += lshsje.getCreditMoney();
                        countBalance += lshsje.getBalance();
                    }
                }

                Query query1 = session.createQuery("from Lshsje je where je.spNo1 = ? and je.itemNo =?");
                query1.setParameter(0,spNoByLevel2);
                query1.setParameter(1,itemNo);
                List<Lshsje> lshsjeList = query1.list();
                Lshsje lshsje = lshsjeList.get(0);
                lshsje.setSupMoney(countSupMoney);
                lshsje.setDebitMoney(countDebitMoney);
                lshsje.setCreditMoney(countCreditMoney);
                lshsje.setBalance(countBalance);

                session.update(lshsje);

                //统计第二级的数据之和，汇总到第一级
                String spNoByLevel1 = spNo1.substring(0,4);
                spNoByLevel1 = spNoByLevel1+"00000000";

                query = session.createQuery("from Lshsje je where je.spNo1 like '"+spNo1.substring(0,4)+"%' and je.itemNo =?");
                query.setParameter(0,itemNo);
                lshsjes = query.list();

                countSupMoney=0.0d;countDebitMoney=0.0d;countCreditMoney=0.0d;countBalance=0.0d;
                for (Lshsje lshsje1 : lshsjes) {
                    query = session.createQuery("from Lshszd lshszd where lshszd.spNo = '"+lshsje1.getSpNo1()+"' and lshszd.catNo = '"+catNo1+"'");
                    lshszds = query.list();
                    lshszd1 = lshszds.get(0);
                    level = lshszd1.getSpLevel();

                    if(level.equals("2")){
                        countSupMoney += lshsje1.getSupMoney();
                        countDebitMoney += lshsje1.getDebitMoney();
                        countCreditMoney += lshsje1.getCreditMoney();
                        countBalance += lshsje1.getBalance();
                    }
                }


                Query query2 = session.createQuery("from Lshsje je where je.spNo1 = ? and je.itemNo =?");
                query2.setParameter(0,spNoByLevel1);
                query2.setParameter(1,itemNo);
                lshsjeList = query2.list();
                lshsje = lshsjeList.get(0);
                lshsje.setSupMoney(countSupMoney);
                lshsje.setDebitMoney(countDebitMoney);
                lshsje.setCreditMoney(countCreditMoney);
                lshsje.setBalance(countBalance);

                session.update(lshsje);


                //如果是数量账，则把第二级的数量账汇总到第一级
                //当变动的核算对象为数量账时进行的操作
                if(accType.equals("Y")){
                    query = session.createQuery("from Lshssl sl where sl.spNo1 like '"+spNo1.substring(0,8)+"%' and sl.itemNo =?");
                    query.setParameter(0,itemNo);
                    lshssls = query.list();

                    for (Lshssl lshssl : lshssls) {

                        query = session.createQuery("from Lshszd lshszd where lshszd.spNo = '"+lshssl.getSpNo1()+"' and lshszd.catNo = '"+catNo1+"'");
                        lshszds = query.list();
                        lshszd1 = lshszds.get(0);
                        level = lshszd1.getSpLevel();

                        if(level.equals("3")){
                            countSupQty += lshssl.getSupQty();
                            countDebitQtySup += lshssl.getDebitQtySup();
                            countCreditQtySup += lshssl.getCreditQtySup();
                            countLeftQty += lshssl.getLeftQty();
                        }
                    }

                    query1 = session.createQuery("from Lshssl sl where sl.spNo1 = ? and sl.itemNo = ?");
                    query1.setParameter(0,spNoByLevel2);
                    query1.setParameter(1,itemNo);
                    List<Lshssl> lshsslList = query1.list();
                    Lshssl lshssl = lshsslList.get(0);
                    lshssl.setSupQty(countSupQty);
                    lshssl.setDebitQtySup(countDebitQtySup);
                    lshssl.setCreditQtySup(countCreditQtySup);
                    lshssl.setLeftQty(countLeftQty);

                    session.update(lshssl);

                    //数量账，则把第三级的数量账汇总到第二级

                    //统计第二级的数据之和，汇总到第一级

                    query = session.createQuery("from Lshssl sl where sl.spNo1 like '"+spNo1.substring(0,4)+"%' and sl.itemNo =?");
                    query.setParameter(0,itemNo);
                    lshsslList = query.list();

                    countSupQty = 0.0d;countDebitQtySup = 0.0d;countCreditQtySup = 0.0d;countLeftQty = 0.0d;
                    for (Lshssl lshssl1 : lshsslList) {
                        query = session.createQuery("from Lshszd lshszd where lshszd.spNo = '"+lshssl1.getSpNo1()+"' and lshszd.catNo = '"+catNo1+"'");
                        lshszds = query.list();
                        lshszd1 = lshszds.get(0);
                        level = lshszd1.getSpLevel();

                        if(level.equals("2")){
                            countSupQty += lshssl1.getSupQty();
                            countDebitQtySup += lshssl1.getDebitQtySup();
                            countCreditQtySup += lshssl1.getCreditQtySup();
                            countLeftQty += lshssl1.getLeftQty();
                        }
                    }


                    query2 = session.createQuery("from Lshssl sl where sl.spNo1 = ? and sl.itemNo =?");
                    query2.setParameter(0,spNoByLevel1);
                    query2.setParameter(1,itemNo);
                    lshsslList = query2.list();
                    lshssl = lshsslList.get(0);
                    lshssl.setSupQty(countSupQty);
                    lshssl.setDebitQtySup(countDebitQtySup);
                    lshssl.setCreditQtySup(countCreditQtySup);
                    lshssl.setLeftQty(countLeftQty);

                    session.update(lshssl);

                }



            }
            //当变动为数据为第二级时进行的操作
            if(levelFlag.equals("2")){

                //统计第二级的数据之和，汇总到第一级
                String spNoByLevel1 = spNo1.substring(0,4);
                spNoByLevel1 = spNoByLevel1+"00000000";

                query = session.createQuery("from Lshsje je where je.spNo1 like '"+spNo1.substring(0,4)+"%' and je.itemNo =?");
                query.setParameter(0,itemNo);
                List<Lshsje> lshsjes = query.list();

                countSupMoney=0.0d;countDebitMoney=0.0d;countCreditMoney=0.0d;countBalance=0.0d;
                for (Lshsje lshsje1 : lshsjes) {
                    query = session.createQuery("from Lshszd lshszd where lshszd.spNo = '"+lshsje1.getSpNo1()+"' and lshszd.catNo = '"+catNo1+"'");
                    lshszds = query.list();
                    lshszd1 = lshszds.get(0);
                    level = lshszd1.getSpLevel();

                    if(level.equals("2")){
                        countSupMoney += lshsje1.getSupMoney();
                        countDebitMoney += lshsje1.getDebitMoney();
                        countCreditMoney += lshsje1.getCreditMoney();
                        countBalance += lshsje1.getBalance();
                    }
                }


                Query query2 = session.createQuery("from Lshsje je where je.spNo1 = ? and je.itemNo =?");
                query2.setParameter(0,spNoByLevel1);
                query2.setParameter(1,itemNo);
                lshsjes = query2.list();
                Lshsje lshsje = lshsjes.get(0);
                lshsje.setSupMoney(countSupMoney);
                lshsje.setDebitMoney(countDebitMoney);
                lshsje.setCreditMoney(countCreditMoney);
                lshsje.setBalance(countBalance);

                session.update(lshsje);

                if(accType.equals("Y")){
                    query = session.createQuery("from Lshssl sl where sl.spNo1 like '"+spNo1.substring(0,4)+"%' and sl.itemNo =?");
                    query.setParameter(0,itemNo);
                    List<Lshssl> lshssls1 = query.list();

                    countSupQty = 0.0d;countDebitQtySup = 0.0d;countCreditQtySup = 0.0d;countLeftQty = 0.0d;
                    for (Lshssl lshssl : lshssls1) {
                        query = session.createQuery("from Lshszd lshszd where lshszd.spNo = '"+lshssl.getSpNo1()+"' and lshszd.catNo = '"+catNo1+"'");
                        lshszds = query.list();
                        lshszd1 = lshszds.get(0);
                        level = lshszd1.getSpLevel();

                        if(level.equals("2")){
                            countSupQty += lshssl.getSupQty();
                            countDebitQtySup += lshssl.getDebitQtySup();
                            countCreditQtySup += lshssl.getCreditQtySup();
                            countLeftQty += lshssl.getLeftQty();
                        }
                    }


                    Query query3 = session.createQuery("from Lshssl sl where sl.spNo1 = ? and sl.itemNo =?");
                    query3.setParameter(0,spNoByLevel1);
                    query3.setParameter(1,itemNo);
                    lshssls1 = query3.list();
                    Lshssl lshssl = lshssls1.get(0);
                    lshssl.setSupQty(countSupQty);
                    lshssl.setDebitQtySup(countDebitQtySup);
                    lshssl.setCreditQtySup(countCreditQtySup);
                    lshssl.setLeftQty(countLeftQty);

                    session.update(lshssl);
                }



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

    public List<LshsjeOrLshsslQueryVo> getLshsjeOrLshssl2(String itemNo , String accType , String catNo1 ,String catNo2) {
        Session session = null;
        Transaction tx = null;
        List<LshsjeOrLshsslQueryVo> lshsjeOrLshsslQueryVos = new ArrayList<LshsjeOrLshsslQueryVo>();
        try {
            session = getSession();
            tx = session.beginTransaction();

            Query queryJ = session.createQuery("from Lshsje where itemNo = ? order by spNo1 ASC");
            queryJ.setParameter(0,itemNo);
            List<Lshsje> lshsjes = queryJ.list();

            String spNo1,spName1,spNo2,spName2;
            int i = 0;
            for(Lshsje lshsje : lshsjes){
                Query query;
                List<Lshszd> lshszds;
                query = session.createQuery("from Lshszd lshszd where lshszd.spNo = '"+lshsje.getSpNo1()+"' and lshszd.catNo = '"+catNo1+"'");
                lshszds = query.list();
                Lshszd lshszd1 = lshszds.get(0);

                query = session.createQuery("from Lshszd lshszd where lshszd.spNo = '"+lshsje.getSpNo2()+"' and lshszd.catNo = '"+catNo2+"'");
                lshszds = query.list();
                Lshszd lshszd2 = lshszds.get(0);

                if(lshszd1.getFinLevel() == 1 && lshszd2.getFinLevel() ==1){
                    LshsjeOrLshsslQueryVo lshsjeOrLshsslQueryVo = new LshsjeOrLshsslQueryVo();

                    spNo1 = lshsje.getSpNo1();
                    spNo2 = lshsje.getSpNo2();
                    lshsjeOrLshsslQueryVo.setSpNo1(spNo1);
                    lshsjeOrLshsslQueryVo.setSpNo2(spNo2);

                    spName1 = lshszd1.getSpName();
                    lshsjeOrLshsslQueryVo.setSpName1(spName1);

                    spName2 = lshszd2.getSpName();
                    lshsjeOrLshsslQueryVo.setSpName2(spName2);


                    lshsjeOrLshsslQueryVo.setSupMoney(lshsje.getSupMoney());
                    lshsjeOrLshsslQueryVo.setDebitMoneySup(lshsje.getDebitMoneySup());
                    lshsjeOrLshsslQueryVo.setCreditMoneySup(lshsje.getCreditMoneySup());
                    lshsjeOrLshsslQueryVo.setBalance(lshsje.getBalance());

                    if(accType.equals("Y")){
                        Query query3 =session.createQuery("from Lshssl lshssl where lshssl.spNo1 = '"+spNo1+"' and lshssl.spNo2 = '"+spNo2+"' and lshssl.itemNo = '"+itemNo+"'");
                        List<Lshssl> lshssls1 = query3.list();
                        Lshssl lshssl = lshssls1.get(0);


                        lshsjeOrLshsslQueryVo.setCreditQtySup(lshssl.getCreditQtySup());
                        lshsjeOrLshsslQueryVo.setDebitQtySup(lshssl.getDebitQtySup());
                        lshsjeOrLshsslQueryVo.setLeftQty(lshssl.getLeftQty());
                        lshsjeOrLshsslQueryVo.setSupQty(lshssl.getSupQty());
                    }

                    lshsjeOrLshsslQueryVos.add(i,lshsjeOrLshsslQueryVo);
                    i=i+1;
                }
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lshsjeOrLshsslQueryVos;
        }
    }



    public boolean updateSpCount2(LshsjeOrLshsslQueryVo lshsjeOrLshsslQueryVo,String itemNo,String accType,String catNo1,String catNo2) {
        Session session = null;
        Transaction tx = null;
        List<Lswlje> lswljes = null;
        List<Lshssl> lshssls = null;
        try {
            session = getSession();
            tx = session.beginTransaction();

            //将修改好的数据更新到数据库
            String spNo1 = lshsjeOrLshsslQueryVo.getSpNo1();  //获取当前修改的级数
            String spNo2 = lshsjeOrLshsslQueryVo.getSpNo2();

            Double supMoney = lshsjeOrLshsslQueryVo.getSupMoney();
            Double debitMoney = lshsjeOrLshsslQueryVo.getDebitMoneySup();
            Double creditMoney = lshsjeOrLshsslQueryVo.getCreditMoneySup();
            Double balance = lshsjeOrLshsslQueryVo.getBalance();

            Double supQty = 0.0d,debitQtySup = 0.0d,creditQtySup = 0.0d,leftQty = 0.0d;
            if(accType.equals("Y")){
                supQty = lshsjeOrLshsslQueryVo.getSupQty();
                debitQtySup  = lshsjeOrLshsslQueryVo.getDebitQtySup();
                creditQtySup = lshsjeOrLshsslQueryVo.getCreditQtySup();
                leftQty  = lshsjeOrLshsslQueryVo.getLeftQty();
            }

            Query queryupdateJe,queryupdateSl;
            queryupdateJe=session.createQuery("update Lshsje je set je.supMoney= "+supMoney+", je.debitMoney = "+debitMoney+",je.creditMoney = "+creditMoney+",je.balance = "+balance+" where je.itemNo= '"+itemNo+"' and je.spNo1='"+spNo1+"' and je.spNo2='"+spNo2+"'");
            queryupdateJe.executeUpdate(); //更新lshsje表
            if(accType.equals("Y")){
                queryupdateSl=session.createQuery("update Lshssl sl set sl.supQty= "+supQty+", sl.debitQtySup = "+debitQtySup+",sl.creditQtySup = "+creditQtySup+",sl.leftQty = "+leftQty+" where sl.itemNo= '"+itemNo+"' and sl.spNo1='"+spNo1+"' and sl.spNo2='"+spNo2+"'");
                queryupdateSl.executeUpdate();//更新lshssl表
            }

            /*Query query = session.createQuery("from Lshszd lshszd where lshszd.spNo = '"+spNo1+"' and lshszd.catNo = '"+catNo1+"'");
            List<Lshszd> lshszds = query.list();
            Lshszd lshszd1 = lshszds.get(0);
            String  levelFlag= lshszd1.getSpLevel();
            String level;

            double countSupMoney = 0.0d,countDebitMoney = 0.0d,countCreditMoney = 0.0d,countBalance = 0.0d;
            double countSupQty = 0.0d,countDebitQtySup = 0.0d,countCreditQtySup = 0.0d,countLeftQty = 0.0d;

            //当变动为数据为第三级时进行的操作
            String spNoByLevel2;
            if(levelFlag.equals("3")){
                //统计第三级的数据之和，汇总到第二级
                spNoByLevel2 = spNo1.substring(0,8);
                spNoByLevel2 = spNoByLevel2+"0000";
                query = session.createQuery("from Lshsje je where je.spNo1 like '"+spNo1.substring(0,8)+"%' and je.itemNo =?");
                query.setParameter(0,itemNo);
                List<Lshsje> lshsjes = query.list();

                for (Lshsje lshsje : lshsjes) {

                    query = session.createQuery("from Lshszd lshszd where lshszd.spNo = '"+lshsje.getSpNo1()+"' and lshszd.catNo = '"+catNo1+"'");
                    lshszds = query.list();
                    lshszd1 = lshszds.get(0);
                    level = lshszd1.getSpLevel();

                    if(level.equals("3")){
                        countSupMoney += lshsje.getSupMoney();
                        countDebitMoney += lshsje.getDebitMoney();
                        countCreditMoney += lshsje.getCreditMoney();
                        countBalance += lshsje.getBalance();
                    }
                }

                Query query1 = session.createQuery("from Lshsje je where je.spNo1 = ? and je.itemNo =?");
                query1.setParameter(0,spNoByLevel2);
                query1.setParameter(1,itemNo);
                List<Lshsje> lshsjeList = query1.list();
                Lshsje lshsje = lshsjeList.get(0);
                lshsje.setSupMoney(countSupMoney);
                lshsje.setDebitMoney(countDebitMoney);
                lshsje.setCreditMoney(countCreditMoney);
                lshsje.setBalance(countBalance);

                session.update(lshsje);

                //统计第二级的数据之和，汇总到第一级
                String spNoByLevel1 = spNo1.substring(0,4);
                spNoByLevel1 = spNoByLevel1+"00000000";

                query = session.createQuery("from Lshsje je where je.spNo1 like '"+spNo1.substring(0,4)+"%' and je.itemNo =?");
                query.setParameter(0,itemNo);
                lshsjes = query.list();

                countSupMoney=0.0d;countDebitMoney=0.0d;countCreditMoney=0.0d;countBalance=0.0d;
                for (Lshsje lshsje1 : lshsjes) {
                    query = session.createQuery("from Lshszd lshszd where lshszd.spNo = '"+lshsje1.getSpNo1()+"' and lshszd.catNo = '"+catNo1+"'");
                    lshszds = query.list();
                    lshszd1 = lshszds.get(0);
                    level = lshszd1.getSpLevel();

                    if(level.equals("2")){
                        countSupMoney += lshsje1.getSupMoney();
                        countDebitMoney += lshsje1.getDebitMoney();
                        countCreditMoney += lshsje1.getCreditMoney();
                        countBalance += lshsje1.getBalance();
                    }
                }


                Query query2 = session.createQuery("from Lshsje je where je.spNo1 = ? and je.itemNo =?");
                query2.setParameter(0,spNoByLevel1);
                query2.setParameter(1,itemNo);
                lshsjeList = query2.list();
                lshsje = lshsjeList.get(0);
                lshsje.setSupMoney(countSupMoney);
                lshsje.setDebitMoney(countDebitMoney);
                lshsje.setCreditMoney(countCreditMoney);
                lshsje.setBalance(countBalance);

                session.update(lshsje);


                //如果是数量账，则把第二级的数量账汇总到第一级
                //当变动的核算对象为数量账时进行的操作
                if(accType.equals("Y")){
                    query = session.createQuery("from Lshssl sl where sl.spNo1 like '"+spNo1.substring(0,8)+"%' and sl.itemNo =?");
                    query.setParameter(0,itemNo);
                    lshssls = query.list();

                    for (Lshssl lshssl : lshssls) {

                        query = session.createQuery("from Lshszd lshszd where lshszd.spNo = '"+lshssl.getSpNo1()+"' and lshszd.catNo = '"+catNo1+"'");
                        lshszds = query.list();
                        lshszd1 = lshszds.get(0);
                        level = lshszd1.getSpLevel();

                        if(level.equals("3")){
                            countSupQty += lshssl.getSupQty();
                            countDebitQtySup += lshssl.getDebitQtySup();
                            countCreditQtySup += lshssl.getCreditQtySup();
                            countLeftQty += lshssl.getLeftQty();
                        }
                    }

                    query1 = session.createQuery("from Lshssl sl where sl.spNo1 = ? and sl.itemNo = ?");
                    query1.setParameter(0,spNoByLevel2);
                    query1.setParameter(1,itemNo);
                    List<Lshssl> lshsslList = query1.list();
                    Lshssl lshssl = lshsslList.get(0);
                    lshssl.setSupQty(countSupQty);
                    lshssl.setDebitQtySup(countDebitQtySup);
                    lshssl.setCreditQtySup(countCreditQtySup);
                    lshssl.setLeftQty(countLeftQty);

                    session.update(lshssl);

                    //数量账，则把第三级的数量账汇总到第二级

                    //统计第二级的数据之和，汇总到第一级

                    query = session.createQuery("from Lshssl sl where sl.spNo1 like '"+spNo1.substring(0,4)+"%' and sl.itemNo =?");
                    query.setParameter(0,itemNo);
                    lshsslList = query.list();

                    countSupQty = 0.0d;countDebitQtySup = 0.0d;countCreditQtySup = 0.0d;countLeftQty = 0.0d;
                    for (Lshssl lshssl1 : lshsslList) {
                        query = session.createQuery("from Lshszd lshszd where lshszd.spNo = '"+lshssl1.getSpNo1()+"' and lshszd.catNo = '"+catNo1+"'");
                        lshszds = query.list();
                        lshszd1 = lshszds.get(0);
                        level = lshszd1.getSpLevel();

                        if(level.equals("2")){
                            countSupQty += lshssl1.getSupQty();
                            countDebitQtySup += lshssl1.getDebitQtySup();
                            countCreditQtySup += lshssl1.getCreditQtySup();
                            countLeftQty += lshssl1.getLeftQty();
                        }
                    }


                    query2 = session.createQuery("from Lshssl sl where sl.spNo1 = ? and sl.itemNo =?");
                    query2.setParameter(0,spNoByLevel1);
                    query2.setParameter(1,itemNo);
                    lshsslList = query2.list();
                    lshssl = lshsslList.get(0);
                    lshssl.setSupQty(countSupQty);
                    lshssl.setDebitQtySup(countDebitQtySup);
                    lshssl.setCreditQtySup(countCreditQtySup);
                    lshssl.setLeftQty(countLeftQty);

                    session.update(lshssl);

                }



            }
            //当变动为数据为第二级时进行的操作
            if(levelFlag.equals("2")){

                //统计第二级的数据之和，汇总到第一级
                String spNoByLevel1 = spNo1.substring(0,4);
                spNoByLevel1 = spNoByLevel1+"00000000";

                query = session.createQuery("from Lshsje je where je.spNo1 like '"+spNo1.substring(0,4)+"%' and je.itemNo =?");
                query.setParameter(0,itemNo);
                List<Lshsje> lshsjes = query.list();

                countSupMoney=0.0d;countDebitMoney=0.0d;countCreditMoney=0.0d;countBalance=0.0d;
                for (Lshsje lshsje1 : lshsjes) {
                    query = session.createQuery("from Lshszd lshszd where lshszd.spNo = '"+lshsje1.getSpNo1()+"' and lshszd.catNo = '"+catNo1+"'");
                    lshszds = query.list();
                    lshszd1 = lshszds.get(0);
                    level = lshszd1.getSpLevel();

                    if(level.equals("2")){
                        countSupMoney += lshsje1.getSupMoney();
                        countDebitMoney += lshsje1.getDebitMoney();
                        countCreditMoney += lshsje1.getCreditMoney();
                        countBalance += lshsje1.getBalance();
                    }
                }


                Query query2 = session.createQuery("from Lshsje je where je.spNo1 = ? and je.itemNo =?");
                query2.setParameter(0,spNoByLevel1);
                query2.setParameter(1,itemNo);
                lshsjes = query2.list();
                Lshsje lshsje = lshsjes.get(0);
                lshsje.setSupMoney(countSupMoney);
                lshsje.setDebitMoney(countDebitMoney);
                lshsje.setCreditMoney(countCreditMoney);
                lshsje.setBalance(countBalance);

                session.update(lshsje);

                if(accType.equals("Y")){
                    query = session.createQuery("from Lshssl sl where sl.spNo1 like '"+spNo1.substring(0,4)+"%' and sl.itemNo =?");
                    query.setParameter(0,itemNo);
                    List<Lshssl> lshssls1 = query.list();

                    countSupQty = 0.0d;countDebitQtySup = 0.0d;countCreditQtySup = 0.0d;countLeftQty = 0.0d;
                    for (Lshssl lshssl : lshssls1) {
                        query = session.createQuery("from Lshszd lshszd where lshszd.spNo = '"+lshssl.getSpNo1()+"' and lshszd.catNo = '"+catNo1+"'");
                        lshszds = query.list();
                        lshszd1 = lshszds.get(0);
                        level = lshszd1.getSpLevel();

                        if(level.equals("2")){
                            countSupQty += lshssl.getSupQty();
                            countDebitQtySup += lshssl.getDebitQtySup();
                            countCreditQtySup += lshssl.getCreditQtySup();
                            countLeftQty += lshssl.getLeftQty();
                        }
                    }


                    Query query3 = session.createQuery("from Lshssl sl where sl.spNo1 = ? and sl.itemNo =?");
                    query3.setParameter(0,spNoByLevel1);
                    query3.setParameter(1,itemNo);
                    lshssls1 = query3.list();
                    Lshssl lshssl = lshssls1.get(0);
                    lshssl.setSupQty(countSupQty);
                    lshssl.setDebitQtySup(countDebitQtySup);
                    lshssl.setCreditQtySup(countCreditQtySup);
                    lshssl.setLeftQty(countLeftQty);

                    session.update(lshssl);
                }*/



            //}

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

}
