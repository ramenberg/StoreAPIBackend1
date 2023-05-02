package com.example.storeapi.Controllers;

import com.example.storeapi.Models.Customer;
import com.example.storeapi.Models.Item;
import com.example.storeapi.Models.Order;
import com.example.storeapi.Models.OrderItem;
import com.example.storeapi.Repos.CustomerRepo;
import com.example.storeapi.Repos.ItemRepo;
import com.example.storeapi.Repos.OrderItemRepo;
import com.example.storeapi.Repos.OrderRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderRepo mockOrderRepo;

    @MockBean
    private CustomerRepo mockCustomerRepo;

    @MockBean
    private ItemRepo mockItemRepo;

    @MockBean
    private OrderItemRepo mockOrderItemRepo;



    @BeforeEach
    public void init() {



        Item item1 = new Item(1L, "Ananas", 25.0, null);
        Item item2 = new Item(2L, "Gurka", 17.9, null);


        Customer customer1 = new Customer(1L, "Kalle", "Anka", "543321", null);
        Customer customer2 = new Customer(2L, "Scott", "Eriksson", "334524", null);

        OrderItem orderItem1 = new OrderItem(1L, null, item1, 1);
        OrderItem orderItem2 = new OrderItem(2L, null, item2, 1);


        Set <OrderItem> orderItemSet1 = new HashSet<>();
        Set <OrderItem> orderItemSet2 = new HashSet<>();

        orderItemSet1.add(orderItem1);
        orderItemSet2.add(orderItem2);


        Order order1 = new Order(1L, null, customer1, orderItemSet1);
        Order order2 = new Order(2L, null, customer2, orderItemSet2);

        when(mockOrderRepo.findAll()).thenReturn(Arrays.asList(order1, order2));


    }


    @Test
    void getAllOrders() throws Exception{
        this.mvc.perform(get("/orders")).andExpect(status().isOk()).
                andExpect(content().json("[{\"orderId\": 1, \"timestamp\": null, \"customer\": " +
            "{\"customerId\": 1, \"firstName\": \"Kalle\", \"lastName\":\"Anka\", \"ssn\": \"543321\"}," +
            "\"orderItems\": [{\"orderItemId\": 1, \"item\": {\"itemId\": 1, \"name\": \"Ananas\", " +
            "\"price\": 25.0},\"quantity\": 1}]}," +
            "{\"orderId\": 2, \"timestamp\": null, \"customer\":" +
            "{\"customerId\": 2, \"firstName\": \"Scott\", \"lastName\":\"Eriksson\", \"ssn\": \"334524\"}," +
            "\"orderItems\": [{\"orderItemId\": 2, \"item\": {\"itemId\": 2, \"name\": \"Gurka\"," +
            "\"price\": 17.9}, \"quantity\": 1}]}]"));
    }

    @Test
    void getCustomerOrders() {
    }
}