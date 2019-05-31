package com.bjut.ssh.service.AssistedManagement;

import com.bjut.ssh.entity.Lswlfl;
import com.bjut.ssh.entity.Msg;

import java.util.List;

/**
 * @Title: ContactsCategoryService
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/3/28 16:47
 * @Version: 1.0
 */
public interface ContactsCategoryService {

    //根据id获取往来分类的信息
    List<Lswlfl> getContactsCategory(String id);

    //获取所有的往来分类信息，用户转出到excel
    List<Lswlfl> getContactsCategoryAll(String id);

    //保存新增的往来分类信息
    Msg saveContactsCategory(Lswlfl lswlfl);

    //根据编号，级数，获取对应的往来分类信息
    Msg delContactsCategoryById(String id,String catLevel);

//   Msg delAll(List<Integer> del_ids);

    //根据id，级数，查询对应的往来分类信息
    List<Lswlfl> queryContactsCategoryByLevel(String id,String levelFLag);
}
