package org.david.rain.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomAccessDecisionManager implements AccessDecisionManager {

	private static final Logger logger = LoggerFactory
			.getLogger(CustomFilterSecurityInterceptor.class);

	@Override
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		logger.debug("CustomAccessDecisionManager.decide(Authentication, Object, Collection<ConfigAttribute>) - start");
		logger.debug("正在访问的URL是：" + object.toString());

		if (configAttributes == null) {
			logger.debug("CustomAccessDecisionManager.decide(Authentication, Object, Collection<ConfigAttribute>) - end");
			return;
		}

//		// 获取上下文
//		SecurityContext securityContext = SecurityContextHolder.getContext();
//		// 获取认证对象
//		Authentication auth = securityContext.getAuthentication();
//		// 在认证对象中获得主体对象
//		Object principal = auth.getPrincipal();
//		String userName = "";
//		if (principal instanceof UserDetails) {
//			userName = ((UserDetails) principal).getUsername();
//		} else {
//			userName = principal.toString();
//		}

		Object principal = authentication.getPrincipal();
		if (principal instanceof UserDetails) {
			CustomUserDetails securityUser = (CustomUserDetails) principal;
			if (securityUser.isAccountIsAdmin()) return;
			
			if (!securityUser.isEnabled())
				throw new AccessDeniedException("帐号不可用！");
		}

		for (ConfigAttribute ca : configAttributes) {
			String needRole = ((SecurityConfig) ca).getAttribute();
			logger.debug("需要的Role是：" + needRole);
			// needRole 为访问相应的资源应该具有的权限
			// ga 为用户所被赋予的权限
			for (GrantedAuthority ga : authentication.getAuthorities()) {
				if (needRole.trim().equals(ga.getAuthority().trim())) {
					logger.debug("判断到需要的Role是：" + needRole + "，用户的角色是："
							+ ga.getAuthority() + "，授权数据相匹配");
					logger.debug("CustomAccessDecisionManager.decide(Authentication, Object, Collection<ConfigAttribute>) - end");
					return;
				}
			}
		}
		throw new AccessDeniedException("没有权限！");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
