package org.david.rain.cloud.feign.hystrix.web.outservice;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xdw9486
 */
//@FeignClient("CLIENT-SERVICE-START")
//结合feign使用降级
    @Qualifier("dcClient")
@FeignClient(name = "CLIENT-SERVICE-START", fallback = DcClientFallback.class)
public interface DcClient {


    /**
     * 读取service
     *
     * @return json?
     */
    @GetMapping("/dc")
    String consumer();
}
