package com.noah.crm.cloud.common.event.dao;


import com.noah.crm.cloud.common.event.domain.AskResponseEventPublish;

/**
 * Created by liubin on 2016/3/29.
 */
public interface AskResponseEventPublishRepository extends
        EventPublishRepository<AskResponseEventPublish>, AskResponseEventPublishRepositoryCustom {

    Long countByAskEventId(Long askEventId);

    AskResponseEventPublish getByAskEventId(Long askEventId);
}
