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
import java.util.Objects;

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
    @GetMapping("/items")
    public String showAllItems(Model model) {
        List<Item> itemList = itemRepo.findAll();
        model.addAttribute("allItems", itemList);
        model.addAttribute("id", "Id:");
        model.addAttribute("name", "Name:");
        model.addAttribute("price", "Price:");
        model.addAttribute("title", "All items in our database:");
        return "showAllItems";
    }

    // http://localhost:8080/graphics/items/{id} (Denna genererar HTML-sida som visar en vara baserat på varans id)
    @GetMapping("/items/{id}")
    public String showItemById(@PathVariable long id, Model model) {
        List<Item> itemList = itemRepo.findAll().stream().filter(item -> item.getItemId() == (id)).toList();
        model.addAttribute("allItems", itemList);
        model.addAttribute("id", "Id:");
        model.addAttribute("name", "Name:");
        model.addAttribute("price", "Price:");
        model.addAttribute("title", "The item you required:");
        return "showItem";
    }

    // http://localhost:8080/graphics/items/add (Denna endpoint genererar HTML-sida som skapar en ny vara)
    @RequestMapping("/items/add")
    public String addItem(Model model) {
        model.addAttribute("title", "Please enter information about the new product:");
        return "createItem";
    }

    @PostMapping("/createItem")
    public String createItem(@RequestParam String name, @RequestParam double price, Model model) {
        itemRepo.save(new Item(name, price));
        return showAllItems(model);
    }

    @RequestMapping("/deleteItem")
    public String getAllItemsAfterDelete(Model model) {
        List<Item> itemList = itemRepo.findAll();
        model.addAttribute("allItems", itemList);
        model.addAttribute("id", "Id:");
        model.addAttribute("name", "Name:");
        model.addAttribute("price", "Price:");
        model.addAttribute("title", "All items in our database:");
        return "showAllItems";
    }

    @RequestMapping(path = "/deleteItemById/{id}")
    public String deleteItem(@PathVariable Long id, Model model) {
        itemRepo.deleteById(id);
        return getAllItemsAfterDelete(model);
    }

    public Boolean itemAlreadyExists(Item itemToCheck) {
        List<Item> itemsWithSameName = itemRepo.findAll().stream().filter(currentItem ->
                currentItem.getName().equals(itemToCheck.getName())).toList();
        if (itemsWithSameName.size() != 0) {
            List<Item> itemsWithTheSamePrice = itemsWithSameName.stream().filter(currentItem ->
                    Objects.equals(currentItem.getPrice(), itemToCheck.getPrice())).toList();
            return itemsWithTheSamePrice.size() != 0;
        }
        return false;
    }
}