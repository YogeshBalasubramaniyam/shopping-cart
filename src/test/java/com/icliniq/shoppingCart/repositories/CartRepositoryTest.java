package com.icliniq.shoppingCart.repositories;

import com.icliniq.shoppingCart.entities.Cart;
import com.icliniq.shoppingCart.exceptions.CartNotFoundException;
import com.icliniq.shoppingCart.repositories.interfaces.JpaCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CartRepositoryTest {

    @Mock
    private JpaCartRepository jpaCartRepository;

    @InjectMocks
    private CartRepository cartRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveCart() {
        Cart cart = new Cart();
        when(jpaCartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart savedCart = cartRepository.save(cart);

        assertNotNull(savedCart);
    }

    @Test
    void shouldFindById() {
        long cartId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);
        when(jpaCartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        Cart foundCart = cartRepository.findById(cartId);

        assertNotNull(foundCart);
        assertEquals(cartId, foundCart.getId());
    }

    @Test
    void shouldThrowException_WhenCartIsNotFound() {
        when(jpaCartRepository.findById(999L)).thenReturn(Optional.empty());

        CartNotFoundException exception = assertThrows(CartNotFoundException.class, () -> {
            cartRepository.findById(999L);
        });
        assertEquals("Cart with ID 999 not found", exception.getMessage());
    }
}
