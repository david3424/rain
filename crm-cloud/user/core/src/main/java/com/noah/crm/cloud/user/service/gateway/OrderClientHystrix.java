package com.noah.crm.cloud.user.service.gateway;

import com.noah.crm.cloud.order.api.dtos.OrderDto;
import com.noah.crm.cloud.order.api.dtos.PlaceOrderDto;
import com.noah.crm.cloud.utils.logback.LoggerUtil;
import org.springframework.stereotype.Component;

/**
 * @author xdw9486
 */
@Component
public class OrderClientHystrix implements OrderClient {

    @Override
    public OrderDto placeOrder(PlaceOrderDto placeOrderDto) {
        return new OrderDto();
    }



}
