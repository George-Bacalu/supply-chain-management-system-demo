package com.project.repository;

import com.project.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.project.mocks.CustomerMock.getMockedCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerRepositoryTest {

    Customer customer = getMockedCustomer();

    @Autowired
    private CustomerRepository customerRepositoryTest;

    @BeforeEach
    void setUp() {
        customerRepositoryTest.save(customer);
    }

    @Test
    @DisplayName("getAllCustomers")
    public void getAllCustomers_shouldReturnAllCustomers() {
        assertThat(customerRepositoryTest.findAll().toString()).isEqualTo(List.of(customer).toString());
    }

    @Test
    @DisplayName("findCustomerByName")
    void findCustomerByName_shouldReturnCustomer_whenGivenName() {
        assertThat(customerRepositoryTest.findCustomerByName("Adidas").toString()).isEqualTo(customer.toString());
    }

    @Test
    @DisplayName("findCustomerByPhoneNumber")
    void findCustomerByPhoneNumber_shouldReturnCustomer_whenGivenPhoneNumber() {
        assertThat(customerRepositoryTest.findCustomerByPhoneNumber("0000000000").toString()).isEqualTo(customer.toString());
    }
}