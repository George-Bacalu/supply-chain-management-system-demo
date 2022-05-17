package com.project.service.impl;

import com.project.entity.Customer;
import com.project.entity.Order;
import com.project.exception.OrderNotFoundException;
import com.project.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.project.constant.ClientConstants.ORDER_WITH_ID_NOT_FOUND;
import static com.project.mocks.OrderMock.getMockedOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

   private static final Long orderId = 1L;
   private final static Order order = getMockedOrder();

   @Mock
   private OrderRepository orderRepository;
   @InjectMocks
   private OrderServiceImpl orderService;

   @Captor
   ArgumentCaptor<Order> orderCaptor;

   @Test
   @DisplayName("saveOrder")
   public void saveOrder_shouldReturnOrder() {
      /*
      ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
      verify(orderRepository).save(orderArgumentCaptor.capture());

      Order capturedOrder = orderArgumentCaptor.getValue();
      assertThat(capturedOrder.toString()).isEqualTo(order.toString());
       */

      given(orderRepository.save(getMockedOrder())).willReturn(getMockedOrder());

      verify(orderRepository).save(orderCaptor.capture());

      assertThat(orderCaptor.getValue()).isEqualTo(getMockedOrder());
   }

   @Test
   @DisplayName("getAllOrders")
   public void getAllOrders_shouldReturnAllOrders() {
      given(orderRepository.findAll()).willReturn(List.of(order));
      orderService.getAllOrders();

      assertThat(orderRepository.findAll().toString()).isEqualTo(List.of(order).toString());
      verify(orderRepository).findAll();
   }

   @Test
   @DisplayName("whenOrderWithIdExists_getOrderById")
   public void getOrderById_shouldReturnOrder_whenOrderWithIdExists() {
      given(orderRepository.existsById(orderId)).willReturn(true);
      orderService.getOrderById(orderId);

      assertThat(orderRepository.getById(orderId).toString()).isEqualTo(List.of(order).toString());
      verify(orderRepository).findById(orderId);
   }

   @Test
   @DisplayName("whenOrderWithIdDoesNotExist_getOrderById_shouldThrowException")
   public void getOrderById_shouldThrowException_whenOrderWithIdDoesNotExist() {
      given(orderRepository.existsById(orderId)).willReturn(false);

      /*
      assertThatThrownBy(() -> orderService.getOrderById(orderId))
              .isInstanceOf(OrderNotFoundException.class)
              .hasMessageContaining(ORDER_WITH_ID_NOT_FOUND);

      verify(orderRepository, never()).findById(orderId);
      */

      OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(orderId));

      verify(orderRepository, never()).findById(orderId);

      assertThat(exception.getMessage()).isEqualTo(ORDER_WITH_ID_NOT_FOUND, orderId);
   }

   @Test
   @DisplayName("whenOrderWithIdExists_updateOrderById")
   public void updateOrderById_shouldReturnOrder_whenOrderWithIdExists() {
      order.setOrderId(2L);
      Customer customer = order.getCustomer();
      customer.setName("Nike");
      order.setCustomer(customer);

      Order newOrder = new Order();
      customer.setName("Reebok");
      order.setCustomer(customer);

      given(orderRepository.findById(order.getOrderId())).willReturn(Optional.of(order));

      orderService.updateOrderById(newOrder, order.getOrderId());
   }

   @Test
   @DisplayName("whenOrderWithIdDoesNotExist_updateOrderById_shouldThrowException")
   public void updateOrderById_shouldThrowException_whenOrderWithIdDoesNotExist() {
      order.setOrderId(2L);
      Customer customer = order.getCustomer();
      customer.setName("Nike");
      order.setCustomer(customer);

      Order newOrder = new Order();
      customer.setName("Reebok");
      order.setCustomer(customer);

      /*
      assertThatThrownBy(() -> orderService.updateOrderById(newOrder, order.getOrderId()))
              .isInstanceOf(OrderNotFoundException.class)
              .hasMessageContaining(ORDER_WITH_ID_NOT_FOUND);

      verify(orderRepository, never()).save(order);
       */

      OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> orderService.updateOrderById(newOrder, order.getOrderId()));

      verify(orderRepository, never()).findById(orderId);

      assertThat(exception.getMessage()).isEqualTo(ORDER_WITH_ID_NOT_FOUND, orderId);
   }

   @Test
   @DisplayName("whenOrderWithIdExists_deleteOrderById")
   public void deleteOrderById_shouldReturnConfirmMessage_whenOrderWithIdExists() {
      given(orderRepository.existsById(orderId)).willReturn(true);

      orderService.deleteOrderById(orderId);

      verify(orderRepository).deleteById(orderId);
   }

   @Test
   @DisplayName("whenOrderWithIdDoesNotExist_deleteOrderById_showThrowException")
   public void deleteOrderById_shouldReturnConfirmMessage_whenGivenIdToDeleteOrderIsInvalid() {
      given(orderRepository.existsById(orderId)).willReturn(false);

      /*
      assertThatThrownBy(() -> orderService.deleteOrderById(orderId))
              .isInstanceOf(OrderNotFoundException.class)
              .hasMessageContaining(ORDER_WITH_ID_NOT_FOUND);

      verify(orderRepository, never()).deleteById(orderId);
       */

      OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> orderService.deleteOrderById(orderId));

      verify(orderRepository, never()).findById(orderId);

      assertThat(exception.getMessage()).isEqualTo(ORDER_WITH_ID_NOT_FOUND, orderId);
   }
}