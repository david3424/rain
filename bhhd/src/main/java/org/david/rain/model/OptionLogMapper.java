package org.david.rain.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.david.rain.utils.Utils.converDateTimestampToStr;

public class OptionLogMapper implements RowMapper<OptionLog> {

	@Override
	public OptionLog mapRow(ResultSet rs, int rowNum) throws SQLException {
		OptionLog bean = new OptionLog();
		bean.setLogId(rs.getInt(1));
		bean.setAccount(rs.getString(2));
		bean.setLoginIp(rs.getString(3));
		bean.setOptionTime(rs.getTimestamp(4));
		bean.setOptionTimeStr(converDateTimestampToStr(bean.getOptionTime()));
		bean.setMessage(rs.getString(5));

		return bean;
	}

}
