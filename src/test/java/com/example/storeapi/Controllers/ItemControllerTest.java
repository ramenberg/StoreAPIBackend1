package com.example.storeapi.Controllers;

import com.example.storeapi.Models.Customer;
import com.example.storeapi.Models.Item;
import com.example.storeapi.Repos.CustomerRepo;
import com.example.storeapi.Repos.ItemRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemRepo mockItemRepo;
    @MockBean
    private CustomerRepo mockCustomerRepo;


    @BeforeEach
    public void init() {
        Item i1 = new Item(1L, "Ananas", 25.0, null);
        Item i2 = new Item(2L, "Gurka", 17.9, null);
        Item i3 = new Item(3L, "Kaffe", 109.0, null);
        Item i4 = new Item(4L, "Choklad", 35.0, null);
        Item i5 = new Item(5L, "Té med smak av vilda bär", 34.0, null);
        Item i6 = new Item(6L, "Ost", 89.0, null);
        Item i7 = new Item(7L, "Smör", 55.0, null);
        Item i8 = new Item(8L, "Bröd med frukt och nötter", 69.0, null);
        Item i9 = new Item(9L, "Lantmjölk", 22.0, null);
        Item i10 = new Item(10L, "Apelsiner", 20.0, null);
        Item i11 = new Item(11L, "Bananer", 25.0, null);
        Item i12 = new Item(12L, "Potatis Amandine", 28.0, null);

        Customer c1 = new Customer(1L,"David", "Smith", "123456",null);
        Customer c2 = new Customer(2L,"Nana", "Yamamoto", "456789",null);
        Customer c3 = new Customer(3L,"Erik", "Ljunggren", "789123",null);

        when(mockItemRepo.findById(1L)).thenReturn(Optional.of(i1));
        when(mockItemRepo.findById(2L)).thenReturn(Optional.of(i2));
        when(mockItemRepo.findById(3L)).thenReturn(Optional.of(i3));
        when(mockItemRepo.findById(4L)).thenReturn(Optional.of(i4));
        when(mockItemRepo.findById(5L)).thenReturn(Optional.of(i5));
        when(mockItemRepo.findById(6L)).thenReturn(Optional.of(i6));
        when(mockItemRepo.findById(7L)).thenReturn(Optional.of(i7));
        when(mockItemRepo.findById(8L)).thenReturn(Optional.of(i8));
        when(mockItemRepo.findById(9L)).thenReturn(Optional.of(i9));
        when(mockItemRepo.findById(10L)).thenReturn(Optional.of(i10));
        when(mockItemRepo.findById(11L)).thenReturn(Optional.of(i11));
        when(mockItemRepo.findById(12L)).thenReturn(Optional.of(i12));

        when(mockCustomerRepo.findById(1L)).thenReturn(Optional.of(c1));
        when(mockCustomerRepo.findById(2L)).thenReturn(Optional.of(c2));
        when(mockCustomerRepo.findById(3L)).thenReturn(Optional.of(c3));

        when(mockItemRepo.findAll()).thenReturn(Arrays.asList(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12));
        when(mockCustomerRepo.findAll()).thenReturn(Arrays.asList(c1, c2, c3));
    }

    @Test
    void getAllItems() throws Exception {
        this.mvc.perform(get("/items")).andExpect(status().isOk()).
                andExpect(content().json("[{\"name\":\"Ananas\",\"itemId\":1,\"orders\":null,\"price\":25.0}," +
                        "{\"name\":\"Gurka\",\"orders\":null,\"itemId\":2,\"price\":17.9}," +
                        "{\"name\":\"Kaffe\",\"orders\":null,\"itemId\":3,\"price\":109.0}," +
                        "{\"name\":\"Choklad\",\"orders\":null,\"itemId\":4,\"price\":35.0}," +
                        "{\"name\":\"Té med smak av vilda bär\",\"orders\":null,\"itemId\":5,\"price\":34.0}," +
                        "{\"name\":\"Ost\",\"orders\":null,\"itemId\":6,\"price\":89.0}," +
                        "{\"name\":\"Smör\",\"orders\":null,\"itemId\":7,\"price\":55.0}," +
                        "{\"name\":\"Bröd med frukt och nötter\",\"orders\":null,\"itemId\":8,\"price\":69.0}," +
                        "{\"name\":\"Lantmjölk\",\"orders\":null,\"itemId\":9,\"price\":22.0}," +
                        "{\"name\":\"Apelsiner\",\"orders\":null,\"itemId\":10,\"price\":20.0}," +
                        "{\"name\":\"Bananer\",\"orders\":null,\"itemId\":11,\"price\":25.0}," +
                        "{\"name\":\"Potatis Amandine\",\"orders\":null,\"itemId\":12,\"price\":28.0}]"));
    }

    @Test
    void getItemById() throws Exception {
        this.mvc.perform(get("/items/8")).andExpect(status().isOk()).
                andExpect(content().json("{\"name\":\"Bröd med frukt och nötter\",\"orders\":null,\"itemId\":8,\"price\":69.0}"));
    }

    //Två tester för att kontroller endpoint addItem. Den ena testen tar in en vara som inte existerar i databasen medan den andra tar in en vara som gör det.
    //Därför olika utskrifter.
    @Test
    void addItemWhenDoesntExist() throws Exception {
        this.mvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Äpple\",\"id\":13,\"price\":10.0}")).andExpect(status().isOk()).
                andExpect(content().string(equalTo("Item Äpple for the price of 10.0 has been added to the database.")));

    }

    @Test
    void addItemWhenExists() throws Exception {
        this.mvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Potatis Amandine\",\"id\":12,\"price\":28.0}")).andExpect(status().isOk()).
                andExpect(content().string(equalTo("ERROR: Item Potatis Amandine for the price of 28.0 ALREADY EXISTS IN THE DATABASE.")));

    }

    //x-www-form-urlencoded
    //customerId=3&itemId=8
    /*
    @Test
    void buyItem() throws Exception {
        this.mvc.perform(post("/items/buy")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("customerId", "3").param("itemId", "8")).andExpect(status().isOk()).
                andExpect(content().string(equalTo("Item Bröd med frukt och nötter has been bought by the customer Erik Ljunggren")));
    }
    */
    /*
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
     */
}