package com.example.storeapi.Controllers;

import com.example.storeapi.Models.Customer;
import com.example.storeapi.Models.Item;
import com.example.storeapi.Models.Order;
import com.example.storeapi.Repos.CustomerRepo;
import com.example.storeapi.Repos.ItemRepo;
import com.example.storeapi.Repos.OrderRepo;
import com.example.storeapi.exceptions.ItemNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
public class ItemController {
    private final ItemRepo itemRepo;
    private final CustomerRepo customerRepo;
    private final OrderRepo orderRepo;
    private static final Logger log = LoggerFactory.getLogger(ItemController.class);

    public ItemController(ItemRepo itemRepo, CustomerRepo customerRepo, OrderRepo orderRepo) {
        this.itemRepo = itemRepo;
        this.customerRepo = customerRepo;
        this.orderRepo = orderRepo;
    }

    // http://localhost:8080/items (Denna returnerar alla varor)
    @GetMapping("items")
    public List<Item> getAllItems() {
        log.info("GET-ting all the items");
        return itemRepo.findAll();
    }

    // http://localhost:8080/items/{id} (Denna returnerar en vara baserat på varans id. Denna
    // endpoint ska använda path params)
    @GetMapping("items/{id}")
    public Item getItemById(@PathVariable long id) {
        log.info("GET-ting the item with the id " + id);
        return itemRepo.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
    }

    // http://localhost:8080/items (Denna endpoint skapar en ny vara)
    @PostMapping("items")
    public String addItem(@RequestBody Item itemToAdd) {
        if (itemAlreadyExists(itemToAdd)) {
            log.info("ERROR: Item " + itemToAdd.getName() + " for the price of " + itemToAdd.getPrice() + " ALREADY EXISTS IN THE DATABASE.");
            return "ERROR: Item " + itemToAdd.getName() + " for the price of " + itemToAdd.getPrice() + " ALREADY EXISTS IN THE DATABASE.";
        }
        itemRepo.save(itemToAdd);
        log.info("Item " + itemToAdd.getName() + " for the price of " + itemToAdd.getPrice() + " has been added to the database.");
        return "Item " + itemToAdd.getName() + " for the price of " + itemToAdd.getPrice() + " has been added to the database.";
    }

    // http://localhost:8080/items/buy (Denna endpoint gör ett nytt köp för en specifik kund och
    // en specifik vara, baserat på id).
    @PostMapping("items/buy")
    public String buyItem(@RequestParam Long customerId, @RequestParam Long itemId) {
        Customer customer = customerRepo.findAll().stream()
                .filter(currentCustomer -> Objects.equals(currentCustomer.getCustomerId(), customerId)).findFirst().orElse(null);
        Item item = itemRepo.findAll().stream()
                .filter(currentItem -> Objects.equals(currentItem.getItemId(), itemId)).findFirst().orElse(null);
        if (customer != null && item != null) {
            Timestamp timestamp = new Timestamp(new Date().getTime());
            Set<Item> items = new HashSet<>();
            items.add(item);
            orderRepo.save(new Order(timestamp, customer, items));
            log.info("Item " + item.getName() + " has been bought by the customer " + customer.getFirstName() + " " + customer.getLastName());
            return "Item " + item.getName() + " has been bought by the customer " + customer.getFirstName() + " " + customer.getLastName();
        }
        log.error("An error occurred while buying this item. Please check that CUSTOMER ID or ITEM ID are correct and exist.");
        return "An error occurred while buying this item. Please check that CUSTOMER ID or ITEM ID are correct and exist.";
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