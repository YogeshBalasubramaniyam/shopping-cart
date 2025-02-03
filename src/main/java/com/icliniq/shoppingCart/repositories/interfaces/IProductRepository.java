package com.icliniq.shoppingCart.repositories.interfaces;

import com.icliniq.shoppingCart.entities.Product;

import java.util.List;

public interface IProductRepository {
    Product save(Product product);

    Product findById(long id);

    List<Product> findAll();

    void deleteById(long id);
}
