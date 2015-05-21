package effectivejava.ch2i1;// Service provider framework sketch

// Noninstantiable class for service registration and access - Pages 8-9

import java.util.*;
import java.util.concurrent.*;
// 服务提供者注册与服务提供者接口（好比DriverManager）

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
