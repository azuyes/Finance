package com.bjut.ssh.setProperties;
import com.bjut.ssh.getProperties.getProperties;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.Properties;


/**
 * @Title: setProperties
 * @Description: TODO
 * @Author: LYH
 * @CreateDate: 2018/7/23 21:07
 * @Version: 1.0
 */

public class setProperties {
    private static Properties prop;

    public static void load(){
        //这里的path是项目文件的绝对路径
        //先获取项目绝对路径：Thread.currentThread().getContextClassLoader().getResource("").getPath();
        //然后在项目路径后面拼接"properties/sysConfig.properties";
        prop= new Properties();// 属性集合对象
        try {
            InputStream InputStream = new getProperties().getClass().getResourceAsStream("/jdbc.properties");
            prop.load(InputStream);
            InputStream.close();// 关闭流
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void setProperties(String driver,String username,String password){
        if(prop==null){
            load();
            System.out.println("修改前重新加载一遍");
        }
        //System.out.println("获取添加或修改前的属性值："+key+"=" + prop.getProperty(key));
       // System.out.println(value);
       // prop.setProperty(key, value);
        // 文件输出流

        try{
            if(driver.contains("mysql")){
                FileOutputStream oFile = new FileOutputStream("/jdbc.properties", true);//true表示追加打开
                prop.setProperty("jdbc.username", username);
                prop.setProperty("jdbc.password",password );
                prop.setProperty("jdbc.driver", "com.mysql.jdbc.Driver");
                prop.setProperty("jdbc.url", "jdbc:mysql://127.0.0.1:3306/cx_finance?characterEncoding=utf8&useSSL=true");//ssh_finance为数据库名，以后要改成cx-finance
                prop.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect" );
                prop.store(oFile, "Update info" );
                oFile.close();

            }else if (driver.contains("oracle")){
                FileOutputStream oFile = new FileOutputStream("/jdbc.properties", true);//true表示追加打开
                prop.setProperty("jdbc.username", username);
                prop.setProperty("jdbc.password",password );
                prop.setProperty("jdbc.driver", "oracle.jdbc.driver.OracleDriver");
                prop.setProperty("jdbc.url", "jdbc:oracle:thin:@localhost:1521:cx_finance");//sshfinance为数据库名，以后要改成cx-finance
                prop.setProperty("hibernate.dialect","org.hibernate.dialect.OracleDialect" );
                prop.store(oFile, "Update info" );
                oFile.close();
            }else if (driver.contains("sqlserver")){
                FileOutputStream oFile = new FileOutputStream("/jdbc.properties", true);//true表示追加打开
                prop.setProperty("jdbc.username", username);
                prop.setProperty("jdbc.password",password );
                prop.setProperty("jdbc.driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
                prop.setProperty("jdbc.url", "jdbc:sqlserver://localhost;DatabaseName=cx_finance");//ssh_finance为数据库名，以后要改成cx-finance
                prop.setProperty("hibernate.dialect","org.hibernate.dialect.SQLServerDialect" );
                prop.store(oFile, "Update info" );
                oFile.close();
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("获取添加或修改后的属性值："+driver+"=" + prop.getProperty("jdbc.driver"));
        System.out.println("获取添加或修改后的属性值："+"jdbc.username"+"=" + prop.getProperty("jdbc.username"));
        System.out.println("获取添加或修改后的属性值："+"jdbc.url"+"=" + prop.getProperty("jdbc.url"));

    }

}