package com.bjut.ssh.serviceImpl.FinanceProcess;

import com.bjut.ssh.dao.FinanceProcess.AccountBookDao;
import com.bjut.ssh.entity.Lsszzd;
import com.bjut.ssh.entity.Lszcdy;
import com.bjut.ssh.entity.Lszczd;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.FinanceProcess.AccountBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Title: AccountBookServiceImpl
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/4/24 14:12
 * @Version: 1.0
 */
@Service
@Transactional
public class AccountBookServiceImpl implements AccountBookService{
    @Autowired
    private AccountBookDao accountBookDao;

    @Override
    public Msg getConfigsForBook(){
        return accountBookDao.getConfigsForBook();
    }

    @Override
    public List<Lszczd> queryAccountBook(){
        return accountBookDao.queryAccountBook();
    }

    @Override
    public List<Lszcdy> queryAccountBookDef(String AcontBookNo){
        return accountBookDao.queryAccountBookDef(AcontBookNo);
    }

    @Override
    public List<Lsszzd> queryPrintFor(){
        return accountBookDao.queryPrintFor();
    }

    @Override
    public Msg saveAccountBook(Lszczd lszczd){
        return accountBookDao.saveAccountBook(lszczd);
    }

    @Override
    public Msg saveAccountBookDef(Lszcdy lszcdy){
        return accountBookDao.saveAccountBookDef(lszcdy);
    }

    @Override
    public Msg savePrintFor(Lsszzd lsszzd){
        return accountBookDao.savePrintFor(lsszzd);
    }

    @Override
    public Msg editAccountBookDef(Lszcdy lszcdy){return accountBookDao.editAccountBookDef(lszcdy);}

    @Override
    public Msg editAccountBook(Lszczd lszczd){
        return accountBookDao.editAccountBook(lszczd);
    }

    @Override
    public Msg editPrintFor(Lsszzd lsszzd){
        return accountBookDao.editPrintFor(lsszzd);
    }

    @Override
    public Msg delAccountBook(String id){
        return accountBookDao.delAccountBook(id);
    }

    @Override
    public Msg delAccountBookDef(String id){
        return accountBookDao.delAccountBookDef(id);
    }

    @Override
    public Msg delPrintFor(String id){
        return accountBookDao.delPrintFor(id);
    }
}
