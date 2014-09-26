package org.david.rain.dao;


import org.david.rain.model.*;

import java.util.List;

public interface RoleDao {

	List<Role> getRoleDetailList();

	int countUserRoleByName(String roleName);

	int countUserRoleByCode(String roleCode);

	int insertUserRole(String roleName, String roleCode, String roleDescribe);

	int countUserRoleByNameWithoutId(String roleName, Integer roleId);

	int countUserRoleByCodeWithoutId(String roleCode, Integer roleId);

	int updateUserRole(Integer roleId, String roleName, String roleCode,
                       String roleDescribe);

	int deleteUserRole(Integer roleId);

	List<Role> getRoleDetailById(Integer roleId);

	List<Permission> getBindPermissionsByRoleId(Integer roleId);

	List<Permission> getUnbindPermissionsByRoleId(Integer roleId);

	int insertRolePromissionMappingById(Integer roleId, Integer permissionId);

	int deleteRolePromissionMappingById(Integer roleId, Integer permissionId);

	List<RolePermissionMapping> getRolePermissionMapping();

	int countUserByRoleId(Integer roleId);

	int countRole();

	List<Role> getRoleGrid(int from, int length, String sidx, String sord);

	List<Menu> getMenuListByRoleCode(String roleCode);

	List<MenuType> getMenuTypeList();

	int countMenuByConditions(Integer gameId, Integer menuTypeId,
                              String resourceName, String resourceUrl);

	List<Menu> getMenuGrid(Integer gameId, Integer menuTypeId,
                           String resourceName, String resourceUrl, int from, int length,
                           String sidx, String sord);

	int countByResourceUrl(String resourceUrl);

	int insertPermission(String resourceName, String resourceUrl,
                         Integer gameId, Integer menuTypeId, String description);
	public int insertPermission2(Menu menu);

	int countByResourceUrlWithoutId(Integer permissionId, String resourceUrl);

	int updatePermission(Integer permissionId, String resourceName,
                         String resourceUrl, Integer gameId, Integer menuTypeId,
                         String description);
	int updatePermissionOrder(Integer seq,
                              String resourceUrl, Integer gameId);

	int deletePermission(Integer permissionId);

	List<Permission> getBindPermissionsByRoleIdAndGameId(Integer roleId,
                                                         Integer gameId);

	List<Permission> getUnbindPermissionsByRoleIdAndGameId(Integer roleId,
                                                           Integer gameId);

	List<Permission> getPermissionsById(Integer permissionId);

	List<MenuType> getMenuTypeById(Integer menuTypeId);
}
