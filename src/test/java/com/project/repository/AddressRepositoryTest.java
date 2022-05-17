package com.project.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.project.mocks.AddressMock.getMockedAddress;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    @DisplayName("saveAddress")
    public void saveAddress_shouldReturnSavedAddress() {
        addressRepository.save(getMockedAddress());
    }

    @Test
    @DisplayName("getAllAddresses")
    public void getAllAddresses_shouldReturnAllAddresses() {
        assertThat(addressRepository.findAll().toString()).isEqualTo(List.of(getMockedAddress()).toString());
    }

    @Test
    @DisplayName("findAddressByCity")
    void findAddressByCity_shouldReturnAddress_whenGivenCity() {
        assertThat(addressRepository.findAddressByCity("Brasov").toString()).isEqualTo(List.of(getMockedAddress()).toString());
    }
}