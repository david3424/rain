package org.david.rain.monitor.monitor.shiro;

import org.david.rain.monitor.exception.DecoderException;
import org.david.rain.monitor.monitor.domain.Role;
import org.david.rain.monitor.monitor.domain.User;
import org.david.rain.monitor.monitor.service.UserService;
import org.david.rain.monitor.monitor.util.AuthCodeUtil;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;

/**
 * Created by david on 14-2-23.
 * work for myself
 */

@Service
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
       /* String username = (String) principals.fromRealm(getName()).iterator().next();
        if (username != null) {
            User user = userService.getUserByName(username);
            if (user != null && user.getRoles() != null) {
                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                *//*for( SecurityRole each: user.getRoles() ){
                        info.addRole(each.getName());
                        info.addStringPermissions(each.getPermissionsAsString());
                }*//*
                info.addRoles(user.getRoleList());
                return info;
            }
        }
        return null;*/
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        User user = userService.selectUserRoles(shiroUser.id);

        		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        		for (Role role : user.getRoleList()) {
        			// 基于Role的权限信息
        			info.addRole(role.getName());
        			// 基于Permission的权限信息
        			info.addStringPermissions(role.getPermissionList());
        		}
        		return info;
    }

    /**
     * 认证回调函数,登录时调用.
     */
    protected AuthenticationInfo doGetAuthenticationInfo (  AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        User user = null; //认证
        try {
            user = userService.getUserByName(token.getUsername());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if (user != null) {
            byte[] salt = new byte[0];
            try {
                salt = AuthCodeUtil.decodeHex(user.getSalt().toCharArray());
            } catch (DecoderException e) {
                e.printStackTrace();
            }
            return new SimpleAuthenticationInfo(new ShiroUser(user.getId(), user.getUsername(), user.getChName()),
                    user.getPassword(), ByteSource.Util.bytes(salt), getName());
        } else {
            return null;
        }
    }

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(UserService.HASH_ALGORITHM);
        matcher.setHashIterations(UserService.HASH_INTERATIONS);

        setCredentialsMatcher(matcher);
    }

    /**
     * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
     */
    public static class ShiroUser implements Serializable {
        private static final long serialVersionUID = -1373760761780840081L;
        public Integer id;
        public String loginName;
        public String name;

        public ShiroUser(Integer id, String loginName, String name) {
            this.id = id;
            this.loginName = loginName;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        /**
         * 本函数输出将作为默认的<shiro:principal/>输出.
         */
        @Override
        public String toString() {
            return loginName;
        }

        /**
         * 重载equals,只计算loginName;
         */
        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this, "loginName");
        }

        /**
         * 重载equals,只比较loginName
         */
        @Override
        public boolean equals(Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj, "loginName");
        }
    }

}
