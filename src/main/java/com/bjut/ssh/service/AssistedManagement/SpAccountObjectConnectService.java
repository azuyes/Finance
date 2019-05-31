package com.bjut.ssh.service.AssistedManagement;

import com.bjut.ssh.entity.Lshsfl;
import com.bjut.ssh.entity.Lshszd;
import com.bjut.ssh.entity.Lskmzd;

import java.util.List;

/**
 * @Title: SpAccountObjectConnectService
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/5/14 10:56
 * @Version: 1.0
 */
public interface SpAccountObjectConnectService {
    List<Lshszd> getSpAccountName (String spNo,String catNo);

    Lskmzd getSpItemById(String itemNo);

    List<Lshsfl> getSpAccountCategory();

    boolean spAccountConnect1(String[] spNos1 , String itemNo);

    boolean spAccountConnect2(String[] spNos1 ,String[] spNos2 , String itemNo);

    //获取已有的权限信息
    String getAuthority1(String itemNo);

    //获取已有的权限信息
    String getAuthority2(String itemNo);

    boolean judgeUnChecked(String spNo,String itemNo);

    String getCatNameBySpItemNo(String catNo);
}
