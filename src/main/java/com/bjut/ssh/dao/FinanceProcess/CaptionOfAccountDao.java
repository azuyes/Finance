package com.bjut.ssh.dao.FinanceProcess;

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
import java.lang.reflect.Method;

import static java.lang.System.in;

/**
 * @Title: CaptionOfAccountDao
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/4/10 12:57
 * @Version: 1.0
 */

@Repository
@Transactional
public class CaptionOfAccountDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        return this.sessionFactory.openSession();
    }

    public void close(Session session) {
        if (session != null)
            session.close();
    }

    /**
     * @return java.lang.String
     * @author czh
     * @Description 获取科目结构
     * @Date 2018/4/12 9:33
     * @Param []
     **/
    public String getSubjectStructure() {
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();

            Lsconf lsconf = (Lsconf) session.get(Lsconf.class, "sub_stru");

            tx.commit();
            close(session);
            return lsconf.getConfValue();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return "0";
        }
    }

    public List<String> getPrecisions() {
        Session session = null;
        Transaction tx = null;
        List<String> prec_list = new ArrayList();
        try {
            session = getSession();
            tx = session.beginTransaction();

            Lsconf price = (Lsconf) session.get(Lsconf.class, "price_dec");
            prec_list.add(price.getConfValue());
            Lsconf qty = (Lsconf) session.get(Lsconf.class, "quantity_dec");
            prec_list.add(qty.getConfValue());
            tx.commit();
            close(session);
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            return prec_list;
        }
    }

    /**
     * @return com.bjut.ssh.entity.Msg
     * @author czh
     * @Description 获取初始化配置信息
     * @Date 2018/6/13 16:13
     * @Param []
     **/
    public Msg getConfigsForCap() {
        Session session = null;
        Transaction tx = null;
        Msg result = Msg.success();
        try {
            session = getSession();
            tx = session.beginTransaction();

            //科目结构
            Lsconf stru = (Lsconf) session.get(Lsconf.class, "sub_stru");
            result.add("sub_stru", stru.getConfValue());
            //单价精度
            Lsconf price = (Lsconf) session.get(Lsconf.class, "price_dec");
            result.add("price_dec", price.getConfValue());
            //数量精度
            Lsconf qty = (Lsconf) session.get(Lsconf.class, "quantity_dec");
            result.add("quantity_dec", qty.getConfValue());
            //初始日期
            Lsconf begin = (Lsconf) session.get(Lsconf.class, "begin_date");
            result.add("begin_date", begin.getConfValue());

            //表头
            for(int i = 1; i <= 6; i++) {
                Lsconf table = (Lsconf) session.get(Lsconf.class, "table" + Integer.toString(i));
                result.add("table" + Integer.toString(i), table.getConfValue());
            }

            tx.commit();
            close(session);
            return result;
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "未知异常！");
        }
    }

    public boolean isAccountUsing() {
        Session session = null;
        Transaction tx = null;
        Msg result = Msg.success();
        try {
            session = getSession();
            tx = session.beginTransaction();

            Query query = session.createQuery("from Lspzk1 where auditorNo is not null and auditorNo != ''");
            List<Lspzk1> lspzk1List = query.list();
            if(lspzk1List.size() > 0) return true;
            else return false;
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasMoneyData(Lskmzd lskmzd){
        if((lskmzd.getBalance() != null && lskmzd.getBalance() != 0) ||
                (lskmzd.getCreditMoney() != null && lskmzd.getCreditMoney() != 0) ||
                (lskmzd.getDebitMoney() != null && lskmzd.getDebitMoney() != 0) ||
                (lskmzd.getBalance01() != null && lskmzd.getBalance01() != 0) ||
                (lskmzd.getBalance02() != null && lskmzd.getBalance02() != 0) ||
                (lskmzd.getBalance03() != null && lskmzd.getBalance03() != 0) ||
                (lskmzd.getBalance04() != null && lskmzd.getBalance04() != 0) ||
                (lskmzd.getBalance05() != null && lskmzd.getBalance05() != 0) ||
                (lskmzd.getBalance06() != null && lskmzd.getBalance06() != 0) ||
                (lskmzd.getBalance07() != null && lskmzd.getBalance07() != 0) ||
                (lskmzd.getBalance08() != null && lskmzd.getBalance08() != 0) ||
                (lskmzd.getBalance09() != null && lskmzd.getBalance09() != 0) ||
                (lskmzd.getBalance10() != null && lskmzd.getBalance10() != 0) ||
                (lskmzd.getBalance11() != null && lskmzd.getBalance11() != 0) ||
                (lskmzd.getBalance12() != null && lskmzd.getBalance12() != 0) ||
                (lskmzd.getCreditMoney01() != null && lskmzd.getCreditMoney01() != 0) ||
                (lskmzd.getCreditMoney02() != null && lskmzd.getCreditMoney02() != 0) ||
                (lskmzd.getCreditMoney03() != null && lskmzd.getCreditMoney03() != 0) ||
                (lskmzd.getCreditMoney04() != null && lskmzd.getCreditMoney04() != 0) ||
                (lskmzd.getCreditMoney05() != null && lskmzd.getCreditMoney05() != 0) ||
                (lskmzd.getCreditMoney06() != null && lskmzd.getCreditMoney06() != 0) ||
                (lskmzd.getCreditMoney07() != null && lskmzd.getCreditMoney07() != 0) ||
                (lskmzd.getCreditMoney08() != null && lskmzd.getCreditMoney08() != 0) ||
                (lskmzd.getCreditMoney09() != null && lskmzd.getCreditMoney09() != 0) ||
                (lskmzd.getCreditMoney10() != null && lskmzd.getCreditMoney10() != 0) ||
                (lskmzd.getCreditMoney11() != null && lskmzd.getCreditMoney11() != 0) ||
                (lskmzd.getCreditMoney12() != null && lskmzd.getCreditMoney12() != 0) ||
                (lskmzd.getDebitMoney01() != null && lskmzd.getDebitMoney01() != 0) ||
                (lskmzd.getDebitMoney02() != null && lskmzd.getDebitMoney02() != 0) ||
                (lskmzd.getDebitMoney03() != null && lskmzd.getDebitMoney03() != 0) ||
                (lskmzd.getDebitMoney02() != null && lskmzd.getDebitMoney02() != 0) ||
                (lskmzd.getDebitMoney05() != null && lskmzd.getDebitMoney05() != 0) ||
                (lskmzd.getDebitMoney06() != null && lskmzd.getDebitMoney06() != 0) ||
                (lskmzd.getDebitMoney07() != null && lskmzd.getDebitMoney07() != 0) ||
                (lskmzd.getDebitMoney08() != null && lskmzd.getDebitMoney08() != 0) ||
                (lskmzd.getDebitMoney09() != null && lskmzd.getDebitMoney09() != 0) ||
                (lskmzd.getDebitMoney10() != null && lskmzd.getDebitMoney10() != 0) ||
                (lskmzd.getDebitMoney11() != null && lskmzd.getDebitMoney11() != 0) ||
                (lskmzd.getDebitMoney12() != null && lskmzd.getDebitMoney12() != 0)
                ){
            return true;
        }
        else return false;
    }

    public boolean hasQtyData(Lskmsl lskmsl){
        if((lskmsl.getLeftQty() != null && lskmsl.getLeftQty() != 0) ||
                (lskmsl.getCreditQty() != null && lskmsl.getCreditQty() != 0) ||
                (lskmsl.getDebitQty() != null && lskmsl.getDebitQty() != 0) ||
                (lskmsl.getLeftQty01() != null && lskmsl.getLeftQty01() != 0) ||
                (lskmsl.getLeftQty02() != null && lskmsl.getLeftQty02() != 0) ||
                (lskmsl.getLeftQty03() != null && lskmsl.getLeftQty03() != 0) ||
                (lskmsl.getLeftQty04() != null && lskmsl.getLeftQty04() != 0) ||
                (lskmsl.getLeftQty05() != null && lskmsl.getLeftQty05() != 0) ||
                (lskmsl.getLeftQty06() != null && lskmsl.getLeftQty06() != 0) ||
                (lskmsl.getLeftQty07() != null && lskmsl.getLeftQty07() != 0) ||
                (lskmsl.getLeftQty08() != null && lskmsl.getLeftQty08() != 0) ||
                (lskmsl.getLeftQty09() != null && lskmsl.getLeftQty09() != 0) ||
                (lskmsl.getLeftQty10() != null && lskmsl.getLeftQty10() != 0) ||
                (lskmsl.getLeftQty11() != null && lskmsl.getLeftQty11() != 0) ||
                (lskmsl.getLeftQty12() != null && lskmsl.getLeftQty12() != 0) ||
                (lskmsl.getCreditQty01() != null && lskmsl.getCreditQty01() != 0) ||
                (lskmsl.getCreditQty02() != null && lskmsl.getCreditQty02() != 0) ||
                (lskmsl.getCreditQty03() != null && lskmsl.getCreditQty03() != 0) ||
                (lskmsl.getCreditQty04() != null && lskmsl.getCreditQty04() != 0) ||
                (lskmsl.getCreditQty05() != null && lskmsl.getCreditQty05() != 0) ||
                (lskmsl.getCreditQty06() != null && lskmsl.getCreditQty06() != 0) ||
                (lskmsl.getCreditQty07() != null && lskmsl.getCreditQty07() != 0) ||
                (lskmsl.getCreditQty08() != null && lskmsl.getCreditQty08() != 0) ||
                (lskmsl.getCreditQty09() != null && lskmsl.getCreditQty09() != 0) ||
                (lskmsl.getCreditQty10() != null && lskmsl.getCreditQty10() != 0) ||
                (lskmsl.getCreditQty11() != null && lskmsl.getCreditQty11() != 0) ||
                (lskmsl.getCreditQty12() != null && lskmsl.getCreditQty12() != 0) ||
                (lskmsl.getDebitQty01() != null && lskmsl.getDebitQty01() != 0) ||
                (lskmsl.getDebitQty02() != null && lskmsl.getDebitQty02() != 0) ||
                (lskmsl.getDebitQty03() != null && lskmsl.getDebitQty03() != 0) ||
                (lskmsl.getDebitQty02() != null && lskmsl.getDebitQty02() != 0) ||
                (lskmsl.getDebitQty05() != null && lskmsl.getDebitQty05() != 0) ||
                (lskmsl.getDebitQty06() != null && lskmsl.getDebitQty06() != 0) ||
                (lskmsl.getDebitQty07() != null && lskmsl.getDebitQty07() != 0) ||
                (lskmsl.getDebitQty08() != null && lskmsl.getDebitQty08() != 0) ||
                (lskmsl.getDebitQty09() != null && lskmsl.getDebitQty09() != 0) ||
                (lskmsl.getDebitQty10() != null && lskmsl.getDebitQty10() != 0) ||
                (lskmsl.getDebitQty11() != null && lskmsl.getDebitQty11() != 0) ||
                (lskmsl.getDebitQty12() != null && lskmsl.getDebitQty12() != 0)
                ){
            return true;
        }
        else return false;
    }

    /**
     * @return java.lang.String
     * @author czh
     * @Description 计算当前级别科目的去零后编号
     * @Date 2018/4/12 9:35
     * @Param [num, level]
     **/
    public String getCatNo(String num, String level) {
        int length = getNumLength(Integer.parseInt(level) - 1);
        String no = num.substring(0, length);
        return no;
    }

    /**
     * @return int
     * @author czh
     * @Description 计算当前级别科目编号长度
     * @Date 2018/4/13 11:27
     * @Param [level]
     **/
    public int getNumLength(int level) {
        if (level == 0) {
            return 0;
        }
        String sub_stru = getSubjectStructure();
        int length = 0;
        for (int i = 0; i < level; i++) {
            length += sub_stru.charAt(i) - '0';//获取当前位的固定长度
        }
        return length;
    }

    /**
     * @return int
     * @author czh
     * @Description 计算补零长度
     * @Date 2018/4/13 11:29
     * @Param [level]
     **/
    public int getZeroLength(int level) {
        int length = 0;
        String sub_stru = getSubjectStructure();
        for (int i = level; i < sub_stru.length(); i++) {
            length += sub_stru.charAt(i) - '0';
        }
        return length;
    }

    /**
     * @return java.lang.String
     * @author czh
     * @Description 获得完整的科目编号，即补0后的编号
     * @Date 2018/4/17 12:10
     * @Param [uperNo, level]
     **/
    public String getFullNo(String uperNo, int level) {
        int length = getZeroLength(level);
        String fullNo = uperNo;
        for (int i = 0; i < length; i++) {
            fullNo += "0";
        }
        return fullNo;
    }


    public Msg addAllCaptionOfAccount(LskmzdNLskmslQueryVo lskmzdNLskmslQueryVo) {
        Session session = null;
        Transaction tx = null;
        List<LskmzdNLskmslQueryVo> lskmzdNLskmslQueryVos = null;
        List<Lskmzd> lskmzdList = null;
        String last_num;
        try {
            session = getSession();
            tx = session.beginTransaction();

            Lskmzd lskmzd = lskmzdNLskmslQueryVo.getLskmzd();
            Lskmsl lskmsl = lskmzdNLskmslQueryVo.getLskmsl();
            Query query = session.createQuery("from Lskmzd where itemNo = '" + lskmzd.getItemNo() + "'");
            lskmzdList = query.list();
            if (lskmzdList.size() > 0) {
                close(session);
                return Msg.fail().add("errorInfo", "编号重复，请重新输入");
            }
            //如果科目正在使用
            boolean is_using = isAccountUsing();
            if(is_using && (hasQtyData(lskmsl) || hasMoneyData(lskmzd))){
                close(session);
                return Msg.fail().add("errorInfo", "科目正在使用，不允许添加金额或数量信息（均须填写0）");
            }
            //如果上级科目是末级且有金额信息
            if (is_using && !lskmzd.getItem().equals("1")) {
                last_num = getFullNo(getCatNo(lskmzd.getItemNo(), lskmzd.getItem()), Integer.parseInt(lskmzd.getItem()) - 1);
                String hql_last = "from Lskmzd as s where s.finLevel= 1 and s.itemNo = '" + last_num + "'";
                Query query_last = session.createQuery(hql_last);
                Lskmzd last = (Lskmzd)query_last.uniqueResult();
                if(last != null && hasMoneyData(last)){
                    return Msg.fail().add("errorInfo", "科目上级为有金额信息的末级，不允许添加下级");
                }
            }
            lskmzd.setFinLevel((byte) 1);
            session.save(lskmzd);
            if(lskmzd.getAccType().equals("Y")) session.save(lskmsl);

            if (!lskmzd.getItem().equals("1")) {
                last_num = getFullNo(getCatNo(lskmzd.getItemNo(), lskmzd.getItem()), Integer.parseInt(lskmzd.getItem()) - 1);
                String hql_update = "update Lskmzd as s set s.finLevel= 0 where s.itemNo = '" + last_num + "'";
                Query query_update = session.createQuery(hql_update);
                query_update.executeUpdate();
            }
            tx.commit();
            close(session);
            //上级科目金额应为下级合计
            if (!lskmzd.getItem().equals("1")) {
                updateUpperLevelMoney(lskmzd);
                if (lskmzd.getAccType().equals("Y")) {
                    updateUpperLevelQty(lskmsl, lskmzd.getItem());
                }
            }
            return Msg.success().add("lskmzd", lskmzd);
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "未知异常！");
        }
    }

    /**
     * @return com.bjut.ssh.entity.Msg
     * @author czh
     * @Description 添加科目字典条目
     * @Date 2018/4/12 9:33
     * @Param [lskmzd]
     **/
    public Msg addCaptionOfAccount(Lskmzd lskmzd) {
        Session session = null;
        Transaction tx = null;
        List<Lskmzd> lskmzdList = null;
        String last_num;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Lskmzd where itemNo = '" + lskmzd.getItemNo() + "'");
            lskmzdList = query.list();
            if (lskmzdList.size() > 0) {
                close(session);
                return Msg.fail().add("errorInfo", "编号重复，请重新输入");
            } else {
                lskmzd.setFinLevel((byte) 1);
                session.save(lskmzd);
                if (!lskmzd.getItem().equals("1")) {
                    last_num = getFullNo(getCatNo(lskmzd.getItemNo(), lskmzd.getItem()), Integer.parseInt(lskmzd.getItem()) - 1);
                    String hql_update = "update Lskmzd as s set s.finLevel= 0 where s.itemNo = '" + last_num + "'";
                    Query query_update = session.createQuery(hql_update);
                    query_update.executeUpdate();
                }
                //上级科目金额应为下级合计
                tx.commit();
                close(session);
                if (!lskmzd.getItem().equals("1")) {
                    updateUpperLevelMoney(lskmzd);
                }
                return Msg.success().add("lskmzd", lskmzd);
            }
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "未知异常！");
        }
    }

    public Msg delCaptionOfAccount(String itemNo, String level, String is_quan) {
        Session session = null;
        Transaction tx = null;
        String last_num;
        try {
            session = getSession();
            tx = session.beginTransaction();
            String new_id = getCatNo(itemNo, Integer.toString(Integer.parseInt(level) + 1));

            Lskmzd lskmzd = (Lskmzd) session.get(Lskmzd.class, itemNo);//根据主键id查找
            Lskmsl lskmsl = null;

            //判断该科目是否正在使用或科目里有金额信息
            List<Lspzk1> lspzk1List =  session.createQuery("from Lspzk1 where itemNo = '" + itemNo + "'").list();
            if(lspzk1List.size() > 0 || hasMoneyData(lskmzd)){
                return Msg.fail().add("errorInfo", "该科目正在使用，无法删除！");
            }

            if (is_quan.equals("Y")) {
                lskmsl =(Lskmsl) session.createQuery("from Lskmsl where itemNo = '" + itemNo + "'").list().get(0);
                Query slquery = session.createQuery("from Lskmsl where itemNo like '" + new_id + "%'");
                List<Lskmsl> lskmslList = slquery.list();
                for (Lskmsl del_lskmsl : lskmslList) {
                    session.delete(del_lskmsl);
                }
            }

            Query zdquery = session.createQuery("from Lskmzd where itemNo like '" + new_id + "%'");
            List<Lskmzd> lskmzdList = zdquery.list();
            for (Lskmzd del_lskmzd : lskmzdList) {
                session.delete(del_lskmzd);
            }

            //查找该层次有没有其他记录，如果没有则将上一级明细改为1
            if (!level.equals("1")) {
                String hqlQuery = "from Lskmzd as s where s.item = '" + level + "' and s.itemNo like '" + getCatNo(itemNo, level) + "%'";
                Query query = session.createQuery(hqlQuery);
                lskmzdList = query.list();
                if (lskmzdList.size() == 0) {
                    last_num = getFullNo(getCatNo(lskmzd.getItemNo(), lskmzd.getItem()), Integer.parseInt(lskmzd.getItem()) - 1);
                    String hql_update = "update Lskmzd as s set s.finLevel= 1 where s.itemNo = '" + last_num + "'";
                    Query query_update = session.createQuery(hql_update);
                    query_update.executeUpdate();
                }
            }

            tx.commit();
            close(session);
            if (!level.equals("1")) {
                updateUpperLevelMoney(lskmzd);
                if (is_quan.equals("Y")) {
                    updateUpperLevelQty(lskmsl, level);
                }
            }
            return Msg.success().add("lskmzd", lskmzd);
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "未知异常！");
        }
    }

    /**
     * @return com.bjut.ssh.entity.Msg
     * @author czh
     * @Description TODO
     * @Date 2018/4/16 16:11
     * @Param [lskmsl]
     **/
    public Msg addCaptionOfAccountQuantity(Lskmsl lskmsl) {
        Session session = null;
        Transaction tx = null;
        List<Lskmsl> lskmslList = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Lskmsl where itemNo = '" + lskmsl.getItemNo() + "'");
            lskmslList = query.list();
            if (lskmslList.size() > 0) {
                close(session);
                return Msg.fail().add("errorInfo", "编号重复，请重新输入");
            } else {
                session.save(lskmsl);
                tx.commit();
                close(session);
                return Msg.success().add("lskmsl", lskmsl);
            }
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "未知异常！");
        }
    }

    //更新上级科目金额信息合计
    public void updateUpperLevelMoney(Lskmzd lskmzd) {
        int level = Integer.parseInt(lskmzd.getItem());
        String id = lskmzd.getItemNo();

        for (int i = level; i > 1; i--) {
            //String upper_level = Integer.toString(i-1);
            String new_level = Integer.toString(i);
            String new_id = getCatNo(id, new_level);//得到上级的去0后的id
            List<Lskmzd> lskmzdList = queryCaptionOfAccountByLevel(new_id, new_level);//根据上级id查找当前级所有科目字典
            Lskmzd lskmzd_sum = computTotalMoney(lskmzdList, getFullNo(new_id, i - 1));
            editCaptionOfAccount(lskmzd_sum);
        }
    }

    //更新上级科目中的数量信息合计
    public void updateUpperLevelQty(Lskmsl lskmsl, String lv) {
        int level = Integer.parseInt(lv);
        String id = lskmsl.getItemNo();

        for (int i = level; i > 1; i--) {
            //String upper_level = Integer.toString(i-1);
            String new_level = Integer.toString(i);
            String new_id = getCatNo(id, new_level);//得到上级的去0后的id
            List<Lskmsl> lskmslList = queryCaptionOfAccountQtyByLevel(new_id);//根据上级id查找当前级所有科目数量
            Lskmsl lskmsl_sum = computTotalQty(lskmslList, getFullNo(new_id, i - 1));
            editCaptionOfAccountQty(lskmsl_sum);
        }
    }

