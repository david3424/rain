package com.noah.crm.cloud.common.event.dao;


import com.noah.crm.cloud.common.dao.AbstractRepository;
import com.noah.crm.cloud.common.event.constant.ProcessStatus;

/**
 */
public interface EventWatchProcessRepositoryCustom extends AbstractRepository {

    int updateStatusBatch(Long[] ids, ProcessStatus status);

}
