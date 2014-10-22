package org.david.rain.service;

import org.david.rain.model.*;

import java.util.List;

public interface SystemManageService {

	int countUserOptionLogByConditions(String beginDate, String endDate,
                                       Integer logType, String userAccount, String optionContent);

	List<OptionLog> getUserOptionLogGrid(String beginDate, String endDate,
                                         Integer logType, String userAccount, String optionContent,
                                         int from, int length, String sidx, String sord);

	List<Role> getRoleDetailList();

	int countUserRoleByName(String roleName);

	int countUserRoleByCode(String roleCode);

	int addUserRole(String roleName, String roleCode, String roleDescribe);

	int countUserRoleByNameWithoutId(String roleName, Integer roleId);

	int countUserRoleByCodeWithoutId(String roleCode, Integer roleId);

	int editUserRole(Integer roleId, String roleName, String roleCode,
                     String roleDescribe);

	int countUserByRoleId(Integer roleId);

	int deleteUserRole(Integer roleId);

	Role getRoleDetailById(Integer roleId);

	int countUserByConditions(Integer roleId, Integer userStatus,
                              String userName, String account);

	List<User> getUserGrid(Integer roleId, Integer userStatus, String userName,
                           String account, int from, int length, String sidx, String sord);

	List<Permission> getBindPermissionsByRoleId(Integer roleId);

	List<Permission> getUnbindPermissionsByRoleId(Integer roleId);

	int bindPermissions(Integer roleId, String permissionIds);

	int unbindPermissions(Integer roleId, String permissionIds);

	int addUser(String account, String userName, String userPwd,
                Integer roleId, Integer userStatus, String agent);

	int editUser(Integer userId, String account, String userName,
                 String userPwd, Integer roleId, Integer userStatus, String agent);

	int deleteUser(Integer userId);

	int changePassword(String oldPassword, String newPassword);

	int countRole();

	List<Role> getRoleGrid(int from, int length, String sidx, String sord);

	List<GameBean> getGameList();

	List<MenuType> getMenuTypeList();

	int countMenuByConditions(Integer gameId, Integer menuTypeId,
                              String resourceName, String resourceUrl);

	List<Menu> getMenuGrid(Integer gameId, Integer menuTypeId,
                           String resourceName, String resourceUrl, int from, int length, String sidx, String sord);

	int addMenu(String resourceName, String resourceUrl, Integer gameId,
                Integer menuTypeId, String description);

	int editMenu(Integer permissionId, String resourceName, String resourceUrl,
                 Integer gameId, Integer menuTypeId, String description);

	int deleteMenu(Integer permissionId);

	List<RoleGameDetail> getRoleGameListByRoleId(Integer roleId);

	int countGameList();

	List<RoleGameDetail> getRoleGameListByRoleId(Integer roleId,
                                                 int from, int length, String sidx, String sord);

	int bindRoleGame(Integer roleId, Integer gameId);

	int unbindRoleGame(Integer roleId, Integer gameId);

	GameBean getGameDetailById(Integer gameId);

	List<Permission> getBindPermissionsByRoleIdAndGameId(Integer roleId, Integer gameId);

	List<Permission> getUnbindPermissionsByRoleIdAndGameId(Integer roleId, Integer gameId);

}
