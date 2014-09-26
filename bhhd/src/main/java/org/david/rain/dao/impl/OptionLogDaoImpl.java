package org.david.rain.dao.impl;

import org.david.rain.utils.Constants;
import org.david.rain.dao.OptionLogDao;
import org.david.rain.model.OptionLog;
import org.david.rain.model.OptionLogMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository("userOptionLogDao")
public class OptionLogDaoImpl extends JdbcDaoSupport implements
		OptionLogDao {

	private static final String TABLE_NAME = "t_sys_log";

	@Resource(name = "dataSource")
	private DataSource dataSource;

	@PostConstruct
	public void injectDataSource() {
		super.setDataSource(dataSource);
	}

	@Override
	public int countUserOptionLog(String beginDate, String endDate,
			Integer logType, String account, String message) {
		List<Object> params = new ArrayList<Object>();
		String sql = "select count(1) from " + TABLE_NAME + " where LOG_TYPE = ? ";
		params.add(logType);

		if (StringUtils.isNotBlank(beginDate)) {
			sql += "and OPTION_TIME >= to_date(?, 'yyyy-mm-dd') ";
			params.add(beginDate);
		}

		if (StringUtils.isNotBlank(endDate)) {
			sql += "and OPTION_TIME < (to_date(?, 'yyyy-mm-dd') + 1) ";
			params.add(endDate);
		}

		if (StringUtils.isNotBlank(account)) {
			sql += "and ACCOUNT like ? ";
			account = "%" + account + "%";
			params.add(account);
		}

		if (StringUtils.isNotBlank(message)) {
			sql += "and MESSAGE like ? ";
			message = "%" + message + "%";
			params.add(message);
		}

		Object[] args = new Object[params.size()];
		for (int i = 0; i < params.size(); i++) {
			args[i] = params.get(i);
		}

		int records = getJdbcTemplate()
				.queryForObject(sql, args, Integer.class);

		return records;
	}

	@Override
	public List<OptionLog> getUserOptionLogGrid(String beginDate,
			String endDate, Integer logType, String account, String message,
			int from, int length, String sidx, String sord) {
		List<Object> params = new ArrayList<Object>();
		String sql = "select LOG_ID, ACCOUNT, LOGIN_IP, OPTION_TIME, MESSAGE from "
				+ TABLE_NAME + " where LOG_TYPE = ? ";
		params.add(logType);

		if (StringUtils.isNotBlank(beginDate)) {
			sql += "and OPTION_TIME >= to_date(?, 'yyyy-mm-dd') ";
			params.add(beginDate);
		}

		if (StringUtils.isNotBlank(endDate)) {
			sql += "and OPTION_TIME < (to_date(?, 'yyyy-mm-dd') + 1) ";
			params.add(endDate);
		}

		if (StringUtils.isNotBlank(account)) {
			sql += "and ACCOUNT like ? ";
			account = "%" + account + "%";
			params.add(account);
		}

		if (StringUtils.isNotBlank(message)) {
			sql += "and MESSAGE like ? ";
			message = "%" + message + "%";
			params.add(message);
		}

		if (StringUtils.isBlank(sidx)) {
			sql += "order by OPTION_TIME desc ";
		} else {
			if ("desc".equalsIgnoreCase(sord)) {
				sql += "order by " + sidx + " desc ";
			} else {
				sql += "order by " + sidx + " asc ";
			}
		}

	 /*修改为mysql分页*/
        sql += " limit ?,? ";
        params.add(from);
		params.add(length);

		Object[] args = new Object[params.size()];
		for (int i = 0; i < params.size(); i++) {
			args[i] = params.get(i);
		}

		List<OptionLog> gridDate = getJdbcTemplate().query(sql, args,
				new OptionLogMapper());

		return gridDate;
	}

	@Override
	public int insert(String account, String loginIp, String message) {
		final String sql = "insert into " + TABLE_NAME
				+ " ( ACCOUNT, LOGIN_IP, MESSAGE) "
				+ " values (?, ?, ?) ";

		Object[] args = { account, loginIp, message };

		int record = getJdbcTemplate().update(sql, args);

		return record;
	}

	@Override
	public int insert(String account, String loginIp, String message,
			String exeScript) {
		final String sql = "insert into " + TABLE_NAME
				+ " (LOG_ID, ACCOUNT, LOGIN_IP, MESSAGE, EXE_SCRIPT) "
				+ " values (T_SYS_SEQ.nextval, ?, ?, ?, ?) ";

		Object[] args = { account, loginIp, message, exeScript };

		int record = getJdbcTemplate().update(sql, args);

		return record;
	}

	@Override
	public int insertUserLoginLog(String account, String loginIp, String message) {
		final String sql = "insert into " + TABLE_NAME
				+ " ( ACCOUNT, LOGIN_IP, LOG_TYPE, MESSAGE) "
				+ " values (?, ?, ?, ?) ";

		Object[] args = { account, loginIp, Constants.USER_LOGIN_LOG_TYPE,
				message };

		int record = getJdbcTemplate().update(sql, args);

		return record;
	}

	@Override
	public int insertUserOptionLog(String account, String loginIp,
			String message) {
		final String sql = "insert into " + TABLE_NAME
				+ " ( ACCOUNT, LOGIN_IP, LOG_TYPE, MESSAGE) "
				+ " values ( ?, ?, ?, ?) ";

		Object[] args = { account, loginIp, Constants.USER_OPTION_LOG_TYPE, message };

		int record = getJdbcTemplate().update(sql, args);

		return record;
	}

}
