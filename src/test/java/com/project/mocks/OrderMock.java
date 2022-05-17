package com.project.mocks;

import com.project.entity.Order;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.project.mocks.AddressMock.getMockedAddress;
import static com.project.mocks.CustomerMock.getMockedCustomer;
import static com.project.mocks.ProductMock.getMockedProductList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderMock {

   public static Order getMockedOrder() {
      return Order.builder()
              .orderId(1L)
              .totalPrice(18000.0)
              .createdAt(LocalDateTime.now().withNano(0))
              .updatedAt(LocalDateTime.now().withNano(0))
              .customer(getMockedCustomer())
              .products(getMockedProductList())
              .address(getMockedAddress())
              .build();
   }
}
