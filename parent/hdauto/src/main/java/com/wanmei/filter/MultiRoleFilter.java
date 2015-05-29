package com.david.web.wanmei.init.servlet.filter;

import com.david.web.wanmei.common.SessionConst;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: czw
 * Date: 13-10-24
 * Time: 上午10:58
 * To change this template use File | Settings | File Templates.
 */
public class MultiRoleFilter extends AuthorizationFilter {
    Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);


    @SuppressWarnings({"unchecked"})
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {

        Subject subject = getSubject(request, response);
        Object session = subject.getSession().getAttribute(SessionConst.LOGIN_SESSION_NAME);
        logger.info("session:" + (session == null ? "" : session.toString()));

        String[] rolesArray = (String[]) mappedValue;

        if (rolesArray == null || rolesArray.length == 0) {
            //no roles specified, so nothing to check - allow access.
            return true;
        }

        Set<String> roles = CollectionUtils.asSet(rolesArray);
        Iterator<String> rolesItr = roles.iterator();
        while (rolesItr.hasNext()) {
            String role = rolesItr.next();
            logger.info("in while:" + role);
            if (subject.hasRole(role)) {
                return true;
            }
        }
        return false;
    }
}
