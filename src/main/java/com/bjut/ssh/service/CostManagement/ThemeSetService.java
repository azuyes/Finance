package com.bjut.ssh.service.CostManagement;
import com.bjut.ssh.entity.Msg;

/**
 * @Title: QuotaSetService
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/7/6 11:09
 * @Version: 1.0
 */
public interface ThemeSetService {
    public Msg getGraphAll();
    public Msg addThemeInfo(String chartIDSelect, String chartThemeSelect);
}
