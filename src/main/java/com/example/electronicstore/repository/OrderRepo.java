package com.example.electronicstore.repository;

import com.example.electronicstore.entity.Order;
import com.example.electronicstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, String> {
    Optional<Order> findByUser(User user);
}
