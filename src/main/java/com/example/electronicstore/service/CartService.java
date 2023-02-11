package com.example.electronicstore.service;

import com.example.electronicstore.dto.CartDto;
import com.example.electronicstore.dto.requests.AddItemToCartRequest;

public interface CartService {

    //add product to cart or (create a cart and add product if first time)
    CartDto addToCart(String userId, AddItemToCartRequest cartRequest);

    //remove product from cart
    String removeFromCart(String userId, int cartItemId);

    //clear cart
    String clearCart(String userId);
}
