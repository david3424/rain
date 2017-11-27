package com.noah.crm.cloud.common.event.dao;

import com.noah.crm.cloud.common.event.constant.ProcessStatus;
import com.noah.crm.cloud.common.event.domain.EventProcess;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by liubin on 2016/3/29.
 */
public interface EventProcessRepository extends PagingAndSortingRepository<EventProcess, Long>, EventProcessRepositoryCustom{

    List<EventProcess> findByStatus(ProcessStatus status);

    EventProcess getByEventId(Long eventId);


}
