package com.project.repository;

import com.project.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static com.project.mocks.AddressMock.getMockedAddress;
import static com.project.mocks.CustomerMock.getMockedCustomer;
import static com.project.mocks.OrderMock.getMockedOrder;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderRepositoryTest {

   Order order = getMockedOrder();

   @Autowired
   private OrderRepository orderRepositoryTest;

   @BeforeEach
   void setUp() {
      orderRepositoryTest.save(order);
   }

   @Test
   @DisplayName("getAllOrdersRepository")
   public void getAllOrders_repository_shouldReturnAllOrders() {
      assertThat(orderRepositoryTest.findAll().toString()).isEqualTo(List.of(order).toString());
   }

   @Test
   @DisplayName("findOrderByCustomer_Name")
   public void findOrderByCustomer_Name_shouldReturnOrders_whenGivenName() {
      assertThat(orderRepositoryTest.findOrderByCustomer_Name(getMockedCustomer().getName()).toString()).isEqualTo(List.of(order).toString());
   }

   @Test
   @DisplayName("findOrderByAddress_Country")
   public void findOrderByAddress_Country_shouldReturnOrders_whenGivenCountry() {
      assertThat(orderRepositoryTest.findOrderByAddress_Country(getMockedAddress().getCountry()).toString()).isEqualTo(List.of(order).toString());
   }

   @Test
   @DisplayName("findOrderByAddress_City")
   public void findOrderByAddress_City_shouldReturnOrders_whenGivenName() {
      assertThat(orderRepositoryTest.findOrderByAddress_City(getMockedAddress().getCity()).toString()).isEqualTo(List.of(order).toString());
   }

   @Test
   @DisplayName("findOrderByAddress_AddressId")
   public void findOrderByAddress_AddressId_shouldReturnOrders_whenGivenName() {
      assertThat(orderRepositoryTest.findOrderByAddress_AddressId(getMockedAddress().getAddressId()).toString()).isEqualTo(List.of(order).toString());
   }

   @Test
   @DisplayName("findOrderByCreatedAtBetween")
   public void findOrderByCreatedAtBetween_shouldReturnOrders_whenGivenName() {
     LocalDateTime start = LocalDateTime.of(2022, Month.JANUARY, 29, 19, 30, 40);
     LocalDateTime end = LocalDateTime.of(2022, Month.MAY, 29, 19, 30, 40);
      assertThat(orderRepositoryTest.findOrderByCreatedAtBetween(start, end).toString()).isEqualTo(List.of(order).toString());
   }
}