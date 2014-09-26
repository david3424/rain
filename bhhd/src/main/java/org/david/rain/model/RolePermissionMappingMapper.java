package org.david.rain.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RolePermissionMappingMapper implements
		RowMapper<RolePermissionMapping> {

	@Override
	public RolePermissionMapping mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		RolePermissionMapping bean = new RolePermissionMapping();
		bean.setMappingId(rs.getInt(1));
		bean.setRoleId(rs.getInt(2));
		bean.setPermissionId(rs.getInt(3));
		bean.setRoleCode(rs.getString(4));
		bean.setResourceUrl(rs.getString(5));

		return bean;
	}

}
