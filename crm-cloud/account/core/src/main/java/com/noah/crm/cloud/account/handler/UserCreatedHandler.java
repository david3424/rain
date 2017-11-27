package com.noah.crm.cloud.account.handler;

import com.noah.crm.cloud.account.service.AccountService;
import com.noah.crm.cloud.common.event.handler.NotifyEventHandler;
import com.noah.crm.cloud.common.spring.ApplicationContextHolder;
import com.noah.crm.cloud.user.api.events.UserCreated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author xdw9486
 */
public class UserCreatedHandler implements NotifyEventHandler<UserCreated> {

    protected Logger logger = LoggerFactory.getLogger(UserCreatedHandler.class);

    @Override
    public void notify(UserCreated event) {

        AccountService accountService = ApplicationContextHolder.context.getBean(AccountService.class);

        try {
            accountService.initAccount(event.getUserId());
        } catch (DataIntegrityViolationException e) {
            logger.warn(String.format("userId=%d的account在数据库已存在, errorMsg: %s",
                    event.getUserId(), e.getMessage()));
        }
    }
}
