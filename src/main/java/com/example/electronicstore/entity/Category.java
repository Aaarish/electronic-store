package com.example.electronicstore.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private String categoryImage;
}
