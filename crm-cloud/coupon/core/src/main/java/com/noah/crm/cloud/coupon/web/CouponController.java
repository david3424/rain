package com.noah.crm.cloud.coupon.web;

import com.noah.crm.cloud.apis.api.BooleanWrapper;
import com.noah.crm.cloud.coupon.api.dtos.CouponDto;
import com.noah.crm.cloud.coupon.domain.Coupon;
import com.noah.crm.cloud.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.noah.crm.cloud.coupon.api.CouponUrl.CHECK_VALID_URL;
import static com.noah.crm.cloud.coupon.api.CouponUrl.COUPON_LIST_URL;
import static com.noah.crm.cloud.coupon.api.CouponUrl.USER_COUPON_LIST_URL;


/**
 * @author xdw9486
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class CouponController {

    @Autowired
    CouponService couponService;

    @RequestMapping(value = CHECK_VALID_URL, method = RequestMethod.GET)
    public BooleanWrapper checkCoupon(@PathVariable("couponId") Long couponId) {
        boolean valid = couponService.checkCoupon(couponId);
        return new BooleanWrapper(valid);
    }

    @RequestMapping(value = COUPON_LIST_URL, method = RequestMethod.GET)
    public List<CouponDto> listCouponsById(@RequestParam("id") Long[] ids) {

        List<Long> idList = Arrays.asList(ids);

        List<Coupon> coupons = couponService.findCouponsById(idList);

        return coupons.stream().map(this::convertCouponDto).collect(Collectors.toList());

    }


    @RequestMapping(value = USER_COUPON_LIST_URL, method = RequestMethod.GET)
    public List<CouponDto> listUserCoupons(@PathVariable("userId") Long userId) {

        List<Coupon> coupons = couponService.findCouponsByUser(userId);
        return coupons.stream().map(this::convertCouponDto).collect(Collectors.toList());

    }

    private CouponDto convertCouponDto(Coupon coupon) {
        CouponDto couponDto = new CouponDto();
        couponDto.setAmount(coupon.getAmount());
        couponDto.setCode(coupon.getCode());
        couponDto.setCreateTime(coupon.getCreateTime());
        couponDto.setId(coupon.getId());
        couponDto.setOrderId(coupon.getOrderId());
        couponDto.setState(coupon.getState());
        couponDto.setUpdateTime(coupon.getUpdateTime());
        couponDto.setUserId(coupon.getUserId());
        couponDto.setUseTime(coupon.getUseTime());
        return couponDto;
    }


}
