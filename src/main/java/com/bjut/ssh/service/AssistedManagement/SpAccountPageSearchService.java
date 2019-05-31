package com.bjut.ssh.service.AssistedManagement;

import com.bjut.ssh.entity.Lskmzd;
import com.bjut.ssh.entity.Lspzk1VoForSearch;
import com.bjut.ssh.entity.QueryLskmzdFoSpAccountPageSearch;

import java.util.List;

/**
 * @Title: SpAccountPageSearchService
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/9/5 9:22
 * @Version: 1.0
 */
public interface SpAccountPageSearchService {

    public List<Lspzk1VoForSearch> querySpAccountPage(String from, String to, String itemNo, String spCatNo1, String spNo1, String spCatNo2, String spNo2, String searchOption);

    public List<Lskmzd> querySpAccByCatNo(String id, String catNo1, String catNo2);

    public Double queryColumn(String table_name, String col_name, String condition);

    public abstract QueryLskmzdFoSpAccountPageSearch queryLskmzdForHeadInfo (String itemNo, String  spCatNo1, String  spNo1,String spCatNo2,String spNo2 );
}
