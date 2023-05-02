package com.example.storeapi;

import com.example.storeapi.Models.Customer;
import com.example.storeapi.Models.Item;
import com.example.storeapi.Models.Order;
import com.example.storeapi.Models.OrderItem;
import com.example.storeapi.Repos.CustomerRepo;
import com.example.storeapi.Repos.ItemRepo;
import com.example.storeapi.Repos.OrderItemRepo;
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
    public CommandLineRunner prepDb(CustomerRepo customerRepo, ItemRepo itemRepo, OrderRepo orderRepo, OrderItemRepo orderItemRepo) {
        return (args) -> {

            // Kontrollerar om något repo har innehåll. Då körs inte prep igen.
            if (itemRepo.count() > 0 || customerRepo.count() > 0 || orderRepo.count() > 0 || orderItemRepo.count() > 0) {
                log.info("Prep items already in db");
            } else {

                Item i1 = new Item("Ananas", 25.0);
                Item i2 = new Item("Gurka", 17.9);
                Item i3 = new Item("Kaffe", 109.0);
                Item i4 = new Item("Choklad", 35.0);
                Item i5 = new Item("Té med smak av vilda bär", 34.0);
                Item i6 = new Item("Ost", 89.00);
                Item i7 = new Item("Smör", 55.0);
                Item i8 = new Item("Bröd med frukt och nötter", 69.0);
                Item i9 = new Item("Lantmjölk", 22.0);
                Item i10 = new Item("Apelsiner", 20.0);
                Item i11 = new Item("Bananer", 20.0);
                Item i12 = new Item("Potatis Amandine", 28.0);

                itemRepo.save(i1);
                itemRepo.save(i2);
                itemRepo.save(i3);
                itemRepo.save(i4);
                itemRepo.save(i5);
                itemRepo.save(i6);
                itemRepo.save(i7);
                itemRepo.save(i8);
                itemRepo.save(i9);
                itemRepo.save(i10);
                itemRepo.save(i11);
                itemRepo.save(i12);

            log.info("Prep items " +
                    i1 + " " + i2 + " " + i3 + " " + i4 + " " + i5 +
                    " " + i6 + " " + i7 + " " + i8 + " " + i9 + " " +
                    i10 + " " + i11 + " " + i12);


                Customer c1 = new Customer("David", "Smith", "123456");
                Customer c2 = new Customer("Nana", "Yamamoto", "456789");
                Customer c3 = new Customer("Erik", "Ljunggren", "789123");

                customerRepo.save(c1);
                customerRepo.save(c2);
                customerRepo.save(c3);
                log.info("Prep customers" + c1 + " " + c2 + " " + c3);

                Order o1 = new Order(today,c1);
                Order o2= new Order(today,c2);
                Order o3 = new Order(today,c3);
                orderRepo.save(o1);
                orderRepo.save(o2);
                orderRepo.save(o3);
//
                OrderItem orderItem1 = new OrderItem(o1, i1,1);
                OrderItem orderItem2 = new OrderItem(o2, i2,1);
                OrderItem orderItem3 = new OrderItem(o3, i3,1);
                orderItemRepo.save(orderItem1);
                orderItemRepo.save(orderItem2);
                orderItemRepo.save(orderItem3);
            }
        };
    }
}