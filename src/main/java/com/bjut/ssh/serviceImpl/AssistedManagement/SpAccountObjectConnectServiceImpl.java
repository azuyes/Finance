package com.bjut.ssh.serviceImpl.AssistedManagement;

import com.bjut.ssh.dao.AssistedManagement.SpAccountObjectConnectDao;
import com.bjut.ssh.entity.Lshsfl;
import com.bjut.ssh.entity.Lshszd;
import com.bjut.ssh.entity.Lskmzd;
import com.bjut.ssh.service.AssistedManagement.SpAccountObjectConnectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Title: SpAccountObjectConnectImpl
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/5/14 10:56
 * @Version: 1.0
 */
@Service
@Transactional
public class SpAccountObjectConnectServiceImpl implements SpAccountObjectConnectService {
    @Autowired
    private SpAccountObjectConnectDao spAccountObjectConnectDao;

    @Transactional
    @Override
    public List<Lshszd> getSpAccountName(String spNo, String catNo) {
        List<Lshszd> lshszds = spAccountObjectConnectDao.getSpAccountName(spNo, catNo);
        //转换成树形json，待完成
        return lshszds;
    }

    @Override
    public Lskmzd getSpItemById(String itemNo) {
        return spAccountObjectConnectDao.getSpItemById(itemNo);
    }

    @Override
    public List<Lshsfl> getSpAccountCategory() {
        return spAccountObjectConnectDao.getSpAccountCategory();
    }

    @Override
    public boolean spAccountConnect1(String[] spNos1, String itemNo) {
        try {
            String spNo, itemNum;

            Set set = new HashSet();
            for (int i = 0; i < spNos1.length; i++) {
                set.add(spNos1[i]);
            }
            spNos1 = (String[]) set.toArray(new String[0]);


            List<Lskmzd> lskmzdList = spAccountObjectConnectDao.getItemNosByItemNo(itemNo);
            for (int i = 0; i < spNos1.length; i++) {
                for(Lskmzd lskmzd : lskmzdList){
                    spNo = spNos1[i];
                    itemNum = lskmzd.getItemNo();
                    if (!spAccountObjectConnectDao.existSpAccountConnect1(spNo, itemNum)) {
                        spAccountObjectConnectDao.spAccountConnect1(spNo, itemNum);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean spAccountConnect2(String[] spNos1, String[] spNos2, String itemNo) {
        try {
            String spNo1, spNo2, itemNum;

            Set set1 = new HashSet();
            for (int i = 0; i < spNos1.length; i++) {
                set1.add(spNos1[i]);
            }
            spNos1 = (String[]) set1.toArray(new String[0]);

            Set set2 = new HashSet();
            for (int j = 0; j < spNos2.length; j++) {
                set2.add(spNos2[j]);
            }
            spNos2 = (String[]) set2.toArray(new String[0]);

            List<Lskmzd> lskmzdList = spAccountObjectConnectDao.getItemNosByItemNo(itemNo);
            for (int i = 0; i < spNos1.length; i++) {
                for (int m = 0; m < spNos2.length; m++) {
                    for(Lskmzd lskmzd : lskmzdList){
                        spNo1 = spNos1[i];
                        spNo2 = spNos2[m];
                        itemNum = lskmzd.getItemNo();
                        if (!spAccountObjectConnectDao.existSpAccountConnect2(spNo1, spNo2, itemNum)) {
                            spAccountObjectConnectDao.spAccountConnect2(spNo1, spNo2, itemNum);
                        }
                    }

                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getAuthority1(String itemNo) {

        return spAccountObjectConnectDao.getAuthority1(itemNo);
    }

    @Override
    public String getAuthority2(String itemNo) {

        return spAccountObjectConnectDao.getAuthority2(itemNo);
    }

    @Override
    public boolean judgeUnChecked(String spNo, String itemNo) {

        Lskmzd lskmzd = spAccountObjectConnectDao.getItemNo(itemNo);

        String supAcc2 = lskmzd.getSupAcc2();
        String accType = lskmzd.getAccType();
        String supAcc1 = lskmzd.getSupAcc1();

        boolean bool = true;

//        if(supAcc2 == null) {
        List<Lskmzd> lskmzdList = spAccountObjectConnectDao.getItemNosByItemNo(itemNo);
        for (Lskmzd lskmzd1 : lskmzdList) {
            //String spNo1Sub = spNo.substring(0,4);
            String itemNo1 = lskmzd1.getItemNo();
            bool = spAccountObjectConnectDao.judgeUnChecked1(spNo, itemNo1, accType, supAcc1);
            if (bool == false)
                break;
        }
//        }

        if(bool){
            if(supAcc2 == null) {
                for(Lskmzd lskmzd1 : lskmzdList){
                    String itemNo1 = lskmzd1.getItemNo();
                    bool = spAccountObjectConnectDao.deleteUnChecked1(spNo,itemNo1,accType,supAcc1);
                    if(bool == false)
                        break;
                }
            }else{
                for(Lskmzd lskmzd1 : lskmzdList){
                    String itemNo1 = lskmzd1.getItemNo();
                    bool = spAccountObjectConnectDao.deleteUnChecked2(spNo,itemNo1,accType,supAcc1);
                    if(bool == false)
                        break;
                }
            }

        }

        return bool;
    }

    @Override
    public String getCatNameBySpItemNo(String catNo) {
        return spAccountObjectConnectDao.getCatNameBySpItemNo(catNo);
    }
}

