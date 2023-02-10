package com.example.electronicstore.repository;

import com.example.electronicstore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, String> {
    Category findByCategoryTitle(String title);
    void deleteByCategoryTitle(String title);
}
