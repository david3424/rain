package com.noah.crm.cloud.order.dao;

import com.noah.crm.cloud.order.domain.OrderCoupon;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 */
public interface OrderCouponRepository extends PagingAndSortingRepository<OrderCoupon, Long> {
}
