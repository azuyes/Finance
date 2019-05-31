package com.bjut.ssh.dao.FinanceProcess;

import com.bjut.ssh.entity.*;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Title: AccountingVouchersDao
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/5/4 11:59
 * @Version: 1.0
 */
@Repository
@Transactional
public class AccountingVouchersDao {
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    public void close(Session session){
        if(session != null)
            session.close();
    }

    public Msg saveVoucher(List<Lspzk1QueryVo> lspzk1QueryVoList){
        Session session = null;
        Transaction tx = null;
        try{
            session = getSession();
            tx = session.beginTransaction();

            String inputDate = lspzk1QueryVoList.get(0).getInputDate();
            String voucherNo = lspzk1QueryVoList.get(0).getVoucherNo();
            //更新当前财务日期
            //updateCurrentDate(inputDate);

            int auto_id = 0;

            //先删除当前凭证内容，以保存新修改的凭证内容
            String del_lspzk1_hql = "from Lspzk1 as s where s.voucherNo = '" + voucherNo + "' and s.inputDate like '" + inputDate.substring(0,6) + "%'";
            Query lspzk1_query = session.createQuery(del_lspzk1_hql);
            List<Lspzk1> dellspzk1List= lspzk1_query.list();
            for(Lspzk1 dellspzk1 : dellspzk1List){
                session.delete(dellspzk1);

                String lsyspz_hql = "from Lsyspz as s where s.voucherNo = '" + dellspzk1.getVoucherNo() + "' and s.inputDate = '" + dellspzk1.getInputDate() + "' and s.entryNo = '" + dellspzk1.getEntryNo() + "'";
                Query orignal_query = session.createQuery(lsyspz_hql);
                List<Lsyspz> lsyspzList= orignal_query.list();
                for(Lsyspz dellsyspz : lsyspzList){
                    session.delete(dellsyspz);
                }

                String lshspz_hql = "from Lshspz as s where s.voucherNo = '" + dellspzk1.getVoucherNo() + "' and s.inputDate = '" + dellspzk1.getInputDate() + "' and s.entryNo = '" + dellspzk1.getEntryNo() + "'";
                Query sp_query = session.createQuery(lshspz_hql);
                List<Lshspz> lshspzList= sp_query.list();
                for(Lshspz dellshspz : lshspzList){
                    session.delete(dellshspz);
                }
            }

            tx.commit();
            close(session);

            for(Lspzk1QueryVo lspzk1QueryVo : lspzk1QueryVoList) {

                session = getSession();
                tx = session.beginTransaction();

                Query query = session.createQuery("from Lspzk1 where inputDate = '" + lspzk1QueryVo.getInputDate() + "'and voucherNo = '" + lspzk1QueryVo.getVoucherNo() + "'and entryNo = '" + lspzk1QueryVo.getEntryNo() + "'and itemNo = '" + lspzk1QueryVo.getItemNo() + "'");
                List<Lspzk1> lspzk1List = query.list();
                if(lspzk1List.size() == 0){
                    //设置id不能重复
                    Query last_query = session.createQuery("from Lspzk1 where pzk1AutoId = (select max(pzk1AutoId) from Lspzk1)");
                    Lspzk1 last = (Lspzk1) last_query.uniqueResult();
                    auto_id = last == null ? 1 : last.getPzk1AutoId() + 1;
                }
                else {
                    for (Lspzk1 editLspzk11 : lspzk1List) {
                        auto_id = editLspzk11.getPzk1AutoId();
                        session.delete(editLspzk11);
                    }
                }

                //保存凭证库
                Lspzk1 lspzk1 = new Lspzk1();
                if(auto_id != 0) lspzk1.setPzk1AutoId(auto_id);
                lspzk1.setVoucherNo(lspzk1QueryVo.getVoucherNo());
                lspzk1.setItemNo(lspzk1QueryVo.getItemNo());
                lspzk1.setEntryNo(lspzk1QueryVo.getEntryNo());
                lspzk1.setInputDate(lspzk1QueryVo.getInputDate());
                lspzk1.setAcsDocCnt(lspzk1QueryVo.getAcsDocCnt());
                lspzk1.setVoucherType(lspzk1QueryVo.getVoucherType());
                lspzk1.setSummary(lspzk1QueryVo.getSummary());
                lspzk1.setBkpDirection(lspzk1QueryVo.getBkpDirection());
                lspzk1.setPreActDoc(lspzk1QueryVo.getPreActDoc());
                lspzk1.setPreActDoc(lspzk1QueryVo.getPreActDoc());
                lspzk1.setPreActNo(lspzk1QueryVo.getPreActNo());
                lspzk1.setQty(lspzk1QueryVo.getQty());
                lspzk1.setMoney(lspzk1QueryVo.getMoney());
                session.save(lspzk1);

                tx.commit();
                close(session);

                //auto_id = lspzk1.getPzk1AutoId() + 1;//获取当前保存数据的自增id

                //保存原始凭证
                if(lspzk1QueryVo.getSupAcc1()== null || lspzk1QueryVo.getSupAcc1().equals("")){
                    saveOriginalVouchers(lspzk1QueryVo.getLsyspzList());
                }
                //保存核算凭证
                else{
                    saveSpVouchers(lspzk1QueryVo.getLshspzQueryVoList());
                }
            }

            //更新当前财务日期
            //TODO:之后有可能又会加回来
            //updateCurrentDate(inputDate);

            return Msg.success().add("lspzk1QueryVoList", lspzk1QueryVoList);
        }
        catch(Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "未知异常！");
        }
    }

    public Msg saveOriginalVouchers(List<Lsyspz> lsyspzList){
        Session session = null;
        Transaction tx = null;
        try{
            session = getSession();
            tx = session.beginTransaction();
            for(Lsyspz lsyspz : lsyspzList) {
                Query query = session.createQuery("from Lsyspz where inputDate = '" + lsyspz.getInputDate() + "'and voucherNo = '" + lsyspz.getVoucherNo() + "'and entryNo = '" + lsyspz.getEntryNo() + "'and originalNo = '" + lsyspz.getOriginalNo() + "'");
                List<Lsyspz> edit_lsyspzList = query.list();
                for (Lsyspz editLsyspz : edit_lsyspzList) {
                    session.delete(editLsyspz);
                }
                //设置id不能重复
                Query last_query = session.createQuery("from Lsyspz where yspzAutoId = (select max(yspzAutoId) from Lsyspz)");
                Lsyspz last = (Lsyspz) last_query.uniqueResult();
                int auto_id = last == null ? 1 : last.getYspzAutoId() + 1;
                lsyspz.setYspzAutoId(auto_id);

                session.save(lsyspz);
            }
            tx.commit();
            close(session);
            return Msg.success().add("lsyspzList",lsyspzList);
        }
        catch(Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "未知异常！");
        }
    }

