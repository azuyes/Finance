package com.bjut.ssh.serviceImpl.AssistedManagement;

import com.bjut.ssh.dao.AssistedManagement.SpAccountBalanceSearchDao;
import com.bjut.ssh.entity.*;
import com.bjut.ssh.service.AssistedManagement.SpAccountBalanceSearchService;
import com.bjut.ssh.util.FormulaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: SpAccountBalanceSearchServiceImpl
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/9/5 9:23
 * @Version: 1.0
 */
@Service
@Transactional
public class SpAccountBalanceSearchServiceImpl implements SpAccountBalanceSearchService {

    @Autowired
    private SpAccountBalanceSearchDao spAccountBalanceSearchDao;

    @Override
    public List<SpAccountBalanceQueryVo> querySpAccountBalance1(String year, String month, String itemNo, String spCatNo, String spNo, String spLevel,String searchAccType, String searchOption) {
        return spAccountBalanceSearchDao.querySpAccountBalance1(year, month, itemNo, spCatNo, spNo,spLevel, searchAccType, searchOption);
    }

    @Override
    public List<SpAccountBalanceQueryVo> querySpInfoByLevel(String year, String month, String itemNo, String spCatNo, String spNo, String spLevel, String searchAccType, String searchOption) {
        return spAccountBalanceSearchDao.querySpInfoByLevel(year, month, itemNo, spCatNo, spNo,spLevel, searchAccType, searchOption);
    }

    @Override
    public List<SpAccountBalanceQueryVo> querySpAccountBalance2(String year, String month, String itemNo,String item, String spCatNo, String spNo, String searchAccType, String searchOption) {
        return spAccountBalanceSearchDao.querySpAccountBalance2(year, month, itemNo, item,spCatNo, spNo, searchAccType, searchOption);
    }

    @Override
    public Map<String, Object> getAllColumn3(String spCatNo,String itemNo) {
        List<FieldBean> beans = new ArrayList<FieldBean>();
        Map<String,Object> result = new HashMap<String,Object>();

        FieldBean bean1 = new FieldBean();
        bean1.setTitle("核算编号");
        bean1.setField("spNo");
        bean1.setAlign("center");
        bean1.setWidth(100);
        beans.add(bean1);

        FieldBean bean2 = new FieldBean();
        bean2.setTitle("核算名称");
        bean2.setField("spName");
        bean2.setAlign("center");
        bean2.setWidth(170);
        beans.add(bean2);

        List<Lskmzd> lskmzdList = spAccountBalanceSearchDao.querySpAccountItem3(spCatNo,itemNo);
        for(Lskmzd lskmzd:lskmzdList){

            FieldBean bean = new FieldBean();
            bean.setTitle(lskmzd.getItemName());
            bean.setField(lskmzd.getItemNo());
            bean.setAlign("center");
            bean.setWidth(100);
            beans.add(bean);
        }
        result.put("columns", beans);
        return result;
    }

