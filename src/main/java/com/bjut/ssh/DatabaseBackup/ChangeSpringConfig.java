package com.bjut.ssh.DatabaseBackup;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @Title: ChangeSpringConfig
 * @Description: TODO
 * @Author: czh
 * @CreateDate: 2018/10/12 10:52
 * @Version: 1.0
 */

public class ChangeSpringConfig extends HttpServlet
{

    /**
     * The doGet method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to get.
     *
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        doPost(request, response);
    }

    /**
     * The doPost method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to post.
     *
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
//先取得servleContext对象，提供给spring的WebApplicationUtils来动态修改applicationContext.xml

//        ApplicationContext applicationContext =new ClassPathXmlApplicationContext("applicationContext.xml");
//        BeanDefinition bean = new GenericBeanDefinition();
//
//        try {
//            bean.setBeanClassName("com.mchange.v2.c3p0.ComboPooledDataSource");
//
//            DefaultListableBeanFactory fty = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
//            fty.registerBeanDefinition("DataSource2", bean);
//
//            ComboPooledDataSource cpds = (ComboPooledDataSource) applicationContext.getBean("DataSource2");
//            cpds.setDriverClass("${jdbc.driver}");
//            cpds.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/cx-finance-2?characterEncoding=utf8&useSSL=true");
//            cpds.setUser("${jdbc.username}");
//            cpds.setPassword("${jdbc.password}");
//            cpds.setMaxPoolSize(40);
//            cpds.setMinPoolSize(1);
//            cpds.setInitialPoolSize(10);
//            cpds.setMaxIdleTime(20);
//            System.out.println("添加数据库" + cpds.getJdbcUrl());
//
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
    }

}