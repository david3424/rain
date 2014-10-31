package org.david.rain.controller;

import org.apache.commons.lang.StringUtils;
import org.david.rain.controller.common.BaseController;
import org.david.rain.dao.MenuTypeDao;
import org.david.rain.model.*;
import org.david.rain.security.tool.SessionUser;
import org.david.rain.service.SystemManageService;
import org.david.rain.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system")
public class SystemManageController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(SystemManageController.class);

	 @Autowired
	 private HttpSession session;

	@Resource(name = "systemManageService")
	private SystemManageService systemManageService;

	@Resource(name = "menuTypeDaoImpl")
	private MenuTypeDao menuTypeDao;


	@RequestMapping(value = "/show_log_page", method = RequestMethod.GET)
	public String showUserOptionLogPage(ModelMap modelMap) {
		logger.info("show_log_page start");

		return "system/show_log_page";
	}

	@RequestMapping(value = "/get_log_table", method = RequestMethod.POST)
	public void getLogTable(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("dt_json") String jsonString) {
		logger.info("getLogTable start");

		// 把参数转换为Map对象
		Map<String, Object> params = jsonToMap(jsonString);
		// 获取查询选项
		Integer displayStart = Integer.parseInt((String) params.get(Constants.DATATABLES_IDISPLAYSTART));
		Integer displayLength = Integer.parseInt((String) params.get(Constants.DATATABLES_IDISPLAYLENGTH));
		String beginDate = (String)params.get("beginDate");
		String endDate = (String)params.get("endDate");
		Integer logType = Integer.parseInt((String)params.get("logType"));
		String userAccount = (String)params.get("userAccount");
		String optionContent = (String)params.get("optionContent");
		int records = systemManageService.countUserOptionLogByConditions(
				beginDate, endDate, logType, userAccount, optionContent);
		List<OptionLog> gridData = new ArrayList<OptionLog>();
		if (records != 0) {
			gridData = systemManageService.getUserOptionLogGrid(beginDate, endDate, logType, userAccount,
					optionContent, displayStart,  displayLength, "", "");
		}

		printDataTables(response, records, gridData);
	}
	@RequestMapping(value = "get_log_grid", method = RequestMethod.POST)
	public @ResponseBody
	JqGrid<OptionLog> getUserOptionLogGrid(String beginDate,
			String endDate, Integer logType, String userAccount, String optionContent,
			Integer page, Integer rows, String sidx, String sord) {
		logger.info("getUserOptionLogGrid start");

		JqGrid<OptionLog> pluginDetailGrid = new JqGrid<OptionLog>();
		int records = systemManageService.countUserOptionLogByConditions(
				beginDate, endDate, logType, userAccount, optionContent);

		int from = rows * (page - 1);
		int length = rows * page;
		int total = (int) Math.ceil((double) records / (double) rows);

		List<OptionLog> gridData = systemManageService
				.getUserOptionLogGrid(beginDate, endDate, logType, userAccount,
						optionContent, from, length, sidx, sord);

		pluginDetailGrid.setGridData(gridData);
		pluginDetailGrid.setPage(page);
		pluginDetailGrid.setRecords(records);
		pluginDetailGrid.setTotal(total);

		return pluginDetailGrid;
	}

	@RequestMapping(value = "/show_role_page", method = RequestMethod.GET)
	public String showUserRoleListPage(ModelMap modelMap) {
		logger.info("showUserRoleListPage start");

//		List<Role> roleDetailList = systemManageService.getRoleDetailList();

//		modelMap.put("roleDetailList", roleDetailList);
		return "system/user_role_list";
	}

	@RequestMapping(value = "/get_role_table", method = RequestMethod.POST)
	public void getRoleTable(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("dt_json") String jsonString) {
		logger.info("getUserGrid start");

		// 把参数转换为Map对象
		Map<String, Object> params = jsonToMap(jsonString);
		// 获取查询选项
		Integer displayStart = Integer.parseInt((String) params.get(Constants.DATATABLES_IDISPLAYSTART));
		Integer displayLength = Integer.parseInt((String) params.get(Constants.DATATABLES_IDISPLAYLENGTH));

		int records = systemManageService.countRole();
		List<Role> gridData = new ArrayList<Role>();
		if (records != 0) {
			gridData = systemManageService.getRoleGrid(displayStart,  displayLength, "", "");
		}

		printDataTables(response, records, gridData);
	}

	@RequestMapping(value = "add_user_role", method = RequestMethod.POST)
	public @ResponseBody
	Integer addUserRole(String roleName, String roleCode, String roleDescribe) {
		logger.info("addUserRole start");

		int records = systemManageService.countUserRoleByName(roleName);
		if (records > 0) {
			return 2;
		}

		records = systemManageService.countUserRoleByCode(roleCode);
		if (records > 0) {
			return 3;
		}

		records = systemManageService.addUserRole(roleName, roleCode,
				roleDescribe);

		return records;
	}

	@RequestMapping(value = "edit_user_role", method = RequestMethod.POST)
	public @ResponseBody
	Integer editUserRole(Integer roleId, String roleName, String roleCode,
			String roleDescribe) {
		logger.info("editUserRole start");

		int records = systemManageService.countUserRoleByNameWithoutId(
				roleName, roleId);
		if (records > 0) {
			return 2;
		}

		records = systemManageService.countUserRoleByCodeWithoutId(roleCode,
				roleId);
		if (records > 0) {
			return 3;
		}

		records = systemManageService.editUserRole(roleId, roleName, roleCode,
				roleDescribe);

		return records;
	}

	@RequestMapping(value = "delete_user_role", method = RequestMethod.POST)
	public @ResponseBody
	Integer deleteUserRole(Integer roleId) {
		logger.info("deleteUserRole start");

		int records = systemManageService.countUserByRoleId(roleId);
		if (records > 0) {
			return 2;
		}

		records = systemManageService.deleteUserRole(roleId);

		return records;
	}

	@RequestMapping(value = "/show_role_game_page", method = RequestMethod.GET)
	public String showRoleGamePage(Integer roleId, ModelMap modelMap) {
		logger.info("url: showRoleGamePage start");

		Role roleDetail = systemManageService.getRoleDetailById(roleId);

		modelMap.put("roleDetail", roleDetail);

		return "system/role_game_list";
	}

	@RequestMapping(value = "/get_role_game_table", method = RequestMethod.POST)
	public void getRoleGameTable(HttpServletRequest request,
			HttpServletResponse response, 
			@RequestParam("dt_json") String jsonString) {
		logger.info("getRoleGameTable start");

		// 把参数转换为Map对象
		Map<String, Object> params = jsonToMap(jsonString);
		// 获取查询选项
		Integer displayStart = Integer.parseInt((String) params.get(Constants.DATATABLES_IDISPLAYSTART));
		Integer displayLength = Integer.parseInt((String) params.get(Constants.DATATABLES_IDISPLAYLENGTH));
		Integer roleId = Integer.parseInt((String)params.get("roleId"));

		int records = systemManageService.countGameList();
		// TODO 将GameId=0 平台管理加上
		records += 1;
		List<RoleGameDetail> gridData = new ArrayList<RoleGameDetail>();
		if (records != 0) {
			gridData = systemManageService.getRoleGameListByRoleId(roleId, displayStart, displayLength, "", "");
		}

		printDataTables(response, records, gridData);
	}

	@RequestMapping(value = "/bind_role_game", method = RequestMethod.POST)
	public @ResponseBody
	Integer bindRoleGame(Integer roleId, Integer gameId) {
		logger.info("url: bind_role_game start");

		int result = systemManageService.bindRoleGame(roleId,
				gameId);

		return result;
	}

	@RequestMapping(value = "/unbind_role_game", method = RequestMethod.POST)
	public @ResponseBody
	Integer unbindRoleGame(Integer roleId, Integer gameId) {
		logger.info("url: unbind_role_game start");

		int result = systemManageService.unbindRoleGame(roleId,
				gameId);

		return result;
	}

	@RequestMapping(value = "/show_permission_detail_page", method = RequestMethod.GET)
	public String showPermissionDetailPage(Integer roleId, Integer gameId,
			ModelMap modelMap) {
		logger.info("showPermissionDetailPage start");

		Role roleDetail = systemManageService.getRoleDetailById(roleId);

		GameBean gameDetail = systemManageService.getGameDetailById(gameId);

		List<Permission> bindPermissions = systemManageService
				.getBindPermissionsByRoleIdAndGameId(roleId, gameId);
		List<Permission> unbindPermissions = systemManageService
				.getUnbindPermissionsByRoleIdAndGameId(roleId, gameId);

		modelMap.put("roleDetail", roleDetail);
		modelMap.put("gameDetail", gameDetail);
		modelMap.put("bindPermissions", bindPermissions);
		modelMap.put("unbindPermissions", unbindPermissions);

		return "system/permission_detail";
	}

	@RequestMapping(value = "/bind_permissions", method = RequestMethod.POST)
	public @ResponseBody
	Integer bindPermissions(Integer roleId, String permissionIds) {
		logger.info("bindPermissions start");

		int result = systemManageService.bindPermissions(roleId, permissionIds);

		return result;
	}

	@RequestMapping(value = "/unbind_permissions", method = RequestMethod.POST)
	public @ResponseBody
	Integer unbindPermissions(Integer roleId, String permissionIds) {
		logger.info("unbindPermissions start");

		int result = systemManageService.unbindPermissions(roleId,
				permissionIds);

		return result;
	}

	@RequestMapping(value = "/show_user_page", method = RequestMethod.GET)
	public String showUserPage(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		logger.info("showUserPage start");

		List<Role> roleDetailList = systemManageService.getRoleDetailList();
		List<Role> roleDetailListAll = new ArrayList<Role>();
		if (roleDetailList.size() > 0) {
			Role roleDetail = new Role();
			roleDetail.setRoleId(0);
			roleDetail.setRoleName("ALL");
			roleDetailListAll.add(roleDetail);
		}
		roleDetailListAll.addAll(roleDetailList);

		List<SelectBean> userStatusList = new ArrayList<SelectBean>();
		SelectBean selectBean1 = new SelectBean();
		selectBean1.setId(200);
		selectBean1.setName("VALID");
		userStatusList.add(selectBean1);

		SelectBean selectBean2 = new SelectBean();
		selectBean2.setId(100);
		selectBean2.setName("INVALID");
		userStatusList.add(selectBean2);

		List<SelectBean> userStatusListAll = new ArrayList<SelectBean>();
		SelectBean selectBean = new SelectBean();
		selectBean.setId(0);
		selectBean.setName("ALL");
		userStatusListAll.add(selectBean);
		userStatusListAll.addAll(userStatusList);

		String gameShort = (String) session.getAttribute(SessionUser.SESSION_CURRENT_GAME_SHORT);
		int gameId = getGameId(request, gameShort);

		modelMap.put("roleDetailList", roleDetailList);
		modelMap.put("roleDetailListAll", roleDetailListAll);
		modelMap.put("userStatusList", userStatusList);
		modelMap.put("userStatusListAll", userStatusListAll);

		return "system/user_list";
	}

	@RequestMapping(value = "/get_user_table", method = RequestMethod.POST)
	public void getUserTable(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("dt_json") String jsonString) {
		logger.info("getUserGrid start");

		// 把参数转换为Map对象
		Map<String, Object> params = jsonToMap(jsonString);
		// 获取查询选项
		Integer displayStart = Integer.parseInt((String) params.get(Constants.DATATABLES_IDISPLAYSTART));
		Integer displayLength = Integer.parseInt((String) params.get(Constants.DATATABLES_IDISPLAYLENGTH));
		Integer roleId = Integer.parseInt((String)params.get("roleId"));
		Integer userStatus = Integer.parseInt((String)params.get("userStatus"));
		String userName = (String)params.get("userName");
		String account = (String)params.get("account");
		int records = systemManageService.countUserByConditions(roleId,
				userStatus, userName, account);
		List<User> gridData = new ArrayList<User>();
		if (records != 0) {
			gridData = systemManageService.getUserGrid(roleId, userStatus,
					userName, account, displayStart,  displayLength, "", "");
		}

		printDataTables(response, records, gridData);
	}
	@RequestMapping(value = "/get_user_grid", method = RequestMethod.POST)
	public @ResponseBody
	JqGrid<User> getUserGrid(Integer roleId, Integer userStatus,
			String userName, String account, Integer page, Integer rows,
			String sidx, String sord) {
		logger.info("getUserGrid start");

		JqGrid<User> userGrid = new JqGrid<User>();

		logger.info("getUserGrid start");

		userGrid = new JqGrid<User>();
		int records = systemManageService.countUserByConditions(roleId,
				userStatus, userName, account);
		if (records == 0) {
			return userGrid;
		}

		int from = rows * (page - 1);
		int length = rows * page;
		int total = (int) Math.ceil((double) records / (double) rows);
		List<User> gridData = systemManageService.getUserGrid(roleId,
				userStatus, userName, account, from, length, sidx, sord);

		userGrid.setGridData(gridData);
		userGrid.setPage(page);
		userGrid.setRecords(records);
		userGrid.setTotal(total);

		return userGrid;
	}

	@RequestMapping(value = "/add_user", method = RequestMethod.POST)
	public @ResponseBody
	Integer addUser(String account, String userName, String userPwd,
			Integer roleId, Integer userStatus, String agent) {
		logger.info("addUser start");

		int records = systemManageService.addUser(account, userName, userPwd,
				roleId, userStatus, agent);

		return records;
	}

	@RequestMapping(value = "/edit_user", method = RequestMethod.POST)
	public @ResponseBody
	Integer editUser(Integer userId, String account, String userName,
			String userPwd, Integer roleId, Integer userStatus, String agent) {
		logger.info("editUser start");

		int records = systemManageService.editUser(userId, account, userName,
				userPwd, roleId, userStatus, agent);

		return records;

	}

	@RequestMapping(value = "/delete_user", method = RequestMethod.POST)
	public @ResponseBody
	Integer deleteUser(Integer userId) {
		logger.info("deleteUser start");

		int records = systemManageService.deleteUser(userId);

		return records;

	}

	@RequestMapping(value = "/change_password")
	public @ResponseBody
	Integer changePassword(String oldPassword, String newPassword) {
		logger.info("changePassword start");

		int records = systemManageService.changePassword(oldPassword,
				newPassword);

		return records;
	}

	@RequestMapping(value = "/show_menu_page", method = RequestMethod.GET)
	public String showMenuPage(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		logger.info("showMenuPage start");

		List<GameBean> gameList = systemManageService.getGameList();
		GameBean sys = new GameBean();
		sys.setGameId(0);
		sys.setGameName("系统");
		sys.setShortName("system");
		gameList.add(sys);

		List<MenuType> menuTypeList = systemManageService.getMenuTypeList();

		modelMap.put("gameList", gameList);
		modelMap.put("menuTypeList", menuTypeList);

		return "system/menu_list";
	}

	@RequestMapping(value = "/get_menu_table", method = RequestMethod.POST)
	public void getMenuTable(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("dt_json") String jsonString) {
		logger.info("getMenuTable start");

		// 把参数转换为Map对象
		Map<String, Object> params = jsonToMap(jsonString);
		// 获取查询选项
		Integer displayStart = Integer.parseInt((String) params
				.get(Constants.DATATABLES_IDISPLAYSTART));
		Integer displayLength = Integer.parseInt((String) params
				.get(Constants.DATATABLES_IDISPLAYLENGTH));
		Integer gameId = null;
		if (!StringUtils.isBlank((String) params.get("gameId")))
			gameId = Integer.parseInt((String) params.get("gameId"));
		Integer menuTypeId = null;
		if (!StringUtils.isBlank((String) params.get("menuTypeId")))
			menuTypeId = Integer.parseInt((String) params.get("menuTypeId"));
		String resourceName = (String) params.get("resourceName");
		String resourceUrl = (String) params.get("resourceUrl");
		int records = systemManageService.countMenuByConditions(gameId,
				menuTypeId, resourceName, resourceUrl);
		List<Menu> gridData = new ArrayList<Menu>();
		if (records != 0) {
			gridData = systemManageService.getMenuGrid(gameId, menuTypeId,resourceName, resourceUrl, displayStart, displayLength, "", "");
		}

		printDataTables(response, records, gridData);
	}

	@RequestMapping(value = "/add_menu", method = RequestMethod.POST)
	public @ResponseBody
	Integer addMenu(String resourceName, String resourceUrl, Integer gameId,
			Integer menuTypeId, String description) {
		logger.info("addMenu start");

		int records = systemManageService.addMenu(resourceName, resourceUrl,
				gameId, menuTypeId, description);

		return records;
	}

	@RequestMapping(value = "/edit_menu", method = RequestMethod.POST)
	public @ResponseBody
	Integer editMenu(Integer permissionId, String resourceName,
			String resourceUrl, Integer gameId, Integer menuTypeId,
			String description) {
		logger.info("editMenu start");

		int records = systemManageService.editMenu(permissionId, resourceName,
				resourceUrl, gameId, menuTypeId, description);

		return records;

	}

	@RequestMapping(value = "/delete_menu", method = RequestMethod.POST)
	public @ResponseBody
	Integer deleteMenu(Integer permissionId) {
		logger.info("deleteMenu start");
		int records = systemManageService.deleteMenu(permissionId);
		return records;
	}


    /*菜单类型管理*/

    @RequestMapping(value = "/menu_type", method = RequestMethod.GET)
    public String menuType() {
        return "system/menu_type_list";
    }


    @ResponseBody
    @RequestMapping(value = "/addMenuType", method = RequestMethod.POST)
    public Map addMenuType( MenuType menuType) {
        String message;
        Map<String, Object> mresult = new HashMap<>();
        try {
            message = menuTypeDao.addMenuType(menuType);
            mresult.put("result", message);
        } catch (Exception e) {
            e.printStackTrace();
            message = "异常错误";
        }
        mresult.put("result", message);
        return mresult;
    }

    @RequestMapping(value = "/getMenuTypeTableData", method = RequestMethod.POST)
    public void getActivityTypeTableData( HttpServletResponse response,
                                         @RequestParam("dt_json") String dtjson) {
        Map<String, Object> map = jsonToMap(dtjson);
        Integer displayStart = Integer.parseInt((String) map.get(Constants.DATATABLES_IDISPLAYSTART));
        Integer displayLength = Integer.parseInt((String) map.get(Constants.DATATABLES_IDISPLAYLENGTH));
        //执行查询
        List<MenuType> list = menuTypeDao.getMenuTypeList(displayStart, displayLength);
        int count = menuTypeDao.getMenuTypeListCount();
        printDataTables(response, count, list);
    }


    @RequestMapping(value = "/deleteMenuType", method = RequestMethod.GET)
    @ResponseBody
    public Map deleteMenuType(Integer menuTypeId) {
        Map<String, Object> mresult = new HashMap<>();
        String message;
        int count = menuTypeDao.getMenuTypeCountUsed(menuTypeId);

        if(count>0){
            message =  "nodelete";
        }else{
            message = menuTypeDao.deleteMenuTypeById(menuTypeId);
        }
        mresult.put("result",message);
        return mresult;
    }

}
