package com.bjut.ssh.serviceImpl.AssistedManagement;

import com.bjut.ssh.dao.AssistedManagement.SpAccountPageSearchDao;
import com.bjut.ssh.entity.Lskmzd;
import com.bjut.ssh.entity.Lspzk1;
import com.bjut.ssh.entity.Lspzk1VoForSearch;
import com.bjut.ssh.entity.QueryLskmzdFoSpAccountPageSearch;
import com.bjut.ssh.service.AssistedManagement.SpAccountPageSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Title: SpAccountPageSearchServiceImpl
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/9/5 9:23
 * @Version: 1.0
 */
@Service
@Transactional
public class SpAccountPageSearchServiceImpl implements SpAccountPageSearchService {

    @Autowired
    private SpAccountPageSearchDao spAccountPageSearchDao;

    @Override
    public List<Lspzk1VoForSearch> querySpAccountPage(String from, String to, String itemNo, String spCatNo1, String spNo1, String spCatNo2, String spNo2, String searchOption) {

        List<Lspzk1VoForSearch> lspzk1VoForSearches = spAccountPageSearchDao.querySpAccountPage(from, to, itemNo, spCatNo1, spNo1, spCatNo2, spNo2, searchOption);

        if( ((!itemNo.equals("....")) || (!itemNo.contains("....")) ) && (!spCatNo1.equals("...") )){
            return dealResult(lspzk1VoForSearches,from,to);
        }

        return lspzk1VoForSearches;
    }


    @Override
    public List<Lskmzd> querySpAccByCatNo(String id, String catNo1, String catNo2){
        return spAccountPageSearchDao.querySpAccByCatNo(id, catNo1, catNo2);
    }

    @Override
    public Double queryColumn(String table_name, String col_name, String condition){
        return spAccountPageSearchDao.queryColumn(table_name, col_name, condition);
    }

    @Override
    public QueryLskmzdFoSpAccountPageSearch queryLskmzdForHeadInfo(String itemNo, String spCatNo1, String spNo1,String spCatNo2, String spNo2 ) {
        return spAccountPageSearchDao.queryLskmzdForHeadInfo(itemNo, spCatNo1, spNo1,spCatNo2,spNo2);
    }


    public List<Lspzk1VoForSearch> dealResult(List<Lspzk1VoForSearch> lspzk1VoForSearches,String from,String to){

        int fromInt = Integer.parseInt(from.substring(4,6));
        int toInt = Integer.parseInt(to.substring(4,6));
        if(lspzk1VoForSearches.size() == 1){
            Lspzk1 lspzk1 = new Lspzk1();
            lspzk1.setSummary("本月合计");
            lspzk1.setPzk1AutoId(-1);
            Lspzk1VoForSearch lspzk1VoForSearch = new Lspzk1VoForSearch();
            lspzk1VoForSearch.setLspzk1(lspzk1);
            lspzk1VoForSearch.setMoneyD(0.00);
            lspzk1VoForSearch.setMoneyJ(0.00);
            lspzk1VoForSearch.setQtyJ(0.00);
            lspzk1VoForSearch.setQtyD(0.00);
            lspzk1VoForSearch.setMoney(0.00);
            lspzk1VoForSearch.setQty(0.00);

            lspzk1VoForSearches.add(1,lspzk1VoForSearch);
        }

        //本月合计：借方金额，借方数量，贷方金额，贷方数量，数量余额，金额余额
        Double moneyD = 0.00;
        Double moneyJ = 0.00;
        Double qtyD = 0.00;
        Double qtyJ = 0.00;
        Double balanceMoney = 0.00;
        Double balanceQty = 0.00;
        for(int i=1;i<lspzk1VoForSearches.size();i++){
            String month = lspzk1VoForSearches.get(i).getLspzk1().getInputDate().substring(4,6);

            int monthInt = Integer.parseInt(month);
            if(monthInt != fromInt ){
                Lspzk1 lspzk1 = new Lspzk1();
                lspzk1.setSummary("本月合计");
                lspzk1.setPzk1AutoId(-monthInt);

                Lspzk1VoForSearch lspzk1VoForSearch = new Lspzk1VoForSearch();
                lspzk1VoForSearch.setLspzk1(lspzk1);

                lspzk1VoForSearch.setMoneyD(moneyD);
                lspzk1VoForSearch.setMoneyJ(moneyJ);
                lspzk1VoForSearch.setQtyJ(qtyJ);
                lspzk1VoForSearch.setQtyD(qtyD);
                lspzk1VoForSearch.setMoney(balanceMoney);
                lspzk1VoForSearch.setQty(balanceQty);
                moneyD = 0.00; moneyJ = 0.00;
                qtyD = 0.00; qtyJ = 0.00;
                balanceMoney = 0.00; balanceQty = 0.00;

                lspzk1VoForSearches.add(i,lspzk1VoForSearch);
                fromInt +=1;
            }else{
                if(lspzk1VoForSearches.get(i).getLspzk1().getBkpDirection().equals("D")){
                    moneyD += lspzk1VoForSearches.get(i).getLspzk1().getMoney();
                    qtyD += lspzk1VoForSearches.get(i).getLspzk1().getQty();
                }else{
                    moneyJ += lspzk1VoForSearches.get(i).getLspzk1().getMoney();
                    qtyJ += lspzk1VoForSearches.get(i).getLspzk1().getQty();
                }
            }
            balanceMoney = lspzk1VoForSearches.get(i).getMoney();
            balanceQty = lspzk1VoForSearches.get(i).getQty();
        }

        Lspzk1 lspzk1 = new Lspzk1();
        lspzk1.setSummary("本月合计");
        lspzk1.setPzk1AutoId(-toInt);
        Lspzk1VoForSearch lspzk1VoForSearch = new Lspzk1VoForSearch();
        lspzk1VoForSearch.setLspzk1(lspzk1);
        lspzk1VoForSearch.setMoneyD(moneyD);
        lspzk1VoForSearch.setMoneyJ(moneyJ);
        lspzk1VoForSearch.setQtyJ(qtyJ);
        lspzk1VoForSearch.setQtyD(qtyD);
        lspzk1VoForSearch.setMoney(balanceMoney);
        lspzk1VoForSearch.setQty(balanceQty);

        lspzk1VoForSearches.add(lspzk1VoForSearch);

        return lspzk1VoForSearches;
    }
}
