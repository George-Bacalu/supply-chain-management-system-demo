package com.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.entity.Order;
import com.project.exception.ResourceNotFoundException;
import com.project.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.project.constant.ClientConstants.ORDER_WITH_ID_NOT_FOUND;
import static com.project.mocks.OrderMock.getMockedOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderRestControllerTest {

   Order order = getMockedOrder();

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private ObjectMapper mapper;

   @Mock
   private OrderService orderServiceTest;

   @Test
   public void displayAllOrders_whenGetMethod() throws Exception {
      given(orderServiceTest.getAllOrders()).willReturn(List.of(order));

      MvcResult result = mockMvc.perform(get("/api/client/orders")
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$", hasSize(1)))
              .andExpect(jsonPath("$[0].customer", is(order.getCustomer())))
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(200);
   }

   @Test
   public void displayUserById_WhenGetMethod() throws Exception {
      given(orderServiceTest.getOrderById(order.getOrderId())).willReturn(order);

      MvcResult result = mockMvc.perform(get("/api/client/orders/" + order.getOrderId().toString())
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("customer", is(order.getCustomer())))
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(200);
   }

   @Test
   @DisplayName("displayUserById_WhenGetMethod_throwException")
   public void whenGivenIdToGetOrder_shouldThrowException_ifNotFound() throws Exception {
      Mockito.doThrow(new ResourceNotFoundException(ORDER_WITH_ID_NOT_FOUND)).when(orderServiceTest).getOrderById(order.getOrderId());

      MvcResult result = mockMvc.perform(delete("/api/client/orders/" + order.getOrderId().toString())
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isNotFound())
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(404);
   }

   @Test
   public void saveOrder_whenPostMethod() throws Exception {
      given(orderServiceTest.saveOrder(order)).willReturn(order);

      MvcResult result = mockMvc.perform(post("/api/client/orders")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(mapper.writeValueAsString(order)))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.customer", is(order.getCustomer())))
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(201);
   }

   @Test
   public void updateOrder_whenPutMethod() throws Exception {
      given(orderServiceTest.updateOrderById(order, order.getOrderId())).willReturn(order);

      MvcResult result = mockMvc.perform(put("/api/client/orders/" + order.getOrderId().toString())
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(mapper.writeValueAsString(order)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.customer", is(order.getCustomer())))
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(200);
   }

   @Test
   @DisplayName("updateUser_whenPutMethod_throwException")
   public void whenGivenIdToUpdateOrder_shouldThrowException_ifNotFound() throws Exception {
      Mockito.doThrow(new ResourceNotFoundException(ORDER_WITH_ID_NOT_FOUND)).when(orderServiceTest).updateOrderById(order, order.getOrderId());

      MvcResult result = mockMvc.perform(put("/api/client/orders/" + order.getOrderId().toString())
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(mapper.writeValueAsString(order)))
              .andExpect(status().isNotFound())
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(404);
   }

   @Test
   public void deleteOrderById_whenDeleteMethod() throws Exception {
      doNothing().when(orderServiceTest).deleteOrderById(order.getOrderId());

      MvcResult result = mockMvc.perform(delete("/api/client/orders/" + order.getOrderId().toString())
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isNoContent())
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(204);
   }

   @Test
   @DisplayName("deleteOrderById_whenDeleteMethod_throwException")
   public void whenGivenIdToDeleteOrder_shouldThrowException_ifNotFound() throws Exception {
      Mockito.doThrow(new ResourceNotFoundException(ORDER_WITH_ID_NOT_FOUND)).when(orderServiceTest).deleteOrderById(order.getOrderId());

      MvcResult result = mockMvc.perform(delete("/api/client/orders/" + order.getOrderId().toString())
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isNotFound())
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(404);
   }
}