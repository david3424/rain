package com.noah.crm.cloud.common.event.dao;

import com.noah.crm.cloud.common.event.constant.AskEventStatus;
import com.noah.crm.cloud.common.event.domain.EventWatch;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 */
public interface EventWatchRepository extends PagingAndSortingRepository<EventWatch, Long>, EventWatchRepositoryCustom{


    List<EventWatch> findByAskEventStatusAndTimeoutTimeBefore(AskEventStatus askEventStatus, LocalDateTime timeoutTime);

}
