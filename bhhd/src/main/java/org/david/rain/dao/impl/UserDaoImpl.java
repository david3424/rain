package org.david.rain.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.david.rain.dao.UserDao;
import org.david.rain.model.User;
import org.david.rain.model.UserMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository("accountDao")
public class UserDaoImpl extends JdbcDaoSupport implements UserDao {

	private static final String TABLE_NAME = "t_sys_user";

	private static final String TABLE_NAME_ROLE = "t_sys_role";

	@Resource(name = "dataSource")
	private DataSource dataSource;

	@PostConstruct
	public void injectDataSource() {
		super.setDataSource(dataSource);
	}

	@Override
	public List<User> getSecurityUserByAccount(String account) {
		final String sql = "select t1.user_id, t1.user_name, t1.account, t1.password, "
				+ "t1.account_type, t1.status, t1.role_id, null as role_name, null as role_code, t1.agent from "
				+ TABLE_NAME + " t1 where t1.account = ? ";

		Object[] args = { account };

		List<User> list = getJdbcTemplate().query(sql, args, new UserMapper());

		return list;
	}

	@Override
	public List<String> getSecurityRolesByAccount(String account) {
		final String sql = "select distinct(t2.role_code) from " + TABLE_NAME
				+ " t1, " + TABLE_NAME_ROLE + " t2 "
				+ "where t1.account = ? and t1.role_id = t2.role_id";
		Object[] args = { account };

		List<String> list = getJdbcTemplate().queryForList(sql, args,
				String.class);

		return list;
	}

	@Override
	public int countUserByConditions(Integer roleId, Integer userStatus,
			String userName, String account) {
		List<Object> params = new ArrayList<Object>();
		String sql = "select count(1) from " + TABLE_NAME + " t1, "
				+ TABLE_NAME_ROLE + " t2 where t1.role_id = t2.role_id ";

		if (roleId != null && roleId.intValue() > 0) {
			sql += "and t1.role_id = ? ";
			params.add(roleId);
		}

		if (userStatus != null && userStatus.intValue() > 0) {
			sql += "and t1.status = ? ";
			params.add(userStatus);
		}

		if (!StringUtils.isBlank(userName)) {
			sql += "and t1.user_name like ? ";
			userName = "%" + userName + "%";
			params.add(userName);
		}

		if (!StringUtils.isBlank(account)) {
			sql += "and t1.account like ? ";
			account = "%" + account + "%";
			params.add(account);
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
	public List<User> getUserGrid(Integer roleId, Integer userStatus,
			String userName, String account, int from, int length, String sidx,
			String sord) {
		List<Object> params = new ArrayList<Object>();
		String sql = "select t1.user_id, t1.user_name, t1.account, null as password, t1.account_type, t1.status, t1.role_id, t2.role_name, t2.role_code, t1.agent from "
				+ TABLE_NAME
				+ " t1, "
				+ TABLE_NAME_ROLE
				+ " t2 where t1.role_id = t2.role_id ";

		if (roleId != null && roleId.intValue() > 0) {
			sql += "and t1.role_id = ? ";
			params.add(roleId);
		}

		if (userStatus != null && userStatus.intValue() > 0) {
			sql += "and t1.status = ? ";
			params.add(userStatus);
		}

		if (!StringUtils.isBlank(userName)) {
			sql += "and t1.user_name like ? ";
			userName = "%" + userName + "%";
			params.add(userName);
		}

		if (!StringUtils.isBlank(account)) {
			sql += "and t1.account like ? ";
			account = "%" + account + "%";
			params.add(account);
		}

		if (StringUtils.isBlank(sidx)) {
			sql += "order by user_id desc ";
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

		List<User> gridData = getJdbcTemplate().query(sql, args,
				new UserMapper());

		return gridData;
	}

	@Override
	public int countByAccount(String account) {
		final String sql = "select count(1) from " + TABLE_NAME
				+ " t1 where t1.account = ? ";

		Object[] args = { account };

		int records = getJdbcTemplate()
				.queryForObject(sql, args, Integer.class);

		return records;
	}

	@Override
	public int insert(String userName, String account, String userPwd,
			Integer roleId, Integer userStatus, String agent) {
		final String sql = "insert into "
				+ TABLE_NAME
				+ " ( user_name, account, password, status, role_id, agent) "
				+ "values ( ?, ?,?, ?, ?, ?) ";

		Object[] args = { userName, account, userPwd, userStatus, roleId, agent };

		int records = getJdbcTemplate().update(sql, args);

		return records;
	}

	@Override
	public int countByAccountWithoutId(Integer userId, String account) {
		final String sql = "select count(1) from " + TABLE_NAME
				+ " t1 where t1.account = ? and t1.user_id <> ? ";

		Object[] args = { account, userId };

		int records = getJdbcTemplate()
				.queryForObject(sql, args, Integer.class);

		return records;
	}

	@Override
	public int updateWithoutPwd(Integer userId, String userName,
			String account, Integer roleId, Integer userStatus, String agent) {
		final String sql = "update "
				+ TABLE_NAME
				+ " set user_name = ?, account = ?, status = ?, role_id = ?, agent = ? where user_id = ? ";
		Object[] args = { userName, account, userStatus, roleId, agent, userId };

		int records = getJdbcTemplate().update(sql, args);

		return records;
	}

	@Override
	public int update(Integer userId, String userName, String account,
			String userPwd, Integer roleId, Integer userStatus, String agent) {
		final String sql = "update "
				+ TABLE_NAME
				+ " set user_name = ?, account = ?, password = ?, status = ?, role_id = ?, agent = ? where user_id = ? ";
		Object[] args = { userName, account, userPwd, userStatus, roleId,
				agent, userId };

		int records = getJdbcTemplate().update(sql, args);

		return records;
	}

	@Override
	public int delete(Integer userId) {
		final String sql = "delete from " + TABLE_NAME + " where user_id = ? ";

		Object[] args = { userId };

		return getJdbcTemplate().update(sql, args);
	}

	@Override
	public List<User> getUserById(Integer userId) {
		String sql = "select t1.user_id, t1.user_name, t1.account, null as password, t1.account_type, t1.status, t1.role_id, t2.role_name, t2.role_code, t1.agent from "
				+ TABLE_NAME
				+ " t1, "
				+ TABLE_NAME_ROLE
				+ " t2 where t1.role_id = t2.role_id and user_id = ?";

		Object[] args = { userId };

		List<User> userList = getJdbcTemplate().query(sql, args,
				new UserMapper());

		return userList;
	}

	@Override
	public int countByPassword(String account, String password) {
		final String sql = "select count(1) from " + TABLE_NAME
				+ " t1 where t1.account = ? and t1.password = ? ";

		Object[] args = { account, password };
		int records = getJdbcTemplate()
				.queryForObject(sql, args, Integer.class);

		return records;
	}

	@Override
	public int updatePassword(String account, String password) {
		final String sql = "update " + TABLE_NAME
				+ " set password = ? where account = ? ";
		Object[] args = { password, account };

		int records = getJdbcTemplate().update(sql, args);

		return records;
	}

}
