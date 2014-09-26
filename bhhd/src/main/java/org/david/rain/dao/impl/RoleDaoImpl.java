package org.david.rain.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.david.rain.dao.RoleDao;
import org.david.rain.model.*;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository("roleDetailDao")
public class RoleDaoImpl extends JdbcDaoSupport implements RoleDao {

	private static final String TABLE_NAME = "T_SYS_ROLE";
	private static final String TABLE_NAME_MAPPING = "T_SYS_PERMISSION_MAPPING";
	private static final String TABLE_NAME_PERMISSION = "T_SYS_PERMISSION";
	private static final String TABLE_NAME_USER = "T_SYS_USER";
	private static final String TABLE_NAME_MENU_TYPE = "T_SYS_MENU_TYPE";
	private static final String TABLE_NAME_GAME = "T_GAME_DICT";

	@Resource(name = "dataSource")
	private DataSource dataSource;
    //bean初始化时自动执行该方法
	@PostConstruct
	public void injectDataSource() {
		super.setDataSource(dataSource);
	}

	@Override
	public List<Role> getRoleDetailList() {
		final String sql = "select ROLE_ID, ROLE_NAME, ROLE_CODE, DESCRIPTION from "
				+ TABLE_NAME
				+ " order by ROLE_NAME ";

		List<Role> roleDetailList = getJdbcTemplate().query(sql,
				new RoleMapper());

		return roleDetailList;
	}

	@Override
	public int countUserRoleByName(String roleName) {
		String sql = "select count(1) from " + TABLE_NAME
				+ " where ROLE_NAME = ? ";

		Object[] args = { roleName };

		int records = getJdbcTemplate()
				.queryForObject(sql, args, Integer.class);

		return records;
	}

	@Override
	public int countUserRoleByCode(String roleCode) {
		String sql = "select count(1) from " + TABLE_NAME
				+ " where ROLE_CODE = ? ";

		Object[] args = { roleCode };

		int records = getJdbcTemplate()
				.queryForObject(sql, args, Integer.class);

		return records;
	}

	@Override
	public int insertUserRole(String roleName, String roleCode,
			String roleDescribe) {
		final String sql = "insert into "
				+ TABLE_NAME
				+ " ( ROLE_NAME, ROLE_CODE, DESCRIPTION) values (?, ?, ?) ";

		Object[] args = { roleName, roleCode, roleDescribe };

		int record = getJdbcTemplate().update(sql, args);

		return record;
	}

	@Override
	public int countUserRoleByNameWithoutId(String roleName, Integer roleId) {
		String sql = "select count(1) from " + TABLE_NAME
				+ " where ROLE_NAME = ? and ROLE_ID <> ? ";

		Object[] args = { roleName, roleId };

		int records = getJdbcTemplate()
				.queryForObject(sql, args, Integer.class);

		return records;
	}

	@Override
	public int countUserRoleByCodeWithoutId(String roleCode, Integer roleId) {
		String sql = "select count(1) from " + TABLE_NAME
				+ " where ROLE_CODE = ? and ROLE_ID <> ? ";

		Object[] args = { roleCode, roleId };

		int records = getJdbcTemplate()
				.queryForObject(sql, args, Integer.class);

		return records;
	}

	@Override
	public int updateUserRole(Integer roleId, String roleName, String roleCode,
			String roleDescribe) {
		final String sql = "update "
				+ TABLE_NAME
				+ " set ROLE_NAME = ?, ROLE_CODE = ?, DESCRIPTION = ? where ROLE_ID = ? ";

		Object[] args = { roleName, roleCode, roleDescribe, roleId };

		int records = getJdbcTemplate().update(sql, args);

		return records;
	}

	@Override
	public int deleteUserRole(Integer roleId) {
		final String sql = "delete from " + TABLE_NAME + " where ROLE_ID = ? ";

		Object[] args = { roleId };

		int records = getJdbcTemplate().update(sql, args);

		return records;
	}

	@Override
	public List<Role> getRoleDetailById(Integer roleId) {
		final String sql = "select ROLE_ID,ROLE_NAME, ROLE_CODE, DESCRIPTION from "
				+ TABLE_NAME + " where ROLE_ID = ? ";

		Object[] args = { roleId };

		List<Role> roleDetailList = getJdbcTemplate().query(sql, args,
				new RoleMapper());

		return roleDetailList;
	}

