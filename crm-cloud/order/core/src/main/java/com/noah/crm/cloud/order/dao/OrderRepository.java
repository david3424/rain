package com.noah.crm.cloud.order.dao;

import com.noah.crm.cloud.order.domain.Order;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 */
public interface OrderRepository extends PagingAndSortingRepository<Order, Long>, OrderRepositoryCustom {
}
