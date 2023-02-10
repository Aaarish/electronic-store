package com.example.electronicstore.controller;

import com.example.electronicstore.dto.CategoryDto;
import com.example.electronicstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getUser(@PathVariable String categoryId){
        return ResponseEntity.ok(categoryService.getCategory(categoryId));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getUsers(){
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> removeUser(@PathVariable String categoryId){
        categoryService.removeCategory(categoryId);
        return ResponseEntity.ok("User deleted");
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<String> updateUser(@PathVariable String categoryId, @RequestBody CategoryDto categoryDto){
        categoryService.updateCategory(categoryId, categoryDto);
        return ResponseEntity.ok("User updated");
    }
}
