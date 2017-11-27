package com.noah.crm.cloud.account.dao;

import com.noah.crm.cloud.account.domain.AccountFlow;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 */
public interface AccountFlowRepository extends PagingAndSortingRepository<AccountFlow, Long>, AccountFlowRepositoryCustom {
}
