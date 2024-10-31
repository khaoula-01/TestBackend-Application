package com.tawtechs.testbackend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.tawtechs.testbackend.Entity.Customer;
import com.tawtechs.testbackend.Repository.CustomerRepository;
import com.tawtechs.testbackend.Service.CustomerService;
import com.tawtechs.testbackend.Service.CustomerServiceImpl; // Assume this is your implementation class
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService; // Use the implementation class

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhone("1234567890");

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer created = customerService.createCustomer(customer);

        assertThat(created.getName()).isEqualTo("John Doe");
        verify(customerRepository).save(customer);
    }

    @Test
    void testRetrieveCustomerById() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Optional<Customer> found = customerService.getCustomerById(1L);

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("John Doe");
        verify(customerRepository).findById(1L);
    }
    @Test
    void testRetrieveCustomerById_NotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Customer> result = customerService.getCustomerById(1L);

        assertThat(result).isEmpty(); // Assert that the result is empty
    }



}
