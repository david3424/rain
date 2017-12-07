package com.noah.crm.cloud.order.service.gateway;

import com.noah.crm.cloud.product.api.ProductUrl;
import com.noah.crm.cloud.product.api.dtos.ProductDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author xdw9486
 */
@FeignClient(ProductUrl.SERVICE_NAME)
public interface ProductClient {

    @RequestMapping(method = RequestMethod.GET, value = ProductUrl.PRODUCT_LIST_URL)
    List<ProductDto> findProducts(@RequestParam("id") List<Long> id);

    @RequestMapping(method = RequestMethod.GET, value = ProductUrl.ALL_PRODUCT_LIST_URL)
    List<ProductDto> findAllProducts();

}
