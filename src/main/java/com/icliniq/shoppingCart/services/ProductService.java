package com.icliniq.shoppingCart.services;

import com.icliniq.shoppingCart.entities.Product;
import com.icliniq.shoppingCart.entities.dto.ProductDto;
import com.icliniq.shoppingCart.exceptions.DataAccessException;
import com.icliniq.shoppingCart.exceptions.ProductNotFoundException;
import com.icliniq.shoppingCart.repositories.interfaces.IProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final IProductRepository productRepository;

    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(ProductDto productDto) {
        Product product = new Product(productDto.getName(), productDto.getDescription(), productDto.getPrice(), productDto.getStock());
        try {
            return productRepository.save(product);
        } catch (Exception exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    public Product fetchProduct(long productId) {
        try {
            return productRepository.findById(productId);
        } catch (ProductNotFoundException exception) {
            throw new ProductNotFoundException(exception.getMessage());
        } catch (Exception exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    public Product updateProduct(long productId, ProductDto productDto) {
        Product product = productRepository.findById(productId);
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        try {
            return productRepository.save(product);
        } catch (Exception exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    public List<Product> fetchProducts() {
        try {
            return productRepository.findAll();
        } catch (Exception exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    public void deleteProduct(long productId) {
        try {
            productRepository.deleteById(productId);
        } catch (Exception exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }
}

