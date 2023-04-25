package com.example.storeapi.Controllers;

import com.example.storeapi.Models.Order;
import com.example.storeapi.Repos.CustomerRepo;
import com.example.storeapi.Repos.ItemRepo;
import com.example.storeapi.Repos.OrderRepo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    private final OrderRepo orderRepo;

    private final ItemRepo itemRepo;

    private final CustomerRepo customerRepo;

    OrderController(OrderRepo orderRepo, ItemRepo itemRepo, CustomerRepo customerRepo){
        this.orderRepo=orderRepo;
        this.itemRepo=itemRepo;
        this.customerRepo=customerRepo;
    }

    @RequestMapping("orders")
    public List<Order> getAllOrders(){



        return orderRepo.findAll() ;
    }


}
