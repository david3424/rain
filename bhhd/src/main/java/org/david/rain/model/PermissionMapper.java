package org.david.rain.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PermissionMapper implements RowMapper<Permission> {

	@Override
	public Permission mapRow(ResultSet rs, int rowNum) throws SQLException {
		Permission bean = new Permission();
		bean.setPermissionId(rs.getInt(1));
		bean.setResourceUrl(rs.getString(2));
		bean.setResourceName(rs.getString(3));
		bean.setGameId(rs.getInt(4));
		bean.setMenuTypeId(rs.getInt(5));

		return bean;
	}

}
