package com.example.electronicstore.service;

import com.example.electronicstore.dto.CategoryDto;

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
}
