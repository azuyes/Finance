package com.bjut.ssh.dao.OtherSettings;

import com.bjut.ssh.DatabaseBackup.ChangeSpringConfig;
import com.bjut.ssh.DynamicDatabase.DatabaseContextHolder;
import com.bjut.ssh.entity.*;
import com.bjut.ssh.getProperties.getProperties;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

/**
 * @Title: DataManagementDao
 * @Description: 数据管理
 * @Author: LYH
 * @CreateDate: 2018/4/13 10:52
 * @Version: 1.0
 */

@Repository
@Transactional

public class DataManagementDao {

    @Autowired
    private SessionFactory sessionFactory;

    Session getsession() {

        return this.sessionFactory.openSession();
    }
    public void close(Session session){
        if(session != null)
            session.close();
    }

    public void changeDatabase(String datasource){
        //DatabaseContextHolder.setCustomerType(datasource);
        String a = DatabaseContextHolder.getCustomerType();
        System.out.println(a);
    }


    //TODO:待验证
    public void createDatabase(String name, HttpServletRequest request, HttpServletResponse response){
        try {
            //一开始必须填一个已经存在的数据库
            Connection conn = SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
            Statement stat = conn.createStatement();

            //创建数据库hello
//            stat.executeUpdate("create database " + name);

            //打开创建的数据库
            stat.close();
            conn.close();
            String url = "jdbc:mysql://localhost:3306/" + name + "?useUnicode=true&characterEncoding=utf-8";
            conn = DriverManager.getConnection(url, "root", "123456");
            stat = conn.createStatement();

//            FileSystemResource rc = new FileSystemResource("D:/cx_finance.sql");
//            EncodedResource er = new EncodedResource(rc, "utf-8");
//            ScriptUtils.executeSqlScript(conn, er);

            doPost(name, request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void doPost(String name, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
//先取得servleContext对象，提供给spring的WebApplicationUtils来动态修改applicationContext.xml

        //ApplicationContext applicationContext =new ClassPathXmlApplicationContext("applicationContext.xml");
        BeanDefinition bean = new GenericBeanDefinition();

        try {
            bean.setBeanClassName("com.mchange.v2.c3p0.ComboPooledDataSource");

//            DefaultListableBeanFactory fty = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
//            fty.registerBeanDefinition("dataSource2", bean);

            SAXReader reader = new SAXReader();
            Document document = reader.read(new File("D:/WORK/ssh_finance/target/classes/applicationContext.xml"));
            //4、向指定元素节点中增加子元素节:
            //获取根元素
            Element rootElement = document.getRootElement();
            //添加元素
            Element beanElement = rootElement.addElement("bean");
            //设置文本
            beanElement.addAttribute("id", "dataSource2");
            beanElement.addAttribute("class", "com.mchange.v2.c3p0.ComboPooledDataSource");
            beanElement.addAttribute("destroy-method", "close");

            Element e1 = beanElement.addElement("property");
            e1.addAttribute("name", "jdbcUrl");
            e1.addAttribute("value", "${jdbc.url2}");

            Element e2 = beanElement.addElement("property");
            e2.addAttribute("name", "driverClass");
            e2.addAttribute("value", "${jdbc.driver}");

            Element e3 = beanElement.addElement("property");
            e3.addAttribute("name", "user");
            e3.addAttribute("value", "${jdbc.username}");

            Element e4 = beanElement.addElement("property");
            e4.addAttribute("name", "password");
            e4.addAttribute("value", "${jdbc.password}");

            Element e5 = beanElement.addElement("property");
            e5.addAttribute("name", "maxPoolSize");
            e5.addAttribute("value", "40");

            Element e6 = beanElement.addElement("property");
            e6.addAttribute("name", "minPoolSize");
            e6.addAttribute("value", "1");

            Element e7 = beanElement.addElement("property");
            e7.addAttribute("name", "initialPoolSize");
            e7.addAttribute("value", "1");

            Element e8 = beanElement.addElement("property");
            e8.addAttribute("name", "maxIdleTime");
            e8.addAttribute("value", "20");

            //Element dynamic = document.elementByID("dynamicDataSource");

            Element dynamic = null;
            for(Object obj : rootElement.elements("bean")){
                Element ele = (Element)obj;
                if(ele.attributeValue("id").equals("dynamicDataSource")){
                    dynamic = ele;
                }
            }

            Element prop = (Element) dynamic.elements().get(0);
            Element map = prop.element("map");
            Element entry = map.addElement("entry");
            entry.addAttribute("value-ref", "dataSource2");
            entry.addAttribute("key", "dataSource2");

            //写回文件
            write2XML(document);

//            ComboPooledDataSource cpds = (ComboPooledDataSource) applicationContext.getBean("dataSource2");
//            cpds.setDriverClass("com.mysql.jdbc.Driver");
//            cpds.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/" + name + "?characterEncoding=utf8&useSSL=true");
//            cpds.setUser("root");
//            cpds.setPassword("123456");
//            cpds.setMaxPoolSize(40);
//            cpds.setMinPoolSize(1);
//            cpds.setInitialPoolSize(10);
//            cpds.setMaxIdleTime(20);
            System.out.println("添加数据库");

            //ComboPooledDataSource dynamicDataSource = (ComboPooledDataSource) applicationContext.getBean("dynamicDataSource");

            DatabaseContextHolder.setCustomerType("dataSource2");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void write2XML(Document document) throws IOException {
        OutputFormat format = OutputFormat.createPrettyPrint();
        //format.setEncoding("UTF-8");//默认的编码就是UTF-8
        XMLWriter writer = new XMLWriter( new FileOutputStream("D:/WORK/ssh_finance/target/classes/applicationContext.xml"), format );
        writer.write( document );
    }

    public  List<Lsusgn> DataBak(){

        Session session = null;
        Transaction ts = null;
        try {
            session = getsession();
            ts = session.beginTransaction();
            Query query = session.createQuery("from Lsusgn");
            List<Lsusgn> lsusgnList = query.list();
            ts.commit();
            close(session);

            return lsusgnList;
        }catch (Exception e){
            e.printStackTrace();
            ts.rollback();
            return null;
        }
    }

/**
*@author LYH
*@Description 导入Lswlfl数据表文件(覆盖原有数据)
*@Date 2018/5/8 10:55
*@Param [lswlfl]
*@return com.bjut.ssh.entity.Msg
**/

    public void addLswlfl(Lswlfl lswlfl){

        Session session = null;
        Transaction ts = null;
        try{
            session=getsession();
            ts = session.beginTransaction();
            session.saveOrUpdate(lswlfl);
            ts.commit();
            close(session);
        }catch (Exception e){
            ts.rollback();
            e.printStackTrace();
        }
    }

    /**
    *@author LYH
    *@Description 导入Lsconf数据表文件(覆盖原有数据)
    *@Date 2018/5/9 15:01
    *@Param [lsconf]
    *@return void
    **/

    public void addLsconf(Lsconf lsconf){

        Session session = null;
        Transaction ts = null;
        try{
            session=getsession();
            ts = session.beginTransaction();
            session.saveOrUpdate(lsconf);
            ts.commit();
            close(session);
        }catch (Exception e){
            ts.rollback();
            e.printStackTrace();
        }
    }

    public void addLspzbh(Lspzbh lspzbh){

        Session session = null;
        Transaction ts = null;
        try{
            session=getsession();
            ts = session.beginTransaction();
            session.saveOrUpdate(lspzbh);
            ts.commit();
            close(session);
        }catch (Exception e) {
            ts.rollback();
            e.printStackTrace();
        }
    }

    public void addLskmzd(Lskmzd lskmzd){

        Session session = null;
        Transaction ts = null;
        try{
            session=getsession();
            ts = session.beginTransaction();
            session.saveOrUpdate(lskmzd);
            ts.commit();
            close(session);
        }catch (Exception e) {
            ts.rollback();
            e.printStackTrace();
        }
    }


    public void addLskmsl(Lskmsl lskmsl){

        Session session = null;
        Transaction ts = null;
        try{
            session=getsession();
            ts = session.beginTransaction();
            session.saveOrUpdate(lskmsl);
            ts.commit();
            close(session);
        }catch (Exception e) {
            ts.rollback();
            e.printStackTrace();
        }
    }

    public void addLszczd(Lszczd lszczd){

        Session session = null;
        Transaction ts = null;
        try{
            session=getsession();
            ts = session.beginTransaction();
            session.saveOrUpdate(lszczd);
            ts.commit();
            close(session);
        }catch (Exception e) {
            ts.rollback();
            e.printStackTrace();
        }
    }

    public void addLcbzd(Lcbzd lcbzd){

        Session session = null;
        Transaction ts = null;
        try{
            session=getsession();
            ts = session.beginTransaction();
            session.saveOrUpdate(lcbzd);
            ts.commit();
            close(session);
        }catch (Exception e) {
            ts.rollback();
            e.printStackTrace();
        }
    }

    public void addLszcdy(Lszcdy lszcdy){

        Session session = null;
        Transaction ts = null;
        try{
            session=getsession();
            ts = session.beginTransaction();
            session.saveOrUpdate(lszcdy);
            ts.commit();
            close(session);
        }catch (Exception e) {
            ts.rollback();
            e.printStackTrace();
        }
    }

    public void addLcdyzd(Lcdyzd lcdyzd){

        Session session = null;
        Transaction ts = null;
        try{
            session=getsession();
            ts = session.beginTransaction();
            session.saveOrUpdate(lcdyzd);
            ts.commit();
            close(session);
        }catch (Exception e) {
            ts.rollback();
            e.printStackTrace();
        }
    }

    public void addLsszzd(Lsszzd lsszzd){

        Session session = null;
        Transaction ts = null;
        try{
            session=getsession();
            ts = session.beginTransaction();
            session.saveOrUpdate(lsszzd);
            ts.commit();
            close(session);
        }catch (Exception e) {
            ts.rollback();
            e.printStackTrace();
        }
    }

    public void addLspzk1(Lspzk1 lspzk1){

        Session session = null;
        Transaction ts = null;
        try{
            session=getsession();
            ts = session.beginTransaction();
            session.saveOrUpdate(lspzk1);
            ts.commit();
            close(session);
        }catch (Exception e) {
            ts.rollback();
            e.printStackTrace();
        }
    }

    public void addLsyspz(Lsyspz lsyspz){

        Session session = null;
        Transaction ts = null;
        try{
            session=getsession();
            ts = session.beginTransaction();
            session.saveOrUpdate(lsyspz);
            ts.commit();
            close(session);
        }catch (Exception e) {
            ts.rollback();
            e.printStackTrace();
        }
    }

    public void addLshspz(Lshspz lshspz){

        Session session = null;
        Transaction ts = null;
        try{
            session=getsession();
            ts = session.beginTransaction();
            session.saveOrUpdate(lshspz);
            ts.commit();
            close(session);
        }catch (Exception e) {
            ts.rollback();
            e.printStackTrace();
        }
    }

    public void addLshssl(Lshssl lshssl){

        Session session = null;
        Transaction ts = null;
        try{
            session=getsession();
            ts = session.beginTransaction();
            session.saveOrUpdate(lshssl);
            ts.commit();
            close(session);
        }catch (Exception e) {
            ts.rollback();
            e.printStackTrace();
        }
    }

    public void addLshsje(Lshsje lshsje){

        Session session = null;
        Transaction ts = null;
        try{
            session=getsession();
            ts = session.beginTransaction();
            session.saveOrUpdate(lshsje);
            ts.commit();
            close(session);
        }catch (Exception e) {
            ts.rollback();
            e.printStackTrace();
        }
    }

    public void addLshsfl(Lshsfl lshsfl){

        Session session = null;
        Transaction ts = null;
        try{
            session=getsession();
            ts = session.beginTransaction();
            session.saveOrUpdate(lshsfl);
            ts.commit();
            close(session);
        }catch (Exception e) {
            ts.rollback();
            e.printStackTrace();
        }
    }

    public void addLshszd(Lshszd lshszd){

        Session session = null;
        Transaction ts = null;
        try{
            session=getsession();
            ts = session.beginTransaction();
            session.saveOrUpdate(lshszd);
            ts.commit();
            close(session);
        }catch (Exception e) {
            ts.rollback();
            e.printStackTrace();
        }
    }

    public void addLswlje(Lswlje lswlje){

        Session session = null;
        Transaction ts = null;
        try{
            session=getsession();
            ts = session.beginTransaction();
            session.saveOrUpdate(lswlje);
            ts.commit();
            close(session);
        }catch (Exception e) {
            ts.rollback();
            e.printStackTrace();
        }
    }

    public void addLswlsl(Lswlsl lswlsl){

        Session session = null;
        Transaction ts = null;
        try{
            session=getsession();
            ts = session.beginTransaction();
            session.saveOrUpdate(lswlsl);
            ts.commit();
            close(session);
        }catch (Exception e) {
            ts.rollback();
            e.printStackTrace();
        }
    }

    public void addLswldw(Lswldw lswldw){

        Session session = null;
        Transaction ts = null;
        try{
            session=getsession();
            ts = session.beginTransaction();
            session.saveOrUpdate(lswldw);
            ts.commit();
            close(session);
        }catch (Exception e) {
            ts.rollback();
            e.printStackTrace();
        }
    }


}