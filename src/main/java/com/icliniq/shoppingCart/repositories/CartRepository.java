package com.icliniq.shoppingCart.repositories;

import com.icliniq.shoppingCart.entities.Cart;
import com.icliniq.shoppingCart.exceptions.CartNotFoundException;
import com.icliniq.shoppingCart.repositories.interfaces.ICartRepository;
import com.icliniq.shoppingCart.repositories.interfaces.JpaCartRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepository implements ICartRepository {
    private final JpaCartRepository jpaCartRepository;

    public CartRepository(JpaCartRepository jpaCartRepository) {
        this.jpaCartRepository = jpaCartRepository;
    }

    @Override
    public Cart save(Cart cart) {
        return jpaCartRepository.save(cart);
    }

    @Override
    public Cart findById(long id) {
        return jpaCartRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException("Cart with ID " + id + " not found"));
    }
}
