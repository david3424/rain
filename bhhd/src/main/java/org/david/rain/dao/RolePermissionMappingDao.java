package org.david.rain.dao;

public interface RolePermissionMappingDao {

	int deleteRolePermissionMappingByRoleId(Integer roleId);

	int deleteRolePermissionMappingBypermissionId(Integer permissionId);
}
