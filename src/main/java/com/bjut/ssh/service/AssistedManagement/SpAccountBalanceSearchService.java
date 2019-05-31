package com.bjut.ssh.service.AssistedManagement;

import com.bjut.ssh.entity.*;

import java.util.List;
import java.util.Map;

/**
 * @Title: SpAccountBalanceSearchService
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/9/5 9:22
 * @Version: 1.0
 */
public interface SpAccountBalanceSearchService {

    public abstract List<SpAccountBalanceQueryVo> querySpAccountBalance1(String year,String month,String itemNo,String spCatNo,String spNo,String spLevel,String searchAccType,String searchOption);

    public abstract List<SpAccountBalanceQueryVo> querySpInfoByLevel(String year,String month,String itemNo,String spCatNo,String spNo,String spLevel,String searchAccType,String searchOption);

    public abstract List<SpAccountBalanceQueryVo> querySpAccountBalance2(String year,String month,String itemNo,String item,String spCatNo,String spNo,String searchAccType,String searchOption);

    public abstract Map<String,Object> getAllColumn3(String spCatNo,String itemNo);

    public abstract Map<String,Object> getAllInfo3(String year,String month,String itemNo,String spCatNo,String spNo,String spLevel,String searchAccType,String searchOption,String queryDataType);


    public abstract Map<String,Object> getAllColumn4(String spCatNo);

    public abstract Map<String,Object> getAllInfo4(String year,String month,String itemNo,String spCatNo,String spNo,String spLevel,String searchAccType,String searchOption,String queryDataType);

    public abstract List<Lskmzd> querySpAccountItem(String catNo);

    public abstract List<Lshsje> queryLshsje(String spNo, String itemNo);

    public abstract List<Object> getFormulaResult(String sql);


    public abstract List<SpAccountBalanceQueryVo2> querySpAccountBalance5(String year, String month, String itemNo, String catNo1,String spNo1,String catNo2,String spNo2, String searchAccType, String searchOption);

    public abstract List<SpAccountBalanceQueryVo2> querySpAccountBalance6(String year,String month,String itemNo,String item,String catNo1,String spNo1,String catNo2,String spNo2,String searchAccType,String searchOption);

    public abstract Map<String,Object> getAllColumn7(String catNo1,String catNo2,String itemNo);
    public abstract Map<String,Object> getAllInfo7(String year,String month,String itemNo,String catNo1,String spNo1,String catNo2,String spNo2,String searchAccType,String searchOption,String queryDataType);


    public abstract Map<String,Object> getAllColumn8(String catNo1,String catNo2);
    public abstract Map<String,Object> getAllInfo8(String year,String month,String itemNo,String catNo1,String spNo1,String catNo2,String spNo2,String searchAccType,String searchOption,String queryDataType);

    public abstract List<Lshszd> queryLshszd(String catNo,String spNo,String spLevel);

}
