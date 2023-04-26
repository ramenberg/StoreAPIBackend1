package com.example.storeapi.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "orders")
//@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    @Column
    private Timestamp timestamp;

    @ManyToOne // ev cascade om vi vill att order ska tas bort om kunden försvinner
    @JoinColumn(name = "customer_id", referencedColumnName = "customerId")
    private Customer customer;


    @OneToMany(mappedBy = "orderItemId", cascade = CascadeType.ALL)
    @ToString.Exclude
//    @JsonIgnore
    private Set<OrderItem> orderItems = new HashSet<>();

//    @ManyToMany
//    //@JsonIgnore
//    @JoinTable(
//            name = "orders_items",
//            joinColumns = @JoinColumn(name = "order_id"),
//            inverseJoinColumns = @JoinColumn(name = "item_id")
//    )
//    private Set<Item> items = new HashSet<>();

    // Constructors

    public Order(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Order(Timestamp timestamp, Customer customer) {
        this.timestamp = timestamp;
        this.customer = customer;
    }

//    public Order(Timestamp timestamp, Customer customer, Set<Item> items) {
//        this.timestamp = timestamp;
//        this.customer = customer;
//        this.items = items;
//    }

    public Order(Timestamp timestamp, Customer customer, Set<OrderItem> orderItems) {
        this.timestamp = timestamp;
        this.customer = customer;
        this.orderItems = orderItems;
    }
}
