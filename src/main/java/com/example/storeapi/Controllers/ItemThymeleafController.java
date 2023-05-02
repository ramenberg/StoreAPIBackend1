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
        if (itemList.size() != 0) {
            model.addAttribute("allItems", itemList);
            model.addAttribute("id", "Id:");
            model.addAttribute("name", "Name:");
            model.addAttribute("price", "Price:");
            model.addAttribute("title", "The item you required:");
            return "showItem";
        } else {
            model.addAttribute("title", "The item you required:");
            model.addAttribute("info", "There is no item in the system matching the id " + id + ".");
            return "noCorrespondingItem";
        }
    }

    // http://localhost:8080/graphics/items/add (Denna endpoint genererar HTML-sida som skapar en ny vara)
    @RequestMapping("/items/add")
    public String addItemThymeleaf(Model model) {
        model.addAttribute("title", "Please enter information about the new product:");
        return "createItem";
    }

    @PostMapping("/createItem")
    public String createItem(@RequestParam String name, @RequestParam double price, Model model) {
        if (!itemAlreadyExists(name, price)) {
            itemRepo.save(new Item(name, price));
            return showAllItems(model);
        } else {
            model.addAttribute("title", "Please enter information about the new product:");
            model.addAttribute("info", "ERROR: Item " + name +
                    " for the price of " + price + " already exists in the database.");
            return "itemAlreadyExists";
        }
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

    public Boolean itemAlreadyExists(String name, double price) {
        List<Item> itemsWithSameName = itemRepo.findAll().stream().filter(currentItem ->
                currentItem.getName().equals(name)).toList();
        if (itemsWithSameName.size() != 0) {
            List<Item> itemsWithTheSamePrice = itemsWithSameName.stream().filter(currentItem ->
                    Objects.equals(currentItem.getPrice(), price)).toList();
            return itemsWithTheSamePrice.size() != 0;
        }
        return false;
    }
}