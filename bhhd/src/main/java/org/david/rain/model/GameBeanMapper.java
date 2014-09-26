package org.david.rain.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameBeanMapper implements RowMapper<GameBean> {

	@Override
	public GameBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		GameBean bean = new GameBean();
		bean.setGameId(rs.getInt(1));
		bean.setGameName(rs.getString(2));
		bean.setShortName(rs.getString(3));
		bean.setClient(rs.getInt(4));

		return bean;
	}

}
