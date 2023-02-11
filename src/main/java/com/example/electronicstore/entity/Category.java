package com.example.electronicstore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @Column(name = "id")
    private String categoryId;

    @Column(name = "title", unique = true, nullable = false)
    private String categoryTitle;

    private String description;

    @Column(name = "image")
    private String categoryImage;

    @OneToMany(mappedBy = "category" , cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Product> product = new ArrayList<>();
}
