package com.bjut.ssh.SQLServerBak;

import java.sql.*;

/**
 * @Title: SQLServerBak
 * @Description: sqlserver数据库备份专用
 * @Author: LYH
 * @CreateDate: 2018/7/23 8:59
 * @Version: 1.0
 */

public class SQLServerBak {
    /** 访问数据库的用户名 */
    private String username;
    /** 访问数据库的密码 */
    private String password;
    /** 访问数据库的驱动名 */
    private String driverName;
    /** 访问数据库的url */
    private String URLName;

    public SQLServerBak() {
    }

    public SQLServerBak(String username, String password, String driverName, String URLName) {
        this.username = username;
        this.password = password;
        this.driverName = driverName;
        this.URLName = URLName;
    }

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

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getURLName() {
        return URLName;
    }

    public void setURLName(String URLName) {
        this.URLName = URLName;
    }

    //连接数据库
    private Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driverName);
            if (connection == null) ;
            connection = java.sql.DriverManager.getConnection(URLName,
                    username, password);
        } catch (Exception er) {
            er.printStackTrace();
        }
        return connection;
    }


    //断开数据库连接
    protected static void closeConnection(Connection connection) {
        if (null != connection) {
            try {
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    //备份数据库
    //TODO:数据库名可配置
    public void doBackUp(String path){

        //String diskFilePath = C:\\Program Files (x86)\\Microsoft SQL Server\\MSSQL10_50.MSSQLSERVER\\MSSQL\\Backup\\bak_test”;
        String bakSql = "BACKUP DATABASE ssh_finance TO DISK='" + path + "\\ssh_finance.bak"+ "'";
        PreparedStatement ps = null;
        try {
            ps = getConnection().prepareStatement(bakSql);
            ps.execute();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    //恢复数据库
    public void dorestore(String path) {
        String restoreSql = "ALTER DATABASE ssh_finance SET ONLINE WITH ROLLBACK IMMEDIATE";
        PreparedStatement ps = null;
        Connection conn = getConnection();
        try {
            ps = conn.prepareStatement("USE master");//注意使用master下的存储过程。
            ps.execute();
            ps = conn.prepareStatement(restoreSql);
            CallableStatement cs = conn.prepareCall("{call killrestore(?,?)}");
            cs.setString(1, "ssh_finance");
            //此处路径是上面备份数据库文件的路径
            cs.setString(2, path+"\\ssh_finance.bak");
            cs.execute();
            ps.execute();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}