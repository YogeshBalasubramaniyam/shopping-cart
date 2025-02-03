package com.icliniq.shoppingCart.repositories.interfaces;

import com.icliniq.shoppingCart.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductRepository extends JpaRepository<Product, Long> {
}

