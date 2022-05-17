package com.project.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static com.project.mocks.OrderMock.getMockedOrder;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderRepositoryTest {

   @Autowired
   private OrderRepository orderRepository;

   @Test
   @DisplayName("saveOrder")
   void saveOrder_shouldReturnSavedOrder() {
      orderRepository.save(getMockedOrder());
   }

   @Test
   @DisplayName("getAllOrdersRepository")
   public void getAllOrders_repository_shouldReturnAllOrders() {
      assertThat(orderRepository.findAll().toString()).isEqualTo(List.of(getMockedOrder()).toString());
   }

   @Test
   @DisplayName("findOrderByCustomer_Name")
   public void findOrderByCustomer_Name_shouldReturnOrders_whenGivenName() {
      assertThat(orderRepository.findOrderByCustomer_Name("Adidas").toString()).isEqualTo(List.of(getMockedOrder()).toString());
   }

   @Test
   @DisplayName("findOrderByAddress_Country")
   public void findOrderByAddress_Country_shouldReturnOrders_whenGivenCountry() {
      assertThat(orderRepository.findOrderByAddress_Country("Romania").toString()).isEqualTo(List.of(getMockedOrder()).toString());
   }

   @Test
   @DisplayName("findOrderByAddress_City")
   public void findOrderByAddress_City_shouldReturnOrders_whenGivenCity() {
      assertThat(orderRepository.findOrderByAddress_City("Brasov").toString()).isEqualTo(List.of(getMockedOrder()).toString());
   }

   @Test
   @DisplayName("findOrderByAddress_AddressId")
   public void findOrderByAddress_AddressId_shouldReturnOrders_whenGivenName() {
      assertThat(orderRepository.findOrderByAddress_AddressId(1L).toString()).isEqualTo(List.of(getMockedOrder()).toString());
   }

   @Test
   @DisplayName("findOrderByCreatedAtBetween")
   public void findOrderByCreatedAtBetween_shouldReturnOrders_whenGivenName() {
     LocalDateTime start = LocalDateTime.of(2022, Month.JANUARY, 29, 19, 30, 40);
     LocalDateTime end = LocalDateTime.of(2022, Month.MAY, 29, 19, 30, 40);
      assertThat(orderRepository.findOrderByCreatedAtBetween(start, end).toString()).isEqualTo(List.of(getMockedOrder()).toString());
   }
}