package org.david.rain.monitor.monitor.util;

import org.david.rain.monitor.dbutils.repository.impl.CommonDao;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 启动时把数据源放入MAP*
 */
@Component
@Lazy(false)
public class DataSourceContext implements InitializingBean {
    @Autowired
    DataSource event;
    @Autowired
    DataSource event_test;
    @Autowired
    CommonDao eventDao;
    @Autowired
    CommonDao eventTestDao;
    Map<String, DataSource> dataSourceMap;
    Map<String, CommonDao> daoMap;

    public DataSourceContext() {
    }

    public DataSource getDataSource(String dataSourceName) {
        return dataSourceMap.get(dataSourceName);
    }

    public CommonDao getCommonDao(String dataSourceName) {
        return daoMap.get(dataSourceName);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        dataSourceMap = new HashMap<>();
        dataSourceMap.put("event", event);
        dataSourceMap.put("event-test", event_test);
        daoMap = new HashMap<>();
        daoMap.put("event", eventDao);
        daoMap.put("event_test", eventTestDao);
    }
}
