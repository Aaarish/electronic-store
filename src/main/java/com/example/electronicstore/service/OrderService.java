package com.example.electronicstore.service;

import com.example.electronicstore.dto.OrderDto;

public interface OrderService {

    //create order
    OrderDto createOrder(String userId, OrderDto orderDto);

    //delete order
    String deleteOrder(String userId);

    //get order of a user
    OrderDto getOrder(String userId);
}
