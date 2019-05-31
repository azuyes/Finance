package com.bjut.ssh.service.AssistedManagement;

import com.bjut.ssh.entity.Lskmzd;
import com.bjut.ssh.entity.LswljeOrLswlslQueryVo;
import com.bjut.ssh.entity.LswljeQueryVo;

import java.util.List;

/**
 * @Title: ContactsInitializationService
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/4/18 16:29
 * @Version: 1.0
 */
public interface ContactsInitializationService {

    //根据前台输入或者选择的科目编号进行判断是否是明细和往来科目，若不是返回false，否则返回查询lskmzd,lskmsl,lswlsl,lswlje等数据。
    LswljeOrLswlslQueryVo getItemByIdAndCompany (String itemNo);

    //更新lswlje表中的数据
    boolean updateContactsInitial(List<LswljeQueryVo> lswljeQueryVos);

    //更新lswlje,lswlsl中的数据
    boolean updateContactsInitialSl(List<LswljeQueryVo> lswljeQueryVos);

    List<Lskmzd> queryCaptionOfAccountByLevel(String id, String levelFlag);
}
