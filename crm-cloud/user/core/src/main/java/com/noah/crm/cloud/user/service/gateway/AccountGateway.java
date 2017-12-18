package com.noah.crm.cloud.user.service.gateway;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.noah.crm.cloud.account.api.dtos.AccountDto;
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

    @Autowired
    AccountClient accountClient;

    @HystrixCommand(ignoreExceptions = RemoteCallException.class)
    public AccountDto saveAccount(Long userId) {
        return accountClient.saveAccount(userId);
    }

}

