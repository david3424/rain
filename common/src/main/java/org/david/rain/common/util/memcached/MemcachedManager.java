package org.david.rain.common.util.memcached;


import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by david on 13-11-18.
 *
 */
public class MemcachedManager {


    private String serverUrl;
    private int poolSize;
    MemcachedClient mcc = null;


    MemcachedManager(String serverUrl,int poolSize) {
        this.serverUrl = serverUrl;
        this.poolSize = poolSize;
        init();

    }

    private void init() {
        MemcachedClientBuilder builder = new XMemcachedClientBuilder(
                AddrUtil.getAddresses(serverUrl));
        try {
            builder.setCommandFactory(new BinaryCommandFactory());
            builder.setConnectionPoolSize(poolSize);
            mcc = builder.build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert mcc != null;
        try {
            mcc.flushAll();
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            e.printStackTrace();
        }
    }



    //工具方法

    /**
     * @param key   key值
     * @param value value值
     * @return true:添加成功 ; false：添加失败（如果存在相同的key则返回false）
     */
    public boolean add(String key, Object value) {
        return add(key, value, Integer.MAX_VALUE);
    }

    /**
     * @param key    key值
     * @param value  value值
     * @param expiry 过期时间，最小计算到1秒钟，（设置过期时间：）
     * @return true:添加成功 ; false：添加失败（如果存在相同的key则返回false）
     */
    public boolean add(String key, Object value, Integer expiry) {
        try {
            return mcc.add(key, expiry, value);
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key   key值
     * @param value value值
     * @return true:添加成功 ; false：添加失败（如果存在相同的key则进行替换）
     */
    public boolean set(String key, Object value) {
        return set(key, value,Integer.MAX_VALUE);
    }

    /**
     * @param key    key值
     * @param value  value值
     * @param expiry 过期时间，最小计算到1秒钟，（设置过期时间：）
     * @return true:添加成功 ; false：添加失败（如果存在相同的key则进行替换）
     */
    public boolean set(String key, Object value, Integer expiry) {
        try {
            return mcc.set(key,expiry, value);
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key key值
     * @return 获取key对应的value值，如果不存在则返回NULL;
     */
    public Object get(String key) {
        try {
            return mcc.get(key);
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param keys key值数组
     * @return keys对应的map
     */
    public Map<String, Object> getMulti(List keys) {
        try {
            return mcc.get(keys);
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param key   key值
     * @param value value值
     * @return 寻找mem里key对应的值，找到并成功替换则返回true，不存在或替换失败则返回false
     */
    public boolean replace(String key, Object value) {
        return replace(key,value,10);
    }

    /**
     * @param key    key值
     * @param value  value值
     * @param expiry 过期时间，最小计算到1秒钟，（设置过期时间：）
     * @return 寻找mem里key对应的值，找到并成功替换则返回true，不存在或替换失败则返回false
     */
    public boolean replace(String key, Object value, Integer expiry) {
        try {
            return mcc.replace(key, expiry, value);
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 清除所有缓存
     *
     * @return
     */
    public boolean flushAll() {
        try {
            mcc.flushAll();
            return  true;
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * *
     * 查看缓存状态
     *
     * @return
     */
    public Map<InetSocketAddress, Map<String, String>> stats() {
        try {
            return mcc.getStats();
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * **
     * 删除缓存
     *
     * @param key
     * @return
     */
    public boolean delete(String key) {
        try {
            return mcc.delete(key);
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            e.printStackTrace();
            return false;
        }
        }
}
