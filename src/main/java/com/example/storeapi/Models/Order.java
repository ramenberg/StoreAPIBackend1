package com.example.storeapi.Models;
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
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    @Column
    private Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customerId")
    private Customer customer;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<OrderItem> orderItems = new HashSet<>();

    // Constructors

    public Order(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Order(Timestamp timestamp, Customer customer) {
        this.timestamp = timestamp;
        this.customer = customer;
    }

    public Order(Timestamp timestamp, Customer customer, Set<OrderItem> orderItems) {
        this.timestamp = timestamp;
        this.customer = customer;
        this.orderItems = orderItems;
    }
}
