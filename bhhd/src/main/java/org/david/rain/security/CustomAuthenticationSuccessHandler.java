package org.david.rain.security;

import org.david.rain.security.tool.SessionUser;
import org.david.rain.dao.GameDao;
import org.david.rain.dao.RoleDao;
import org.david.rain.dao.RoleGameMappingDao;
import org.david.rain.model.GameBean;
import org.david.rain.model.Menu;
import org.david.rain.model.RolePermissionMapping;
import org.david.rain.service.GeneralService;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

class CustomAuthenticationSuccessHandler extends
		SavedRequestAwareAuthenticationSuccessHandler {

	private boolean verifyIpFlg = false;

	private static final String URL_SPRING_KEY = "/wanmei/";

	private static final String SYSTEM_URL = "system";

	@Resource(name = "generalCollectionService")
	private GeneralService generalCollectionService;

	@Resource(name = "gameDao")
	private GameDao gameDao;

	@Resource(name = "roleDetailDao")
	private RoleDao roleDetailDao;

	@Resource(name = "roleGameMappingDao")
	private RoleGameMappingDao roleGameMappingDao;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		
		if (verifyIpFlg) {
			generalCollectionService.insertUserLoginLog("登录成功！");
		}

		HttpSession httpSession = request.getSession(true);
		SessionUser sessionUser = (SessionUser) httpSession
				.getAttribute(SessionUser.SESSION_ROOT_KEY);
		if (sessionUser == null) {
			sessionUser = new SessionUser();
		}

		Map<String, GameBean> gameMap = getGameMap(gameDao.getGameList());

		Map<String, Set<String>> authorityMap = getAuthorityMap(roleDetailDao
				.getRolePermissionMapping());

		String authorityRole = authentication.getAuthorities().iterator()
				.next().getAuthority();
		List<Menu> userMenuList = roleDetailDao
				.getMenuListByRoleCode(authorityRole);
		Map<Integer, Map<String, List<Menu>>> userMenuMap = getUserMenuMap(userMenuList);

		List<String> authorityGameList = getAuthorityGameList(authorityRole);

		sessionUser.setGameMap(gameMap);
		sessionUser.setAuthorityMap(authorityMap);
		sessionUser.setUserMenuMap(userMenuMap);
		sessionUser.setAuthorityGameList(authorityGameList);

		String url = request.getServletPath() + request.getPathInfo();
		if (url.startsWith(URL_SPRING_KEY)) {
			int begin = URL_SPRING_KEY.length();
			int end = url.indexOf("/", begin);
			if (end < 0) {
				end = url.length();
			}
			String gameShort = url.substring(begin, end);
			if (!StringUtils.isBlank(gameShort)
					&& !gameShort.equalsIgnoreCase(SYSTEM_URL)) {
				if (gameMap != null && gameMap.containsKey(gameShort)) {
					httpSession.setAttribute(
							SessionUser.SESSION_CURRENT_GAME_SHORT, gameShort);
				}
			}
		}

		httpSession.setAttribute(SessionUser.SESSION_ROOT_KEY, sessionUser);

		super.onAuthenticationSuccess(request, response, authentication);
	}

	private List<String> getAuthorityGameList(String authorityRole) {
		List<String> authorityGameList = roleGameMappingDao.getGameListByRoleCode(authorityRole);
		if (CollectionUtils.isEmpty(authorityGameList)) {
			authorityGameList = new ArrayList<String>();
		}

		return authorityGameList;
	}

	private Map<String, GameBean> getGameMap(List<GameBean> gameList) {
		Map<String, GameBean> gameMap = new HashMap<String, GameBean>();

		if (!CollectionUtils.isEmpty(gameList)) {
			for (GameBean game : gameList) {
				gameMap.put(game.getShortName(), game);
			}
		}
		return gameMap;
	}

	private Map<String, Set<String>> getAuthorityMap(
			List<RolePermissionMapping> rolePermissionMapping) {
		Map<String, Set<String>> authorityMap = new HashMap<String, Set<String>>();

		if (!CollectionUtils.isEmpty(rolePermissionMapping)) {
			String resourceUrl = null;
			String roleCode = null;
			Set<String> roleSet = null;
			for (RolePermissionMapping mapping : rolePermissionMapping) {
				resourceUrl = mapping.getResourceUrl();
				roleCode = mapping.getRoleCode();
				if (authorityMap.containsKey(resourceUrl)) {
					roleSet = authorityMap.get(resourceUrl);
					roleSet.add(roleCode);
				} else {
					roleSet = new HashSet<String>();
					roleSet.add(roleCode);
					authorityMap.put(resourceUrl, roleSet);
				}
			}
		}

		return authorityMap;
	}

	private Map<Integer, Map<String, List<Menu>>> getUserMenuMap(
			List<Menu> menuList) {
		Map<Integer, Map<String, List<Menu>>> userMenuMap = new HashMap<Integer, Map<String, List<Menu>>>();

		if (!CollectionUtils.isEmpty(menuList)) {
			Integer gameId = null;
			String menuTypeName = null;
			Map<String, List<Menu>> menuEntityMap = null;
			List<Menu> menuEntityList = null;
			for (Menu menu : menuList) {
				gameId = menu.getGameId();
				menuTypeName = menu.getMenuTypeName();
				if (userMenuMap.containsKey(gameId)) {
					menuEntityMap = userMenuMap.get(gameId);

					if (menuEntityMap.containsKey(menuTypeName)) {
						menuEntityList = menuEntityMap.get(menuTypeName);
						menuEntityList.add(menu);
					} else {
						menuEntityList = new ArrayList<Menu>();
						menuEntityList.add(menu);
						menuEntityMap.put(menuTypeName, menuEntityList);
					}
				} else {
					menuEntityMap = new LinkedHashMap<String, List<Menu>>();
					menuEntityList = new ArrayList<Menu>();
					menuEntityList.add(menu);
					menuEntityMap.put(menuTypeName, menuEntityList);
					userMenuMap.put(gameId, menuEntityMap);
				}
			}
		}

		return userMenuMap;
	}

	public boolean isVerifyIpFlg() {
		return verifyIpFlg;
	}

	public void setVerifyIpFlg(boolean verifyIpFlg) {
		this.verifyIpFlg = verifyIpFlg;
	}

}
