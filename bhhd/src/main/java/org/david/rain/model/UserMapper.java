package org.david.rain.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User bean = new User();
		bean.setUserId(rs.getInt("USER_ID"));
		bean.setUserName(rs.getString("USER_NAME"));
		bean.setAccount(rs.getString("ACCOUNT"));
		bean.setPassword(rs.getString("PASSWORD"));
		bean.setAccountType(rs.getInt("ACCOUNT_TYPE"));
		bean.setStatus(rs.getInt("STATUS"));
		bean.setRoleId(rs.getInt("ROLE_ID"));
		bean.setRoleName(rs.getString("ROLE_NAME"));
		bean.setRoleCode(rs.getString("ROLE_CODE"));
		bean.setAgent(rs.getString("AGENT"));

		return bean;
	}

}
