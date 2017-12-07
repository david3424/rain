package com.noah.crm.cloud.order.service.gateway;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.noah.crm.cloud.apis.exception.RemoteCallException;
import com.noah.crm.cloud.product.api.dtos.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xdw9486
 */
@Service
public class ProductGateway {

    protected Logger logger = LoggerFactory.getLogger(ProductGateway.class);

    @Autowired
    ProductClient productClient;

    @HystrixCommand(ignoreExceptions = RemoteCallException.class)
    public List<ProductDto> findProducts(List<Long> productIds) {

        return productClient.findProducts(productIds);
    }

    @HystrixCommand(ignoreExceptions = RemoteCallException.class)
    public List<ProductDto> findAllProducts() {
        List<ProductDto> productDtos = productClient.findAllProducts();

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return productDtos;

    }

}
