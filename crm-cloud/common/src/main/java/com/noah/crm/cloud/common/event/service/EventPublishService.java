package com.noah.crm.cloud.common.event.service;

import com.noah.crm.cloud.common.event.constant.ProcessStatus;
import com.noah.crm.cloud.common.event.dao.AskRequestEventPublishRepository;
import com.noah.crm.cloud.common.event.dao.AskResponseEventPublishRepository;
import com.noah.crm.cloud.common.event.dao.NotifyEventPublishRepository;
import com.noah.crm.cloud.common.event.dao.RevokeAskEventPublishRepository;
import com.noah.crm.cloud.common.event.domain.AskRequestEventPublish;
import com.noah.crm.cloud.common.event.domain.EventPublish;
import com.noah.crm.cloud.common.exception.EventException;
import com.noah.crm.cloud.utils.CustomPreconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by liubin on 2016/6/13.
 */
@Service
public class EventPublishService {

    @Autowired
    protected NotifyEventPublishRepository notifyEventPublishRepository;

    @Autowired
    protected AskRequestEventPublishRepository askRequestEventPublishRepository;

    @Autowired
    protected RevokeAskEventPublishRepository revokeAskEventPublishRepository;

    @Autowired
    protected AskResponseEventPublishRepository askResponseEventPublishRepository;


    @Transactional(readOnly = true)
    public List<EventPublish> findUnpublishedEvent() {
        List<EventPublish> unpublishedEvents = new ArrayList<>();
        unpublishedEvents.addAll(notifyEventPublishRepository.findByStatus(ProcessStatus.NEW));
        unpublishedEvents.addAll(askRequestEventPublishRepository.findByStatus(ProcessStatus.NEW));
        unpublishedEvents.addAll(revokeAskEventPublishRepository.findByStatus(ProcessStatus.NEW));
        unpublishedEvents.addAll(askResponseEventPublishRepository.findByStatus(ProcessStatus.NEW));
        return unpublishedEvents;
    }

    @Transactional(readOnly = true)
    public AskRequestEventPublish getAskRequestEventByEventId(Long eventId) {
        AskRequestEventPublish askRequestEventPublish = askRequestEventPublishRepository.getByEventId(eventId);
        if(askRequestEventPublish == null) {
            throw new EventException(String.format("根据事件ID[%d]没有找到AskRequestEventPublish", eventId));
        }
        return askRequestEventPublish;
    }

    @Transactional(readOnly = true)
    public List<AskRequestEventPublish> findAskRequestEventByEventId(List<Long> eventIds) {
        CustomPreconditions.assertNotGreaterThanMaxQueryBatchSize(eventIds.size());
        Map<Long, AskRequestEventPublish> map = askRequestEventPublishRepository.findByEventIdIn(eventIds)
                .stream()
                .collect(Collectors.toMap(AskRequestEventPublish::getEventId, Function.identity()));

        Set<Long> eventNotExistIdSet = new HashSet<>();

        List<AskRequestEventPublish> askRequestEventPublishList = eventIds.stream().map(eventId -> {
            AskRequestEventPublish p = map.get(eventId);
            if (p == null) eventNotExistIdSet.add(eventId);
            return p;
        }).collect(Collectors.toList());

        if(!eventNotExistIdSet.isEmpty()) {
            throw new EventException(String.format("根据事件ID[%s]没有找到AskRequestEventPublish", eventNotExistIdSet));
        }

        return askRequestEventPublishList;
    }



}
