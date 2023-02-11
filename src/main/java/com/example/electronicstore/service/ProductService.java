package com.example.electronicstore.service;

import com.example.electronicstore.dto.ProductDto;

import java.util.List;

public interface ProductService {
    //create product
    ProductDto addProduct(ProductDto productDto);

    //update product
    String updateProduct(int productId, ProductDto productDto);

    //remove product
    String removeProduct(int productId);

    //get single product
    ProductDto getProduct(int productId);

    //get all products
    List<ProductDto> getProducts();
}
