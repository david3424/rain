package com.noah.crm.cloud.user.service.gateway;

import com.noah.crm.cloud.account.api.dtos.AccountDto;
import org.springframework.stereotype.Component;

/**
 * @author xdw9486
 */
@Component
public class AccountClientHystrix {
    public AccountDto saveAccount(Long userId) {
        return new AccountDto();
    }
}
