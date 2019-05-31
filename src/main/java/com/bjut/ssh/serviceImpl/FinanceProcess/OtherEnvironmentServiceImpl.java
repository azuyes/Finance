package com.bjut.ssh.serviceImpl.FinanceProcess;

import com.bjut.ssh.dao.FinanceProcess.OtherEnvironmentDao;
import com.bjut.ssh.entity.Lsconf;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.FinanceProcess.OtherEnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Title: OtherEnvironmentServiceImpl
 * @Description: 其他环境设置服务
 * @Author: czh
 * @CreateDate: 2018/3/29 10:17
 * @Version: 1.0
 */

@Service
@Transactional
public class OtherEnvironmentServiceImpl implements OtherEnvironmentService{
    @Autowired
    private OtherEnvironmentDao otherEnvironmentDao;

    @Override
    @Transactional
    public Msg setOtherEnvironment(List<Lsconf> configs){
        return otherEnvironmentDao.setOtherEnvironment(configs);
    }

    @Override
    @Transactional
    public void setOtherEnvironment(Lsconf config){
        otherEnvironmentDao.setOtherEnvironment(config);
    };

    @Override
    @Transactional
    public Msg getConfigs(List<String> config_list){
        return otherEnvironmentDao.getConfigs(config_list);
    }
}
