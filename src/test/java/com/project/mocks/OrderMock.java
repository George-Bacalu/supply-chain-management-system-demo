package com.project.mocks;

import com.project.entity.Address;
import com.project.entity.Customer;
import com.project.entity.Order;
import com.project.entity.Product;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class OrderMock {

   public static Order getMockedOrder() {
      Product product1 = Product.builder().productId(1L).name("Shoe order").price(80.0).quantity(100).build();
      Product product2 = Product.builder().productId(2L).name("T-shirt order").price(50.0).quantity(200).build();
      Customer customer = Customer.builder().customerId(1L).name("Adidas").phoneNumber("0000000000").build();
      Address address = Address.builder().addressId(1L).country("Country 1").city("City 1").street("Street 1").number(1).build();
      return Order.builder()
              .orderId(1L)
              .totalPrice(18000.0)
              .createdAt(LocalDateTime.now())
              .updatedAt(LocalDateTime.now())
              .customer(customer)
              .products(List.of(product1, product2))
              .address(address)
              .build();
   }
}
