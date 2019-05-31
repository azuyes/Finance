package com.bjut.ssh.dao.OtherSettings;

import com.bjut.ssh.entity.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

/**
 * @Title: UserManagementDao
 * @Description: 用户管理
 * @Author: LYH
 * @CreateDate: 2018/3/29 11:17
 * @Version: 1.0
 */
@Repository
@Transactional

public class UserManagementDao {
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    public void close(Session session){
        if(session != null)
            session.close();
    }
    /**
    *@author LYH
    *@Description 显示用户信息
    *@Date 2018/3/29 11:48
    *@Param []
    *@return java.util.List<com.bjut.ssh.entity.Lspass>
    **/
    public List<Lspass> findUser()throws Exception{
       Session session = getSession();
        Query query = session.createQuery("from  Lspass  where userNo != ?");
        query.setParameter(0,"0000");
        List<Lspass> lspassList = query.list();
        close(session);
        return lspassList;
    }
    /**
    *@author LYH
    *@Description 修改用户信息
    *@Date 2018/4/8 16:23
    *@Param [lspass]
    *@return void
    **/

    public void updateInfo (Lspass lspass){
        Session session = getSession();
        Transaction ts = session.getTransaction();
        try {
            ts.begin();
            Lspass lspass1 = (Lspass) session.get(Lspass.class,lspass.getUserNo());
            lspass1.setDepartment(lspass.getDepartment());
            lspass1.setUserName(lspass.getUserName());
            lspass1.setUserNote(lspass.getUserNote());
            session.update(lspass1);
            ts.commit();
        }catch(Exception e){
            e.printStackTrace();
            ts.rollback();
        }finally {
            close(session);
        }
    }

    /**
    *@author LYH
    *@Description 新建用户信息
    *@Date 2018/4/8 17:15
    *@Param [lspass]
    *@return java.lang.Boolean
    **/

    public Boolean addInfo (Lspass lspass){

        Session session = null;
        Transaction ts = null;
        try {
            session = getSession();
            ts = session.beginTransaction();
            Lspass obj = (Lspass)session.get(Lspass.class,lspass.getUserNo());
            if (obj!=null){
                return false;
            }else {
                session.save(lspass);
            }
            ts.commit();
            close(session);
            return true;
        }
        catch (Exception e){
            ts.rollback();
            e.printStackTrace();
            return false;
        }
    }
    /**
    *@author LYH
    *@Description 删除用户信息
    *@Date 2018/4/11 16:23
    *@Param [userNo]
    *@return java.lang.Boolean
    **/

    public Boolean delInfo (String userNo){
        Session session = null;
        Transaction ts = null;
        try {
            session = getSession();
            ts = session.beginTransaction();

            Query query=session.createQuery("from Lsusgn gn where gn.userNo = '"+userNo+"' ");
            List<Lsusgn> list=query.list();     //先把数据查出来
            for (Lsusgn lsusgn:list) {
                session.delete(lsusgn);
            }

            Lspass obj = (Lspass)session.get(Lspass.class,userNo);
            session.delete(obj);
            ts.commit();
            close(session);
            return true;
        }catch (Exception e){
            ts.rollback();
            e.printStackTrace();
            return false;
        }
    }


    public Boolean defaultPassword (String userNo){
        Session session = null;
        Transaction ts = null;
        try {
            session = getSession();
            ts = session.beginTransaction();
            Lspass lspass = (Lspass)session.get(Lspass.class,userNo);
            lspass.setUserPass("123456");
            session.save(lspass);
            ts.commit();
            close(session);
            return true;
        }catch (Exception e){
            ts.rollback();
            e.printStackTrace();
            return false;
        }
    }

    /**
    *@author LYH
    *@Description 保存权限信息
    *@Date 2018/4/11 16:24
    *@Param
    *@return
    **/
    public void saveAuthority(Lsusgn lsusgn){

        Session session = null;
        Transaction ts = null;
        try{
            session = getSession();
            ts = session.beginTransaction();
            session.save(lsusgn);
            ts.commit();
            close(session);
        }catch (Exception e){
            ts.rollback();
            e.printStackTrace();
        }
    }

