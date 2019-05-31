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
 * @Title: SpAccountPageSearchDao
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/9/18 14:38
 * @Version: 1.0
 */
@Repository
@Transactional
public class SpAccountPageSearchDao {
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    public void close(Session session){
        if(session != null)
            session.close();
    }

    public List<Lspzk1VoForSearch> querySpAccountPage(String from, String to, String itemNo, String spCatNo1, String spNo1, String spCatNo2, String spNo2, String searchOption) {
        Session session = null;
        Transaction tx = null;
        List<Lspzk1VoForSearch> lspzk1VoForSearches = new ArrayList<Lspzk1VoForSearch>();

        try {
            session = getSession();
            tx = session.beginTransaction();

            //Lskmzd lskmzd = (Lskmzd)session.get(Lskmzd.class,itemNo);

            //查询核算凭证
            Query query = null;

            //只填写了核算编号1和核算分类1
            if(spNo2 == null) {
                //科目编号为通配符
                if (itemNo.equals("....")) {
                    query = session.createQuery("from Lshspz where spNo1 = ? and inputDate >= ? and inputDate <= ? order by inputDate, voucherNo");
                    query.setParameter(0, spNo1);
                    query.setParameter(1, from);
                    query.setParameter(2, to);
                }
                //科目编号末级为通配符
                else if (itemNo.contains("....")) {
                    itemNo = itemNo.substring(0, itemNo.length() - 4);
                    query = session.createQuery("from Lshspz where itemNo like ? and spNo1 = ? and inputDate >= ? and inputDate <= ? order by inputDate, voucherNo");
                    query.setParameter(0, itemNo + "%");
                    query.setParameter(1, spNo1);
                    query.setParameter(2, from);
                    query.setParameter(3, to);
                }
                //核算编号为通配符
                else if (spNo1.equals("...")) {
                    query = session.createQuery("from Lshspz where itemNo = ? and inputDate >= ? and inputDate <= ? order by inputDate, voucherNo");
                    query.setParameter(0, itemNo);
                    query.setParameter(1, from);
                    query.setParameter(2, to);
                }
                //所有参数写完整
                else {
                    query = session.createQuery("from Lshspz where itemNo = ? and spNo1 = ? and inputDate >= ? and inputDate <= ? order by inputDate, voucherNo");
                    query.setParameter(0, itemNo);
                    query.setParameter(1, spNo1);
                    query.setParameter(2, from);
                    query.setParameter(3, to);
                }
            }
            else{
                //科目编号为通配符
                if (itemNo.equals("....")) {
                    query = session.createQuery("from Lshspz where spNo1 = ? and spNo2 = ? and inputDate >= ? and inputDate <= ? order by inputDate, voucherNo");
                    query.setParameter(0, spNo1);
                    query.setParameter(1, spNo2);
                    query.setParameter(2, from);
                    query.setParameter(3, to);
                }
                //科目编号末级为通配符
                else if (itemNo.contains("....")) {
                    itemNo = itemNo.substring(0, itemNo.length() - 4);
                    query = session.createQuery("from Lshspz where itemNo like ? and spNo1 = ? and spNo2 = ? and inputDate >= ? and inputDate <= ? order by inputDate, voucherNo");
                    query.setParameter(0, itemNo + "%");
                    query.setParameter(1, spNo1);
                    query.setParameter(2, spNo2);
                    query.setParameter(3, from);
                    query.setParameter(4, to);
                }
                //核算编号为通配符
                else if (spNo2.equals("...")) {
                    query = session.createQuery("from Lshspz where itemNo = ? and inputDate >= ? and inputDate <= ? order by inputDate, voucherNo");
                    query.setParameter(0, itemNo);
                    query.setParameter(1, from);
                    query.setParameter(2, to);
                }
                //所有参数写完整
                else {
                    query = session.createQuery("from Lshspz where itemNo = ? and spNo1 = ? and inputDate >= ? and inputDate <= ? order by inputDate, voucherNo");
                    query.setParameter(0, itemNo);
                    query.setParameter(1, spNo1);
                    query.setParameter(2, spNo2);
                    query.setParameter(3, from);
                    query.setParameter(4, to);
                }
            }

            List<Lshspz> lshspzList = query.list();

            //计算上期结转
            String supMoney_str = "";
            String supQty_str = "";
            int last_month = Integer.parseInt(from.substring(4, 6)) - 1;
            //如果当前为1月，则上期结转就是上年结转
            if(last_month == 0){
                supMoney_str = "supMoney";
                supQty_str = "supQty";
            }
            //如果是其他月份，则上期结转就是上月余额
            else{
                String month_str = last_month < 10 ? "0" + Integer.toString(last_month) : Integer.toString(last_month);
                supMoney_str = "balance" + month_str;
                supQty_str = "leftQty" + month_str;
            }

            Double supMoney = 0.0;
            Double supQty = 0.0;
            for(Lshspz lshspz : lshspzList) {
                String condition = "itemNo = '" + lshspz.getItemNo() + "'";
                Double add_supMoney = queryColumn("Lshsje", supMoney_str, condition);
                Double add_supQty = queryColumn("Lshssl", supQty_str, condition);
                supMoney = add_supMoney == null ? supMoney : supMoney + add_supMoney;
                supQty = add_supQty == null ? supQty : supQty + add_supQty;
            }

            //添加上期结转行
            Lshspz sup_hspz = new Lshspz();
            Lspzk1 sup_pzk = new Lspzk1();
            sup_pzk.setSummary("上期结转");
            Lspzk1VoForSearch sup_row = new Lspzk1VoForSearch();
            sup_row.setLspzk1(sup_pzk);
            sup_row.setLshspz(sup_hspz);
            sup_row.setMoney(supMoney);
            sup_row.setQty(supQty);
            lspzk1VoForSearches.add(sup_row);

            Double balance = supMoney;//当前余额
            Double leftQty = supQty;
            for(int i = 0; i < lshspzList.size(); i++){
                Lshspz lshspz = lshspzList.get(i);
                Lspzk1VoForSearch lspzk1VoForSearch = new Lspzk1VoForSearch();
                lspzk1VoForSearch.setLshspz(lshspz);

                //借方发生额则增加余额
                if(lshspz.getBkpDirection().equals("J")){
                    balance += lshspz.getMoney() == null ? 0.0 : lshspz.getMoney();
                    leftQty += lshspz.getQty() == null ? 0.0 : lshspz.getQty();
                }
                //贷方发生额则减少余额
                else{
                    balance -= lshspz.getMoney() == null ? 0.0 : lshspz.getMoney();
                    leftQty -= lshspz.getQty() == null ? 0.0 : lshspz.getQty();
                }

                //查询凭证
                query = session.createQuery("from Lspzk1 where itemNo = '"+lshspz.getItemNo()+"' and voucherNo = '"+lshspz.getVoucherNo()+"' and inputDate = '"+lshspz.getInputDate()+"' and entryNo = '"+lshspz.getEntryNo()+"'");
//                query.setParameter(0,lshspz.getItemNo());
//                query.setParameter(1,lshspz.getVoucherNo());
//                query.setParameter(2,lshspz.getInputDate());
//                query.setParameter(3,lshspz.getEntryNo());
                List<Lspzk1> lspzk1List = query.list();

                lspzk1VoForSearch.setLspzk1(lspzk1List.get(0));
                lspzk1VoForSearch.setMoney(balance);
                lspzk1VoForSearch.setQty(leftQty);

                lspzk1VoForSearches.add(lspzk1VoForSearch);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lspzk1VoForSearches;
        }
    }

    public Double queryColumn(String table_name, String col_name, String condition){
        Session session = null;
        Transaction tx = null;
        Double money = -1.0;
        try{
            session = getSession();
            tx = session.beginTransaction();

            Query query_sup = session.createQuery("select " + col_name + " from " + table_name + " where " + condition);
            List<Double> sups = query_sup.list();
            money = sups.get(0);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            tx.rollback();
            close(session);
            return money;
        }
    }


    public List<Lskmzd> querySpAccByCatNo(String id, String catNo1, String catNo2) {
        Session session = null;
        Transaction tx = null;
        List<Lskmzd> LskmzdList = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query;
            //科目编号为通配符
            if(id.equals("....")){
                if(catNo2 == null || catNo2.equals("")) {
                    query = session.createQuery("from Lskmzd where supAcc1 = ?");
                    query.setParameter(0, catNo1);
                    LskmzdList = query.list();
                }
                else{
                    query = session.createQuery("from Lskmzd where supAcc1 = ? and supAcc2 = ?");
                    query.setParameter(0, catNo1);
                    query.setParameter(1, catNo2);
                    LskmzdList = query.list();
                }
            }
            //科目编号后面含有通配符
            else if(id.contains("....")){
                if(catNo2 == null || catNo2.equals("")) {
                    id = id.substring(0, id.length() - 4);
                    query = session.createQuery("from Lskmzd where itemNo like ? and supAcc1 = ?");
                    query.setParameter(0, id + "%");
                    query.setParameter(1, catNo1);
                    LskmzdList = query.list();
                }
                else{
                    query = session.createQuery("from Lskmzd where itemNo like ? and supAcc1 = ? and supAcc2 = ?");
                    query.setParameter(0, id);
                    query.setParameter(1, catNo1);
                    query.setParameter(2, catNo2);
                    LskmzdList = query.list();
                }
            }
            //分类编号是通配符
            else{
                query = session.createQuery("from Lskmzd where itemNo = ?");
                query.setParameter(0, id);
                LskmzdList = query.list();
            }
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


    public QueryLskmzdFoSpAccountPageSearch queryLskmzdForHeadInfo(String itemNo,String spCatNo1,String spNo1,String spCatNo2,String spNo2) {
        Session session = null;
        Transaction tx = null;
        QueryLskmzdFoSpAccountPageSearch headInfo = new QueryLskmzdFoSpAccountPageSearch();
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
            if(spCatNo1 != null){
                if(spNo1.equals("...")){
                    headInfo.setSpName1(null);
                }else{
                    query = session.createQuery("from Lshszd where catNo = '"+spCatNo1+"' and spNo = '"+spNo1+"'");
                    List<Lshszd> lshszds = query.list();
                    if(lshszds.size()>0){
                        Lshszd lshszd = lshszds.get(0);
                        headInfo.setSpName1(lshszd.getSpName());
                    }
                }
            }else{
                headInfo.setSpName1(null);
            }


            if(spCatNo2 != null){
                if(spNo2.equals("...")){
                    headInfo.setSpName2(null);
                }else{
                    query = session.createQuery("from Lshszd where catNo = '"+spCatNo2+"' and spNo = '"+spNo2+"'");
                    List<Lshszd> lshszds = query.list();
                    if(lshszds.size()>0){
                        Lshszd lshszd = lshszds.get(0);
                        headInfo.setSpName2(lshszd.getSpName());
                    }
                }
            }else{
                headInfo.setSpName2(null);
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

}



