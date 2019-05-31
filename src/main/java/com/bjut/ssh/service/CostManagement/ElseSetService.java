package com.bjut.ssh.service.CostManagement;

import com.bjut.ssh.entity.Msg;

/**
 * @Title: QuotaSetService
 * @Description: TODO
 * @Author: lxy
 * @CreateDate: 2018/7/6 11:09
 * @Version: 1.0
 */
public interface ElseSetService {
    public Msg getInitGrid();
    public Msg getDefinedItemGridInfo();
    public Msg addDefinedItemInfo(String ItemNo, String ItemName);
    public Msg deleteDefinedItemInfo(String ItemNo);
    public Msg getFixedItemGridInfo();
    public Msg getDefinedItemInfo(String itemNo);
    public Msg getItemNameSelect_defined(String selectItemTypeValue, String itemNo_defined);
    public Msg addDefinedItemInfo_defined(String itemNo_defined, String ItemID_defined, String selectItemTypeValue);
    public Msg deleteItemInfo_defined(String ItemID, String itemNo_defined, String selectItemTypeValue);
    public Msg getRelateItemNameSelect_fixed(String selectRelatedItemType);
    public Msg getRelateItemNoSelect_fixed(String selectRelatedItemType);
    public Msg addFixedItemInfo_fixed(String relateItemType_fixed, String relateItemNo_fixed, String ItemID_fixed);
    public Msg getMenuInfo();
    public Msg addMenuInfo(String MenuName, String IsShow);
}
