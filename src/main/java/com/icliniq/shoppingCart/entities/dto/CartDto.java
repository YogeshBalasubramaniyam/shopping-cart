package com.icliniq.shoppingCart.entities.dto;

import com.icliniq.shoppingCart.entities.Cart;
import com.icliniq.shoppingCart.entities.CartItem;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CartDto {
    private final long id;

    private final Set<CartItemDto> items;

    public CartDto(Cart cart) {
        this.id = cart.getId();
        this.items = new HashSet<>();
        Set<CartItem> items = cart.getItems();
        for (CartItem item : items) {
            CartItemDto cartItemDto = new CartItemDto(item);
            this.items.add(cartItemDto);
        }
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CartDto cartDto = (CartDto) object;
        return id == cartDto.id && Objects.equals(items, cartDto.items);
    }
}
