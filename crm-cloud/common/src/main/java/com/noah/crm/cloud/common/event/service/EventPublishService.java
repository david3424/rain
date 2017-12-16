package com.noah.crm.cloud.common.event.service;

import com.noah.crm.cloud.common.event.constant.ProcessStatus;
import com.noah.crm.cloud.common.event.dao.NotifyEventPublishRepository;
import com.noah.crm.cloud.common.event.domain.EventPublish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 */
@Service
public class EventPublishService {

    @Autowired
    protected NotifyEventPublishRepository notifyEventPublishRepository;



    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<EventPublish> findUnpublishedEvent() {
        List<EventPublish> unpublishedEvents = new ArrayList<>();
        unpublishedEvents.addAll(notifyEventPublishRepository.findByStatus(ProcessStatus.NEW));
        return unpublishedEvents;
    }

}
