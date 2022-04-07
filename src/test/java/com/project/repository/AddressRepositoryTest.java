package com.project.repository;

import com.project.entity.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.project.mocks.AddressMock.getMockedAddress;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AddressRepositoryTest {

    Address address = getMockedAddress();

    @Autowired
    private AddressRepository addressRepositoryTest;

    @BeforeEach
    void setUp() {
        addressRepositoryTest.save(address);
    }

    @Test
    @DisplayName("getAllCustomers")
    public void getAllCustomers_shouldReturnAllCustomers() {
        assertThat(addressRepositoryTest.findAll().toString()).isEqualTo(List.of(address).toString());
    }

    @Test
    @DisplayName("findAddressByCity")
    void findAddressByCity_shouldReturnAddress_whenGivenCity() {
        assertThat(addressRepositoryTest.findAddressByCity("Brasov").toString()).isEqualTo(List.of(address).toString());
    }
}