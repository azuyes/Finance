package com.bjut.ssh.dao.FinanceProcess;

import com.bjut.ssh.DatabaseBackup.DatabaseBackup;
import com.bjut.ssh.entity.*;
import com.bjut.ssh.getProperties.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: MonthlyStatementDao
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/5/21 14:26
 * @Version: 1.0
 */
@Repository
@Transactional
public class MonthlyStatementDao {
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    public void close(Session session){
        if(session != null)
            session.close();
    }

    public Msg checkCurrentDate(){
        Session session = null;
        Transaction tx = null;
        try{
            session = getSession();
            tx = session.beginTransaction();
            Lsconf lsconf = (Lsconf) session.get(Lsconf.class,"current_date");
            String date = lsconf.getConfValue();
            return Msg.success().add("date", date);
        }
        catch (Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "检查当前财务日期发生未知异常！");
        }
    }

    /**
    *@author czh
    *@Description 检查当月是否有未审核凭证
    *@Date 2018/5/23 14:34
    *@Param [year_month]
    *@return com.bjut.ssh.entity.Msg
    **/
    public Msg checkReviewedStatement(String year_month){
        Session session = null;
        Transaction tx = null;
        try{
            session = getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Lspzk1 where inputDate like '" + year_month + "%' and auditor is null");
            List<Lspzk1> lspzk1List = query.list();
            if(lspzk1List.size() > 0){
                close(session);
                return Msg.success().add("info", "fail");
            }
            else{
                close(session);
                return Msg.success().add("info", "pass");
            }
        }
        catch(Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "检查未审核凭证发生未知异常！");
        }
    }

    /**
    *@author czh
    *@Description 检查当月损益类科目余额是否为0
    *@Date 2018/5/23 14:34
    *@Param [year_month]
    *@return com.bjut.ssh.entity.Msg
    **/
    public Msg checkLossNProfitBalance(String year_month){
        Session session = null;
        Transaction tx = null;
        try{
            session = getSession();
            tx = session.beginTransaction();
            String balance = "balance" + year_month.substring(4, 6);
            Query query = session.createQuery("from Lskmzd where ele = '06' and " + balance + "  != 0");
            List<Lskmzd> lskmzdList = query.list();
            if(lskmzdList.size() > 0){
                close(session);
                return Msg.success().add("info", "fail");
            }
            else{
                close(session);
                return Msg.success().add("info", "pass");
            }
        }
        catch(Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "检查余额发生未知异常！");
        }
    }

    public void copyOldTable(String year){
        Session session = null;
        Transaction tx = null;
        try{
            session = getSession();
            tx = session.beginTransaction();

            Query query = session.createQuery("from Accrecord where flag = 1");
            Accrecord cur_acc = (Accrecord) query.list().get(0);
            String id = cur_acc.getId();

            Query create_lspzk1 = session.createSQLQuery("create table " + id + "lspzk1" + year + " select * from Lspzk1");
            Query create_lspzbh = session.createSQLQuery("create table " + id + "lspzbh" + year + " select * from Lspzbh");
            Query create_lstspz = session.createSQLQuery("create table " + id + "lsyspz" + year + " select * from Lsyspz");
            Query create_lshspz = session.createSQLQuery("create table " + id + "lshspz" + year + " select * from Lshspz");
            Query create_lskmzd = session.createSQLQuery("create table " + id + "lskmzd" + year + " select * from Lskmzd");
            Query create_lskmsl = session.createSQLQuery("create table " + id + "lskmsl" + year + " select * from Lskmsl");
            Query create_lswlje = session.createSQLQuery("create table " + id + "lswlje" + year + " select * from Lswlje");
            Query create_lswlsl = session.createSQLQuery("create table " + id + "lswlsl" + year + " select * from Lswlsl");
            Query create_lshsje = session.createSQLQuery("create table " + id + "lshsje" + year + " select * from Lshsje");
            Query create_lshssl = session.createSQLQuery("create table " + id + "lshssl" + year + " select * from Lshssl");

            create_lspzk1.executeUpdate();
            create_lspzbh.executeUpdate();
            create_lstspz.executeUpdate();
            create_lshspz.executeUpdate();
            create_lskmzd.executeUpdate();
            create_lskmsl.executeUpdate();
            create_lswlje.executeUpdate();
            create_lswlsl.executeUpdate();
            create_lshsje.executeUpdate();
            create_lshssl.executeUpdate();

            tx.commit();
            close(session);
        }
        catch(Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
        }
    }


    /**
    *@author czh
    *@Description 年结处理
    *@Date 2018/5/23 14:35
    *@Param []
    *@return com.bjut.ssh.entity.Msg
    **/
    public Msg yearlyStatement(String year){
        //当年数据库备份加年份
//        String userName ="root";
//        String pwd ="123456";
//        DatabaseBackup bak = new DatabaseBackup(userName, pwd);
//        bak.backupDatabase(getProperties.getProperties("jdbc.url"), "D:/DATA", year);

        Session session = null;
        Transaction tx = null;
        try{
            session = getSession();
            tx = session.beginTransaction();

            copyOldTable(year);

            //删除凭证库和凭证编号表
            Query dele_pzk = session.createQuery("delete from Lspzk1 where inputDate like '" + year + "%'");
            Query dele_pzbh = session.createQuery("delete from Lspzbh where iniDate like '" + year + "%'");
            dele_pzbh.executeUpdate();
            dele_pzk.executeUpdate();

            //删除初始化原始凭证表
            Query dele_yspz = session.createQuery("delete from Lsyspz where inputDate like '" + year + "%'");
            Query dele_hspz = session.createQuery("delete from Lshspz where inputDate like '" + year + "%'");
            dele_yspz.executeUpdate();
            dele_hspz.executeUpdate();

            String new_date = Integer.toString(Integer.parseInt(year) + 1) + "0101";
            Lsconf begin_date = (Lsconf) session.get(Lsconf.class, "begin_date");
            begin_date.setConfValue(new_date);
            session.update(begin_date);
            Lsconf current_date = (Lsconf) session.get(Lsconf.class, "current_date");
            current_date.setConfValue(new_date);
            session.update(current_date);

            //LSPZBH生成到次年12月，凭证编号默认0001，日期全是1日
            Lsconf first_num = (Lsconf) session.get(Lsconf.class,"first_num");
            String first_num_str = first_num.getConfValue();
            for(int month = 1; month <= 24; month ++) {
                int m = month % 12 == 0 ? 12 : month % 12;
                int add = month > 12 ? 1 : 0;
                int y = Integer.parseInt(year) + add + 1;
                String mm = m < 10 ? "0" + Integer.toString(m) : Integer.toString(m);
                String yyyy = Integer.toString(y);
                Lspzbh lspzbh = new Lspzbh();
                lspzbh.setPzbhAutoId(month);
                lspzbh.setVoucherName("记账凭证");
                lspzbh.setVoucherNo("0001");
                lspzbh.setIniDate(yyyy + mm + "01");
                lspzbh.setVoucherFirstChar(first_num_str);
                session.saveOrUpdate(lspzbh);
            }

            //初始化科目字典表
            Query set_bala = session.createQuery("update Lskmzd set supMoney=balance12,balance=balance12,balance01=balance12, balance02=balance12, balance03=balance12, balance04=balance12, balance05=balance12, balance06=balance12, balance07=balance12, balance08=balance12, balance09=balance12, balance10=balance12, debitMoney11=balance12");
            Query set_cd = session.createQuery("update Lskmzd set creditMoney=0, debitMoney=0, creditMoneySup=0, debitMoneySup=0, creditMoneyAcm=0, debitMoneyAcm=0, creditMoney01=0, debitMoney01=0, creditMoney02=0, debitMoney02=0, creditMoney03=0, debitMoney03=0, creditMoney04=0, debitMoney04=0, creditMoney05=0, debitMoney05=0, creditMoney06=0,debitMoney06=0, creditMoney07=0, debitMoney07=0, creditMoney08=0, debitMoney08=0, creditMoney09=0, debitMoney09=0, creditMoney10=0, debitMoney10=0, creditMoney11=0, creditMoney11=0, creditMoney12=0, debitMoney12=0");
            //create_lskmzd.executeUpdate();
            set_bala.executeUpdate();
            set_cd.executeUpdate();

            //初始化科目数量表
            Query set_balaqty = session.createQuery("update Lskmsl set supQty=leftQty12, leftQty=leftQty12, leftQty01=leftQty12, leftQty02=leftQty12, leftQty03=leftQty12, leftQty04=leftQty12, leftQty05=leftQty12, leftQty06=leftQty12, leftQty07=leftQty12, leftQty08=leftQty12, leftQty09=leftQty12, leftQty10=leftQty12, leftQty11=leftQty12");
            Query set_cdqty = session.createQuery("update Lskmsl set creditQty=0, debitQty=0, creditQtySup=0, debitQtySup=0, creditQtyAcm=0, debitQtyAcm=0, creditQty01=0, debitQty01=0, creditQty02=0,debitQty02=0, creditQty03=0, debitQty03=0, creditQty04=0, debitQty04=0, creditQty05=0, debitQty05=0, creditQty06=0, debitQty06=0, creditQty07=0, debitQty07=0, creditQty08=0, debitQty08=0, creditQty09=0, debitQty09=0, creditQty10=0, debitQty10=0, creditQty11=0, creditQty11=0, creditQty12=0, debitQty12=0");
            set_balaqty.executeUpdate();
            set_cdqty.executeUpdate();

            //初始化专项核算表
            Query set_spbala = session.createQuery("update Lshsje set supMoney=balance12,balance=balance12,balance01=balance12, balance02=balance12, balance03=balance12, balance04=balance12, balance05=balance12, balance06=balance12, balance07=balance12, balance08=balance12, balance09=balance12, balance10=balance12, debitMoney11=balance12");
            Query set_spcd = session.createQuery("update Lshsje set creditMoney=0, debitMoney=0, creditMoneySup=0, debitMoneySup=0, creditMoneyAcm=0, debitMoneyAcm=0, creditMoney01=0, debitMoney01=0, creditMoney02=0, debitMoney02=0, creditMoney03=0, debitMoney03=0, creditMoney04=0, debitMoney04=0, creditMoney05=0, debitMoney05=0, creditMoney06=0,debitMoney06=0, creditMoney07=0, debitMoney07=0, creditMoney08=0, debitMoney08=0, creditMoney09=0, debitMoney09=0, creditMoney10=0, debitMoney10=0, creditMoney11=0, creditMoney11=0, creditMoney12=0, debitMoney12=0");
            set_spbala.executeUpdate();
            set_spcd.executeUpdate();

            //初始化专项核算数量表
            Query set_spbalaqty = session.createQuery("update Lshssl set supQty=leftQty12, leftQty=leftQty12, leftQty01=leftQty12, leftQty02=leftQty12, leftQty03=leftQty12, leftQty04=leftQty12, leftQty05=leftQty12, leftQty06=leftQty12, leftQty07=leftQty12, leftQty08=leftQty12, leftQty09=leftQty12, leftQty10=leftQty12, leftQty11=leftQty12");
            Query set_spcdqty = session.createQuery("update Lshssl set creditQty=0, debitQty=0, creditQtySup=0, debitQtySup=0, creditQtyAcm=0, debitQtyAcm=0, creditQty01=0, debitQty01=0, creditQty02=0,debitQty02=0, creditQty03=0, debitQty03=0, creditQty04=0, debitQty04=0, creditQty05=0, debitQty05=0, creditQty06=0, debitQty06=0, creditQty07=0, debitQty07=0, creditQty08=0, debitQty08=0, creditQty09=0, debitQty09=0, creditQty10=0, debitQty10=0, creditQty11=0, creditQty11=0, creditQty12=0, debitQty12=0");
            set_spbalaqty.executeUpdate();
            set_spcdqty.executeUpdate();

            //初始化往来单位金额表
            Query set_exbala = session.createQuery("update Lswlje set supMoney=balance12,balance=balance12,balance01=balance12, balance02=balance12, balance03=balance12, balance04=balance12, balance05=balance12, balance06=balance12, balance07=balance12, balance08=balance12, balance09=balance12, balance10=balance12, debitMoney11=balance12");
            Query set_excd = session.createQuery("update Lswlje set creditMoney=0, debitMoney=0, creditMoneySup=0, debitMoneySup=0, creditMoneyAcm=0, debitMoneyAcm=0, creditMoney01=0, debitMoney01=0, creditMoney02=0, debitMoney02=0, creditMoney03=0, debitMoney03=0, creditMoney04=0, debitMoney04=0, creditMoney05=0, debitMoney05=0, creditMoney06=0,debitMoney06=0, creditMoney07=0, debitMoney07=0, creditMoney08=0, debitMoney08=0, creditMoney09=0, debitMoney09=0, creditMoney10=0, debitMoney10=0, creditMoney11=0, creditMoney11=0, creditMoney12=0, debitMoney12=0");
            set_exbala.executeUpdate();
            set_excd.executeUpdate();

            //初始化往来单位数量表
            Query set_exbalaqty = session.createQuery("update Lswlsl set supQty=leftQty12, leftQty=leftQty12, leftQty01=leftQty12, leftQty02=leftQty12, leftQty03=leftQty12, leftQty04=leftQty12, leftQty05=leftQty12, leftQty06=leftQty12, leftQty07=leftQty12, leftQty08=leftQty12, leftQty09=leftQty12, leftQty10=leftQty12, leftQty11=leftQty12");
            Query set_excdqty = session.createQuery("update Lswlsl set creditQty=0, debitQty=0, creditQtySup=0, debitQtySup=0, creditQtyAcm=0, debitQtyAcm=0, creditQty01=0, debitQty01=0, creditQty02=0,debitQty02=0, creditQty03=0, debitQty03=0, creditQty04=0, debitQty04=0, creditQty05=0, debitQty05=0, creditQty06=0, debitQty06=0, creditQty07=0, debitQty07=0, creditQty08=0, debitQty08=0, creditQty09=0, debitQty09=0, creditQty10=0, debitQty10=0, creditQty11=0, creditQty11=0, creditQty12=0, debitQty12=0");
            set_exbalaqty.executeUpdate();
            set_excdqty.executeUpdate();

            tx.commit();
            close(session);
            return Msg.success();
        }
        catch(Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "年结发生未知异常！");
        }
    }

    /**
    *@author czh
    *@Description 初始化下一个月的财务日期
    *@Date 2018/5/24 8:58 
    *@Param [year_month]
    *@return void
    **/
    public Msg initNewMonth(String year_month){
        //当前财务日期变为下月1日
        String date = Integer.toString(Integer.parseInt(year_month) + 1) + "01";
        try{
            setCurrentDate(date);
            return Msg.success();
        }
        catch(Exception e){
            return Msg.fail().add("errorInfo", "初始化财务日期发生未知异常！");
        }
    }

    /**
    *@author czh
    *@Description 设置当前财务日期
    *@Date 2018/5/24 9:02
    *@Param [date]
    *@return com.bjut.ssh.entity.Msg
    **/
    public Msg setCurrentDate(String date){
        Session session = null;
        Transaction tx = null;
        try{
            session = getSession();
            tx = session.beginTransaction();
            Lsconf lsconf = (Lsconf) session.get(Lsconf.class,"current_date");
            lsconf.setConfValue(date);
            session.update(lsconf);
            tx.commit();
            close(session);
            return Msg.success().add("date", date);
        }
        catch(Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "未知异常！");
        }
    }
}
