package org.david.rain.cloud.feign.web.outservice;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xdw9486
 */
@FeignClient("CLIENT-SERVICE-START")
public interface DcClient {


    /**
     * 读取service
     *
     * @return json?
     */
    @GetMapping("/dc")
    String consumer();
}
