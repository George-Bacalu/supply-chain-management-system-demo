package com.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.entity.DeliveryAddress;
import com.project.entity.Order;
import com.project.entity.OrderItem;
import com.project.exception.ResourceNotFoundException;
import com.project.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.project.constant.OrderConstants.RESOURCE_WITH_ID_NOT_FOUND;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderRestController.class)
class OrderRestControllerTest {

   OrderItem item1 = OrderItem.builder().name("Shoe order").quantity(100).build();
   OrderItem item2 = OrderItem.builder().name("T-shirt order").quantity(200).build();
   DeliveryAddress deliveryAddress = DeliveryAddress.builder().country("Country 1").city("City 1").street("Street 1").phoneNumber("0000000000").build();
   Order order = Order.builder().customer("Adidas").customerId(1L).orderItems(List.of(item1, item2)).deliveryAddress(deliveryAddress).build();

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private ObjectMapper mapper;

   @MockBean
   private OrderService orderServiceTest;

   @Test
   public void displayAllOrders_whenGetMethod() throws Exception {
      given(orderServiceTest.getAllOrders()).willReturn(List.of(order));

      mockMvc.perform(get("/api/client/orders")
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$", hasSize(1)))
              .andExpect(jsonPath("$[0].customer", is(order.getCustomer())));
   }

   @Test
   public void displayUserById_WhenGetMethod() throws Exception {
      given(orderServiceTest.getOrderById(order.getOrderId())).willReturn(order);

      mockMvc.perform(get("/api/client/orders/" + order.getOrderId().toString())
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("customer", is(order.getCustomer())));
   }

   @Test
   @DisplayName("displayUserById_WhenGetMethod_throwException")
   public void whenGivenIdToGetOrder_shouldThrowException_ifNotFound() throws Exception {
      Mockito.doThrow(new ResourceNotFoundException(RESOURCE_WITH_ID_NOT_FOUND)).when(orderServiceTest).getOrderById(order.getOrderId());

      mockMvc.perform(delete("/api/client/orders/" + order.getOrderId().toString())
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isNotFound());
   }

   @Test
   public void saveOrder_whenPostMethod() throws Exception {
      given(orderServiceTest.saveOrder(order)).willReturn(order);

      mockMvc.perform(post("/api/client/orders")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(mapper.writeValueAsString(order)))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.customer", is(order.getCustomer())));
   }

   @Test
   public void updateOrder_whenPutMethod() throws Exception {
      given(orderServiceTest.updateOrderById(order, order.getOrderId())).willReturn(order);

      mockMvc.perform(put("/api/client/orders/" + order.getOrderId().toString())
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(mapper.writeValueAsString(order)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.customer", is(order.getCustomer())));
   }

   @Test
   @DisplayName("updateUser_whenPutMethod_throwException")
   public void whenGivenIdToUpdateOrder_shouldThrowException_ifNotFound() throws Exception {
      Mockito.doThrow(new ResourceNotFoundException(RESOURCE_WITH_ID_NOT_FOUND)).when(orderServiceTest).updateOrderById(order, order.getOrderId());

      mockMvc.perform(put("/api/client/orders/" + order.getOrderId().toString())
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(mapper.writeValueAsString(order)))
              .andExpect(status().isNotFound());
   }

   @Test
   public void deleteOrderById_whenDeleteMethod() throws Exception {
      doNothing().when(orderServiceTest).deleteOrderById(order.getOrderId());

      mockMvc.perform(delete("/api/client/orders/" + order.getOrderId().toString())
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isNoContent());
   }

   @Test
   @DisplayName("deleteOrderById_whenDeleteMethod_throwException")
   public void whenGivenIdToDeleteOrder_shouldThrowException_ifNotFound() throws Exception {
      Mockito.doThrow(new ResourceNotFoundException(RESOURCE_WITH_ID_NOT_FOUND)).when(orderServiceTest).deleteOrderById(order.getOrderId());

      mockMvc.perform(delete("/api/client/orders/" + order.getOrderId().toString())
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isNotFound());
   }
}