package com.bjut.ssh.serviceImpl.CostManagement;

import com.bjut.ssh.dao.CostManagement.ElseSetDao;
import com.bjut.ssh.entity.Msg;
import com.bjut.ssh.service.CostManagement.ElseSetService;
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
public class ElseSetImpl implements ElseSetService{
    @Autowired
    private ElseSetDao elseSetDao;

    @Override
    public Msg getInitGrid(){
        return elseSetDao.getInitGrid();
    }

    @Override
    public Msg getDefinedItemGridInfo(){
        return elseSetDao.getDefinedItemGridInfo();
    }

    @Override
    public Msg addDefinedItemInfo(String ItemNo, String ItemName){
        return elseSetDao.addDefinedItemInfo(ItemNo, ItemName);
    }

    @Override
    public Msg deleteDefinedItemInfo(String ItemNo){
        return elseSetDao.deleteDefinedItemInfo(ItemNo);
    }

    @Override
    public Msg getFixedItemGridInfo(){
        return elseSetDao.getFixedItemGridInfo();
    }

    @Override
    public Msg getDefinedItemInfo(String ItemNo){
        return elseSetDao.getDefinedItemInfo(ItemNo);
    }

    @Override
    public Msg getItemNameSelect_defined(String selectItemTypeValue, String itemNo_defined){
        return elseSetDao.getItemNameSelect_defined(selectItemTypeValue, itemNo_defined);
    }

    @Override
    public Msg addDefinedItemInfo_defined(String itemNo_defined, String ItemID_defined, String selectItemTypeValue){
        return elseSetDao.addDefinedItemInfo_defined(itemNo_defined, ItemID_defined, selectItemTypeValue);
    }

    @Override
    public Msg deleteItemInfo_defined(String ItemID, String itemNo_defined, String selectItemTypeValue){
        return elseSetDao.deleteItemInfo_defined(ItemID, itemNo_defined, selectItemTypeValue);
    }

    @Override
    public Msg getRelateItemNameSelect_fixed(String selectRelatedItemType){
        return elseSetDao.getRelateItemNameSelect_fixed(selectRelatedItemType);
    }

    @Override
    public Msg getRelateItemNoSelect_fixed(String selectRelatedItemType){
        return elseSetDao.getRelateItemNoSelect_fixed(selectRelatedItemType);
    }

    @Override
    public Msg addFixedItemInfo_fixed(String relateItemType_fixed, String relateItemNo_fixed, String ItemID_fixed){
        return elseSetDao.addFixedItemInfo_fixed(relateItemType_fixed, relateItemNo_fixed, ItemID_fixed);
    }

    @Override
    public Msg getMenuInfo(){
        return elseSetDao.getMenuInfo();
    }

    @Override
    public Msg addMenuInfo(String MenuName, String IsShow){
        return elseSetDao.addMenuInfo(MenuName, IsShow);
    }

}
