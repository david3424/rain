package org.david.rain.security;

import org.david.rain.utils.Utils;
import org.david.rain.dao.UserDao;
import org.david.rain.model.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CustomUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {

	private static final Logger logger = LoggerFactory
			.getLogger(UsernamePasswordAuthenticationFilter.class);

	@Resource(name = "accountDao")
	private UserDao userDao;

	@Value("${SSOPassport}")
	private String SSOPassport;

	protected boolean continueChainBeforeSuccessfulAuthentication = false;

	protected SessionAuthenticationStrategy sessionStrategy = new NullAuthenticatedSessionStrategy();

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		Authentication authResult = null;
		try {
			if (Utils.getLoginUserDetail() != null) {
				chain.doFilter(request, response);
				return;
			}

			String ssoAccount = getUserNameByHeader(request, SSOPassport);

			if (StringUtils.isBlank(ssoAccount)) {
				throw new AuthenticationServiceException("您没有登陆，请使用单点登陆！");
			}

			logger.debug("sso account is {}", ssoAccount);

			authResult = attemptAuthentication(request, response);

			if (authResult == null) {
				// return immediately as subclass has indicated that it hasn't
				// completed authentication
				return;
			}

			sessionStrategy.onAuthentication(authResult, request, response);
		} catch (AuthenticationException failed) {
			// Authentication failed
			unsuccessfulAuthentication(request, response, failed);
			return;
		}

		// Authentication success
		if (continueChainBeforeSuccessfulAuthentication) {
			chain.doFilter(request, response);
		}

		successfulAuthentication(request, response, chain, authResult);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) {

		String ssoAccount = getUserNameByHeader(request, SSOPassport);

		List<User> users = userDao.getSecurityUserByAccount(ssoAccount);

		if (CollectionUtils.isEmpty(users)) {
			throw new AuthenticationServiceException(ssoAccount
					+ "您没有权限访问本系统，如果需要请联系管理员开通权限！");
		}

		String password = users.get(0).getPassword();

		// UsernamePasswordAuthenticationToken实现 Authentication
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				ssoAccount, password);
		// Place the last username attempted into HttpSession for views

		// 允许子类设置详细属性
		setDetails(request, authRequest);

		// 运行UserDetailsService的loadUserByUsername 再次封装Authentication
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	private String getUserNameByHeader(HttpServletRequest request, String ssoKey) {
		String userName = request.getHeader(ssoKey);
		if (StringUtils.isBlank(userName)) {
			userName = request.getParameter(ssoKey);
		}
		return StringUtils.isEmpty(userName)?"test1":userName;
	}

}
