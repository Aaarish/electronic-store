package com.example.electronicstore.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {
    private int orderItemId;
    private ProductDto product;
    private int quantity;
    private int totalPrice;
}
