package com.example.electronicstore.controller;

import com.example.electronicstore.dto.OrderDto;
import com.example.electronicstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/{userId}")
    public ResponseEntity<OrderDto> createOrder(@PathVariable String userId, @RequestBody OrderDto orderDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(userId, orderDto));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> cancelOrder(@PathVariable String userId){
        return ResponseEntity.ok(orderService.deleteOrder(userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<OrderDto> viewOrder(@PathVariable String userId){
        return ResponseEntity.ok(orderService.getOrder(userId));
    }
}
