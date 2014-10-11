package org.david.rain.dao.impl;

import org.david.rain.dao.RolePermissionMappingDao;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

@Repository("rolePermissionMappingDao")
public class RolePermissionMappingDaoImpl extends JdbcDaoSupport implements
        RolePermissionMappingDao {

	private static final String TABLE_NAME = "T_SYS_PERMISSION_MAPPING";

	@Resource(name = "dataSource")
	private DataSource dataSource;

	@PostConstruct
	public void injectDataSource() {
		super.setDataSource(dataSource);
	}

	@Override
	public int deleteRolePermissionMappingByRoleId(Integer roleId) {
		final String sql = "delete from " + TABLE_NAME + " where role_id = ? ";

		Object[] args = { roleId };

		int records = getJdbcTemplate().update(sql, args);

		return records;
	}

	@Override
	public int deleteRolePermissionMappingBypermissionId(Integer permissionId) {
		final String sql = "delete from " + TABLE_NAME + " where permission_id = ? ";

		Object[] args = { permissionId };

		int records = getJdbcTemplate().update(sql, args);

		return records;
	}

}
