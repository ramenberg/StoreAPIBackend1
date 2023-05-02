package com.example.storeapi.Controllers;
import com.example.storeapi.Repos.CustomerRepo;
import com.example.storeapi.Repos.ItemRepo;
import com.example.storeapi.Repos.OrderItemRepo;
import com.example.storeapi.Repos.OrderRepo;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderItemController {

    private final OrderItemRepo orderItemRepo;

    private final ItemRepo itemRepo;

    private final CustomerRepo customerRepo;

    private final OrderRepo orderRepo;

    public OrderItemController(OrderItemRepo orderItemRepo, ItemRepo itemRepo, CustomerRepo customerRepo, OrderRepo orderRepo){
        this.orderItemRepo=orderItemRepo;
        this.itemRepo=itemRepo;
        this.customerRepo=customerRepo;
        this.orderRepo=orderRepo;
    }
}