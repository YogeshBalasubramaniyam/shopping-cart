package com.icliniq.shoppingCart.controllers;

import com.icliniq.shoppingCart.entities.Cart;
import com.icliniq.shoppingCart.entities.dto.CartDto;
import com.icliniq.shoppingCart.entities.dto.CartItemDto;
import com.icliniq.shoppingCart.services.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/create")
    public ResponseEntity<CartDto> createCart() {
        Cart cart = cartService.createCart();
        return new ResponseEntity<>(new CartDto(cart), HttpStatus.CREATED);
    }

    @PostMapping("/{cartId}/add")
    public ResponseEntity<CartDto> addItem(@PathVariable long cartId, @RequestParam long productId) {
        Cart cart = cartService.addItem(cartId, productId);
        return new ResponseEntity<>(new CartDto(cart), HttpStatus.OK);
    }

    @DeleteMapping("/{cartId}/remove")
    public ResponseEntity<CartDto> removeItem(@PathVariable long cartId,
                                              @RequestParam long productId) {
        Cart cart = cartService.removeItem(cartId, productId);
        return new ResponseEntity<>(new CartDto(cart), HttpStatus.OK);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> viewCart(@PathVariable long cartId) {
        Cart cart = cartService.viewCart(cartId);
        return new ResponseEntity<>(new CartDto(cart), HttpStatus.OK);
    }

    @PutMapping("/{cartId}/update")
    public ResponseEntity<CartDto> updateItemQuantity(@Valid @RequestBody CartItemDto cartItemDto, @PathVariable long cartId) {
        Cart cart = cartService.updateItemQuantity(cartId, cartItemDto.getProductId(), cartItemDto.getQuantity());
        return new ResponseEntity<>(new CartDto(cart), HttpStatus.OK);
    }
}