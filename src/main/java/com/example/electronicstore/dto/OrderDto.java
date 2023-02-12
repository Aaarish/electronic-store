package com.example.electronicstore.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private String orderId;
    private String orderStatus;
    private boolean paymentStatus;
    private String billingAddress;
    private String billingPhone;
    private int totalAmount;
    private UserDto user;
    private List<OrderItemDto> orderItems = new ArrayList<>();
}
