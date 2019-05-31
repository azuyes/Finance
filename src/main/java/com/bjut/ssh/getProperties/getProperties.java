package com.bjut.ssh.getProperties;


import java.io.BufferedInputStream;
import java.io.File;
import java.io .FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io .support.PropertiesLoaderUtils;
/**
 * @Title: getProperties
 * @Description: TODO
 * @Author: LYH
 * @CreateDate: 2018/7/16 14:54
 * @Version: 1.0
 */

public class getProperties {

    public static String getProperties(String keyWord){
        Properties prop = new Properties();
        String value = null;
        try {
            // 通过输入缓冲流进行读取配置文件
            String dirPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();//获取config.properties文件所在的父目录
            File file = new File(dirPath,"/jdbc.properties");

            InputStream InputStream = new getProperties().getClass().getResourceAsStream("/jdbc.properties");
            // 加载输入流
            prop.load(InputStream);
            // 根据关键字获取value值
            value = prop.getProperty(keyWord);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }


}