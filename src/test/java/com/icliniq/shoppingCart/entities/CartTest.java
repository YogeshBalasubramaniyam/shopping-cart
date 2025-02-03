package com.icliniq.shoppingCart.entities;

import com.icliniq.shoppingCart.exceptions.ProductNotAvailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    private Cart cart;

    @BeforeEach
    void setUp() {
        cart = new Cart();
    }

    @Test
    void shouldAddItem() {
        CartItem item = new CartItem(1L, 2);

        cart.addItem(item);
        Set<CartItem> items = cart.getItems();

        assertEquals(1, items.size());
        assertTrue(items.contains(item));
    }

    @Test
    void shouldRemoveItem() {
        long productId = 1L;
        CartItem item = new CartItem(productId, 2);
        cart.addItem(item);

        cart.removeItem(productId);
        Set<CartItem> items = cart.getItems();

        assertEquals(0, items.size());
    }

    @Test
    void shouldUpdateItemQuantity() {
        long productId = 1L;
        CartItem item = new CartItem(productId, 2);
        cart.addItem(item);

        cart.updateItemQuantity(productId, 5);
        Set<CartItem> items = cart.getItems();
        CartItem updatedItem = items.stream().filter(i -> i.getProductId() == productId).findFirst().orElse(null);

        assertNotNull(updatedItem);
        assertEquals(5, updatedItem.getQuantity());
    }

    @Test
    void shouldThrowException_WhenQuantityIsMore_WhileUpdating() {
        ProductNotAvailableException exception = assertThrows(ProductNotAvailableException.class, () -> {
            cart.updateItemQuantity(1L, 5);
        });

        assertEquals("Product with ID 1 not found in cart.", exception.getMessage());
    }
}