//    public Msg saveOriginalVoucher(Lsyspz lsyspz, Boolean is_first_save){
//        Session session = null;
//        Transaction tx = null;
//        try{
//            session = getSession();
//            tx = session.beginTransaction();
//            Query query = session.createQuery("from Lsyspz where inputDate = '"+lsyspz.getInputDate()+"'and voucherNo = '"+lsyspz.getVoucherNo()+"'and entryNo = '"+lsyspz.getEntryNo()+"'and originalNo = '"+lsyspz.getOriginalNo()+"'");
//            List<Lsyspz> lsyspzList= query.list();
//            if(is_first_save){
//                if(lsyspzList.size()>0){
//                    close(session);
//                    return Msg.fail().add("errorInfo","原始编号重复，请重新输入");
//                }else {
//                    session.save(lsyspz);
//                }
//            }
//            else{
//                for(Lsyspz editLsyspz : lsyspzList){
//                    session.delete(editLsyspz);
//                }
//                session.save(lsyspz);
//            }
//            tx.commit();
//            close(session);
//            return Msg.success().add("lsyspz",lsyspz);
//        }
//        catch(Exception e){
//            tx.rollback();
//            close(session);
//            e.printStackTrace();
//            return Msg.fail().add("errorInfo", "未知异常！");
//        }
//    }

    public Msg saveSpVouchers(List<LshspzQueryVo> lshspzQueryVoList){
        Session session = null;
        Transaction tx = null;
        try{
            session = getSession();
            tx = session.beginTransaction();
            for(LshspzQueryVo lshspzQueryVo : lshspzQueryVoList) {
                String spNo2 = "";
                String spNo_null_str = "";
                if(lshspzQueryVo.getSpNo2() == null) {
                    spNo_null_str = "or spNo2 is null";
                }
                else{
                    spNo2 = lshspzQueryVo.getSpNo2();
                }

                Query query = session.createQuery("from Lshspz where inputDate = '" + lshspzQueryVo.getInputDate() + "' and voucherNo = '" + lshspzQueryVo.getVoucherNo() + "' and itemNo = '" + lshspzQueryVo.getItemNo() + "' and entryNo = '" + lshspzQueryVo.getEntryNo() + "' and spNo1 = '" + lshspzQueryVo.getSpNo1() + "' and (spNo2 = '" + spNo2 + "'" + spNo_null_str + ")");
                List<Lshspz> lshspzList = query.list();
                for (Lshspz editLshspz : lshspzList) {
                    session.delete(editLshspz);
                }

                //设置id不能重复
                Query last_query = session.createQuery("from Lshspz where hspzAutoId = (select max(hspzAutoId) from Lshspz)");
                Lshspz last = (Lshspz) last_query.uniqueResult();
                int auto_id = last == null ? 1 : last.getHspzAutoId() + 1;

                Lshspz lshspz = new Lshspz();
                lshspz.setHspzAutoId(auto_id);
                lshspz.setEntryNo(lshspzQueryVo.getEntryNo());
                lshspz.setVoucherNo(lshspzQueryVo.getVoucherNo());
                lshspz.setInputDate(lshspzQueryVo.getInputDate());
                lshspz.setItemNo(lshspzQueryVo.getItemNo());
                lshspz.setBkpDirection(lshspzQueryVo.getBkpDirection());
                lshspz.setMoney(lshspzQueryVo.getMoney());
                lshspz.setQty(lshspzQueryVo.getQty());
                lshspz.setSpNo1(lshspzQueryVo.getSpNo1());
                lshspz.setSpNo2(lshspzQueryVo.getSpNo2());

                session.save(lshspz);
            }
            tx.commit();
            close(session);
            return Msg.success().add("lshspzQueryVoList",lshspzQueryVoList);
        }
        catch(Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "未知异常！");
        }
    }

//    public Msg saveSpVoucher(Lshspz lshspz){
//        Session session = null;
//        Transaction tx = null;
//        try{
//            session = getSession();
//            tx = session.beginTransaction();
//            Query query = session.createQuery("from Lshspz where inputDate = '"+lshspz.getInputDate()+"'and voucherNo = '"+lshspz.getVoucherNo()+"'and entryNo = '"+lshspz.getEntryNo()+"'and spNo1 = '"+lshspz.getSpNo1()+"'and spNo2 = '"+lshspz.getSpNo2()+"'");
//            List<Lshspz> lshspzList= query.list();
//            if(lshspzList.size()>0){
//                for(Lshspz editLshspz : lshspzList){
//                    session.delete(editLshspz);
//                }
//                session.save(lshspz);
//            }
//            else{
//                session.save(lshspz);
//            }
//            tx.commit();
//            close(session);
//            return Msg.success().add("lshspz",lshspz);
//        }
//        catch(Exception e){
//            tx.rollback();
//            close(session);
//            e.printStackTrace();
//            return Msg.fail().add("errorInfo", "未知异常！");
//        }
//    }

