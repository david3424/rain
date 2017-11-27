package com.noah.crm.cloud.account.dao;

import com.noah.crm.cloud.account.domain.AccountFlow;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by liubin on 2016/4/26.
 */
public interface AccountFlowRepository extends PagingAndSortingRepository<AccountFlow, Long>, AccountFlowRepositoryCustom {
}
