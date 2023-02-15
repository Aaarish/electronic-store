package com.example.electronicstore.service.impl;

import com.example.electronicstore.dto.OrderDto;
import com.example.electronicstore.entity.Cart;
import com.example.electronicstore.entity.Order;
import com.example.electronicstore.entity.OrderItem;
import com.example.electronicstore.entity.User;
import com.example.electronicstore.exception.ErrorModel;
import com.example.electronicstore.exception.ErrorType;
import com.example.electronicstore.exception.ResourceNotFoundException;
import com.example.electronicstore.repository.CartRepo;
import com.example.electronicstore.repository.OrderRepo;
import com.example.electronicstore.repository.UserRepo;
import com.example.electronicstore.service.OrderService;
import org.aspectj.asm.IModelFilter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderDto createOrder(String userId, OrderDto orderDto) {
        User user = userRepo.findById(userId).get();
        Cart cart = cartRepo.findByUser(user).get();

        if(cart.getCartItems().size() <= 0){
            throw new RuntimeException("Order cannot be placed");
        }

        Order order = Order.builder()
                .orderId(UUID.randomUUID().toString())
                .orderStatus(orderDto.getOrderStatus())
                .paymentStatus(orderDto.isPaymentStatus())
                .totalAmount(orderDto.getTotalAmount())
                .user(user)
                .billingAddress(orderDto.getBillingAddress())
                .billingPhone(orderDto.getBillingPhone())
                .build();


        AtomicReference<Integer> totalAmountOfOrder = new AtomicReference<>(0);
        List<OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = OrderItem.builder()
                            .product(cartItem.getProduct())
                            .quantity(cartItem.getQuantity())
                            .build();
                    totalAmountOfOrder.set(totalAmountOfOrder.get() + cartItem.getTotalPrice());

                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmountOfOrder.get());

        cart.getCartItems().clear();

        cartRepo.save(cart);
        Order savedOrder = orderRepo.save(order);

        return modelMapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public String deleteOrder(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
                Arrays.asList(new ErrorModel(ErrorType.RESOURCE_NOT_FOUND, "No user found with this id"))
        ));
        Order order = orderRepo.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(
                Arrays.asList(new ErrorModel(ErrorType.RESOURCE_NOT_FOUND, "No order found with this user"))
        ));

        orderRepo.delete(order);
        return "Order cancelled successfully";
    }

    @Override
    public OrderDto getOrder(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
                Arrays.asList(new ErrorModel(ErrorType.RESOURCE_NOT_FOUND, "No user found with this id"))
        ));
        Order order = orderRepo.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(
                Arrays.asList(new ErrorModel(ErrorType.RESOURCE_NOT_FOUND, "No order found with this user"))
        ));

        return modelMapper.map(order, OrderDto.class);
    }
}
