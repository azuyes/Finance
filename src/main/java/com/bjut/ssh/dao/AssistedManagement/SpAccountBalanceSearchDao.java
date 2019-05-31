package com.bjut.ssh.dao.AssistedManagement;

import com.bjut.ssh.entity.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @Title: SpAccountBalanceSearchDao
 * @Description: TODO
 * @Author: lz
 * @CreateDate: 2018/9/5 9:49
 * @Version: 1.0
 */
@Repository
@Transactional
public class SpAccountBalanceSearchDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    public void close(Session session){
        if(session != null)
            session.close();
    }


    public List<SpAccountBalanceQueryVo> querySpAccountBalance1(String year, String month, String itemNo, String spCatNo, String spNo,String spLevel, String searchAccType, String searchOption) {
        Session session = null;
        Transaction tx = null;
        List<SpAccountBalanceQueryVo> spAccountBalanceQueryVos = new ArrayList<SpAccountBalanceQueryVo>();

        try {
            session = getSession();
            tx = session.beginTransaction();

            //财务日期
            //Lsconf begin = (Lsconf) session.get(Lsconf.class, "current_date");
            //result.add("current_date", begin.getConfValue());

            //

            Lskmzd lskmzd = (Lskmzd)session.get(Lskmzd.class,itemNo);

            String catNo = lskmzd.getSupAcc1();

            if(spLevel.equals("0")){

                //查询lshsjeList
                Query query;
                query = session.createQuery("from Lshsje je where je.itemNo = ?");
                query.setParameter(0,itemNo);
                List<Lshsje> lshsjeList = query.list();
                Lshszd lshszd = new Lshszd();

                int i = 0;
                for(Lshsje lshsje : lshsjeList){

                    SpAccountBalanceQueryVo spAccountBalanceQueryVo = new SpAccountBalanceQueryVo();

                    query = session.createQuery("from Lshszd l where l.spNo = ? and l.catNo = ?");
                    query.setParameter(0,lshsje.getSpNo1());
                    query.setParameter(1,catNo);
                    List<Lshszd> lshszds = query.list();
                    if(lshszds.size()>0) {
                        lshszd = lshszds.get(0);
                    }

                    if(searchAccType.equals("S")){
                        query = session.createQuery("from Lshssl as sl where sl.itemNo = ? and sl.spNo1 = ?");
                        query.setParameter(0,itemNo);
                        query.setParameter(1,lshsje.getSpNo1());
                        List<Lshssl> lshsslList = query.list();

                        if(lshsslList.size()>0) {
                            Lshssl lshssl = lshsslList.get(0);
                            spAccountBalanceQueryVo.setLshssl(lshssl);
                        }else{
                            spAccountBalanceQueryVo.setLshssl(null);
                        }
                    }else{
                        spAccountBalanceQueryVo.setLshssl(null);
                    }

                    spAccountBalanceQueryVo.setLskmzd(lskmzd);
                    spAccountBalanceQueryVo.setLshsje(lshsje);
                    spAccountBalanceQueryVo.setLshszd(lshszd);

                    spAccountBalanceQueryVos.add(i,spAccountBalanceQueryVo);
                    i = i+1;

                }
            }else if(spLevel.equals("1")){

                //查询lshsjeList
                Query query;
                query = session.createQuery("from Lshsje je where je.itemNo = ?");
                query.setParameter(0,itemNo);
                List<Lshsje> lshsjeList = query.list();
                Lshszd lshszd = new Lshszd();

                int i = 0;
                for(Lshsje lshsje : lshsjeList){

                    SpAccountBalanceQueryVo spAccountBalanceQueryVo = new SpAccountBalanceQueryVo();

                    query = session.createQuery("from Lshszd l where l.spNo = ? and l.catNo = ?");
                    query.setParameter(0,lshsje.getSpNo1());
                    query.setParameter(1,catNo);
                    List<Lshszd> lshszds = query.list();
                    if(lshszds.size()>0) {
                        lshszd = lshszds.get(0);
                    }

                    if(lshszd.getSpLevel().equals("1")){   //查询核算对象为一级的数据
                        if(searchAccType.equals("S")){
                            query = session.createQuery("from Lshssl as sl where sl.itemNo = ? and sl.spNo1 = ?");
                            query.setParameter(0,itemNo);
                            query.setParameter(1,lshsje.getSpNo1());
                            List<Lshssl> lshsslList = query.list();

                            if(lshsslList.size()>0) {
                                Lshssl lshssl = lshsslList.get(0);
                                spAccountBalanceQueryVo.setLshssl(lshssl);
                            }else{
                                spAccountBalanceQueryVo.setLshssl(null);
                            }
                        }else{
                            spAccountBalanceQueryVo.setLshssl(null);
                        }

                        spAccountBalanceQueryVo.setLskmzd(lskmzd);
                        spAccountBalanceQueryVo.setLshsje(lshsje);
                        spAccountBalanceQueryVo.setLshszd(lshszd);

                        spAccountBalanceQueryVos.add(i,spAccountBalanceQueryVo);
                        i = i+1;
                    }

                }
            }else{

                //查询lshsjeList
                Query query;
                query = session.createQuery("from Lshsje je where je.itemNo = '"+itemNo+"' and je.spNo1 like '"+spNo+"%'");
                List<Lshsje> lshsjeList = query.list();
                Lshszd lshszd = new Lshszd();

                int i = 0;
                for(Lshsje lshsje : lshsjeList){

                    SpAccountBalanceQueryVo spAccountBalanceQueryVo = new SpAccountBalanceQueryVo();

                    query = session.createQuery("from Lshszd l where l.spNo = ? and l.catNo = ?");
                    query.setParameter(0,lshsje.getSpNo1());
                    query.setParameter(1,catNo);
                    List<Lshszd> lshszds = query.list();
                    if(lshszds.size()>0) {
                        lshszd = lshszds.get(0);
                    }

                    if(lshszd.getSpLevel().equals(spLevel)){   //查询核算对象为当前级数的数据
                        if(searchAccType.equals("S")){
                            query = session.createQuery("from Lshssl as sl where sl.itemNo = ? and sl.spNo1 = ?");
                            query.setParameter(0,itemNo);
                            query.setParameter(1,lshsje.getSpNo1());
                            List<Lshssl> lshsslList = query.list();

                            if(lshsslList.size()>0) {
                                Lshssl lshssl = lshsslList.get(0);
                                spAccountBalanceQueryVo.setLshssl(lshssl);
                            }else{
                                spAccountBalanceQueryVo.setLshssl(null);
                            }
                        }else{
                            spAccountBalanceQueryVo.setLshssl(null);
                        }

                        spAccountBalanceQueryVo.setLskmzd(lskmzd);
                        spAccountBalanceQueryVo.setLshsje(lshsje);
                        spAccountBalanceQueryVo.setLshszd(lshszd);

                        spAccountBalanceQueryVos.add(i,spAccountBalanceQueryVo);
                        i = i+1;
                    }
                }
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return spAccountBalanceQueryVos;
        }
    }



    public List<SpAccountBalanceQueryVo> querySpInfoByLevel(String year, String month, String itemNo, String spCatNo, String spNo, String spLevel,String searchAccType, String searchOption) {
        Session session = null;
        Transaction tx = null;
        List<SpAccountBalanceQueryVo> spAccountBalanceQueryVos = new ArrayList<SpAccountBalanceQueryVo>();

        try {
            session = getSession();
            tx = session.beginTransaction();

            //财务日期
            //Lsconf begin = (Lsconf) session.get(Lsconf.class, "current_date");
            //result.add("current_date", begin.getConfValue());

            //
            Lskmzd lskmzd = (Lskmzd)session.get(Lskmzd.class,itemNo);

            String catNo = lskmzd.getSupAcc1();

            //查询lshsjeList
            Query query;
            query = session.createQuery("from Lshsje je where je.itemNo = ?");
            query.setParameter(0,itemNo);
            List<Lshsje> lshsjeList = query.list();
            Lshszd lshszd = new Lshszd();

            int i = 0;
            for(Lshsje lshsje : lshsjeList){

                SpAccountBalanceQueryVo spAccountBalanceQueryVo = new SpAccountBalanceQueryVo();

                query = session.createQuery("from Lshszd l where l.spNo = ? and l.catNo = ?");
                query.setParameter(0,lshsje.getSpNo1());
                query.setParameter(1,catNo);
                List<Lshszd> lshszds = query.list();
                if(lshszds.size()>0) {
                    lshszd = lshszds.get(0);
                }

                if(searchAccType.equals("S")){
                    query = session.createQuery("from Lshssl as sl where sl.itemNo = ? and sl.spNo1 = ?");
                    query.setParameter(0,itemNo);
                    query.setParameter(1,lshsje.getSpNo1());
                    List<Lshssl> lshsslList = query.list();

                    if(lshsslList.size()>0) {
                        Lshssl lshssl = lshsslList.get(0);
                        spAccountBalanceQueryVo.setLshssl(lshssl);
                    }else{
                        spAccountBalanceQueryVo.setLshssl(null);
                    }
                }else{
                    spAccountBalanceQueryVo.setLshssl(null);
                }

                spAccountBalanceQueryVo.setLskmzd(lskmzd);
                spAccountBalanceQueryVo.setLshsje(lshsje);
                spAccountBalanceQueryVo.setLshszd(lshszd);

                spAccountBalanceQueryVos.add(i,spAccountBalanceQueryVo);
                i = i+1;

            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return spAccountBalanceQueryVos;
        }
    }

    public List<SpAccountBalanceQueryVo> querySpAccountBalance2(String year, String month, String itemNo, String item,String spCatNo, String spNo, String searchAccType, String searchOption) {
        Session session = null;
        Transaction tx = null;
        List<SpAccountBalanceQueryVo> spAccountBalanceQueryVos = new ArrayList<SpAccountBalanceQueryVo>();

        try {
            session = getSession();
            tx = session.beginTransaction();

            //财务日期
            //Lsconf begin = (Lsconf) session.get(Lsconf.class, "current_date");
            //result.add("current_date", begin.getConfValue());

            if(item.equals("0") || item.equals("1")){//查询全显时的数据
                //查询lshsjeList
                Query query;
                query = session.createQuery("from Lshsje je where je.spNo1 = '"+spNo+"' and je.spNo2 is null");
                List<Lshsje> lshsjeList = query.list();

                Lshszd lshszd = new Lshszd();
                Lskmzd lskmzd = new Lskmzd();
                List<Lshszd> lshszds = new ArrayList<>();
                boolean flag = false;

                int i = 0;
                for(Lshsje lshsje : lshsjeList){

                    itemNo = lshsje.getItemNo();
                    lskmzd = (Lskmzd)session.get(Lskmzd.class,itemNo);
                    if(item.equals("0")){
                        if(lskmzd.getSupAcc1().equals(spCatNo) ){
                            flag = true;
                        }else{
                            flag = false;
                            continue;
                        }
                    } else if(item.equals("1")){
                        if(lskmzd.getSupAcc1().equals(spCatNo) && lskmzd.getItem().equals("1")){
                            flag = true;
                        }else{
                            flag = false;
                            continue;
                        }
                    }


                    if(flag){
                        SpAccountBalanceQueryVo spAccountBalanceQueryVo = new SpAccountBalanceQueryVo();
                        query = session.createQuery("from Lshszd l where l.spNo = ? and l.catNo = ?");
                        query.setParameter(0,lshsje.getSpNo1());
                        query.setParameter(1,spCatNo);
                        lshszds = query.list();
                        if(lshszds.size()>0) {
                            lshszd = lshszds.get(0);
                        }
                        if(searchAccType.equals("S")){
                            query = session.createQuery("from Lshssl as sl where sl.itemNo = ? and sl.spNo1 = ?");
                            query.setParameter(0,itemNo);
                            query.setParameter(1,lshsje.getSpNo1());
                            List<Lshssl> lshsslList = query.list();

                            if(lshsslList.size()>0) {
                                Lshssl lshssl = lshsslList.get(0);
                                spAccountBalanceQueryVo.setLshssl(lshssl);
                            }else{
                                spAccountBalanceQueryVo.setLshssl(null);
                            }
                        }else{
                            spAccountBalanceQueryVo.setLshssl(null);
                        }

                        spAccountBalanceQueryVo.setLskmzd(lskmzd);
                        spAccountBalanceQueryVo.setLshsje(lshsje);
                        spAccountBalanceQueryVo.setLshszd(lshszd);

                        spAccountBalanceQueryVos.add(i,spAccountBalanceQueryVo);
                        i = i+1;
                    }
                }
            }else{
                //查询lshsjeList
                Query query;
                query = session.createQuery("from Lshsje je where je.spNo1 = '"+spNo+"' and je.spNo2 is null and je.itemNo like '"+itemNo+"%'");
                List<Lshsje> lshsjeList = query.list();

                Lshszd lshszd = new Lshszd();
                Lskmzd lskmzd = new Lskmzd();
                List<Lshszd> lshszds = new ArrayList<>();
                boolean flag = false;

                int i = 0;
                for(Lshsje lshsje : lshsjeList){

                    itemNo = lshsje.getItemNo();
                    lskmzd = (Lskmzd)session.get(Lskmzd.class,itemNo);
                    if(item.equals(lskmzd.getItem())){
                        if(lskmzd.getSupAcc1().equals(spCatNo) ){
                            flag = true;
                        }else{
                            flag = false;
                            continue;
                        }
                    }
                    if(flag){
                        SpAccountBalanceQueryVo spAccountBalanceQueryVo = new SpAccountBalanceQueryVo();
                        query = session.createQuery("from Lshszd l where l.spNo = ? and l.catNo = ?");
                        query.setParameter(0,lshsje.getSpNo1());
                        query.setParameter(1,spCatNo);
                        lshszds = query.list();
                        if(lshszds.size()>0) {
                            lshszd = lshszds.get(0);
                        }
                        if(searchAccType.equals("S")){
                            query = session.createQuery("from Lshssl as sl where sl.itemNo = ? and sl.spNo1 = ?");
                            query.setParameter(0,itemNo);
                            query.setParameter(1,lshsje.getSpNo1());
                            List<Lshssl> lshsslList = query.list();

                            if(lshsslList.size()>0) {
                                Lshssl lshssl = lshsslList.get(0);
                                spAccountBalanceQueryVo.setLshssl(lshssl);
                            }else{
                                spAccountBalanceQueryVo.setLshssl(null);
                            }
                        }else{
                            spAccountBalanceQueryVo.setLshssl(null);
                        }

                        spAccountBalanceQueryVo.setLskmzd(lskmzd);
                        spAccountBalanceQueryVo.setLshsje(lshsje);
                        spAccountBalanceQueryVo.setLshszd(lshszd);

                        spAccountBalanceQueryVos.add(i,spAccountBalanceQueryVo);
                        i = i+1;
                    }
                }
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return spAccountBalanceQueryVos;
        }
    }

    //获取科目结构
    public String getSubjectStructure() {
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();

            Lsconf lsconf = (Lsconf) session.get(Lsconf.class, "sub_stru");

            tx.commit();
            close(session);
            return lsconf.getConfValue();
        }
        catch(Exception e){
            tx.rollback();
            e.printStackTrace();
            return "0";
        }
    }

    public List<Lskmzd> querySpAccountItem3(String catNo,String itemNo) {
        Session session = null;
        Transaction tx = null;
        List<Lskmzd> lskmzdList = new ArrayList<Lskmzd>();

        try {
            session = getSession();
            tx = session.beginTransaction();

            String subStru= getSubjectStructure();

            int index = itemNo.indexOf(".");

            String itemNoSub = itemNo.substring(0,index);

            int level = 1;
            int len = 0;
            int i = 0;
            while(level<7){
                char ch = subStru.charAt(i);
                len = len + (int)ch - 48;
                if(itemNoSub.length() == len){
                    ++level;
                    break;
                }
                level++;
                i++;
            }

            Query query = session.createQuery("from Lskmzd where supAcc1 = '"+catNo+"' and supAcc2 is null and item = '"+level+"' and itemNo like '"+itemNoSub+"%'");
            lskmzdList = query.list();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lskmzdList;
        }
    }


    /**
    *@author lz
    *@Description 根据分类编号获取科目字典中的科目
    *@Date 2018/9/6 15:45
    *@Param [catNo] 分类编号
    *@return java.util.List<com.bjut.ssh.entity.Lskmzd>
    **/
    public List<Lskmzd> querySpAccountItem(String catNo) {
        Session session = null;
        Transaction tx = null;
        List<Lskmzd> lskmzdList = new ArrayList<Lskmzd>();

        try {
            session = getSession();
            tx = session.beginTransaction();
            String level = "1";
            Query query = session.createQuery("from Lskmzd where supAcc1 = '"+catNo+"' and supAcc2 is null and item = '"+level+"'");
            lskmzdList = query.list();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lskmzdList;
        }
    }


    public List<Lshszd> queryLshszdByCatNo8(String catNo) {
        Session session = null;
        Transaction tx = null;
        List<Lshszd> lshszdList = new ArrayList<Lshszd>();

        try {
            session = getSession();
            tx = session.beginTransaction();
            byte finLevel = 1;
            Query query = session.createQuery("from Lshszd where catNo = ? and finLevel = ? ");
            query.setParameter(0,catNo);
            query.setParameter(1,finLevel);

            lshszdList = query.list();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lshszdList;
        }
    }


    /**
    *@author lz
    *@Description 根据分类编号获取对应的核算对象
    *@Date 2018/9/6 15:43
    *@Param [catNo] 分类编号
    *@return java.util.List<com.bjut.ssh.entity.Lshszd>
    **/
    public List<Lshszd> queryLshszdByCatNo(String catNo,String spNo,String spLevel) {
        Session session = null;
        Transaction tx = null;
        List<Lshszd> lshszdList = new ArrayList<Lshszd>();

        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query;

            if(spLevel.equals("0")){
                query = session.createQuery("from Lshszd where catNo = '"+catNo+"' ");
            }else if(spLevel.equals("1")){
                query = session.createQuery("from Lshszd where catNo = ? and spLevel = ? ");
                query.setParameter(0,catNo);
                query.setParameter(1,"1");
            }else{
                query = session.createQuery("from Lshszd where catNo = '"+catNo+"' and spLevel = '"+spLevel+"' and spNo like '"+spNo+"%' ");
            }
            lshszdList = query.list();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lshszdList;
        }
    }

    /**
    *@author lz
    *@Description 根据科目编号，核算对象1编号，核算对象2编号为空，查询符合条件的核算金额数据
    *@Date 2018/9/6 15:55
    *@Param [spNo 核算对象1, itemNo 科目编号]
    *@return java.util.List<com.bjut.ssh.entity.Lshsje>
    **/
    public List<Lshsje> queryLshsje(String spNo, String itemNo) {
        Session session = null;
        Transaction tx = null;
        List<Lshsje> lshsjeList = new ArrayList<Lshsje>();

        try {
            session = getSession();
            tx = session.beginTransaction();

            Query query = session.createQuery("from Lshsje je where je.spNo1 = '"+spNo+"' and je.itemNo = '"+itemNo+"' and je.spNo2 is null ");
            lshsjeList = query.list();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lshsjeList;
        }
    }


    public List<Object> getFormulaResult(String hql){
        List<Object> result = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery(hql);
            result= query.list();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            if(result!=null && result.size()>0)
                return result;
            else
                return null;
        }
    }

    /**   *@author lz
    *@Description 交叉查询，第五种查询情况。
    *@Date 2018/9/11 16:08
    *@Param [year, month, itemNo, catNo1, spNo1, catNo2, spNo2, searchAccType, searchOption]
    *@return java.util.List<com.bjut.ssh.entity.SpAccountBalanceQueryVo2>
    **/
    public List<SpAccountBalanceQueryVo2> querySpAccountBalance5(String year, String month, String itemNo, String catNo1, String spNo1,String catNo2,String spNo2, String searchAccType, String searchOption) {
        Session session = null;
        Transaction tx = null;
        List<SpAccountBalanceQueryVo2> spAccountBalanceQueryVo2s = new ArrayList<SpAccountBalanceQueryVo2>();

        try {
            session = getSession();
            tx = session.beginTransaction();

            //财务日期
            //Lsconf begin = (Lsconf) session.get(Lsconf.class, "current_date");
            //result.add("current_date", begin.getConfValue());

            //
            Lskmzd lskmzd = (Lskmzd)session.get(Lskmzd.class,itemNo);

            catNo1 = lskmzd.getSupAcc1();
            catNo2 = lskmzd.getSupAcc2();

            //查询lshsjeList
            Query query;
            query = session.createQuery("from Lshsje je where je.itemNo = ?");
            query.setParameter(0,itemNo);
            List<Lshsje> lshsjeList = query.list();

            Lshszd lshszd1 = new Lshszd();
            Lshszd lshszd2 = new Lshszd();
            List<Lshszd> lshszds = new ArrayList<>();

            int i = 0;
            for(Lshsje lshsje : lshsjeList){

                SpAccountBalanceQueryVo2 spAccountBalanceQueryVo2 = new SpAccountBalanceQueryVo2();

                query = session.createQuery("from Lshszd l where l.spNo = ? and l.catNo = ?");
                query.setParameter(0,lshsje.getSpNo1());
                query.setParameter(1,catNo1);
                lshszds = query.list();
                if(lshszds.size()>0) {
                    lshszd1 = lshszds.get(0);
//                    if(lshszd1.getFinLevel() == 0){
//                        continue;
//                    }
                }

                query = session.createQuery("from Lshszd l where l.spNo = ? and l.catNo = ?");
                query.setParameter(0,lshsje.getSpNo1());
                query.setParameter(1,catNo2);
                lshszds = query.list();
                if(lshszds.size()>0) {
                    lshszd2 = lshszds.get(0);
//                    if(lshszd2.getFinLevel() == 0){
//                        continue;
//                    }
                }

                if(searchAccType.equals("S")){
                    query = session.createQuery("from Lshssl as sl where sl.itemNo = ? and sl.spNo1 = ? and sl.spNo2 = ?");
                    query.setParameter(0,itemNo);
                    query.setParameter(1,lshsje.getSpNo1());
                    query.setParameter(2,lshsje.getSpNo2());
                    List<Lshssl> lshsslList = query.list();

                    if(lshsslList.size()>0) {
                        Lshssl lshssl = lshsslList.get(0);
                        spAccountBalanceQueryVo2.setLshssl(lshssl);
                    }else{
                        spAccountBalanceQueryVo2.setLshssl(null);
                    }
                }else{
                    spAccountBalanceQueryVo2.setLshssl(null);
                }

                spAccountBalanceQueryVo2.setLskmzd(lskmzd);
                spAccountBalanceQueryVo2.setLshsje(lshsje);
                spAccountBalanceQueryVo2.setLshszd1(lshszd1);
                spAccountBalanceQueryVo2.setLshszd2(lshszd2);

                spAccountBalanceQueryVo2s.add(i,spAccountBalanceQueryVo2);
                i = i+1;

            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return spAccountBalanceQueryVo2s;
        }
    }

    /**
    *@author lz
    *@Description 交叉查询，第六种查询情况
    *@Date 2018/9/11 16:07
    *@Param [year, month, itemNo, catNo1, spNo1, catNo2, spNo2, searchAccType, searchOption]
    *@return java.util.List<com.bjut.ssh.entity.SpAccountBalanceQueryVo2>
    **/
    public List<SpAccountBalanceQueryVo2> querySpAccountBalance6(String year, String month, String itemNo,String item, String catNo1, String spNo1,String catNo2,String spNo2,  String searchAccType, String searchOption) {
        Session session = null;
        Transaction tx = null;
        List<SpAccountBalanceQueryVo2> spAccountBalanceQueryVo2s = new ArrayList<SpAccountBalanceQueryVo2>();
        try {
            session = getSession();
            tx = session.beginTransaction();

            //财务日期
            //Lsconf begin = (Lsconf) session.get(Lsconf.class, "current_date");
            //result.add("current_date", begin.getConfValue());

            //
            if(item.equals("0") || item.equals("1")){//查询全显时的数据
                //查询lshsjeList
                Query query;
                query = session.createQuery("from Lshsje je where je.spNo1 = '"+spNo1+"' and je.spNo2 ='"+spNo2+"'");
                List<Lshsje> lshsjeList = query.list();

                Lshszd lshszd1 = new Lshszd();
                Lshszd lshszd2 = new Lshszd();
                Lskmzd lskmzd = new Lskmzd();
                List<Lshszd> lshszds = new ArrayList<>();
                boolean flag = false;

                int i = 0;
                for(Lshsje lshsje : lshsjeList){

                    itemNo = lshsje.getItemNo();
                    lskmzd = (Lskmzd)session.get(Lskmzd.class,itemNo);
                    if(item.equals("0")){
                        if(lskmzd.getSupAcc1().equals(catNo1) && lskmzd.getSupAcc2().equals(catNo2) ){
                            flag = true;
                        }else{
                            flag = false;
                            continue;
                        }
                    } else if(item.equals("1")){
                        if(lskmzd.getSupAcc1().equals(catNo1) && lskmzd.getSupAcc2().equals(catNo2) && lskmzd.getItem().equals("1")){
                            flag = true;
                        }else{
                            flag = false;
                            continue;
                        }
                    }

                    if(flag){

                        SpAccountBalanceQueryVo2 spAccountBalanceQueryVo2 = new SpAccountBalanceQueryVo2();

                        query = session.createQuery("from Lshszd l where l.spNo = ? and l.catNo = ?");
                        query.setParameter(0,lshsje.getSpNo1());
                        query.setParameter(1,catNo1);
                        lshszds = query.list();
                        if(lshszds.size()>0) {
                            lshszd1 = lshszds.get(0);
                        }

                        query = session.createQuery("from Lshszd l where l.spNo = ? and l.catNo = ?");
                        query.setParameter(0,lshsje.getSpNo1());
                        query.setParameter(1,catNo2);
                        lshszds = query.list();
                        if(lshszds.size()>0) {
                            lshszd2 = lshszds.get(0);
                        }

                        if(searchAccType.equals("S")){
                            query = session.createQuery("from Lshssl as sl where sl.itemNo = ? and sl.spNo1 = ? and sl.spNo2 = ?");
                            query.setParameter(0,itemNo);
                            query.setParameter(1,lshsje.getSpNo1());
                            query.setParameter(2,lshsje.getSpNo2());
                            List<Lshssl> lshsslList = query.list();

                            if(lshsslList.size()>0) {
                                Lshssl lshssl = lshsslList.get(0);
                                spAccountBalanceQueryVo2.setLshssl(lshssl);
                            }else{
                                spAccountBalanceQueryVo2.setLshssl(null);
                            }
                        }else{
                            spAccountBalanceQueryVo2.setLshssl(null);
                        }

                        spAccountBalanceQueryVo2.setLskmzd(lskmzd);
                        spAccountBalanceQueryVo2.setLshsje(lshsje);
                        spAccountBalanceQueryVo2.setLshszd1(lshszd1);
                        spAccountBalanceQueryVo2.setLshszd2(lshszd2);

                        spAccountBalanceQueryVo2s.add(i,spAccountBalanceQueryVo2);
                        i = i+1;
                    }
                }
            }else{
                //查询lshsjeList
                Query query;
                query = session.createQuery("from Lshsje je where je.spNo1 = '"+spNo1+"' and je.spNo2 = '"+spNo2+"' and je.itemNo like '"+itemNo+"%'");
                List<Lshsje> lshsjeList = query.list();

                Lshszd lshszd1 = new Lshszd();
                Lshszd lshszd2 = new Lshszd();
                Lskmzd lskmzd = new Lskmzd();
                List<Lshszd> lshszds = new ArrayList<>();
                boolean flag = false;

                int i = 0;
                for(Lshsje lshsje : lshsjeList){

                    itemNo = lshsje.getItemNo();
                    lskmzd = (Lskmzd)session.get(Lskmzd.class,itemNo);
                    if(item.equals(lskmzd.getItem())){
                        if(lskmzd.getSupAcc1().equals(catNo1) && lskmzd.getSupAcc2().equals(catNo2)){
                            flag = true;
                        }else{
                            flag = false;
                            continue;
                        }
                    }
                    if(flag){

                        SpAccountBalanceQueryVo2 spAccountBalanceQueryVo2 = new SpAccountBalanceQueryVo2();

                        query = session.createQuery("from Lshszd l where l.spNo = ? and l.catNo = ?");
                        query.setParameter(0,lshsje.getSpNo1());
                        query.setParameter(1,catNo1);
                        lshszds = query.list();
                        if(lshszds.size()>0) {
                            lshszd1 = lshszds.get(0);
                        }

                        query = session.createQuery("from Lshszd l where l.spNo = ? and l.catNo = ?");
                        query.setParameter(0,lshsje.getSpNo1());
                        query.setParameter(1,catNo2);
                        lshszds = query.list();
                        if(lshszds.size()>0) {
                            lshszd2 = lshszds.get(0);
                        }

                        if(searchAccType.equals("S")){
                            query = session.createQuery("from Lshssl as sl where sl.itemNo = ? and sl.spNo1 = ? and sl.spNo2 = ?");
                            query.setParameter(0,itemNo);
                            query.setParameter(1,lshsje.getSpNo1());
                            query.setParameter(2,lshsje.getSpNo2());
                            List<Lshssl> lshsslList = query.list();

                            if(lshsslList.size()>0) {
                                Lshssl lshssl = lshsslList.get(0);
                                spAccountBalanceQueryVo2.setLshssl(lshssl);
                            }else{
                                spAccountBalanceQueryVo2.setLshssl(null);
                            }
                        }else{
                            spAccountBalanceQueryVo2.setLshssl(null);
                        }

                        spAccountBalanceQueryVo2.setLskmzd(lskmzd);
                        spAccountBalanceQueryVo2.setLshsje(lshsje);
                        spAccountBalanceQueryVo2.setLshszd1(lshszd1);
                        spAccountBalanceQueryVo2.setLshszd2(lshszd2);

                        spAccountBalanceQueryVo2s.add(i,spAccountBalanceQueryVo2);
                        i = i+1;
                    }
                }
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return spAccountBalanceQueryVo2s;
        }


    }

    public List<Lskmzd> querySpAccountItem7(String catNo1,String catNo2,String itemNo) {
        Session session = null;
        Transaction tx = null;
        List<Lskmzd> lskmzdList = new ArrayList<Lskmzd>();

        try {
            session = getSession();
            tx = session.beginTransaction();

            String subStru= getSubjectStructure();

            int index = itemNo.indexOf(".");

            String itemNoSub = itemNo.substring(0,index);

            int level = 1;
            int len = 0;
            int i = 0;
            while(level<7){
                char ch = subStru.charAt(i);
                len = len + (int)ch - 48;
                if(itemNoSub.length() == len){
                    ++level;
                    break;
                }
                level++;
                i++;
            }

            Query query = session.createQuery("from Lskmzd where supAcc1 = '"+catNo1+"' and supAcc2 = '"+catNo2+"' and item = '"+level+"' and itemNo like '"+itemNoSub+"%'");
            lskmzdList = query.list();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lskmzdList;
        }
    }


    public List<Lskmzd> querySpAccountItem8(String catNo1,String catNo2) {
        Session session = null;
        Transaction tx = null;
        List<Lskmzd> lskmzdList = new ArrayList<Lskmzd>();

        try {
            session = getSession();
            tx = session.beginTransaction();

            Query query = session.createQuery("from Lskmzd where supAcc1 = ? and supAcc2 =? and item = ? ");
            query.setParameter(0,catNo1);
            query.setParameter(1,catNo2);
            query.setParameter(2,"1");
            lskmzdList = query.list();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lskmzdList;
        }
    }

    public List<Lshsje> queryLshsje8(String spNo1,String spNo2, String itemNo) {
        Session session = null;
        Transaction tx = null;
        List<Lshsje> lshsjeList = new ArrayList<Lshsje>();

        try {
            session = getSession();
            tx = session.beginTransaction();

            Query query = session.createQuery("from Lshsje je where je.spNo1 = '"+spNo1+"' and je.itemNo = '"+itemNo+"' and je.spNo2  = '"+spNo2+"'");
            lshsjeList = query.list();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lshsjeList;
        }
    }

    public List<Lshszd> queryLshszd(String catNo, String spNo, String spLevel) {
        Session session = null;
        Transaction tx = null;
        List<Lshszd> Lshszds = new ArrayList<Lshszd>();

        try {
            session = getSession();
            tx = session.beginTransaction();

            Query query ;
            if(spLevel.equals("1")){
                query = session.createQuery("from Lshszd where catNo = ? and spLevel = ?");
                query.setParameter(0,catNo);
                query.setParameter(1,spLevel);
            }else{
                query = session.createQuery("from Lshszd where catNo = '"+catNo+"' and spLevel = '"+spLevel+"' and spNo like '"+spNo+"%'");
            }
            Lshszds = query.list();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return Lshszds;
        }
    }


}
