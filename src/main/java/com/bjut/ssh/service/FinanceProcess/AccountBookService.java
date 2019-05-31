package com.bjut.ssh.service.FinanceProcess;

import com.bjut.ssh.entity.Lsszzd;
import com.bjut.ssh.entity.Lszczd;
import com.bjut.ssh.entity.Lszcdy;
import com.bjut.ssh.entity.Msg;

import java.util.List;

/**
 * @Title: AccountBookService
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/4/24 14:10
 * @Version: 1.0
 */
public interface AccountBookService {
    public Msg getConfigsForBook();
    public List<Lszczd> queryAccountBook();
    public List<Lszcdy> queryAccountBookDef(String AcontBookNo);
    public List<Lsszzd> queryPrintFor();
    public Msg saveAccountBook(Lszczd lszczd);
    public Msg saveAccountBookDef(Lszcdy lszcdy);
    public Msg savePrintFor(Lsszzd lsszzd);
    public Msg editAccountBook(Lszczd lszczd);
    public Msg editAccountBookDef(Lszcdy lszcdy);
    public Msg editPrintFor(Lsszzd lsszzd);
    public Msg delAccountBook(String id);
    public Msg delAccountBookDef(String id);
    public Msg delPrintFor(String id);
}
