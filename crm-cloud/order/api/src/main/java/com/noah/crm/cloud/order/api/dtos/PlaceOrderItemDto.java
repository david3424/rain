package com.noah.crm.cloud.order.api.dtos;

import javax.validation.constraints.NotNull;

/**
 * @author xdw9486
 */
public class PlaceOrderItemDto {

    private int quantity = 0;

    @NotNull(message = "产品ID不能为空")
    private Long productId;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
