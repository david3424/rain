package org.david.rain.model;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.JavaScriptUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleMapper implements RowMapper<Role> {

	@Override
	public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
		Role bean = new Role();
		bean.setRoleId(rs.getInt(1));
		bean.setRoleName(rs.getString(2));
		bean.setRoleCode(rs.getString(3));
		bean.setRoleDescribe(HtmlUtils.htmlEscape(JavaScriptUtils
				.javaScriptEscape(rs.getString(4))));

		return bean;
	}

}
