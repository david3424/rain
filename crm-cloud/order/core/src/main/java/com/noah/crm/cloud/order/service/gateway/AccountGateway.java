package com.noah.crm.cloud.order.service.gateway;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.noah.crm.cloud.apis.exception.RemoteCallException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xdw9486
 */
@Service
public class AccountGateway {

    protected Logger logger = LoggerFactory.getLogger(AccountGateway.class);

    @Autowired
    AccountClient accountClient;

    @HystrixCommand(ignoreExceptions = RemoteCallException.class)
    public boolean isBalanceEnough(Long userId, Long amount) {
        return accountClient.checkEnoughBalance(userId, amount).isSuccess();
    }

}

