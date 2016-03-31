package org.david.rain.monitor.monitor.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    Map<String, DataSource> dataSourceMap;

    public DataSourceContext() {
    }

    public DataSource getDataSource(String dataSourceName) {
        return dataSourceMap.get(dataSourceName);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        dataSourceMap = new HashMap<>();
        dataSourceMap.put("event", event);
    }
}
