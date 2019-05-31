package com.bjut.ssh.serviceImpl.AssistedManagement;

import com.bjut.ssh.dao.AssistedManagement.SpAccountCategoryDao;
import com.bjut.ssh.dao.AssistedManagement.SpAccountInitializationDao;
import com.bjut.ssh.dao.FinanceProcess.CaptionOfAccountDao;
import com.bjut.ssh.entity.*;
import com.bjut.ssh.service.AssistedManagement.SpAccountInitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @Title: SpAccountInitializationServiceImpl
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/5/28 9:45
 * @Version: 1.0
 */
@Service
@Transactional
public class SpAccountInitializationServiceImpl implements SpAccountInitializationService {

    @Autowired
    private SpAccountInitializationDao spAccountInitializationDao;
    @Autowired
    private CaptionOfAccountDao captionOfAccountDao;

    @Transactional
    @Override
    public List<LskmzdQueryVo> getCaptionOfSpAccount(String id, String levelFlag) {
        List<LskmzdQueryVo> lswldwQueryVos = new ArrayList<LskmzdQueryVo>();
        List<Lskmzd> lskmzdList = spAccountInitializationDao.queryAccountByLevel(id,levelFlag);
        int i=0;
        if(lskmzdList.size()>0){
            for(Lskmzd lskmzd : lskmzdList){
                String spAcc1 = lskmzd.getSupAcc1();
                String spAcc2 = lskmzd.getSupAcc2();
                LskmzdQueryVo lskmzdQueryVo = new LskmzdQueryVo();
                if(spAcc1!=null && spAcc1 != ""){
                    String sp_acc_name1 = captionOfAccountDao.getCatNameOfLshsfl(spAcc1);
                    lskmzdQueryVo.setspAccName1(sp_acc_name1);
                }
                if(spAcc2!=null && spAcc2 != ""){
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

    @Override
    @Transactional
    public LshsjeOrLshsslForInit getItemByIdAndSpecial1(String itemNo,String spLevel) {
        LshsjeOrLshsslForInit lshsjeOrLshsslForInit = new LshsjeOrLshsslForInit();

        LskmzdVoForSpecial lskmzdVoForSpecial = spAccountInitializationDao.getItemById(itemNo);

        lshsjeOrLshsslForInit.setLskmzdVoForSpecial(lskmzdVoForSpecial);

        List<LshsjeOrLshsslQueryVo> lshsjeOrLshsslQueryVos = null;

        String accType = lskmzdVoForSpecial.getAccType();
        String catNo1 = lskmzdVoForSpecial.getSupAcc1();
        String catNo2 = lskmzdVoForSpecial.getSupAcc2();

        lshsjeOrLshsslQueryVos = spAccountInitializationDao.getLshsjeOrLshssl(itemNo,accType,spLevel,catNo1,catNo2);

        lshsjeOrLshsslForInit.setLshsjeOrLshsslQueryVos(lshsjeOrLshsslQueryVos);

        return lshsjeOrLshsslForInit;
    }

    @Override
    @Transactional
    public List<LshsjeOrLshsslQueryVo> queryContactsCategoryByLevel(String id, String levelFlag, String itemNo) {
        LskmzdVoForSpecial lskmzdVoForSpecial = spAccountInitializationDao.getItemById(itemNo);

        String accType = lskmzdVoForSpecial.getAccType();
        String catNo1 = lskmzdVoForSpecial.getSupAcc1();

        return spAccountInitializationDao.queryContactsCategoryByLevel(id,accType,levelFlag,itemNo,catNo1);
    }

    @Override
    public boolean updateSpCount1(List<LshsjeOrLshsslQueryVo> lshsjeOrLshsslQueryVos,String itemNo) {

        LskmzdVoForSpecial lskmzdVoForSpecial = spAccountInitializationDao.getItemById(itemNo);

        String accType = lskmzdVoForSpecial.getAccType();
        String catNo1 = lskmzdVoForSpecial.getSupAcc1();

        for (LshsjeOrLshsslQueryVo lshsjeOrLshsslQueryVo : lshsjeOrLshsslQueryVos) {
            spAccountInitializationDao.updateSpCount1(lshsjeOrLshsslQueryVo, itemNo, accType,catNo1);
        }
        return true;
    }


    @Override
    @Transactional
    public LshsjeOrLshsslForInit getItemByIdAndSpecial2(String itemNo) {
        LshsjeOrLshsslForInit lshsjeOrLshsslForInit = new LshsjeOrLshsslForInit();

        LskmzdVoForSpecial lskmzdVoForSpecial = spAccountInitializationDao.getItemById(itemNo);

        lshsjeOrLshsslForInit.setLskmzdVoForSpecial(lskmzdVoForSpecial);

        List<LshsjeOrLshsslQueryVo> lshsjeOrLshsslQueryVos = null;

        String accType = lskmzdVoForSpecial.getAccType();
        String catNo1 = lskmzdVoForSpecial.getSupAcc1();
        String catNo2 = lskmzdVoForSpecial.getSupAcc2();

        lshsjeOrLshsslQueryVos = spAccountInitializationDao.getLshsjeOrLshssl2(itemNo,accType,catNo1,catNo2);

        lshsjeOrLshsslForInit.setLshsjeOrLshsslQueryVos(lshsjeOrLshsslQueryVos);

        return lshsjeOrLshsslForInit;
    }


    @Override
    public boolean updateSpCount2(List<LshsjeOrLshsslQueryVo> lshsjeOrLshsslQueryVos,String itemNo) {

        LskmzdVoForSpecial lskmzdVoForSpecial = spAccountInitializationDao.getItemById(itemNo);

        String accType = lskmzdVoForSpecial.getAccType();
        String catNo1 = lskmzdVoForSpecial.getSupAcc1();
        String catNo2 = lskmzdVoForSpecial.getSupAcc2();

        for (LshsjeOrLshsslQueryVo lshsjeOrLshsslQueryVo : lshsjeOrLshsslQueryVos) {
            spAccountInitializationDao.updateSpCount2(lshsjeOrLshsslQueryVo, itemNo, accType,catNo1,catNo2);
        }
        return true;
    }
}