//    public void equalQtyToMondy(Lskmzd lskmzd){
//        Lskmsl lskmsl = new Lskmsl();
//        lskmsl.setItemNo(lskmzd.getItemNo());
//        lskmsl.setLeftQty(lskmzd.getBalance());
//        lskmsl.setSupQty(lskmzd.getSupMoney());
//        lskmsl.setCreditQty(lskmzd.getCreditMoney());
//        lskmsl.setDebitQty(lskmzd.getDebitMoney());
//        editCaptionOfAccountQty(lskmsl);
//    }

    public Msg queryAllCaptionOfAccountByLevel(String id, String levelFlag) {
        Session session = null;
        Transaction tx = null;
        List<LskmzdNLskmslQueryVo> lskmzdNLskmslQueryVos = new ArrayList<LskmzdNLskmslQueryVo>();
        String hql;
        try {
            session = getSession();
            tx = session.beginTransaction();

            //本来想直接同时查询两个表，但是发现lskmsl如果为空的话就完全查询不到数据，只能先判断一下它是不是空的了，
            String c = "select count(*) from Lskmsl ";
            Query cq = session.createQuery(c);
            int count = ((Long) cq.iterate().next()).intValue();

            String hql_plus = "";
            if (count > 0) {
                hql_plus = ", Lskmsl as q";
            }

            if (levelFlag.equals("1")) {
                hql = "from Lskmzd as m " + hql_plus + " where m.item = '" + levelFlag + "' order by m.itemNo";
            } else {
                hql = "from Lskmzd as m " + hql_plus + " where m.itemNo like '" + id + "%' and m.item = '" + levelFlag + "' order by m.itemNo";
            }

            Query query = session.createQuery(hql);

            if(count > 0) {
                List<Object[]> list = query.list();
                for (Object[] objs : list) {
                    LskmzdNLskmslQueryVo lskmzdNLskmslQueryVo = new LskmzdNLskmslQueryVo();
                    lskmzdNLskmslQueryVo.setLskmzd((Lskmzd) objs[0]);
                    lskmzdNLskmslQueryVo.setItemNo(lskmzdNLskmslQueryVo.getLskmzd().getItemNo());
                    lskmzdNLskmslQueryVo.setLskmsl((Lskmsl) objs[1]);
                    if (lskmzdNLskmslQueryVos.size() > 0) {
                        if (!lskmzdNLskmslQueryVo.getItemNo().equals(lskmzdNLskmslQueryVos.get(lskmzdNLskmslQueryVos.size() - 1).getItemNo())) {
                            lskmzdNLskmslQueryVos.add(lskmzdNLskmslQueryVo);
                        }
                    } else {
                        lskmzdNLskmslQueryVos.add(lskmzdNLskmslQueryVo);
                    }
                }
            }
            else{
                List<Lskmzd> list = query.list();
                for (Lskmzd lskmzd : list) {
                    LskmzdNLskmslQueryVo lskmzdNLskmslQueryVo = new LskmzdNLskmslQueryVo();
                    lskmzdNLskmslQueryVo.setLskmzd(lskmzd);
                    lskmzdNLskmslQueryVo.setItemNo(lskmzdNLskmslQueryVo.getLskmzd().getItemNo());
                    if (lskmzdNLskmslQueryVos.size() > 0) {
                        if (!lskmzdNLskmslQueryVo.getItemNo().equals(lskmzdNLskmslQueryVos.get(lskmzdNLskmslQueryVos.size() - 1).getItemNo())) {
                            lskmzdNLskmslQueryVos.add(lskmzdNLskmslQueryVo);
                        }
                    } else {
                        lskmzdNLskmslQueryVos.add(lskmzdNLskmslQueryVo);
                    }
                }
            }
            tx.commit();
            close(session);
            return Msg.success().add("lskmzdNLskmslQueryVos", lskmzdNLskmslQueryVos);
        } catch (Exception e) {
            tx.rollback();
            close(session);
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "查询语句无效，请重新输入！");
        }
    }

    /**
     * @return java.util.List<com.bjut.ssh.entity.Lskmzd>
     * @author czh
     * @Description 按级数查询科目字典
     * @Date 2018/4/12 9:33
     * @Param [id, levelFlag]
     **/
    public List<Lskmzd> queryCaptionOfAccountByLevel(String id, String levelFlag) {
        Session session = null;
        Transaction tx = null;
        List<Lskmzd> LskmzdList = null;
        String hql;
        try {
            session = getSession();
            tx = session.beginTransaction();
            if (levelFlag.equals("1")) {
                hql = "from Lskmzd as s where s.item = '" + levelFlag + "' order by s.itemNo";
            } else {
                hql = "from Lskmzd as s where s.itemNo like '" + id + "%' and s.item = '" + levelFlag + "' order by s.itemNo";
            }
            Query query = session.createQuery(hql);
            LskmzdList = query.list();
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

    public List<Lskmsl> queryCaptionOfAccountQtyByLevel(String id) {
        Session session = null;
        Transaction tx = null;
        List<Lskmsl> LskmslList = null;
        String hql;
        try {
            session = getSession();
            tx = session.beginTransaction();
            hql = "from Lskmsl as s where s.itemNo like '" + id + "%' order by s.itemNo";
            Query query = session.createQuery(hql);
            LskmslList = query.list();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return LskmslList;
        }
    }

    public Lskmzd getCaptionOfAccount(String id) {
        Session session = null;
        Transaction tx = null;
        Lskmzd lskmzd = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            lskmzd = (Lskmzd) session.get(Lskmzd.class, id);//根据主键id查找
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

    /**
     * @return com.bjut.ssh.entity.Lskmzd
     * @author czh
     * @Description 计算特定科目下级各项金额总和
     * @Date 2018/5/7 10:18
     * @Param [lskmzdList, id]
     **/
    public Lskmzd computTotalMoney(List<Lskmzd> lskmzdList, String id) {
        int m = Integer.parseInt(getBeginMonth()) - 1;
        String last_month = m < 10 ? ("0" + Integer.toString(m)) : Integer.toString(m);
        Lskmzd lskmzd_sum = new Lskmzd();

        String methodName = "Balance" + last_month;

        try {
            //获取对象
            //Object sum_obj = lskmzd_sum;
            //获取方法
            Method setLastLeftMoney = lskmzd_sum.getClass().getDeclaredMethod("set" + methodName, Double.class);
            Method getLastLeftMoney = lskmzd_sum.getClass().getDeclaredMethod("get" + methodName);

            lskmzd_sum.setItemNo(id);
            //lskmzd_sum.setLeftQty(0.0);
            // 调用方法
            setLastLeftMoney.invoke(lskmzd_sum, 0.0);
            lskmzd_sum.setCreditMoneySup(0.0);
            lskmzd_sum.setDebitMoneySup(0.0);
            lskmzd_sum.setSupMoney(0.0);
            lskmzd_sum.setBalance(0.0);
            for (Lskmzd lskmzd : lskmzdList) {
                //lskmsl_sum.setLeftQty(lskmsl_sum.getLeftQty() + lskmsl.getLeftQty());
                //Object obj = lskmzd;
                Method getLast = lskmzd.getClass().getDeclaredMethod("get" + methodName);
                double this_balance = (double) getLast.invoke(lskmzd);
                double this_sum = (double) getLastLeftMoney.invoke(lskmzd_sum);
                setLastLeftMoney.invoke(lskmzd_sum, this_balance + this_sum);

                lskmzd_sum.setCreditMoneySup(lskmzd_sum.getCreditMoneySup() + lskmzd.getCreditMoneySup());
                lskmzd_sum.setDebitMoneySup(lskmzd_sum.getDebitMoneySup() + lskmzd.getDebitMoneySup());
                lskmzd_sum.setSupMoney(lskmzd_sum.getSupMoney() + lskmzd.getSupMoney());
                lskmzd_sum.setBalance(lskmzd_sum.getBalance() + lskmzd.getBalance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return lskmzd_sum;
        }
    }

    /**
     * @return com.bjut.ssh.entity.Lskmsl
     * @author czh
     * @Description 计算特定科目下级各项数量总和
     * @Date 2018/5/7 10:18
     * @Param [lskmslList, id]
     **/
    public Lskmsl computTotalQty(List<Lskmsl> lskmslList, String id) {
        int m = Integer.parseInt(getBeginMonth()) - 1;
        String last_month = m < 10 ? ("0" + Integer.toString(m)) : Integer.toString(m);
        Lskmsl lskmsl_sum = new Lskmsl();

        String methodName = "LeftQty" + last_month;
        try {
            //获取对象
            //Object sum_obj = lskmsl_sum;
            //获取方法
            Method setLastLeftQty = lskmsl_sum.getClass().getDeclaredMethod("set" + methodName, Double.class);
            Method getLastLeftQty = lskmsl_sum.getClass().getDeclaredMethod("get" + methodName);

            lskmsl_sum.setItemNo(id);
            //lskmsl_sum.setLeftQty(0.0);
            // 调用方法
            setLastLeftQty.invoke(lskmsl_sum, 0.0);
            lskmsl_sum.setCreditQtySup(0.0);
            lskmsl_sum.setDebitQtySup(0.0);
            lskmsl_sum.setSupQty(0.0);
            lskmsl_sum.setLeftQty(0.0);
            for (Lskmsl lskmsl : lskmslList) {
                //lskmsl_sum.setLeftQty(lskmsl_sum.getLeftQty() + lskmsl.getLeftQty());
                //Object obj = lskmsl;
                Method getLast = lskmsl.getClass().getDeclaredMethod("get" + methodName);
                setLastLeftQty.invoke(lskmsl_sum, (double) getLastLeftQty.invoke(lskmsl_sum) + (double) getLast.invoke(lskmsl));

                lskmsl_sum.setCreditQtySup(lskmsl_sum.getCreditQtySup() + lskmsl.getCreditQtySup());
                lskmsl_sum.setDebitQtySup(lskmsl_sum.getDebitQtySup() + lskmsl.getDebitQtySup());
                lskmsl_sum.setSupQty(lskmsl_sum.getSupQty() + lskmsl.getSupQty());
                lskmsl_sum.setLeftQty(lskmsl_sum.getLeftQty() + lskmsl.getLeftQty());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return lskmsl_sum;
        }
    }

    public List<Lskmzd> dealCaptionOfAccount(List<Lskmzd> LskmzdList) {
        //TODO:转换属性、账式、栏目、往来
        return LskmzdList;
    }

    /**
     * @return java.lang.String
     * @author czh
     * @Description 获取财务初始日期的月份
     * @Date 2018/4/12 17:22
     * @Param []
     **/
    public String getBeginMonth() {
        String month = getConfig("begin_date") != null ? getConfig("begin_date").substring(4, 6) : "0";
        return month;
    }

    /**
     * @return java.lang.String
     * @author czh
     * @Description 从Lsconf中获取相应配置信息
     * @Date 2018/6/12 11:25
     * @Param [key]
     **/
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

    public List<Lshsfl> querySpecialAcc() {
        Session session = null;
        List<Lshsfl> lshsflList = null;
        String hql;
        try {
            session = getSession();
            hql = "from Lshsfl";
            Query query = session.createQuery(hql);
            lshsflList = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lshsflList;
        }
    }

    public Msg editAllCaptionOfAccount(LskmzdNLskmslQueryVo lskmzdNLskmslQueryVo) {
        int m = Integer.parseInt(getBeginMonth()) - 1;
        String last_month = m < 10 ? ("0" + Integer.toString(m)) : Integer.toString(m);

        String methodName = "Balance" + last_month;
        if (m == 0) methodName = "SupMoney";

        Session session = null;
        Transaction tx = null;
        List<Lskmzd> lskmzdList = null;
        String last_num;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Lskmzd lskmzd = lskmzdNLskmslQueryVo.getLskmzd();
            Lskmsl lskmsl = lskmzdNLskmslQueryVo.getLskmsl();

            boolean sp_flag = false;//判断此时是否录入的是特殊科目

            Query query = session.createQuery("from Lskmzd where itemNo = '" + lskmzd.getItemNo() + "'");
            lskmzdList = query.list();
            //如果科目正在使用
            boolean is_using = isAccountUsing();
            if(is_using && (hasQtyData(lskmsl) || hasMoneyData(lskmzd))){
                close(session);
                return Msg.fail().add("errorInfo", "科目正在使用，不允许修改金额或数量信息（均须填写0）");
            }

            Lskmzd edit_lskmzd = (Lskmzd) session.get(Lskmzd.class, lskmzd.getItemNo());//根据主键id查找

            //获取对象
            //Object edit_obj = edit_lskmzd;
            //获取方法
            Method setLastLeftMoney = edit_lskmzd.getClass().getDeclaredMethod("set" + methodName, Double.class);

            //编辑特殊科目
            if (lskmzd.getJournal() != null) {
                sp_flag = true;
                if(is_using){
                    close(session);
                    return Msg.fail().add("errorInfo", "科目正在使用，不允许修改特殊科目");
                }

                String new_id = getCatNo(edit_lskmzd.getItemNo(), Integer.toString(Integer.parseInt(edit_lskmzd.getItem()) + 1));
                Query zdquery = session.createQuery("from Lskmzd where itemNo like '" + new_id + "%'");
                List<Lskmzd> sub_lskmzdList = zdquery.list();

                //所有子科目的特殊科目和上级一致
                for (Lskmzd sub_lskmzd : sub_lskmzdList) {

                    sub_lskmzd.setJournal(lskmzd.getJournal());
                    sub_lskmzd.setExchang(lskmzd.getExchang());
                    sub_lskmzd.setSupAcc1(lskmzd.getSupAcc1());
                    sub_lskmzd.setSupAcc2(lskmzd.getSupAcc2());
                    session.update(sub_lskmzd);
                }

                edit_lskmzd.setJournal(lskmzd.getJournal());
                edit_lskmzd.setExchang(lskmzd.getExchang());
                edit_lskmzd.setSupAcc1(lskmzd.getSupAcc1());
                edit_lskmzd.setSupAcc2(lskmzd.getSupAcc2());
            }
            //编辑会计科目
            else if (lskmzd.getItemName() != null) {
                edit_lskmzd.setItemName(lskmzd.getItemName());

                String new_id = getCatNo(edit_lskmzd.getItemNo(), Integer.toString(Integer.parseInt(edit_lskmzd.getItem()) + 1));
                Query zdquery = session.createQuery("from Lskmzd where itemNo like '" + new_id + "%'");
                List<Lskmzd> sub_lskmzdList = zdquery.list();

                //所有子科目的栏目/科目和上级一致
                for (Lskmzd sub_lskmzd : sub_lskmzdList) {
                    sub_lskmzd.setEle(lskmzd.getEle());
                    sub_lskmzd.setAccType(lskmzd.getAccType());
                    sub_lskmzd.setSpType(lskmzd.getSpType());
                    session.update(sub_lskmzd);
                }
                edit_lskmzd.setEle(lskmzd.getEle());
                edit_lskmzd.setAccType(lskmzd.getAccType());
                edit_lskmzd.setSpType(lskmzd.getSpType());
//                edit_lskmzd.setBalance(lskmzd.getBalance());
//                edit_lskmzd.setCreditMoneySup(lskmzd.getCreditMoneySup());
//                edit_lskmzd.setDebitMoneySup(lskmzd.getDebitMoneySup());
//                edit_lskmzd.setSupMoney(lskmzd.getSupMoney());

                //根据下级合计修改上级金额部分
                //edit_lskmzd.setBalance(lskmzd.getBalance());
                //Object obj = lskmzd;
                Method getLast = lskmzd.getClass().getDeclaredMethod("get" + methodName);
                setLastLeftMoney.invoke(edit_lskmzd, getLast.invoke(lskmzd));

                edit_lskmzd.setCreditMoneySup(lskmzd.getCreditMoneySup());
                edit_lskmzd.setDebitMoneySup(lskmzd.getDebitMoneySup());
                edit_lskmzd.setSupMoney(lskmzd.getSupMoney());

                session.update(edit_lskmzd);

                if (lskmzd.getAccType().equals("Y")) {
                    methodName = "LeftQty" + last_month;
                    if (m == 0) methodName = "SupQty";

                    Query slquery = session.createQuery("from Lskmsl where itemNo = '" + lskmsl.getItemNo() + "'");

                    List<Lskmsl> lskmslList = slquery.list();
                    for (Lskmsl edit_lskmsl : lskmslList) {
                        Method setLastLeftQty = edit_lskmsl.getClass().getDeclaredMethod("set" + methodName, Double.class);

                        //编辑会计科目
                        if (lskmsl.getHead1() != null) {
                            edit_lskmsl.setHead1(lskmsl.getHead1());
                            edit_lskmsl.setHead2(lskmsl.getHead2());
                            edit_lskmsl.setHead3(lskmsl.getHead3());
                            edit_lskmsl.setHead4(lskmsl.getHead4());
                            edit_lskmsl.setHead5(lskmsl.getHead5());
                            edit_lskmsl.setHead6(lskmsl.getHead6());
//                    edit_lskmsl.setLeftQty(lskmsl.getLeftQty());
//                    edit_lskmsl.setCreditQtySup(lskmsl.getCreditQtySup());
//                    edit_lskmsl.setDebitQtySup(lskmsl.getDebitQtySup());
//                    edit_lskmsl.setSupQty(lskmsl.getSupQty());
                        }
                        //根据下级合计修改上级金额部分
                        //edit_lskmsl.setLeftQty(lskmsl.getLeftQty());
                        Method getLastQty = lskmsl.getClass().getDeclaredMethod("get" + methodName);
                        setLastLeftQty.invoke(edit_lskmsl, getLastQty.invoke(lskmsl));

                        edit_lskmsl.setCreditQtySup(lskmsl.getCreditQtySup());
                        edit_lskmsl.setDebitQtySup(lskmsl.getDebitQtySup());
                        edit_lskmsl.setSupQty(lskmsl.getSupQty());

                        session.update(edit_lskmsl);
                    }
                }
            }

            tx.commit();
            close(session);

            if (!lskmzd.getItem().equals("1") && !sp_flag) {
                updateUpperLevelMoney(lskmzd);
                if (lskmzd.getAccType().equals("Y")) {
                    updateUpperLevelQty(lskmsl, lskmzd.getItem());
                }
            }

            return Msg.success().add("lskmzd", lskmzd);
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "未知异常！");
        }
    }

    public Msg editCaptionOfAccount(Lskmzd lskmzd) {
        int m = Integer.parseInt(getBeginMonth()) - 1;
        String last_month = m < 10 ? ("0" + Integer.toString(m)) : Integer.toString(m);

        String methodName = "Balance" + last_month;
        if (m == 0) methodName = "SupMoney";

        Session session = null;
        Transaction tx = null;
        List<Lskmzd> lskmzdList = null;
        String last_num;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Lskmzd edit_lskmzd = (Lskmzd) session.get(Lskmzd.class, lskmzd.getItemNo());//根据主键id查找
            boolean sp_flag = false;

            //获取对象
            //Object edit_obj = edit_lskmzd;
            //获取方法
            Method setLastLeftMoney = edit_lskmzd.getClass().getDeclaredMethod("set" + methodName, Double.class);

            //编辑特殊科目
            if (lskmzd.getJournal() != null) {
                sp_flag = true;
                String new_id = getCatNo(edit_lskmzd.getItemNo(), Integer.toString(Integer.parseInt(edit_lskmzd.getItem()) + 1));
                Query zdquery = session.createQuery("from Lskmzd where itemNo like '" + new_id + "%'");
                List<Lskmzd> sub_lskmzdList = zdquery.list();

                //所有子科目的特殊科目和上级一致
                for (Lskmzd sub_lskmzd : sub_lskmzdList) {
                    sub_lskmzd.setJournal(lskmzd.getJournal());
                    sub_lskmzd.setExchang(lskmzd.getExchang());
                    sub_lskmzd.setSupAcc1(lskmzd.getSupAcc1());
                    sub_lskmzd.setSupAcc2(lskmzd.getSupAcc2());
                    session.update(sub_lskmzd);
                }

                edit_lskmzd.setJournal(lskmzd.getJournal());
                edit_lskmzd.setExchang(lskmzd.getExchang());
                edit_lskmzd.setSupAcc1(lskmzd.getSupAcc1());
                edit_lskmzd.setSupAcc2(lskmzd.getSupAcc2());
            }
            //编辑会计科目
            else if (lskmzd.getItemName() != null) {
                edit_lskmzd.setItemName(lskmzd.getItemName());
                edit_lskmzd.setEle(lskmzd.getEle());
                edit_lskmzd.setAccType(lskmzd.getAccType());
                edit_lskmzd.setSpType(lskmzd.getSpType());
//                edit_lskmzd.setBalance(lskmzd.getBalance());
//                edit_lskmzd.setCreditMoneySup(lskmzd.getCreditMoneySup());
//                edit_lskmzd.setDebitMoneySup(lskmzd.getDebitMoneySup());
//                edit_lskmzd.setSupMoney(lskmzd.getSupMoney());
            }

            if(!sp_flag) {
                //根据下级合计修改上级金额部分
                //edit_lskmzd.setBalance(lskmzd.getBalance());
                //Object obj = lskmzd;
                Method getLast = lskmzd.getClass().getDeclaredMethod("get" + methodName);
                setLastLeftMoney.invoke(edit_lskmzd, getLast.invoke(lskmzd));

                edit_lskmzd.setCreditMoneySup(lskmzd.getCreditMoneySup());
                edit_lskmzd.setDebitMoneySup(lskmzd.getDebitMoneySup());
                edit_lskmzd.setSupMoney(lskmzd.getSupMoney());
            }
            session.update(edit_lskmzd);
            tx.commit();
            close(session);
            return Msg.success().add("lskmzd", lskmzd);
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "未知异常！");
        }
    }

    public Msg editCaptionOfAccountQty(Lskmsl lskmsl) {
        int m = Integer.parseInt(getBeginMonth()) - 1;
        String last_month = m < 10 ? ("0" + Integer.toString(m)) : Integer.toString(m);

        String methodName = "LeftQty" + last_month;
        if (m == 0) methodName = "SupQty";
        Session session = null;
        Transaction tx = null;
        String last_num;
        try {
            session = getSession();
            tx = session.beginTransaction();
            //Lskmsl edit_lskmsl = (Lskmsl) session.get(Lskmsl.class, lskmsl.getItemNo());//根据主键id查找
            Query slquery = session.createQuery("from Lskmsl where itemNo = '" + lskmsl.getItemNo() + "'");

            List<Lskmsl> lskmslList = slquery.list();
            for (Lskmsl edit_lskmsl : lskmslList) {
                Method setLastLeftMoney = edit_lskmsl.getClass().getDeclaredMethod("set" + methodName, Double.class);

                //编辑会计科目
                if (lskmsl.getHead1() != null) {
                    edit_lskmsl.setHead1(lskmsl.getHead1());
                    edit_lskmsl.setHead2(lskmsl.getHead2());
                    edit_lskmsl.setHead3(lskmsl.getHead3());
                    edit_lskmsl.setHead4(lskmsl.getHead4());
                    edit_lskmsl.setHead5(lskmsl.getHead5());
                    edit_lskmsl.setHead6(lskmsl.getHead6());
//                    edit_lskmsl.setLeftQty(lskmsl.getLeftQty());
//                    edit_lskmsl.setCreditQtySup(lskmsl.getCreditQtySup());
//                    edit_lskmsl.setDebitQtySup(lskmsl.getDebitQtySup());
//                    edit_lskmsl.setSupQty(lskmsl.getSupQty());
                }
                //根据下级合计修改上级金额部分
                //edit_lskmsl.setLeftQty(lskmsl.getLeftQty());
                Method getLast = lskmsl.getClass().getDeclaredMethod("get" + methodName);
                setLastLeftMoney.invoke(edit_lskmsl, getLast.invoke(lskmsl));

                edit_lskmsl.setCreditQtySup(lskmsl.getCreditQtySup());
                edit_lskmsl.setDebitQtySup(lskmsl.getDebitQtySup());
                edit_lskmsl.setSupQty(lskmsl.getSupQty());

                session.update(edit_lskmsl);
            }
            tx.commit();
            close(session);

            return Msg.success().add("lskmsl", lskmsl);
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "未知异常！");
        }
    }

    public String getCatNameOfLshsfl(String catNo) {
        Session session = null;
        String catName = "";
        try {
            session = getSession();
            Lshsfl lshsfl = (Lshsfl) session.get(Lshsfl.class, catNo);
            catName = lshsfl.getCatName();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return catName;
        }
    }
}
