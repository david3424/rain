package com.noah.crm.cloud.user.service.gateway;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.noah.crm.cloud.apis.exception.RemoteCallException;
import com.noah.crm.cloud.order.api.dtos.OrderDto;
import com.noah.crm.cloud.order.api.dtos.PlaceOrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author xdw9486
 */
@Service
public class OrderGateway {

    protected Logger logger = LoggerFactory.getLogger(OrderGateway.class);

    @Autowired
    OrderClient orderClient;

    @HystrixCommand(ignoreExceptions = RemoteCallException.class)
    public OrderDto placeOrder(PlaceOrderDto placeOrderDto) {

        return orderClient.placeOrder(placeOrderDto);
    }

}
