package com.bjut.ssh.service.AssistedManagement;

import com.bjut.ssh.entity.Lshszd;
import com.bjut.ssh.entity.LshszdQueryVo;
import com.bjut.ssh.entity.Lskmzd;
import com.bjut.ssh.entity.Msg;

import java.util.List;

/**
 * @Title: SpAccountObjectDefService
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/5/7 12:55
 * @Version: 1.0
 */
public interface SpAccountObjectDefService {

    LshszdQueryVo getSpAccountCategoryById (String catNo);

    List<Lshszd> queryContactsCategoryByLevel(String id, String levelFlag,String catNo);

    Msg saveSpAccountObjectDef(Lshszd lshszd);

    Msg delSpAccountObjectDef(String id, String spLevel,String catNo);

    List<Lskmzd> queryCaptionOfAccountByLevel(String id, String levelFlag);

    List<Lskmzd> queryCaptionOfAccountByLevel1(String id, String levelFlag);
}
