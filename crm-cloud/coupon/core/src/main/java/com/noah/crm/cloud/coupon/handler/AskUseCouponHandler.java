package com.noah.crm.cloud.coupon.handler;
import com.noah.crm.cloud.apis.api.BooleanWrapper;
import com.noah.crm.cloud.apis.event.constants.FailureInfo;
import com.noah.crm.cloud.common.event.handler.RevokableAskEventHandler;
import com.noah.crm.cloud.common.spring.ApplicationContextHolder;
import com.noah.crm.cloud.coupon.api.events.AskUseCoupon;
import com.noah.crm.cloud.coupon.service.CouponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xdw9486
 */
public class AskUseCouponHandler implements RevokableAskEventHandler<AskUseCoupon> {

    private static Logger logger = LoggerFactory.getLogger(AskUseCouponHandler.class);


    @Override
    public void processRevoke(AskUseCoupon originEvent, FailureInfo failureInfo) {
        logger.debug("AskUseCouponHandler processRevoke, receive AskUseCoupon: " + originEvent);

        CouponService couponService = ApplicationContextHolder.context.getBean(CouponService.class);
        couponService.revokeUse(originEvent.getCouponIds(), originEvent.getUserId(), originEvent.getOrderId());
    }

    @Override
    public BooleanWrapper processRequest(AskUseCoupon event) {
        logger.debug("AskUseCouponHandler processRequest, receive AskUseCoupon: " + event);

        if(event.getCouponIds() == null || event.getCouponIds().isEmpty()
                || event.getUserId() == null || event.getOrderId() == null) {
            return new BooleanWrapper(false, "couponId or userId or orderId is null");
        }

        CouponService couponService = ApplicationContextHolder.context.getBean(CouponService.class);
        couponService.useCoupon(event.getCouponIds(), event.getUserId(), event.getOrderId());
        return new BooleanWrapper(true);

    }
}
