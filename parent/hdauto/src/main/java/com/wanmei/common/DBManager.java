package com.david.web.pppppp.common;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by IntelliJ IDEA.
 * 作者: davidxu
 * 日期: 2012-10-24
 * 时间: 11:30:16
 * 版本: 1.0
 * 数据源相关操作方法
 */


public class DBManager {

    //新活动数据库
    private static DataSource huodongdataSource161 = null;//读写数据源
    private static DataSource huodongdataSource164 = null;//普通据量数据库
    private static DataSource huodongdataSource218 = null;
    private static DataSource getHuodongdataSource108 = null;
    private static DataSource coupon = null;//点券、积分数据库

   /* static {
        try {
            System.out.println("------datasource init successful------------");
        } catch (Exception e) {
            System.out.println("------datasource init fails------------");
            e.printStackTrace();
        }
    }*/

    /**
     * 私有的构造函数
     */
    private DBManager() {
    }

    /**
     * 初始化数据源
     * huodong数据库，164
     *
     * @return
     * @throws javax.naming.NamingException
     */
    private static DataSource initDbHuodong164() throws NamingException {
        if (huodongdataSource164 == null) {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource source = (DataSource) envCtx.lookup("jdbc/huodong164");
            return source;
        } else {
            return huodongdataSource164;
        }
    }


    private static DataSource initDbHuodong218() throws NamingException {
        if (huodongdataSource164 == null) {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource source = (DataSource) envCtx.lookup("jdbc/huodong218");
            return source;
        } else {
            return huodongdataSource164;
        }
    }

    private static DataSource initDbHuodong108() throws NamingException {
        if (huodongdataSource164 == null) {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource source = (DataSource) envCtx.lookup("jdbc/huodong108");
            return source;
        } else {
            return huodongdataSource164;
        }
    }

    /**
     * 初始化数据源
     *
     * @return
     * @throws javax.naming.NamingException
     */
    private static DataSource initDbCoupon() throws NamingException {
        if (coupon == null) {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource source = (DataSource) envCtx.lookup("jdbc/coupon");
            return source;
        } else {
            return coupon;
        }
    }

    /**
     * 初始化数据源
     * huodong数据库，161
     *
     * @return
     * @throws javax.naming.NamingException
     */
    private static DataSource initDbHuodong161() throws NamingException {
        if (huodongdataSource161 == null) {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource source = (DataSource) envCtx.lookup("jdbc/huodong161");
            return source;
        } else {
            return huodongdataSource161;
        }
    }


    /**
     * 得到默认数据库链接，默认为写数据库 161
     *
     * @return connection
     * @throws Exception
     */
    public static Connection getHuodongConnection() throws Exception {
        Connection conn = null;

        // 大流量数据库161
        if (huodongdataSource161 != null) {
            conn = huodongdataSource161.getConnection();
        } else {
            huodongdataSource161 = initDbHuodong161();
            conn = huodongdataSource161.getConnection();
        }
        return conn;
    }

    /*  *//**
     * 利用语句 读写分离
     *
     * @return connection
     * @throws Exception
     *//*
    public static Connection getHuodongConnectionBySQL(String sql) throws Exception {
        Connection conn = null;
        if (sql != null && !CheckFormUtils.hasInvalidChar(sql, new String[]{"insert", "update", "delete"})) {
            // 写操作
            if (huodongdataSource161 != null) {
                conn = huodongdataSource161.getConnection();
            } else {
                huodongdataSource161 = initDbHuodong161();
                conn = huodongdataSource161.getConnection();
            }
            return conn;
        } else {
            // 读操作
            if (huodongdataSource164 != null) {
                conn = huodongdataSource164.getConnection();
            } else {
                huodongdataSource164 = initDbHuodong164();
                conn = huodongdataSource164.getConnection();
            }
            return conn;
        }

    }*/

    /**
     * 得到数据库链接，注意：用完后务必关闭连接
     *
     * @return connection
     * @throws Exception
     */
    public static Connection getHuodongConnection(String hddbname) throws Exception {
        Connection conn = null;
        if (hddbname.equals("huodong164")) {
            // 普通流量数据库164
            if (huodongdataSource164 != null) {
                conn = huodongdataSource164.getConnection();
            } else {
                huodongdataSource164 = initDbHuodong164();
                conn = huodongdataSource164.getConnection();
            }
        } else if (hddbname.equals("huodong161")) {
            // 大数据量数据库161
            if (huodongdataSource161 != null) {
                conn = huodongdataSource161.getConnection();
            } else {
                huodongdataSource161 = initDbHuodong161();
                conn = huodongdataSource161.getConnection();
            }
        } else if (hddbname.equals("coupon")) {
            //odi
            if (coupon != null) {
                conn = coupon.getConnection();
            } else {
                coupon = initDbCoupon();
                conn = coupon.getConnection();
            }

        } else if (hddbname.equals("huodong218")) {
            // 大数据量数据库218
            if (huodongdataSource218 != null) {
                conn = huodongdataSource218.getConnection();
            } else {
                huodongdataSource218 = initDbHuodong218();
                conn = huodongdataSource218.getConnection();
            }
        } else if (hddbname.equals("huodong108")) {
            // 大数据量数据库218
            if (huodongdataSource218 != null) {
                conn = huodongdataSource218.getConnection();
            } else {
                huodongdataSource218 = initDbHuodong108();
                conn = huodongdataSource218.getConnection();
            }
        }

        return conn;
    }

    /**
     * 关闭连接
     *
     * @param connection
     * @throws java.sql.SQLException
     */
    public static void closeConn(Connection connection) throws SQLException {
        if (connection != null) {
            if (!connection.isClosed())
                connection.close();
        }

    }

    /**
     * 关闭  statement
     *
     * @param statement
     * @throws java.sql.SQLException
     */
    public static void closeStat(Statement statement) throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }


    /**
     * 关闭  resultSet
     *
     * @param resultSet
     */
    public static void closeResult(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

    }

    /**
     * 关闭 result，stat ，conn
     *
     * @param result
     * @param stat
     * @param conn
     */
    public static void close(ResultSet result, Statement stat, Connection conn) {
        try {
            closeResult(result);
            closeStat(stat);
            closeConn(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
