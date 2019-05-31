package com.bjut.ssh.dao.FinanceProcess;

import com.bjut.ssh.entity.Lsconf;
import com.bjut.ssh.entity.Lspzbh;
import com.bjut.ssh.entity.Msg;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Title: OtherEnvironmentDao
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/3/29 10:21
 * @Version: 1.0
 */
@Repository
public class OtherEnvironmentDao {
    @Autowired
    private SessionFactory sessionFactory;

    /**
    *@author czh
    *@Description 获取会话
    *@Date 2018/4/3 11:24
    *@Param []
    *@return org.hibernate.Session
    **/
    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    /**
    *@author czh
    *@Description 关闭会话
    *@Date 2018/4/3 11:24
    *@Param [session]
    *@return void
    **/
    public void close(Session session){
        if(session != null)
            session.close();
    }

    /**
    *@author czh
    *@Description 设置其他环境
    *@Date 2018/4/3 11:25
    *@Param [configs]
    *@return void
    **/
    public Msg setOtherEnvironment(List<Lsconf> configs){
        Session session = getSession();
        session.beginTransaction();
        try {
            Boolean defined_flag = false;
            Boolean using_flag = false;

            //原需求为：定义过一次则不允许修改
            Lsconf lsconf = (Lsconf) session.get(Lsconf.class, "stru_def");
            if(lsconf != null) {
                defined_flag = lsconf.getConfValue().equals("true");
            }

            //现需求为：科目字典有条目则不允许修改
            Query q = session.createQuery("from Lskmzd");
            if(q.list().size() > 0){
                using_flag = true;
            }

            boolean ini_flag = false;
            Lsconf begin_date = new Lsconf();
            String first_num = null;
            for (int i = 0; i < configs.size(); i++) {
                Lsconf config = configs.get(i);
                if(using_flag && (config.getConfKey().equals("stru_def") ||
                        config.getConfKey().equals("sub_stru") ||
                        config.getConfKey().equals("first_num") ||
                        config.getConfKey().equals("sub_level") ||
                        config.getConfKey().equals("quantity_dec") ||
                        config.getConfKey().equals("price_dec") ||
                        config.getConfKey().equals("begin_date"))){
                    continue;
                }
                else session.saveOrUpdate(config);

                if(config.getConfKey().equals("begin_date")){
                    Lsconf current_date = new Lsconf();
                    current_date.setConfKey("current_date");
                    current_date.setConfValue(begin_date.getConfValue());
                    current_date.setConfNote("当前财务日期");
                    session.saveOrUpdate(current_date);
                    begin_date = config;
                    ini_flag = true;
                }
                if(config.getConfKey().equals("first_num")){
                    first_num = config.getConfValue();
                }
            }
            if(ini_flag) initVoucherNum(begin_date.getConfValue(), first_num);
            session.getTransaction().commit();
            close(session);
            return Msg.success().add("defined_flag", defined_flag).add("using_flag", using_flag);
        }
        catch (Exception e){
            e.printStackTrace();
            close(session);
            return Msg.fail();
        }
    }

    public void initVoucherNum(String date, String first_num){
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try{
            Lsconf config = (Lsconf) session.get(Lsconf.class, "first_num");
            String first_char = config == null ? first_num : config.getConfValue();
            int begin_year = Integer.parseInt(date.substring(0, 4));
            int begin_month = Integer.parseInt(date.substring(4, 6));
            int begin_day = Integer.parseInt(date.substring(6, 8));
            int total_month = 24 - begin_month + 1;
            for(int i = 0; i < total_month; i++) {
                String this_month = Integer.toString(begin_month + i > 12 ? begin_month + i - 12 : begin_month + i);
                String this_year = Integer.toString(begin_month + i > 12 ? begin_year + 1 : begin_year);
                String this_day = i == 0 ? date.substring(6, 8) : "01";
                this_month = this_month.length() == 1 ? "0" + this_month : this_month;
                this_year = this_year.length() == 1 ? "0" + this_year : this_year;
                String this_date = this_year + this_month + this_day;

                Query q = session.createQuery("from Lspzbh where iniDate like '" + this_year + this_month + "%'");
                if(q.list().size() > 0) continue;

                Query last_query = session.createQuery("from Lspzbh where pzbhAutoId = (select max(pzbhAutoId) from Lspzbh)");
                Lspzbh last = (Lspzbh)last_query.uniqueResult();
                int auto_id = last == null ? 0 : last.getPzbhAutoId() + 1;

                Lspzbh lspzbh = new Lspzbh();
                lspzbh.setPzbhAutoId(auto_id);
                lspzbh.setVoucherName("记账凭证");
                lspzbh.setVoucherFirstChar(first_char);
                lspzbh.setIniDate(this_date);
                lspzbh.setVoucherNo("0001");

                session.save(lspzbh);
            }
            tx.commit();
            close(session);
        }catch (Exception e){
            e.printStackTrace();
            close(session);
        }
    }

    public void setOtherEnvironment(Lsconf config){
        Session session = getSession();
        session.beginTransaction();
        try {
            session.save(config);
            session.getTransaction().commit();
            close(session);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Msg getConfigs(List<String> config_list){
        Session session = null;
        Transaction tx = null;
        Msg result = Msg.success();
        try {
            session = getSession();
            tx = session.beginTransaction();

            for(String current : config_list) {
                Lsconf stru = (Lsconf) session.get(Lsconf.class, current);
                String value = null;
                if(stru != null) {
                   value = stru.getConfValue();
                }
                result.add(current, value);
            }

            tx.commit();
            close(session);
            return result;
        }
        catch(Exception e){
            tx.rollback();
            e.printStackTrace();
            return Msg.fail().add("errorInfo","未定义或无法获取数据！");
        }
    }

}
