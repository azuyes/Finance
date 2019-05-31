package com.bjut.ssh.service.AssistedManagement;

import com.bjut.ssh.entity.LshsjeOrLshsslForInit;
import com.bjut.ssh.entity.LshsjeOrLshsslQueryVo;
import com.bjut.ssh.entity.Lshszd;
import com.bjut.ssh.entity.LskmzdQueryVo;

import java.util.List;

/**
 * @Title: SpAccountInitializationService
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/5/28 9:45
 * @Version: 1.0
 */
public interface SpAccountInitializationService {

    public List<LskmzdQueryVo> getCaptionOfSpAccount(String id, String levelFlag);

    LshsjeOrLshsslForInit getItemByIdAndSpecial1 (String itemNo, String spLevel);

    List<LshsjeOrLshsslQueryVo> queryContactsCategoryByLevel(String id, String levelFlag, String itemNo);

    boolean updateSpCount1(List<LshsjeOrLshsslQueryVo> lshsjeOrLshsslQueryVos,String itemNo);

    LshsjeOrLshsslForInit getItemByIdAndSpecial2 (String itemNo);

    boolean updateSpCount2(List<LshsjeOrLshsslQueryVo> lshsjeOrLshsslQueryVos,String itemNo);


}
