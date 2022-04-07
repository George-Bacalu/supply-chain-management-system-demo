package com.project.service.impl;

import com.project.entity.Customer;
import com.project.entity.Order;
import com.project.exception.ResourceNotFoundException;
import com.project.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.project.constant.ClientConstants.ORDER_WITH_ID_NOT_FOUND;
import static com.project.mocks.OrderMock.getMockedOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

   Order order = getMockedOrder();

   @Mock
   private OrderRepository orderRepositoryTest;

   @InjectMocks
   private OrderServiceImpl orderServiceTest;

   @BeforeEach
   void setUp() {
      orderServiceTest.saveOrder(order);
   }

   @Test
   @DisplayName("getAllOrdersService")
   public void getAllOrders_service_shouldReturnAllOrders() {
      orderServiceTest.getAllOrders();

      given(orderRepositoryTest.findAll()).willReturn(List.of(order));

      assertThat(orderRepositoryTest.findAll().toString()).isEqualTo(List.of(order).toString());
      verify(orderRepositoryTest).findAll();
   }

   @Test
   @DisplayName("getOrderByIdValid")
   public void getOrderById_shouldReturnOrder_whenGivenIdToGetOrderIsValid() {
      Long orderId = 1L;
      given(orderRepositoryTest.existsById(orderId)).willReturn(true);
      orderServiceTest.getOrderById(orderId);

      assertThat(orderRepositoryTest.getById(orderId).toString()).isEqualTo(List.of(order).toString());
      verify(orderRepositoryTest).findById(orderId);
   }

   @Test
   @DisplayName("getOrderByIdInvalid")
   public void getOrderById_shouldThrowException_whenGivenIdToGetOrderIsInvalid() {
      Long orderId = 1L;
      given(orderRepositoryTest.existsById(orderId)).willReturn(false);

      assertThatThrownBy(() -> orderServiceTest.getOrderById(orderId))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessageContaining(ORDER_WITH_ID_NOT_FOUND);

      verify(orderRepositoryTest, never()).findById(orderId);
   }

   @Test
   @DisplayName("saveOrder")
   public void saveOrder_shouldReturnOrder() {
      ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
      verify(orderRepositoryTest).save(orderArgumentCaptor.capture());

      Order capturedOrder = orderArgumentCaptor.getValue();
      assertThat(capturedOrder.toString()).isEqualTo(order.toString());
   }

   @Test
   @DisplayName("updateOrderByIdValid")
   public void updateOrderById_shouldReturnOrder_whenGivenIdToUpdateOrderIsValid() {
      order.setOrderId(2L);
      Customer customer = order.getCustomer();
      customer.setName("Nike");
      order.setCustomer(customer);

      Order newOrder = new Order();
      customer.setName("Reebok");
      order.setCustomer(customer);

      given(orderRepositoryTest.findById(order.getOrderId())).willReturn(Optional.of(order));

      orderServiceTest.updateOrderById(newOrder, order.getOrderId());
   }

   @Test
   @DisplayName("updateOrderByIdInvalid")
   public void updateOrderById_shouldThrowException_whenGivenIdToUpdateOrderIsInvalid() {
      order.setOrderId(2L);
      Customer customer = order.getCustomer();
      customer.setName("Nike");
      order.setCustomer(customer);

      Order newOrder = new Order();
      customer.setName("Reebok");
      order.setCustomer(customer);

      assertThatThrownBy(() -> orderServiceTest.updateOrderById(newOrder, order.getOrderId()))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessageContaining(ORDER_WITH_ID_NOT_FOUND);

      verify(orderRepositoryTest, never()).save(order);
   }

   @Test
   @DisplayName("deleteOrderByIdIsValid")
   public void deleteOrderById_shouldReturnConfirmMessage_whenGivenIdToDeleteOrderIsValid() {
      Long orderId = 1L;
      given(orderRepositoryTest.existsById(orderId)).willReturn(true);

      orderServiceTest.deleteOrderById(orderId);

      verify(orderRepositoryTest).deleteById(orderId);
   }

   @Test
   @DisplayName("deleteOrderByIdIsInValid")
   public void deleteOrderById_shouldReturnConfirmMessage_whenGivenIdToDeleteOrderIsInvalid() {
      Long orderId = 1L;
      given(orderRepositoryTest.existsById(orderId)).willReturn(false);

      assertThatThrownBy(() -> orderServiceTest.deleteOrderById(orderId))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessageContaining(ORDER_WITH_ID_NOT_FOUND);

      verify(orderRepositoryTest, never()).deleteById(orderId);
   }
}