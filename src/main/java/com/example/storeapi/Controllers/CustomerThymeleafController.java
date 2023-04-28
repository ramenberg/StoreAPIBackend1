package com.example.storeapi.Controllers;

import com.example.storeapi.Models.Customer;
import com.example.storeapi.Repos.CustomerRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        return "customers.html";
    }

    public List<Customer> all() {
        return customerRepo.findAll();
    }
}
