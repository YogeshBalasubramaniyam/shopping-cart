package com.icliniq.shoppingCart.repositories;

import com.icliniq.shoppingCart.entities.Product;
import com.icliniq.shoppingCart.exceptions.ProductNotFoundException;
import com.icliniq.shoppingCart.repositories.interfaces.IProductRepository;
import com.icliniq.shoppingCart.repositories.interfaces.JpaProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository implements IProductRepository {

    private final JpaProductRepository jpaProductRepository;

    public ProductRepository(JpaProductRepository jpaProductRepository) {
        this.jpaProductRepository = jpaProductRepository;
    }

    @Override
    public Product save(Product product) {
        return jpaProductRepository.save(product);
    }

    @Override
    public Product findById(long id) {
        return jpaProductRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
    }

    @Override
    public List<Product> findAll() {
        return jpaProductRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        jpaProductRepository.deleteById(id);
    }
}
