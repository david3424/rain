package com.noah.crm.cloud.account.web;

import com.noah.crm.cloud.account.api.dtos.AccountDto;
import com.noah.crm.cloud.account.domain.Account;
import com.noah.crm.cloud.account.service.AccountService;
import com.noah.crm.cloud.apis.api.ApisErrorCode;
import com.noah.crm.cloud.apis.api.BooleanWrapper;
import com.noah.crm.cloud.apis.exception.ServiceException;
import com.noah.crm.cloud.utils.logback.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.noah.crm.cloud.account.api.constants.AccountUrl.*;

/**
 * @author xdw9486
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    @Autowired
    AccountService accountService;

    @RequestMapping(value = CHECK_ENOUGH_BALANCE, method = RequestMethod.GET)
    public BooleanWrapper checkAccountBalanceEnough(@PathVariable("userId") Long userId,
                                                    @RequestParam("balance") Long balance) {
        boolean result = accountService.checkEnoughBalance(userId, balance);
        return new BooleanWrapper(result);
    }

    @RequestMapping(value = ACCOUNT_BALANCE, method = RequestMethod.GET)
    public Long accountBalance(@PathVariable(value = "userId") Long userId) {

        return accountService.getByUserId(userId).getBalance();
    }

    @RequestMapping(value = ACCOUNT_TRANSACTIONS, method = RequestMethod.POST)
    public Long operateAccountBalance(
            @PathVariable(value = "userId") Long userId,
            @RequestParam(value = "amount") Long amount) {

        if (amount >= 0L) {
            return accountService.addBalance(userId, amount);
        } else {
            return accountService.reduceBalance(userId, Math.abs(amount));
        }
    }

    @RequestMapping(value = ACCOUNT_CREATE, method = RequestMethod.POST)
    public AccountDto saveAccount(Long userId) {
        try {
            Account account = accountService.initAccount(userId);
            AccountDto accountDto = new AccountDto();
            accountDto.setUserId(userId);
            accountDto.setBalance(account.getBalance());
            accountDto.setId(account.getId());
            return accountDto;
        } catch (DataIntegrityViolationException e) {
            LoggerUtil.warn(String.format("userId=%d的account在数据库已存在, errorMsg: %s", userId, e.getMessage()));
            throw new ServiceException(ApisErrorCode.DATA_ERROR_S, e.getMessage());
        }
    }
}
