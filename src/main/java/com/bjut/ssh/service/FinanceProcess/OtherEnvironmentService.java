package com.bjut.ssh.service.FinanceProcess;

import com.bjut.ssh.entity.Lsconf;
import com.bjut.ssh.entity.Msg;

import java.util.List;
/**
 * @Title: OtherEnvironmentService
 * @Description: 其他环境设置服务接口
 * @Author: czh
 * @CreateDate: 2018/3/29 10:20
 * @Version: 1.0
 */
public interface OtherEnvironmentService {
    /**
    *@author czh
    *@Description 设置其他环境接口
    *@Date 2018/4/3 11:25
    *@Param [configs]
    *@return void
    **/
    public Msg setOtherEnvironment(List<Lsconf> configs);
    public void setOtherEnvironment(Lsconf config);
    public Msg getConfigs(List<String> config_list);
}
