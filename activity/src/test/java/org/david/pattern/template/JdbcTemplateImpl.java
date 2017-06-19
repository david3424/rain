package org.david.pattern.template;

import org.david.rain.act.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xdw9486 on 2017/2/13.
 * 继承模板类，覆盖指定方法
 */
public class JdbcTemplateImpl extends JdbcTemplate {


    @Override
    protected Object doInStatement(ResultSet rs) {
        List<User> userList = new ArrayList<>();

        try {
            User user;
            while (rs.next()) {

                user = new User();
                user.setId(rs.getLong("id"));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 重写钩子
     *
     * @return
     */
    protected boolean aHook() {
        return true;
    }

    protected Connection getConnection() {
        return super.getConnection(); //可以自己实现
    }


    public static void main(String[] args) throws SQLException {
        String sql = "select * from ss_user ";
        JdbcTemplate jdbcTemplate = new JdbcTemplateImpl(); //父类调用模板类
        jdbcTemplate.execute(sql);

        //取消抽象模板类，加入接口，回调时加入具体逻辑，不用每个类都去继承模板类
        JdbcTemplateWithCallback jdbcTemplateWithCallback = new JdbcTemplateWithCallback();
        jdbcTemplateWithCallback.execute(new StatementCallback() {
            @Override
            public Object doInStatement(ResultSet rs) throws SQLException {
                List<User> userList = new ArrayList<>();
                try {
                    User user;
                    while (rs.next()) {
                        user = new User();
                        user.setId(rs.getLong("id"));
                        userList.add(user);
                    }
                    return userList;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }, sql);

    }
}
