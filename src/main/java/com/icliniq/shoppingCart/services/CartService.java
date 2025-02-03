package com.icliniq.shoppingCart.services;

import com.icliniq.shoppingCart.entities.Cart;
import com.icliniq.shoppingCart.entities.CartItem;
import com.icliniq.shoppingCart.exceptions.CartNotFoundException;
import com.icliniq.shoppingCart.exceptions.DataAccessException;
import com.icliniq.shoppingCart.exceptions.ProductNotAvailableException;
import com.icliniq.shoppingCart.repositories.interfaces.ICartRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    public static final int INITIAL_QUANTITY = 1;

    private final ICartRepository cartRepository;
    private final ProductService productService;

    public CartService(ICartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    @Transactional
    public Cart createCart() {
        Cart cart = new Cart();
        try {
            return cartRepository.save(cart);
        } catch (Exception exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    @Transactional
    public Cart addItem(long cartId, long productId) {
        int availableStock = productService.fetchProduct(productId).getStock();
        if (availableStock <= 0)
            throw new ProductNotAvailableException("Product with ID " + productId + " has 0 stocks and hence can't be added.");
        Cart cart = cartRepository.findById(cartId);
        CartItem cartItem = new CartItem(productId, INITIAL_QUANTITY);
        cart.addItem(cartItem);
        try {
            return cartRepository.save(cart);
        } catch (Exception exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    @Transactional
    public Cart removeItem(long cartId, long productId) {
        Cart cart = cartRepository.findById(cartId);
        cart.removeItem(productId);
        try {
            return cartRepository.save(cart);
        } catch (Exception exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    public Cart viewCart(long cartId) {
        try {
            return cartRepository.findById(cartId);
        } catch (CartNotFoundException ex) {
            throw new CartNotFoundException(ex.getMessage());
        } catch (Exception ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    @Transactional
    public Cart updateItemQuantity(long cartId, long productId, int quantity) {
        int availableStock = productService.fetchProduct(productId).getStock();
        if (quantity > availableStock)
            throw new ProductNotAvailableException("Product with ID " + productId + " has only " + availableStock + " stocks.");
        Cart cart = cartRepository.findById(cartId);
        cart.updateItemQuantity(productId, quantity);
        try {
            return cartRepository.save(cart);
        } catch (Exception exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }
}
