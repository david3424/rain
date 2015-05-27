/*
*  时间： 2007-8-13 11:57:32
*  北京完美时空网络技术有限公司
*/
package com.wanmei.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * 作者: 李亮阳
 * 日期: 2007-8-13
 * 时间: 11:57:32
 * 版本: 1.0
 * 类说明:完美时空 游戏活动管理平台
 */
public class TempDBManager {
    private static String ClassName = "org.gjt.mm.mysql.Driver"; //数据库驱动
    private static String Url = "jdbc:mysql://172.23.254.6:3306/huodong"; //数据库地址
    private static String UserName = "DB_HD_254_6"; //数据库用户名
    private static String PassWord = "Huo_Dong*{1010]"; //数库库密码

    public static Connection getTempConnection() {

        Connection con = null;
        try {
            Class.forName(ClassName).newInstance();
            con = DriverManager.getConnection(Url, UserName, PassWord);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }


    /**
     * 连接一张表，测试
     */
    public static String getTestList(String hdname) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String returnStr="";
        String sql = "select *  from test";
        try {
            System.out.println("hdname=" + hdname);
            conn = DBManager.getHuodongConnection(hdname.trim());
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs!=null&&rs.next()){
                returnStr+=rs.getString(1)+rs.getString(2)+"<br>";
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            DBManager.close(rs, pstmt, conn);
        }

        return returnStr;
    }


}
