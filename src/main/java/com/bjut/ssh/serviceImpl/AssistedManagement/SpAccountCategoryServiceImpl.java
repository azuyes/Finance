package com.bjut.ssh.serviceImpl.AssistedManagement;

import com.bjut.ssh.dao.AssistedManagement.SpAccountCategoryDao;
import com.bjut.ssh.entity.Lshsfl;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.AssistedManagement.SpAccountCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Title: SpAccountCategoryServiceImpl
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/5/2 14:48
 * @Version: 1.0
 */
@Service
@Transactional
public class SpAccountCategoryServiceImpl implements SpAccountCategoryService {

    @Autowired
    private SpAccountCategoryDao spAccountCategoryDao;

    @Transactional
    @Override
    public List<Lshsfl> getSpAccountCategory() {
        List<Lshsfl> lshsfls = spAccountCategoryDao.getSpAccountCategory();
        return lshsfls;
    }

    @Transactional
    @Override
    public Msg saveSpAccountCategory(Lshsfl lshsfl) {
        return spAccountCategoryDao.saveSpAccountCategory(lshsfl);
    }

    @Transactional
    @Override
    public Msg delSpAccountCategoryById(String id) {
        return  spAccountCategoryDao.delSpAccountCategoryById(id);
    }
}
