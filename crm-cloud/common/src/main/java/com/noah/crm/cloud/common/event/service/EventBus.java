package com.noah.crm.cloud.common.event.service;

import com.google.common.base.Stopwatch;
import com.noah.crm.cloud.apis.event.constants.EventType;
import com.noah.crm.cloud.apis.event.constants.FailureInfo;
import com.noah.crm.cloud.apis.event.constants.FailureReason;
import com.noah.crm.cloud.apis.event.domain.BaseEvent;
import com.noah.crm.cloud.apis.event.domain.NotifyEvent;
import com.noah.crm.cloud.apis.exception.ServiceException;
import com.noah.crm.cloud.common.event.EventRegistry;
import com.noah.crm.cloud.common.event.EventUtils;
import com.noah.crm.cloud.common.event.constant.AskEventStatus;
import com.noah.crm.cloud.common.event.constant.EventCategory;
import com.noah.crm.cloud.common.event.constant.ProcessStatus;
import com.noah.crm.cloud.common.event.dao.EventProcessRepository;
import com.noah.crm.cloud.common.event.dao.NotifyEventPublishRepository;
import com.noah.crm.cloud.common.event.domain.*;
import com.noah.crm.cloud.common.event.handler.NotifyEventHandler;
import com.noah.crm.cloud.common.exception.EventException;
import com.noah.crm.cloud.common.spring.ApplicationContextHolder;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author xdw9486
 */
@Service
public class EventBus {

    private static Logger logger = LoggerFactory.getLogger(EventBus.class);

    @Autowired
    protected NotifyEventPublishRepository notifyEventPublishRepository;

    @Autowired
    protected EventProcessRepository eventProcessRepository;

    @Autowired
    protected EventActivator eventActivator;

    @Autowired
    protected TaskExecutor taskExecutor;

    @Autowired
    protected EventRegistry eventRegistry;

    @Autowired
    protected EventWatchService eventWatchService;

    @Autowired
    protected EventPublishService eventPublishService;

    @Autowired
    protected EventHandlerExecutor eventHandlerExecutor;


    /**
     * 发布Notify事件
     *
     * @param notifyEvent
     * @return
     */
    @Transactional
    public NotifyEventPublish publish(NotifyEvent notifyEvent) {

        fillEventId(notifyEvent);
        String payload = EventUtils.serializeEvent(notifyEvent);

        NotifyEventPublish eventPublish = new NotifyEventPublish();
        eventPublish.setPayload(payload);
        eventPublish.setEventId(notifyEvent.getId());
        eventPublish.setEventType(notifyEvent.getType());

        notifyEventPublishRepository.save(eventPublish);
        return eventPublish;
    }






    /**
     * 发布未处理消息到队列
     */
    @Transactional(rollbackFor = Exception.class)
    public void sendUnpublishedEvent() {

        List<EventPublish> events = eventPublishService.findUnpublishedEvent();
        logger.info("【sendEvent】待发布事件数量: " + events.size());

        for (EventPublish event : events) {
            try {
                //eventActivator.sendMessage抛异常不会导致整个事务回滚
                if (eventActivator.sendMessage(event.getPayload(), event.getEventType().name())) {
                    event.setStatus(ProcessStatus.PROCESSED);
//                    更新状态
                    saveEventPublish(event);
                    logger.info("【sendEvent】更新事件{}-{}为process ", event.getEventType(), event.getEventId());
                }
            } catch (EventException e) {
                logger.error(e.getMessage());
            } catch (Exception e) {
                logger.error(String.format("发送消息到队列的时候发生异常, EventPublish[id=%d, payload=%s]",
                        event.getId(), event.getPayload()), e);
            }
        }
    }

