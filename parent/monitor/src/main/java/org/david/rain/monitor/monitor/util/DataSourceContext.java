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
 * Created by czw on 14-2-27.
 */
@Component
@Lazy(false)
public class DataSourceContext implements InitializingBean {
    @Autowired
    @Qualifier("huodong218")
    DataSource huodong218;
    @Autowired
    @Qualifier("huodong226")
    DataSource huodong226;
    @Autowired
    @Qualifier("huodong164")
    DataSource huodong164;
    @Autowired
    @Qualifier("huodong108")
    DataSource huodong108;
    @Autowired
    @Qualifier("event")
    DataSource event;
    @Autowired
    @Qualifier("hdbase")
    DataSource hdbase;
    @Autowired
    @Qualifier("img")
    DataSource img;


    Map<String, DataSource> dataSourceMap;

    public DataSourceContext() {
    }

    public DataSource getDataSource(String dataSourceName) {
        return dataSourceMap.get(dataSourceName);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        dataSourceMap = new HashMap<>();
        dataSourceMap.put("huodong218", huodong218);
        dataSourceMap.put("huodong226", huodong226);
        dataSourceMap.put("huodong164", huodong164);
        dataSourceMap.put("huodong108", huodong108);
        dataSourceMap.put("event", event);
        dataSourceMap.put("hdbase", hdbase);
        dataSourceMap.put("img", img);
    }
}
