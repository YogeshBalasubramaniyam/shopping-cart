package com.icliniq.shoppingCart.services;

import com.icliniq.shoppingCart.entities.Cart;
import com.icliniq.shoppingCart.entities.CartItem;
import com.icliniq.shoppingCart.entities.Product;
import com.icliniq.shoppingCart.exceptions.CartNotFoundException;
import com.icliniq.shoppingCart.exceptions.DataAccessException;
import com.icliniq.shoppingCart.exceptions.ProductNotAvailableException;
import com.icliniq.shoppingCart.repositories.interfaces.ICartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private ICartRepository cartRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CartService cartService;

    private Cart cart;
    private Product product;
    long cartId;
    long productId;

    @BeforeEach
    void setUp() {
        cartId = 1L;
        productId = 1L;
        MockitoAnnotations.openMocks(this);
        cart = new Cart();
        cart.setId(cartId);
        product = new Product("Product name", "Product description", 1.0, 10);
        product.setId(1L);
    }

    @Test
    void createCart_ShouldReturnNewCart() {
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart createdCart = cartService.createCart();

        assertNotNull(createdCart);
        assertEquals(cartId, createdCart.getId());
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void createCart_ShouldThrowDataAccessException() {
        when(cartRepository.save(any(Cart.class))).thenThrow(new DataAccessException("Database error") {
        });

        assertThrows(DataAccessException.class, () -> cartService.createCart());
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void addItem_ShouldAddNewItemToCart() {
        when(productService.fetchProduct(productId)).thenReturn(product);
        when(cartRepository.findById(cartId)).thenReturn(cart);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart updatedCart = cartService.addItem(cartId, productId);

        assertNotNull(updatedCart);
        assertEquals(1, updatedCart.getItems().size());
        List<CartItem> itemsList = new ArrayList<>(updatedCart.getItems());
        CartItem item = itemsList.getFirst();
        assertEquals(productId, item.getProductId());
        assertEquals(1, item.getQuantity());
        verify(cartRepository, times(1)).findById(cartId);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void addItem_ShouldThrowProductNotAvailableException_WhenStockIsZero() {
        product.setStock(0);
        when(productService.fetchProduct(productId)).thenReturn(product);

        ProductNotAvailableException exception = assertThrows(ProductNotAvailableException.class, () -> {
            cartService.addItem(cartId, productId);
        });

        assertEquals("Product with ID 1 has 0 stocks and hence can't be added.", exception.getMessage());
        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    void addItem_ShouldThrowDataAccessException() {
        when(productService.fetchProduct(productId)).thenReturn(product);
        when(cartRepository.findById(cartId)).thenReturn(cart);
        when(cartRepository.save(any(Cart.class))).thenThrow(new DataAccessException("Database error") {
        });

        assertThrows(DataAccessException.class, () -> cartService.addItem(cartId, productId));
        verify(cartRepository, times(1)).save(any(Cart.class));
    }


    @Test
    void removeItem_ShouldRemoveItemFromCart() {
        CartItem cartItem = new CartItem(productId, 1);
        cart.addItem(cartItem);
        when(cartRepository.findById(cartId)).thenReturn(cart);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart updatedCart = cartService.removeItem(cartId, productId);

        assertNotNull(updatedCart);
        assertTrue(updatedCart.getItems().isEmpty());
        verify(cartRepository, times(1)).findById(cartId);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void removeItem_ShouldThrowDataAccessException() {
        CartItem cartItem = new CartItem(productId, 1);
        cart.addItem(cartItem);
        when(cartRepository.findById(cartId)).thenReturn(cart);
        when(cartRepository.save(any(Cart.class))).thenThrow(new DataAccessException("Database error") {
        });

        assertThrows(DataAccessException.class, () -> cartService.removeItem(cartId, productId));
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void viewCart_ShouldReturnCart() {
        when(cartRepository.findById(cartId)).thenReturn(cart);

        Cart foundCart = cartService.viewCart(cartId);

        assertNotNull(foundCart);
        assertEquals(cartId, foundCart.getId());
        verify(cartRepository, times(1)).findById(cartId);
    }

    @Test
    void viewCart_ShouldThrowCartNotFoundException_WhenCartDoesNotExist() {
        when(cartRepository.findById(cartId)).thenThrow(new CartNotFoundException("Cart not found"));

        assertThrows(CartNotFoundException.class, () -> cartService.viewCart(cartId));
        verify(cartRepository, times(1)).findById(cartId);
    }

    @Test
    void viewItem_ShouldThrowDataAccessException() {
        when(cartRepository.findById(cartId)).thenThrow(new DataAccessException("Database error") {
        });

        assertThrows(DataAccessException.class, () -> cartService.viewCart(cartId));
        verify(cartRepository, times(1)).findById(cartId);
    }

    @Test
    void updateItemQuantity_ShouldUpdateQuantity() {
        CartItem cartItem = new CartItem(productId, 1);
        cart.addItem(cartItem);
        when(productService.fetchProduct(productId)).thenReturn(product);
        when(cartRepository.findById(cartId)).thenReturn(cart);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart updatedCart = cartService.updateItemQuantity(cartId, productId, 5);

        assertNotNull(updatedCart);
        assertEquals(1, updatedCart.getItems().size());
        List<CartItem> itemsList = new ArrayList<>(updatedCart.getItems());
        CartItem item = itemsList.getFirst();
        assertEquals(1L, item.getProductId());
        assertEquals(5, item.getQuantity());
        verify(cartRepository, times(1)).findById(cartId);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void updateItemQuantity_ShouldThrowProductNotAvailableException_WhenQuantityExceedsStock() {
        when(productService.fetchProduct(productId)).thenReturn(product);

        ProductNotAvailableException exception = assertThrows(ProductNotAvailableException.class, () -> {
            cartService.updateItemQuantity(cartId, productId, 15);
        });

        assertEquals("Product with ID 1 has only 10 stocks.", exception.getMessage());
        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    void updateItemQuantity_ShouldThrowDataAccessException() {
        CartItem cartItem = new CartItem(productId, 1);
        cart.addItem(cartItem);
        when(productService.fetchProduct(productId)).thenReturn(product);
        when(cartRepository.findById(cartId)).thenReturn(cart);
        when(cartRepository.save(any(Cart.class))).thenThrow(new DataAccessException("Database error") {
        });

        assertThrows(DataAccessException.class, () -> cartService.updateItemQuantity(cartId, productId, 3));
        verify(cartRepository, times(1)).save(any(Cart.class));
    }
}
