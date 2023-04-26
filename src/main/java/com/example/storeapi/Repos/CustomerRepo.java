package com.example.storeapi.Repos;

import com.example.storeapi.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Customer findBySsn(String ssn);
}
