package com.example.electronicstore.service.impl;

import com.example.electronicstore.dto.CartDto;
import com.example.electronicstore.dto.request.AddItemToCartRequest;
import com.example.electronicstore.entity.Cart;
import com.example.electronicstore.entity.CartItem;
import com.example.electronicstore.entity.Product;
import com.example.electronicstore.entity.User;
import com.example.electronicstore.exception.ErrorModel;
import com.example.electronicstore.exception.ErrorType;
import com.example.electronicstore.exception.ResourceNotFoundException;
import com.example.electronicstore.repository.CartItemRepo;
import com.example.electronicstore.repository.CartRepo;
import com.example.electronicstore.repository.ProductRepo;
import com.example.electronicstore.repository.UserRepo;
import com.example.electronicstore.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CartItemRepo cartItemRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDto addToCart(String userId, AddItemToCartRequest cartRequest) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
                Arrays.asList(new ErrorModel(ErrorType.RESOURCE_NOT_FOUND, "No user found with this id"))
        ));

        Product product = productRepo.findById(cartRequest.getProductId()).orElseThrow(() -> new ResourceNotFoundException(
                Arrays.asList(new ErrorModel(ErrorType.RESOURCE_NOT_FOUND, "No product found with this id"))
        ));

        Cart cart = null;

        try {
            cart = cartRepo.findByUser(user).get();
        } catch (NoSuchElementException e){
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
            cart.setUser(user);
            cart.setCartItems(new ArrayList<>());
        }

        AtomicReference<Boolean> isAlreadyPresent = new AtomicReference<>(false);
        List<CartItem> cartItemList = cart.getCartItems().stream()
                .map(i -> {
                    if (i.getProduct().getProductId() == cartRequest.getProductId()) {
                        i.setQuantity(cartRequest.getQuantity());
                        i.setTotalPrice(product.getPrice() * cartRequest.getQuantity());
                        isAlreadyPresent.set(true);
                        return i;
                    } else {
                        return i;
                    }
                })
                .collect(Collectors.toList());

        if(!isAlreadyPresent.get()){
            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(cartRequest.getQuantity())
                    .totalPrice(product.getPrice() * cartRequest.getQuantity())
                    .build();

            cart.getCartItems().add(cartItem);
        }

        cartRepo.save(cart);

        return modelMapper.map(cart, CartDto.class);
    }

    @Override
    public String removeFromCart(String userId, int cartItemId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
                Arrays.asList(new ErrorModel(ErrorType.RESOURCE_NOT_FOUND, "No user found with this id"))
        ));
        Cart cart = cartRepo.findByUser(user).get();
        CartItem cartItem = cartItemRepo.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException(
                Arrays.asList(new ErrorModel(ErrorType.RESOURCE_NOT_FOUND, "No cart found with this user"))
        ));

        cart.getCartItems().remove(cartItem);
        cartRepo.save(cart);

        return "Desired item removed from cart successfully";
    }

    @Override
    public String clearCart(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
                Arrays.asList(new ErrorModel(ErrorType.RESOURCE_NOT_FOUND, "No user found with this id"))
        ));
        Cart cart = cartRepo.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(
                Arrays.asList(new ErrorModel(ErrorType.RESOURCE_NOT_FOUND, "No cart found with this user"))
        ));

        cart.getCartItems().clear();
        cartRepo.save(cart);

        return "Cart cleared successfully";
    }
}
