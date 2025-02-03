package com.icliniq.shoppingCart.services;

import com.icliniq.shoppingCart.entities.Product;
import com.icliniq.shoppingCart.entities.dto.ProductDto;
import com.icliniq.shoppingCart.exceptions.DataAccessException;
import com.icliniq.shoppingCart.exceptions.ProductNotFoundException;
import com.icliniq.shoppingCart.repositories.interfaces.IProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private IProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product("Product name", "Product description", 1.0, 0);
        product.setId(1L);
        productDto = new ProductDto(product);
    }

    @Test
    void addProduct_ShouldReturnSavedProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = productService.addProduct(productDto);

        assertNotNull(savedProduct);
        assertEquals(product.getId(), savedProduct.getId());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void addProduct_ShouldThrowDataAccessException() {
        when(productRepository.save(any(Product.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(DataAccessException.class, () -> productService.addProduct(productDto));
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void fetchProduct_ShouldReturnProduct() {
        when(productRepository.findById(product.getId())).thenReturn(product);

        Product fetchedProduct = productService.fetchProduct(product.getId());

        assertNotNull(fetchedProduct);
        assertEquals(product.getId(), fetchedProduct.getId());
        verify(productRepository, times(1)).findById(product.getId());
    }

    @Test
    void fetchProduct_ShouldThrowProductNotFoundException() {
        when(productRepository.findById(product.getId())).thenThrow(new ProductNotFoundException("Product not found"));

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.fetchProduct(product.getId());
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).findById(product.getId());
    }

    @Test
    void fetchProduct_ShouldThrowDataAccessException() {
        when(productRepository.findById(product.getId())).thenThrow(new DataAccessException("Database Error"));

        assertThrows(DataAccessException.class, () -> productService.fetchProduct(product.getId()));
        verify(productRepository, times(1)).findById(product.getId());
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() {
        when(productRepository.findById(product.getId())).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product updatedProduct = productService.updateProduct(product.getId(), productDto);

        assertNotNull(updatedProduct);
        assertEquals(product.getId(), updatedProduct.getId());
        verify(productRepository, times(1)).findById(product.getId());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_ShouldThrowDataAccessException() {
        when(productRepository.findById(product.getId())).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(DataAccessException.class, () -> productService.updateProduct(product.getId(), productDto));
        verify(productRepository, times(1)).findById(product.getId());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void fetchProducts_ShouldReturnListOfProducts() {
        List<Product> productList = Collections.singletonList(product);
        when(productRepository.findAll()).thenReturn(productList);

        List<Product> fetchedProducts = productService.fetchProducts();

        assertNotNull(fetchedProducts);
        assertEquals(1, fetchedProducts.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void fetchProducts_ShouldThrowDataAccessException() {
        when(productRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        assertThrows(DataAccessException.class, () -> productService.fetchProducts());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void deleteProduct_ShouldInvokeDeleteById() {
        doNothing().when(productRepository).deleteById(product.getId());

        productService.deleteProduct(product.getId());

        verify(productRepository, times(1)).deleteById(product.getId());
    }

    @Test
    void deleteProduct_ShouldThrowDataAccessException() {
        doThrow(new RuntimeException("Database error")).when(productRepository).deleteById(product.getId());

        assertThrows(DataAccessException.class, () -> productService.deleteProduct(product.getId()));
        verify(productRepository, times(1)).deleteById(product.getId());
    }
}
