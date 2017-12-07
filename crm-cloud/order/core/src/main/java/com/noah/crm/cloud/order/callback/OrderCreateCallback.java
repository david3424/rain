package com.noah.crm.cloud.order.callback;

import com.noah.crm.cloud.account.api.events.AskReduceBalance;
import com.noah.crm.cloud.apis.event.constants.FailureInfo;
import com.noah.crm.cloud.apis.exception.ServiceException;
import com.noah.crm.cloud.common.spring.ApplicationContextHolder;
import com.noah.crm.cloud.coupon.api.events.AskUseCoupon;
import com.noah.crm.cloud.order.service.OrderService;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author xdw9486
 */
public class OrderCreateCallback {

    public void onSuccess(AskReduceBalance askReduceBalance, AskUseCoupon askUseCoupon, String orderId) {

        long oId = NumberUtils.toLong(orderId);
        if (oId == 0L) {
            throw new ServiceException("orderId为空: " + orderId);
        }

        OrderService orderService = ApplicationContextHolder.context.getBean(OrderService.class);

        orderService.markCreateSuccess(oId);
    }

    public void onFailure(AskReduceBalance askReduceBalance, AskUseCoupon askUseCoupon,
                          String orderId, FailureInfo failureInfo) {

        long oId = NumberUtils.toLong(orderId);
        if (oId == 0L) {
            throw new ServiceException("orderId为空: " + orderId);
        }

        OrderService orderService = ApplicationContextHolder.context.getBean(OrderService.class);

        orderService.markCreateFail(oId);

    }

}
