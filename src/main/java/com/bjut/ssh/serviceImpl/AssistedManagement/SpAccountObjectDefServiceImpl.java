package com.bjut.ssh.serviceImpl.AssistedManagement;

import com.bjut.ssh.dao.AssistedManagement.SpAccountObjectDefDao;
import com.bjut.ssh.entity.*;
import com.bjut.ssh.service.AssistedManagement.SpAccountObjectDefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Title: SpAccountObjectDefServiceImpl
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/5/7 12:57
 * @Version: 1.0
 */
@Service
@Transactional
public class SpAccountObjectDefServiceImpl implements SpAccountObjectDefService {

    @Autowired
    private SpAccountObjectDefDao spAccountObjectDefDao;

    @Transactional
    @Override
    public LshszdQueryVo getSpAccountCategoryById(String catNo) {

        LshszdQueryVo lshszdQueryVo = new LshszdQueryVo();
        Lshsfl lshsfl = spAccountObjectDefDao.getSpAccountCategoryById(catNo);
        lshszdQueryVo.setLshsfl(lshsfl);
        List<Lshszd>lshszds = null;
        if(lshsfl != null){
            lshszds = spAccountObjectDefDao.getLshszd(catNo);
            lshszdQueryVo.setLshszds(lshszds);
            return lshszdQueryVo;
        }else{
            return null;
        }
    }

    @Transactional
    @Override
    public List<Lshszd> queryContactsCategoryByLevel(String id, String levelFlag, String catNo) {
        return spAccountObjectDefDao.queryContactsCategoryByLevel(id,levelFlag,catNo);
    }

    @Override
    public Msg saveSpAccountObjectDef(Lshszd lshszd){

        return spAccountObjectDefDao.saveSpAccountObjectDef(lshszd);
    }

    @Override
    public Msg delSpAccountObjectDef(String id, String spLevel,String catNo) {
        return spAccountObjectDefDao.delSpAccountObjectDef(id,spLevel,catNo);
    }

    @Override
    public List<Lskmzd> queryCaptionOfAccountByLevel(String id, String levelFlag){
        return spAccountObjectDefDao.queryCaptionOfAccountByLevel(id, levelFlag);
    }

    @Override
    public List<Lskmzd> queryCaptionOfAccountByLevel1(String id, String levelFlag){
        return spAccountObjectDefDao.queryCaptionOfAccountByLevel1(id, levelFlag);
    }

}
