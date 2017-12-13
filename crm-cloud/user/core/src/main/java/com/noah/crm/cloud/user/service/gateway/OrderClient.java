package com.noah.crm.cloud.user.service.gateway;

import com.noah.crm.cloud.order.api.OrderUrl;
import com.noah.crm.cloud.order.api.dtos.OrderDto;
import com.noah.crm.cloud.order.api.dtos.PlaceOrderDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * @author xdw9486
 */
@FeignClient(value = OrderUrl.SERVICE_NAME, fallback = OrderClientHystrix.class)
public interface OrderClient {

    @RequestMapping(method = RequestMethod.POST, value = OrderUrl.PLACE_ORDER)
    OrderDto placeOrder(@Valid @RequestBody PlaceOrderDto placeOrderDto);
}
