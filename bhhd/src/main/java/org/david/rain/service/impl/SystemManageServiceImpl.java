package org.david.rain.service.impl;

import org.david.rain.utils.Constants;
import org.david.rain.utils.Utils;
import org.david.rain.dao.*;
import org.david.rain.model.*;
import org.david.rain.service.SystemManageService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("systemManageService")
public class SystemManageServiceImpl implements SystemManageService {

	private final Logger logger = LoggerFactory
			.getLogger(SystemManageServiceImpl.class);

	@Resource(name = "userOptionLogDao")
	private OptionLogDao userOptionLogDao;

	@Resource(name = "rolePermissionMappingDao")
	private RolePermissionMappingDao rolePermissionMappingDao;

	@Resource(name = "roleDetailDao")
	private RoleDao roleDetailDao;

	@Resource(name = "accountDao")
	private UserDao userDao;

	@Resource(name = "gameDao")
	private GameDao gameDao;

	@Resource(name = "roleGameMappingDao")
	private RoleGameMappingDao roleGameMappingDao;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public int countUserOptionLogByConditions(String beginDate, String endDate,
			Integer logType, String userAccount, String optionContent) {
		logger.debug("count user option log by conditions start");

		int count = userOptionLogDao.countUserOptionLog(beginDate, endDate,
				logType, userAccount, optionContent);

		logger.debug("count user option log by conditions is {}", count);
		return count;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<OptionLog> getUserOptionLogGrid(String beginDate,
			String endDate, Integer logType, String userAccount,
			String optionContent, int from, int length, String sidx, String sord) {
		logger.debug("get user option log grid start");

		List<OptionLog> gridData = userOptionLogDao.getUserOptionLogGrid(
				beginDate, endDate, logType, userAccount, optionContent, from,
				length, sidx, sord);

		if (CollectionUtils.isEmpty(gridData)) {
			logger.debug("user option log grid can not find any row");
			return new ArrayList<OptionLog>();
		}

		return gridData;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Role> getRoleDetailList() {
		logger.debug("get role detail list start");

		List<Role> roleDetailList = roleDetailDao.getRoleDetailList();
		if (CollectionUtils.isEmpty(roleDetailList)) {
			logger.debug("role detail list can not find any row");
			return new ArrayList<Role>();
		}

		return roleDetailList;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public int countUserRoleByName(String roleName) {
		logger.debug("count user role by roleName start");

		int records = roleDetailDao.countUserRoleByName(roleName);

		logger.debug("count user role is {}", records);
		return records;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public int countUserRoleByCode(String roleCode) {
		logger.debug("count user role by roleCode start");

		int records = roleDetailDao.countUserRoleByCode(roleCode);

		logger.debug("count user role is {}", records);
		return records;
	}

	@Override
	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public int addUserRole(String roleName, String roleCode, String roleDescribe) {
		logger.debug("add plugin group start");

		int records = roleDetailDao.insertUserRole(roleName, roleCode,
				roleDescribe);

		return records;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public int countUserRoleByNameWithoutId(String roleName, Integer roleId) {
		logger.debug("count user role by roleName without id start");

		int records = roleDetailDao.countUserRoleByNameWithoutId(roleName,
				roleId);

		logger.debug("count user role is {}", records);
		return records;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public int countUserRoleByCodeWithoutId(String roleCode, Integer roleId) {
		logger.debug("count user role by roleCode without id start");

		int records = roleDetailDao.countUserRoleByCodeWithoutId(roleCode,
				roleId);

		logger.debug("count user role is {}", records);
		return records;
	}

	@Override
	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public int editUserRole(Integer roleId, String roleName, String roleCode,
			String roleDescribe) {
		logger.debug("edit plugin group start");

		int records = roleDetailDao.updateUserRole(roleId, roleName, roleCode,
				roleDescribe);

		return records;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public int countUserByRoleId(Integer roleId) {
		logger.debug("count user by roleId start");

		int records = roleDetailDao.countUserByRoleId(roleId);

		logger.debug("count user role is {}", records);
		return records;
	}

	@Override
	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public int deleteUserRole(Integer roleId) {
		logger.debug("edit plugin group start");

		int records = rolePermissionMappingDao
				.deleteRolePermissionMappingByRoleId(roleId);
		records = roleDetailDao.deleteUserRole(roleId);
		if (records == 0)
			throw new RuntimeException();

		return records;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Role getRoleDetailById(Integer roleId) {
		logger.debug("get roleDetail start");

		List<Role> roleDetails = roleDetailDao.getRoleDetailById(roleId);

		if (CollectionUtils.isEmpty(roleDetails)) {
			logger.debug("roleDetails can not find any row");
			return new Role();
		}

		return roleDetails.get(0);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Permission> getBindPermissionsByRoleId(Integer roleId) {
		logger.debug("get bindPermissions by roleId start");

		List<Permission> bindPermissions = roleDetailDao
				.getBindPermissionsByRoleId(roleId);

		if (CollectionUtils.isEmpty(bindPermissions)) {
			logger.debug("bindPermissions can not find any row");
			return new ArrayList<Permission>();
		}

		return bindPermissions;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Permission> getUnbindPermissionsByRoleId(Integer roleId) {
		logger.debug("get unbindPermissions by roleId start");

		List<Permission> unbindPermissions = roleDetailDao
				.getUnbindPermissionsByRoleId(roleId);

		if (CollectionUtils.isEmpty(unbindPermissions)) {
			logger.debug("unbindPermissions can not find any row");
			return new ArrayList<Permission>();
		}

		return unbindPermissions;
	}

	@Override
	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public int bindPermissions(Integer roleId, String permissionIds) {
		logger.debug("bind permissions start");

		int result = 0;
		if (StringUtils.isBlank(permissionIds))
			return result;

		List<Integer> permissionIdList = Utils
				.stringsToIntegerList(permissionIds);

		int records = 0;
		for (Integer permissionId : permissionIdList) {
			records = roleDetailDao.insertRolePromissionMappingById(roleId,
					permissionId);
			if (records > 0) {
				result++;
			}
		}

		if (result != permissionIdList.size())
			throw new RuntimeException();

		return result;
	}

	@Override
	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public int unbindPermissions(Integer roleId, String permissionIds) {
		logger.debug("unbind permissions start");

		int result = 0;
		if (StringUtils.isBlank(permissionIds))
			return result;

		List<Integer> permissionIdList = Utils
				.stringsToIntegerList(permissionIds);

		int records = 0;
		for (Integer permissionId : permissionIdList) {
			records = roleDetailDao.deleteRolePromissionMappingById(roleId,
					permissionId);
			if (records > 0) {
				result++;
			}
		}

		if (result != permissionIdList.size())
			throw new RuntimeException();

		return result;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public int countUserByConditions(Integer roleId, Integer userStatus,
			String userName, String account) {
		logger.debug("count userByConditions start");

		int records = userDao.countUserByConditions(roleId, userStatus,
				userName, account);

		return records;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<User> getUserGrid(Integer roleId, Integer userStatus,
			String userName, String account, int from, int length, String sidx,
			String sord) {
		logger.debug("getUserGrid start");

		List<User> gridData = userDao.getUserGrid(roleId, userStatus, userName,
				account, from, length, sidx, sord);

		if (CollectionUtils.isEmpty(gridData)) {
			return new ArrayList<User>();
		}

		return gridData;
	}

	@Override
	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public int addUser(String account, String userName, String userPwd,
			Integer roleId, Integer userStatus, String agent) {
		logger.debug("addUser service start");

		int records = userDao.countByAccount(account);

		if (records > 0)
			return Constants.EXIST_FLAG;

		records = userDao.insert(userName, account,
				Utils.getMd5(userPwd, account), roleId, userStatus, agent);

		return records;
	}

	@Override
	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public int editUser(Integer userId, String account, String userName,
			String userPwd, Integer roleId, Integer userStatus, String agent) {
		logger.debug("editUser service start");

		int records = userDao.countByAccountWithoutId(userId, account);

		if (records > 0)
			return Constants.EXIST_FLAG;

		if (StringUtils.isBlank(userPwd)) {
			records = userDao.updateWithoutPwd(userId, userName, account,
					roleId, userStatus, agent);
		} else {
			records = userDao.update(userId, userName, account,
					Utils.getMd5(userPwd, account), roleId, userStatus, agent);
		}

		return records;
	}

	@Override
	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public int deleteUser(Integer userId) {
		logger.debug("deleteUser service start");

		int records = userDao.delete(userId);

		return records;
	}

	@Override
	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public int changePassword(String oldPassword, String newPassword) {
		logger.debug("change password service start");

		String account = Utils.getLoginAccount();

		int records = userDao.countByPassword(account,
				Utils.getMd5(oldPassword, account));
		if (records == 0)
			return -1;

		records = userDao.updatePassword(account,
				Utils.getMd5(newPassword, account));

		return records;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public int countRole() {
		logger.debug("count role list start");

		int records = roleDetailDao.countRole();

		return records;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Role> getRoleGrid(int from, int length, String sidx, String sord) {
		logger.debug("getUserGrid start");

		List<Role> gridData = roleDetailDao.getRoleGrid(from, length, sidx,
				sord);

		if (CollectionUtils.isEmpty(gridData)) {
			return new ArrayList<Role>();
		}

		return gridData;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<GameBean> getGameList() {
		logger.debug("getGameList start");

		List<GameBean> gameList = gameDao.getGameList();

		if (CollectionUtils.isEmpty(gameList)) {
			return new ArrayList<GameBean>();
		}

		return gameList;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<MenuType> getMenuTypeList() {
		logger.debug("getMenuTypeList start");

		List<MenuType> menuTypeList = roleDetailDao.getMenuTypeList();

		if (CollectionUtils.isEmpty(menuTypeList)) {
			return new ArrayList<MenuType>();
		}

		return menuTypeList;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public int countMenuByConditions(Integer gameId, Integer menuTypeId,
			String resourceName, String resourceUrl) {
		logger.debug("count menuByConditions start");

		int records = roleDetailDao.countMenuByConditions(gameId, menuTypeId,
				resourceName, resourceUrl);

		return records;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Menu> getMenuGrid(Integer gameId, Integer menuTypeId,
			String resourceName, String resourceUrl, int from, int length,
			String sidx, String sord) {
		logger.debug("getMenuGrid start");

		List<Menu> gridData = roleDetailDao.getMenuGrid(gameId, menuTypeId,
				resourceName, resourceUrl, from, length, sidx, sord);

		if (CollectionUtils.isEmpty(gridData)) {
			return new ArrayList<Menu>();
		}

		return gridData;
	}

	@Override
	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public int addMenu(String resourceName, String resourceUrl, Integer gameId,
			Integer menuTypeId, String description) {
		logger.debug("addMenu service start");

		int records = roleDetailDao.countByResourceUrl(resourceUrl);

		if (records > 0)
			return Constants.EXIST_FLAG;

		records = roleDetailDao.insertPermission(resourceName, resourceUrl,
				gameId, menuTypeId, description);

		return records;
	}



    @Override
	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public int editMenu(Integer permissionId, String resourceName,
			String resourceUrl, Integer gameId, Integer menuTypeId,
			String description) {
		logger.debug("editMenu service start");

		int records = roleDetailDao.countByResourceUrlWithoutId(permissionId,
				resourceUrl);

		if (records > 0)
			return Constants.EXIST_FLAG;

		records = roleDetailDao.updatePermission(permissionId, resourceName,
				resourceUrl, gameId, menuTypeId, description);

		return records;
	}

	@Override
	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public int deleteMenu(Integer permissionId) {
		logger.debug("deleteMenu group start");

		int records = rolePermissionMappingDao
				.deleteRolePermissionMappingBypermissionId(permissionId);

		records = roleDetailDao.deletePermission(permissionId);

		return records;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<RoleGameDetail> getRoleGameListByRoleId(Integer roleId) {
		logger.debug("get roleGameList by roleId start");

		List<RoleGameDetail> list = roleGameMappingDao
				.getRoleGameListByRoleId(roleId);

		if (CollectionUtils.isEmpty(list)) {
			logger.debug("roleGameList can not find any row");
			return new ArrayList<RoleGameDetail>();
		}

		return list;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public int countGameList() {
		logger.debug("count gamelist");

		int records = roleGameMappingDao.countGameList();

		return records;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<RoleGameDetail> getRoleGameListByRoleId(Integer roleId,
			int from, int length, String sidx, String sord) {
		logger.debug("get roleGameList start");

		List<RoleGameDetail> gridData = roleGameMappingDao
				.getRoleGameListByRoleId(roleId, from, length, sidx, sord);

		if (CollectionUtils.isEmpty(gridData)) {
			return new ArrayList<RoleGameDetail>();
		}

		return gridData;
	}

	@Override
	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public int bindRoleGame(Integer roleId, Integer gameId) {
		logger.debug("bind role game start");

		int result = roleGameMappingDao.countByRoleIdAndGameid(roleId, gameId);
		if (result > 0) {
			return 2;
		}

		result = roleGameMappingDao.insertRoleGameMapping(roleId, gameId);

		return result;
	}

	@Override
	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public int unbindRoleGame(Integer roleId, Integer gameId) {
		logger.debug("bind role game start");

		int result = roleGameMappingDao.countByRoleIdAndGameid(roleId, gameId);
		if (result == 0) {
			return 2;
		}

		result = roleGameMappingDao
				.deleteRolePermissionMappingByRoleIdAndGameId(roleId, gameId);

		result = roleGameMappingDao.deleteRoleGameMapping(roleId, gameId);

		return result;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public GameBean getGameDetailById(Integer gameId) {
		logger.debug("get gameDetail start");

		if (gameId.intValue() == 0) {
			GameBean system = new GameBean();
			system.setGameId(0);
			system.setGameName("系统");
			system.setShortName("system");
			return system;
		}

		List<GameBean> gameDetails = gameDao.getGameById(gameId);

		if (CollectionUtils.isEmpty(gameDetails)) {
			logger.debug("gameDetail can not find any row");
			return new GameBean();
		}

		return gameDetails.get(0);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Permission> getBindPermissionsByRoleIdAndGameId(Integer roleId,
			Integer gameId) {
		logger.debug("get bindPermissions by roleId and gameId start");

		List<Permission> bindPermissions = roleDetailDao
				.getBindPermissionsByRoleIdAndGameId(roleId, gameId);

		if (CollectionUtils.isEmpty(bindPermissions)) {
			logger.debug("bindPermissions can not find any row");
			return new ArrayList<Permission>();
		}

		return bindPermissions;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Permission> getUnbindPermissionsByRoleIdAndGameId(
			Integer roleId, Integer gameId) {
		logger.debug("get unbindPermissions by roleId and gameId start");

		List<Permission> unbindPermissions = roleDetailDao
				.getUnbindPermissionsByRoleIdAndGameId(roleId, gameId);

		if (CollectionUtils.isEmpty(unbindPermissions)) {
			logger.debug("unbindPermissions can not find any row");
			return new ArrayList<Permission>();
		}

		return unbindPermissions;
	}

}
