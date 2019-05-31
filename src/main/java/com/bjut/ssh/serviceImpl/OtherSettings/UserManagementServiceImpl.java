package com.bjut.ssh.serviceImpl.OtherSettings;

import com.bjut.ssh.dao.OtherSettings.UserManagementDao;
import com.bjut.ssh.entity.*;
import com.bjut.ssh.service.OtherSettings.UserManagementService;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Title: UserManagementServiceImpl
 * @Description: TODO
 * @Author: LYH
 * @CreateDate: 2018/3/29 15:11
 * @Version: 1.0
 */
@Service
@Transactional
public class UserManagementServiceImpl implements UserManagementService{
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    public void close(Session session){
        if(session != null)
            session.close();
    }

    @Autowired
    private UserManagementDao userManagementDao;

    //查询全部用户信息
    @Override
    @Transactional
    public List<Lspass> findUser()throws Exception{

        List<Lspass> lspassList = userManagementDao.findUser();
        return lspassList;
    }
    //根据id查找用户并修改
    @Override
    public void updateInfo(Lspass lspass) {
        userManagementDao.updateInfo(lspass);
    }

    @Override
    public Boolean addInfo(Lspass lspass) {

        return userManagementDao.addInfo(lspass);
    }

    @Override
    public Boolean delInfo(String userNo) {
        return userManagementDao.delInfo(userNo);
    }

    @Override
    public Boolean defaultPassword(String userNo) {
        return userManagementDao.defaultPassword(userNo);
    }
    /**
    *@author LYH
    *@Description 用户权限设置
    *@Date 2018/4/12 11:33
    *@Param [userNo, FunctionAuthority]
    *@return void
    **/

    @Override
    public void saveAuthority(String userNo, String FunctionAuthority) {
        Lsusgn lsusgn = new Lsusgn();
        String[] functionAuthority = FunctionAuthority.split(",");
        Session session = getSession();
        Transaction ts = session.getTransaction();
        List<Lsusgn> lsusgnList = null;
        Query query = session.createQuery("from Lsusgn where userNo='"+userNo+"'");
        lsusgnList=query.list();
        try {
            ts.begin();
            //Lsusgn lsusgn1 = (Lsusgn)session.get(Lsusgn.class,userNo);
            if (lsusgnList.size()==0){
                for(int i=0;i<functionAuthority.length;i++){

                    lsusgn.setUserNo(userNo);
                    lsusgn.setMenuNo(functionAuthority[i]);
                    userManagementDao.saveAuthority(lsusgn);
                }
            }else {
                //先删除已有权限信息
                for(Lsusgn lsusgn1 : lsusgnList){
                    String id = lsusgn1.getUserNo();
                    userManagementDao.delAuthority(id);
                }
                //再加入新的权限信息
                for(int i=0;i<functionAuthority.length;i++) {

                    lsusgn.setUserNo(userNo);
                    lsusgn.setMenuNo(functionAuthority[i]);
                    userManagementDao.saveAuthority(lsusgn);
                }
             }
            ts.commit();
            close(session);
        }catch (Exception e){
            e.printStackTrace();
            ts.rollback();
        }
    }


    @Override
    public String getAuthority(String userNo) {

        return userManagementDao.getAuthority(userNo);
    }


    /**
    *@author LYH
    *@Description 身份验证
    *@Date 2018/4/17 17:38
    *@Param [userNo, userPass]
    *@return java.lang.Boolean
    **/

    @Override
    public String findByUsernameAndPassword(String userNo, String userPass) {

        String flag = " ";
        Lspass lspass = this.userManagementDao.findByUsernameAndPassword(userNo,userPass);
        if(lspass != null){
            //if(userNo.equals((lspass.getUserNo())) && userPass.equals(lspass.getUserPass())){

                flag = lspass.getUserName();
           // }
        }

        return flag;

    }

    @Override
    public List<Lsgnbh> findFunctionAuthority(String menuNo) {
        return userManagementDao.findFunctionAuthority(menuNo);
    }


    /**
    *@author LYH
    *@Description 用户修改个人信息（密码等）
    *@Date 2018/4/27 10:20
    *@Param [lspass]
    *@return void
    **/
    @Override
    public void updateMsg(Lspass lspass) {
        userManagementDao.updateMsg(lspass);
    }


    @Override
    public List<Accrecord> findAcc(){
        return userManagementDao.findAcc();
    }

    @Override
    public void switchAcc(String id){
        userManagementDao.switchAcc(id);
    }

    @Override
    public Msg addAcc(Accrecord accrecord){
        return userManagementDao.addAcc(accrecord);
    }

    @Override
    public Msg updateAcc(Accrecord accrecord){
        return userManagementDao.updateAcc(accrecord);
    }

    @Override
    public Lspass getUserInfo(String userNo) {
        return userManagementDao.getUserInfo(userNo);
    }
}