package com.noah.crm.cloud.user.service.gateway;

import com.noah.crm.cloud.account.api.dtos.AccountDto;

/**
 * @author xdw9486
 */
public class AccountClientHystrix implements AccountClient {
    @Override
    public AccountDto saveAccount(Long userId) {
        return new AccountDto();
    }
}
