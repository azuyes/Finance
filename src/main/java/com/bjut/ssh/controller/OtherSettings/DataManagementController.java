package com.bjut.ssh.controller.OtherSettings;

import com.bjut.ssh.DatabaseBackup.DatabaseBackup;
import com.bjut.ssh.getProperties.getProperties;
import com.bjut.ssh.setProperties.setProperties;
import com.bjut.ssh.entity.*;
import com.bjut.ssh.service.OtherSettings.DataManagementService;
import com.bjut.ssh.service.OtherSettings.UserManagementService;
import com.bjut.ssh.service.ReportManagement.ReportBaseService;
import com.bjut.ssh.serviceImpl.ReportManagement.ReportBaseServiceImpl;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * @Title: DataManagementController
 * @Description: 数据管理
 * @Author: LYH
 * @CreateDate: 2018/4/13 10:49
 * @Version: 1.0
 */

@Controller
@RequestMapping("/DataManagement")

public class DataManagementController {

    @Autowired
    DataManagementService dataManagementService;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    ReportBaseService reportBaseService;

    Session getsession() {

        return this.sessionFactory.openSession();
    }
    public void close(Session session){
        if(session != null)
            session.close();
    }

    @RequestMapping("/changeDatabase/{datasource}")
    public void changeDatabase(@PathVariable("datasource") String datasource){
        dataManagementService.changeDatabase(datasource);
    }

    @RequestMapping("/createDatabase/{name}")
    public void createDatabase(@PathVariable("name") String name, HttpServletRequest request, HttpServletResponse response) {
        dataManagementService.createDatabase(name, request, response);
    }

    /**
     *@author LYH
     *@Description 上传文件
     *@Date 2018/5/4 16:05
     *@Param [upLoadfile]
     *@return void
     **/

    @RequestMapping("/upLoadFile")
    @ResponseBody
    public Boolean upLoadFile(@RequestParam("path")String path,@RequestParam("tableName")String table_Name)
            throws IllegalStateException, IOException{
        if (table_Name.equals("")||path.equals("")){//必须选择数据库表和文件
            return false;
        }else {
            String userName =getProperties.getProperties("jdbc.username");
            String pwd = getProperties.getProperties("jdbc.password");
            String database = getProperties.getProperties("jdbc.url");
            String driver = getProperties.getProperties("jdbc.driver");
            String[] table = table_Name.split(",");
            DatabaseBackup bak = new DatabaseBackup(userName, pwd);
            for(int i= 0;i<table.length;i++) {
                if(table[i].equals("财务功能管理")){
                    String allName = "lsconf,lspzbh,lskmzd,lskmsl,lszczd,lszcdy,lsszzd,lspzk1,lsyspz,lshspz,lshssl,lshsje,lshsfl,lshszd,lswlje,lswlfl,lswlsl,lswldw,lcbzd,lcdyzd";
                    String[] allTable = allName.split(",");
                    int num = allTable.length;
                    for (int j = 0; j <num ; j++) {
                        bak.restore(driver,database,path, allTable[j]);
                    }
                }else if(table[i].equals("账务处理")){
                    String accName = "lsconf,lspzbh,lskmzd,lskmsl,lszczd,lszcdy,lsszzd,lspzk1,lsyspz,lshspz,lshssl,lshsje,lshsfl,lshszd,lswlje,lswlfl,lswlsl,lswldw";
                    String[] accTable = accName.split(",");
                    int num = accTable.length;
                    for (int j = 0; j <num ; j++) {
                        bak.restore(driver,database,path,accTable[j]);

                    }
                }else if(table[i].equals("报表管理")){
                    String rptName = "lcbzd,lcdyzd";
                    String[] rptTable = rptName.split(",");
                    int num = rptTable.length;
                    for (int j = 0; j <num ; j++) {
                        bak.restore(driver,database,path, rptTable[j]);

                    }
                }
            }
            return true;
        }
    }


    @RequestMapping("/downloadFile")
    @ResponseBody
    public Boolean downloadFile (@RequestParam("path1")String path,@RequestParam("tableName1")String tablename)
            throws IllegalStateException, IOException{

        System.out.println("*********************downloadFile");
        System.out.println(path);
        File targetFile = new File(path);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        if (tablename.equals("")||path.equals("")){//必须选择数据库表和文件
            return false;
        }else {
            String userName =getProperties.getProperties("jdbc.username");
            String pwd = getProperties.getProperties("jdbc.password");
            String database = getProperties.getProperties("jdbc.url");
            String driver = getProperties.getProperties("jdbc.driver");
            String[] table = tablename.split(",");
            DatabaseBackup bak = new DatabaseBackup(userName, pwd);
            for(int i= 0;i<table.length;i++) {
                if(table[i].equals("财务功能管理")){
                    String allName = "lsconf,lspzbh,lskmzd,lskmsl,lszczd,lszcdy,lsszzd,lspzk1,lsyspz,lshspz,lshssl,lshsje,lshsfl,lshszd,lswlje,lswlfl,lswlsl,lswldw,lcbzd,lcdyzd";
                    String[] allTable = allName.split(",");
                    int num = allTable.length;
                    for (int j = 0; j <num ; j++) {
                        bak.backup(driver,database,path, allTable[j]);
                    }
                }else if(table[i].equals("账务处理")){
                    String accName = "lsconf,lspzbh,lskmzd,lskmsl,lszczd,lszcdy,lsszzd,lspzk1,lsyspz,lshspz,lshssl,lshsje,lshsfl,lshszd,lswlje,lswlfl,lswlsl,lswldw";
                    String[] accTable = accName.split(",");
                    int num = accTable.length;
                    for (int j = 0; j <num ; j++) {
                        bak.backup(driver,database,path,accTable[j]);
                    }
                }else if(table[i].equals("报表管理")){
                    String rptName = "lcbzd,lcdyzd";
                    String[] rptTable = rptName.split(",");
                    int num = rptTable.length;
                    for (int j = 0; j <num ; j++) {
                        bak.backup(driver,database,path,rptTable[j]);
                    }
                }
            }
            return true;
        }

    }

