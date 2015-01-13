package org.david.rain.dubbox.provider.redis;

import org.springframework.beans.factory.FactoryBean;
import org.springside.modules.nosql.redis.pool.JedisPool;
import org.springside.modules.nosql.redis.pool.JedisPoolBuilder;

/**
 * Created by david on 2015/1/12.
 * add spring support of jedis builder
 */
public class JedisFactoryBean implements FactoryBean<JedisPool> {


    private String url;
    private int threadCount;

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public JedisPool getObject() throws Exception {
        return new JedisPoolBuilder().setUrl(url + "?poolSize=" + threadCount).buildPool();
    }

    @Override
    public Class<? extends JedisPool> getObjectType() {
        return JedisPool.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
