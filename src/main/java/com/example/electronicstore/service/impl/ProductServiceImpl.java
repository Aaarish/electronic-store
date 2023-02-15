package com.example.electronicstore.service.impl;

import com.example.electronicstore.dto.ProductDto;
import com.example.electronicstore.entity.Product;
import com.example.electronicstore.exception.ErrorModel;
import com.example.electronicstore.exception.ErrorType;
import com.example.electronicstore.exception.ResourceNotFoundException;
import com.example.electronicstore.repository.ProductRepo;
import com.example.electronicstore.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        Product savedProduct = productRepo.save(product);

        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public String updateProduct(int productId, ProductDto productDto) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(
                Arrays.asList(new ErrorModel(ErrorType.RESOURCE_NOT_FOUND, "No product found with this id"))
        ));

        product.setProductName(productDto.getProductName());
        product.setPrice(productDto.getPrice());
        product.setLive(product.isLive());
        product.setStock(product.isStock());

        productRepo.save(product);
        return "Product updated successfully";
    }

    @Override
    public String removeProduct(int productId) {
        productRepo.deleteById(productId);
        return "Product removed successfully";
    }

    @Override
    public ProductDto getProduct(int productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(
                Arrays.asList(new ErrorModel(ErrorType.RESOURCE_NOT_FOUND, "No product found with this id"))
        ));
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public List<ProductDto> getProducts() {
        List<Product> products = productRepo.findAll();

        List<ProductDto> productDtos = products.stream()
                .map(p -> modelMapper.map(p, ProductDto.class))
                .collect(Collectors.toList());

        return productDtos;
    }
}
