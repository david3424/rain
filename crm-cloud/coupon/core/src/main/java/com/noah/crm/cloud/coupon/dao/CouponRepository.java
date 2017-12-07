package com.noah.crm.cloud.coupon.dao;

import com.noah.crm.cloud.coupon.domain.Coupon;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author xdw9486
 */
public interface CouponRepository extends PagingAndSortingRepository<Coupon, Long>, CouponRepositoryCustom {

    List<Coupon> findByUserId(Long userId);

}
