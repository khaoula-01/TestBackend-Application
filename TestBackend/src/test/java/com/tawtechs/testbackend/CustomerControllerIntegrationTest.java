package com.tawtechs.testbackend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tawtechs.testbackend.Entity.Customer;
import com.tawtechs.testbackend.Repository.CustomerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ExtendWith(SpringExtension.class)
public class CustomerControllerIntegrationTest {

    @Container
    public static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("test")
            .withUsername("user")
            .withPassword("password");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();

        // Create a test customer for reuse in multiple tests
        testCustomer = new Customer();
        testCustomer.setName("John Doe");
        testCustomer.setEmail("john.doe@example.com");
        testCustomer.setPhone("1234567890");
        testCustomer.setAddress("123 Main St");
    }

    @Test
    void testCreateCustomer_Success() throws Exception {
        String customerJson = objectMapper.writeValueAsString(testCustomer);

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(testCustomer.getName()))
                .andExpect(jsonPath("$.email").value(testCustomer.getEmail()))
                .andExpect(jsonPath("$.phone").value(testCustomer.getPhone()))
                .andExpect(jsonPath("$.address").value(testCustomer.getAddress()))
                .andExpect(jsonPath("$.id").exists())
                .andDo(print());
    }



    @Test
    void testGetCustomer_Success() throws Exception {
        Customer savedCustomer = customerRepository.save(testCustomer);

        mockMvc.perform(get("/customers/{id}", savedCustomer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testCustomer.getName()))
                .andExpect(jsonPath("$.email").value(testCustomer.getEmail()))
                .andExpect(jsonPath("$.phone").value(testCustomer.getPhone()))
                .andExpect(jsonPath("$.address").value(testCustomer.getAddress()))
                .andDo(print());
    }

    @Test
    void testGetCustomer_NotFound() throws Exception {
        mockMvc.perform(get("/customers/{id}", 999L))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void testGetAllCustomers() throws Exception {
        customerRepository.save(testCustomer);

        // Create and save another customer
        Customer customer2 = new Customer();
        customer2.setName("Jane Doe");
        customer2.setEmail("jane.doe@example.com");
        customer2.setPhone("0987654321");
        customer2.setAddress("456 Oak St");
        customerRepository.save(customer2);

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("John Doe", "Jane Doe")))
                .andDo(print());
    }

    @Test
    void testUpdateCustomer_Success() throws Exception {
        Customer savedCustomer = customerRepository.save(testCustomer);
        savedCustomer.setName("John Updated");
        String updatedCustomerJson = objectMapper.writeValueAsString(savedCustomer);

        mockMvc.perform(put("/customers/{id}", savedCustomer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCustomerJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Updated"))
                .andExpect(jsonPath("$.email").value(testCustomer.getEmail()))
                .andDo(print());
    }

    @Test
    void testUpdateCustomer_NotFound() throws Exception {
        String customerJson = objectMapper.writeValueAsString(testCustomer);

        mockMvc.perform(put("/customers/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void testDeleteCustomer_Success() throws Exception {
        Customer savedCustomer = customerRepository.save(testCustomer);

        mockMvc.perform(delete("/customers/{id}", savedCustomer.getId()))
                .andExpect(status().isNoContent())
                .andDo(print());

        // Verify customer is deleted
        mockMvc.perform(get("/customers/{id}", savedCustomer.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCustomer_NotFound() throws Exception {
        mockMvc.perform(delete("/customers/{id}", 999L))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


}
