package com.example.electronicstore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private String categoryId;

    @NotBlank(message = "title is required!!")
    private String categoryTitle;

    private String description;
    private String categoryImage;
}
