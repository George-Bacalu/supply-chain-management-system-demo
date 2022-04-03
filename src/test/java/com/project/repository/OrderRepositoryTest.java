package com.project.repository;

import com.project.entity.DeliveryAddress;
import com.project.entity.Order;
import com.project.entity.OrderItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderRepositoryTest {

   OrderItem item1 = OrderItem.builder().name("Shoe order").quantity(100).build();
   OrderItem item2 = OrderItem.builder().name("T-shirt order").quantity(200).build();
   DeliveryAddress deliveryAddress = DeliveryAddress.builder().country("Country 1").city("City 1").street("Street 1").phoneNumber("0000000000").build();
   Order order = Order.builder().customer("Adidas").customerId(1L).orderItems(List.of(item1, item2)).deliveryAddress(deliveryAddress).build();

   @Autowired
   private OrderRepository orderRepositoryTest;

   @Test
   @DisplayName("saveOrder")
   public void whenGivenOrder_shouldSaveOrder() {
      orderRepositoryTest.save(order);
   }

   @Test
   @DisplayName("getAllOrders")
   public void shouldReturnAllOrders() {
      assertThat(orderRepositoryTest.findAll()).isEqualTo(List.of(order));
   }

   @Test
   @DisplayName("getOrderByCustomer")
   public void whenGivenCustomer_shouldReturnOrder() {
      assertThat(orderRepositoryTest.findByCustomer("Adidas")).isEqualTo(List.of(order));
   }

   @Test
   @DisplayName("findOrderByCustomerContaining")
   public void whenGivenCustomerContaining_shouldReturnOrder() {
      assertThat(orderRepositoryTest.findByCustomerContaining("Adi")).isEqualTo(List.of(order));
   }

   @Test
   @DisplayName("findCustomerByCustomerId")
   public void whenGivenCustomerId_shouldReturnOrderCustomer() {
      assertThat(orderRepositoryTest.getOrderCustomerByCustomerId(1L)).isEqualTo(order.getCustomerId());
   }
}