package org.david.rain.dubbox.provider.entity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by mac on 14-11-30.
 */
public class UserTaskMapper implements RowMapper<UserTask> {

    @Override
    public UserTask mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserTask userTask = new UserTask();
        userTask.setLogin_name(rs.getString("login_name"));
        userTask.setTitle(rs.getString("title"));
        userTask.setUser_id(rs.getLong("user_id"));
        return userTask;
    }
}
