package org.david.pattern.template;

import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by xdw9486 on 2017/2/13.
 * 操作JDBC模板类 每次执行都需要继承该类
 */
public abstract class JdbcTemplate {

    DataSource dataSource;

    //template method
    public final Object execute(String sql) throws SQLException {

        Connection con = getConnection(); //从连接池中拿连接 默认有实现，可覆盖 1
        ResultSet rs = getRsByStatement(con, sql);//根据sql读取数据集 2
        if (aHook()) {
            System.out.println("钩子内容，看到说明子类已覆盖");
        }
        return doInStatement(rs);// 处理数据集 抽象方法，留给之类处理 3
    }

    protected Connection getConnection() {

        return DataSourceUtils.getConnection(dataSource);

    }

    private ResultSet getRsByStatement(Connection con, String sql) throws SQLException {
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            try {
                assert stmt != null;
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (!con.isClosed()) {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //implements in subclass
    protected abstract Object doInStatement(ResultSet rs);

    protected boolean aHook() {
        return false;
    }
}
