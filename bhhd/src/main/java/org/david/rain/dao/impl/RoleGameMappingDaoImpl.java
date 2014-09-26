package org.david.rain.dao.impl;

import org.david.rain.dao.RoleGameMappingDao;
import org.david.rain.model.RoleGameDetail;
import org.david.rain.model.RoleGameMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

@Repository("roleGameMappingDao")
public class RoleGameMappingDaoImpl extends JdbcDaoSupport implements
		RoleGameMappingDao {

	private static final String TABLE_NAME = "T_SYS_ROLE_GAME_MAPPING";

	private static final String TABLE_NAME_DICT = "T_GAME_DICT";

	private static final String TABLE_NAME_ROLE = "T_SYS_ROLE";

	private static final String TABLE_NAME_PERMISSION_MAPPING = "T_SYS_PERMISSION_MAPPING";

	private static final String TABLE_NAME_PERMISSION = "T_SYS_PERMISSION";

	@Resource(name = "dataSource")
	private DataSource dataSource;

	@PostConstruct
	public void injectDataSource() {
		super.setDataSource(dataSource);
	}

	@Override
	public List<RoleGameDetail> getRoleGameListByRoleId(Integer roleId) {
		final String sql = "select t1.game_id, t1.game_name, t1.game_ab, t2.role_id from "
				+ TABLE_NAME_DICT
				+ " t1 left join (select game_id, role_id from "
				+ TABLE_NAME
				+ " where role_id = ? ) t2 on t1.game_id = t2.game_id where t1.game_type = 2 order by t1.game_id";

		Object[] args = { roleId };
		List<RoleGameDetail> list = getJdbcTemplate().query(sql, args,
				new RoleGameMapper());

		return list;
	}

	@Override
	public int countGameList() {
		final String sql = "select count(1) from " + TABLE_NAME_DICT
				+ " where game_type = 2 ";

		int records = getJdbcTemplate().queryForObject(sql, Integer.class);
		return records;
	}

	@Override
	public List<RoleGameDetail> getRoleGameListByRoleId(Integer roleId,
			int from, int length, String sidx, String sord) {
		String sql = "select * from ("
				+ "select t1.game_id, t1.game_name, t1.game_ab, ifnull(t2.role_id, -1) as role_id from "
				+ TABLE_NAME_DICT
				+ " t1 left join (select game_id, role_id from "
				+ TABLE_NAME
				+ " where role_id = ? ) t2 on t1.game_id = t2.game_id where t1.game_type = 2 "
				+ " union all "
				+ " select t3.game_id, t3.game_name, t3.game_ab, ifnull(t4.role_id, -1) as role_id from "
				+ " (select 0 as game_id, '平台管理' as game_name, 'system' as game_ab from dual)t3 "
				+ " left join (select game_id, role_id from " + TABLE_NAME
				+ " where role_id = ?) t4 on t3.game_id = t4.game_id) t ";

		if (StringUtils.isBlank(sidx)) {
			sql += " order by GAME_ID asc ";
		} else {
			if ("desc".equalsIgnoreCase(sord)) {
				sql += " order by " + sidx + " desc ";
			} else {
				sql += " order by " + sidx + " asc ";
			}
		}
         /*修改为mysql分页*/
        sql += " limit ?,? ";

		Object[] args = { roleId, roleId, from*length, length };
		List<RoleGameDetail> list = getJdbcTemplate().query(sql, args,
				new RoleGameMapper());

		return list;
	}

	@Override
	public List<String> getGameListByRoleCode(String roleCode) {
		final String sql = "select distinct t2.game_ab from "
				+ TABLE_NAME
				+ " t1, "
				+ TABLE_NAME_DICT
				+ " t2, "
				+ TABLE_NAME_ROLE
				+ " t3 where t3.role_code = ? and t1.role_id = t3.role_id and t1.game_id = t2.game_id ";

		Object[] args = { roleCode };
		List<String> list = getJdbcTemplate().queryForList(sql, args,
				String.class);

		return list;
	}

	@Override
	public int countByRoleIdAndGameid(Integer roleId, Integer gameId) {
		final String sql = "select count(1) from " + TABLE_NAME
				+ " where role_id = ? and game_id = ? ";

		Object[] args = { roleId, gameId };
		int records = getJdbcTemplate()
				.queryForObject(sql, args, Integer.class);
		return records;
	}

	@Override
	public int insertRoleGameMapping(Integer roleId, Integer gameId) {
		final String sql = "insert into "
				+ TABLE_NAME
				+ " ( ROLE_ID, GAME_ID) values ( ?, ?) ";
		Object[] args = { roleId, gameId };

		int records = getJdbcTemplate().update(sql, args);

		return records;
	}

	@Override
	public int deleteRoleGameMapping(Integer roleId, Integer gameId) {
		final String sql = "delete from " + TABLE_NAME
				+ " where ROLE_ID = ? and GAME_ID = ? ";

		Object[] args = { roleId, gameId };
		int records = getJdbcTemplate().update(sql, args);

		return records;
	}

	@Override
	public int deleteRolePermissionMappingByRoleIdAndGameId(Integer roleId,
			Integer gameId) {
		final String sql = "delete from " + TABLE_NAME_PERMISSION_MAPPING
				+ " where ROLE_ID = ? and PERMISSION_ID IN ( "
				+ "select PERMISSION_ID FROM " + TABLE_NAME_PERMISSION
				+ " where game_id = ?)";

		Object[] args = { roleId, gameId };
		int records = getJdbcTemplate().update(sql, args);

		return records;
	}

}
