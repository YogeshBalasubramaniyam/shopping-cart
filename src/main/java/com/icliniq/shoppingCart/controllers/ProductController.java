package com.icliniq.shoppingCart.controllers;

import com.icliniq.shoppingCart.entities.Product;
import com.icliniq.shoppingCart.entities.dto.ProductDto;
import com.icliniq.shoppingCart.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductDto productDto) {
        Product createdProduct = productService.addProduct(productDto);
        return new ResponseEntity<>(new ProductDto(createdProduct), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable long id, @Valid @RequestBody ProductDto productDto) {
        Product updatedProduct = productService.updateProduct(
                id,
                productDto
        );
        return new ResponseEntity<>(new ProductDto(updatedProduct), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> fetchProduct(@PathVariable long id) {
        Product product = productService.fetchProduct(id);
        return ResponseEntity.ok(new ProductDto(product));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> fetchProducts() {
        List<ProductDto> products = productService.fetchProducts().stream()
                .map(ProductDto::new)
                .toList();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}

