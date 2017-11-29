package com.noah.crm.cloud.common.event.dao;

import com.noah.crm.cloud.common.event.constant.ProcessStatus;
import com.noah.crm.cloud.common.event.domain.EventPublish;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author xdw9486
 */
//@NoRepositoryBean
public interface EventPublishRepository<T extends EventPublish> extends PagingAndSortingRepository<T, Long> {

    List<T> findByStatus(ProcessStatus status);

    T getByEventId(Long eventId);

    List<T> findByEventIdIn(List<Long> eventIds);

}
