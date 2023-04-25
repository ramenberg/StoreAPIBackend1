package com.example.storeapi;

import com.example.storeapi.Models.Customer;
import com.example.storeapi.Models.Item;
import com.example.storeapi.Models.Order;
import com.example.storeapi.Repos.CustomerRepo;
import com.example.storeapi.Repos.ItemRepo;
import com.example.storeapi.Repos.OrderRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Logger;

@SpringBootApplication
public class StoreApiApplication {

    private final Logger log = Logger.getLogger(StoreApiApplication.class.getName());
    private final Timestamp today = new Timestamp(new Date().getTime());


    public static void main(String[] args) {
        SpringApplication.run(StoreApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner prepDb(CustomerRepo customerRepo, ItemRepo itemRepo, OrderRepo orderRepo) {
        return (args) -> {

            // Kontrollerar om något repo har innehåll. Då körs inte prep igen.
            if (itemRepo.count() > 0 || customerRepo.count() > 0 || orderRepo.count() > 0) {
                log.info("Prep items already in db");
            } else {
                log.info("Prep items" +
                itemRepo.save(new Item("Ananas", 25.0)) + " " +
                itemRepo.save(new Item("Gurka", 17.9)) + " " +
                itemRepo.save(new Item("Kaffe", 109.0)));

                Customer c1 = new Customer("David", "Smith", "123456");
                Customer c2 = new Customer("Nana", "Yamamoto", "456789");
                Customer c3 = new Customer("Erik", "Ljunggren", "789123");

                customerRepo.save(c1);
                customerRepo.save(c2);
                customerRepo.save(c3);
                log.info("Prep customers" + c1 + " " + c2 + " " + c3);

                orderRepo.save(new Order(today,c1));
                orderRepo.save(new Order(today,c2));
                orderRepo.save(new Order(today,c2));
            }
        };
    }
}