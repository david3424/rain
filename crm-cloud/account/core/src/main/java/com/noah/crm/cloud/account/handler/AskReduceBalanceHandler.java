package com.noah.crm.cloud.account.handler;

import com.noah.crm.cloud.account.service.AccountService;
import com.noah.crm.cloud.apis.api.BooleanWrapper;
import com.noah.crm.cloud.apis.event.constants.FailureInfo;
import com.noah.crm.cloud.common.event.handler.RevokableAskEventHandler;
import com.noah.crm.cloud.common.spring.ApplicationContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
public class AskReduceBalanceHandler implements RevokableAskEventHandler<AskReduceBalance> {

    private static Logger logger = LoggerFactory.getLogger(AskReduceBalanceHandler.class);

    @Override
    public void processRevoke(AskReduceBalance originEvent, FailureInfo failureInfo) {
        logger.debug("AskReduceBalanceHandler processRevoke, receive AskReduceBalance: " + originEvent);

        AccountService accountService = ApplicationContextHolder.context.getBean(AccountService.class);
        accountService.addBalance(originEvent.getUserId(), originEvent.getBalance());
    }

    @Override
    public BooleanWrapper processRequest(AskReduceBalance event) {
        logger.debug("AskReduceBalanceHandler processRequest, receive AskReduceBalance: " + event);

        if(event.getUserId() == null || event.getBalance() == null) {
            return new BooleanWrapper(false, "userId or balance is null");
        }

        AccountService accountService = ApplicationContextHolder.context.getBean(AccountService.class);
        accountService.reduceBalance(event.getUserId(), event.getBalance());
        return new BooleanWrapper(true);
    }



}
