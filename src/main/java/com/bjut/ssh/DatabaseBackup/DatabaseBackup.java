package com.bjut.ssh.DatabaseBackup;

import org.hibernate.cfg.Configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import com.bjut.ssh.SQLServerBak.SQLServerBak;

/**
 * @Title: DatabaseBackup
 * @Description: TODO
 * @Author: LYH
 * @CreateDate: 2018/5/16 12:24
 * @Version: 1.0
 */

public class DatabaseBackup {

    /** 访问数据库的用户名 */
    private String username;
    /** 访问数据库的密码 */
    private String password;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public DatabaseBackup(String username, String password) {
        this.username = username;
        this.password = password;
    }


    /**
     * 备份数据库，如果指定路径的文件不存在会自动生成
     *
     * @param path
     *            备份文件的路径
     * @param tablename
     *            要备份的数据库表名
     */
    public void backup(String driver,String database,String path, String tablename) {
        try {
            if(database.contains("mysql")){
                String command = "mysqldump -u" + username + " -p" + password + " --set-charset=utf8 " +"ssh_finance "+tablename+">"+path+"\\"+tablename+".sql";
                Runtime rt = Runtime.getRuntime();
                rt.exec("cmd /c " +command);
            }else if(database.contains("oracle")){
                String command = "exp " + username + "/" + password + "@" +"ssh_finance "+"file="+path+"\\"+tablename+".dmp"+" tables=("+tablename+")";
                Runtime rt = Runtime.getRuntime();
                rt.exec("cmd /c " +command);
            }else if(database.contains("sqlserver")){
                SQLServerBak sqlServerBak = new SQLServerBak(username,password,driver,database);
                sqlServerBak.doBackUp(path);
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    *@author LYH
    *@Description 历史数据备份
    *@Date 2018/6/12 10:38
    *@Param [path, year]
    *@return void
    **/

    public void backupDatabase(String driver,String database,String path, String tablename,String year) {
        try{
            if(database.contains("mysql")){
                String command = "mysqldump -u" + username + " -p" + password + " --set-charset=utf8 " +"ssh_finance "+tablename+year+">"+path+"\\"+tablename+year+".sql";
                Runtime rt = Runtime.getRuntime();
                rt.exec("cmd /c " +command);
            }else if(database.contains("oracle")){
                String command = "exp " + username + "/" + password + "@" +"ssh_finance "+"file="+path+"\\"+tablename+year+".dmp"+" tables=("+tablename+year+")";
                Runtime rt = Runtime.getRuntime();
                rt.exec("cmd /c " +command);
            }else if(database.contains("sqlserver")){
                SQLServerBak sqlServerBak = new SQLServerBak(username,password,driver,database);
                sqlServerBak.doBackUp(path);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    *@author LYH
    *@Description 历史数据恢复
    *@Date 2018/6/12 14:57
    *@Param [path, year, tablename]
    *@return void
    **/

    public void restoreDatabase(String driver,String database,String path, String year,String tablename) {
        try{
           if(database.contains("mysql")){
                String command ="Mysql -u" + username + " -p" + password +" -f -D "+"ssh_finance"+"<"+path+"\\"+tablename+year+".sql";
                Runtime rt = Runtime.getRuntime();
                rt.exec("cmd /c " +command);
            }else if(database.contains("oracle")){
                String command = "imp" + username + "/" + password + "@" +"ssh_finance "+"file="+path+"\\"+tablename+year+".dmp"+" tables=("+tablename+year+")";
                Runtime rt = Runtime.getRuntime();
                rt.exec("cmd /c " +command);
            }else if(database.contains("sqlserver")){
               SQLServerBak sqlServerBak = new SQLServerBak(username,password,driver,database);
               sqlServerBak.dorestore(path);
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }


/**
*@author LYH
*@Description 恢复数据库
*@Date 2018/5/16 14:39
*@Param [path]备份数据库的地址
*@return void
**/

//
    public void restore(String driver,String database,String path,String tablename) {
        try {
           if(database.contains("mysql")){
                String command ="Mysql -u" + username + " -p" + password +" -f -D "+"ssh_finance"+"<"+path+"\\"+tablename+".sql";
                Runtime rt = Runtime.getRuntime();
                rt.exec("cmd /c " +command);
            }else if(database.contains("oracle")){
               String command = "imp " + username + "/" + password + "@" +"ssh_finance "+"file="+path+"\\"+tablename+".dmp"+" tables=("+tablename+")";
               Runtime rt = Runtime.getRuntime();
               rt.exec("cmd /c " +command);
            }else if(database.contains("sqlserver")){
               SQLServerBak sqlServerBak = new SQLServerBak(username,password,driver,database);
               sqlServerBak.dorestore(path);
            }

        }  catch (IOException e) {
            e.printStackTrace();
        }
    }




}