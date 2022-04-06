package com.project.mocks;

import com.project.entity.Address;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressMock {

    public static Address getMockedAddress() {
        return Address.builder().addressId(1L).country("Romania").city("Brasov").street("Turnului").number(12).build();
    }
}
