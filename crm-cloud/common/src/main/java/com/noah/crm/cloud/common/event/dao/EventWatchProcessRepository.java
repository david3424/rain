package com.noah.crm.cloud.common.event.dao;

import com.noah.crm.cloud.common.event.constant.ProcessStatus;
import com.noah.crm.cloud.common.event.domain.EventWatchProcess;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 */
public interface EventWatchProcessRepository extends PagingAndSortingRepository<EventWatchProcess, Long>,
        EventWatchProcessRepositoryCustom{

    List<EventWatchProcess> findByStatus(ProcessStatus status);
}
