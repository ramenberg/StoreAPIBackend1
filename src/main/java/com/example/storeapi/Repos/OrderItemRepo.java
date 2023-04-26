package com.example.storeapi.Repos;

import com.example.storeapi.Models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
}