package com.example.storeapi.Controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.storeapi.Models.Customer;
import com.example.storeapi.Models.Item;
import com.example.storeapi.Models.Order;
import com.example.storeapi.Models.OrderItem;
import com.example.storeapi.Repos.CustomerRepo;
import com.example.storeapi.Repos.ItemRepo;
import com.example.storeapi.Repos.OrderItemRepo;
import com.example.storeapi.Repos.OrderRepo;

import java.sql.Timestamp;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest {

    @Autowired
    private OrderController orderController;

    @MockBean
    private CustomerRepo customerRepo;

    @MockBean
    private ItemRepo itemRepo;

    @MockBean
    private OrderRepo orderRepo;

    @MockBean
    private OrderItemRepo orderItemRepo;

    @BeforeEach
    void setUp() {

        final Timestamp today = new Timestamp(System.currentTimeMillis());

        Item i1 = new Item("Item 1", 1.0);
        Item i2 = new Item("Item 2", 2.0);
        Item i3 = new Item("Item 3", 3.0);
        Item i4 = new Item("Item 4", 4.0);

        Customer c1 = new Customer("Pelle", "Svensson", "5210");
        Customer c2 = new Customer("Kalle", "Larsson", "8712");

        Order o1 = new Order(today, c1);
        Order o2 = new Order(today, c2);

        OrderItem oi1 = new OrderItem(o1, i1, 1);
        OrderItem oi2 = new OrderItem(o1, i2, 2);
        OrderItem oi3 = new OrderItem(o2, i3, 3);
        OrderItem oi4 = new OrderItem(o2, i4, 4);

        o1.setOrderItems(new HashSet<>());
        o2.setOrderItems(new HashSet<>());

        o1.getOrderItems().add(oi1);
        o1.getOrderItems().add(oi2);
        o2.getOrderItems().add(oi3);
        o2.getOrderItems().add(oi4);

        when(this.customerRepo.findAll()).thenReturn(Arrays.asList(c1, c2));
        when(this.customerRepo.findById(any())).thenReturn(Optional.of(c1));
        when(this.customerRepo.findBySsn(any())).thenReturn(c1);
        when(this.itemRepo.findAll()).thenReturn(Arrays.asList(i1, i2, i3, i4));
        when(this.itemRepo.findById(any())).thenReturn(Optional.of(i1));
        when(this.orderRepo.findAll()).thenReturn(Arrays.asList(o1, o2));
        when(this.orderRepo.findById(any())).thenReturn(Optional.of(o1));
        when(this.orderItemRepo.findAll()).thenReturn(Arrays.asList(oi1, oi2, oi3, oi4));
        when(this.orderItemRepo.findById(any())).thenReturn(Optional.of(oi1));
        when(this.orderItemRepo.findByOrder(any())).thenReturn(Arrays.asList(oi1, oi2));
        when(this.orderItemRepo.findByItem(any())).thenReturn(Arrays.asList(oi1, oi2));
        when(this.orderItemRepo.findByOrderAndItem(any(), any())).thenReturn(Optional.of(oi1));
        when(this.orderItemRepo.findByOrderAndItem(any(), any())).thenReturn(Optional.of(oi2));
        when(this.orderItemRepo.findByOrderAndItem(any(), any())).thenReturn(Optional.of(oi3));
        when(this.orderItemRepo.findByOrderAndItem(any(), any())).thenReturn(Optional.of(oi4));
    }

    @Test
    public void getAllOrdersTest() {
        List<Order> orders = orderController.getAllOrders();
        assertEquals(2, orders.size());

    }
}

