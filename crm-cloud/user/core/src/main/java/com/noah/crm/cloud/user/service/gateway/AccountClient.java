package com.noah.crm.cloud.user.service.gateway;

import com.noah.crm.cloud.account.api.constants.AccountUrl;
import com.noah.crm.cloud.account.api.dtos.AccountDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.noah.crm.cloud.account.api.constants.AccountUrl.ACCOUNT_CREATE;

/**
 * @author xdw9486
 */
@FeignClient(value = AccountUrl.SERVICE_NAME, fallback = AccountClientHystrix.class)
public interface AccountClient {

    @RequestMapping(value = ACCOUNT_CREATE, method = RequestMethod.POST)
    AccountDto saveAccount(Long userId);

}
