package com.bjut.ssh.serviceImpl.CostManagement;
import com.bjut.ssh.dao.CostManagement.ElseSetDao;
import com.bjut.ssh.dao.CostManagement.QuotaSetDao;
import com.bjut.ssh.dao.CostManagement.ThemeSetDao;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.ElseSetService;
import com.bjut.ssh.service.CostManagement.QuotaSetService;
import com.bjut.ssh.service.CostManagement.ThemeSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Title: BasicOperatingExpensesServieImp
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/7/6 14:01
 * @Version: 1.0
 */
@Service
@Transactional
public class ThemeSetImpl implements ThemeSetService{
    @Autowired
    private ThemeSetDao themeSetDao;

    @Override
    public Msg getGraphAll(){
        return themeSetDao.getGraphAll();
    }

    @Override
    public Msg addThemeInfo(String chartIDSelect, String chartThemeSelect){
        return themeSetDao.addThemeInfo(chartIDSelect, chartThemeSelect);
    }
}
