package com.example.electronicstore.controller;

import com.example.electronicstore.dto.CategoryDto;
import com.example.electronicstore.dto.ProductDto;
import com.example.electronicstore.service.CategoryService;
import com.example.electronicstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> addUser(@RequestBody CategoryDto categoryDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.addCategory(categoryDto));
    }

    @GetMapping("/{categoryTitle}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable String categoryTitle){
        return ResponseEntity.ok(categoryService.getCategory(categoryTitle));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories(){
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @DeleteMapping("/{categoryTitle}")
    public ResponseEntity<String> removeCategory(@PathVariable String categoryTitle){
        categoryService.removeCategory(categoryTitle);
        return ResponseEntity.ok("User deleted");
    }

    @PutMapping("/{categoryTitle}")
    public ResponseEntity<String> updateCategory(@PathVariable String categoryTitle, @RequestBody CategoryDto categoryDto){
        categoryService.updateCategory(categoryTitle, categoryDto);
        return ResponseEntity.ok("User updated");
    }

    @PostMapping("/image/{categoryTitle}")
    public ResponseEntity<String> uploadImage(@PathVariable String categoryTitle, @RequestParam("img") MultipartFile file){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.uploadCategoryImage(categoryTitle, file));
    }

    @PostMapping("/{categoryTitle}")
    public ResponseEntity<ProductDto> createProductWithCategory(@PathVariable String categoryTitle, @RequestBody ProductDto productDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createProductWithCategory(categoryTitle, productDto));
    }

    @PutMapping("/{categoryTitle}/products/{productId}")
    public ResponseEntity<ProductDto> assignProductToCategory(@PathVariable String categoryTitle, @PathVariable int productId){
        return ResponseEntity.ok(categoryService.assignProductToCategory(categoryTitle, productId));
    }

}
