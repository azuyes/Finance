package com.bjut.ssh.service.AssistedManagement;

import com.bjut.ssh.entity.ContactsBalanceQueryVo;
import com.bjut.ssh.entity.Lskmzd;
import com.bjut.ssh.entity.Lswldw;
import com.bjut.ssh.entity.Lswlje;

import java.util.List;

/**
 * @Title: ContactsBalanceSearchService
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/7/13 17:27
 * @Version: 1.0
 */
public interface ContactsBalanceSearchService {

    public abstract List<ContactsBalanceQueryVo> queryContactsBalance1 (String year, String month, String itemNo, String companyNo, String searchAccType, String searchOption);

    public abstract List<ContactsBalanceQueryVo> queryContactsBalance2 (String year, String month, String itemNo, String companyNo, String searchAccType, String searchOption);

    public abstract List<ContactsBalanceQueryVo> queryContactsBalance4 (String year, String month, String itemNo, String companyNo, String searchAccType, String searchOption,String queryDataType);

    public abstract List<Lskmzd> queryContactsItem();

    public abstract List<Lswlje> queryLswlje();

    public abstract List<Lswldw> queryLswldw();

    public abstract boolean judgeLswlje(String itemNo);

    public abstract List<Object> getFormulaResult(String sql);

    public abstract List<ContactsBalanceQueryVo> queryContactsBalanceQueryVo();

}