    //中油数据迁移
    @RequestMapping("/moveData")
    @ResponseBody
    public Boolean moveData(@RequestParam("path") String path)
            throws IllegalStateException, IOException, ClassNotFoundException {
        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        if (file.exists()) {
            Session session = null;
            session = getsession();
            Query year_query = session.createQuery("from Lsconf where confKey = 'current_date'");
            Lsconf  current_date = (Lsconf) year_query.uniqueResult();
            String flag = "";
            String year = current_date == null ? flag : current_date.getConfValue();
            close(session);
            if(year.equals("")){
                try (FileReader reader = new FileReader(path+"/LSTABLE.TXT");
                     BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
                ) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        // 一次读入一行数据
                        String[] s = line.split("\t");
                        String tableName = s[0];
                        String duizhao_name= s[1];
                        dataMove(tableName,duizhao_name,path);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                try (FileReader reader = new FileReader(path+"/LSFLAG@@.TXT");
                     BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
                ) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        // 一次读入一行数据
                        String[] s = line.split(",");
                        String old_date = s[1];
                        System.out.println(old_date);
                        System.out.println(year);
                        int ret = old_date.compareTo(year);
                        System.out.println(ret);
                        if(ret == 1){
                            year = year.substring(0,4);
                            Session session1 = null;
                            Transaction tx = null;
                            try{
                                session1 = getsession();
                                tx = session1.beginTransaction();

                                Query create_lsconf = session1.createSQLQuery("create table " + "lsconf" + year + " select * from Lsconf");
                                Query delete_lsconf = session1.createQuery("delete from Lsconf");
                                create_lsconf.executeUpdate();
                                delete_lsconf.executeUpdate();

                                Query create_lspzbh = session1.createSQLQuery("create table " + "lspzbh" + year + " select * from Lspzbh");
                                Query delete_lspzbh = session1.createQuery("delete from Lspzbh");
                                create_lspzbh.executeUpdate();
                                delete_lspzbh.executeUpdate();

                                Query create_lskmzd = session1.createSQLQuery("create table " + "lskmzd" + year + " select * from Lskmzd");
                                Query delete_lskmzd = session1.createQuery("delete from Lskmzd ");
                                create_lskmzd.executeUpdate();
                                delete_lskmzd.executeUpdate();

                                Query create_lskmsl = session1.createSQLQuery("create table " + "lskmsl" + year + " select * from Lskmsl");
                                Query delete_lskmsl = session1.createQuery("delete from Lskmsl");
                                create_lskmsl.executeUpdate();
                                delete_lskmsl.executeUpdate();

                                Query create_lszczd = session1.createSQLQuery("create table " + "lszczd" + year + " select * from Lszczd");
                                Query delete_lszczd = session1.createQuery("delete from Lszczd");
                                create_lszczd.executeUpdate();
                                delete_lszczd.executeUpdate();

                                Query create_lszcdy = session1.createSQLQuery("create table " + "lszcdy" + year + " select * from Lszcdy");
                                Query delete_lszcdy = session1.createQuery("delete from Lszcdy ");
                                create_lszcdy.executeUpdate();
                                delete_lszcdy.executeUpdate();

                                Query create_lsszzd = session1.createSQLQuery("create table " + "lsszzd" + year + " select * from Lsszzd");
                                Query delete_lsszzd = session1.createQuery("delete from Lsszzd ");
                                create_lsszzd.executeUpdate();
                                delete_lsszzd.executeUpdate();

                                Query create_lspzk1 = session1.createSQLQuery("create table " + "lspzk1" + year + " select * from Lspzk1");
                                Query delete_lspzk1 = session1.createQuery("delete from Lspzk1 ");
                                create_lspzk1.executeUpdate();
                                delete_lspzk1.executeUpdate();

                                Query create_lsyspz = session1.createSQLQuery("create table " + "lsyspz" + year + " select * from Lsyspz");
                                Query delete_lsyspz = session1.createQuery("delete from Lsyspz ");
                                create_lsyspz.executeUpdate();
                                delete_lsyspz.executeUpdate();

                                Query create_lshspz = session1.createSQLQuery("create table " + "lshspz" + year + " select * from Lshspz");
                                Query delete_lshspz = session1.createQuery("delete from Lshspz ");
                                create_lshspz.executeUpdate();
                                delete_lshspz.executeUpdate();

                                Query create_lshssl = session1.createSQLQuery("create table " + "lshssl" + year + " select * from Lshssl");
                                Query delete_lshssl = session1.createQuery("delete from Lshssl ");
                                create_lshssl.executeUpdate();
                                delete_lshssl.executeUpdate();

                                Query create_lshsje = session1.createSQLQuery("create table " + "lshsje" + year + " select * from Lshsje");
                                Query delete_lshsje = session1.createQuery("delete from Lshsje ");
                                create_lshsje.executeUpdate();
                                delete_lshsje.executeUpdate();

                                Query create_lshsfl = session1.createSQLQuery("create table " + "lshsfl" + year + " select * from Lshsfl");
                                Query delete_lshsfl = session1.createQuery("delete from Lshsfl ");
                                create_lshsfl.executeUpdate();
                                delete_lshsfl.executeUpdate();

                                Query create_lshszd = session1.createSQLQuery("create table " + "lshszd" + year + " select * from Lshszd");
                                Query delete_lshszd = session1.createQuery("delete from Lshszd ");
                                create_lshszd.executeUpdate();
                                delete_lshszd.executeUpdate();

                                Query create_lswlje = session1.createSQLQuery("create table " + "lswlje" + year + " select * from Lswlje");
                                Query delete_lswlje = session1.createQuery("delete from Lswlje ");
                                create_lswlje.executeUpdate();
                                delete_lswlje.executeUpdate();

                                Query create_lswlfl = session1.createSQLQuery("create table " + "lswlfl" + year + " select * from Lswlfl");
                                Query delete_lswlfl = session1.createQuery("delete from Lswlfl ");
                                create_lswlfl.executeUpdate();
                                delete_lswlfl.executeUpdate();

                                Query create_lswlsl = session1.createSQLQuery("create table " + "lswlsl" + year + " select * from Lswlsl");
                                Query delete_lswlsl = session1.createQuery("delete from Lswlsl ");
                                create_lswlsl.executeUpdate();
                                delete_lswlsl.executeUpdate();

                                Query create_lswldw = session1.createSQLQuery("create table " + "lswldw" + year + " select * from Lswldw");
                                Query delete_lswldw = session1.createQuery("delete from Lswldw ");
                                create_lswldw.executeUpdate();
                                delete_lswldw.executeUpdate();

                                Query create_lcbzd = session1.createSQLQuery("create table " + "lcbzd" + year + " select * from Lcbzd");
                                Query delete_lcbzd = session1.createQuery("delete from Lcbzd ");
                                create_lcbzd.executeUpdate();
                                delete_lcbzd.executeUpdate();

                                Query create_lcdyzd = session1.createSQLQuery("create table " + "lcdyzd" + year + " select * from Lcdyzd");
                                Query delete_lcdyzd = session1.createQuery("delete from Lcdyzd ");
                                create_lcdyzd.executeUpdate();
                                delete_lcdyzd.executeUpdate();

                                System.out.println("复制&清空数据库");
                                tx.commit();
                                close(session1);
                                try (FileReader reader1 = new FileReader(path+"/LSTABLE.TXT");
                                     BufferedReader br1 = new BufferedReader(reader1) // 建立一个对象，它把文件内容转成计算机能读懂的语言
                                ) {
                                    String line1;
                                    while ((line1 = br1.readLine()) != null) {
                                        // 一次读入一行数据
                                        String[] s1 = line1.split("\t");
                                        String tableName1 = s1[0];
                                        String duizhao_name1= s1[1];
                                        dataMove(tableName1,duizhao_name1,path);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            catch(Exception e){
                                tx.rollback();
                                close(session1);
                                e.printStackTrace();
                            }
                        }else {
                            return false;
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }else {
            System.out.println("文件不存在!");
            return false;
        }

        return true;
    }


    //备份历史数据
    @RequestMapping("/historyDownloadFile")
    @ResponseBody
    public Boolean historyDownloadFile (@RequestParam("path")String path,@RequestParam("tableName")String tablename,@RequestParam("year")String year)
            throws IllegalStateException, IOException{
        File targetFile = new File(path);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        if (tablename.equals("")||path.equals("")){//必须选择数据库表和文件
            return false;
        }else {
            String userName =getProperties.getProperties("jdbc.username");
            String pwd = getProperties.getProperties("jdbc.password");
            String database = getProperties.getProperties("jdbc.url");
            String driver = getProperties.getProperties("jdbc.driver");
            String[] table = tablename.split(",");
            DatabaseBackup bak = new DatabaseBackup(userName, pwd);
            for(int i= 0;i<table.length;i++) {
                if(table[i].equals("财务功能管理")){
                    String allName = "lsconf,lspzbh,lskmzd,lskmsl,lszczd,lszcdy,lsszzd,lspzk1,lsyspz,lshspz,lshssl,lshsje,lshsfl,lshszd,lswlje,lswlfl,lswlsl,lswldw,lcbzd,lcdyzd";
                    String[] allTable = allName.split(",");
                    int num = allTable.length;
                    for (int j = 0; j <num ; j++) {
                        bak.backupDatabase(driver,database,path, allTable[j],year);
                    }
                }else if(table[i].equals("账务处理")){
                    String accName = "lsconf,lspzbh,lskmzd,lskmsl,lszczd,lszcdy,lsszzd,lspzk1,lsyspz,lshspz,lshssl,lshsje,lshsfl,lshszd,lswlje,lswlfl,lswlsl,lswldw";
                    String[] accTable = accName.split(",");
                    int num = accTable.length;
                    for (int j = 0; j <num ; j++) {
                        bak.backupDatabase(driver,database,path,accTable[j],year);
                    }
                }else if(table[i].equals("报表管理")){
                    String rptName = "lcbzd,lcdyzd";
                    String[] rptTable = rptName.split(",");
                    int num = rptTable.length;
                    for (int j = 0; j <num ; j++) {
                        bak.backupDatabase(driver,database,path,rptTable[j],year);
                    }
                }
            }
            return true;
        }

    }


    //恢复历史数据
    @RequestMapping("/historyupLoadFile")
    @ResponseBody
    public Boolean historyupLoadFile(@RequestParam("path")String path,@RequestParam("tableName")String tablename,@RequestParam("year")String year)
            throws IllegalStateException, IOException{

        if (tablename.equals("")||path.equals("")){//必须选择数据库表和文件
            return false;
        }else {
            String userName =getProperties.getProperties("jdbc.username");
            String pwd = getProperties.getProperties("jdbc.password");
            String database = getProperties.getProperties("jdbc.url");
            String driver = getProperties.getProperties("jdbc.driver");
            String[] table = tablename.split(",");
            DatabaseBackup bak = new DatabaseBackup(userName, pwd);
            for(int i= 0;i<table.length;i++) {
                if(table[i].equals("财务功能管理")){
                    String allName = "lsconf,lspzbh,lskmzd,lskmsl,lszczd,lszcdy,lsszzd,lspzk1,lsyspz,lshspz,lshssl,lshsje,lshsfl,lshszd,lswlje,lswlfl,lswlsl,lswldw,lcbzd,lcdyzd";
                    String[] allTable = allName.split(",");
                    int num = allTable.length;
                    for (int j = 0; j <num ; j++) {
                        bak.restoreDatabase(driver,database,path,year,allTable[j]);
                    }
                }else if(table[i].equals("账务处理")){
                    String accName = "lsconf,lspzbh,lskmzd,lskmsl,lszczd,lszcdy,lsszzd,lspzk1,lsyspz,lshspz,lshssl,lshsje,lshsfl,lshszd,lswlje,lswlfl,lswlsl,lswldw";
                    String[] accTable = accName.split(",");
                    int num = accTable.length;
                    for (int j = 0; j <num ; j++) {
                        bak.restoreDatabase(driver,database,path,year,accTable[j]);
                    }
                }else if(table[i].equals("报表管理")){
                    String rptName = "lcbzd,lcdyzd";
                    String[] rptTable = rptName.split(",");
                    int num = rptTable.length;
                    for (int j = 0; j <num ; j++) {
                        bak.restoreDatabase(driver,database,path,year,rptTable[j]);
                    }
                }
            }
            return true;
        }
    }

    //修改jdbc配置文件
    @RequestMapping("/setup/{userName}/{userPass}/{driver}")
    @ResponseBody
    public Msg setup(@PathVariable("userName")String userName,@PathVariable("userPass")String userPass,@PathVariable("driver")String driver){

        setProperties.setProperties(driver,userName,userPass);

        return Msg.success();
    }

    //数据迁移
    public void dataMove(String tableName, String duizhaoName,String path){
        String filename = tableName.toLowerCase();//文件名转换成小写单词
            System.out.println(path+"\\"+duizhaoName);
            switch (filename){//根据选择判断上传的表名
                case "lswlfl":
                    if(filename.equals("lswlfl")){
                        try (FileReader reader = new FileReader(path+"/"+duizhaoName);
                             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
                        ) {
                            String lineTxt = null;
                            Lswlfl lswlfl = null;
                            long count= 0;
                            List<Lswlfl> lswlflList = new ArrayList<Lswlfl>();
                            while((lineTxt = br.readLine())!= null){
                                lswlfl = new Lswlfl();
                                String[] s = lineTxt.split("\t");
                                lswlfl.setCatNo1(s[0]);
                                lswlfl.setCatLevel(s[1]);
                                lswlfl.setFinLevel(s[2]);
                                lswlfl.setCatName1(s[3]);
                                lswlflList.add(lswlfl);
                                count++;
                            }
//                            Session session = null;
//                            try{
//                                session=getsession();
//                                Query query = session.createQuery("delete from Lswlfl ");
//                                query.executeUpdate();//先删除在添加
//                                close(session);
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
                            for (int j=0;j<count;j++) {
                                dataManagementService.addLswlfl(lswlflList.get(j));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case "lspzbh":
                    if(filename.equals("lspzbh")){
                        try (FileReader reader = new FileReader(path+"/"+duizhaoName);
                             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
                        ){
                            String lineTxt = null;
                            Lspzbh lspzbh = null;
                            int count= 0;
                            List<Lspzbh> lspzbhList = new ArrayList<Lspzbh>();
                            Session session = null;
                            session = getsession();
                            Query last_query = session.createQuery("from Lspzbh where pzbhAutoId = (select max(pzbhAutoId) from Lspzbh)");
                            Lspzbh last = (Lspzbh) last_query.uniqueResult();
                            int auto_id = last == null ? 1 : last.getPzbhAutoId() + 1;
                            close(session);
                            while((lineTxt = br.readLine())!= null){
                                lspzbh = new Lspzbh();
                                String[] s = lineTxt.split("\t");
                                lspzbh.setPzbhAutoId(auto_id);
                                lspzbh.setVoucherName(s[0]);
                                lspzbh.setVoucherFirstChar(s[1]);
                                lspzbh.setVoucherNo(s[5]);
                                lspzbh.setIniDate(s[6]);
                                //lspzbh.setfPzgs(s[5]);
                                //lspzbh.setfPzys(s[6]);
                                lspzbhList.add(lspzbh);
                                auto_id++;
                                count++;
                            }
//                            Session session = null;
//                            try{
//                                session=getsession();
//                                Query query = session.createQuery("delete from Lspzbh ");
//                                query.executeUpdate();//先删除在添加
//                                close(session);
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
                            for (int j=0;j<count;j++) {
                                dataManagementService.addLspzbh(lspzbhList.get(j));
                            }
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case "lsconf":
                    if(filename.equals("lsconf")){
                        try (FileReader reader = new FileReader(path+"/"+duizhaoName);
                             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
                        ){
                            String lineTxt = null;
                            Lsconf lsconf = null;
                            long count= 0;
                            List<Lsconf> lsconfList = new ArrayList<Lsconf>();
                            while((lineTxt = br.readLine())!= null){
                                lsconf = new Lsconf();
                                String[] s = lineTxt.split("\t");
                                int tNums=0;
                                for (int i = lineTxt.length()-1; i >=0 ; i--) {
                                    if(lineTxt.charAt(i)=='\t')
                                        tNums++;
                                    else
                                        break;
                                }
                                String[] ss = new String[s.length+tNums];
                                for (int i = 0; i <s.length ; i++) {
                                    ss[i]=s[i];
                                }
                                for (int i = 0; i < tNums; i++) {
                                    ss[s.length+i]=Integer.toString(0);
                                }
                                switch (ss[0]){
                                    case "ZW_SLDECN":
                                        ss[0] = "quantity_dec";
                                        break;
                                    case "ZW_KMLENT":
                                        ss[0] = "sub_length";
                                        break;
                                    case "ZW_KMSTRU":
                                        ss[0] = "sub_stru";
                                        break;
                                    case "CW_SJDWBH":
                                        ss[0] = "superior_unit";
                                        break;
                                    case "ZW_SCBT01":
                                        ss[0] = "table1";
                                        break;
                                    case "ZW_SCBT02":
                                        ss[0] = "table2";
                                        break;
                                    case "ZW_SCBT03":
                                        ss[0] = "table3";
                                        break;
                                    case "CW_SYDWMC":
                                        ss[0] = "unit_name";
                                        break;
                                    case "CW_SYDWBH":
                                        ss[0] = "unit_number";
                                        break;
                                    case "ZW_JYLENT":
                                        ss[0] = "abs_length";
                                        break;
                                    case "ZW_ZGNAME":
                                        ss[0] = "acc_manager";
                                        break;
                                    case "ZW_QSDATE":
                                        ss[0] = "begin_date";
                                        break;
                                    case "ZW_ZYPAGE":
                                        ss[0] = "bill_row";
                                        break;
                                    case "ZW_LSCWRQ":
                                        ss[0] = "current_date";
                                        break;
                                }
                                lsconf.setConfKey(ss[0]);
                                lsconf.setConfValue(ss[1]);
                                lsconf.setConfNote(ss[2]);
                                lsconfList.add(lsconf);
                                count++;
                            }
//                            Session session = null;
//                            try{
//                                session=getsession();
//                                Query query = session.createQuery("delete from Lsconf ");
//                                query.executeUpdate();//先删除在添加
//                                close(session);
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
                            for (int j=0;j<count;j++) {
                                dataManagementService.addLsconf(lsconfList.get(j));
                            }
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case "lskmzd":
                    if(filename.equals("lskmzd")){
                        try (FileReader reader = new FileReader(path+"/"+duizhaoName);
                             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
                        ){
                            String lineTxt = null;
                            Lskmzd lskmzd = null;
                            long count= 0;
                            List<Lskmzd> lskmzdList = new ArrayList<Lskmzd>();
                            while((lineTxt = br.readLine())!= null){
                                lskmzd = new Lskmzd();
                                String[] s = lineTxt.split("\t");
                                lskmzd.setItem(s[0]);
                                lskmzd.setItemNo(s[1]);
                                lskmzd.setItemName(s[3]);
                                lskmzd.setEle(s[4]);
                                lskmzd.setAccType(s[5]);
                                lskmzd.setSpType(s[6]);
                                lskmzd.setExchang(Byte.parseByte(s[7]));
                                lskmzd.setSupAcc1(s[9]);
                                //lskmzd.setSupAcc2(s[8]);
                                lskmzd.setJournal(Byte.parseByte(s[13]));
                                lskmzd.setFinLevel(Byte.parseByte(s[14]));
                                lskmzd.setBalance(Double.parseDouble(s[23]));
                                lskmzd.setDebitMoney(Double.parseDouble(s[24]));
                                lskmzd.setCreditMoney(Double.parseDouble(s[25]));
                                lskmzd.setSupMoney(Double.parseDouble(s[26]));
                                lskmzd.setDebitMoneySup(Double.parseDouble(s[27]));
                                lskmzd.setCreditMoneySup(Double.parseDouble(s[28]));
                                lskmzd.setDebitMoneyAcm(Double.parseDouble(s[29]));
                                lskmzd.setCreditMoneyAcm(Double.parseDouble(s[30]));
                                lskmzd.setDebitMoney01(Double.parseDouble(s[31]));
                                lskmzd.setCreditMoney01(Double.parseDouble(s[32]));
                                lskmzd.setBalance01(Double.parseDouble(s[33]));
                                lskmzd.setDebitMoney02(Double.parseDouble(s[34]));
                                lskmzd.setCreditMoney02(Double.parseDouble(s[35]));
                                lskmzd.setBalance02(Double.parseDouble(s[36]));
                                lskmzd.setDebitMoney03(Double.parseDouble(s[37]));
                                lskmzd.setCreditMoney03(Double.parseDouble(s[38]));
                                lskmzd.setBalance03(Double.parseDouble(s[39]));
                                lskmzd.setDebitMoney04(Double.parseDouble(s[40]));
                                lskmzd.setCreditMoney04(Double.parseDouble(s[41]));
                                lskmzd.setBalance04(Double.parseDouble(s[42]));
                                lskmzd.setDebitMoney05(Double.parseDouble(s[43]));
                                lskmzd.setCreditMoney05(Double.parseDouble(s[44]));
                                lskmzd.setBalance05(Double.parseDouble(s[45]));
                                lskmzd.setDebitMoney06(Double.parseDouble(s[46]));
                                lskmzd.setCreditMoney06(Double.parseDouble(s[47]));
                                lskmzd.setBalance06(Double.parseDouble(s[48]));
                                lskmzd.setDebitMoney07(Double.parseDouble(s[49]));
                                lskmzd.setCreditMoney07(Double.parseDouble(s[50]));
                                lskmzd.setBalance07(Double.parseDouble(s[51]));
                                lskmzd.setDebitMoney08(Double.parseDouble(s[52]));
                                lskmzd.setCreditMoney08(Double.parseDouble(s[53]));
                                lskmzd.setBalance08(Double.parseDouble(s[54]));
                                lskmzd.setDebitMoney09(Double.parseDouble(s[55]));
                                lskmzd.setCreditMoney09(Double.parseDouble(s[56]));
                                lskmzd.setBalance09(Double.parseDouble(s[57]));
                                lskmzd.setDebitMoney10(Double.parseDouble(s[58]));
                                lskmzd.setCreditMoney10(Double.parseDouble(s[59]));
                                lskmzd.setBalance10(Double.parseDouble(s[60]));
                                lskmzd.setDebitMoney11(Double.parseDouble(s[61]));
                                lskmzd.setCreditMoney11(Double.parseDouble(s[62]));
                                lskmzd.setBalance11(Double.parseDouble(s[63]));
                                lskmzd.setDebitMoney12(Double.parseDouble(s[64]));
                                lskmzd.setCreditMoney12(Double.parseDouble(s[65]));
                                lskmzd.setBalance12(Double.parseDouble(s[66]));
                                //lskmzd.setfKzjm(s[2]);
                                //lskmzd.setfKjqj(Byte.parseByte(s[56]));
                                //lskmzd.setfDz(Byte.parseByte(s[57]));
//                            lskmzd.setfDzok(Byte.parseByte(s[58]));
//                            lskmzd.setfPzsy(Byte.parseByte(s[59]));
//                            lskmzd.setfDykm(s[60]);
//                            lskmzd.setfPjmc(s[61]);
//                            lskmzd.setfSjly(s[62]);
//                            lskmzd.setfYssx(Double.parseDouble(s[63]));
//                            lskmzd.setfYsxx(Double.parseDouble(s[64]));
//                            lskmzd.setfDf13(Double.parseDouble(s[65]));
//                            lskmzd.setfJf13(Double.parseDouble(s[66]));
//                            lskmzd.setfYe13(Double.parseDouble(s[67]));

                                lskmzdList.add(lskmzd);
                                count++;
                            }
//                            Session session = null;
//                            try{
//                                session=getsession();
//                                Query query = session.createQuery("delete from Lskmzd ");
//                                query.executeUpdate();//先删除在添加
//                                close(session);
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
                            for (int j=0;j<count;j++) {
                                dataManagementService.addLskmzd(lskmzdList.get(j));
                            }
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case "lskmsl":
                    if(filename.equals("lskmsl")){
                        try (FileReader reader = new FileReader(path+"/"+duizhaoName);
                             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
                        ){
                            String lineTxt = null;
                            Lskmsl lskmsl = null;
                            long count= 0;
                            Session session = null;
                            session = getsession();
                            Query last_query = session.createQuery("from Lskmsl where kmslAutoId = (select max(kmslAutoId) from Lskmsl)");
                            Lskmsl last = (Lskmsl) last_query.uniqueResult();
                            int auto_id = last == null ? 1 : last.getKmslAutoId() + 1;
                            close(session);
                            List<Lskmsl> lskmslList = new ArrayList<Lskmsl>();
                            while((lineTxt = br.readLine())!= null){
                                lskmsl = new Lskmsl();
                                String[] s = lineTxt.split("\t");
                                lskmsl.setKmslAutoId(auto_id);
                                lskmsl.setItemNo(s[0]);
                                lskmsl.setHead1(s[1]);
                                lskmsl.setHead2(s[2]);
                                lskmsl.setHead3(s[3]);
                                //lskmsl.setHead4(s[5]);
                                //lskmsl.setHead5(s[6]);
                                //lskmsl.setHead6(s[7]);
                                lskmsl.setBudQty(Double.parseDouble(s[4]));
                                lskmsl.setLeftQty(Double.parseDouble(s[6]));
                                lskmsl.setUnitPrice(Double.parseDouble(s[7]));
                                lskmsl.setDebitQty(Double.parseDouble(s[8]));
                                lskmsl.setCreditQty(Double.parseDouble(s[9]));
                                lskmsl.setSupQty(Double.parseDouble(s[10]));
                                lskmsl.setDebitQtySup(Double.parseDouble(s[11]));
                                lskmsl.setCreditQtySup(Double.parseDouble(s[12]));
                                lskmsl.setDebitQtyAcm(Double.parseDouble(s[13]));
                                lskmsl.setCreditQtyAcm(Double.parseDouble(s[14]));
                                lskmsl.setDebitQty01(Double.parseDouble(s[15]));
                                lskmsl.setCreditQty01(Double.parseDouble(s[16]));
                                lskmsl.setLeftQty01(Double.parseDouble(s[17]));
                                lskmsl.setDebitQty02(Double.parseDouble(s[18]));
                                lskmsl.setCreditQty02(Double.parseDouble(s[19]));
                                lskmsl.setLeftQty02(Double.parseDouble(s[20]));
                                lskmsl.setDebitQty03(Double.parseDouble(s[21]));
                                lskmsl.setCreditQty03(Double.parseDouble(s[22]));
                                lskmsl.setLeftQty03(Double.parseDouble(s[23]));
                                lskmsl.setDebitQty04(Double.parseDouble(s[24]));
                                lskmsl.setCreditQty04(Double.parseDouble(s[25]));
                                lskmsl.setLeftQty04(Double.parseDouble(s[26]));
                                lskmsl.setDebitQty05(Double.parseDouble(s[27]));
                                lskmsl.setCreditQty05(Double.parseDouble(s[28]));
                                lskmsl.setLeftQty05(Double.parseDouble(s[29]));
                                lskmsl.setDebitQty06(Double.parseDouble(s[30]));
                                lskmsl.setCreditQty06(Double.parseDouble(s[31]));
                                lskmsl.setLeftQty06(Double.parseDouble(s[32]));
                                lskmsl.setDebitQty07(Double.parseDouble(s[33]));
                                lskmsl.setCreditQty07(Double.parseDouble(s[34]));
                                lskmsl.setLeftQty07(Double.parseDouble(s[35]));
                                lskmsl.setDebitQty08(Double.parseDouble(s[36]));
                                lskmsl.setCreditQty08(Double.parseDouble(s[37]));
                                lskmsl.setLeftQty08(Double.parseDouble(s[38]));
                                lskmsl.setDebitQty09(Double.parseDouble(s[39]));
                                lskmsl.setCreditQty09(Double.parseDouble(s[40]));
                                lskmsl.setLeftQty09(Double.parseDouble(s[41]));
                                lskmsl.setDebitQty10(Double.parseDouble(s[42]));
                                lskmsl.setCreditQty10(Double.parseDouble(s[43]));
                                lskmsl.setLeftQty10(Double.parseDouble(s[44]));
                                lskmsl.setDebitQty11(Double.parseDouble(s[45]));
                                lskmsl.setCreditQty11(Double.parseDouble(s[46]));
                                lskmsl.setLeftQty11(Double.parseDouble(s[47]));
                                lskmsl.setDebitQty12(Double.parseDouble(s[48]));
                                lskmsl.setCreditQty12(Double.parseDouble(s[49]));
                                lskmsl.setLeftQty12(Double.parseDouble(s[50]));
                                lskmsl.setfYsxl(Double.parseDouble(s[5]));
                                lskmsl.setfDs13(Double.parseDouble(s[51]));
                                lskmsl.setfJs13(Double.parseDouble(s[52]));
                                lskmsl.setfSl13(Double.parseDouble(s[53]));

                                lskmslList.add(lskmsl);
                                auto_id++;
                                count++;
                            }
//                            Session session = null;
//                            try{
//                                session=getsession();
//                                Query query = session.createQuery("delete from Lskmsl ");
//                                query.executeUpdate();//先删除在添加
//                                close(session);
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
                            for (int j=0;j<count;j++) {
                                dataManagementService.addLskmsl(lskmslList.get(j));
                            }
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case "lszczd":
                    if(filename.equals("lszczd")){
                        try (FileReader reader = new FileReader(path+"/"+duizhaoName);
                             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
                        ){
                            String lineTxt = null;
                            Lszczd lszczd = null;
                            long count= 0;
                            List<Lszczd> lszczdList = new ArrayList<Lszczd>();
                            while((lineTxt = br.readLine())!= null){
                                lszczd = new Lszczd();
                                String[] s = lineTxt.split("\t");
                                int tNums=0;
                                for (int i = lineTxt.length()-1; i >=0 ; i--) {
                                    if(lineTxt.charAt(i)=='\t')
                                        tNums++;
                                    else
                                        break;
                                }
                                String[] ss = new String[s.length+tNums];
                                for (int i = 0; i <s.length ; i++) {
                                    ss[i]=s[i];
                                }
                                for (int i = 0; i < tNums; i++) {
                                    ss[s.length+i]="";
                                }
                                lszczd.setAcontBookNo(ss[0]);
                                lszczd.setAccBookName(ss[1]);
                                lszczd.setAccBookType(ss[2]);
                                lszczd.setAccType(ss[3]);
                                lszczd.setPageRules(Integer.parseInt(ss[4]));
                                lszczd.setAbsWidth(Byte.parseByte(ss[6]));
                                lszczd.setChaPage(ss[8]);
                                lszczd.setPrintFor(ss[9]);
                                lszczd.setFormatNo(ss[10]);
                                //lszczd.setAddEmptyRow(s[10]);
                                //lszczd.setfTdyn(Byte.parseByte(s[10]));
                                lszczdList.add(lszczd);
                                count++;
                            }
//                            Session session = null;
//                            try{
//                                session=getsession();
//                                Query query = session.createQuery("delete from Lszczd ");
//                                query.executeUpdate();//先删除在添加
//                                close(session);
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
                            for (int j=0;j<count;j++) {
                                dataManagementService.addLszczd(lszczdList.get(j));
                            }
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case "lcbzd":
                    if(filename.equals("lcbzd")){
                        try (FileReader reader = new FileReader(path+"/"+duizhaoName);
                             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
                        ){

                            String lineTxt = null;
                            Lcbzd lcbzd = null;
                            long count= 0;
                            List<Lcbzd> lcbzdList = new ArrayList<Lcbzd>();
                            while((lineTxt = br.readLine())!= null){
                                lcbzd = new Lcbzd();
                                String[] s = lineTxt.split("\t");
                                lcbzd.setBzdAutoId(null);
                                lcbzd.setRptOrde(s[0]);
                                lcbzd.setRptNo(s[1]);
                                lcbzd.setRptDate(s[2]);
                                lcbzd.setRptName(s[3]);
                                lcbzd.setTitleRules(Integer.parseInt(s[7]));
                                lcbzd.setHeadRules(Integer.parseInt(s[8]));
                                lcbzd.setBodyRules(Integer.parseInt(s[9]));
                                lcbzd.setRptCol(Integer.parseInt(s[10]));
                                lcbzd.setFixRow(Integer.parseInt(s[11]));
                                //lcbzd.setFixCol(Integer.parseInt(s[14]));
                                lcbzd.setReportNo(s[13]);

                                lcbzdList.add(lcbzd);
                                count++;
                            }
//                            Session session = null;
//                            try{
//                                session=getsession();
//                                Query query = session.createQuery("delete from Lcbzd ");
//                                query.executeUpdate();//先删除在添加
//                                close(session);
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
                            //ReportBaseService reportBaseService=new ReportBaseServiceImpl();
                            reportBaseService.saveOrUpdateRpt(lcbzdList,null);
//                        for (int j=0;j<count;j++) {
//                            dataManagementService.addLcbzd(lcbzdList.get(j));
//                        }
                        }catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    break;
                case "lszcdy":
                    if(filename.equals("lszcdy")){
                        try (FileReader reader = new FileReader(path+"/"+duizhaoName);
                             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
                        ){
                            String lineTxt = null;
                            Lszcdy lszcdy = null;
                            int count= 0;
                            Session session = null;
                            session = getsession();
                            Query last_query = session.createQuery("from Lszcdy where zcdyAutoId = (select max(zcdyAutoId) from Lszcdy)");
                            Lszcdy last = (Lszcdy) last_query.uniqueResult();
                            int auto_id = last == null ? 1 : last.getZcdyAutoId() + 1;
                            close(session);
                            List<Lszcdy> lszcdyList = new ArrayList<Lszcdy>();
                            while((lineTxt = br.readLine())!= null){
                                lszcdy = new Lszcdy();
                                String[] s = lineTxt.split("\t");
                                int tNums=0;
                                for (int i = lineTxt.length()-1; i >=0 ; i--) {
                                    if(lineTxt.charAt(i)=='\t')
                                        tNums++;
                                    else
                                        break;
                                }
                                String[] ss = new String[s.length+tNums];
                                for (int i = 0; i <s.length ; i++) {
                                    ss[i]=s[i];
                                }
                                for (int i = 0; i < tNums; i++) {
                                    ss[s.length+i]=Integer.toString(0);
                                }
                                lszcdy.setZcdyAutoId(auto_id);
                                lszcdy.setAcontBookNo(ss[0]);
                                lszcdy.setItemNo(ss[1]);
                                lszcdy.setAccLevel(ss[2]);
                                lszcdy.setCombineEntry(ss[3]);
                                lszcdy.setOppItem(ss[4]);
                                lszcdy.setSummary(ss[5]);
                                lszcdy.setDaysTge(ss[6]);
                                lszcdy.setListItemLevel(ss[7]);
                                lszcdy.setItemDir(ss[8]);
                                //lcbzd.setFixCol(Integer.parseInt(s[14]));
                                lszcdy.setPrintUse(ss[10]);
                                //lszcdy.setFixCol(Byte.parseByte(ss[11]));
                                //lszcdy.setPageCol(Byte.parseByte(ss[12]));

                                lszcdyList.add(lszcdy);
                                auto_id++;
                                count++;
                            }
//                            Session session = null;
//                            try{
//                                session=getsession();
//                                Query query = session.createQuery("delete from Lszcdy ");
//                                query.executeUpdate();//先删除在添加
//                                close(session);
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }

                            for (int j=0;j<count;j++) {
                                dataManagementService.addLszcdy(lszcdyList.get(j));
                            }
                        }catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    break;
                case "lcdyzd":
                    if(filename.equals("lcdyzd")){
                        try (FileReader reader = new FileReader(path+"/"+duizhaoName);
                             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
                        ){
                            String lineTxt = null;
                            Lcdyzd lcdyzd = null;
                            int count= 0;
                            Session session = null;
                            session = getsession();
                            Query last_query = session.createQuery("from Lcdyzd where dyzdAutoId = (select max(dyzdAutoId) from Lcdyzd)");
                            Lcdyzd last = (Lcdyzd) last_query.uniqueResult();
                            int auto_id = last == null ? 1 : last.getDyzdAutoId() + 1;
                            close(session);
                            List<Lcdyzd> lcdyzdList = new ArrayList<Lcdyzd>();
                            while((lineTxt = br.readLine())!= null){
                                lcdyzd = new Lcdyzd();
                                String[] s = lineTxt.split("\t");
                                lcdyzd.setDyzdAutoId(auto_id);
                                lcdyzd.setRptNo(s[0]);
                                lcdyzd.setRptDate(s[1]);
                                lcdyzd.setCellNo(s[2]);
                                lcdyzd.setRowInfoNo(s[3]);
                                lcdyzd.setColInfoNo(s[4]);
                                lcdyzd.setCellType(s[7]);
                                lcdyzd.setCellContent(s[9]);
                                lcdyzd.setCellData(Double.parseDouble(s[10]));
                                lcdyzd.setFormula(s[15]);

                                lcdyzdList.add(lcdyzd);
                                auto_id++;
                                count++;
                            }
//                            Session session = null;
//                            try{
//                                session=getsession();
//                                Query query = session.createQuery("delete from Lcdyzd ");
//                                query.executeUpdate();//先删除在添加
//                                close(session);
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
                            //ReportBaseService reportBaseService=new ReportBaseServiceImpl();
                            //reportBaseService.saveOrUpdateRpt(null,lcdyzdList);
                            for (int j=0;j<count;j++) {
                                dataManagementService.addLcdyzd(lcdyzdList.get(j));
                            }
                        }catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    break;
                case "lsszzd":
                    if(filename.equals("lsszzd")){
                        try (FileReader reader = new FileReader(path+"/"+duizhaoName);
                             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
                        ){
                            String lineTxt = null;
                            Lsszzd lsszzd = null;
                            int count= 0;
                            Session session = null;
                            session = getsession();
                            Query last_query = session.createQuery("from Lsszzd where szzdAutoId = (select max(szzdAutoId) from Lsszzd)");
                            Lsszzd last = (Lsszzd) last_query.uniqueResult();
                            int auto_id = last == null ? 1 : last.getSzzdAutoId() + 1;
                            close(session);
                            List<Lsszzd> lsszzdList = new ArrayList<Lsszzd>();
                            while((lineTxt = br.readLine())!= null){
                                lsszzd = new Lsszzd();
                                String[] s = lineTxt.split("\t");
                                lsszzd.setSzzdAutoId(auto_id);
                                lsszzd.setPrintNo(s[0]);
                                lsszzd.setSetNo(s[1]);
                                lsszzd.setSetNote(s[2]);
                                lsszzd.setTitleFont(s[3]);
                                lsszzd.setTitleSize(s[4]);
                                lsszzd.setBodyFont(s[5]);
                                lsszzd.setBodySize(s[6]);
                                lsszzd.setHorZoom(Byte.parseByte(s[7]));
                                lsszzd.setVerZoom(Byte.parseByte(s[8]));
                                lsszzd.setPrintQua(s[9]);
                                lsszzd.setOutputType(s[10]);
                                lsszzd.setPageSize(s[11]);
                                lsszzd.setLeftMargin(s[12]);
                                lsszzd.setRightMargin(s[13]);
                                lsszzd.setTopMargin(s[14]);
                                lsszzd.setBottomMargin(s[15]);
                                lsszzd.setDefaulTuse(Byte.parseByte(s[16]));

                                lsszzdList.add(lsszzd);
                                auto_id++;
                                count++;
                            }
//                            Session session = null;
//                            try{
//                                session=getsession();
//                                Query query = session.createQuery("delete from Lsszzd ");
//                                query.executeUpdate();//先删除在添加
//                                close(session);
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
                            //ReportBaseService reportBaseService=new ReportBaseServiceImpl();
                            //reportBaseService.saveOrUpdateRpt(null,lcdyzdList);
                            for (int j=0;j<count;j++) {
                                dataManagementService.addLsszzd(lsszzdList.get(j));
                            }
                        }catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    break;

                case "lspzk1":
                    if(filename.equals("lspzk1")){
                        try (FileReader reader = new FileReader(path+"/"+duizhaoName);
                             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
                        ){
                            String lineTxt = null;
                            Lspzk1 lspzk1 = null;
                            int count= 0;
                            Session session = null;
                            session = getsession();
                            Query last_query = session.createQuery("from Lspzk1 where pzk1AutoId = (select max(pzk1AutoId) from Lspzk1)");
                            Lspzk1 last = (Lspzk1) last_query.uniqueResult();
                            int auto_id = last == null ? 1 : last.getPzk1AutoId() + 1;
                            close(session);
                            List<Lspzk1> lspzk1List = new ArrayList<Lspzk1>();
                            while((lineTxt = br.readLine())!= null){
                                lspzk1 = new Lspzk1();
                                String[] s = lineTxt.split("\t");
                                int tNums=0;
                                for (int i = lineTxt.length()-1; i >=0 ; i--) {
                                    if(lineTxt.charAt(i)=='\t')
                                        tNums++;
                                    else
                                        break;
                                }
                                String[] ss = new String[s.length+tNums];
                                for (int i = 0; i <s.length ; i++) {
                                    ss[i]=s[i];
                                }
                                for (int i = 0; i < tNums; i++) {
                                    ss[s.length+i]=Integer.toString(0);
                                }
                                lspzk1.setPzk1AutoId(auto_id);
                                lspzk1.setItemNo(ss[0]);
                                lspzk1.setInputDate(ss[1]);
                                lspzk1.setVoucherNo(ss[2]);
                                lspzk1.setEntryNo(ss[3]);
                                lspzk1.setAcsDocCnt(Integer.parseInt(ss[4]));
                                lspzk1.setVoucherType(ss[5]);
                                lspzk1.setSummary(ss[6]);
                                lspzk1.setBkpDirection(s[7]);
                                lspzk1.setMoney(Double.parseDouble(ss[8]));
                                lspzk1.setQty(Double.parseDouble(ss[9]));
                                lspzk1.setPreActDoc(ss[11]);
                                lspzk1.setAuditor(ss[13]);
                                lspzk1.setAuditorNo(ss[14]);
                                lspzk1.setDirectorName(ss[15]);
                                lspzk1.setAuditSign(Byte.parseByte(ss[17]));
                                lspzk1.setCompanyNo(ss[21]);
                                lspzk1.setPreActNo(ss[12]);

                                lspzk1List.add(lspzk1);
                                auto_id++;
                                count++;
                            }
//                            Session session = null;
//                            try{
//                                session=getsession();
//                                Query query = session.createQuery("delete from Lspzk1 ");
//                                query.executeUpdate();//先删除在添加
//                                close(session);
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
                            for (int j=0;j<count;j++) {
                                dataManagementService.addLspzk1(lspzk1List.get(j));
                            }
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case "lsyspz":
                    if(filename.equals("lsyspz")){
                        try (FileReader reader = new FileReader(path+"/"+duizhaoName);
                             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
                        ){
                            String lineTxt = null;
                            Lsyspz lsyspz = null;
                            int count= 0;
                            Session session = null;
                            session = getsession();
                            Query last_query = session.createQuery("from Lsyspz where yspzAutoId = (select max(yspzAutoId) from Lsyspz)");
                            Lsyspz last = (Lsyspz) last_query.uniqueResult();
                            int auto_id = last == null ? 1 : last.getYspzAutoId() + 1;
                            close(session);
                            List<Lsyspz> lsyspzList = new ArrayList<Lsyspz>();
                            while((lineTxt = br.readLine())!= null){
                                lsyspz = new Lsyspz();
                                String[] s = lineTxt.split("\t");
                                int tNums=0;
                                for (int i = lineTxt.length()-1; i >=0 ; i--) {
                                    if(lineTxt.charAt(i)=='\t')
                                        tNums++;
                                    else
                                        break;
                                }
                                String[] ss = new String[s.length+tNums];
                                for (int i = 0; i <s.length ; i++) {
                                    ss[i]=s[i];
                                }
                                for (int i = 0; i < tNums; i++) {
                                    ss[s.length+i]=Integer.toString(0);
                                }
                                lsyspz.setYspzAutoId(auto_id);
                                lsyspz.setItemNo(ss[0]);
                                lsyspz.setInputDate(ss[1]);
                                lsyspz.setVoucherNo(ss[3]);
                                lsyspz.setEntryNo(ss[4]);
                                if(ss[5]==""){
                                    ss[5]="null";
                                }
                                lsyspz.setSummary(ss[5]);
                                lsyspz.setBkpDirection(s[6]);
                                lsyspz.setMoney(Double.parseDouble(ss[8]));
                                lsyspz.setQty(Double.parseDouble(ss[9]));
                                lsyspz.setUnitPrice(Double.parseDouble(ss[11]));
                                lsyspz.setOriginalNo(ss[13]);

                                lsyspzList.add(lsyspz);
                                auto_id++;
                                count++;
                            }
//                            Session session = null;
//                            try{
//                                session=getsession();
//                                Query query = session.createQuery("delete from Lsyspz ");
//                                query.executeUpdate();//先删除在添加
//                                close(session);
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
                            for (int j=0;j<count;j++) {
                                dataManagementService.addLsyspz(lsyspzList.get(j));
                            }
                        }catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    break;
                case "lshspz":
                    if(filename.equals("lshspz")){
                        try (FileReader reader = new FileReader(path+"/"+duizhaoName);
                             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
                        ){

                            String lineTxt = null;
                            Lshspz lshspz = null;
                            int count= 0;
                            Session session = null;
                            session = getsession();
                            Query last_query = session.createQuery("from Lshspz where hspzAutoId = (select max(hspzAutoId) from Lshspz)");
                            Lshspz last = (Lshspz) last_query.uniqueResult();
                            int auto_id = last == null ? 1 : last.getHspzAutoId() + 1;
                            close(session);
                            List<Lshspz> lshspzList = new ArrayList<Lshspz>();
                            while((lineTxt = br.readLine())!= null){
                                lshspz = new Lshspz();
                                String[] s = lineTxt.split("\t");
                                int tNums=0;
                                for (int i = lineTxt.length()-1; i >=0 ; i--) {
                                    if(lineTxt.charAt(i)=='\t')
                                        tNums++;
                                    else
                                        break;
                                }
                                String[] ss = new String[s.length+tNums];
                                for (int i = 0; i <s.length ; i++) {
                                    ss[i]=s[i];
                                }
                                for (int i = 0; i < tNums; i++) {
                                    ss[s.length+i]=Integer.toString(0);
                                }
                                lshspz.setHspzAutoId(auto_id);
                                lshspz.setItemNo(ss[3]);
                                lshspz.setInputDate(ss[0]);
                                lshspz.setVoucherNo(ss[1]);
                                lshspz.setEntryNo(ss[2]);
                                lshspz.setSpNo1(ss[4]);
                                lshspz.setBkpDirection(s[5]);
                                if(ss[6].equals(""))
                                    ss[6]=Integer.toString(0);
                                lshspz.setMoney(Double.parseDouble(ss[6]));
                                if(ss[7].equals(""))
                                    ss[7]=Integer.toString(0);
                                lshspz.setQty(Double.parseDouble(ss[7]));

                                lshspzList.add(lshspz);
                                auto_id++;
                                count++;
                            }
//                            Session session = null;
//                            try{
//                                session=getsession();
//                                Query query = session.createQuery("delete from Lshspz ");
//                                query.executeUpdate();//先删除在添加
//                                close(session);
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
                            for (int j=0;j<count;j++) {
                                dataManagementService.addLshspz(lshspzList.get(j));
                            }
                        }catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    break;
                case "lshssl":
                    if(filename.equals("lshssl")){
                        try (FileReader reader = new FileReader(path+"/"+duizhaoName);
                             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
                        ){

                            String lineTxt = null;
                            Lshssl lshssl = null;
                            int count= 0;
                            Session session = null;
                            session = getsession();
                            Query last_query = session.createQuery("from Lshssl where hsslAutoId = (select max(hsslAutoId) from Lshssl)");
                            Lshssl last = (Lshssl) last_query.uniqueResult();
                            int auto_id = last == null ? 1 : last.getHsslAutoId() + 1;
                            close(session);
                            List<Lshssl> lshsslList = new ArrayList<Lshssl>();
                            while((lineTxt = br.readLine())!= null){
                                lshssl = new Lshssl();
                                String[] s = lineTxt.split("\t");
                                int tNums=0;
                                for (int i = lineTxt.length()-1; i >=0 ; i--) {
                                    if(lineTxt.charAt(i)=='\t')
                                        tNums++;
                                    else
                                        break;
                                }
                                String[] ss = new String[s.length+tNums];
                                for (int i = 0; i <s.length ; i++) {
                                    ss[i]=s[i];
                                }
                                for (int i = 0; i < tNums; i++) {
                                    ss[s.length+i]=Integer.toString(0);
                                }
                                lshssl.setHsslAutoId(auto_id);
                                lshssl.setItemNo(ss[0]);
                                lshssl.setSpNo1(ss[1]);
                                //lshssl.setSpNo2(ss[2]);
                                if(ss[4].equals(""))
                                    ss[4]=Integer.toString(0);
                                lshssl.setLeftQty(Double.parseDouble(ss[4]));
                                if(ss[5].equals(""))
                                    ss[5]=Integer.toString(0);
                                lshssl.setUnitPrice(Double.parseDouble(ss[5]));
                                if(ss[6].equals(""))
                                    ss[6]=Integer.toString(0);
                                lshssl.setDebitQty(Double.parseDouble(ss[6]));
                                if(ss[7].equals(""))
                                    ss[7]=Integer.toString(0);
                                lshssl.setCreditQty(Double.parseDouble(ss[7]));
                                if(ss[8].equals(""))
                                    ss[8]=Integer.toString(0);
                                lshssl.setSupQty(Double.parseDouble(ss[8]));
                                if(ss[9].equals(""))
                                    ss[9]=Integer.toString(0);
                                lshssl.setDebitQtySup(Double.parseDouble(ss[9]));
                                if(ss[10].equals(""))
                                    ss[10]=Integer.toString(0);
                                lshssl.setCreditQtySup(Double.parseDouble(ss[10]));
                                if(ss[11].equals(""))
                                    ss[11]=Integer.toString(0);
                                lshssl.setDebitQtyAcm(Double.parseDouble(ss[11]));
                                if(ss[12].equals(""))
                                    ss[12]=Integer.toString(0);
                                lshssl.setCreditQtyAcm(Double.parseDouble(ss[12]));
                                if(ss[13].equals(""))
                                    ss[13]=Integer.toString(0);
                                lshssl.setDebitQty01(Double.parseDouble(ss[13]));
                                if(ss[14].equals(""))
                                    ss[14]=Integer.toString(0);
                                lshssl.setCreditQty01(Double.parseDouble(ss[14]));
                                if(ss[15].equals(""))
                                    ss[15]=Integer.toString(0);
                                lshssl.setLeftQty01(Double.parseDouble(ss[15]));
                                if(ss[16].equals(""))
                                    ss[16]=Integer.toString(0);
                                lshssl.setDebitQty02(Double.parseDouble(ss[16]));
                                if(ss[17].equals(""))
                                    ss[17]=Integer.toString(0);
                                lshssl.setCreditQty02(Double.parseDouble(ss[17]));
                                if(ss[18].equals(""))
                                    ss[18]=Integer.toString(0);
                                lshssl.setLeftQty02(Double.parseDouble(ss[18]));
                                if(ss[19].equals(""))
                                    ss[19]=Integer.toString(0);
                                lshssl.setDebitQty03(Double.parseDouble(ss[19]));
                                if(ss[20].equals(""))
                                    ss[20]=Integer.toString(0);
                                lshssl.setCreditQty03(Double.parseDouble(ss[20]));
                                if(ss[21].equals(""))
                                    ss[21]=Integer.toString(0);
                                lshssl.setLeftQty03(Double.parseDouble(ss[21]));
                                if(ss[22].equals(""))
                                    ss[22]=Integer.toString(0);
                                lshssl.setDebitQty04(Double.parseDouble(ss[22]));
                                if(ss[23].equals(""))
                                    ss[23]=Integer.toString(0);
                                lshssl.setCreditQty04(Double.parseDouble(ss[23]));
                                if(ss[24].equals(""))
                                    ss[24]=Integer.toString(0);
                                lshssl.setLeftQty04(Double.parseDouble(ss[24]));
                                if(ss[25].equals(""))
                                    ss[25]=Integer.toString(0);
                                lshssl.setDebitQty05(Double.parseDouble(ss[25]));
                                if(ss[26].equals(""))
                                    ss[26]=Integer.toString(0);
                                lshssl.setCreditQty05(Double.parseDouble(ss[26]));
                                if(ss[27].equals(""))
                                    ss[27]=Integer.toString(0);
                                lshssl.setLeftQty05(Double.parseDouble(ss[27]));
                                if(ss[28].equals(""))
                                    ss[28]=Integer.toString(0);
                                lshssl.setDebitQty06(Double.parseDouble(ss[28]));
                                if(ss[29].equals(""))
                                    ss[29]=Integer.toString(0);
                                lshssl.setCreditQty06(Double.parseDouble(ss[29]));
                                if(ss[30].equals(""))
                                    ss[30]=Integer.toString(0);
                                lshssl.setLeftQty06(Double.parseDouble(ss[30]));
                                if(ss[31].equals(""))
                                    ss[31]=Integer.toString(0);
                                lshssl.setDebitQty07(Double.parseDouble(ss[31]));
                                if(ss[32].equals(""))
                                    ss[32]=Integer.toString(0);
                                lshssl.setCreditQty07(Double.parseDouble(ss[32]));
                                if(ss[33].equals(""))
                                    ss[33]=Integer.toString(0);
                                lshssl.setLeftQty07(Double.parseDouble(ss[33]));
                                if(ss[34].equals(""))
                                    ss[34]=Integer.toString(0);
                                lshssl.setDebitQty08(Double.parseDouble(ss[34]));
                                if(ss[35].equals(""))
                                    ss[35]=Integer.toString(0);
                                lshssl.setCreditQty08(Double.parseDouble(ss[35]));
                                if(ss[36].equals(""))
                                    ss[36]=Integer.toString(0);
                                lshssl.setLeftQty08(Double.parseDouble(ss[36]));
                                if(ss[37].equals(""))
                                    ss[37]=Integer.toString(0);
                                lshssl.setDebitQty09(Double.parseDouble(ss[37]));
                                if(ss[38].equals(""))
                                    ss[38]=Integer.toString(0);
                                lshssl.setCreditQty09(Double.parseDouble(ss[38]));
                                if(ss[39].equals(""))
                                    ss[39]=Integer.toString(0);
                                lshssl.setLeftQty09(Double.parseDouble(ss[39]));
                                if(ss[40].equals(""))
                                    ss[40]=Integer.toString(0);
                                lshssl.setDebitQty10(Double.parseDouble(ss[40]));
                                if(ss[41].equals(""))
                                    ss[41]=Integer.toString(0);
                                lshssl.setCreditQty10(Double.parseDouble(ss[41]));
                                if(ss[42].equals(""))
                                    ss[42]=Integer.toString(0);
                                lshssl.setLeftQty10(Double.parseDouble(ss[42]));
                                if(ss[43].equals(""))
                                    ss[43]=Integer.toString(0);
                                lshssl.setDebitQty11(Double.parseDouble(ss[43]));
                                if(ss[44].equals(""))
                                    ss[44]=Integer.toString(0);
                                lshssl.setCreditQty11(Double.parseDouble(ss[44]));
                                if(ss[45].equals(""))
                                    ss[45]=Integer.toString(0);
                                lshssl.setLeftQty11(Double.parseDouble(ss[45]));
                                if(ss[46].equals(""))
                                    ss[46]=Integer.toString(0);
                                lshssl.setDebitQty12(Double.parseDouble(ss[46]));
                                if(ss[47].equals(""))
                                    ss[47]=Integer.toString(0);
                                lshssl.setCreditQty12(Double.parseDouble(ss[47]));
                                if(ss[48].equals(""))
                                    ss[48]=Integer.toString(0);
                                lshssl.setLeftQty12(Double.parseDouble(ss[48]));

                                lshsslList.add(lshssl);
                                auto_id++;
                                count++;
                            }
//                            Session session = null;
//                            try{
//                                session=getsession();
//                                Query query = session.createQuery("delete from Lshssl ");
//                                query.executeUpdate();//先删除在添加
//                                close(session);
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
                            for (int j=0;j<count;j++) {
                                dataManagementService.addLshssl(lshsslList.get(j));
                            }
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case "lshsje":
                    if(filename.equals("lshsje")){
                        try (FileReader reader = new FileReader(path+"/"+duizhaoName);
                             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
                        ){
                            String lineTxt = null;
                            Lshsje lshsje = null;
                            int count= 0;
                            Session session = null;
                            session = getsession();
                            Query last_query = session.createQuery("from Lshsje where hsjeAutoId = (select max(hsjeAutoId) from Lshsje)");
                            Lshsje last = (Lshsje) last_query.uniqueResult();
                            int auto_id = last == null ? 1 : last.getHsjeAutoId() + 1;
                            close(session);
                            List<Lshsje> lshsjeList = new ArrayList<Lshsje>();
                            while((lineTxt = br.readLine())!= null){
                                lshsje = new Lshsje();
                                String[] s = lineTxt.split("\t");
                                int tNums=0;
                                for (int i = lineTxt.length()-1; i >=0 ; i--) {
                                    if(lineTxt.charAt(i)=='\t')
                                        tNums++;
                                    else
                                        break;
                                }
                                String[] ss = new String[s.length+tNums];
                                for (int i = 0; i <s.length ; i++) {
                                    ss[i]=s[i];
                                }
                                for (int i = 0; i < tNums; i++) {
                                    ss[s.length+i]=Integer.toString(0);
                                }
                                lshsje.setHsjeAutoId(auto_id);
                                lshsje.setItemNo(ss[0]);
                                lshsje.setSpNo1(ss[1]);
                                //lshssl.setSpNo2(ss[2]);
                                if(ss[4].equals(""))
                                    ss[4]=Integer.toString(0);
                                lshsje.setBalance(Double.parseDouble(ss[4]));
                                if(ss[5].equals(""))
                                    ss[5]=Integer.toString(0);
                                lshsje.setDebitMoney(Double.parseDouble(ss[5]));
                                if(ss[6].equals(""))
                                    ss[6]=Integer.toString(0);
                                lshsje.setCreditMoney(Double.parseDouble(ss[6]));
                                if(ss[7].equals(""))
                                    ss[7]=Integer.toString(0);
                                lshsje.setSupMoney(Double.parseDouble(ss[7]));
                                if(ss[8].equals(""))
                                    ss[8]=Integer.toString(0);
                                lshsje.setDebitMoneySup(Double.parseDouble(ss[8]));
                                if(ss[9].equals(""))
                                    ss[9]=Integer.toString(0);
                                lshsje.setCreditMoneySup(Double.parseDouble(ss[9]));
                                if(ss[10].equals(""))
                                    ss[10]=Integer.toString(0);
                                lshsje.setDebitMoneyAcm(Double.parseDouble(ss[10]));
                                if(ss[11].equals(""))
                                    ss[11]=Integer.toString(0);
                                lshsje.setCreditMoneyAcm(Double.parseDouble(ss[11]));
                                if(ss[12].equals(""))
                                    ss[12]=Integer.toString(0);
                                lshsje.setDebitMoney01(Double.parseDouble(ss[12]));
                                if(ss[13].equals(""))
                                    ss[13]=Integer.toString(0);
                                lshsje.setCreditMoney01(Double.parseDouble(ss[13]));
                                if(ss[14].equals(""))
                                    ss[14]=Integer.toString(0);
                                lshsje.setBalance01(Double.parseDouble(ss[14]));
                                if(ss[15].equals(""))
                                    ss[15]=Integer.toString(0);
                                lshsje.setDebitMoney02(Double.parseDouble(ss[15]));
                                if(ss[16].equals(""))
                                    ss[16]=Integer.toString(0);
                                lshsje.setCreditMoney02(Double.parseDouble(ss[16]));
                                if(ss[17].equals(""))
                                    ss[17]=Integer.toString(0);
                                lshsje.setBalance02(Double.parseDouble(ss[17]));
                                if(ss[18].equals(""))
                                    ss[18]=Integer.toString(0);
                                lshsje.setDebitMoney03(Double.parseDouble(ss[18]));
                                if(ss[19].equals(""))
                                    ss[19]=Integer.toString(0);
                                lshsje.setCreditMoney03(Double.parseDouble(ss[19]));
                                if(ss[20].equals(""))
                                    ss[20]=Integer.toString(0);
                                lshsje.setBalance03(Double.parseDouble(ss[20]));
                                if(ss[20].equals(""))
                                    ss[20]=Integer.toString(0);
                                lshsje.setDebitMoney04(Double.parseDouble(ss[20]));
                                if(ss[21].equals(""))
                                    ss[21]=Integer.toString(0);
                                lshsje.setCreditMoney04(Double.parseDouble(ss[21]));
                                if(ss[22].equals(""))
                                    ss[22]=Integer.toString(0);
                                lshsje.setBalance04(Double.parseDouble(ss[22]));
                                if(ss[23].equals(""))
                                    ss[23]=Integer.toString(0);
                                lshsje.setDebitMoney05(Double.parseDouble(ss[23]));
                                if(ss[24].equals(""))
                                    ss[24]=Integer.toString(0);
                                lshsje.setCreditMoney05(Double.parseDouble(ss[24]));
                                if(ss[25].equals(""))
                                    ss[25]=Integer.toString(0);
                                lshsje.setBalance05(Double.parseDouble(ss[25]));
                                if(ss[26].equals(""))
                                    ss[26]=Integer.toString(0);
                                lshsje.setDebitMoney06(Double.parseDouble(ss[26]));
                                if(ss[27].equals(""))
                                    ss[27]=Integer.toString(0);
                                lshsje.setCreditMoney06(Double.parseDouble(ss[27]));
                                if(ss[28].equals(""))
                                    ss[28]=Integer.toString(0);
                                lshsje.setBalance06(Double.parseDouble(ss[28]));
                                if(ss[29].equals(""))
                                    ss[29]=Integer.toString(0);
                                lshsje.setDebitMoney07(Double.parseDouble(ss[29]));
                                if(ss[30].equals(""))
                                    ss[30]=Integer.toString(0);
                                lshsje.setCreditMoney07(Double.parseDouble(ss[30]));
                                if(ss[31].equals(""))
                                    ss[31]=Integer.toString(0);
                                lshsje.setBalance07(Double.parseDouble(ss[31]));
                                if(ss[32].equals(""))
                                    ss[32]=Integer.toString(0);
                                lshsje.setDebitMoney08(Double.parseDouble(ss[32]));
                                if(ss[33].equals(""))
                                    ss[33]=Integer.toString(0);
                                lshsje.setCreditMoney08(Double.parseDouble(ss[33]));
                                if(ss[34].equals(""))
                                    ss[34]=Integer.toString(0);
                                lshsje.setBalance08(Double.parseDouble(ss[34]));
                                if(ss[35].equals(""))
                                    ss[35]=Integer.toString(0);
                                lshsje.setDebitMoney09(Double.parseDouble(ss[35]));
                                if(ss[36].equals(""))
                                    ss[36]=Integer.toString(0);
                                lshsje.setCreditMoney09(Double.parseDouble(ss[36]));
                                if(ss[37].equals(""))
                                    ss[37]=Integer.toString(0);
                                lshsje.setBalance09(Double.parseDouble(ss[37]));
                                if(ss[38].equals(""))
                                    ss[38]=Integer.toString(0);
                                lshsje.setDebitMoney10(Double.parseDouble(ss[38]));
                                if(ss[39].equals(""))
                                    ss[39]=Integer.toString(0);
                                lshsje.setCreditMoney10(Double.parseDouble(ss[39]));
                                if(ss[40].equals(""))
                                    ss[40]=Integer.toString(0);
                                lshsje.setBalance10(Double.parseDouble(ss[40]));
                                if(ss[41].equals(""))
                                    ss[41]=Integer.toString(0);
                                lshsje.setDebitMoney11(Double.parseDouble(ss[41]));
                                if(ss[42].equals(""))
                                    ss[42]=Integer.toString(0);
                                lshsje.setCreditMoney11(Double.parseDouble(ss[42]));
                                if(ss[43].equals(""))
                                    ss[43]=Integer.toString(0);
                                lshsje.setBalance11(Double.parseDouble(ss[43]));
                                if(ss[44].equals(""))
                                    ss[44]=Integer.toString(0);
                                lshsje.setDebitMoney12(Double.parseDouble(ss[44]));
                                if(ss[45].equals(""))
                                    ss[45]=Integer.toString(0);
                                lshsje.setCreditMoney12(Double.parseDouble(ss[45]));
                                if(ss[46].equals(""))
                                    ss[46]=Integer.toString(0);
                                lshsje.setBalance12(Double.parseDouble(ss[46]));


                                lshsjeList.add(lshsje);
                                auto_id++;
                                count++;
                            }
//                            Session session = null;
//                            try{
//                                session=getsession();
//                                Query query = session.createQuery("delete from Lshsje ");
//                                query.executeUpdate();//先删除在添加
//                                close(session);
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
                            for (int j=0;j<count;j++) {
                                dataManagementService.addLshsje(lshsjeList.get(j));
                            }
                        }catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    break;
                case "lshsfl":
                    if(filename.equals("lshsfl")){
                        try (FileReader reader = new FileReader(path+"/"+duizhaoName);
                             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
                        ){
                            String lineTxt = null;
                            Lshsfl lshsfl = null;
                            int count= 0;
                            List<Lshsfl> lshsflList = new ArrayList<Lshsfl>();
                            while((lineTxt = br.readLine())!= null){
                                lshsfl = new Lshsfl();
                                String[] s = lineTxt.split("\t");
                                int tNums=0;
                                for (int i = lineTxt.length()-1; i >=0 ; i--) {
                                    if(lineTxt.charAt(i)=='\t')
                                        tNums++;
                                    else
                                        break;
                                }
                                String[] ss = new String[s.length+tNums];
                                for (int i = 0; i <s.length ; i++) {
                                    ss[i]=s[i];
                                }
                                for (int i = 0; i < tNums; i++) {
                                    ss[s.length+i]=Integer.toString(0);
                                }
                                lshsfl.setCatNo(ss[0]);
                                lshsfl.setCatName(ss[1]);


                                lshsflList.add(lshsfl);
                                count++;
                            }
//                            Session session = null;
//                            try{
//                                session=getsession();
//                                Query query = session.createQuery("delete from Lshsfl ");
//                                query.executeUpdate();//先删除在添加
//                                close(session);
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
                            for (int j=0;j<count;j++) {
                                dataManagementService.addLshsfl(lshsflList.get(j));
                            }
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case "lshszd":
                    if(filename.equals("lshszd")){
                        try (FileReader reader = new FileReader(path+"/"+duizhaoName);
                             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
                        ){

                            String lineTxt = null;
                            Lshszd lshszd = null;
                            int count= 0;
                            Session session = null;
                            session = getsession();
                            Query last_query = session.createQuery("from Lshszd where hszdAutoId = (select max(hszdAutoId) from Lshszd)");
                            Lshszd last = (Lshszd) last_query.uniqueResult();
                            int auto_id = last == null ? 1 : last.getHszdAutoId() + 1;
                            close(session);
                            List<Lshszd> lshszdList = new ArrayList<Lshszd>();
                            while((lineTxt = br.readLine())!= null){
                                lshszd = new Lshszd();
                                String[] s = lineTxt.split("\t");
                                int tNums=0;
                                for (int i = lineTxt.length()-1; i >=0 ; i--) {
                                    if(lineTxt.charAt(i)=='\t')
                                        tNums++;
                                    else
                                        break;
                                }
                                String[] ss = new String[s.length+tNums];
                                for (int i = 0; i <s.length ; i++) {
                                    ss[i]=s[i];
                                }
                                for (int i = 0; i < tNums; i++) {
                                    ss[s.length+i]=Integer.toString(0);
                                }
                                lshszd.setHszdAutoId(auto_id);
                                lshszd.setSpNo(ss[0]);
                                lshszd.setSpLevel(ss[1]);
                                lshszd.setFinLevel(Byte.parseByte(ss[2]));
                                lshszd.setSpName(ss[3]);
                                lshszd.setCatNo(s[4]);
                                lshszd.setItemNo(ss[5]);

                                lshszdList.add(lshszd);
                                auto_id++;
                                count++;
                            }
//                            Session session = null;
//                            try{
//                                session=getsession();
//                                Query query = session.createQuery("delete from Lshszd ");
//                                query.executeUpdate();//先删除在添加
//                                close(session);
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
                            for (int j=0;j<count;j++) {
                                dataManagementService.addLshszd(lshszdList.get(j));
                            }
                        }catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    break;
                case "lswlje":
                    if(filename.equals("lswlje")){
                        try (FileReader reader = new FileReader(path+"/"+duizhaoName);
                             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
                        ){

                            String lineTxt = null;
                            Lswlje lswlje = null;
                            int count= 0;
                            Session session = null;
                            session = getsession();
                            Query last_query = session.createQuery("from Lswlje where wljeAutoId = (select max(wljeAutoId) from Lswlje)");
                            Lswlje last = (Lswlje) last_query.uniqueResult();
                            int auto_id = last == null ? 1 : last.getWljeAutoId() + 1;
                            close(session);
                            List<Lswlje> lswljeList = new ArrayList<Lswlje>();
                            while((lineTxt = br.readLine())!= null){
                                lswlje = new Lswlje();
                                String[] s = lineTxt.split("\t");
                                int tNums=0;
                                for (int i = lineTxt.length()-1; i >=0 ; i--) {
                                    if(lineTxt.charAt(i)=='\t')
                                        tNums++;
                                    else
                                        break;
                                }
                                String[] ss = new String[s.length+tNums];
                                for (int i = 0; i <s.length ; i++) {
                                    ss[i]=s[i];
                                }
                                for (int i = 0; i < tNums; i++) {
                                    ss[s.length+i]=Integer.toString(0);
                                }
                                lswlje.setWljeAutoId(auto_id);
                                lswlje.setItemNo(ss[0]);
                                lswlje.setCompanyNo(ss[1]);
                                if(ss[2].equals(""))
                                    ss[2]=Integer.toString(0);
                                lswlje.setBalance(Double.parseDouble(ss[2]));
                                if(ss[3].equals(""))
                                    ss[3]=Integer.toString(0);
                                lswlje.setDebitMoney(Double.parseDouble(ss[3]));
                                if(ss[4].equals(""))
                                    ss[4]=Integer.toString(0);
                                lswlje.setCreditMoney(Double.parseDouble(ss[4]));
                                if(ss[5].equals(""))
                                    ss[5]=Integer.toString(0);
                                lswlje.setSupMoney(Double.parseDouble(ss[5]));
                                if(ss[6].equals(""))
                                    ss[6]=Integer.toString(0);
                                lswlje.setDebitMoneySup(Double.parseDouble(ss[6]));
                                if(ss[7].equals(""))
                                    ss[7]=Integer.toString(0);
                                lswlje.setCreditMoneySup(Double.parseDouble(ss[7]));
                                if(ss[8].equals(""))
                                    ss[8]=Integer.toString(0);
                                lswlje.setDebitMoneyAcm(Double.parseDouble(ss[8]));
                                if(ss[9].equals(""))
                                    ss[9]=Integer.toString(0);
                                lswlje.setCreditMoneyAcm(Double.parseDouble(ss[9]));
                                if(ss[10].equals(""))
                                    ss[10]=Integer.toString(0);
                                lswlje.setDebitMoney01(Double.parseDouble(ss[10]));
                                if(ss[11].equals(""))
                                    ss[11]=Integer.toString(0);
                                lswlje.setCreditMoney01(Double.parseDouble(ss[11]));
                                if(ss[12].equals(""))
                                    ss[12]=Integer.toString(0);
                                lswlje.setBalance01(Double.parseDouble(ss[12]));
                                if(ss[13].equals(""))
                                    ss[13]=Integer.toString(0);
                                lswlje.setDebitMoney02(Double.parseDouble(ss[13]));
                                if(ss[14].equals(""))
                                    ss[14]=Integer.toString(0);
                                lswlje.setCreditMoney02(Double.parseDouble(ss[14]));
                                if(ss[15].equals(""))
                                    ss[15]=Integer.toString(0);
                                lswlje.setBalance02(Double.parseDouble(ss[15]));
                                if(ss[16].equals(""))
                                    ss[16]=Integer.toString(0);
                                lswlje.setDebitMoney03(Double.parseDouble(ss[16]));
                                if(ss[17].equals(""))
                                    ss[17]=Integer.toString(0);
                                lswlje.setCreditMoney03(Double.parseDouble(ss[17]));
                                if(ss[18].equals(""))
                                    ss[18]=Integer.toString(0);
                                lswlje.setBalance03(Double.parseDouble(ss[18]));
                                if(ss[19].equals(""))
                                    ss[19]=Integer.toString(0);
                                lswlje.setDebitMoney04(Double.parseDouble(ss[19]));
                                if(ss[20].equals(""))
                                    ss[20]=Integer.toString(0);
                                lswlje.setCreditMoney04(Double.parseDouble(ss[20]));
                                if(ss[21].equals(""))
                                    ss[21]=Integer.toString(0);
                                lswlje.setBalance04(Double.parseDouble(ss[21]));
                                if(ss[22].equals(""))
                                    ss[22]=Integer.toString(0);
                                lswlje.setDebitMoney05(Double.parseDouble(ss[22]));
                                if(ss[23].equals(""))
                                    ss[23]=Integer.toString(0);
                                lswlje.setCreditMoney05(Double.parseDouble(ss[23]));
                                if(ss[24].equals(""))
                                    ss[24]=Integer.toString(0);
                                lswlje.setBalance05(Double.parseDouble(ss[24]));
                                if(ss[25].equals(""))
                                    ss[25]=Integer.toString(0);
                                lswlje.setDebitMoney06(Double.parseDouble(ss[25]));
                                if(ss[26].equals(""))
                                    ss[26]=Integer.toString(0);
                                lswlje.setCreditMoney06(Double.parseDouble(ss[26]));
                                if(ss[27].equals(""))
                                    ss[27]=Integer.toString(0);
                                lswlje.setBalance06(Double.parseDouble(ss[27]));
                                if(ss[28].equals(""))
                                    ss[28]=Integer.toString(0);
                                lswlje.setDebitMoney07(Double.parseDouble(ss[28]));
                                if(ss[29].equals(""))
                                    ss[29]=Integer.toString(0);
                                lswlje.setCreditMoney07(Double.parseDouble(ss[29]));
                                if(ss[30].equals(""))
                                    ss[30]=Integer.toString(0);
                                lswlje.setBalance07(Double.parseDouble(ss[30]));
                                if(ss[31].equals(""))
                                    ss[31]=Integer.toString(0);
                                lswlje.setDebitMoney08(Double.parseDouble(ss[31]));
                                if(ss[32].equals(""))
                                    ss[32]=Integer.toString(0);
                                lswlje.setCreditMoney08(Double.parseDouble(ss[32]));
                                if(ss[33].equals(""))
                                    ss[33]=Integer.toString(0);
                                lswlje.setBalance08(Double.parseDouble(ss[33]));
                                if(ss[34].equals(""))
                                    ss[34]=Integer.toString(0);
                                lswlje.setDebitMoney09(Double.parseDouble(ss[34]));
                                if(ss[35].equals(""))
                                    ss[35]=Integer.toString(0);
                                lswlje.setCreditMoney09(Double.parseDouble(ss[35]));
                                if(ss[36].equals(""))
                                    ss[36]=Integer.toString(0);
                                lswlje.setBalance09(Double.parseDouble(ss[36]));
                                if(ss[37].equals(""))
                                    ss[37]=Integer.toString(0);
                                lswlje.setDebitMoney10(Double.parseDouble(ss[37]));
                                if(ss[38].equals(""))
                                    ss[38]=Integer.toString(0);
                                lswlje.setCreditMoney10(Double.parseDouble(ss[38]));
                                if(ss[39].equals(""))
                                    ss[39]=Integer.toString(0);
                                lswlje.setBalance10(Double.parseDouble(ss[39]));
                                if(ss[40].equals(""))
                                    ss[40]=Integer.toString(0);
                                lswlje.setDebitMoney11(Double.parseDouble(ss[40]));
                                if(ss[41].equals(""))
                                    ss[41]=Integer.toString(0);
                                lswlje.setCreditMoney11(Double.parseDouble(ss[41]));
                                if(ss[42].equals(""))
                                    ss[42]=Integer.toString(0);
                                lswlje.setBalance11(Double.parseDouble(ss[42]));
                                if(ss[43].equals(""))
                                    ss[43]=Integer.toString(0);
                                lswlje.setDebitMoney12(Double.parseDouble(ss[43]));
                                if(ss[44].equals(""))
                                    ss[44]=Integer.toString(0);
                                lswlje.setCreditMoney12(Double.parseDouble(ss[44]));
                                if(ss[45].equals(""))
                                    ss[45]=Integer.toString(0);
                                lswlje.setBalance12(Double.parseDouble(ss[45]));

                                lswljeList.add(lswlje);
                                auto_id++;
                                count++;
                            }
//                            Session session = null;
//                            try{
//                                session=getsession();
//                                Query query = session.createQuery("delete from Lswlje ");
//                                query.executeUpdate();//先删除在添加
//                                close(session);
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
                            for (int j=0;j<count;j++) {
                                dataManagementService.addLswlje(lswljeList.get(j));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    break;
                case "lswlsl":
                    if(filename.equals("lswlsl")){
                        try (FileReader reader = new FileReader(path+"/"+duizhaoName);
                             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
                        ){

                            String lineTxt = null;
                            Lswlsl lswlsl = null;
                            int count= 0;
                            Session session = null;
                            session = getsession();
                            Query last_query = session.createQuery("from Lswlsl where wlslAutoId = (select max(wlslAutoId) from Lswlsl)");
                            Lswlsl last = (Lswlsl) last_query.uniqueResult();
                            int auto_id = last == null ? 1 : last.getWlslAutoId() + 1;
                            close(session);
                            List<Lswlsl> lswlslList = new ArrayList<Lswlsl>();
                            while((lineTxt = br.readLine())!= null){
                                lswlsl = new Lswlsl();
                                String[] s = lineTxt.split("\t");
                                int tNums=0;
                                for (int i = lineTxt.length()-1; i >=0 ; i--) {
                                    if(lineTxt.charAt(i)=='\t')
                                        tNums++;
                                    else
                                        break;
                                }
                                String[] ss = new String[s.length+tNums];
                                for (int i = 0; i <s.length ; i++) {
                                    ss[i]=s[i];
                                }
                                for (int i = 0; i < tNums; i++) {
                                    ss[s.length+i]=Integer.toString(0);
                                }
                                lswlsl.setWlslAutoId(auto_id);
                                lswlsl.setItemNo(ss[0]);
                                lswlsl.setCompanyNo(ss[1]);
                                if(ss[2].equals(""))
                                    ss[2]=Integer.toString(0);
                                lswlsl.setLeftQty(Double.parseDouble(ss[2]));
                                if(ss[3].equals(""))
                                    ss[3]=Integer.toString(0);
                                lswlsl.setUnitPrice(Double.parseDouble(ss[3]));
                                if(ss[4].equals(""))
                                    ss[4]=Integer.toString(0);
                                lswlsl.setDebitQty(Double.parseDouble(ss[4]));
                                if(ss[5].equals(""))
                                    ss[5]=Integer.toString(0);
                                lswlsl.setCreditQty(Double.parseDouble(ss[5]));
                                if(ss[6].equals(""))
                                    ss[6]=Integer.toString(0);
                                lswlsl.setSupQty(Double.parseDouble(ss[6]));
                                if(ss[7].equals(""))
                                    ss[7]=Integer.toString(0);
                                lswlsl.setDebitQtySup(Double.parseDouble(ss[7]));
                                if(ss[8].equals(""))
                                    ss[8]=Integer.toString(0);
                                lswlsl.setCreditQtySup(Double.parseDouble(ss[8]));
                                if(ss[9].equals(""))
                                    ss[9]=Integer.toString(0);
                                lswlsl.setDebitQtyAcm(Double.parseDouble(ss[9]));
                                if(ss[10].equals(""))
                                    ss[10]=Integer.toString(0);
                                lswlsl.setCreditQtyAcm(Double.parseDouble(ss[10]));
                                if(ss[11].equals(""))
                                    ss[11]=Integer.toString(0);
                                lswlsl.setDebitQty01(Double.parseDouble(ss[11]));
                                if(ss[12].equals(""))
                                    ss[12]=Integer.toString(0);
                                lswlsl.setCreditQty01(Double.parseDouble(ss[12]));
                                if(ss[13].equals(""))
                                    ss[13]=Integer.toString(0);
                                lswlsl.setLeftQty01(Double.parseDouble(ss[13]));
                                if(ss[14].equals(""))
                                    ss[14]=Integer.toString(0);
                                lswlsl.setDebitQty02(Double.parseDouble(ss[14]));
                                if(ss[15].equals(""))
                                    ss[15]=Integer.toString(0);
                                lswlsl.setCreditQty02(Double.parseDouble(ss[15]));
                                if(ss[16].equals(""))
                                    ss[16]=Integer.toString(0);
                                lswlsl.setLeftQty02(Double.parseDouble(ss[16]));
                                if(ss[17].equals(""))
                                    ss[17]=Integer.toString(0);
                                lswlsl.setDebitQty03(Double.parseDouble(ss[17]));
                                if(ss[18].equals(""))
                                    ss[18]=Integer.toString(0);
                                lswlsl.setCreditQty03(Double.parseDouble(ss[18]));
                                if(ss[19].equals(""))
                                    ss[19]=Integer.toString(0);
                                lswlsl.setLeftQty03(Double.parseDouble(ss[19]));
                                if(ss[20].equals(""))
                                    ss[20]=Integer.toString(0);
                                lswlsl.setDebitQty04(Double.parseDouble(ss[20]));
                                if(ss[21].equals(""))
                                    ss[21]=Integer.toString(0);
                                lswlsl.setCreditQty04(Double.parseDouble(ss[21]));
                                if(ss[22].equals(""))
                                    ss[22]=Integer.toString(0);
                                lswlsl.setLeftQty04(Double.parseDouble(ss[22]));
                                if(ss[23].equals(""))
                                    ss[23]=Integer.toString(0);
                                lswlsl.setDebitQty05(Double.parseDouble(ss[23]));
                                if(ss[24].equals(""))
                                    ss[24]=Integer.toString(0);
                                lswlsl.setCreditQty05(Double.parseDouble(ss[24]));
                                if(ss[25].equals(""))
                                    ss[25]=Integer.toString(0);
                                lswlsl.setLeftQty05(Double.parseDouble(ss[25]));
                                if(ss[26].equals(""))
                                    ss[26]=Integer.toString(0);
                                lswlsl.setDebitQty06(Double.parseDouble(ss[26]));
                                if(ss[27].equals(""))
                                    ss[27]=Integer.toString(0);
                                lswlsl.setCreditQty06(Double.parseDouble(ss[27]));
                                if(ss[28].equals(""))
                                    ss[28]=Integer.toString(0);
                                lswlsl.setLeftQty06(Double.parseDouble(ss[28]));
                                if(ss[29].equals(""))
                                    ss[29]=Integer.toString(0);
                                lswlsl.setDebitQty07(Double.parseDouble(ss[29]));
                                if(ss[30].equals(""))
                                    ss[30]=Integer.toString(0);
                                lswlsl.setCreditQty07(Double.parseDouble(ss[30]));
                                if(ss[30].equals(""))
                                    ss[30]=Integer.toString(0);
                                lswlsl.setLeftQty07(Double.parseDouble(ss[30]));
                                if(ss[31].equals(""))
                                    ss[31]=Integer.toString(0);
                                lswlsl.setDebitQty08(Double.parseDouble(ss[31]));
                                if(ss[32].equals(""))
                                    ss[32]=Integer.toString(0);
                                lswlsl.setCreditQty08(Double.parseDouble(ss[32]));
                                if(ss[33].equals(""))
                                    ss[33]=Integer.toString(0);
                                lswlsl.setLeftQty08(Double.parseDouble(ss[33]));
                                if(ss[34].equals(""))
                                    ss[34]=Integer.toString(0);
                                lswlsl.setDebitQty09(Double.parseDouble(ss[34]));
                                if(ss[35].equals(""))
                                    ss[35]=Integer.toString(0);
                                lswlsl.setCreditQty09(Double.parseDouble(ss[35]));
                                if(ss[36].equals(""))
                                    ss[36]=Integer.toString(0);
                                lswlsl.setLeftQty09(Double.parseDouble(ss[36]));
                                if(ss[37].equals(""))
                                    ss[37]=Integer.toString(0);
                                lswlsl.setDebitQty10(Double.parseDouble(ss[37]));
                                if(ss[38].equals(""))
                                    ss[38]=Integer.toString(0);
                                lswlsl.setCreditQty10(Double.parseDouble(ss[38]));
                                if(ss[39].equals(""))
                                    ss[39]=Integer.toString(0);
                                lswlsl.setLeftQty10(Double.parseDouble(ss[39]));
                                if(ss[40].equals(""))
                                    ss[40]=Integer.toString(0);
                                lswlsl.setDebitQty11(Double.parseDouble(ss[40]));
                                if(ss[41].equals(""))
                                    ss[41]=Integer.toString(0);
                                lswlsl.setCreditQty11(Double.parseDouble(ss[41]));
                                if(ss[42].equals(""))
                                    ss[42]=Integer.toString(0);
                                lswlsl.setLeftQty11(Double.parseDouble(ss[42]));
                                if(ss[43].equals(""))
                                    ss[43]=Integer.toString(0);
                                lswlsl.setDebitQty12(Double.parseDouble(ss[44]));
                                if(ss[45].equals(""))
                                    ss[45]=Integer.toString(0);
                                lswlsl.setCreditQty12(Double.parseDouble(ss[45]));
                                if(ss[46].equals(""))
                                    ss[46]=Integer.toString(0);
                                lswlsl.setLeftQty12(Double.parseDouble(ss[46]));

                                lswlslList.add(lswlsl);
                                auto_id++;
                                count++;
                            }
//                            Session session = null;
//                            try{
//                                session=getsession();
//                                Query query = session.createQuery("delete from Lswlsl ");
//                                query.executeUpdate();//先删除在添加
//                                close(session);
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
                            for (int j=0;j<count;j++) {
                                dataManagementService.addLswlsl(lswlslList.get(j));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                    break;
                case "lswldw":
                    if(filename.equals("lswldw")){
                        try (FileReader reader = new FileReader(path+"/"+duizhaoName);
                             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
                        ){
                            String lineTxt = null;
                            Lswldw lswldw = null;
                            int count= 0;
                            List<Lswldw> lswldwList = new ArrayList<Lswldw>();
                            while((lineTxt = br.readLine())!= null){
                                lswldw = new Lswldw();
                                String[] s = lineTxt.split("\t");
                                int tNums=0;
                                for (int i = lineTxt.length()-1; i >=0 ; i--) {
                                    if(lineTxt.charAt(i)=='\t')
                                        tNums++;
                                    else
                                        break;
                                }
                                String[] ss = new String[s.length+tNums];
                                for (int i = 0; i <s.length ; i++) {
                                    ss[i]=s[i];
                                }
                                for (int i = 0; i < tNums; i++) {
                                    ss[s.length+i]=Integer.toString(0);
                                }
                                //lswldw.setHspzAutoId(count+1);
                                lswldw.setCompanyNo(ss[0]);
                                lswldw.setCompanyName(ss[1]);
                                lswldw.setCatNo1(ss[2]);
                                lswldw.setCont(ss[3]);
                                lswldw.setTel(ss[4]);
                                lswldw.setCompanyAddr(s[5]);
                                lswldw.setCompanyPost(ss[6]);
                                lswldw.setBank(ss[7]);
                                lswldw.setAccount(ss[8]);
                                lswldw.setCreditStanding(ss[9]);
                                lswldw.setTaxIdNo(ss[10]);
                                lswldw.setMemo(ss[11]);

                                lswldwList.add(lswldw);
                                count++;
                            }
//                            Session session = null;
//                            try{
//                                session=getsession();
//                                Query query = session.createQuery("delete from Lswldw ");
//                                query.executeUpdate();//先删除在添加
//                                close(session);
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
                            for (int j=0;j<count;j++) {
                                dataManagementService.addLswldw(lswldwList.get(j));
                            }}catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                    break;


            }
        }

}