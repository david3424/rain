package com.noah.crm.cloud.account.dao;

import com.noah.crm.cloud.account.domain.Account;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 */
public interface AccountRepository extends PagingAndSortingRepository<Account, Long>, AccountRepositoryCustom {

    Account findByUserId(Long userId);

}
