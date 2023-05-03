package com.example.storeapi.Controllers;

import com.example.storeapi.Models.Customer;
import com.example.storeapi.Repos.CustomerRepo;
import com.example.storeapi.exceptions.CustomerNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/graphics")
public class CustomerThymeleafController {

    private final Logger log = Logger.getLogger(CustomerController.class.getName());
    private final CustomerRepo customerRepo;

    public CustomerThymeleafController(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }


    @GetMapping("/customers")
    public String customers(Model model) {
        log.info("GET all /customers");
        model.addAttribute("customers", this.all());
        return "customers";
    }

    public List<Customer> all() {
        return customerRepo.findAll();
    }

    public Customer one(@PathVariable Long id) {
        log.info("GET /customers/" + id);
        return customerRepo.findById(id).orElse(null);
    }

    @GetMapping("/customers/{id}")
    public String findOne(@PathVariable Long id, Model model) {
        log.info("GET /customers/" + id);
        Customer customer = one(id);
        if(customer == null) {
            model.addAttribute("id", id);
            return "notfound";
        } else {
            model.addAttribute("customer", customer);
            return "customer";
        }
    }

    @GetMapping("/customers/search")
    public String searchCustomer() {
        return "customer_search";
    }

    @GetMapping("/customers/notfound")
    public String notFound() {
        return "notfound";
    }
}
