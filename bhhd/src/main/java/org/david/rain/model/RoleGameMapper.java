package org.david.rain.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleGameMapper implements RowMapper<RoleGameDetail> {

	@Override
	public RoleGameDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
		RoleGameDetail bean = new RoleGameDetail();
		bean.setGameId(rs.getInt("GAME_ID"));
		bean.setGameName(rs.getString("GAME_NAME"));
		bean.setGameAb(rs.getString("GAME_AB"));
		bean.setRoleId(rs.getInt("ROLE_ID"));

		return bean;
	}

}
