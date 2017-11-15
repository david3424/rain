package org.david.rain.cloud.feign.hystrix.web.outservice;

import org.springframework.stereotype.Service;

/**
 * @author xdw9486
 */
@Service("dcClientFallback")
public class DcClientFallback implements DcClient {
    @Override
    public String consumer() {
        return "fallback";
    }
}