//    public Msg saveEntry(Lspzk1 lspzk1, Boolean is_first_save){
//        Session session = null;
//        Transaction tx = null;
//        try{
//            session = getSession();
//            tx = session.beginTransaction();
//            Query query = session.createQuery("from Lspzk1 where inputDate = '"+lspzk1.getInputDate()+"'and voucherNo = '"+lspzk1.getVoucherNo()+"'and entryNo = '"+lspzk1.getEntryNo()+"'and itemNo = '"+lspzk1.getItemNo()+"'");
//            List<Lspzk1> lspzk1List= query.list();
//            if(is_first_save){
//                if(lspzk1List.size()>0){
//                    close(session);
//                    return Msg.fail().add("errorInfo","科目编号重复，请重新输入");
//                }else {
//                    session.save(lspzk1);
//                }
//            }
//            else{
//                for(Lspzk1 editLspzk11 : lspzk1List){
//                    session.delete(editLspzk11);
//                }
//                session.save(lspzk1);
//            }
//
//            //更新当前财务日期
//            updateCurrentDate(lspzk1.getInputDate());
//
//            tx.commit();
//            close(session);
//            return Msg.success().add("lspzk1",lspzk1);
//        }
//        catch(Exception e){
//            tx.rollback();
//            close(session);
//            e.printStackTrace();
//            return Msg.fail().add("errorInfo", "未知异常！");
//        }
//    }

    public Msg delOriginalVoucher(String inputDate, String voucherNo, String entryNo, String originalNo){
        Session session = null;
        Transaction tx = null;
        List<Lsyspz> lsyspzList = null;
        try{
            session = getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Lsyspz where inputDate = '"+inputDate+"'and voucherNo = '"+voucherNo+"'and entryNo = '"+entryNo+"'and originalNo = '"+originalNo+"'");
            lsyspzList= query.list();
            for(Lsyspz dellsyspz : lsyspzList){
                session.delete(dellsyspz);
            }
            tx.commit();
            close(session);
            return Msg.success().add("lsyspzList",lsyspzList);
        }
        catch(Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "未知异常！");
        }
    }

    public Msg delEntry(String inputDate, String voucherNo, String entryNo, String itemNo){
        Session session = null;
        Transaction tx = null;
        List<Lspzk1> lspzk1List = null;
        try{
            session = getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Lspzk1 where inputDate = '"+inputDate+"'and voucherNo = '"+voucherNo+"'and entryNo = '"+entryNo+"'and itemNo = '"+itemNo+"'");
            lspzk1List= query.list();
            for(Lspzk1 dellspzk1 : lspzk1List){
                session.delete(dellspzk1);

                String hql = "from Lsyspz as s where s.voucherNo = '" + dellspzk1.getVoucherNo() + "' and s.inputDate = '" + dellspzk1.getInputDate() + "' and s.entryNo = '" + dellspzk1.getEntryNo() + "'";
                Query orignal_query = session.createQuery(hql);
                List<Lsyspz> lsyspzList= orignal_query.list();
                for(Lsyspz dellsyspz : lsyspzList){
                    session.delete(dellsyspz);
                }
            }
            tx.commit();
            close(session);
            return Msg.success().add("lspzk1List",lspzk1List);
        }
        catch(Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "未知异常！");
        }
    }

    public Msg delVoucher(String voucherNo, String inputDate){
        Session session = null;
        Transaction tx = null;
        try{
            session = getSession();
            tx = session.beginTransaction();

            String lspzk1_hql = "from Lspzk1 as s where s.voucherNo = '" + voucherNo + "' and s.inputDate like '" + inputDate.substring(0,6) + "%'";
            Query lspzk1_query = session.createQuery(lspzk1_hql);
            List<Lspzk1> lspzk1List= lspzk1_query.list();
            for(Lspzk1 dellspzk1 : lspzk1List){
                session.delete(dellspzk1);

                String lsyspz_hql = "from Lsyspz as s where s.voucherNo = '" + dellspzk1.getVoucherNo() + "' and s.inputDate = '" + dellspzk1.getInputDate() + "' and s.entryNo = '" + dellspzk1.getEntryNo() + "'";
                Query orignal_query = session.createQuery(lsyspz_hql);
                List<Lsyspz> lsyspzList= orignal_query.list();
                for(Lsyspz dellsyspz : lsyspzList){
                    session.delete(dellsyspz);
                }

                String lshspz_hql = "from Lshspz as s where s.voucherNo = '" + dellspzk1.getVoucherNo() + "' and s.inputDate = '" + dellspzk1.getInputDate() + "' and s.entryNo = '" + dellspzk1.getEntryNo() + "'";
                Query sp_query = session.createQuery(lshspz_hql);
                List<Lshspz> lshspzList= sp_query.list();
                for(Lshspz dellshspz : lshspzList){
                    session.delete(dellshspz);
                }
            }

            //Lspzk1和Lsyspz的所有后面的凭证编号-1
            String higher_hql = "from Lspzk1 as s where s.voucherNo > '" + voucherNo + "' and s.inputDate like '" + inputDate.substring(0, 6) + "%'";
            Query higher_query = session.createQuery(higher_hql);
            List<Lspzk1> higherList = higher_query.list();
            for(Lspzk1 lspzk1 : higherList){
                //计算-1后的凭证编号
                String new_voucher_no = getUpdateFullNo(lspzk1.getVoucherNo(), 4, 0);

                String lsyspz_hql = "from Lsyspz as s where s.voucherNo = '" + lspzk1.getVoucherNo() + "' and s.inputDate = '" + lspzk1.getInputDate() + "' and s.entryNo = '" + lspzk1.getEntryNo() + "'";
                Query orignal_query = session.createQuery(lsyspz_hql);
                List<Lsyspz> lsyspzList= orignal_query.list();
                for(Lsyspz lsyspz : lsyspzList){
                    lsyspz.setVoucherNo(new_voucher_no);
                    session.update(lsyspz);
                }

                String lshspz_hql = "from Lshspz as s where s.voucherNo = '" + lspzk1.getVoucherNo() + "' and s.inputDate = '" + lspzk1.getInputDate() + "' and s.entryNo = '" + lspzk1.getEntryNo() + "'";
                Query sp_query = session.createQuery(lshspz_hql);
                List<Lshspz> lshspzList= sp_query.list();
                for(Lshspz dellshspz : lshspzList){
                    dellshspz.setVoucherNo(new_voucher_no);
                    session.update(dellshspz);
                }

                lspzk1.setVoucherNo(new_voucher_no);
                session.update(lspzk1);
            }

            //Lspzbh编号-1
            updateVoucherData(0, inputDate.substring(0,6));

            //更新当前财务日期
            //TODO:之后有可能又会加回来
            //updateCurrentDate(inputDate);

            tx.commit();
            close(session);
            return Msg.success().add("lspzk1List",lspzk1List);
        }
        catch(Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "未知异常！");
        }
    }

    public Msg changeDateNSummary(String voucherNo, String date, String summary){
        Session session = null;
        Transaction tx = null;
        try{
            session = getSession();
            tx = session.beginTransaction();

            //找出当前凭证的所有分录
            String entry_hql = "from Lspzk1 as s where s.voucherNo = '" + voucherNo + "' and s.inputDate like '" + date.substring(0,6) + "%'";
            Query entry_query = session.createQuery(entry_hql);
            List<Lspzk1> entryList = entry_query.list();
            for(Lspzk1 lspzk1 : entryList){
                //找出当前分录所有原始凭证
                String lsyspz_hql = "from Lsyspz as s where s.voucherNo = '" + lspzk1.getVoucherNo() + "' and s.inputDate = '" + lspzk1.getInputDate() + "' and s.entryNo = '" + lspzk1.getEntryNo() + "'";
                Query orignal_query = session.createQuery(lsyspz_hql);
                List<Lsyspz> lsyspzList= orignal_query.list();
                for(Lsyspz lsyspz : lsyspzList){
                    lsyspz.setInputDate(date);
                    lsyspz.setSummary(summary);
                    session.update(lsyspz);
                }

                lspzk1.setInputDate(date);
                lspzk1.setSummary(summary);
                session.update(lspzk1);
            }

            //更新当前财务日期
            updateCurrentDate(date);

            tx.commit();
            close(session);
            return Msg.success().add("info","修改成功！");
        }
        catch(Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "未知异常！");
        }
    }

    public Msg changeBkpDirection(String inputDate, String voucherNo, String entryNo, String itemNo){
        Session session = null;
        Transaction tx = null;
        List<Lspzk1> lspzk1List = null;
        try{
            session = getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Lspzk1 where inputDate = '"+inputDate+"'and voucherNo = '"+voucherNo+"'and entryNo = '"+entryNo+"'and itemNo = '"+itemNo+"'");
            lspzk1List= query.list();
            for(Lspzk1 lspzk1 : lspzk1List){
                String direction = "";
                if(lspzk1.getBkpDirection().equals("J")) {
                    direction = "D";
                }
                else{
                    direction = "J";
                }
                lspzk1.setBkpDirection(direction);
                session.update(lspzk1);

                String hql = "from Lsyspz as s where s.voucherNo = '" + lspzk1.getVoucherNo() + "' and s.inputDate = '" + lspzk1.getInputDate() + "' and s.entryNo = '" + lspzk1.getEntryNo() + "'";
                Query orignal_query = session.createQuery(hql);
                List<Lsyspz> lsyspzList= orignal_query.list();
                for(Lsyspz lsyspz : lsyspzList){
                    lspzk1.setBkpDirection(direction);
                    session.update(lsyspz);
                }
            }
            tx.commit();
            close(session);
            return Msg.success().add("lspzk1List",lspzk1List);
        }
        catch(Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "未知异常！");
        }
    }

    public Msg getVoucherData(String year_month){
        Session session = null;
        Transaction tx = null;
        try{
            session = getSession();
            tx = session.beginTransaction();
            String hql = "from Lspzbh where iniDate like '"+ year_month + "%'";
            Query query = session.createQuery(hql);
            List<Lspzbh> lspzbhlist = query.list();
            Lspzbh lspzbh = lspzbhlist.get(0);
            tx.commit();
            close(session);
            return Msg.success().add("lspzbh",lspzbh);
        }
        catch(Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "未知异常！");
        }
    }

    /**
    *@author czh
    *@Description 更新LSPZBH中的编号
    *@Date 2018/5/10 17:18
    *@Param [is_add, year_month]
    *@return com.bjut.ssh.entity.Msg
    **/
    public Msg updateVoucherData(int is_add, String year_month){
        Session session = null;
        Transaction tx = null;
        try{
            session = getSession();
            tx = session.beginTransaction();
            String hql = "from Lspzbh where iniDate like '"+ year_month + "%'";
            Query query = session.createQuery(hql);
            List<Lspzbh> lspzbhList = query.list();
            Lspzbh lspzbh = lspzbhList.get(0);
            String new_voucher_no = getUpdateFullNo(lspzbh.getVoucherNo(), 4, is_add);
            lspzbh.setVoucherNo(new_voucher_no);
            session.update(lspzbh);
            tx.commit();
            close(session);
            return Msg.success().add("lspzbh",lspzbh);
        }
        catch(Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "未知异常！");
        }
    }

    public void updateCurrentDate(String date){
        Session session = null;
        Transaction tx = null;
        try{
            session = getSession();
            tx = session.beginTransaction();
            //找到最新的凭证编号
            Query lattest_voucher_query = session.createQuery("from Lspzbh where iniDate like '" + date.substring(0,6) + "%'");
            List<Lspzbh> lspzbhList = lattest_voucher_query.list();
            String lattest_voucher = lspzbhList.get(0).getVoucherNo();
            //Lspzbh中的凭证编号是下一个凭证的编号，所以编号-1
            lattest_voucher = lspzbhList.get(0).getVoucherFirstChar() + getUpdateFullNo(lattest_voucher, 4, 0);

            //找出最新的凭证的录入日期
            Query lattest_date_query = session.createQuery("from Lspzk1 where voucherNo = '" + lattest_voucher + "' and inputDate like '" + date.substring(0,6) + "%'");
            List<Lspzk1> lspzk1List = lattest_date_query.list();
            String lattest_date;
            if(lspzk1List.size() == 0){
                lattest_date = date;
            }
            else{
                lattest_date = lspzk1List.get(0).getInputDate();
            }

//            Query update_date = session.createQuery("update Lsconf set confValue = '" + lattest_date + "' where confKey = 'current_date'");
//            update_date.executeUpdate();
            Lsconf current_date = (Lsconf) session.get(Lsconf.class, "current_date");
            current_date.setConfValue(lattest_date);
            session.update(current_date);
            tx.commit();
            close(session);
        }
        catch (Exception e){
            close(session);
            tx.rollback();
            e.printStackTrace();
        }
    }

    public String queryVoucherDate(String voucherNo, String inputDate, int is_next){
        Session session = null;
        String date = null;
        String hql;
        try {
            session = getSession();
            //获取前一个或后一个凭证编号
            String first_char = voucherNo.substring(0, 1);
            String voucher_num = voucherNo.substring(1);
            String query_voucher_no = first_char + getUpdateFullNo(voucher_num, 4, is_next);

            hql = "from Lspzk1 as s where s.voucherNo = '" + query_voucher_no + "' and s.inputDate like '" + inputDate.substring(0,6) + "%'";
            Query query = session.createQuery(hql);
            List<Lspzk1> lspzk1List = query.list();
            if(lspzk1List.size() > 0) {
                date = lspzk1List.get(0).getInputDate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return date;
        }
    }

    public List<Lspzk1> queryVouchers(String voucherNo, String inputDate){
        Session session = null;
        //Transaction tx = null;
        List<Lspzk1> Lspzk1List = null;
        String hql;
        try {
            session = getSession();
            //tx = session.beginTransaction();
            hql = "from Lspzk1 as s where s.voucherNo = '" + voucherNo + "' and s.inputDate like '" + inputDate.substring(0,6) + "%'";
            Query query = session.createQuery(hql);
            Lspzk1List= query.list();
            //tx.commit();
        } catch (Exception e) {
            //tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return Lspzk1List;
        }
    }

    public List<Lsyspz> queryOriginalVouchers(String voucherNo, String inputDate, String entryNo){
        Session session = null;
        Transaction tx = null;
        List<Lsyspz> lsyspzList = null;
        String hql;
        try {
            session = getSession();
            tx = session.beginTransaction();
            hql = "from Lsyspz as s where s.voucherNo = '" + voucherNo + "' and s.inputDate = '" + inputDate + "' and s.entryNo = '" + entryNo + "'";
            Query query = session.createQuery(hql);
            lsyspzList= query.list();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lsyspzList;
        }
    }

//    /**
//    *@author czh
//    *@Description 查找科目对应的专项核算对象
//    *@Date 2018/5/24 11:03
//    *@Param [itemNo, catNo]
//    *@return java.util.List.com.bjut.ssh.entity.Lshszd>
//    **/
//    public List<Lshszd> querySpDict(String itemNo, String catNo){
//        Session session = null;
//        Transaction tx = null;
//        List<Lshszd> lshszdList = null;
//        String hql;
//        try {
//            session = getSession();
//            tx = session.beginTransaction();
//            hql = "from Lshszd as s where s.itemNo = '" + itemNo + "' and s.catNo = '" + catNo + "'";
//            Query query = session.createQuery(hql);
//            lshszdList= query.list();
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//            e.printStackTrace();
//        } finally {
//            //关闭操作
//            close(session);
//            return lshszdList;
//        }
//    }

    ///查询凭证中科目对应的核算凭证可填写列表
    public List<LshspzQueryVo> queryTotalSpVouchers(String itemNo, String voucherNo, String inputDate, String entryNo){
        Session session = null;
        Transaction tx = null;
        List<LshspzQueryVo> lshspzQueryVoList = new ArrayList<>();
        try{
            session = getSession();
            tx = session.beginTransaction();
            //1.查询对应核算科目的分类编号
            Lskmzd lskmzd = (Lskmzd) session.get(Lskmzd.class, itemNo);

            //2.查询核算科目对应匹配的核算编号
            Query queryhsje = session.createQuery("from Lshsje where itemNo = ?");
            queryhsje.setParameter(0, itemNo);
            List<Lshsje> lshsjeList = queryhsje.list();


            //3.筛选核算字典中对应的条目，且核算应为明细
            for(Lshsje lshsje : lshsjeList) {
                Query query1, query2;
                List<Lshszd> lshszdList1 = new ArrayList<>();
                List<Lshszd> lshszdList2 = new ArrayList<>();

                //3.1.查询核算编号1的核算字典，以得到核算对象
                query1 = session.createQuery("from Lshszd where catNo = ? and spNo = ? and finLevel = ?");
                query1.setParameter(0, lskmzd.getSupAcc1());
                query1.setParameter(1, lshsje.getSpNo1());
                query1.setParameter(2, (byte)1);
                lshszdList1 = query1.list();

                //3.2.如果有核算编号2则再查询
                if (lskmzd.getSupAcc2() != null && !lskmzd.getSupAcc2().equals("")) {
                    query2 = session.createQuery("from Lshszd where catNo = ? and spNo = ? and finLevel = ?");
                    query2.setParameter(0, lskmzd.getSupAcc2());
                    query2.setParameter(1, lshsje.getSpNo2());
                    query2.setParameter(2, (byte)  1);
                    lshszdList2 = query2.list();

                    //交叉记录两种核算
                    for(Lshszd lshszd1 : lshszdList1){
                        for(Lshszd lshszd2 : lshszdList2){
                            LshspzQueryVo lshspzQueryVo = new LshspzQueryVo();
                            lshspzQueryVo.setSpNo1(lshszd1.getSpNo());
                            lshspzQueryVo.setSpName1(lshszd1.getSpName());
                            String upName1 = upperSpName(lshszd1.getSpNo(), lshszd1.getCatNo(), lshszd1.getSpLevel());
                            lshspzQueryVo.setUpperName1(upName1);

                            lshspzQueryVo.setSpNo2(lshszd2.getSpNo());
                            lshspzQueryVo.setSpName2(lshszd2.getSpName());
                            String upName2 = upperSpName(lshszd2.getSpNo(), lshszd2.getCatNo(), lshszd2.getSpLevel());
                            lshspzQueryVo.setUpperName2(upName2);

                            lshspzQueryVoList.add(lshspzQueryVo);
                        }
                    }
                }
                else{
                    for(Lshszd lshszd1 : lshszdList1){
                        LshspzQueryVo lshspzQueryVo = new LshspzQueryVo();
                        lshspzQueryVo.setSpNo1(lshszd1.getSpNo());
                        lshspzQueryVo.setSpName1(lshszd1.getSpName());
                        String upName1 = upperSpName(lshszd1.getSpNo(), lshszd1.getCatNo(), lshszd1.getSpLevel());
                        lshspzQueryVo.setUpperName1(upName1);

                        lshspzQueryVoList.add(lshspzQueryVo);
                    }
                }

                for(LshspzQueryVo lshspzQueryVo : lshspzQueryVoList){
                    //4.查询当前条目是否已经在核算凭证中保存过
                    Lshspz lshspz = querySpVoucher(voucherNo, inputDate, entryNo, itemNo, lshspzQueryVo.getSpNo1(), lshspzQueryVo.getSpNo2());
                    if(lshspz != null){
                        lshspzQueryVo.setEntryNo(lshspz.getEntryNo());
                        lshspzQueryVo.setVoucherNo(lshspz.getVoucherNo());
                        lshspzQueryVo.setInputDate(lshspz.getInputDate());
                        lshspzQueryVo.setItemNo(lshspz.getItemNo());
                        lshspzQueryVo.setBkpDirection(lshspz.getBkpDirection());
                        lshspzQueryVo.setMoney(lshspz.getMoney());
                        lshspzQueryVo.setQty(lshspz.getQty());
                    }
                }
            }

        }
        catch(Exception e){
            tx.rollback();
            e.printStackTrace();
        }
        finally {
            close(session);
            return lshspzQueryVoList;
        }
    }

    public String upperSpName(String spNo, String catNo, String spLevel){
        Session session = null;
        try{
            session = getSession();

            if(spLevel.equals("1")) return null;

            String upperName = "";
            int level = Integer.parseInt(spLevel);
            while(level > 1) {
                String upNo = spNo.substring(0, (level - 1) * 4);
                for (int i = 0; i < (4 - level) * 4; i++) {
                    upNo += "0";
                }
                Query q = session.createQuery("from Lshszd where spNo = '" + upNo + "' and catNo = '" + catNo + "'");
                Lshszd lshszd = (Lshszd) q.uniqueResult();
                upperName += "/" + lshszd.getSpName();
                level--;
            }
            close(session);
            return upperName;
        }
        catch (Exception e){
            e.printStackTrace();
            close(session);
            return null;
        }
    }

    ///查询单个核算凭证
    public Lshspz querySpVoucher(String voucherNo, String inputDate, String entryNo, String itemNo, String spNo1, String spNo2){
        Session session = null;
        //Transaction tx = null;
        Lshspz lshspz = null;
        String hql;
        try {
            session = getSession();
            //tx = session.beginTransaction();

            //判断spNo2是否为空
            String sp2_hql = "s.spNo2 ='" + spNo2 + "'";
            if(spNo2 == null){
                sp2_hql = "s.spNo2 is null";
            }
            hql = "from Lshspz as s where s.itemNo = '" + itemNo + "' and s.entryNo = '" + entryNo + "' and s.inputDate = '" + inputDate + "' and s.voucherNo = '" + voucherNo + "' and s.spNo1 = '" + spNo1 + "' and " + sp2_hql;
            Query query = session.createQuery(hql);
            //lshspz = (Lshspz)query.uniqueResult();
            List<Lshspz> lshspzList = query.list();
            if(lshspzList.size() > 0){
                lshspz = lshspzList.get(0);
            }
        } catch (Exception e) {
            //tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lshspz;
        }
    }

    //查询整个分录的核算凭证
    public List<Lshspz> querySpVouchers(String voucherNo, String inputDate, String entryNo){
        Session session = null;
        Transaction tx = null;
        List<Lshspz> lshspzList = new ArrayList<>();
        String hql;
        try {
            session = getSession();
            tx = session.beginTransaction();
            hql = "from Lshspz as s where s.entryNo = '" + entryNo + "' and s.inputDate = '" + inputDate + "' and s.voucherNo = '" + voucherNo + "'";
            Query query = session.createQuery(hql);
            lshspzList= query.list();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            return lshspzList;
        }
    }

    /**
    *@author czh
    *@Description 获取指定位数的编号
    *@Date 2018/5/24 11:02
    *@Param [num, len, is_add]
    *@return java.lang.String
    **/
    public String getUpdateFullNo(String num, int len, int is_add){
        int int_num;
        if(num.length() == 5){
            int_num = Integer.parseInt(num.substring(1));
        }
        else{
            int_num = Integer.parseInt(num);
        }

        int_num = is_add == 1 ? int_num + 1 : int_num - 1;
        String new_num = Integer.toString(int_num);
        while(new_num.length() < len) {
            new_num = '0' + new_num;
        }
        if(num.length() == 5) {
            new_num = num.substring(0, 1) + new_num;
        }
        return new_num;
    }

    /**
    *@author czh
    *@Description 根据凭证号审核
    *@Date 2018/5/24 11:02
    *@Param [is_review, inputDate, start, end, name, no]
    *@return com.bjut.ssh.entity.Msg
    **/
    public Msg operateReviewByNum(Boolean is_review, String inputDate, String start, String end, String name, String no){
        Session session = null;
        Transaction tx = null;
        try{
            session = getSession();
            tx = session.beginTransaction();

            String hql = "from Lspzk1 as s where s.voucherNo >= '" + start + "'and s.voucherNo <= '" + end + "' and s.inputDate like '" + inputDate.substring(0, 6) + "%'";
            Query query = session.createQuery(hql);
            List<Lspzk1> lspzk1List = query.list();
            if(lspzk1List.size() == 0){
                close(session);
                return Msg.fail().add("errorInfo", "没有符合条件的凭证");
            }

            String check = firstAuditCheck(lspzk1List);
            if(!check.equals("1") && is_review){
                tx.rollback();
                close(session);
                return Msg.fail().add("errorInfo", check);
            }

            for(Lspzk1 lspzk1 : lspzk1List){
                if(lspzk1.getPreActNo().equals(no)){
                    close(session);
                    return Msg.fail().add("errorInfo", "不允许复核本人制证的凭证！");
                }

                if(is_review){
                    lspzk1.setAuditor(name);
                    lspzk1.setAuditorNo(no);
                    lspzk1.setAuditSign((byte)1);
                }
                else{
                    lspzk1.setAuditor(null);
                    lspzk1.setAuditorNo(null);
                    lspzk1.setAuditSign(null);
                }
                session.update(lspzk1);
            }

            if(is_review) {
                reviewVouchers(inputDate, start, end);
                reviewAccounts(inputDate, start, end);
            }
            tx.commit();
            close(session);
            return Msg.success().add("lspzk1List",lspzk1List);
        }
        catch(Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "未知异常！");
        }
    }

    /**
    *@author czh
    *@Description 根据日期审核
    *@Date 2018/5/24 11:03
    *@Param [is_review, inputDate, start, end, name, no]
    *@return com.bjut.ssh.entity.Msg
    **/
    public Msg operateReviewByDate(Boolean is_review, String inputDate, String start, String end, String name, String no){
        Session session = null;
        Transaction tx = null;
        try{
            session = getSession();
            tx = session.beginTransaction();

            start = Integer.parseInt(start) < 10 ?('0'+start) : start;
            end = Integer.parseInt(end) < 10 ?('0'+end) : end;

            String hql = "from Lspzk1 as s where s.inputDate >= '" + inputDate.substring(0, 6) + start + "'and s.inputDate <= '" + inputDate.substring(0, 6) + end + "'";
            Query query = session.createQuery(hql);
            List<Lspzk1> lspzk1List = query.list();

            String startNo = lspzk1List.get(0).getItemNo();
            String endNo = lspzk1List.get(0).getItemNo();
            if(lspzk1List.size() == 0){
                close(session);
                return Msg.fail().add("errorInfo", "没有符合条件的凭证");
            }


            String check = firstAuditCheck(lspzk1List);
            if(!check.equals("1") && is_review){
                close(session);
                return Msg.fail().add("errorInfo", check);
            }

            for(Lspzk1 lspzk1 : lspzk1List){
                if(lspzk1.getPreActNo().equals(no)){
                    tx.rollback();
                    close(session);
                    return Msg.fail().add("errorInfo", "不允许复核本人制证的凭证！");
                }

                if(lspzk1.getItemNo().compareTo(startNo) < 0)
                    startNo = lspzk1.getItemNo();
                else if(lspzk1.getItemNo().compareTo(startNo) > 0)
                    endNo = lspzk1.getItemNo();

                if(is_review){
                    lspzk1.setAuditor(name);
                    lspzk1.setAuditorNo(no);
                    lspzk1.setAuditSign((byte)1);
                }
                else{
                    lspzk1.setAuditor(null);
                    lspzk1.setAuditorNo(null);
                    lspzk1.setAuditSign(null);
                }
                session.update(lspzk1);
            }

            if(is_review) {
                reviewVouchers(inputDate, startNo, endNo);
                reviewAccounts(inputDate, startNo, endNo);
            }

            tx.commit();
            close(session);
            return Msg.success().add("lspzk1List",lspzk1List);
        }
        catch(Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
            return Msg.fail().add("errorInfo", "未知异常！");
        }
    }

    public String firstAuditCheck(List<Lspzk1> lspzk1List){
        Session session = null;
        try {
            session = getSession();
            //如果是首次审核先检查科目字典本身及科目数据与往来单位、专项核算对象之间数据是否相符
            for (Lspzk1 lspzk1 : lspzk1List) {
                if(lspzk1.getAuditSign() != null && lspzk1.getAuditSign() == (byte)1) return "1";
            }

            double tssm=0, tsdms=0, tscms=0, tsbalance=0;
            //核对科目字典本身：
            //如果ssm!=0 或者 SBlance!=0 则提示科目余额不平衡，请修改，并退出；如果 SCMS!=SDMS, 则提示科目借贷方发生额不相等，请修改，并退出；
            Query query = session.createQuery("select sum(supMoney) as SSM,sum(debitMoneySup) as SDMS,sum(creditMoneySup) as SCMS,sum(balance) as SBlance from Lskmzd where item = '1'");
            List<Object[]> list = query.list();
            for(Object[] obj : list) {
                if(obj[0] != null) tssm = (double) obj[0];
                if(obj[1] != null) tsdms = (double) obj[1];
                if(obj[2] != null) tscms = (double) obj[2];
                if(obj[3] != null) tsbalance = (double) obj[3];
                if(tssm != 0 || tsbalance != 0) return "科目余额不平衡，请修改";
                if(tscms != tsdms) return "科目借贷方发生额不相等，请修改";
            }
            //如果科目是往来科目（Exchang=1）则与lswlje\lswlsl两表核对：
            // 如果四个字段的合计数都等于科目字典的四个字段值，则执行下一条科目，否则提示该科目金额数据与往来单位金额数据不符，请退出修改；
            query = session.createQuery("from Lskmzd");
            List<Lskmzd> lskmzdList = query.list();
            for(Lskmzd lskmzd : lskmzdList){

                double ssm=0, sdms=0, scms=0, sbalance=0;
                query = session.createQuery("select supMoney,debitMoneySup,creditMoneySup,balance from Lskmzd where itemNo='" + lskmzd.getItemNo() + "'");
                List<Object[]> zdlist = query.list();
                for(Object[] obj : zdlist) {
                    if(obj[0] != null) ssm = (double) obj[0];
                    if(obj[1] != null) sdms = (double) obj[1];
                    if(obj[2] != null) scms = (double) obj[2];
                    if(obj[3] != null) sbalance = (double) obj[3];
                }

                double sq = 0, dqs = 0, cqs = 0, lq = 0;
                query = session.createQuery("select supQty,debitQtySup,creditQtySup,leftQty from Lskmsl where itemNo='" + lskmzd.getItemNo() + "'");
                List<Object[]> sllist = query.list();
                for (Object[] obj : sllist) {
                    if (obj[0] != null) sq = (double) obj[0];
                    if (obj[1] != null) dqs = (double) obj[1];
                    if (obj[2] != null) cqs = (double) obj[2];
                    if (obj[3] != null) lq = (double) obj[3];
                }

                if(lskmzd.getExchang() != null && lskmzd.getExchang() == (byte)1) {

                    query = session.createQuery("select sum(supMoney) as SSM,sum(debitMoneySup) as SDMS,sum(creditMoneySup) as SCMS,sum(balance) as SBlance from Lswlje where itemNo='" + lskmzd.getItemNo() + "'");
                    List<Object[]> wllist = query.list();
                    for (Object[] obj : wllist) {
                        double wlssm = obj[0] == null ? 0 : (double) obj[0];
                        double wlsdms = obj[1] == null ? 0 : (double) obj[1];
                        double wlscms = obj[2] == null ? 0 : (double) obj[2];
                        double wlsbalance = obj[3] == null ? 0 : (double) obj[3];
                        if (wlssm != ssm || wlsbalance != sbalance || wlsdms != sdms || wlscms != scms) {
                            return "科目" + lskmzd.getItemNo() + "金额数据与往来单位金额数据不符，请退出修改";
                        }
                    }

                    //如果该科目账式是数量（AccType=’Y’），则继续核对lswlsl数据:
                    if (lskmzd.getAccType().equals("Y")) {
                        query = session.createQuery("select sum(supQty) as SSM,sum(debitQtySup) as SDMS,sum(creditQtySup) as SCMS,sum(leftQty) as SBlance from Lswlsl where itemNo='" + lskmzd.getItemNo() + "'");
                        List<Object[]> wlsllist = query.list();
                        for (Object[] obj : wlsllist) {
                            double wlsq = obj[0] == null ? 0 : (double) obj[0];
                            double wldqs = obj[1] == null ? 0 : (double) obj[1];
                            double wlcqs = obj[2] == null ? 0 : (double) obj[2];
                            double wllq = obj[3] == null ? 0 : (double) obj[3];
                            if (wlsq != sq || wldqs != dqs || wlcqs != cqs || wllq != lq) {
                                return "科目" + lskmzd.getItemNo() + "数量数据与往来单位数量数据不符，请退出修改";
                            }
                        }
                    }
                }

                //如果科目有专项核算（SupAcc1或SupAcc2不为空）则与lshsje\lshssl两表核对：
                if(lskmzd.getSupAcc1() != null && !lskmzd.getSupAcc1().equals("")){
                    //核对lshsje数据
                    //先提取核算字典里的1级核算编号，构成数组hslist；
                    query = session.createQuery("from Lshszd where spLevel = '1' and catNo = '" + lskmzd.getSupAcc1() + "'");
                    List<Lshszd> lshszd1List = query.list();
                    if(lskmzd.getSupAcc2() != null && !lskmzd.getSupAcc2().equals("")) {
                        query = session.createQuery("from Lshszd where spLevel = '1' and catNo = '" + lskmzd.getSupAcc2() + "'");
                    }
                    List<Lshszd> lshszd2List = query.list();
                    double hsssm = 0, hssdms = 0, hsscms = 0, hssbal = 0;
                    for(int i = 0; i < lshszd1List.size(); i++) {
                        Lshszd lshszd = lshszd1List.get(i);
                        List<Lshsje> lshsjeList = null;
                        //如果只对应有一个核算类别（SupAcc2为空）：
                        if(lskmzd.getSupAcc2() == null || lskmzd.getSupAcc2().equals("")) {
                            query = session.createQuery("from Lshsje where itemNo='" + lskmzd.getItemNo() + "' and spNo1 = '" + lshszd.getSpNo() + "'");// + " and (spNo2 is null or spNo2 = '')");
                            lshsjeList = query.list();

                            for (Lshsje lshsje : lshsjeList) {
                                hsssm += lshsje.getSupMoney();
                                hssdms += lshsje.getDebitMoneySup();
                                hsscms += lshsje.getCreditMoneySup();
                                hssbal += lshsje.getBalance();
                            }
                        }
                        //如果同时对应两个核算类别（SupAcc2不为空）：
                        else{
                            for(int j = 0; j < lshszd2List.size(); j++) {
                                Lshszd lshszd2 = lshszd2List.get(j);
                                query = session.createQuery("from Lshsje where itemNo='" + lskmzd.getItemNo() + "' and spNo1 = '" + lshszd.getSpNo() + "' and spNo2 = '" + lshszd2.getSpNo() + "'");
                                lshsjeList = query.list();

                                for (Lshsje lshsje : lshsjeList) {
                                    hsssm += lshsje.getSupMoney();
                                    hssdms += lshsje.getDebitMoneySup();
                                    hsscms += lshsje.getCreditMoneySup();
                                    hssbal += lshsje.getBalance();
                                }
                            }
                        }
                    }
                    //如果四个字段的合计数都等于科目字典的四个字段值，则执行下一条科目，否则提示该科目金额数据与专项核算单位金额数据不符，请退出修改；
                    if (hsssm != ssm || hssdms != sdms || hsscms != scms || hssbal != sbalance) {
                        return "科目" + lskmzd.getItemNo() + "金额数据与专项核算单位金额数据不符，请退出修改";
                    }

                    if(lskmzd.getAccType().equals("Y")){
                        double hssq = 0, hsdqs = 0, hscqs = 0, hslef = 0;
                        for(int i = 0; i < lshszd1List.size(); i++) {
                            Lshszd lshszd = lshszd1List.get(i);
                            List<Lshssl> lshsslList = null;
                            //如果只对应有一个核算类别（SupAcc2为空）：
                            if(lskmzd.getSupAcc2() == null || lskmzd.getSupAcc2().equals("")) {
                                query = session.createQuery("from Lshssl where itemNo='" + lskmzd.getItemNo() + "' and spNo1 = '" + lshszd.getSpNo() + "'");// + " and (spNo2 is null or spNo2 = '')");
                                lshsslList = query.list();

                                for (Lshssl lshssl : lshsslList) {
                                    hssq += lshssl.getSupQty();
                                    hsdqs += lshssl.getDebitQtySup();
                                    hscqs += lshssl.getCreditQtySup();
                                    hslef += lshssl.getLeftQty();
                                }
                            }
                            //如果同时对应两个核算类别（SupAcc2不为空）：
                            else{
                                for(int j = 0; j < lshszd2List.size(); j++) {
                                    Lshszd lshszd2 = lshszd2List.get(j);
                                    query = session.createQuery("from Lshssl where itemNo='" + lskmzd.getItemNo() + "' and spNo1 = '" + lshszd.getSpNo() + "' and spNo2 = '" + lshszd2.getSpNo() + "'");
                                    lshsslList = query.list();

                                    for (Lshssl lshssl : lshsslList) {
                                        hssq += lshssl.getSupQty();
                                        hsdqs += lshssl.getDebitQtySup();
                                        hscqs += lshssl.getCreditQtySup();
                                        hslef += lshssl.getSupQty();
                                    }
                                }
                            }
                        }

                        if(hssq != sq || hsdqs != dqs || hscqs != cqs || hslef != lq){
                            return "科目" + lskmzd.getItemNo() + "数量数据与核算单位数量数据不符，请退出修改";
                        }
                    }
                }
            }
            return "1";
        }
        catch (Exception e){
            close(session);
            e.printStackTrace();
            return "检查有误";
        }
    }


    public void reviewVouchers(String inputDate, String startNo, String endNo){
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();

            String month = inputDate.substring(4, 6);

            //查找范围内所有凭证分录
            String hql = "from Lspzk1 where inputDate like '" + inputDate.substring(0, 6) + "%' and voucherNo >= '" + startNo + "' and voucherNo <= '" + endNo + "'";
            Query query = session.createQuery(hql);
            List<Lspzk1> lspzk1List = query.list();

            //Query q = session.createQuery("from Lskmzd");
            List<Lskmzd> lskmzdList = new ArrayList<>();
            List<Lswlje> lswljeList = new ArrayList<>();
            for(Lspzk1 lspzk1 : lspzk1List){
                Query q1 = session.createQuery("from Lskmzd where itemNo = '" + lspzk1.getItemNo() + "'");
                if(q1.list().size() > 0) lskmzdList.addAll(q1.list());
                Query q2 = session.createQuery("from Lswlje where itemNo = '" + lspzk1.getItemNo() + "'");
                if(q2.list().size() > 0) lswljeList.addAll(q2.list());
            }

            //建立hashmap用于暂存科目字典相关的数量和金额发生额
            Map<String, Map> map = new HashMap<String, Map>();
            for(Lskmzd lskmzd : lskmzdList){
                Map<String, Double> sub_map = new HashMap<String, Double>();
                sub_map.put("debitMoneyAcm", 0.0);
                sub_map.put("creditMoneyAcm", 0.0);
                sub_map.put("debitQtyAcm", 0.0);
                sub_map.put("creditQtyAcm", 0.0);
                map.put(lskmzd.getItemNo(), sub_map);
            }

            //建立hashmap用于暂存往来相关的数量和金额发生额
            Map<String, Map> cpy_map = new HashMap<String, Map>();
//            Query q2 = session.createQuery("from Lswlje");
//            List<Lswlje> lswljeList = q2.list();
            for(Lswlje lswlje : lswljeList){
                Map<String, Double> sub_map = new HashMap<String, Double>();
                sub_map.put("debitMoneyAcm", 0.0);
                sub_map.put("creditMoneyAcm", 0.0);
                sub_map.put("debitQtyAcm", 0.0);
                sub_map.put("creditQtyAcm", 0.0);
                //科目编号和单位编号共同组成key
                String id = lswlje.getItemNo() + ";" + lswlje.getCompanyNo();
                map.put(id, sub_map);
            }

            for(Lspzk1 lspzk1 : lspzk1List){
                Map m = map.get(lspzk1.getItemNo());
                Double money = lspzk1.getMoney() == null ? 0 : lspzk1.getMoney();
                Double qty = lspzk1.getQty() == null ? 0 : lspzk1.getQty();
                //判断借贷方向
                switch(lspzk1.getBkpDirection()){
                    case "J":
                        Double debitMoneyAcm = (Double) m.get("debitMoneyAcm") + money;
                        Double debitQtyAcm = (Double) m.get("debitQtyAcm") + qty;

                        m.put("debitMoneyAcm", debitMoneyAcm);
                        m.put("debitQtyAcm", debitQtyAcm);
                        map.put(lspzk1.getItemNo(), m);

                        break;
                    case "D":
                        Double creditMoneyAcm = (Double) m.get("creditMoneyAcm") + money;
                        Double creditQtyAcm = (Double) m.get("creditQtyAcm") + qty;

                        m.put("creditMoneyAcm", creditMoneyAcm);
                        m.put("creditQtyAcm", creditQtyAcm);
                        map.put(lspzk1.getItemNo(), m);

                        break;
                }

                //查找当前凭证是否有往来记录
                String id = lspzk1.getItemNo() + ";" + lspzk1.getCompanyNo();
                if(cpy_map.containsKey(id)){
                    Map m2 = cpy_map.get(id);
                    switch(lspzk1.getBkpDirection()){
                        case "J":
                            Double debitMoneyAcm = (Double) m2.get("debitMoneyAcm") + money;
                            Double debitQtyAcm = (Double) m2.get("debitQtyAcm") + qty;

                            m2.put("debitMoneyAcm", debitMoneyAcm);
                            m2.put("debitQtyAcm", debitQtyAcm);
                            cpy_map.put(id, m2);

                            break;
                        case "D":
                            Double creditMoneyAcm = (Double) m2.get("creditMoneyAcm") + money;
                            Double creditQtyAcm = (Double) m2.get("creditQtyAcm") + qty;

                            m2.put("creditMoneyAcm", creditMoneyAcm);
                            m2.put("creditQtyAcm", creditQtyAcm);
                            cpy_map.put(id, m2);

                            break;
                    }
                }
            }

            //遍历科目字典map并更新数据
            Map acm = null;
            Iterator iter = map.entrySet().iterator();
            while(iter.hasNext()) {
                session = getSession();
                tx = session.beginTransaction();

                Map.Entry entry = (Map.Entry)iter.next();
                // 获取key
                String[] key = ((String)entry.getKey()).split(";");
                String itemNo = key[0];
                // 获取value
                acm = (Map)entry.getValue();

                String de = "debitMoney" + month;
                String cr = "creditMoney" + month;
                String set_lskmzd = "update Lskmzd set "+de+" = coalesce("+de+",0.0) + " + acm.get("debitMoneyAcm") + ", "+cr+" = coalesce("+cr +",0.0) +"+ acm.get("creditMoneyAcm") + " where itemNo ='" + itemNo + "'";
                Query set_query = session.createQuery(set_lskmzd);
                set_query.executeUpdate();

                String deq = "debitQty" + month;
                String crq = "creditQty" + month;
                String set_lskmsl = "update Lskmsl set "+deq+" = coalesce("+deq+",0.0) + " + acm.get("debitQtyAcm") + ", "+crq+" = coalesce("+crq +",0.0) +"+ acm.get("creditQtyAcm") + " where itemNo ='" + itemNo + "'";
                Query setq_query = session.createQuery(set_lskmsl);
                setq_query.executeUpdate();

                tx.commit();
                close(session);

                summarizeMoneyData("Lskmzd", month, itemNo);
                summarizeQtyData("Lskmsl", month, itemNo);
            }

            //遍历往来map并更新数据
            Map cpy_acm = null;
            Iterator cpy_iter = map.entrySet().iterator();
            while(cpy_iter.hasNext()) {
                session = getSession();
                tx = session.beginTransaction();

                Map.Entry entry = (Map.Entry)cpy_iter.next();
                // 获取key
                String[] key = ((String)entry.getKey()).split(";");

                if(key.length < 2) continue;
                String itemNo = key[0];
                String companyNo = key[1];
                // 获取value
                cpy_acm = (Map)entry.getValue();

                String de = "debitMoney" + month;
                String cr = "creditMoney" + month;
                String set_lswlje = "update Lswlje set "+de+" = coalesce("+de+",0.0) + " + cpy_acm.get("debitMoneyAcm") + ", "+cr+" = coalesce("+cr +",0.0) +"+ cpy_acm.get("creditMoneyAcm") + " where itemNo ='" + itemNo + "' and companyNo ='" + companyNo + "'";
                Query set_query = session.createQuery(set_lswlje);
                set_query.executeUpdate();

                String deq = "debitQty" + month;
                String crq = "creditQty" + month;
                String set_lswlsl = "update Lswlsl set "+deq+" = coalesce("+deq+",0.0) + " + cpy_acm.get("debitQtyAcm") + ", "+crq+" = coalesce("+crq +",0.0) +"+ cpy_acm.get("creditQtyAcm") + " where itemNo ='" + itemNo + "' and companyNo ='" + companyNo + "'";
                Query setq_query = session.createQuery(set_lswlsl);
                setq_query.executeUpdate();

                tx.commit();
                close(session);

                summarizeMoneyData("Lswlje", month, itemNo);
                summarizeQtyData("Lswlsl", month, itemNo);
            }

//            tx.commit();
//            close(session);
        }
        catch(Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
        }
    }


    public void reviewAccounts(String inputDate, String startNo, String endNo){
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();

            String month = inputDate.substring(4, 6);

            Map<String, Map> map = new HashMap<String, Map>();
            Query q = session.createQuery("from Lshsje");
            List<Lshsje> lshsjeList = q.list();
            for(Lshsje lshsje : lshsjeList){
                Map<String, Double> sub_map = new HashMap<String, Double>();
                sub_map.put("debitMoneyAcm", 0.0);
                sub_map.put("creditMoneyAcm", 0.0);
                sub_map.put("debitQtyAcm", 0.0);
                sub_map.put("creditQtyAcm", 0.0);
                String id = lshsje.getItemNo() + ";" + lshsje.getSpNo1() + ";" + lshsje.getSpNo2();
                map.put(id, sub_map);
            }

            //查找核算凭证
            String hql = "from Lshspz where inputDate like '" + inputDate.substring(0, 6) + "%' and voucherNo >= '" + startNo + "' and voucherNo <= '" + endNo + "'";
            Query query = session.createQuery(hql);
            List<Lshspz> lshspzList = query.list();

            for(Lshspz lshspz : lshspzList){
                String id = lshspz.getItemNo() + ";" + lshspz.getSpNo1() + ";" + lshspz.getSpNo2();
                Map m = map.get(id);
                Double money = lshspz.getMoney() == null ? 0 : lshspz.getMoney();
                Double qty = lshspz.getQty() == null ? 0 : lshspz.getQty();
                switch(lshspz.getBkpDirection()){
                    case "J":
                        Double debitMoneyAcm = (Double) m.get("debitMoneyAcm") + money;
                        Double debitQtyAcm = (Double) m.get("debitQtyAcm") + qty;

                        m.put("debitMoneyAcm", debitMoneyAcm);
                        m.put("debitQtyAcm", debitQtyAcm);
                        map.put(id, m);

                        break;
                    case "D":
                        Double creditMoneyAcm = (Double) m.get("creditMoneyAcm") + money;
                        Double creditQtyAcm = (Double) m.get("creditQtyAcm") + qty;

                        m.put("creditMoneyAcm", creditMoneyAcm);
                        m.put("creditQtyAcm", creditQtyAcm);
                        map.put(id, m);

                        break;
                }
            }

            Map acm = null;
            Iterator iter = map.entrySet().iterator();
            while(iter.hasNext()) {
                session = getSession();
                tx = session.beginTransaction();

                Map.Entry entry = (Map.Entry)iter.next();
                // 获取key
                String[] key = ((String)entry.getKey()).split(";");
                String itemNo = key[0];
                String spNo1 = key[1];
                String spNo2 = key[2];
                // 获取value
                acm = (Map)entry.getValue();

                //更新核算金额
                String de = "debitMoney" + month;
                String cr = "creditMoney" + month;
                String set_lskmzd = "update Lshsje set "+de+" = coalesce("+de+",0.0) + " + acm.get("debitMoneyAcm") + ", "+cr+" = coalesce("+cr +",0.0) +"+ acm.get("creditMoneyAcm") + " where itemNo ='" + itemNo + "' and spNo1 = '" + spNo1 + "' and spNo2 = '" + spNo2 + "'";
                Query set_query = session.createQuery(set_lskmzd);
                set_query.executeUpdate();

                //更新核算数量
                String deq = "debitQty" + month;
                String crq = "creditQty" + month;
                String set_lskmsl = "update Lshssl set "+deq+" = coalesce("+deq+",0.0) + " + acm.get("debitQtyAcm") + ", "+crq+" = coalesce("+crq +",0.0) +"+ acm.get("creditQtyAcm") + " where itemNo ='" + itemNo + "' and spNo1 = '" + spNo1 + "' and spNo2 = '" + spNo2 + "'";
                Query setq_query = session.createQuery(set_lskmsl);
                setq_query.executeUpdate();

                tx.commit();
                close(session);

                summarizeMoneyData("Lshsje", month, itemNo);
                summarizeQtyData("Lshssl", month, itemNo);
            }

//            tx.commit();
//            close(session);
        }
        catch(Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
        }
    }


