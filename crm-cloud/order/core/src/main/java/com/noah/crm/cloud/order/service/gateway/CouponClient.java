package com.noah.crm.cloud.order.service.gateway;

import com.noah.crm.cloud.coupon.api.CouponUrl;
import com.noah.crm.cloud.coupon.api.dtos.CouponDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author xdw9486
 */
@FeignClient(CouponUrl.SERVICE_NAME)
public interface CouponClient {

    @RequestMapping(method = RequestMethod.GET, value = CouponUrl.COUPON_LIST_URL)
    List<CouponDto> findCoupons(@RequestParam("id") List<Long> id);

}
