package com.example.storeapi.Controllers;

import com.example.storeapi.Models.Customer;
import com.example.storeapi.Models.Item;
import com.example.storeapi.Models.Order;
import com.example.storeapi.Repos.CustomerRepo;
import com.example.storeapi.Repos.ItemRepo;
import com.example.storeapi.Repos.OrderRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    @RequestMapping("items")
    public List<Item> getAllItems() {
        return itemRepo.findAll();
    }

    // http://localhost:8080/items/{id} (Denna returnerar en vara baserat på varans id. Denna
    // endpoint ska använda path params)
    @RequestMapping("items/{id}")
    public Item getItemById(@PathVariable long id) {
        return itemRepo.findById(id).get();
    }

    // http://localhost:8080/items (Denna endpoint skapar en ny vara)
    @RequestMapping("items/add")
    public String addItem(@RequestParam String name, @RequestParam double price) {
        itemRepo.save(new Item(name, price));
        log.info("Item " + name + " for the price of " + price + " has been added to the database");
        return "Item " + name + " for the price of " + price + " has been added to the database";
    }

    // http://localhost:8080/items/buy (Denna endpoint gör ett nytt köp för en specifik kund och
    // en specifik vara, baserat på id).
    @RequestMapping("items/buy")
    public String buyItem(@RequestParam Long customerId, @RequestParam Long itemId) {
        Customer customer = customerRepo.findAll().stream()
                .filter(currentCustomer -> Objects.equals(currentCustomer.getCustomerId(), customerId)).findFirst().orElse(null);
        Item item = itemRepo.findAll().stream()
                .filter(currentItem -> Objects.equals(currentItem.getItemId(), itemId)).findFirst().orElse(null);
        if (customer != null && item != null) {
            LocalDateTime localDateTimeNow = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(localDateTimeNow);
            log.info(localDateTimeNow.toString());
            Set<Item> items = new HashSet<>();
            items.add(item);
            orderRepo.save(new Order(timestamp, customer, items));
            log.info("Item " + item.getName() + " has been bought by the customer " + customer.getFirstName() + " " + customer.getLastName());
            return "Item " + item.getName() + " has been bought by the customer " + customer.getFirstName() + " " + customer.getLastName();
        }
        log.error("An error occurred while buying this item. Please check that CUSTOMER ID or ITEM ID are correct and exist.");
        return "An error occurred while buying this item. Please check that CUSTOMER ID or ITEM ID are correct and exist.";
    }
}
