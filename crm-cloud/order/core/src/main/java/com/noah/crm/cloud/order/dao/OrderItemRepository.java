package com.noah.crm.cloud.order.dao;

import com.noah.crm.cloud.order.domain.OrderItem;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author xdw9486
 */
public interface OrderItemRepository extends PagingAndSortingRepository<OrderItem, Long> {
}
