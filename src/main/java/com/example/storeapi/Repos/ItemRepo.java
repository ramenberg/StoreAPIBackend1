package com.example.storeapi.Repos;

import com.example.storeapi.Models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepo extends JpaRepository<Item, Long> {
}
