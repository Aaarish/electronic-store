package com.example.electronicstore.dto;

import com.example.electronicstore.entity.Category;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private int productId;
    private String ProductName;
    private CategoryDto category;
    private int price;
    private boolean stock;
    private boolean live;
}
