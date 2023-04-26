package com.example.storeapi.Controllers;
import com.example.storeapi.Repos.ItemRepo;
import com.example.storeapi.Repos.OrderItemRepo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderItemController {

    private final OrderItemRepo orderItemRepo;

    private final ItemRepo itemRepo;

    public OrderItemController(OrderItemRepo orderItemRepo, ItemRepo itemRepo){
        this.orderItemRepo=orderItemRepo;
        this.itemRepo=itemRepo;
    }
}