	@Override
	public List<Permission> getBindPermissionsByRoleId(Integer roleId) {
		final String sql = "select * from (select t3.permission_id, t3.resource_url, concat(t5.game_name , '-', t4.menu_type_name , '-' , t3.resource_name ) as resource_name, t3.game_id, t3.menu_type_id from "
				+ TABLE_NAME
				+ " t1, "
				+ TABLE_NAME_MAPPING
				+ " t2, "
				+ TABLE_NAME_PERMISSION
				+ " t3, "
				+ TABLE_NAME_MENU_TYPE
				+ " t4, "
				+ TABLE_NAME_GAME
				+ " t5 "
				+ "where t1.role_id = t2.role_id and t2.permission_id = t3.permission_id and t3.menu_type_id = t4.menu_type_id and t3.game_id = t5.game_id and t1.role_id = ? "
				+ "union all select t3.permission_id, t3.resource_url, concat('系统-' , t4.menu_type_name , '-' , t3.resource_name) as resource_name, t3.game_id, t3.menu_type_id from "
				+ TABLE_NAME
				+ " t1, "
				+ TABLE_NAME_MAPPING
				+ " t2, "
				+ TABLE_NAME_PERMISSION
				+ " t3, "
				+ TABLE_NAME_MENU_TYPE
				+ " t4 "
				+ "where t1.role_id = t2.role_id and t2.permission_id = t3.permission_id and t3.menu_type_id = t4.menu_type_id and t3.game_id = 0 and t1.role_id = ?) order by game_id, menu_type_id, permission_id ";

		Object[] args = { roleId, roleId };

		List<Permission> permissions = getJdbcTemplate().query(sql, args,
				new PermissionMapper());

		return permissions;
	}

	@Override
	public List<Permission> getUnbindPermissionsByRoleId(Integer roleId) {
        //替换之 concat(t5.game_name , '-', t4.menu_type_name , '-' , t3.resource_name )
		final String sql = "select * from (select t3.permission_id, t3.resource_url, concat(t5.game_name , '-', t4.menu_type_name , '-' , t3.resource_name ) as resource_name, t3.game_id, t3.menu_type_id from "
				+ TABLE_NAME_PERMISSION
				+ " t3, "
				+ TABLE_NAME_MENU_TYPE
				+ " t4, "
				+ TABLE_NAME_GAME
				+ " t5 "
				+ "where t3.permission_id not in (select t2.permission_id from "
				+ TABLE_NAME
				+ " t1, "
				+ TABLE_NAME_MAPPING
				+ " t2 where t1.role_id = t2.role_id and t1.role_id = ?) "
				+ "and t3.menu_type_id = t4.menu_type_id and t3.game_id = t5.game_id "
				+ "union all select t3.permission_id, t3.resource_url, concat('系统-' , t4.menu_type_name , '-' , t3.resource_name) as resource_name, t3.game_id, t3.menu_type_id from "
				+ TABLE_NAME_PERMISSION
				+ " t3, "
				+ TABLE_NAME_MENU_TYPE
				+ " t4 "
				+ "where t3.permission_id not in (select t2.permission_id from "
				+ TABLE_NAME
				+ " t1, "
				+ TABLE_NAME_MAPPING
				+ " t2 where t1.role_id = t2.role_id and t1.role_id = ?) "
				+ "and t3.menu_type_id = t4.menu_type_id and t3.game_id = 0) order by game_id, menu_type_id, permission_id ";

		Object[] args = { roleId, roleId };

		List<Permission> permissions = getJdbcTemplate().query(sql, args,
				new PermissionMapper());

		return permissions;
	}

	@Override
	public int insertRolePromissionMappingById(Integer roleId, Integer permissionId) {
		final String sql = "insert into "
				+ TABLE_NAME_MAPPING
				+ " ( ROLE_ID, PERMISSION_ID) values (?, ?) ";
		Object[] args = { roleId, permissionId };

		int records = getJdbcTemplate().update(sql, args);

		return records;
	}

	@Override
	public int deleteRolePromissionMappingById(Integer roleId,
			Integer permissionId) {
		final String sql = "delete from " + TABLE_NAME_MAPPING
				+ " where ROLE_ID = ? and PERMISSION_ID = ? ";
		Object[] args = { roleId, permissionId };

		int records = getJdbcTemplate().update(sql, args);

		return records;
	}

