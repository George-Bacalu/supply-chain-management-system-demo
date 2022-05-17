package com.project.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.project.mocks.CustomerMock.getMockedCustomer;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("saveCustomer")
    public void saveCustomer_shouldReturnSavedCustomer() {
        customerRepository.save(getMockedCustomer());
    }

    @Test
    @DisplayName("getAllCustomers")
    public void getAllCustomers_shouldReturnAllCustomers() {
        assertThat(customerRepository.findAll().toString()).isEqualTo(List.of(getMockedCustomer()).toString());
    }

    @Test
    @DisplayName("findCustomerByName")
    void findCustomerByName_shouldReturnCustomer_whenGivenName() {
        assertThat(customerRepository.findCustomerByName("Adidas").toString()).isEqualTo(getMockedCustomer().toString());
    }

    @Test
    @DisplayName("findCustomerByPhoneNumber")
    void findCustomerByPhoneNumber_shouldReturnCustomer_whenGivenPhoneNumber() {
        assertThat(customerRepository.findCustomerByPhoneNumber("+40712345678").toString()).isEqualTo(getMockedCustomer().toString());
    }
}