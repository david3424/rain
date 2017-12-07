package com.noah.crm.cloud.order.web;

import com.noah.crm.cloud.order.api.dtos.OrderDto;
import com.noah.crm.cloud.order.api.dtos.PlaceOrderDto;
import com.noah.crm.cloud.order.domain.Order;
import com.noah.crm.cloud.order.service.OrderService;
import com.noah.crm.cloud.order.utils.OrderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.noah.crm.cloud.order.api.OrderUrl.ORDER_INFO;
import static com.noah.crm.cloud.order.api.OrderUrl.PLACE_ORDER;

/**
 * @author xdw9486
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping(value = PLACE_ORDER, method = RequestMethod.POST)
    public OrderDto placeOrder(@Valid @RequestBody PlaceOrderDto placeOrderDto) {

        Order order = orderService.placeOrder(placeOrderDto);

        return OrderUtils.convertToDto(order);
    }

    @RequestMapping(value = ORDER_INFO, method = RequestMethod.GET)
    public OrderDto orderInfo(@PathVariable("orderId") Long orderId) {

        Order order = orderService.get(orderId);

        return OrderUtils.convertToDto(order);
    }


}
