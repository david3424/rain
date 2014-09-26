package org.david.rain.security;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 602183013020253160L;
	// 用户ID
	protected Integer userId;
	// 账号
	protected String account;
	// 用户名
	protected String username;
	// 密码
	protected String password;
	// 是否可用
	protected boolean enabled;
	// 用户权限集合
	protected Collection<? extends GrantedAuthority> authorties;
	// 账号是否为管理员
	protected boolean accountIsAdmin;
	// 账号是否过期
	protected boolean accountNonExpired;
	// 账号是否被锁
	protected boolean accountNonLocked;
	// 密码是否过期
	protected boolean credentialsNonExpired;
	// 所属联运商
	protected String agent;

	public CustomUserDetails(Integer userId, String account, String username,
			String password, boolean enabled,
			Collection<? extends GrantedAuthority> authorties,
			boolean accountIsAdmin, String agent) {
		this(userId, account, username, password, enabled, authorties,
				accountIsAdmin, true, true, true, agent);
	}

	public CustomUserDetails(Integer userId, String account, String username,
			String password, boolean enabled,
			Collection<? extends GrantedAuthority> authorties,
			boolean accountIsAdmin, boolean accountNonExpired,
			boolean accountNonLocked, boolean credentialsNonExpired, String agent) {
		if (StringUtils.isBlank(account)) {
			throw new IllegalArgumentException("不允许用户帐号为空！");
		}
		this.userId = userId;
		this.account = account;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.authorties = authorties;
		this.accountIsAdmin = accountIsAdmin;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.agent = agent;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getAccount() {
		return this.account;
	}

	public boolean isAccountIsAdmin() {
		return this.accountIsAdmin;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorties;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

}
