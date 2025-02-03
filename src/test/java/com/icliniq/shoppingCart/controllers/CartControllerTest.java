package com.icliniq.shoppingCart.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icliniq.shoppingCart.entities.Cart;
import com.icliniq.shoppingCart.entities.dto.CartItemDto;
import com.icliniq.shoppingCart.services.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CartControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
    }

    @Test
    void shouldCreateCart() throws Exception {
        Cart cart = new Cart();
        when(cartService.createCart()).thenReturn(cart);

        mockMvc.perform(post("/api/cart/create"))
                .andExpect(status().isCreated());

        verify(cartService, times(1)).createCart();
    }

    @Test
    void shouldAddItem() throws Exception {
        long cartId = 1L;
        long productId = 101L;
        Cart cart = new Cart();
        when(cartService.addItem(cartId, productId)).thenReturn(cart);

        mockMvc.perform(post("/api/cart/{cartId}/add", cartId)
                        .param("productId", String.valueOf(productId)))
                .andExpect(status().isOk());

        verify(cartService, times(1)).addItem(cartId, productId);
    }

    @Test
    void shouldRemoveItem() throws Exception {
        long cartId = 1L;
        long productId = 101L;
        Cart cart = new Cart();
        when(cartService.removeItem(cartId, productId)).thenReturn(cart);

        mockMvc.perform(delete("/api/cart/{cartId}/remove", cartId)
                        .param("productId", String.valueOf(productId)))
                .andExpect(status().isOk());

        verify(cartService, times(1)).removeItem(cartId, productId);
    }

    @Test
    void shouldViewCart() throws Exception {
        long cartId = 1L;
        Cart cart = new Cart();
        when(cartService.viewCart(cartId)).thenReturn(cart);

        mockMvc.perform(get("/api/cart/{cartId}", cartId))
                .andExpect(status().isOk());

        verify(cartService, times(1)).viewCart(cartId);
    }

    @Test
    void shouldUpdateItemQuantity() throws Exception {
        long cartId = 1L;
        long productId = 101L;
        int quantity = 2;
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setQuantity(quantity);
        cartItemDto.setProductId(productId);
        Cart cart = new Cart();
        when(cartService.updateItemQuantity(cartId, productId, quantity)).thenReturn(cart);

        mockMvc.perform(put("/api/cart/{cartId}/update", cartId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(cartItemDto)))
                .andExpect(status().isOk());

        verify(cartService, times(1)).updateItemQuantity(cartId, productId, quantity);
    }

    @Test
    void shouldThrowBadRequest_WhenItemQuantityAndProductIdIsZero() throws Exception {
        long cartId = 1L;
        long productId = 101L;
        int quantity = 2;
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setQuantity(0);
        cartItemDto.setProductId(0);
        Cart cart = new Cart();
        when(cartService.updateItemQuantity(cartId, productId, quantity)).thenReturn(cart);

        mockMvc.perform(put("/api/cart/{cartId}/update", cartId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(cartItemDto)))
                .andExpect(status().isBadRequest());
    }
}
