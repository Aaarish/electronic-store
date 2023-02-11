package com.example.electronicstore.controller;

import com.example.electronicstore.dto.CartDto;
import com.example.electronicstore.dto.requests.AddItemToCartRequest;
import com.example.electronicstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest cartRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addToCart(userId, cartRequest));
    }

    @DeleteMapping("/{userId}/{cartItemId}")
    public ResponseEntity<String> removeItemFromCart(@PathVariable String userId, @PathVariable int cartItemId){
        return ResponseEntity.ok(cartService.removeFromCart(userId, cartItemId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable String userId){
        return ResponseEntity.ok(cartService.clearCart(userId));
    }
}
