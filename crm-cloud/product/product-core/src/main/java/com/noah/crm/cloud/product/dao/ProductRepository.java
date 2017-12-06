package com.noah.crm.cloud.product.dao;


import com.noah.crm.cloud.product.domain.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author xdw9486
 */
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}
