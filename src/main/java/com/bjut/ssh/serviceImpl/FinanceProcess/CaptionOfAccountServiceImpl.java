package com.bjut.ssh.serviceImpl.FinanceProcess;

import com.bjut.ssh.dao.FinanceProcess.CaptionOfAccountDao;
import com.bjut.ssh.entity.*;
import com.bjut.ssh.service.FinanceProcess.CaptionOfAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: CaptionOfAccountServiceImpl
 * @Description: 科目字典维护服务实现
 * @Author: czh
 * @CreateDate: 2018/4/10 13:42
 * @Version: 1.0
 */
@Service
@Transactional
public class CaptionOfAccountServiceImpl implements CaptionOfAccountService {
    @Autowired
    private CaptionOfAccountDao captionOfAccountDao;

    @Override
    @Transactional
    public Msg getConfigsForCap(){
        return captionOfAccountDao.getConfigsForCap();
    }

    @Override
    @Transactional
    public String getSubjectStructure(){
        return captionOfAccountDao.getSubjectStructure();
    }

    @Override
    @Transactional
    public String getBeginMonth(){
        return captionOfAccountDao.getBeginMonth();
    }

    @Override
    @Transactional
    public List<String> getPrecisions(){
        return captionOfAccountDao.getPrecisions();
    }

    @Override
    public Msg queryAllCaptionOfAccountByLevel(String id, String levelFlag){
        return captionOfAccountDao.queryAllCaptionOfAccountByLevel(id, levelFlag);
    }

    @Override
    public List<Lskmzd> queryCaptionOfAccountByLevel(String id, String levelFlag){
        return captionOfAccountDao.queryCaptionOfAccountByLevel(id, levelFlag);
    }

    @Override
    public Msg addAllCaptionOfAccount(LskmzdNLskmslQueryVo lskmzdNLskmslQueryVo){
        return captionOfAccountDao.addAllCaptionOfAccount(lskmzdNLskmslQueryVo);
    }

    @Override
    public Msg addCaptionOfAccount(Lskmzd lskmzd){
        return captionOfAccountDao.addCaptionOfAccount(lskmzd);
    }

    @Override
    public Msg addCaptionOfAccountQuantity(Lskmsl lskmsl){
        return captionOfAccountDao.addCaptionOfAccountQuantity(lskmsl);
    }

    @Override
    public Msg delCaptionOfAccount(String itemNo, String level, String is_quan){
        return captionOfAccountDao.delCaptionOfAccount(itemNo, level, is_quan);
    }

    @Override
    public List<Lshsfl> querySpecialAcc(){
        return captionOfAccountDao.querySpecialAcc();
    }

    @Override
    public Msg editAllCaptionOfAccount(LskmzdNLskmslQueryVo lskmzdNLskmslQueryVo){
        return captionOfAccountDao.editAllCaptionOfAccount(lskmzdNLskmslQueryVo);
    }

    @Override
    public Msg editCaptionOfAccount(Lskmzd lskmzd){
        return captionOfAccountDao.editCaptionOfAccount(lskmzd);
    }

    @Override
    public Msg editCaptionOfAccountQty(Lskmsl lskmsl){
        return captionOfAccountDao.editCaptionOfAccountQty(lskmsl);
    }

    @Override
    public List<LskmzdQueryVo> getCaptionWithSpAcc(String id, String levelFlag) {
        List<LskmzdQueryVo> lswldwQueryVos = new ArrayList<LskmzdQueryVo>();
        List<Lskmzd> lskmzdList = captionOfAccountDao.queryCaptionOfAccountByLevel(id,levelFlag);
        int i=0;
        if(lskmzdList.size()>0){
            for(Lskmzd lskmzd : lskmzdList){
                String spAcc1 = lskmzd.getSupAcc1();
                String spAcc2 = lskmzd.getSupAcc2();
                LskmzdQueryVo lskmzdQueryVo = new LskmzdQueryVo();
                if(spAcc1!=null){
                    String sp_acc_name1 = captionOfAccountDao.getCatNameOfLshsfl(spAcc1);
                    lskmzdQueryVo.setspAccName1(sp_acc_name1);
                }
                if(spAcc2!=null){
                    String sp_acc_name2 = captionOfAccountDao.getCatNameOfLshsfl(spAcc2);
                    lskmzdQueryVo.setspAccName2(sp_acc_name2);
                }
                lskmzdQueryVo.setLskmzd(lskmzd);
                lswldwQueryVos.add(i,lskmzdQueryVo);
                i++;
            }
        }else{
            return null;
        }
        return lswldwQueryVos;
    }
}
