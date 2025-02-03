package com.icliniq.shoppingCart.entities.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.icliniq.shoppingCart.entities.CartItem;
import com.icliniq.shoppingCart.entities.deserializers.CustomIntegerDeserializer;
import com.icliniq.shoppingCart.entities.deserializers.CustomLongDeserializer;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CartItemDto {
    private long id;

    @NotNull(message = "Product Id is required.")
    @Min(value = 1, message = "Product Id must be greater than zero.")
    @JsonDeserialize(using = CustomLongDeserializer.class)
    private Long productId;

    @NotNull(message = "Product quantity is required.")
    @Min(value = 1, message = "Product quantity must be greater than zero.")
    @JsonDeserialize(using = CustomIntegerDeserializer.class)
    private Integer quantity;

    public CartItemDto() {
    }

    public CartItemDto(CartItem cartItem) {
        this.id = cartItem.getId();
        this.productId = cartItem.getProductId();
        this.quantity = cartItem.getQuantity();
    }

    public long getId() {
        return id;
    }

    public long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
