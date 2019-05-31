package com.bjut.ssh.dao.FinanceProcess;

import com.bjut.ssh.entity.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: FinanceSearchDao
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/7/3 10:07
 * @Version: 1.0
 */
@Repository
@Transactional
public class FinanceSearchDao {
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    public void close(Session session){
        if(session != null)
            session.close();
    }

    public Msg getConfigsForSearch(){
        Session session = null;
        Transaction tx = null;
        Msg result = Msg.success();
        try {
            session = getSession();
            tx = session.beginTransaction();

            //科目结构
            Lsconf stru = (Lsconf) session.get(Lsconf.class, "sub_stru");
            result.add("sub_stru", stru.getConfValue());
            //财务日期
            Lsconf begin = (Lsconf) session.get(Lsconf.class, "current_date");
            result.add("current_date", begin.getConfValue());
            //打印行数
            Lsconf bill_row = (Lsconf) session.get(Lsconf.class, "bill_row");
            result.add("bill_row", bill_row.getConfValue());
            Lsconf print_row = (Lsconf) session.get(Lsconf.class, "print_row");
            result.add("print_row", print_row.getConfValue());
            //出纳
            Lsconf cashier = (Lsconf) session.get(Lsconf.class, "cashier");
            result.add("cashier", cashier.getConfValue());
            //经办人
            Lsconf operator = (Lsconf) session.get(Lsconf.class, "operator");
            result.add("operator", operator.getConfValue());
            //单位名称
            Lsconf unit_name = (Lsconf) session.get(Lsconf.class, "unit_name");
            result.add("unit_name", unit_name.getConfValue());

            tx.commit();
            close(session);
            return result;
        }
        catch(Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
            return Msg.fail().add("errorInfo","未知异常！");
        }
    }

