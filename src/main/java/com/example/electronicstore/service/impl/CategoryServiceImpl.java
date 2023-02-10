package com.example.electronicstore.service.impl;

import com.example.electronicstore.dto.CategoryDto;
import com.example.electronicstore.entity.Category;
import com.example.electronicstore.repository.CategoryRepo;
import com.example.electronicstore.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        category.setCategoryId(UUID.randomUUID().toString());
        Category savedCategory = categoryRepo.save(category);

        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(String categoryTitle) {
        Category category = categoryRepo.findByCategoryTitle(categoryTitle);

        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories = categoryRepo.findAll();

        List<CategoryDto> categoryDtos = categories.stream()
                .map(c -> modelMapper.map(c, CategoryDto.class))
                .collect(Collectors.toList());

        return categoryDtos;
    }

    @Override
    public void removeCategory(String categoryTitle) {
        Category category = categoryRepo.findByCategoryTitle(categoryTitle);

        categoryRepo.delete(category);
    }

    @Override
    public void updateCategory(String categoryTitle, CategoryDto categoryDto) {
        Category category = categoryRepo.findByCategoryTitle(categoryTitle);

        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCategoryImage(categoryDto.getCategoryImage());

        categoryRepo.save(category);
    }
}
