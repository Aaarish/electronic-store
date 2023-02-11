package com.example.electronicstore.service.impl;

import com.example.electronicstore.dto.CategoryDto;
import com.example.electronicstore.dto.ProductDto;
import com.example.electronicstore.entity.Category;
import com.example.electronicstore.entity.Product;
import com.example.electronicstore.helper.ImageService;
import com.example.electronicstore.helper.PathMapper;
import com.example.electronicstore.repository.CategoryRepo;
import com.example.electronicstore.repository.ProductRepo;
import com.example.electronicstore.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PathMapper pathMapper;

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

    @Override
    public String uploadCategoryImage(String categoryTitle, MultipartFile file) {
        Category category = categoryRepo.findByCategoryTitle(categoryTitle);
        boolean isUploaded = imageService.uploadImage(file, pathMapper.getCategory());

        if(isUploaded){
            category.setCategoryImage(file.getOriginalFilename());
            categoryRepo.save(category);
        }

        return "Image successfully uploaded";
    }

    @Override
    public ProductDto createProductWithCategory(String categoryTitle, ProductDto productDto) {
        Category category = categoryRepo.findByCategoryTitle(categoryTitle);
        Product product = modelMapper.map(productDto, Product.class);

        product.setCategory(category);

        Product savedProduct = productRepo.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto assignProductToCategory(String categoryTitle, int productId) {
        Product product = productRepo.findById(productId).get();
        Category category = categoryRepo.findByCategoryTitle(categoryTitle);

        product.setCategory(category);
        Product savedProduct = productRepo.save(product);

        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public List<ProductDto> getProductsOfCategory(String categoryTitle) {
        Category category = categoryRepo.findByCategoryTitle(categoryTitle);

        List<Product> products = category.getProduct();

        return products.stream().map(p -> modelMapper.map(p, ProductDto.class)).collect(Collectors.toList());
    }
}
