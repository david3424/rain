package org.david.rain.common.util.memcached;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

/**
 * 调用memcache客户端工具类
 * <p/>
 * <p/>
 * 在使用该工具类功能下面配置class根目录下配置 memcached.properties
 * <p/>
 * <p/>
 * date: 2011-10-09
 *
 * @version v1.0
 */

@Configuration
@PropertySource("classpath:/common.properties")
public class MemcachedConfig {

    @Autowired
    Environment env;

    @Bean(name = "memcachedManager")
    @Scope
    public MemcachedManager memcachedManager() {
        return new MemcachedManager(env.getProperty("memcached.server_url"),
                env.getProperty("memcached.server_weight", Integer.class));
    }
}
