package com.noah.crm.cloud.coupon.handler;
import com.noah.crm.cloud.common.event.handler.NotifyEventHandler;
import com.noah.crm.cloud.common.spring.ApplicationContextHolder;
import com.noah.crm.cloud.coupon.service.CouponService;
import com.noah.crm.cloud.user.api.events.UserCreated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xdw9486
 */
public class UserCreatedHandler implements NotifyEventHandler<UserCreated> {

    protected Logger logger = LoggerFactory.getLogger(UserCreatedHandler.class);


    @Override
    public void notify(UserCreated event) {
        CouponService couponService = ApplicationContextHolder.context.getBean(CouponService.class);
        couponService.initCoupon(event.getUserId());
    }
}
