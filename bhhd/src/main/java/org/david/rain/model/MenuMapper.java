package org.david.rain.model;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.JavaScriptUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuMapper implements RowMapper<Menu> {

	@Override
	public Menu mapRow(ResultSet rs, int rowNum) throws SQLException {
		Menu bean = new Menu();
		bean.setPermissionId(rs.getInt("PERMISSION_ID"));
		bean.setGameId(rs.getInt("GAME_ID"));
		bean.setGameShortName(rs.getString("GAME_AB"));
		bean.setGameName(rs.getString("GAME_NAME"));
		bean.setMenuTypeId(rs.getInt("MENU_TYPE_ID"));
		bean.setMenuTypeName(rs.getString("MENU_TYPE_NAME"));
		bean.setResourceUrl(rs.getString("RESOURCE_URL"));
		bean.setResourceName(rs.getString("RESOURCE_NAME"));
		bean.setDescription(HtmlUtils.htmlEscape(JavaScriptUtils
				.javaScriptEscape(rs.getString("DESCRIPTION"))));

		return bean;
	}

}
