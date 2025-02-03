package com.icliniq.shoppingCart.entities;

import com.icliniq.shoppingCart.exceptions.ProductNotAvailableException;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<CartItem> items = new HashSet<>();

    public Cart() {
    }

    public void addItem(CartItem item) {
        items.add(item);
        item.setCart(this);
    }

    public void removeItem(long productId) {
        items.removeIf(item -> item.getProductId() == productId);
    }

    public void updateItemQuantity(long productId, int quantity) {
        for (CartItem item : items) {
            if (item.getProductId() == productId) {
                item.setQuantity(quantity);
                return;
            }
        }
        throw new ProductNotAvailableException("Product with ID " + productId + " not found in cart.");
    }

    public Set<CartItem> getItems() {
        return items;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setItems(Set<CartItem> items) {
        this.items = items;
    }
}

