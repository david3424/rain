package org.david.pattern.Singleton;// Service provider framework sketch

// Noninstantiable class for service registration and access - Pages 8-9

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
// 服务提供者注册与服务提供者接口（好比DriverManager）

/**
 * 包含单例模式：私有化构造函数，通过静态方法newInstance提供唯一的对象* 
 * 这里可以看做是providers这个map，因为是定义时就new了，也叫饿汉模式，而懒汉模式是在调用时判断不为空才new*
 */
public class Services {
    private Services() { }  // Prevents instantiation (Item 4)

    // Maps service names to services
    // 服务名与服务映射，即注册容器
    private static final Map<String, Provider> providers =
        new ConcurrentHashMap<>();
    public static final String DEFAULT_PROVIDER_NAME = "<def>";

    // Provider registration API
    // 服务提供者注册API，即注册工厂实现，相当于DriverManager.registerDriver

    public static void registerDefaultProvider(Provider p) {
        registerProvider(DEFAULT_PROVIDER_NAME, p);
    }
    public static void registerProvider(String name, Provider p){
        providers.put(name, p);
    }

    // Service access API
    // 服务访问API，向外界提供业务实现，相当于DriverManager.getConnection

    public static Service newInstance() {
        return newInstance(DEFAULT_PROVIDER_NAME);
    }
    public static Service newInstance(String name) {
        Provider p = providers.get(name);
        if (p == null)
            throw new IllegalArgumentException(
                "No provider registered with name: " + name);
        return p.newService();
    }
}
