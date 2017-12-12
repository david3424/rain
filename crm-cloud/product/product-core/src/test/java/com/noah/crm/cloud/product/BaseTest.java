package com.noah.crm.cloud.product;


import com.noah.crm.cloud.product.api.dtos.ProductDto;
import com.noah.crm.cloud.product.domain.Product;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BaseTest {


    @Test
    public void testLamada() throws Exception {

        List<Product> products = new ArrayList<>();
        Product p = new Product();
        p.setId(1L);
        p.setName("test1");
        products.add(p);
        p = new Product();
        p.setId(2L);
        p.setName("test2");
        products.add(p);
        Function<Product, ProductDto> f = (Product pp) -> convertProductDto(pp);
//         products.stream().map(f).collect()
        assert (1==2);
        long sum = products.stream()
                .filter(w -> w.getName().equals("test1"))
                .mapToLong(w -> w.getId())
                .sum();

        Map<Long, ProductDto> couponDtoMap = products.stream()
                .map(this::convertProductDto)
//                .map(this::convertProductDto)
                .collect(Collectors.toMap(ProductDto::getId, Function.identity()));
        System.out.println(couponDtoMap);

    }


    private ProductDto convertProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setCategory(product.getCategory());
        productDto.setDescription(product.getDescription());
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        return productDto;
    }


}
