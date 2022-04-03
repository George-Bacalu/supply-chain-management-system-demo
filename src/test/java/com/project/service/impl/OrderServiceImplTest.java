package com.project.service.impl;

import com.project.entity.DeliveryAddress;
import com.project.entity.Order;
import com.project.entity.OrderItem;
import com.project.exception.ResourceNotFoundException;
import com.project.repository.OrderRepository;
import com.project.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static com.project.constant.OrderConstants.RESOURCE_WITH_ID_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

//@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class OrderServiceImplTest {

   OrderItem item1 = OrderItem.builder().name("Shoe order").quantity(100).build();
   OrderItem item2 = OrderItem.builder().name("T-shirt order").quantity(200).build();
   DeliveryAddress deliveryAddress = DeliveryAddress.builder().country("Country 1").city("City 1").street("Street 1").phoneNumber("0000000000").build();
   Order order = Order.builder().customer("Adidas").customerId(1L).orderItems(List.of(item1, item2)).deliveryAddress(deliveryAddress).build();

   @Mock
   private OrderRepository orderRepositoryTest;

   @InjectMocks
   private OrderService orderServiceTest;

   @BeforeEach
   public void setUp() {
      orderServiceTest = new OrderServiceImpl(orderRepositoryTest);
   }

   @Test
   @DisplayName("getAllOrders")
   public void shouldReturnAllOrders() {
      orderServiceTest.getAllOrders();

      given(orderRepositoryTest.findAll()).willReturn(List.of(order));

      assertThat(List.of(order)).isEqualTo(orderRepositoryTest.findAll());
      verify(orderRepositoryTest).findAll();
   }

   @Test
   @DisplayName("getOrderById")
   public void whenGivenIdToGetOrder_shouldReturnOrder_ifFound() {
      Long orderId = 1L;
      given(orderRepositoryTest.existsById(orderId)).willReturn(true);
      orderServiceTest.getOrderById(orderId);

      verify(orderRepositoryTest).findById(orderId);
   }

   @Test
   @DisplayName("getOrderById_throwException")
   public void whenGivenIdToGetOrder_shouldThrowException_ifNotFound() {
      Long orderId = 1L;
      given(orderRepositoryTest.existsById(orderId)).willReturn(false);

      assertThatThrownBy(() -> orderServiceTest.getOrderById(orderId))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessageContaining(RESOURCE_WITH_ID_NOT_FOUND);

      verify(orderRepositoryTest, never()).findById(orderId);
   }

   @Test
   @DisplayName("saveOrder")
   public void whenSaveOrder_shouldReturnOrder() {
      orderServiceTest.saveOrder(order);

      ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
      verify(orderRepositoryTest).save(orderArgumentCaptor.capture());

      Order capturedOrder = orderArgumentCaptor.getValue();
      assertThat(capturedOrder).isEqualTo(order);
   }

   @Test
   @DisplayName("updateOrderById")
   public void whenGivenIdToUpdateOrder_shouldReturnOrder_ifFound() {
      order.setOrderId(2L);
      order.setCustomer("Reebok");

      Order newOrder = new Order();
      order.setCustomer("Nike");

      given(orderRepositoryTest.findById(order.getOrderId())).willReturn(Optional.of(order));
      orderServiceTest.updateOrderById(newOrder, order.getOrderId());
   }

   @Test
   @DisplayName("updateOrderById_throwException")
   public void whenGivenIdToUpdateOrder_shouldThrowException_ifNotFound() {
      order.setOrderId(2L);
      order.setCustomer("Reebok");

      Order newOrder = new Order();
      newOrder.setOrderId(3L);
      order.setCustomer("Nike");

      assertThatThrownBy(() -> orderServiceTest.updateOrderById(newOrder, order.getOrderId()))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessageContaining(RESOURCE_WITH_ID_NOT_FOUND);

      verify(orderRepositoryTest, never()).save(order);
   }

   @Test
   @DisplayName("deleteOrderById")
   public void whenGivenIdToDeleteOrder_shouldReturnConfirmMessage_ifFound() {
      Long orderId = 1L;
      given(orderRepositoryTest.existsById(orderId)).willReturn(true);
      orderServiceTest.deleteOrderById(orderId);

      verify(orderRepositoryTest).deleteById(orderId);
   }

   @Test
   @DisplayName("deleteOrderById_throwException")
   public void whenGivenIdToDeleteOrder_shouldThrowException_ifNotFound() {
      Long orderId = 1L;
      given(orderRepositoryTest.existsById(orderId)).willReturn(false);

      assertThatThrownBy(() -> orderServiceTest.deleteOrderById(orderId))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessageContaining(RESOURCE_WITH_ID_NOT_FOUND);

      verify(orderRepositoryTest, never()).deleteById(orderId);
   }
}