//    void summarizeMoneyData(String table, String month, String itemNo){
//        int m = Integer.parseInt(month);
//        String deb_add = "";
//        String cre_add = "";
//        for(int i = m - 1; i >=1; i--){
//            deb_add += " + debitMoney" + getMonth(i);
//            cre_add += " + creditMoney" + getMonth(i);
//        }
//        String hql = "update " + table
//                + " set balance" + month + " = "
//                + (m == 1 ? " supMoney" : "balance" + getMonth(m - 1))
//                + " + debitMoney" + month + " - creditMoney" + month + ","
//                + " balance = balance" + month + ","
//                + " debitMoneyAcm = debitMoney" + month + deb_add + ","
//                + " creditMoneyAcm = creditMoney" + month + cre_add + ",";
//        for(int i = m + 1; i <= 12; i++){
//            hql += " balance" + getMonth(i) + " = balance" + month + ",";
//        }
//        hql = hql.substring(0, hql.length() - 1) + " where itemNo = '" + itemNo + "'";
//        summarizeData(hql);
//    }

    void summarizeMoneyData(String table, String month, String itemNo){
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            int m = Integer.parseInt(month);
            String deb_add = "";
            String cre_add = "";
            for (int i = m - 1; i >= 1; i--) {
                deb_add += " + debitMoney" + getMonth(i);
                cre_add += " + creditMoney" + getMonth(i);
            }
            String hql = "select debitMoney" + month + deb_add + " from "+ table +" where itemNo = '" + itemNo + "'";
            Query query = session.createSQLQuery(hql);
            List list = query.list();
            double sum_deb = (double)(list.size() == 0 || list.get(0) == null ? 0.0 : list.get(0));

            hql = "select creditMoney" + month + cre_add + " from " + table + " where itemNo = '" + itemNo + "'";
            query = session.createSQLQuery(hql);
            list = query.list();
            double sum_cre = (double)(list.size() == 0 || list.get(0) == null ? 0.0 : list.get(0));

            hql = "select " + (m == 1 ? "supMoney" : "balance" + getMonth(m - 1)) + " + debitMoney" + month + " - creditMoney" + month + " from " + table + " where itemNo = '" + itemNo + "'";
            query = session.createSQLQuery(hql);
            list = query.list();
            double balance_cur = (double)(list.size() == 0 || list.get(0) == null ? 0.0 : list.get(0));

            hql = "update " + table
                    + " set balance" + month + "=" + balance_cur + ","
                    + " balance=balance" + month + ","
                    + " debitMoneyAcm=" + sum_deb + ","
                    + " creditMoneyAcm=" + sum_cre + ",";
            for (int i = m + 1; i <= 12; i++) {
                hql += " balance" + getMonth(i) + " = balance" + month + ",";
            }
            hql = hql.substring(0, hql.length() - 1) + " where itemNo='" + itemNo + "'";
            tx.commit();
            close(session);
            summarizeData(hql);
        }catch (Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
        }
    }