    /**
    *@author LYH
    *@Description 删除已有权限信息
    *@Date 2018/4/12 11:04
    *@Param [lsusgn]
    *@return void
    **/

    public void delAuthority(String id){

        Session session = null;
        Transaction ts = null;
        try{

            session = getSession();
            ts = session.beginTransaction();
            Query query = session.createQuery("from Lsusgn where userNo='"+id+"'");
            List<Lsusgn> lsusgnList = query.list();
            Lsusgn lsusgn = lsusgnList.get(0);
            session.delete(lsusgn);
            ts.commit();
            close(session);

        }catch (Exception e){
            ts.rollback();
            e.printStackTrace();
        }
    }

    /**
    *@author LYH
    *@Description 获取当前用户已有权限信息
    *@Date 2018/4/13 9:52
    *@Param [userNo]
    *@return java.lang.String
    **/
    
    public String getAuthority(String userNo){
        Session session = null;
        Transaction ts = null;
        String FunctionAuthority = "";
        try {
            session=getSession();
            ts = session.beginTransaction();
            Query query = session.createQuery("from Lsusgn where userNo='"+userNo+"' order by menuNo desc");
            List<Lsusgn> lsusgnList = query.list();
            for (int i=0;i<lsusgnList.size();i++){
                String menuNo = lsusgnList.get(i).getMenuNo();
                if (FunctionAuthority != "")
                    FunctionAuthority += ",";
                FunctionAuthority += menuNo;
            }
            ts.commit();
            close(session);
        }catch (Exception e){
            e.printStackTrace();
            ts.rollback();
        }finally {
            return FunctionAuthority;
        }

    }

    /**
    *@author LYH
    *@Description 登录时进行身份验证
    *@Date 2018/4/17 16:42
    *@Param [userNo, userPass]
    *@return com.bjut.ssh.entity.Lspass
    **/

    public Lspass findByUsernameAndPassword(String userNo,String userPass){
        String hsql="from Lspass u where u.userNo= :userNo and u.userPass= :userPass";
        Session session = getSession();
        Query query = session.createQuery(hsql);
        query.setParameter("userNo", userNo).setParameter("userPass", userPass);
        Lspass lspass = (Lspass) query.uniqueResult();
        //System.out.println(lspass);
        close(session);
        return lspass;
    }

/**
*@author LYH
*@Description 读取数据库中权限信息
*@Date 2018/4/23 11:21
*@Param
*@return
**/
public List<Lsgnbh> findFunctionAuthority(String menuNo){
    Session session = null;
    Transaction ts = null;
    List<Lsgnbh> lsgnbhlist = null;
    if(menuNo == null)
        menuNo = "0";
    try {
        session = getSession();
        ts = session.beginTransaction();
        Query query = session.createQuery("from Lsgnbh where tid = ?");//?是通配符，通过query.setParameter来赋值。
        query.setParameter(0,menuNo);//0表示第一个占位符，同理1表示第二个。。。
        lsgnbhlist = query.list();
        ts.commit();
    }catch (Exception e){

        e.printStackTrace();
        ts.rollback();
        return null;
    }finally {
        close(session);
        return lsgnbhlist;
    }

}
/**
*@author LYH
*@Description 用户修改个人信息（密码等）
*@Date 2018/4/27 10:16
*@Param [lspass]
*@return void
**/

    public void updateMsg (Lspass lspass){
        Session session = getSession();
        Transaction ts = session.getTransaction();
        try {
            ts.begin();
            Lspass lspass1 = (Lspass) session.get(Lspass.class,lspass.getUserNo());
            lspass1.setUserName(lspass.getUserName());
            lspass1.setUserPass(lspass.getUserPass());
            session.update(lspass1);
            ts.commit();
        }catch(Exception e){
            e.printStackTrace();
            ts.rollback();
        }finally {
            close(session);
        }
    }

