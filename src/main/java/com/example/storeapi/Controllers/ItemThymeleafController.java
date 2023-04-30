package com.example.storeapi.Controllers;

import com.example.storeapi.Models.Item;
import com.example.storeapi.Repos.CustomerRepo;
import com.example.storeapi.Repos.ItemRepo;
import com.example.storeapi.Repos.OrderItemRepo;
import com.example.storeapi.Repos.OrderRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping(path = "/graphics")
public class ItemThymeleafController {

    private final ItemRepo itemRepo;
    private final CustomerRepo customerRepo;
    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private static final Logger log = LoggerFactory.getLogger(ItemController.class);

    public ItemThymeleafController(ItemRepo itemRepo, CustomerRepo customerRepo, OrderRepo orderRepo, OrderItemRepo orderItemRepo) {
        this.itemRepo = itemRepo;
        this.customerRepo = customerRepo;
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
    }

    // http://localhost:8080/graphics/items (Denna genererar HTML-sida som visar alla varor)
    @RequestMapping("/items")
    public String showAllItems(Model model) {
        List<Item> itemList = itemRepo.findAll();
        model.addAttribute("allItems", itemList);
        model.addAttribute("name", "Name:");
        model.addAttribute("price", "Price:");
        model.addAttribute("title", "All items in our database:");
        return "showAllItems";
    }

    // http://localhost:8080/graphics/items/{id} (Denna genererar HTML-sida som visar en vara baserat på varans id)
    @RequestMapping("/items/{id}")
    public String getItemById(@PathVariable long id, Model model) {
        List<Item> itemList = itemRepo.findAll().stream().filter(item -> item.getItemId()==(id)).toList();
        model.addAttribute("allItems", itemList);
        model.addAttribute("id", "Id:");
        model.addAttribute("name", "Name:");
        model.addAttribute("price", "Price:");
        model.addAttribute("title", "The item you required:");
        return "showItem";
    }
}
