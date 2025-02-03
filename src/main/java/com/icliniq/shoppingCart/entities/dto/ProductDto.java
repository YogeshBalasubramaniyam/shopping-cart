package com.icliniq.shoppingCart.entities.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.icliniq.shoppingCart.entities.Product;
import com.icliniq.shoppingCart.entities.deserializers.CustomDoubleDeserializer;
import com.icliniq.shoppingCart.entities.deserializers.CustomIntegerDeserializer;
import com.icliniq.shoppingCart.entities.deserializers.CustomStringDeserializer;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductDto {

    @NotNull(message = "Product name is required.")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters.")
    @JsonDeserialize(using = CustomStringDeserializer.class)
    private String name;

    private String description;

    private long id;

    @NotNull(message = "Price is required.")
    @Min(value = 1, message = "Price must be greater than zero.")
    @JsonDeserialize(using = CustomDoubleDeserializer.class)
    private Double price;

    @NotNull(message = "Stock is required.")
    @Min(value = 0, message = "Stock cannot be negative.")
    @JsonDeserialize(using = CustomIntegerDeserializer.class)
    private Integer stock;

    public ProductDto() {
    }

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stock = product.getStock();
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}

