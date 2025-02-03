package com.icliniq.shoppingCart.repositories.interfaces;

import com.icliniq.shoppingCart.entities.Cart;

public interface ICartRepository {
    Cart save(Cart cart);

    Cart findById(long id);
}
