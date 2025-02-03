package com.icliniq.shoppingCart.repositories;

import com.icliniq.shoppingCart.entities.Product;
import com.icliniq.shoppingCart.exceptions.ProductNotFoundException;
import com.icliniq.shoppingCart.repositories.interfaces.JpaProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductRepositoryTest {

    @Mock
    private JpaProductRepository jpaProductRepository;

    @InjectMocks
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveProduct() {
        Product product = new Product();
        when(jpaProductRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct);
    }

    @Test
    void shouldFindById() {
        long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        when(jpaProductRepository.findById(productId)).thenReturn(Optional.of(product));

        Product foundProduct = productRepository.findById(productId);

        assertNotNull(foundProduct);
        assertEquals(productId, foundProduct.getId());
    }

    @Test
    void shouldThrowException_WhenProductIsNotFound() {
        long productId = 999L;
        when(jpaProductRepository.findById(productId)).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productRepository.findById(productId);
        });
        assertEquals("Product with ID 999 not found", exception.getMessage());
    }

    @Test
    void shouldFindAll() {
        Product product1 = new Product();
        Product product2 = new Product();
        List<Product> products = Arrays.asList(product1, product2);
        when(jpaProductRepository.findAll()).thenReturn(products);

        List<Product> foundProducts = productRepository.findAll();

        assertNotNull(foundProducts);
        assertEquals(2, foundProducts.size());
    }

    @Test
    void shouldDeleteById() {
        long productId = 1L;
        doNothing().when(jpaProductRepository).deleteById(productId);

        productRepository.deleteById(1L);

        verify(jpaProductRepository, times(1)).deleteById(productId);
    }
}
