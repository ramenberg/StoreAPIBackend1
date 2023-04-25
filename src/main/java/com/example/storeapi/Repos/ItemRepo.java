package com.example.storeapi.Repos;

import com.example.storeapi.Models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepo extends JpaRepository<Item, Long> {
    List<Item> findByName(String name);

    List<Item> findByPrice(double price);
}
