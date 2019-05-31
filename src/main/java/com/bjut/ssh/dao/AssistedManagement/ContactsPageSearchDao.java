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
 * @Title: ContactsPageSearchDao
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/7/13 17:28
 * @Version: 1.0
 */
@Repository
@Transactional
public class ContactsPageSearchDao {


    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    public void close(Session session){
        if(session != null)
            session.close();
    }


    public Msg queryContactsAccountPage(String dataFrom,String dataTo, String itemNo, String companyNo,String pageNum ,String searchOption1,String searchOption2) {
        Session session = null;
        Transaction tx = null;
        List<Lspzk1VoForSearch> lspzk1VoForSearches = new ArrayList<>();
        try{
            session = getSession();
            tx = session.beginTransaction();

            dataFrom = dataFrom+"01";
            dataTo = dataTo+"31";

            String hql = "from Lspzk1 where  inputDate >= ? and inputDate<=? and itemNo = ? and companyNo = ? order by inputDate, voucherNo";
            Query query = session.createQuery(hql);
            query.setParameter(0,dataFrom);
            query.setParameter(1,dataTo);
            query.setParameter(2,itemNo);
            query.setParameter(3,companyNo);
            List<Lspzk1> lspzk1List = query.list();

            //计算上期结转
            String supMoney_str = "";
            int last_month = Integer.parseInt(dataFrom.substring(4, 6)) - 1;
            //如果当前为1月，则上期结转就是上年结转
            if(last_month == 0){
                supMoney_str = "supMoney";
            }
            //如果是其他月份，则上期结转就是上月余额
            else{
                String month_str = last_month < 10 ? "0" + Integer.toString(last_month) : Integer.toString(last_month);
                supMoney_str = "balance" + month_str;
            }
            Query query_sup = session.createQuery("select " + supMoney_str + " from Lskmzd where itemNo = '" + itemNo + "'");
            List<Double> sups = query_sup.list();
            Double supMoney = sups.get(0) == null ? 0.0 : sups.get(0);

            //添加上期结转行
            Lspzk1 sup_pzk = new Lspzk1();
            sup_pzk.setSummary("上期结转");
            Lspzk1VoForSearch sup_row = new Lspzk1VoForSearch();
            sup_row.setLspzk1(sup_pzk);
            sup_row.setMoney(supMoney);
            lspzk1VoForSearches.add(sup_row);

            Double balance = supMoney;//当前余额
            for(Lspzk1 lspzk1 : lspzk1List){
                Lspzk1VoForSearch lspzk1VoForSearch = new Lspzk1VoForSearch();
                lspzk1VoForSearch.setLspzk1(lspzk1);

                //借方发生额则增加余额
                if(lspzk1.getBkpDirection().equals("J")){
                    balance += lspzk1.getMoney() == null ? 0.0 : lspzk1.getMoney();
                }
                //贷方发生额则减少余额
                else{
                    balance -= lspzk1.getMoney() == null ? 0.0 : lspzk1.getMoney();
                }
                lspzk1VoForSearch.setMoney(balance);
                lspzk1VoForSearches.add(lspzk1VoForSearch);
            }
            tx.commit();
            close(session);
            return Msg.success().add("lspzk1VoForSearches", lspzk1VoForSearches).add("headInfo",queryLskmzdForHeadInfo(itemNo,companyNo));
        }
        catch(Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
            return Msg.fail().add("errorInfo","查询语句无效，请重新输入！");
        }
    }

