package com.example.storeapi.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;
    private String name;
    private Double price;

    @ManyToMany(mappedBy = "items")
    private List<Order> orders = new ArrayList<>();

    // Constructors
    public Item(String name, Double price) {
        this.name = name;
        this.price = price;
    }

}
