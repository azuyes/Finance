package com.bjut.ssh.service.OtherSettings;

import com.bjut.ssh.entity.Accrecord;
import com.bjut.ssh.entity.Lsgnbh;
import com.bjut.ssh.entity.Lspass;
import com.bjut.ssh.entity.Msg;

import java.util.List;


/**
 * @Title: UserManagementService
 * @Description: TODO
 * @Author: LYH
 * @CreateDate: 2018/3/29 11:22
 * @Version: 1.0
 */

public interface UserManagementService {
    //显示全部用户信息
    public List<Lspass> findUser() throws Exception;

    //根据id查询用户信息并修改
    public void updateInfo(Lspass lspass);

    //添加新用户
    public Boolean addInfo(Lspass lspass);

    //删除用户信息
    public Boolean delInfo(String userNo);

    //重置用户的密码
    public Boolean defaultPassword(String userNo);

    //保存权限设置的信息
    public void saveAuthority(String userNo ,String FunctionAuthority);

    //获取已有的权限信息
    public String getAuthority(String userNo);

    public String findByUsernameAndPassword(String userNo,String userPass);

    public List<Lsgnbh> findFunctionAuthority(String menuNo);

    public void updateMsg(Lspass lspass);

    public List<Accrecord> findAcc();

    public void switchAcc(String id);

    public Msg addAcc(Accrecord accrecord);

    public Msg updateAcc(Accrecord accrecord);

    public abstract Lspass getUserInfo(String userNo);
}

