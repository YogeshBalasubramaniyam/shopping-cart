package com.icliniq.shoppingCart.repositories.interfaces;

import com.icliniq.shoppingCart.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCartRepository extends JpaRepository<Cart, Long> {
}
