package effectivejava.ch2i1;// Service provider framework sketch - Service provider interface - Page 12
// 服务提供都接口，即业务工厂接口。（相当于Driver接口，由第三方厂家实现）



public interface Provider {
    Service newService();
}