	@Override
	public List<RolePermissionMapping> getRolePermissionMapping() {
		final String sql = "select t2.mapping_id, t1.role_id, t3.permission_id, t1.role_code, t3.resource_url from "
				+ TABLE_NAME
				+ " t1, "
				+ TABLE_NAME_MAPPING
				+ " t2, "
				+ TABLE_NAME_PERMISSION
				+ " t3 where t1.role_id = t2.role_id and t2.permission_id = t3.permission_id ";

        return getJdbcTemplate()
                .query(sql, new RolePermissionMappingMapper());
	}

	@Override
	public int countUserByRoleId(Integer roleId) {
		String sql = "select count(1) from " + TABLE_NAME_USER
				+ " where role_id = ? ";

		Object[] args = { roleId };

		int records = getJdbcTemplate()
				.queryForObject(sql, args, Integer.class);

		return records;
	}

	@Override
	public int countRole() {
		String sql = "select count(1) from " + TABLE_NAME + " t1 ";

		int records = getJdbcTemplate().queryForObject(sql, Integer.class);

		return records;
	}

	@Override
	public List<Role> getRoleGrid(int from, int length, String sidx, String sord) {
		List<Object> params = new ArrayList<Object>();
		String sql = "select ROLE_ID, ROLE_NAME, ROLE_CODE, DESCRIPTION from "
				+ TABLE_NAME + " t1 ";

		if (StringUtils.isBlank(sidx)) {
			sql += "order by ROLE_ID desc ";
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

		List<Role> gridData = getJdbcTemplate().query(sql, args,
				new RoleMapper());

		return gridData;
	}

	@Override
	public List<Menu> getMenuListByRoleCode(String roleCode) {
		final String sql = "select * from (select t3.permission_id, t4.menu_order, t3.game_id, t5.game_ab, t5.game_name, t3.menu_type_id, t4.menu_type_name, t3.resource_url, t3.resource_name, t3.resource_order, t3.description from "
				+ TABLE_NAME_MAPPING
				+ " t1, "
				+ TABLE_NAME
				+ " t2, "
				+ TABLE_NAME_PERMISSION
				+ " t3, "
				+ TABLE_NAME_MENU_TYPE
				+ " t4, "
				+ TABLE_NAME_GAME
				+ " t5 "
				+ "where t2.role_code = ? and t1.role_id = t2.role_id and t1.permission_id = t3.permission_id and t3.menu_type_id = t4.menu_type_id and t3.game_id = t5.game_id "
				+ "union all select t3.permission_id, t4.menu_order, t3.game_id, 'system' as game_ab, '系统' as game_name, t3.menu_type_id, t4.menu_type_name, t3.resource_url, t3.resource_name, t3.resource_order, t3.description from "
				+ TABLE_NAME_MAPPING
				+ " t1, "
				+ TABLE_NAME
				+ " t2, "
				+ TABLE_NAME_PERMISSION
				+ " t3, "
				+ TABLE_NAME_MENU_TYPE
				+ " t4 "
				+ "where t2.role_code = ? and t1.role_id = t2.role_id and t1.permission_id = t3.permission_id and t3.menu_type_id = t4.menu_type_id and t3.game_id = 0) t order by menu_order, resource_order, permission_id ";

		Object[] args = { roleCode, roleCode };
		List<Menu> menuList = getJdbcTemplate().query(sql, args,
				new MenuMapper());

		return menuList;
	}

	@Override
	public List<MenuType> getMenuTypeList() {
		final String sql = "select t1.menu_type_id, t1.menu_type_name, t1.description from "
				+ TABLE_NAME_MENU_TYPE + " t1 order by t1.menu_order ";

		List<MenuType> list = getJdbcTemplate()
				.query(sql, new MenuTypeMapper());

		return list;
	}

	@Override
	public int countMenuByConditions(Integer gameId, Integer menuTypeId,
			String resourceName, String resourceUrl) {
		List<Object> params = new ArrayList<Object>();
		String sql = null;
		if (gameId == null) {
			sql = "select count(1) from (select t1.permission_id from "
					+ TABLE_NAME_PERMISSION
					+ " t1, "
					+ TABLE_NAME_MENU_TYPE
					+ " t2, "
					+ TABLE_NAME_GAME
					+ " t3 "
					+ "where t1.menu_type_id = t2.menu_type_id and t1.game_id = t3.game_id ";

			if (menuTypeId != null && menuTypeId.intValue() > 0) {
				sql += "and t1.menu_type_id = ? ";
				params.add(menuTypeId);
			}
			if (!StringUtils.isBlank(resourceName)) {
				sql += "and t1.resource_name like ? ";
				resourceName = "%" + resourceName + "%";
				params.add(resourceName);
			}
			if (!StringUtils.isBlank(resourceUrl)) {
				sql += "and t1.resource_url like ? ";
				resourceUrl = "%" + resourceUrl + "%";
				params.add(resourceUrl);
			}
			sql += " union all select t1.permission_id from "
					+ TABLE_NAME_PERMISSION
					+ " t1, "
					+ TABLE_NAME_MENU_TYPE
					+ " t2 "
					+ "where t1.menu_type_id = t2.menu_type_id and t1.game_id = 0 ";
			if (menuTypeId != null && menuTypeId.intValue() > 0) {
				sql += "and t1.menu_type_id = ? ";
				params.add(menuTypeId);
			}
			if (!StringUtils.isBlank(resourceName)) {
				sql += "and t1.resource_name like ? ";
				resourceName = "%" + resourceName + "%";
				params.add(resourceName);
			}
			if (!StringUtils.isBlank(resourceUrl)) {
				sql += "and t1.resource_url like ? ";
				resourceUrl = "%" + resourceUrl + "%";
				params.add(resourceUrl);
			}
			sql += ") x ";
		} else if (gameId.intValue() != 0) {
			sql = "select count(1) from "
					+ TABLE_NAME_PERMISSION
					+ " t1, "
					+ TABLE_NAME_MENU_TYPE
					+ " t2, "
					+ TABLE_NAME_GAME
					+ " t3 "
					+ "where t1.menu_type_id = t2.menu_type_id and t1.game_id = t3.game_id ";

			if (gameId != null && gameId.intValue() > 0) {
				sql += "and t1.game_id = ? ";
				params.add(gameId);
			}
			if (menuTypeId != null && menuTypeId.intValue() > 0) {
				sql += "and t1.menu_type_id = ? ";
				params.add(menuTypeId);
			}
			if (!StringUtils.isBlank(resourceName)) {
				sql += "and t1.resource_name like ? ";
				resourceName = "%" + resourceName + "%";
				params.add(resourceName);
			}
			if (!StringUtils.isBlank(resourceUrl)) {
				sql += "and t1.resource_url like ? ";
				resourceUrl = "%" + resourceUrl + "%";
				params.add(resourceUrl);
			}
		} else if (gameId.intValue() == 0) {
			sql = "select count(1) from "
					+ TABLE_NAME_PERMISSION
					+ " t1, "
					+ TABLE_NAME_MENU_TYPE
					+ " t2 "
					+ "where t1.menu_type_id = t2.menu_type_id and t1.game_id = 0 ";
			if (menuTypeId != null && menuTypeId.intValue() > 0) {
				sql += "and t1.menu_type_id = ? ";
				params.add(menuTypeId);
			}
			if (!StringUtils.isBlank(resourceName)) {
				sql += "and t1.resource_name like ? ";
				resourceName = "%" + resourceName + "%";
				params.add(resourceName);
			}
			if (!StringUtils.isBlank(resourceUrl)) {
				sql += "and t1.resource_url like ? ";
				resourceUrl = "%" + resourceUrl + "%";
				params.add(resourceUrl);
			}
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
	public List<Menu> getMenuGrid(Integer gameId, Integer menuTypeId,
			String resourceName, String resourceUrl, int from, int length,
			String sidx, String sord) {
		List<Object> params = new ArrayList<Object>();
		String sql = null;
		if (gameId == null) {
			sql = "select * from (select t1.permission_id, t1.resource_url, t1.resource_name, t1.game_id, t3.game_name, t3.game_ab, t1.menu_type_id, t2.menu_type_name, t1.description, t2.menu_order from "
					+ TABLE_NAME_PERMISSION
					+ " t1, "
					+ TABLE_NAME_MENU_TYPE
					+ " t2, "
					+ TABLE_NAME_GAME
					+ " t3 "
					+ "where t1.menu_type_id = t2.menu_type_id and t1.game_id = t3.game_id ";

			if (menuTypeId != null && menuTypeId.intValue() > 0) {
				sql += "and t1.menu_type_id = ? ";
				params.add(menuTypeId);
			}
			if (!StringUtils.isBlank(resourceName)) {
				sql += "and t1.resource_name like ? ";
				resourceName = "%" + resourceName + "%";
				params.add(resourceName);
			}
			if (!StringUtils.isBlank(resourceUrl)) {
				sql += "and t1.resource_url like ? ";
				resourceUrl = "%" + resourceUrl + "%";
				params.add(resourceUrl);
			}
			sql += " union all select t1.permission_id, t1.resource_url, t1.resource_name, t1.game_id, '系统' as game_name, 'system' as game_ab, t1.menu_type_id, t2.menu_type_name, t1.description, t2.menu_order from "
					+ TABLE_NAME_PERMISSION
					+ " t1, "
					+ TABLE_NAME_MENU_TYPE
					+ " t2 "
					+ "where t1.menu_type_id = t2.menu_type_id and t1.game_id = 0 ";
			if (menuTypeId != null && menuTypeId.intValue() > 0) {
				sql += "and t1.menu_type_id = ? ";
				params.add(menuTypeId);
			}
			if (!StringUtils.isBlank(resourceName)) {
				sql += "and t1.resource_name like ? ";
				resourceName = "%" + resourceName + "%";
				params.add(resourceName);
			}
			if (!StringUtils.isBlank(resourceUrl)) {
				sql += "and t1.resource_url like ? ";
				resourceUrl = "%" + resourceUrl + "%";
				params.add(resourceUrl);
			}
			sql += ") x ";  //mysql要别名
		} else if (gameId.intValue() != 0) {
			sql = "select t1.permission_id, t1.resource_url, t1.resource_name, t1.game_id, t3.game_name, t3.game_ab, t1.menu_type_id, t2.menu_type_name, t1.description, t2.menu_order from "
					+ TABLE_NAME_PERMISSION
					+ " t1, "
					+ TABLE_NAME_MENU_TYPE
					+ " t2, "
					+ TABLE_NAME_GAME
					+ " t3 "
					+ "where t1.menu_type_id = t2.menu_type_id and t1.game_id = t3.game_id ";

			if (gameId != null && gameId.intValue() > 0) {
				sql += "and t1.game_id = ? ";
				params.add(gameId);
			}
			if (menuTypeId != null && menuTypeId.intValue() > 0) {
				sql += "and t1.menu_type_id = ? ";
				params.add(menuTypeId);
			}
			if (!StringUtils.isBlank(resourceName)) {
				sql += "and t1.resource_name like ? ";
				resourceName = "%" + resourceName + "%";
				params.add(resourceName);
			}
			if (!StringUtils.isBlank(resourceUrl)) {
				sql += "and t1.resource_url like ? ";
				resourceUrl = "%" + resourceUrl + "%";
				params.add(resourceUrl);
			}
		} else if (gameId.intValue() == 0) {
			sql = "select t1.permission_id, t1.resource_url, t1.resource_name, t1.game_id, '系统' as game_name, 'system' as game_ab, t1.menu_type_id, t2.menu_type_name, t1.description, t2.menu_order from "
					+ TABLE_NAME_PERMISSION
					+ " t1, "
					+ TABLE_NAME_MENU_TYPE
					+ " t2 "
					+ "where t1.menu_type_id = t2.menu_type_id and t1.game_id = 0 ";
			if (menuTypeId != null && menuTypeId.intValue() > 0) {
				sql += "and t1.menu_type_id = ? ";
				params.add(menuTypeId);
			}
			if (!StringUtils.isBlank(resourceName)) {
				sql += "and t1.resource_name like ? ";
				resourceName = "%" + resourceName + "%";
				params.add(resourceName);
			}
			if (!StringUtils.isBlank(resourceUrl)) {
				sql += "and t1.resource_url like ? ";
				resourceUrl = "%" + resourceUrl + "%";
				params.add(resourceUrl);
			}
		}

		if (StringUtils.isBlank(sidx)) {
			sql += "order by menu_order, permission_id ";
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

		List<Menu> gridData = getJdbcTemplate().query(sql, args,
				new MenuMapper());

		return gridData;
	}

	@Override
	public int countByResourceUrl(String resourceUrl) {
		final String sql = "select count(1) from " + TABLE_NAME_PERMISSION
				+ " t1 where t1.resource_url = ? ";

		Object[] args = { resourceUrl };

		int records = getJdbcTemplate()
				.queryForObject(sql, args, Integer.class);

		return records;
	}

	@Override
	public int insertPermission(String resourceName, String resourceUrl,
			Integer gameId, Integer menuTypeId, String description) {
		final String sql = "insert into "
				+ TABLE_NAME_PERMISSION
				+ " ( resource_name, resource_url, game_id, menu_type_id, description) "
				+ "values ( ?, ?, ?, ?, ?) ";

		Object[] args = { resourceName, resourceUrl, gameId, menuTypeId,
				description };

		int records = getJdbcTemplate().update(sql, args);

		return records;
	}

	@Override
	public int insertPermission2(Menu menu) {
		final String sql = "insert into "
				+ TABLE_NAME_PERMISSION
				+ " ( resource_name, resource_url, game_id, menu_type_id, description,resource_order) "
				+ "values ( ?, ?, ?, ?, ?,?) ";
		
		Object[] args = { menu.getResourceName(),menu.getResourceUrl(), menu.getGameId(), menu.getMenuTypeId(),
				menu.getDescription(),menu.getOrderInt() };
		
		int records = getJdbcTemplate().update(sql, args);
		
		return records;
	}
	@Override
	public int updatePermissionOrder(Integer seq, 
			String resourceUrl, Integer gameId) {
		final String sql = "update "
				+ TABLE_NAME_PERMISSION
				+ " set resource_order = ? where game_id = ? and resource_url = ? ";
		Object[] args = { seq, gameId,resourceUrl};
		
		int records = getJdbcTemplate().update(sql, args);
		
		return records;
	}
	@Override
	public int countByResourceUrlWithoutId(Integer permissionId,
			String resourceUrl) {
		final String sql = "select count(1) from " + TABLE_NAME_PERMISSION
				+ " t1 where t1.resource_url = ? and t1.permission_id <> ? ";

		Object[] args = { resourceUrl, permissionId };

		int records = getJdbcTemplate()
				.queryForObject(sql, args, Integer.class);

		return records;
	}

	@Override
	public int updatePermission(Integer permissionId, String resourceName,
			String resourceUrl, Integer gameId, Integer menuTypeId,
			String description) {
		final String sql = "update "
				+ TABLE_NAME_PERMISSION
				+ " set resource_name = ?, resource_url = ?, game_id = ?, menu_type_id = ?, description = ? where permission_id = ? ";
		Object[] args = { resourceName, resourceUrl, gameId, menuTypeId,
				description, permissionId };

		int records = getJdbcTemplate().update(sql, args);

		return records;
	}

	@Override
	public int deletePermission(Integer permissionId) {
		final String sql = "delete from " + TABLE_NAME_PERMISSION
				+ " where permission_id = ? ";

		Object[] args = { permissionId };

		return getJdbcTemplate().update(sql, args);
	}

	@Override
	public List<Permission> getBindPermissionsByRoleIdAndGameId(Integer roleId,
			Integer gameId) {
		List<Object> params = new ArrayList<Object>();
		String sql = "";
		if (gameId.intValue() == 0) {
			sql = "select t3.permission_id, t3.resource_url, concat('系统-' , t4.menu_type_name , '-' , t3.resource_name) as resource_name, t3.game_id, t3.menu_type_id from "
					+ TABLE_NAME
					+ " t1, "
					+ TABLE_NAME_MAPPING
					+ " t2, "
					+ TABLE_NAME_PERMISSION
					+ " t3, "
					+ TABLE_NAME_MENU_TYPE
					+ " t4 "
					+ "where t1.role_id = t2.role_id and t2.permission_id = t3.permission_id and t3.menu_type_id = t4.menu_type_id and t3.game_id = 0 and t1.role_id = ? "
					+ " order by game_id, menu_type_id, permission_id ";
			params.add(roleId);
		} else {
            //替换之concat(t5.game_name , '-', t4.menu_type_name , '-' , t3.resource_name )
			sql = "select t3.permission_id, t3.resource_url, concat(t5.game_name , '-', t4.menu_type_name , '-' , t3.resource_name ) as resource_name, t3.game_id, t3.menu_type_id from "
					+ TABLE_NAME
					+ " t1, "
					+ TABLE_NAME_MAPPING
					+ " t2, "
					+ TABLE_NAME_PERMISSION
					+ " t3, "
					+ TABLE_NAME_MENU_TYPE
					+ " t4, "
					+ TABLE_NAME_GAME
					+ " t5 "
					+ "where t1.role_id = t2.role_id and t2.permission_id = t3.permission_id and t3.menu_type_id = t4.menu_type_id and t3.game_id = t5.game_id and t3.game_id = ? and t1.role_id = ? "
					+ " order by game_id, menu_type_id, permission_id ";
			params.add(gameId);
			params.add(roleId);
		}

		Object[] args = new Object[params.size()];
		for (int i = 0; i < params.size(); i++) {
			args[i] = params.get(i);
		}

		List<Permission> permissions = getJdbcTemplate().query(sql, args,
				new PermissionMapper());

		return permissions;
	}

	@Override
	public List<Permission> getUnbindPermissionsByRoleIdAndGameId(
			Integer roleId, Integer gameId) {
		List<Object> params = new ArrayList<Object>();
		String sql = "";
		if (gameId.intValue() == 0) {
			sql = "select t3.permission_id, t3.resource_url, concat('系统-' , t4.menu_type_name , '-' , t3.resource_name) as resource_name, t3.game_id, t3.menu_type_id from "
					+ TABLE_NAME_PERMISSION
					+ " t3, "
					+ TABLE_NAME_MENU_TYPE
					+ " t4 "
					+ "where t3.permission_id not in (select t2.permission_id from "
					+ TABLE_NAME
					+ " t1, "
					+ TABLE_NAME_MAPPING
					+ " t2 where t1.role_id = t2.role_id and t1.role_id = ?) "
					+ "and t3.menu_type_id = t4.menu_type_id and t3.game_id = 0 order by game_id, menu_type_id, permission_id ";
			params.add(roleId);
		} else {
			sql = "select t3.permission_id, t3.resource_url, concat(t5.game_name , '-', t4.menu_type_name , '-' , t3.resource_name )  as resource_name, t3.game_id, t3.menu_type_id from "
					+ TABLE_NAME_PERMISSION
					+ " t3, "
					+ TABLE_NAME_MENU_TYPE
					+ " t4, "
					+ TABLE_NAME_GAME
					+ " t5 "
					+ "where t3.permission_id not in (select t2.permission_id from "
					+ TABLE_NAME
					+ " t1, "
					+ TABLE_NAME_MAPPING
					+ " t2 where t1.role_id = t2.role_id and t1.role_id = ?) "
					+ "and t3.menu_type_id = t4.menu_type_id and t3.game_id = t5.game_id and t3.game_id = ? "
					+ " order by game_id, menu_type_id, permission_id ";
			params.add(roleId);
			params.add(gameId);
		}

		Object[] args = new Object[params.size()];
		for (int i = 0; i < params.size(); i++) {
			args[i] = params.get(i);
		}

		List<Permission> permissions = getJdbcTemplate().query(sql, args,
				new PermissionMapper());

		return permissions;
	}

	@Override
	public List<Permission> getPermissionsById(Integer permissionId) {
		final String sql = "select t1.permission_id, t1.resource_url, t1.resource_name, t1.game_id, t1.menu_type_id from "
				+ TABLE_NAME_PERMISSION + " t1 where t1.permission_id = ? ";

		Object[] args = { permissionId };

		List<Permission> permissions = getJdbcTemplate().query(sql, args,
				new PermissionMapper());

		return permissions;
	}

	@Override
	public List<MenuType> getMenuTypeById(Integer menuTypeId) {
		final String sql = "select t1.menu_type_id, t1.menu_type_name, t1.description from "
				+ TABLE_NAME_MENU_TYPE + " t1 where t1.menu_type_id = ? ";

		Object[] args = { menuTypeId };
		List<MenuType> list = getJdbcTemplate().query(sql, args,
				new MenuTypeMapper());

		return list;
	}

}
