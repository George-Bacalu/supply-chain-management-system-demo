package com.project.mocks;

import com.project.entity.Customer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerMock {

    public static Customer getMockedCustomer() {
        return Customer.builder()
                .customerId(1L)
                .name("Adidas")
                .phoneNumber("+40712345678")
                //.orders(List.of(getMockedOrder()))
                .build();
    }
}
