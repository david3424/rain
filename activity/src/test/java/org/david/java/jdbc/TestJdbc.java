package org.david.java.jdbc;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * Created by mac on 16/5/26.
 * 测试JDBC连接*
 * 1. 获取资源 2.关闭资源连接
 * * *
 */
public class TestJdbc {


    public static Connection getConnection() {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("/Users/mac/Documents/project/rain/activity/target/classes/jdbc.properties");
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Class.forName((String) properties.get("jdbc.driverClassName"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            return DriverManager.getConnection((String) properties.get("jdbc.url.w"), (String) properties.get("jdbc.username"), (String) properties.get("jdbc.password"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void testResult() {
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from ss_task where id = ? ");
        ) {
            preparedStatement.setInt(1, 1);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = 0;
                id = resultSet.getInt("id");
                String title = null;
                title = resultSet.getString("title");
                System.out.println(MessageFormat.format("id is {0} , title is {1}", id, title));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
    