//    void summarizeQtyData(String table, String month, String itemNo){
//        int m = Integer.parseInt(month);
//        String deb_add = "";
//        String cre_add = "";
//        for(int i = m - 1; i >=1; i--){
//            deb_add += "+ debitQty" + getMonth(i);
//            cre_add += "+ creditQty" + getMonth(i);
//        }
//        String hql = "update " + table
//                + " set leftQty" + month + "="
//                + (m == 1 ? "supQty" : "leftQty" + getMonth(m - 1))
//                + " + debitQty" + month + " - creditQty" + month + ","
//                + " leftQty = leftQty" + month + ","
//                + " debitQtyAcm = debitQty" + month + deb_add + ","
//                + " creditQtyAcm = creditQty" + month + cre_add + ",";
//        for(int i = m + 1; i <= 12; i++){
//            hql += "leftQty" + getMonth(i) + " = leftQty" + month + ",";
//        }
//        hql = hql.substring(0, hql.length() - 1) + " where itemNo = '" + itemNo + "'";
//        summarizeData(hql);
//    }

    void summarizeQtyData(String table, String month, String itemNo){
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            int m = Integer.parseInt(month);
            String deb_add = "";
            String cre_add = "";
            for (int i = m - 1; i >= 1; i--) {
                deb_add += " + debitQty" + getMonth(i);
                cre_add += " + creditQty" + getMonth(i);
            }
            String hql = "select debitQty" + month + deb_add + " from "+ table +" where itemNo = '" + itemNo + "'";
            Query query = session.createSQLQuery(hql);
            List list = query.list();
            double sum_deb = (double)(list.size() == 0 || list.get(0) == null ? 0.0 : list.get(0));

            hql = "select creditQty" + month + cre_add + " from " + table + " where itemNo = '" + itemNo + "'";
            query = session.createSQLQuery(hql);
            list = query.list();
            double sum_cre = (double)(list.size() == 0 || list.get(0) == null ? 0.0 : list.get(0));

            hql = "select " + (m == 1 ? "supQty" : "leftQty" + getMonth(m - 1)) + " + debitQty" + month + " - creditQty" + month + " from " + table + " where itemNo = '" + itemNo + "'";
            query = session.createSQLQuery(hql);
            list = query.list();
            double leftQty_cur = (double)(list.size() == 0 || list.get(0) == null ? 0.0 : list.get(0));

            hql = "update " + table
                    + " set leftQty" + month + "=" + leftQty_cur + ","
                    + " leftQty=leftQty" + month + ","
                    + " debitQtyAcm=" + sum_deb + ","
                    + " creditQtyAcm=" + sum_cre + ",";
            for (int i = m + 1; i <= 12; i++) {
                hql += " leftQty" + getMonth(i) + " = leftQty" + month + ",";
            }
            hql = hql.substring(0, hql.length() - 1) + " where itemNo='" + itemNo + "'";
            tx.commit();
            close(session);
            summarizeData(hql);
        }catch (Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
        }
    }

    void summarizeData(String hql){
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.executeUpdate();
            tx.commit();
            close(session);
        }
        catch(Exception e){
            tx.rollback();
            close(session);
            e.printStackTrace();
        }
    }

    String getMonth(int m){
        return (m < 10 ? "0" + Integer.toString(m) : Integer.toString(m));
    }
}