    public Msg queryCaptionWithSql(String id, String levelFlag, String from, String to, Boolean is_show_all, String sql) {
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

            if(is_show_all){
                if (levelFlag.equals("1")) {
                    hql = "from Lskmzd as m " + hql_plus + " where m.item >= '" + levelFlag + "' and m.itemNo >= '" + from + "' and m.itemNo <='" + to + "'";
                } else {
                    hql = "from Lskmzd as m " + hql_plus + " where m.itemNo like '" + id + "%' and m.item >= '" + levelFlag + "' and m.itemNo >= '" + from + "' and m.itemNo <='" + to + "'";
                }
            }
            else {
                if (levelFlag.equals("1")) {
                    hql = "from Lskmzd as m " + hql_plus + " where m.item = '" + levelFlag + "' and m.itemNo >= '" + from + "' and m.itemNo <='" + to + "'";
                } else {
                    hql = "from Lskmzd as m " + hql_plus + " where m.itemNo like '" + id + "%' and m.item = '" + levelFlag + "' and m.itemNo >= '" + from + "' and m.itemNo <='" + to + "'";
                }
            }
            if(!sql.equals("0")){
                hql = hql + "and " + sql + " order by m.itemNo";
            }
            Query query = session.createQuery(hql);
            List<Object[]> list = query.list();
            for(Object[] objs : list){
                LskmzdNLskmslQueryVo lskmzdNLskmslQueryVo = new LskmzdNLskmslQueryVo();
                lskmzdNLskmslQueryVo.setLskmzd((Lskmzd)objs[0]);
                lskmzdNLskmslQueryVo.setItemNo(lskmzdNLskmslQueryVo.getLskmzd().getItemNo());
                if(count > 0) lskmzdNLskmslQueryVo.setLskmsl((Lskmsl)objs[1]);
                if(lskmzdNLskmslQueryVos.size() > 0){
                    if(!lskmzdNLskmslQueryVo.getItemNo().equals(lskmzdNLskmslQueryVos.get(lskmzdNLskmslQueryVos.size()-1).getItemNo())) {
                        lskmzdNLskmslQueryVos.add(lskmzdNLskmslQueryVo);
                    }
                }
                else{
                    lskmzdNLskmslQueryVos.add(lskmzdNLskmslQueryVo);
                }
            }
//            for(Lskmzd lskmzd : lskmzdList){
//                hql = "from Lskmsl as s where s.itemNo = '"+lskmzd.getItemNo()+"'";
//                Query sl_query = session.createQuery(hql);
//                List<Lskmsl> lskmslList = sl_query.list();
//
//                LskmzdNLskmslQueryVo lskmzdNLskmslQueryVo = new LskmzdNLskmslQueryVo();
//                lskmzdNLskmslQueryVo.setItemNo(lskmzd.getItemNo());
//                lskmzdNLskmslQueryVo.setLskmzd(lskmzd);
//                if(lskmslList.size() > 0)
//                    lskmzdNLskmslQueryVo.setLskmsl(lskmslList.get(0));
//                lskmzdNLskmslQueryVos.add(lskmzdNLskmslQueryVo);
//            }
            tx.commit();
            close(session);
            return Msg.success().add("lskmzdNLskmslQueryVos",lskmzdNLskmslQueryVos);
        } catch (Exception e) {
            tx.rollback();
            close(session);
            e.printStackTrace();
            return Msg.fail().add("errorInfo","查询语句无效，请重新输入！");
        }
    }

    public List<Lspzk1> queryVouchersWithSql(String year_month, String sql){
        ///主要内容
        //查询Lspzk1并把所有金额相加
        ///详细内容
        //查询Lspzk1得到一条数据后，查询对应的Lsyspz和Lshspz，对每个查到的子条目插入到最终的list中
//        StringBuffer sb = new StringBuffer(sql);
//        for(int i = 2; i < sql.length(); i ++){
//            String cur = String.valueOf(sql.charAt(i-2)) + String.valueOf(sql.charAt(i-1)) + String.valueOf(sql.charAt(i));
//            if(cur.equals("%20")){
//                sb.replace(i-2, i+1, " ");
//            }
//        }
//        sql = sb.toString();

        Session session = null;
        Transaction tx = null;
        List<Lspzk1> lspzk1List = new ArrayList<Lspzk1>();
        try{
            session = getSession();
            tx = session.beginTransaction();
            if(sql.equals("0")){
                sql = "";
            }
            else{
                sql = "and " + sql;
            }
            String hql = "from Lspzk1 where inputDate like '" + year_month + "%' " + sql + " order by inputDate, voucherNo";
            Query query = session.createQuery(hql);
            lspzk1List = query.list();
            return lspzk1List;
        }
        catch(Exception e) {
            tx.rollback();
            close(session);
            e.printStackTrace();
            throw e;
        }
    }

    public Msg queryDetailWithSql(String year_month, String itemNo, String sql){
        Session session = null;
        Transaction tx = null;
        List<Lspzk1VoForSearch> lspzk1VoForSearches = new ArrayList<>();
        try{
            session = getSession();
            tx = session.beginTransaction();

            //如果sql语句为0则为初始查询
            String hql = "from Lspzk1 where inputDate like '" + year_month + "%' and itemNo = '" + itemNo + "' order by inputDate, voucherNo";
            Query query = session.createQuery(hql);
            List<Lspzk1> lspzk1List = query.list();

            //查找符合sql条件的条目
            List<Lspzk1> cond_lspzk1List = new ArrayList<>();
            if(!sql.equals("0")) {
                String cond_hql = "from Lspzk1 where inputDate like '" + year_month + "%' and itemNo = '" + itemNo + "' and " + sql + " order by inputDate, voucherNo";
                Query cond_query = session.createQuery(cond_hql);
                cond_lspzk1List = cond_query.list();
            }

            //计算上期结转
            String supMoney_str = "";
            String supQty_str = "";
            int last_month = Integer.parseInt(year_month.substring(4, 6)) - 1;
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
            String condition = "itemNo = '" + itemNo + "'";
            Double supMoney = queryColumn("Lskmzd", supMoney_str, condition);
            Double supQty = queryColumn("Lskmsl", supQty_str, condition);
            supMoney = supMoney == null ? 0.0 : supMoney;
            supQty = supQty == null ? 0.0 : supQty;

            //添加上期结转行
            Lspzk1 sup_pzk = new Lspzk1();
            sup_pzk.setSummary("上期结转");
            Lspzk1VoForSearch sup_row = new Lspzk1VoForSearch();
            sup_row.setLspzk1(sup_pzk);
            sup_row.setMoney(supMoney);
            sup_row.setQty(supQty);
            lspzk1VoForSearches.add(sup_row);

            Double balance = supMoney;//当前余额
            Double leftQty = supQty;
            for(int i = 0, j = 0; i < lspzk1List.size(); i++){
                Lspzk1 lspzk1 = lspzk1List.get(0);
                Lspzk1VoForSearch lspzk1VoForSearch = new Lspzk1VoForSearch();
                lspzk1VoForSearch.setLspzk1(lspzk1);

                //借方发生额则增加余额
                if(lspzk1.getBkpDirection().equals("J")){
                    balance += lspzk1.getMoney() == null ? 0.0 : lspzk1.getMoney();
                    leftQty += lspzk1.getQty() == null ? 0.0 : lspzk1.getQty();
                }
                //贷方发生额则减少余额
                else{
                    balance -= lspzk1.getMoney() == null ? 0.0 : lspzk1.getMoney();
                    leftQty -= lspzk1.getQty() == null ? 0.0 : lspzk1.getQty();
                }
                lspzk1VoForSearch.setMoney(balance);
                lspzk1VoForSearch.setQty(leftQty);

                if(sql.equals("0")) {
                    lspzk1VoForSearches.add(lspzk1VoForSearch);
                }
                else{
                    if (lspzk1.getPzk1AutoId() == cond_lspzk1List.get(j).getPzk1AutoId()) {
                        lspzk1VoForSearches.add(lspzk1VoForSearch);
                        j++;
                    }
                }
            }
            tx.commit();
            close(session);
            return Msg.success().add("lspzk1VoForSearches", lspzk1VoForSearches);
        }
        catch(Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
            return Msg.fail().add("errorInfo","查询语句无效，请重新输入！");
        }
    }

    public Double queryColumn(String table_name, String col_name, String condition){
        Session session = null;
        Double money = 0.0;
        try{
            session = getSession();
            Query query_sup = session.createQuery("select " + col_name + " from " + table_name + " where " + condition);
            Double sup = (Double)query_sup.uniqueResult();
            money = sup == null ? 0.0 : sup;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            close(session);
            return money;
        }
    }

    public Msg queryJournalWithSql(String from, String to, String itemNo, String sql){
        Session session = null;
        Transaction tx = null;
        List<LsyspzQueryVo> lsyspzQueryVoList = new ArrayList<>();
        try{
            session = getSession();
            tx = session.beginTransaction();

            String hql = "from Lsyspz where inputDate >= '" + from + "' and inputDate <= '" + to + "' and itemNo = '" + itemNo + "' order by inputDate, voucherNo";
            Query query = session.createQuery(hql);
            List<Lsyspz> lsyspzList = query.list();

            //查找符合sql语句条件的条目
            List<Lsyspz> cond_lsyspzList = new ArrayList<>();
            if(!sql.equals("0")) {
                String cond_hql = "from Lsyspz where inputDate >= '" + from + "' and inputDate <= '" + to + "' and itemNo = '" + itemNo + "' and " + sql + " order by inputDate, voucherNo";
                Query cond_query = session.createQuery(cond_hql);
                cond_lsyspzList = cond_query.list();
            }

            //获取月初到初试日期的原始凭证以计算余额
            Double before_money = 0.0;
            if(!from.substring(6, 8).equals("01")){
                String before_from = from.substring(0, 6) + "01";
                String before_hql = "from Lsyspz where inputDate >= '" + before_from + "' and inputDate < '" + from + "' and itemNo = '" + itemNo + "' order by inputDate, voucherNo";
                Query before_query = session.createQuery(before_hql);
                List<Lsyspz> before_lsyspzList = before_query.list();

                for(Lsyspz before_lsyspz : before_lsyspzList){

                    if(before_lsyspz.getBkpDirection().equals("J")){
                        before_money += before_lsyspz.getMoney() == null ? 0.0 : before_lsyspz.getMoney();
                    }
                    else{
                        before_money -= before_lsyspz.getMoney() == null ? 0.0 : before_lsyspz.getMoney();
                    }
                }
            }

            String supMoney_str;
            int last_month = Integer.parseInt(from.substring(4, 6)) - 1;
            if(last_month == 0){
                supMoney_str = "supMoney";
            }
            else{
                String month_str = last_month < 10 ? "0" + Integer.toString(last_month) : Integer.toString(last_month);
                supMoney_str = "balance" + month_str;
            }
            Query query_sup = session.createQuery("select " + supMoney_str + " from Lskmzd where itemNo = '" + itemNo + "'");
            List<Double> sups = query_sup.list();
            Double supMoney = sups.get(0) == null ? 0.0 : sups.get(0);

            //添加上期结转行
            Lsyspz sup_ori = new Lsyspz();
            sup_ori.setSummary("上期结转");
            LsyspzQueryVo sup_row = new LsyspzQueryVo();
            sup_row.setLsyspz(sup_ori);
            sup_row.setMoney(supMoney);
            lsyspzQueryVoList.add(sup_row);

            //设定当前余额为上期结转+月初至初始日期的余额累计
            Double balance = supMoney + before_money;
            for(int i = 0, j = 0; i < lsyspzList.size(); i++){
                Lsyspz lsyspz = lsyspzList.get(i);
                LsyspzQueryVo lsyspzQueryVo = new LsyspzQueryVo();
                lsyspzQueryVo.setLsyspz(lsyspz);

                String bkp;
                if(lsyspz.getBkpDirection().equals("J")){
                    balance += lsyspz.getMoney() == null ? 0.0 : lsyspz.getMoney();
                    bkp = "D";
                }
                else{
                    balance -= lsyspz.getMoney() == null ? 0.0 : lsyspz.getMoney();
                    bkp = "J";
                }

                //查找对方科目， 借方金额就选贷方最大金额的科目
                Query query_oppo = session.createQuery("select itemNo from Lspzk1 where money = (select max(v.money) from Lspzk1 v where bkpDirection = '" + bkp + "')");
                List<String> itemNoList = query_oppo.list();
                Lskmzd dict = (Lskmzd) session.get(Lskmzd.class, itemNoList.get(0));
                String itemName = dict.getItemName();
                lsyspzQueryVo.setOppoItem(itemName);

                lsyspzQueryVo.setMoney(balance);

                //筛选出符合条件的条目
                if(sql.equals("0")) {
                    lsyspzQueryVoList.add(lsyspzQueryVo);
                }
                else{
                    if(lsyspz.getYspzAutoId() == cond_lsyspzList.get(j).getYspzAutoId()){
                        j++;
                        lsyspzQueryVoList.add(lsyspzQueryVo);
                    }
                }
            }
            tx.commit();
            close(session);
            return Msg.success().add("lsyspzQueryVoList", lsyspzQueryVoList);
        }
        catch(Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
            return Msg.fail().add("errorInfo","查询语句无效，请重新输入！");
        }
    }

    public List<Lspzk1> queryVoucherByItem(String from, String to, String itemNo){
        Session session = null;
        Transaction tx = null;
        List<Lspzk1> Lspzk1List = null;
        String hql;
        try {
            session = getSession();
            tx = session.beginTransaction();

            hql = "from Lspzk1 as s where s.itemNo = '" + itemNo + "' and s.inputDate >='" + from + "' and s.inputDate <='" + to + "' order by inputDate, voucherNo";
            Query query = session.createQuery(hql);
            Lspzk1List= query.list();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return Lspzk1List;
        }
    }

    public List<Lspzk1> queryVoucherWithSql(String from, String to, String itemNo, String sql){
        Session session = null;
        Transaction tx = null;
        List<Lspzk1> cond_Lspzk1List = null;
        String hql;
        try {
            session = getSession();
            tx = session.beginTransaction();

            sql = " and " + sql;
            String cond_hql = "from Lspzk1 as s where s.itemNo = '" + itemNo + "' and s.inputDate >='" + from + "' and s.inputDate <='" + to + "'" + sql + "  order by inputDate, voucherNo";
            Query cond_query = session.createQuery(cond_hql);
            cond_Lspzk1List= cond_query.list();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return cond_Lspzk1List;
        }
    }

}
