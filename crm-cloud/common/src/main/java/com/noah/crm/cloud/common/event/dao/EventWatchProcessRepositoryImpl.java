package com.noah.crm.cloud.common.event.dao;


import com.noah.crm.cloud.common.event.constant.ProcessStatus;
import com.noah.crm.cloud.utils.database.SQLUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 */
public class EventWatchProcessRepositoryImpl implements EventWatchProcessRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public EntityManager getEm() {
        return em;
    }


    @Override
    public int updateStatusBatch(Long[] ids, ProcessStatus status) {

        return SQLUtils.updateByIdBatch(ids, (updateIds) -> {
            StringBuilder sql = new StringBuilder(
                    String.format("update event_watch_process set status ='%s' where id in (", status.toString()));
            for (Long id : updateIds) {
                sql.append(id).append(",");
            }
            sql.replace(sql.length()-1, sql.length(), ")");
            return getEm().createNativeQuery(sql.toString()).executeUpdate();
        });
    }


}