    @Override
    public Map<String, Object> getAllInfo3(String year, String month, String itemNo,String spCatNo,String spNo,String spLevel, String searchAccType, String searchOption,String queryDataType) {
        List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();

        List<Lskmzd> lskmzdList = spAccountBalanceSearchDao.querySpAccountItem3(spCatNo,itemNo);

        List<Lshszd>  lshszdList = spAccountBalanceSearchDao.queryLshszdByCatNo(spCatNo,spNo,spLevel);

        for(Lshszd lshszd:lshszdList){
            Map<String,Object> data = new HashMap<String,Object>();
            spNo = lshszd.getSpNo();

            data.put("spNo",spNo);
            data.put("spName",lshszd.getSpName());

            for(Lskmzd lskmzd:lskmzdList){

                List<Lshsje> lshsjeList = spAccountBalanceSearchDao.queryLshsje(spNo,lskmzd.getItemNo());

                if(lshsjeList.size()>0){
                    String itemNo1 = "t1.itemNo='" + lskmzd.getItemNo() + "'";
                    String sql, result;
                    switch (queryDataType) {
                        case "1":
                            sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month), "JFFS", "Lshsje", null, null, null, itemNo1, spNo, null);
                            result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                            data.put(lskmzd.getItemNo(), result);
                            break;
                        case "2":
                            data.put(lskmzd.getItemNo(), lshsjeList.get(0).getSupMoney());
                            break;
                        case "3":
                            sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month), "BYJD", "Lshsje", null, null, null, itemNo1, spNo, null);
                            result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                            data.put(lskmzd.getItemNo(), result);
                            break;

                        case "4":
                            sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month), "DFFS", "Lshsje", null, null, null, itemNo1, spNo, null);
                            result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                            data.put(lskmzd.getItemNo(), result);
                            break;
                        case "5":
                            sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month), "JFLJ", "Lshsje", null, null, null, itemNo1, spNo, null);
                            result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                            data.put(lskmzd.getItemNo(), result);
                            break;
                        case "6":
                            sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month), "BYDJ", "Lshsje", null, null, null, itemNo1, spNo, null);
                            result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                            data.put(lskmzd.getItemNo(), result);
                            break;
                        case "7":
                            sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month)+1, "YCJF", "Lshsje", null, null, null, itemNo1, spNo, null);
                            result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                            data.put(lskmzd.getItemNo(), result);
                            break;
                        case "8":
                            sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month), "DFLJ", "Lshsje", null, null, null, itemNo1, spNo, null);
                            result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                            data.put(lskmzd.getItemNo(), result);
                            break;
                        case "9":
                            sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month), "BNJD", "Lshsje", null, null, null, itemNo1, spNo, null);
                            result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                            data.put(lskmzd.getItemNo(), result);
                            break;
                        case "10":
                            sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month), "BNDJ", "Lshsje", null, null, null, itemNo1, spNo, null);
                            result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                            data.put(lskmzd.getItemNo(), result);
                            break;
                    }
                    break;
                }else{
                    data.put(lskmzd.getItemNo(), null);
                }

            }
            datas.add(data);
        }

        //生成列对应的行数据


        Map<String,Object> result = new HashMap<String,Object>();
        result.put("rows", datas);
        result.put("total", datas.size());

        return result;
    }


    @Override
    public Map<String, Object> getAllColumn4(String spCatNo) {
        List<FieldBean> beans = new ArrayList<FieldBean>();
        Map<String,Object> result = new HashMap<String,Object>();

        FieldBean bean1 = new FieldBean();
        bean1.setTitle("核算编号");
        bean1.setField("spNo");
        bean1.setAlign("center");
        bean1.setWidth(100);
        beans.add(bean1);

        FieldBean bean2 = new FieldBean();
        bean2.setTitle("核算名称");
        bean2.setField("spName");
        bean2.setAlign("center");
        bean2.setWidth(170);
        beans.add(bean2);

        List<Lskmzd> lskmzdList = spAccountBalanceSearchDao.querySpAccountItem(spCatNo);
        int size = lskmzdList.size();
        for(Lskmzd lskmzd:lskmzdList){

            FieldBean bean = new FieldBean();
            bean.setTitle(lskmzd.getItemName());
            bean.setField(lskmzd.getItemNo());
            bean.setAlign("center");
            bean.setWidth(80);
            beans.add(bean);
        }
        result.put("columns", beans);
        return result;
    }

    @Override
    public Map<String, Object> getAllInfo4(String year, String month, String itemNo,String spCatNo,String spNo,String spLevel, String searchAccType, String searchOption,String queryDataType) {
        List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();

        List<Lskmzd> lskmzdList = spAccountBalanceSearchDao.querySpAccountItem(spCatNo);

        List<Lshszd>  lshszdList = spAccountBalanceSearchDao.queryLshszdByCatNo(spCatNo,spNo,spLevel);

        //List<Lswlje> lswljeList = contactsBalanceSearchService.queryLswlje();

        for(Lshszd lshszd:lshszdList){
            Map<String,Object> data = new HashMap<String,Object>();
            spNo = lshszd.getSpNo();

            data.put("spNo",spNo);
            data.put("spName",lshszd.getSpName());

            for(Lskmzd lskmzd:lskmzdList){

                List<Lshsje> lshsjeList = spAccountBalanceSearchDao.queryLshsje(spNo,lskmzd.getItemNo());

                if(lshsjeList.size()>0){
                    String itemNo1 = "t1.itemNo='" + lskmzd.getItemNo() + "'";
                    String sql, result;
                    switch (queryDataType) {
                        case "1":
                            sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month), "JFFS", "Lshsje", null, null, null, itemNo1, spNo, null);
                            result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                            data.put(lskmzd.getItemNo(), result);
                            break;
                        case "2":
                            data.put(lskmzd.getItemNo(), lshsjeList.get(0).getSupMoney());
                            break;
                        case "3":
                            sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month), "BYJD", "Lshsje", null, null, null, itemNo1, spNo, null);
                            result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                            data.put(lskmzd.getItemNo(), result);
                            break;

                        case "4":
                            sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month), "DFFS", "Lshsje", null, null, null, itemNo1, spNo, null);
                            result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                            data.put(lskmzd.getItemNo(), result);
                            break;
                        case "5":
                            sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month), "JFLJ", "Lshsje", null, null, null, itemNo1, spNo, null);
                            result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                            data.put(lskmzd.getItemNo(), result);
                            break;
                        case "6":
                            sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month), "BYDJ", "Lshsje", null, null, null, itemNo1, spNo, null);
                            result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                            data.put(lskmzd.getItemNo(), result);
                            break;
                        case "7":
                            sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month)+1, "YCJF", "Lshsje", null, null, null, itemNo1, spNo, null);
                            result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                            data.put(lskmzd.getItemNo(), result);
                            break;
                        case "8":
                            sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month), "DFLJ", "Lshsje", null, null, null, itemNo1, spNo, null);
                            result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                            data.put(lskmzd.getItemNo(), result);
                            break;
                        case "9":
                            sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month), "BNJD", "Lshsje", null, null, null, itemNo1, spNo, null);
                            result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                            data.put(lskmzd.getItemNo(), result);
                            break;
                        case "10":
                            sql = FormulaUtil.getSqlByNumObj(Integer.parseInt(month), "BNDJ", "Lshsje", null, null, null, itemNo1, spNo, null);
                            result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                            data.put(lskmzd.getItemNo(), result);
                            break;
                    }
                    break;
                }else{
                    data.put(lskmzd.getItemNo(), null);
                }

            }
            datas.add(data);
        }

        //生成列对应的行数据


        Map<String,Object> result = new HashMap<String,Object>();
        result.put("rows", datas);
        result.put("total", datas.size());

        return result;
}

    @Override
    public List<Lskmzd> querySpAccountItem(String catNo) {
        return spAccountBalanceSearchDao.querySpAccountItem(catNo);
    }

    @Override
    public List<Lshsje> queryLshsje(String spNo, String itemNo) {
        return spAccountBalanceSearchDao.queryLshsje(spNo,itemNo);
    }

    @Override
    public List<Object> getFormulaResult(String sql) {
        return spAccountBalanceSearchDao.getFormulaResult(sql);
    }


    @Override
    public List<SpAccountBalanceQueryVo2> querySpAccountBalance5(String year, String month, String itemNo, String catNo1, String spNo1, String catNo2, String spNo2, String searchAccType, String searchOption) {
        return spAccountBalanceSearchDao.querySpAccountBalance5(year, month, itemNo, catNo1, spNo1,catNo2,spNo2, searchAccType, searchOption);
    }

    @Override
    public List<SpAccountBalanceQueryVo2> querySpAccountBalance6(String year, String month, String itemNo,String item, String catNo1, String spNo1, String catNo2, String spNo2, String searchAccType, String searchOption) {
        return spAccountBalanceSearchDao.querySpAccountBalance6(year, month, itemNo,item, catNo1, spNo1,catNo2,spNo2, searchAccType, searchOption);
    }

    @Override
    public Map<String, Object> getAllColumn7(String catNo1, String catNo2,String itemNo) {
        List<FieldBean> beans = new ArrayList<FieldBean>();
        Map<String,Object> result = new HashMap<String,Object>();

        FieldBean bean1 = new FieldBean();
        bean1.setTitle("核算名称1");
        bean1.setField("spName1");
        bean1.setAlign("center");
        bean1.setWidth(170);
        beans.add(bean1);

        FieldBean bean2 = new FieldBean();
        bean2.setTitle("核算名称2");
        bean2.setField("spName2");
        bean2.setAlign("center");
        bean2.setWidth(170);
        beans.add(bean2);

        List<Lskmzd> lskmzdList = spAccountBalanceSearchDao.querySpAccountItem7(catNo1,catNo2,itemNo);
        for(Lskmzd lskmzd:lskmzdList){

            FieldBean bean = new FieldBean();
            bean.setTitle(lskmzd.getItemName());
            bean.setField(lskmzd.getItemNo());
            bean.setAlign("center");
            bean.setWidth(80);
            beans.add(bean);
        }
        result.put("columns", beans);
        return result;
    }

    @Override
    public Map<String, Object> getAllInfo7(String year, String month, String itemNo, String catNo1, String spNo1, String catNo2, String spNo2, String searchAccType, String searchOption,String queryDataType) {
        List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();

        List<Lskmzd> lskmzdList = spAccountBalanceSearchDao.querySpAccountItem7(catNo1,catNo2,itemNo);

        List<Lshszd>  lshszdList1 = spAccountBalanceSearchDao.queryLshszdByCatNo8(catNo1);
        List<Lshszd>  lshszdList2 = spAccountBalanceSearchDao.queryLshszdByCatNo8(catNo2);

        //List<Lswlje> lswljeList = contactsBalanceSearchService.queryLswlje();

        for(Lshszd lshszd1:lshszdList1){
            for(Lshszd lshszd2:lshszdList2){
                Map<String,Object> data = new HashMap<String,Object>();
                spNo1 = lshszd1.getSpNo();
                spNo2 = lshszd2.getSpNo();
                data.put("spNo1",spNo1);
                data.put("spNo2",spNo2);
                data.put("spName1",lshszd1.getSpName());
                data.put("spName2",lshszd2.getSpName());

                for(Lskmzd lskmzd:lskmzdList){

                    List<Lshsje> lshsjeList = spAccountBalanceSearchDao.queryLshsje8(spNo1,spNo2,lskmzd.getItemNo());

                    if(lshsjeList.size()>0){
                        String itemNo1 = "t1.itemNo='" + lskmzd.getItemNo() + "'";
                        String sql, result;
                        switch (queryDataType) {
                            case "1":
                                sql = FormulaUtil.getSqlByNumObjForSpAccountBalanceSearch2(Integer.parseInt(month), "JFFS", "Lshsje", null, null, null, itemNo1, spNo1, spNo2);
                                result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;
                            case "2":
                                data.put(lskmzd.getItemNo(), lshsjeList.get(0).getSupMoney());
                                break;
                            case "3":
                                sql = FormulaUtil.getSqlByNumObjForSpAccountBalanceSearch2(Integer.parseInt(month), "BYJD", "Lshsje", null, null, null, itemNo1, spNo1, spNo2);
                                result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;

                            case "4":
                                sql = FormulaUtil.getSqlByNumObjForSpAccountBalanceSearch2(Integer.parseInt(month), "DFFS", "Lshsje", null, null, null, itemNo1, spNo1, spNo2);
                                result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;
                            case "5":
                                sql = FormulaUtil.getSqlByNumObjForSpAccountBalanceSearch2(Integer.parseInt(month), "JFLJ", "Lshsje", null, null, null, itemNo1, spNo1, spNo2);
                                result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;
                            case "6":
                                sql = FormulaUtil.getSqlByNumObjForSpAccountBalanceSearch2(Integer.parseInt(month), "BYDJ", "Lshsje", null, null, null, itemNo1, spNo1, spNo2);
                                result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;
                            case "7":
                                sql = FormulaUtil.getSqlByNumObjForSpAccountBalanceSearch2(Integer.parseInt(month)+1, "YCJF", "Lshsje", null, null, null, itemNo1, spNo1, spNo2);
                                result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;
                            case "8":
                                sql = FormulaUtil.getSqlByNumObjForSpAccountBalanceSearch2(Integer.parseInt(month), "DFLJ", "Lshsje", null, null, null, itemNo1, spNo1, spNo2);
                                result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;
                            case "9":
                                sql = FormulaUtil.getSqlByNumObjForSpAccountBalanceSearch2(Integer.parseInt(month), "BNJD", "Lshsje", null, null, null, itemNo1, spNo1, spNo2);
                                result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;
                            case "10":
                                sql = FormulaUtil.getSqlByNumObjForSpAccountBalanceSearch2(Integer.parseInt(month), "BNDJ", "Lshsje", null, null, null, itemNo1, spNo1, spNo2);
                                result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;
                        }

                    }else{
                        data.put(lskmzd.getItemNo(), null);
                    }

                }
                datas.add(data);
            }

        }

        //生成列对应的行数据


        Map<String,Object> result = new HashMap<String,Object>();
        result.put("rows", datas);
        result.put("total", datas.size());

        return result;
    }


    @Override
    public Map<String, Object> getAllColumn8(String catNo1, String catNo2) {
        List<FieldBean> beans = new ArrayList<FieldBean>();
        Map<String,Object> result = new HashMap<String,Object>();

        FieldBean bean1 = new FieldBean();
        bean1.setTitle("核算名称1");
        bean1.setField("spName1");
        bean1.setAlign("center");
        bean1.setWidth(170);
        beans.add(bean1);

        FieldBean bean2 = new FieldBean();
        bean2.setTitle("核算名称2");
        bean2.setField("spName2");
        bean2.setAlign("center");
        bean2.setWidth(170);
        beans.add(bean2);

        List<Lskmzd> lskmzdList = spAccountBalanceSearchDao.querySpAccountItem8(catNo1,catNo2);
        for(Lskmzd lskmzd:lskmzdList){

            FieldBean bean = new FieldBean();
            bean.setTitle(lskmzd.getItemName());
            bean.setField(lskmzd.getItemNo());
            bean.setAlign("center");
            bean.setWidth(80);
            beans.add(bean);
        }
        result.put("columns", beans);
        return result;
    }

    @Override
    public Map<String, Object> getAllInfo8(String year, String month, String itemNo, String catNo1, String spNo1, String catNo2, String spNo2, String searchAccType, String searchOption,String queryDataType) {
        List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();

        List<Lskmzd> lskmzdList = spAccountBalanceSearchDao.querySpAccountItem8(catNo1,catNo2);

        List<Lshszd>  lshszdList1 = spAccountBalanceSearchDao.queryLshszdByCatNo8(catNo1);
        List<Lshszd>  lshszdList2 = spAccountBalanceSearchDao.queryLshszdByCatNo8(catNo2);

        //List<Lswlje> lswljeList = contactsBalanceSearchService.queryLswlje();

        for(Lshszd lshszd1:lshszdList1){
            for(Lshszd lshszd2:lshszdList2){
                Map<String,Object> data = new HashMap<String,Object>();
                spNo1 = lshszd1.getSpNo();
                spNo2 = lshszd2.getSpNo();
                data.put("spNo1",spNo1);
                data.put("spNo2",spNo2);
                data.put("spName1",lshszd1.getSpName());
                data.put("spName2",lshszd2.getSpName());

                for(Lskmzd lskmzd:lskmzdList){

                    List<Lshsje> lshsjeList = spAccountBalanceSearchDao.queryLshsje8(spNo1,spNo2,lskmzd.getItemNo());

                    if(lshsjeList.size()>0){
                        String itemNo1 = "t1.itemNo='" + lskmzd.getItemNo() + "'";
                        String sql, result;
                        switch (queryDataType) {
                            case "1":
                                sql = FormulaUtil.getSqlByNumObjForSpAccountBalanceSearch2(Integer.parseInt(month), "JFFS", "Lshsje", null, null, null, itemNo1, spNo1, spNo2);
                                result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;
                            case "2":
                                data.put(lskmzd.getItemNo(), lshsjeList.get(0).getSupMoney());
                                break;
                            case "3":
                                sql = FormulaUtil.getSqlByNumObjForSpAccountBalanceSearch2(Integer.parseInt(month), "BYJD", "Lshsje", null, null, null, itemNo1, spNo1, spNo2);
                                result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;

                            case "4":
                                sql = FormulaUtil.getSqlByNumObjForSpAccountBalanceSearch2(Integer.parseInt(month), "DFFS", "Lshsje", null, null, null, itemNo1, spNo1, spNo2);
                                result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;
                            case "5":
                                sql = FormulaUtil.getSqlByNumObjForSpAccountBalanceSearch2(Integer.parseInt(month), "JFLJ", "Lshsje", null, null, null, itemNo1, spNo1, spNo2);
                                result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;
                            case "6":
                                sql = FormulaUtil.getSqlByNumObjForSpAccountBalanceSearch2(Integer.parseInt(month), "BYDJ", "Lshsje", null, null, null, itemNo1, spNo1, spNo2);
                                result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;
                            case "7":
                                sql = FormulaUtil.getSqlByNumObjForSpAccountBalanceSearch2(Integer.parseInt(month)+1, "YCJF", "Lshsje", null, null, null, itemNo1, spNo1, spNo2);
                                result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;
                            case "8":
                                sql = FormulaUtil.getSqlByNumObjForSpAccountBalanceSearch2(Integer.parseInt(month), "DFLJ", "Lshsje", null, null, null, itemNo1, spNo1, spNo2);
                                result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;
                            case "9":
                                sql = FormulaUtil.getSqlByNumObjForSpAccountBalanceSearch2(Integer.parseInt(month), "BNJD", "Lshsje", null, null, null, itemNo1, spNo1, spNo2);
                                result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;
                            case "10":
                                sql = FormulaUtil.getSqlByNumObjForSpAccountBalanceSearch2(Integer.parseInt(month), "BNDJ", "Lshsje", null, null, null, itemNo1, spNo1, spNo2);
                                result = spAccountBalanceSearchDao.getFormulaResult(sql).get(0).toString();
                                data.put(lskmzd.getItemNo(), result);
                                break;
                        }
                    }else{
                        data.put(lskmzd.getItemNo(), null);
                    }

                }
                datas.add(data);
            }

        }

        //生成列对应的行数据


        Map<String,Object> result = new HashMap<String,Object>();
        result.put("rows", datas);
        result.put("total", datas.size());

        return result;
    }

    @Override
    public List<Lshszd> queryLshszd(String catNo, String spNo, String spLevel) {
        return spAccountBalanceSearchDao.queryLshszd( catNo,  spNo,  spLevel);
    }
}