    public List<Accrecord> findAcc(){
        List<Accrecord> accrecords = null;
        try {
            Session session = getSession();
            Query query = session.createQuery("from Accrecord ");
            accrecords = query.list();
            close(session);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return accrecords;
        }
    }

    public void switchAcc(String id){
        Session session = getSession();
        Transaction ts = session.getTransaction();
        try{
            ts.begin();
            Accrecord acc = (Accrecord) session.get(Accrecord.class, id);
            acc.setFlag(1);
            session.update(acc);
            Query query = session.createQuery("from Accrecord where flag = 1");
            Accrecord cur_acc = (Accrecord) query.list().get(0);
            cur_acc.setFlag(0);
            session.update(cur_acc);

            System.out.println("连接数据库成功");

            String[] tables = new String[]{"lsconf", "lsgnbh", "lshsfl", "lshsje", "lshszd",
                                            "lskmsl", "lskmzd", "lspass", "lspzbh", "lspzk1",
                                            "lsszzd", "lsusgn", "lswldw", "lswlfl", "lswlje",
                                            "lswlsl", "lswlys", "lsyspz", "lszcdy", "lszczd"};
            for(String table : tables){
                //当前账套设为原始名称
                String sql1="rename table " + table + " to " + cur_acc.getId() + "_" + table;//生成一条mysql语句
                Query rename1 = session.createSQLQuery(sql1);
                rename1.executeUpdate();

                //目标账套设为当前账套
                String sql2="rename table " + id + "_" + table + " to " + table;//生成一条mysql语句
                Query rename2 = session.createSQLQuery(sql2);
                rename2.executeUpdate();
            }

            ts.commit();
            System.out.println("修改数据库成功");
        }catch(Exception e){
            e.printStackTrace();
            ts.rollback();
        }finally {
            close(session);
            System.out.println("关闭数据库成功");
        }
    }

    public Msg addAcc(Accrecord accrecord){
        Session session = getSession();
        Transaction ts = session.getTransaction();
        try{
            ts.begin();
            Accrecord obj = (Accrecord)session.get(Accrecord.class,accrecord.getId());
            if (obj != null){
                return Msg.fail().add("errorInfo", "账套编号重复！");
            }else {
                session.save(accrecord);
            }

            String[] tables = new String[]{"lsconf", "lsgnbh", "lshsfl", "lshsje", "lshszd",
                    "lskmsl", "lskmzd", "lspass", "lspzbh", "lspzk1",
                    "lsszzd", "lsusgn", "lswldw", "lswlfl", "lswlje",
                    "lswlsl", "lswlys", "lsyspz", "lszcdy", "lszczd"};
            for(String table : tables){
                String sql="create table " + accrecord.getId() + "_" + table + " select * from " + table + " where 0 = 1";//生成一条mysql语句
                Query rename = session.createSQLQuery(sql);
                rename.executeUpdate();
            }

            ts.commit();
            return Msg.success();
        }catch(Exception e){
            e.printStackTrace();
            close(session);
            ts.rollback();
            return Msg.fail().add("errorInfo", "无法创建新账套！");
        }
    }

    public Msg updateAcc(Accrecord accrecord) {
        Session session = getSession();
        Transaction ts = session.getTransaction();
        try {
            ts.begin();
            session.update(accrecord);
            ts.commit();
            return Msg.success();
        } catch (Exception e) {
            e.printStackTrace();
            close(session);
            ts.rollback();
            return Msg.fail().add("errorInfo", "无法修改账套名称！");
        }
    }

    public Lspass getUserInfo(String  userNo) {
        Session session = getSession();
        Transaction ts = session.getTransaction();
        Lspass lspass = new Lspass();
        try {
            ts.begin();
            lspass = (Lspass) session.get(Lspass.class,userNo);
            Lsconf lsconf = (Lsconf)session.get(Lsconf.class,"unit_name");
            lspass.setDepartment(lsconf.getConfValue());
            ts.commit();
        } catch (Exception e) {
            e.printStackTrace();
            close(session);
            ts.rollback();
        }finally {
            return lspass;
        }
    }

}