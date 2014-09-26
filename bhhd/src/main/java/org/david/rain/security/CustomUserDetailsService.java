package org.david.rain.security;

import org.apache.commons.lang.StringUtils;
import org.david.rain.dao.UserDao;
import org.david.rain.model.User;
import org.david.rain.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory
            .getLogger(CustomUserDetailsService.class);

    @Resource(name = "accountDao")
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String account)
            throws UsernameNotFoundException {
        logger.debug(
                "CustomUserDetailsService.loadUserByUsername is start and account is {}",
                account);

        List<User> users = userDao.getSecurityUserByAccount(account);
        if (CollectionUtils.isEmpty(users)) {
            String message = account + "不存在此用户，如有需要请联系管理员开通!!";
            logger.error(message);
            throw new UsernameNotFoundException(message);
        }
        User user = users.get(0);

        List<String> roleNames = userDao.getSecurityRolesByAccount(account);
        if (roleNames == null) {
            roleNames = new ArrayList<String>();
        }

        Collection<GrantedAuthority> authorties = new ArrayList<GrantedAuthority>();
        for (String roleName : roleNames) {
            if (StringUtils.isBlank(roleName))
                continue;
            authorties.add(new SimpleGrantedAuthority(roleName));
        }

        CustomUserDetails securityUser = new CustomUserDetails(
                user.getUserId(), account, user.getUserName(),
                user.getPassword(), isEnabled(user.getStatus()), authorties,
                isAdmin(user.getAccountType()), user.getAgent());

        return securityUser;
    }

    private boolean isAdmin(Integer accountType) {
       return accountType == Constants.IS_ADMIN;
    }

    private boolean isEnabled(Integer userStatus) {
       return userStatus == Constants.STATUS_YES;
    }

}
