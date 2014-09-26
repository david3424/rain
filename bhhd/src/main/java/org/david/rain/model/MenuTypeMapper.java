package org.david.rain.model;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.JavaScriptUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuTypeMapper implements RowMapper<MenuType> {

	@Override
	public MenuType mapRow(ResultSet rs, int rowNum) throws SQLException {
		MenuType bean = new MenuType();
		bean.setMenuTypeId(rs.getInt("MENU_TYPE_ID"));
		bean.setMenuTypeName(rs.getString("MENU_TYPE_NAME"));
		bean.setDescription(HtmlUtils.htmlEscape(JavaScriptUtils
				.javaScriptEscape(rs.getString("DESCRIPTION"))));

		return bean;
	}

}
