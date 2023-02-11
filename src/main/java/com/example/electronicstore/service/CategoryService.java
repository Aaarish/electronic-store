package com.example.electronicstore.service;

import com.example.electronicstore.dto.CategoryDto;
import com.example.electronicstore.dto.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {

    //create category
    CategoryDto addCategory(CategoryDto categoryDto);

    //get single category
    CategoryDto getCategory(String categoryTitle);

    //get all categories
    List<CategoryDto> getCategories();

    //delete category
    void removeCategory(String categoryTitle);

    //update category
    void updateCategory(String categoryTitle, CategoryDto categoryDto);

    //upload category image
    String uploadCategoryImage(String categoryTitle, MultipartFile file);

    //create product with category
    ProductDto createProductWithCategory(String categoryTitle, ProductDto productDto);

    //assign category to product
    ProductDto assignProductToCategory(String categoryTitle, int productId);

    //get all products of a category
    List<ProductDto> getProductsOfCategory(String categoryTitle);
}
