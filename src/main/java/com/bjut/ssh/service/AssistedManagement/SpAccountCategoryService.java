package com.bjut.ssh.service.AssistedManagement;

import com.bjut.ssh.entity.Lshsfl;
import com.bjut.ssh.entity.Msg;

import java.util.List;

/**
 * @Title: SpAccountCategoryService
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/5/2 13:17
 * @Version: 1.0
 */
public interface SpAccountCategoryService {

    //获取lshsfl表中的数据
    List<Lshsfl> getSpAccountCategory();

    //保存核算分类
    Msg saveSpAccountCategory(Lshsfl lshsfl);

    //根据id删除核算分类
    Msg delSpAccountCategoryById(String id);
}
