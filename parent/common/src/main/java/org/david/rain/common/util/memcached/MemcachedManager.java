package org.david.rain.common.util.memcached;


import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

import java.util.Date;
import java.util.Map;

/**
 */
public class MemcachedManager {


    private String serverUrl;

    private Integer weight;

    private Integer initConnNum;

    private Integer minConnNum;

    private Integer maxConnNum;


    /**
     * 加这个可恶的方法是为了idea不红着
     */
    MemcachedManager() {

    }

    MemcachedManager(String serverUrl, Integer weight, Integer initConnNum, Integer minConnNum, Integer maxConnNum) {
        this.serverUrl = serverUrl;
        this.weight = weight;
        this.initConnNum = initConnNum;
        this.maxConnNum = maxConnNum;
        this.minConnNum = minConnNum;
        init();

    }

    private void init() {

        String[] servers = {serverUrl};
        Integer[] weights = {weight};

        // 获取socke连接池的实例对象
        SockIOPool pool = SockIOPool.getInstance();

        // 设置服务器信息
        pool.setServers(servers);
        pool.setWeights(weights);

        // 设置初始连接数、最小和最大连接数以及最大处理时间
        pool.setInitConn(initConnNum);
        pool.setMinConn(minConnNum);
        pool.setMaxConn(maxConnNum);
        pool.setMaxIdle(1000 * 60 * 60 * 6);
        // 设置主线程的睡眠时间
        pool.setMaintSleep(30);
        // 设置TCP的参数，连接超时等
        pool.setNagle(false);
        pool.setSocketTO(3000);
        pool.setSocketConnectTO(0);
        pool.initialize();
    }

    /**
     * ********************************************************************************************************
     */


    //被装饰的类
    private MemCachedClient mcc = new MemCachedClient();


    //工具方法

    /**
     * @param key   key值
     * @param value value值
     * @return true:添加成功 ; false：添加失败（如果存在相同的key则返回false）
     */
    public boolean add(String key, Object value) {
        return mcc.add(key, value);
    }

    /**
     * @param key    key值
     * @param value  value值
     * @param expiry 过期时间，最小计算到1秒钟，（设置过期时间：）
     * @return true:添加成功 ; false：添加失败（如果存在相同的key则返回false）
     */
    public boolean add(String key, Object value, Integer expiry) {
        if (expiry == null) {
            return mcc.add(key, value);
        }
        return mcc.add(key, value, new Date(System.currentTimeMillis() + 1000 * expiry));
    }

    /**
     * @param key   key值
     * @param value value值
     * @return true:添加成功 ; false：添加失败（如果存在相同的key则进行替换）
     */
    public boolean set(String key, Object value) {
        return mcc.set(key, value);
    }

    /**
     * @param key    key值
     * @param value  value值
     * @param expiry 过期时间，最小计算到1秒钟，（设置过期时间：）
     * @return true:添加成功 ; false：添加失败（如果存在相同的key则进行替换）
     */
    public boolean set(String key, Object value, Integer expiry) {
        if (expiry == null) {
            return mcc.set(key, value);
        }
        return mcc.set(key, value, new Date(System.currentTimeMillis() + 1000 * expiry));
    }

    /**
     * @param key key值
     * @return 获取key对应的value值，如果不存在则返回NULL;
     */
    public Object get(String key) {
        return mcc.get(key);
    }

    /**
     * @param keys key值数组
     * @return keys对应的map
     */
    public Map<String, Object> getMulti(String[] keys) {
        return mcc.getMulti(keys);
    }

    /**
     * @param key   key值
     * @param value value值
     * @return 寻找mem里key对应的值，找到并成功替换则返回true，不存在或替换失败则返回false
     */
    public boolean replace(String key, Object value) {
        return mcc.replace(key, value);
    }

    /**
     * @param key    key值
     * @param value  value值
     * @param expiry 过期时间，最小计算到1秒钟，（设置过期时间：）
     * @return 寻找mem里key对应的值，找到并成功替换则返回true，不存在或替换失败则返回false
     */
    public boolean replace(String key, Object value, Integer expiry) {
        if (expiry == null) {
            return mcc.replace(key, value);
        }
        return mcc.add(key, value, new Date(System.currentTimeMillis() + 1000 * expiry));
    }

    /**
     * 清除所有缓存
     *
     * @return
     */
    public boolean flushAll() {
        return mcc.flushAll();
    }

    /**
     * *
     * 查看缓存状态
     *
     * @return
     */
    public Map<String, Map<String, String>> stats() {
        return mcc.stats();
    }


    /**
     * **
     * 删除缓存
     *
     * @param key
     * @return
     */
    public boolean delete(String key) {
        return mcc.delete(key);
    }


    /**
     * memcached放入计数器
     *
     * @param key
     * @return
     */

    public long addOrIncr(String key, long count) {
        return mcc.addOrIncr(key, count);
//		return mcc.incr(key);
    }


    /**
     * memcached放入计数器
     *
     * @param key
     * @return
     */

    public long addOrIncr(String key) {
        return mcc.addOrIncr(key);
//		return mcc.incr(key);
    }


    /**
     * memcached自增计数器
     *
     * @param key
     * @return
     */

    public long incr(String key) {
        return mcc.incr(key);
//		return mcc.incr(key);
    }

    /**
     * memcached自增计数器
     *
     * @param key
     * @return
     */

    public long incr(String key, long count) {
        return mcc.incr(key, count);
//		return mcc.incr(key);
    }


    public String getServerUrl() {
        return serverUrl;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getInitConnNum() {
        return initConnNum;
    }

    public Integer getMinConnNum() {
        return minConnNum;
    }

    public Integer getMaxConnNum() {
        return maxConnNum;
    }

}
