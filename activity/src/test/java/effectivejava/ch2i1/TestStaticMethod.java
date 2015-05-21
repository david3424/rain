package effectivejava.ch2i1;// Simple test program for service provider framework

/**
 * 首先利用静态方法注册默认的以及指定name的provider，实质是把各种provider加入服务内部的ConcurrentHashMap<string,provider></string,provider>中*
 * 这些provider是通过匿名内部类定义的，不同方式实现接口中服务方法，优点是快速，不用单独再去写接口实现类，然后再在这里组合，new出来*
 * 最后通过静态方法newInstance调用默认或者指定name的Provider的Service*
 */
public class TestStaticMethod {
    public static void main(String[] args) {
        // Providers would execute these lines
        Services.registerDefaultProvider(DEFAULT_PROVIDER);
        Services.registerProvider("comp",  COMP_PROVIDER);
        Services.registerProvider("armed", ARMED_PROVIDER);

        // Clients would execute these lines1c
        Service s1 = Services.newInstance();
        Service s2 = Services.newInstance("comp");
        Service s3 = Services.newInstance("armed");
        System.out.printf("%s, %s, %s%n", s1, s2, s3);
    }

    private static Provider DEFAULT_PROVIDER = new Provider() {
        public Service newService() { //provider中的方法，返回Service 这里匿名内部类，通过实现接口返回一个覆盖了toString的Service
            return new Service() {
                @Override public String toString() {
                    return "Default service";
                }
            };
        }
    };

    private static Provider COMP_PROVIDER = new Provider() {
        public Service newService() {
            return new Service() {
                @Override public String toString() {
                    return "Complementary service";
                }
            };
        }
    };

    private static Provider ARMED_PROVIDER = new Provider() {
        public Service newService() {
            return new Service() {
                @Override public String toString() {
                    return "Armed service";
                }
            };
        }
    };
}
