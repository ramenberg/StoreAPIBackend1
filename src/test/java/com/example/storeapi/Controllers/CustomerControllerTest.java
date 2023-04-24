package com.example.storeapi.Controllers;

import com.example.storeapi.Models.Customer;
import com.example.storeapi.Repos.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {

    @Autowired
    private CustomerController customerController;
    @MockBean
    private CustomerRepo customerMockRepo;

    @BeforeEach
    void setUp() {
        Customer c1 = new Customer("John", "Doe", "1234567890");
        Customer c2 = new Customer("Jane", "Doe", "0987654321");
        when(customerMockRepo.findAll()).thenReturn(Arrays.asList(c1, c2));
        when(customerMockRepo.findById(1L)).thenReturn(Optional.of(c1));
        when(customerMockRepo.findById(2L)).thenReturn(Optional.of(c2));
        when(customerMockRepo.findById(3L)).thenReturn(Optional.empty());
    }

    @Test
    void allTestSizeShouldBeEqual() {
        List<Customer> customers = customerController.all();
        assertEquals(2, customers.size());
    }

    @Test
    void allTestFirstNameShouldBeEqual() {
        List<Customer> customers = customerController.all();
        assertEquals("John", customers.get(0).getFirstName());
    }
    @Test
    void one() {
        Customer customer = customerController.one(1L);
        assertEquals("John", customer.getFirstName());
    }
}