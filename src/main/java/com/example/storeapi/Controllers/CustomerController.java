package com.example.storeapi.Controllers;

import com.example.storeapi.Models.Customer;
import com.example.storeapi.Repos.CustomerRepo;
import com.example.storeapi.exceptions.CustomerNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class CustomerController {

    private final Logger log = Logger.getLogger(CustomerController.class.getName());
    private final CustomerRepo customerRepo;

    public CustomerController(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @GetMapping("/customers")
    public List<Customer> all() {
        log.info("GET all /customers");
        return customerRepo.findAll();
    }

    @GetMapping("/customers/{id}")
    public Customer one(@PathVariable Long id) {
        log.info("GET /customers/" + id);
        return customerRepo.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @PostMapping("/customers")
    public Customer newCustomer(@RequestBody Customer newCustomer) {
        // Kontrollera att kund med ssn(unikt) inte redan finns
        if (customerWithSsnAlreadyExists(newCustomer.getSsn())) {
            log.info("Customer with ssn " + newCustomer.getSsn() + " already exists.");
            return customerRepo.findBySsn(newCustomer.getSsn());
        } // Kontrollera att alla fält är ifyllda
        else if (customerDoesNotHaveAllRequiredFields(newCustomer)) {
            log.info("Invalid customer: " + newCustomer + ".");
            return newCustomer;
        } else {
            log.info("Successfully created new customer: " + newCustomer + ".");
            return customerRepo.save(newCustomer);
        }
    }

    public Boolean customerWithSsnAlreadyExists(String ssn) {
        return customerRepo.findBySsn(ssn) != null;
    }
    public Boolean customerDoesNotHaveAllRequiredFields(Customer customer) {
        return customer.getFirstName() == null &&
                customer.getLastName() == null &&
                customer.getSsn() == null;
    }
}
