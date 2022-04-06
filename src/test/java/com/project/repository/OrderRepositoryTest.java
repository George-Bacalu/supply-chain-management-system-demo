package com.project.repository;

import com.project.entity.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.project.mocks.OrderMock.getMockedOrder;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderRepositoryTest {

   Order order = getMockedOrder();

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
}