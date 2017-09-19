package guava.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author tao.ke Date: 14-12-20 Time: 下午1:55
 * @version \$Id$
 */
public class CacheSample {
    private static final Logger logger = LoggerFactory.getLogger(CacheSample.class);
    // common cache
    private static final Cache<String, String> COMMON_CACHE = CacheBuilder.newBuilder()
            .expireAfterWrite(3, TimeUnit.SECONDS).build();
    // Callable形式的Cache
    private static final Cache<String, String> CALLABLE_CACHE = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.SECONDS).maximumSize(1000).recordStats()
            .removalListener(new RemovalListener<Object, Object>() {
                @Override
                public void onRemoval(RemovalNotification<Object, Object> notification) {
                    logger.info("Remove a map entry which key is {},value is {},cause is {}.", notification.getKey(),
                            notification.getValue(), notification.getCause().name());
                }
            }).build();

    /* // CacheLoader形式的Cache
     private static final LoadingCache<String, String> LOADER_CACHE = CacheBuilder.newBuilder()
             .expireAfterWrite(1, TimeUnit.SECONDS).maximumSize(1).recordStats().build(new CacheLoader<String, String>() {
                 @Override
                 public String load(String key) throws Exception {
                     return key + new Date();
                 }
             });*/
    public static void main(String[] args) throws Exception {
        int times = 5;
        COMMON_CACHE.put("key", "key" + new Date());
        while (times-- > 0) {
            Thread.sleep(900);
           /* String valueCallable = CALLABLE_CACHE.get("key", new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return "key" + new Date();
                }
            });
            logger.info("Callable Cache ----->>>>> key is {},value is {}", "key", valueCallable);
            logger.info("Callable Cache ----->>>>> stat miss:{},stat hit:{}",CALLABLE_CACHE.stats().missRate(),CALLABLE_CACHE.stats().hitRate());*/
            /*String valueLoader = LOADER_CACHE.get("key");
            logger.info("Loader Cache ----->>>>> key is {},value is {}", "key", valueLoader);
            logger.info("Loader Cache ----->>>>> stat miss:{},stat hit:{}",LOADER_CACHE.stats().missRate(),LOADER_CACHE.stats().hitRate());*/
            String result = COMMON_CACHE.getIfPresent("key");
            logger.info("COMMON_CACHE ----->>>>> key is {},value is {}", "key", result);
        }
    }
}
