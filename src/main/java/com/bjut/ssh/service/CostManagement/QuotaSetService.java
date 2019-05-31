package com.bjut.ssh.service.CostManagement;
import com.bjut.ssh.entity.Msg;

/**
 * @Title: QuotaSetService
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/7/6 11:09
 * @Version: 1.0
 */
public interface QuotaSetService {
    public Msg getGraphAll();
    public Msg getGraphInfo(String gridNo);
    public Msg deleteGraphInfo(String gridNo, String ItemNo, String ItemType);
    public Msg addGraphInfo(String gridNo, String ItemNo, String ItemType, String ItemFunction);
    public Msg getItemFunctionSelect(String gridNo);
    public Msg getItemTypeSelect(String gridNo, String selectItemFunctionValue);
    public Msg getItemNameSelect(String selectItemTypeValue);
}
