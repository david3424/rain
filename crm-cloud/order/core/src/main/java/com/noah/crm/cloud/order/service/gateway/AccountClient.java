package com.noah.crm.cloud.order.service.gateway;

import com.noah.crm.cloud.account.api.constants.AccountUrl;
import com.noah.crm.cloud.apis.api.BooleanWrapper;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xdw9486
 */
@FeignClient(AccountUrl.SERVICE_NAME)
public interface AccountClient {

    @RequestMapping(method = RequestMethod.GET, value = AccountUrl.CHECK_ENOUGH_BALANCE)
    BooleanWrapper checkEnoughBalance(@PathVariable("userId") Long userId, @RequestParam("balance") Long balance);

}
