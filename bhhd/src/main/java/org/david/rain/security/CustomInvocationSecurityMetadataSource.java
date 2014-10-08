package org.david.rain.security;

import org.david.rain.security.tool.AntUrlPathMatcher;
import org.david.rain.security.tool.SessionUser;
import org.david.rain.security.tool.UrlMatcher;
import org.david.rain.dao.RoleDao;
import org.david.rain.model.GameBean;
import org.david.rain.model.RolePermissionMapping;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

public class CustomInvocationSecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {

	private static final Logger logger = LoggerFactory
			.getLogger(CustomInvocationSecurityMetadataSource.class);

	private static final String SYSTEM_URL = "system";

	private static final String URL_SPRING_KEY = "/wanmei/";

	private static final String BIND_PERMISSIONS_URL = URL_SPRING_KEY + "system/bind_permissions";

	private static final String UNBIND_PERMISSIONS_URL = URL_SPRING_KEY + "system/unbind_permissions";

	private static final String BIND_ROLE_GAME_URL = URL_SPRING_KEY + "system/bind_role_game";

	private static final String UNBIND_ROLE_GAME_URL = URL_SPRING_KEY + "system/unbind_role_game";

	 @Autowired
	 private HttpSession session;

	@Resource(name = "roleDetailDao")
	private RoleDao roleDetailDao;

	private UrlMatcher urlMatcher = new AntUrlPathMatcher();
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null; // url与角色集合
	private static boolean reloadResourceMapFlg = false;

	public CustomInvocationSecurityMetadataSource() {
		// loadResourceDefine();
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	// 根据URL，找到相关的权限配置
	@Override
	public Collection<ConfigAttribute> getAttributes(Object param)
			throws IllegalArgumentException {
		String url = ((FilterInvocation) param).getRequestUrl();
		logger.debug("request url is {}", url);
		int paramFlg = url.indexOf("?");

		if (paramFlg != -1) {
			url = url.substring(0, paramFlg);
		}

		if (reloadResourceMapFlg) {
			loadResourceDefine();
		}

		// TODO 硬编码 绑定解除权限后刷新权限
		if (BIND_PERMISSIONS_URL.equalsIgnoreCase(url)
				|| UNBIND_PERMISSIONS_URL.equalsIgnoreCase(url)
				|| BIND_ROLE_GAME_URL.equalsIgnoreCase(url)
				|| UNBIND_ROLE_GAME_URL.equalsIgnoreCase(url)) {
			reloadResourceMapFlg = true;
		} else {
			reloadResourceMapFlg = false;
		}

		// TODO 解析URL中的gamename放入session
		if (url.startsWith(URL_SPRING_KEY)) {
			int begin = URL_SPRING_KEY.length();
			int end = url.indexOf("/", begin);
			if (end < 0) {
				end = url.length();
			}
			String gameShort = url.substring(begin, end);

			if (!StringUtils.isBlank(gameShort)
					&& !gameShort.equalsIgnoreCase(SYSTEM_URL)) {
				SessionUser sessionUser = (SessionUser) session
						.getAttribute(SessionUser.SESSION_ROOT_KEY);
				if (sessionUser != null) {
					Map<String, GameBean> gameMap = sessionUser.getGameMap();
					if (gameMap != null && gameMap.containsKey(gameShort)) {
						session.setAttribute(
								SessionUser.SESSION_CURRENT_GAME_SHORT, gameShort);
					}
				}
			}
		}

		for (String resourceUrl : resourceMap.keySet()) {
			if (urlMatcher.pathMathesUrl(url, resourceUrl)) {
				return resourceMap.get(resourceUrl);
			}
		}

		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	public void loadResourceDefine() {
		resourceMap = new HashMap<>();
		List<RolePermissionMapping> rolePermissionMapping = roleDetailDao
				.getRolePermissionMapping();

		String url = null;
		String roleCode = null;
		ConfigAttribute configAttribute = null;
		Collection<ConfigAttribute> configAttributes = null;
		for (RolePermissionMapping rolePermission : rolePermissionMapping) {
			url = rolePermission.getResourceUrl();
			roleCode = rolePermission.getRoleCode();
			configAttribute = new SecurityConfig(roleCode);
			if (resourceMap.containsKey(url)) {
				resourceMap.get(url).add(configAttribute);
			} else {
				configAttributes = new ArrayList<ConfigAttribute>();
				configAttributes.add(configAttribute);
				resourceMap.put(url, configAttributes);
			}
		}
	}

}