    private void saveEventPublish(EventPublish eventPublish) {

        if (eventPublish instanceof NotifyEventPublish) {
            notifyEventPublishRepository.save((NotifyEventPublish) eventPublish);
            notifyEventPublishRepository.getEm().flush();
        } else {
            throw new EventException(String.format("unknown eventPublish class: %s, id: %d",
                    eventPublish.getClass(), eventPublish.getId()));
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void searchAndHandleUnprocessedEvent() {

        List<EventProcess> events = eventProcessRepository.findByStatus(ProcessStatus.NEW);
        logger.info("【handleEvent】待处理事件数量: " + events.size());
        CountDownLatch latch = new CountDownLatch(events.size());

        for (EventProcess event : events) {
            final Long eventProcessId = event.getId();
            taskExecutor.execute(() -> {
                try {
                    EventBus eventBus = ApplicationContextHolder.context.getBean(EventBus.class);
                    //handleEventProcess方法内报异常只回滚内部事务 map 处理ask response 类型回调
                    eventBus.handleEventProcess(eventProcessId)
                            .map(eventWatchProcess -> eventWatchService.addToQueue(eventWatchProcess));
                } catch (EventException e) {
                    logger.error(e.getMessage());
                } catch (Exception e) {
                    logger.error(String.format("处理事件的时候发生异常, EventProcess[id=%d]",
                            eventProcessId), e);
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            //等待事件异步处理完成
            latch.await();
        } catch (InterruptedException e) {
            logger.error("", e);
        }
        logger.info("【handleEvent】异步处理结束，回到主线程 ");
    }

    @Transactional
    public Optional<EventWatchProcess> handleEventProcess(Long eventProcessId) {

        Optional<EventWatchProcess> eventWatchProcessOptional = Optional.empty();

        EventProcess eventProcess = eventProcessRepository.findOne(eventProcessId);
        if (!eventProcess.getStatus().equals(ProcessStatus.NEW)) {
            //已经被处理过了, 忽略
            logger.warn("【handleEvent】已经被处理过{}", eventProcess);
            return eventWatchProcessOptional;
        }

        logger.debug(String.format("handle event process, id: %d, event category: %s ",
                eventProcessId, eventProcess.getEventCategory()));

        switch (eventProcess.getEventCategory()) {
            case NOTIFY:
                processNotifyEvent(eventProcess);
                break;
            default:
                throw new EventException(String.format("unknown event category, process id: %d, event category: %s ",
                        eventProcessId, eventProcess.getEventCategory()));
        }

        eventProcess.setStatus(ProcessStatus.PROCESSED);
        eventProcessRepository.save(eventProcess);

        return eventWatchProcessOptional;
    }


    /**
     * 事件处理
     *
     * @param event
     */
    private void processNotifyEvent(EventProcess event) {

        EventType type = event.getEventType();

        Set<NotifyEventHandler> eventHandlers = eventRegistry.getNotifyEventHandlers(type);
        if (eventHandlers == null || eventHandlers.isEmpty()) {
            logger.error(String.format("EventProcess[id=%d, type=%s, payload=%s]的eventHandlers列表为空'",
                    event.getId(), type, event.getPayload()));
            return;
        }

        NotifyEvent notifyEvent = (NotifyEvent) eventRegistry.deserializeEvent(type, event.getPayload());

        eventHandlers.forEach(
                handler -> executeEventHandler(
                        event.getId(),
                        () -> {
                            handler.notify(notifyEvent);
                            return null;
                        },
                        null));

    }

    private <T> EventHandlerResponse<T> executeEventHandler(Long eventProcessId, Supplier<T> supplier, T defaultValue) {

        T value = defaultValue;
        String errorMessage = null;
        Stopwatch stopwatch = null;
        try {
            if (logger.isDebugEnabled()) {
                stopwatch = Stopwatch.createStarted();
            }
            //开启新事务, 防止handler执行方法报错导致整体事务回滚
            value = eventHandlerExecutor.executeEventHandler(supplier);
        } catch (TransactionSystemException ignore) {

        } catch (ServiceException e) {
            errorMessage = e.getMessage();
        } catch (Exception e) {
            logger.error("", e);
            errorMessage = e.getMessage();
        } finally {
            if (logger.isDebugEnabled() && stopwatch != null) {
                stopwatch.stop();
                logger.debug(String.format("执行事件回调结束耗时%dms, EventProcess[id=%d]",
                        stopwatch.elapsed(TimeUnit.MILLISECONDS), eventProcessId));
            }
        }

        return new EventHandlerResponse<>(value, errorMessage);
    }




    @Transactional
    public EventProcess recordEvent(String message) {
        Map<String, Object> eventMap = EventUtils.retrieveEventMapFromJson(message);
        EventType eventType = EventType.valueOfIgnoreCase((String) eventMap.get("type"));
        EventCategory eventCategory = eventRegistry.getEventCategoryByType(eventType);
        if (logger.isDebugEnabled()) {
            logger.debug("receive message from kafka: {}", message);
        }

        EventProcess eventProcess = new EventProcess();
        eventProcess.setPayload(message);
        eventProcess.setEventId((Long) eventMap.get("id"));
        eventProcess.setEventType(eventType);
        eventProcess.setEventCategory(eventCategory);

        eventProcessRepository.save(eventProcess);
        return eventProcess;
    }

    /*//不在这里加事务注解, 因为想让这个方法内对service的调用都是独立事务.
    public void handleUnprocessedEventWatchProcess() {
        List<EventWatchProcess> eventWatchProcessList = eventWatchService.findUnprocessedEventWatchProcess();
        logger.info("待处理eventWatchProcess数量: " + eventWatchProcessList.size());
        Set<Long> successIdSet = new HashSet<>();
        Set<Long> watchIdSet = new HashSet<>();
        for (EventWatchProcess eventWatchProcess : eventWatchProcessList) {
            try {
                if (watchIdSet.add(eventWatchProcess.getWatchId())) {
                    //processUnitedEventWatch方法内报异常只回滚内部事务
                    eventWatchService.processUnitedEventWatch(eventWatchProcess);
                }
                successIdSet.add(eventWatchProcess.getId());
            } catch (EventException e) {
                logger.error(e.getMessage(), e);
                eventWatchService.addToQueue(eventWatchProcess);
                watchIdSet.remove(eventWatchProcess.getWatchId());
            } catch (Exception e) {
                logger.error("处理unitedEventWatch事件的时候发生异常, EventWatchProcessId:" + eventWatchProcess.getId(), e);
                eventWatchService.addToQueue(eventWatchProcess);
                watchIdSet.remove(eventWatchProcess.getWatchId());
            }
        }

        if (!successIdSet.isEmpty()) {
            eventWatchService.updateStatusBatchToProcessed(successIdSet.toArray(new Long[successIdSet.size()]));
        }
    }*/

   /* //不在这里加事务注解, 因为想让这个方法内对service的调用都是独立事务.
    public void handleTimeoutEventWatch() {
        LocalDateTime now = LocalDateTime.now();
        List<EventWatch> eventWatchList = eventWatchService.findTimeoutEventWatch(now);
        FailureInfo failureInfo = new FailureInfo(FailureReason.TIMEOUT, now);
        for (EventWatch eventWatch : eventWatchList) {
            try {
                eventWatchService.processEventWatch(eventWatch.getId(), AskEventStatus.TIMEOUT, failureInfo)
                        .map(eventWatchProcess -> eventWatchService.addToQueue(eventWatchProcess));
            } catch (EventException e) {
                logger.error(e.getMessage());
            } catch (Exception e) {
                logger.error(String.format("处理超时EventWatch的时候发生异常, id=%d",
                        eventWatch.getId()), e);
            }
        }
    }*/


    public Long generateEventId() {
        //TODO generate id
        return Long.parseLong(RandomStringUtils.randomNumeric(18));
    }

    public void fillEventId(BaseEvent baseEvent) {
        if (baseEvent.getId() != null) {
            throw new EventException("event id不为空, id:" + baseEvent.getId());
        }
        baseEvent.setId(generateEventId());

    }


    public void setEventActivator(EventActivator eventActivator) {
        this.eventActivator = eventActivator;
    }


    private static class EventHandlerResponse<T> {

        private T value;

        String errorMessage;

        public EventHandlerResponse(T value, String errorMessage) {
            this.value = value;
            this.errorMessage = errorMessage;
        }

        public T getValue() {
            return value;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