    public QueryLskmzdFoContactsPageSearch queryLskmzdForHeadInfo(String itemNo,String companyNo) {
        Session session = null;
        Transaction tx = null;
        QueryLskmzdFoContactsPageSearch headInfo = new QueryLskmzdFoContactsPageSearch();
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query;
            //科目编号为通配符
            if(itemNo.equals("....")){
                headInfo.setItemName(null);
                headInfo.setHead1Key(null);
                headInfo.setHead1Value(null);
                headInfo.setHead2Key(null);
                headInfo.setHead2Value(null);
                headInfo.setHead3Key(null);
                headInfo.setHead3Value(null);
                headInfo.setHead4Key(null);
                headInfo.setHead4Value(null);
                headInfo.setHead5Key(null);
                headInfo.setHead5Value(null);
                headInfo.setHead6Key(null);
                headInfo.setHead6Value(null);

            }else{
                if(itemNo.contains("....")){
                    itemNo = getCompleteItemNo(itemNo);
                }
                Lskmzd lskmzd = (Lskmzd)session.get(Lskmzd.class,itemNo);
                headInfo.setItemName(lskmzd.getItemName());
                if(lskmzd.getAccType().equals("S")){
                    query = session.createQuery("from Lskmsl where itemNo  = '"+itemNo+"'");
                    List<Lskmsl> lskmsls = query.list();
                    if(lskmsls.size()>0){
                        Lskmsl lskmsl = lskmsls.get(0);

                        headInfo.setHead2Value(lskmsl.getHead2());
                        headInfo.setHead3Value(lskmsl.getHead3());
                        headInfo.setHead4Value(lskmsl.getHead4());
                        headInfo.setHead5Value(lskmsl.getHead5());
                        headInfo.setHead6Value(lskmsl.getHead6());
                        if(lskmsl.getHead1() != null){
                            headInfo.setHead1Value(lskmsl.getHead1());
                            Lsconf lsconf = (Lsconf) session.get(Lsconf.class,"table1");
                            headInfo.setHead1Key(lsconf.getConfValue());
                        }else{
                            headInfo.setHead1Key(null);
                            headInfo.setHead1Value(null);
                        }
                        if(lskmsl.getHead2() != null){
                            headInfo.setHead2Value(lskmsl.getHead2());
                            Lsconf lsconf = (Lsconf) session.get(Lsconf.class,"table2");
                            headInfo.setHead2Key(lsconf.getConfValue());
                        }else{
                            headInfo.setHead2Key(null);
                            headInfo.setHead2Value(null);
                        }
                        if(lskmsl.getHead3() != null){
                            headInfo.setHead3Value(lskmsl.getHead3());
                            Lsconf lsconf = (Lsconf) session.get(Lsconf.class,"table3");
                            headInfo.setHead3Key(lsconf.getConfValue());
                        }else{
                            headInfo.setHead3Key(null);
                            headInfo.setHead3Value(null);
                        }
                        if(lskmsl.getHead4() != null){
                            headInfo.setHead4Value(lskmsl.getHead4());
                            Lsconf lsconf = (Lsconf) session.get(Lsconf.class,"table4");
                            headInfo.setHead4Key(lsconf.getConfValue());
                        }else{
                            headInfo.setHead4Key(null);
                            headInfo.setHead4Value(null);
                        }
                        if(lskmsl.getHead5() != null){
                            headInfo.setHead5Value(lskmsl.getHead5());
                            Lsconf lsconf = (Lsconf) session.get(Lsconf.class,"table5");
                            headInfo.setHead5Key(lsconf.getConfValue());
                        }else{
                            headInfo.setHead5Key(null);
                            headInfo.setHead5Value(null);
                        }
                        if(lskmsl.getHead6() != null){
                            headInfo.setHead6Value(lskmsl.getHead6());
                            Lsconf lsconf = (Lsconf) session.get(Lsconf.class,"table6");
                            headInfo.setHead6Key(lsconf.getConfValue());
                        }else{
                            headInfo.setHead6Key(null);
                            headInfo.setHead6Value(null);
                        }

                    }

                }else{
                    headInfo.setHead1Key(null);
                    headInfo.setHead1Value(null);
                    headInfo.setHead2Key(null);
                    headInfo.setHead2Value(null);
                    headInfo.setHead3Key(null);
                    headInfo.setHead3Value(null);
                    headInfo.setHead4Key(null);
                    headInfo.setHead4Value(null);
                    headInfo.setHead5Key(null);
                    headInfo.setHead5Value(null);
                    headInfo.setHead6Key(null);
                    headInfo.setHead6Value(null);
                }
            }
            if(companyNo.equals("...")){
                    headInfo.setCompanyName(null);
            }else{
                Lswldw lswldw = (Lswldw)session.get(Lswldw.class,companyNo);
                headInfo.setCompanyName(lswldw.getCompanyName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return headInfo;
        }
    }

    public String getCompleteItemNo(String itemNo){
        Session session = null;
        Transaction tx = null;
        QueryLskmzdFoSpAccountPageSearch headInfo = new QueryLskmzdFoSpAccountPageSearch();
        String completeItemNo = "";
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query;
            String key = "sub_stru";
            query = session.createQuery("from Lsconf where confKey = '"+key+"'");
            List<Lsconf> lsconfs = query.list();
            String stru = null;
            if(lsconfs.size()>0){
                Lsconf lsconf = lsconfs.get(0);
                stru = lsconf.getConfValue();
            }

            completeItemNo = itemNo.substring(0,itemNo.length()-4);

            char[] chars  = stru.toCharArray();

            int length = 0;

            for(int i=0; i<chars.length; i++){
                String str=String.valueOf(chars[i]);
                length += Integer.parseInt(str);
            }

            int lengthZero = length - completeItemNo.length();
            for (int i = 0; i < lengthZero ; i++) {
                completeItemNo = completeItemNo + "0";
            }

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return completeItemNo;
        }
    }

    public boolean judgeCompanyConnectItem(String itemNo,String companyNo) {
        Session session = null;
        Transaction tx = null;
        boolean bool = true;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Lswlje je where je.itemNo = ? and je.companyNo = ?");
            query.setParameter(0,itemNo);
            query.setParameter(1,companyNo);
            List<Lswlje> lswljeList = query.list();
            if(lswljeList.size()>0){
                bool = true;
            }else{
                bool =false;
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

    public List<Lswldw> queryCompany() {
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

    public Lswldw judgeContactsCompanyNo(String companyNo) {
        Session session = null;
        Transaction tx = null;
        Lswldw lswldw = new Lswldw();
        try {
            session = getSession();
            tx = session.beginTransaction();
            lswldw = (Lswldw)session.get(Lswldw.class,companyNo);
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

    public Lskmzd judgeItemNo(String itemNo) {
        Session session = null;
        Transaction tx = null;
        Lskmzd lskmzd = new Lskmzd();
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

}
