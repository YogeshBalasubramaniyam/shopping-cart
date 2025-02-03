package com.icliniq.shoppingCart.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icliniq.shoppingCart.entities.Product;
import com.icliniq.shoppingCart.entities.dto.ProductDto;
import com.icliniq.shoppingCart.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private long id;
    Product product;
    ProductDto productDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        id = 0;
        product = new Product("Product name", "Product description", 1.0, 0);
        product.setId(id);
        productDto = new ProductDto(product);
    }

    @Test
    void shouldAddProduct() throws Exception {
        when(productService.addProduct(any(ProductDto.class))).thenReturn(product);

        mockMvc.perform(post("/api/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isCreated());

        verify(productService, times(1)).addProduct(any(ProductDto.class));
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        when(productService.updateProduct(eq(id), any(ProductDto.class))).thenReturn(product);

        mockMvc.perform(put("/api/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk());

        verify(productService, times(1)).updateProduct(eq(id), any(ProductDto.class));
    }

    @Test
    void shouldFetchProduct() throws Exception {
        when(productService.fetchProduct(id)).thenReturn(product);

        mockMvc.perform(get("/api/products/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(productService, times(1)).fetchProduct(id);
    }

    @Test
    void shouldFetchProducts() throws Exception {
        List<Product> productList = Collections.singletonList(product);

        when(productService.fetchProducts()).thenReturn(productList);

        mockMvc.perform(get("/api/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(productService, times(1)).fetchProducts();
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(id);

        mockMvc.perform(delete("/api/products/{id}", id))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(id);
    }
}
