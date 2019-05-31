package com.bjut.ssh.dao.ReportManagement;

import com.bjut.ssh.entity.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cache.spi.QueryResultsRegion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ReportBaseDao {
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return this.sessionFactory.openSession();
    }

    public void close(Session session){
        if(session != null)
            session.close();
    }

    /**
     * 根据月份查询报表 month格式为01、02...
     * @param year
     * @param month
     * @return
     */
    public List<Lcbzd> findReportByYearAndMonth(String year,String month) {
        Session session = null;
        Transaction tx = null;
        List<Lcbzd> lswlflList = null;
        String date = year+"_" + month + "%";
        try {
            session = getSession();
            tx = session.beginTransaction();
            if(isYearNow(year)){
                String hql="from Lcbzd where rptDate like ?";
                Query query = session.createQuery(hql);
                query.setParameter(0,date);
                lswlflList= query.list();
            }else{
                String sql = "SELECT * from Lcbzd_"+ year.trim() +" WHERE rptDate LIKE '"+ date+"'";
                Query sqlQuery = session.createSQLQuery(sql).addEntity(Lcbzd.class);
                lswlflList = sqlQuery.list();
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            if(lswlflList==null)
                lswlflList = new ArrayList<Lcbzd>();
            return lswlflList;
        }
    }

    /**
     * 根据报表编号和日期查询报表基本信息 rptDate格式为2018-01-01
     * @param rptNo
     * @param rptDate
     * @return
     */
    public Lcbzd findRptByNoAndDate(String rptNo,String rptDate) {
        Session session = null;
        Transaction tx = null;
        List<Lcbzd> lcbzdList = null;
        String date = rptDate.substring(0,7)+"%";
        System.out.println("=================Dao"+date);
        try {
            session = getSession();
            tx = session.beginTransaction();
            if(isYearNow(rptDate.substring(0,4))){
                String hql="from Lcbzd where rptNo=? and rptDate like ?";
                Query query = session.createQuery(hql);
                query.setParameter(0,rptNo);
                query.setParameter(1,date);
                lcbzdList= query.list();
            }else{
                String sql = "SELECT * from Lcbzd_"+ rptDate.substring(0,4) +" WHERE rptNo='"+rptNo+"' and  rptDate LIKE '"+ date+"'";
                Query sqlQuery = session.createSQLQuery(sql).addEntity(Lcbzd.class);
                lcbzdList = sqlQuery.list();
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            if(lcbzdList!=null && lcbzdList.size()>0)
                return lcbzdList.get(0);
            else
                return null;
        }
    }

    /**
     * 根据报表编号数组和日期查询报表基本信息 rptDate格式为2018-01-01
     * @param rptNos
     * @param rptDate
     * @return
     */
    public List<Lcbzd> findRptByNoAndDate(String[] rptNos,String rptDate) {
        Session session = null;
        Transaction tx = null;
        List<Lcbzd> lcbzdList = null;
        String date = rptDate.substring(0,7)+"%";
        String rptNosTemp="";
        if (rptNos==null||rptNos.length==0) {
            rptNosTemp = "''";
        }else{
            for (int i = 0; i < rptNos.length ; i++) {
                if(i==rptNos.length-1)
                    rptNosTemp = rptNosTemp+ "'"+rptNos[i]+"'";
                else
                    rptNosTemp = rptNosTemp+ "'"+rptNos[i]+"',";
            }
        }
        System.out.println("=================Dao"+date);
        try {
            session = getSession();
            tx = session.beginTransaction();
            if(isYearNow(rptDate.substring(0,4))){
                String hql="from Lcbzd where rptNo in ("+rptNosTemp+") and rptDate like ?";
                Query query = session.createQuery(hql);
                query.setParameter(0,date);
                lcbzdList= query.list();
            }else{
                String sql = "SELECT * from Lcbzd_"+ rptDate.substring(0,4) +" WHERE rptNo in ("+rptNosTemp+") and  rptDate LIKE '"+ date+"'";
                Query sqlQuery = session.createSQLQuery(sql).addEntity(Lcbzd.class);
                lcbzdList = sqlQuery.list();
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            if(lcbzdList!=null && lcbzdList.size()>0)
                return lcbzdList;
            else
                return null;
        }
    }

    /**
     * 根据报表编号和日期查询报表数据 rptDate格式为2018-01-01
     * @param rptNo
     * @param rptDate
     * @return
     */
    public List<Lcdyzd> findRptDataByNoAndDate(String rptNo,String rptDate){
        Session session = null;
        Transaction tx = null;
        List<Lcdyzd> lcdyzdList = null;
        String date = rptDate.substring(0,7)+"%";
        System.out.println("=================Dao"+date);
        try {
            session = getSession();
            tx = session.beginTransaction();
            if(isYearNow(rptDate.substring(0,4))){
                String hql="from Lcdyzd where rptNo=? and rptDate like ?";
                Query query = session.createQuery(hql);
                query.setParameter(0,rptNo);
                query.setParameter(1,date);
                lcdyzdList= query.list();
            }else{
                String sql = "SELECT * from Lcdyzd_"+ rptDate.substring(0,4) +" WHERE rptNo='"+rptNo+"' and  rptDate LIKE '"+ date+"'";
                Query sqlQuery = session.createSQLQuery(sql).addEntity(Lcdyzd.class);
                lcdyzdList = sqlQuery.list();
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            if(lcdyzdList!=null && lcdyzdList.size()>0)
                return lcdyzdList;
            else
                return null;
        }
    }

    /**
     * 根据报表编号数组和日期查询报表数据 rptDate格式为2018-01-01
     * @param rptNos
     * @param rptDate
     * @return
     */
    public List<Lcdyzd> findRptDataByNoAndDate(String[] rptNos,String rptDate){
        Session session = null;
        Transaction tx = null;
        List<Lcdyzd> lcdyzdList = null;
        String date = rptDate.substring(0,7)+"%";
        String rptNosTemp="";
        if (rptNos==null||rptNos.length==0) {
            rptNosTemp = "''";
        }else{
            for (int i = 0; i < rptNos.length ; i++) {
                if(i==rptNos.length-1)
                    rptNosTemp = rptNosTemp+ "'"+rptNos[i]+"'";
                else
                    rptNosTemp = rptNosTemp+ "'"+rptNos[i]+"',";
            }
        }
        System.out.println("=================Dao"+date);
        try {
            session = getSession();
            tx = session.beginTransaction();
            if(isYearNow(rptDate.substring(0,4))){
                String hql="from Lcdyzd where rptNo in ("+rptNosTemp+") and rptDate like ?";
                Query query = session.createQuery(hql);
                query.setParameter(0,date);
                lcdyzdList= query.list();
            }else{
                String sql = "SELECT * from Lcdyzd_"+ rptDate.substring(0,4) +" WHERE rptNo in ("+rptNosTemp+") and  rptDate LIKE '"+ date+"'";
                Query sqlQuery = session.createSQLQuery(sql).addEntity(Lcdyzd.class);
                lcdyzdList = sqlQuery.list();
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            if(lcdyzdList!=null && lcdyzdList.size()>0)
                return lcdyzdList;
            else
                return null;
        }
    }

    /**
     * 保存或修改lcbzd表和lcdyzd表
     * @param lcbzd
     * @param lcdyzdList
     * @return
     */
    public Msg saveOrUpdateReport(Lcbzd lcbzd, List<Lcdyzd> lcdyzdList){
        Msg msg = new Msg();
        msg.setCode(100);
        Session session = null;
        Transaction tx = null;
        if(lcbzd==null||lcdyzdList==null)
            return msg;
        try {
            session = getSession();
            tx = session.beginTransaction();

            String hqlString1 = "select MAX(bzdAutoId) from Lcbzd";
            Query query1 = session.createQuery(hqlString1);
            String hqlString2 = "select MAX(dyzdAutoId) from Lcdyzd ";
            Query query2 = session.createQuery(hqlString2);
            int bzdId=0,dyzdId=0;
            if(lcbzd.getBzdAutoId()==null){
                Object bzdIdMax = query1.uniqueResult();
                if(bzdIdMax!=null)
                    bzdId=Integer.parseInt(bzdIdMax.toString());
                lcbzd.setBzdAutoId(bzdId+1);
            }
            System.out.println("=================Dao"+lcbzd.getRptNo());
            if(lcbzd!=null){
                session.saveOrUpdate(lcbzd);
                if(lcdyzdList!=null){
                    for (int i=0;i<lcdyzdList.size();i++){
                        Lcdyzd lcdyzd = lcdyzdList.get(i);

                        if(lcdyzd.getDyzdAutoId()==null){
                            Object dyzdIdMax = query2.uniqueResult();
                            if(dyzdIdMax!=null)
                                dyzdId=Integer.parseInt(dyzdIdMax.toString());
                            lcdyzd.setDyzdAutoId(dyzdId+1);
                        }
                        session.saveOrUpdate(lcdyzd);
                        if (i % 50 == 0) {
                            session.flush();
                            session.clear();
                        }
                    }
                }
            }else{
                msg.setCode(101);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
        }
        msg.add("lcbzd",lcbzd);
        msg.add("lcdyzdList",lcdyzdList);
        return msg;
    }

    /**
     * 保存或修改lcbzd表和lcdyzd表
     * @param lcbzdList
     * @param lcdyzdList
     * @return
     */
    public boolean saveOrUpdateReport(List<Lcbzd> lcbzdList, List<Lcdyzd> lcdyzdList){
        Session session = null;
        Transaction tx = null;
        boolean suc = true;
        if(lcbzdList==null||lcdyzdList==null)
            return suc;
        try {
            session = getSession();
            tx = session.beginTransaction();

            String hqlString1 = "select MAX(bzdAutoId) from Lcbzd";
            Query query1 = session.createQuery(hqlString1);
            String hqlString2 = "select MAX(dyzdAutoId) from Lcdyzd ";
            Query query2 = session.createQuery(hqlString2);

            int bzdId=0,dyzdId=0;
            if(lcbzdList!=null && lcbzdList.size()>0){
                for (int i=0;i<lcbzdList.size();i++){
                    Lcbzd lcbzd = lcbzdList.get(i);
                    if(lcbzd.getBzdAutoId()==null){
                        Object bzdIdMax = query1.uniqueResult();
                        if(bzdIdMax!=null)
                            bzdId=Integer.parseInt(bzdIdMax.toString());
                        lcbzd.setBzdAutoId(bzdId+1);
                    }
                    session.saveOrUpdate(lcbzd);
                }

                if(lcdyzdList!=null){
                    for (int i=0;i<lcdyzdList.size();i++){
                        Lcdyzd lcdyzd = lcdyzdList.get(i);
                        if(lcdyzd.getDyzdAutoId()==null){
                            Object dyzdIdMax = query2.uniqueResult();
                            if(dyzdIdMax!=null)
                                dyzdId=Integer.parseInt(dyzdIdMax.toString());
                            lcdyzd.setDyzdAutoId(dyzdId+1);
                        }
                        session.saveOrUpdate(lcdyzd);
                        if (i % 50 == 0) {
                            session.flush();
                            session.clear();
                        }
                    }
                }
            }else{
                suc = false;
            }
            tx.commit();
        } catch (Exception e) {
            suc = false;
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
        }
        return suc;
    }

    /**
     * 根据报表编号和报表日期删除
     * @param rptNo
     * @param rptDate
     * @return
     */
    public boolean deleteRpt(String rptNo,String rptDate){
        Session session = null;
        Transaction tx = null;
        String date = rptDate.substring(0,7)+"%";
        boolean suc = true;
        try {
            session = getSession();
            tx = session.beginTransaction();
            String hqlString1 = "delete from Lcbzd where rptNo=? and rptDate like ?";
            Query query1 = session.createQuery(hqlString1);
            String hqlString2 = "delete from Lcdyzd where rptNo=? and rptDate like ?";
            Query query2 = session.createQuery(hqlString2);
            query1.setParameter(0,rptNo);
            query1.setParameter(1,date);
            query2.setParameter(0,rptNo);
            query2.setParameter(1,date);
            query1.executeUpdate();
            query2.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
        }
        return suc;
    }

    /**
     * 删除大于报表行数的多余数据
     * @param lcbzd
     * @return
     */
    public boolean deleteUnUseRptData(Lcbzd lcbzd){
        Session session = null;
        Transaction tx = null;
        String rptNo = lcbzd.getRptNo();
        String rptDate = lcbzd.getRptDate();
        String date = rptDate.substring(0,7)+"%";
        boolean suc = true;
        try {
            session = getSession();
            tx = session.beginTransaction();
            List<String> rowList=new ArrayList<String>();
            for (int i = 0; i < lcbzd.getTotalRules(); i++) {
                rowList.add(String.valueOf(i));
            }
            List<String> colList=new ArrayList<String>();
            for (int i = 0; i < lcbzd.getRptCol(); i++) {
                colList.add(String.valueOf(i));
            }
            String hqlString1 = "delete from Lcdyzd where rptNo=? and rptDate like ? and (rowInfoNo not in (:rowList) or colInfoNo not in (:colList))";
            Query query1 = session.createQuery(hqlString1);
            query1.setParameter(0,rptNo);
            query1.setParameter(1,date);
            query1.setParameterList("rowList",rowList);
            query1.setParameterList("colList",colList);
            query1.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
        }
        return suc;
    }

    /**
     * 查询Lsconf表一条记录--根据confKey
     * @param confKey
     * @return
     */
    public Lsconf getLsconfData(String confKey) {
        Session session = null;
        Transaction tx = null;
        List<Lsconf> lsconfList = new ArrayList<Lsconf>();
        try {
            session = getSession();
            tx = session.beginTransaction();
            String hql="from Lsconf where confKey=?";
            Query query = session.createQuery(hql);
            query.setParameter(0,confKey);
            lsconfList= query.list();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            if(lsconfList!=null && lsconfList.size()>0)
                return lsconfList.get(0);
            else
                return null;
        }
    }

    /**
     * 修改Lsconf表一条记录--根据confKey
     * @param confKey
     * @param confValue
     */
    public void setLsconfData(String confKey,String confValue) {
        Session session = null;
        Transaction tx = null;
        List<Lsconf> lsconfList = new ArrayList<Lsconf>();
        try {
            session = getSession();
            tx = session.beginTransaction();
            String hql="from Lsconf where confKey=?";
            Query query = session.createQuery(hql);
            query.setParameter(0,confKey);
            lsconfList= query.list();
            Lsconf lsconf = lsconfList.get(0);
            lsconf.setConfValue(confValue);
            session.update(lsconf);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
        }
    }

    /**
     * 年结--新建数据库表，清空原表内容
     * @param rptYear
     * @return
     */
    public boolean yearCopy(String rptYear){
        Session session = null;
        Transaction tx = null;
        boolean flag = true;
        try {
            String createSql1 = "CREATE TABLE lcdyzd_" + rptYear + " LIKE lcdyzd";
            String insertSql1 = "INSERT INTO lcdyzd_" + rptYear + " (SELECT * FROM lcdyzd)";
            String hql1="delete from Lcdyzd";
            String createSql2 = "CREATE TABLE lcbzd_" + rptYear + " LIKE lcbzd";
            String insertSql2 = "INSERT INTO lcbzd_" + rptYear + " (SELECT * FROM lcbzd)";
            String hql2="delete from Lcbzd";

            session = getSession();
            tx = session.beginTransaction();
            
            Query createQuery1 = session.createSQLQuery(createSql1);
            createQuery1.executeUpdate();
            Query insertQuery1 = session.createSQLQuery(insertSql1);
            insertQuery1.executeUpdate();
            Query query1 = session.createQuery(hql1);
            query1.executeUpdate();
            Query createQuery2 = session.createSQLQuery(createSql2);
            createQuery2.executeUpdate();
            Query insertQuery2 = session.createSQLQuery(insertSql2);
            insertQuery2.executeUpdate();
            Query query2 = session.createQuery(hql2);
            query2.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            flag = false;
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
        }
        return flag;
    }

    /**
     *@Description 获取科目结构
     *@Param []
     *@return java.lang.String
     **/
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

    /**
     * 根据hql查询单个字段
     * @param hql
     * @return
     */
    public List<Object> getFormulaResult(String hql){
        List<Object> result = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery(hql);
            result= query.list();
            System.out.println(result.size()+"===========================================================");
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

    /**
     * 根据hql查询多个字段
     * @param hql
     * @return
     */
    public List<Object[]> getFormulaResults(String hql){
        List<Object[]> result = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            Query query = session.createQuery(hql);
            result= query.list();
            System.out.println(result.size()+"===========================================================");
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

    /**
     * 获取所有核算字典记录
     * @return
     */
    public List<Lshszd> getAllHszd(){
        Session session = null;
        Transaction tx = null;
        List<Lshszd> lshszdList = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            String hql="from Lshszd order by spNo asc";
            Query query = session.createQuery(hql);
            lshszdList= query.list();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            if(lshszdList!=null && lshszdList.size()>0)
                return lshszdList;
            else
                return null;
        }
    }

    /**
     * 获取所有往来单位记录
     * @return
     */
    public List<Lswldw> getAllWldw(){
        Session session = null;
        Transaction tx = null;
        List<Lswldw> lswldwList = null;
        try {
            session = getSession();
            tx = session.beginTransaction();
            String hql="from Lswldw order by companyNo asc";
            Query query = session.createQuery(hql);
            lswldwList= query.list();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            if(lswldwList!=null && lswldwList.size()>0)
                return lswldwList;
            else
                return null;
        }
    }

    /**
     * 判断是否是当年报表
     * @param date
     * @return
     */
    public boolean isYearNow(String date){
        boolean res = false;
        Session session = null;
        Transaction tx = null;
        List<Lsconf> lsconfList = new ArrayList<Lsconf>();
        try {
            session = getSession();
            tx = session.beginTransaction();
            String hql="from Lsconf where confKey='Rpt_Year_Now'";
            Query query = session.createQuery(hql);
            lsconfList= query.list();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            //关闭操作
            close(session);
            if(date!=null && lsconfList.size()>0 && lsconfList.get(0).getConfValue().trim().equals(date.trim()))
                res = true;
        }
        return res;
    }
}
