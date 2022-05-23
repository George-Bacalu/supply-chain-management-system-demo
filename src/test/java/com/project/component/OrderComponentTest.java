package com.project.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.controller.OrderRestController;
import com.project.entity.Order;
import com.project.exception.OrderNotFoundException;
import com.project.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderComponentTest {

   //private static BindingResult bindingResult;
   private static final Order order = getMockedOrder();

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private ObjectMapper mapper;

   @InjectMocks
   private OrderRestController orderRestController;
   @Mock
   private OrderService orderService;

   /*
   @Test
   @DisplayName("saveOrder")
   public void saveOrder_shouldReturnOrder() {
      when(orderService.saveOrder(order)).thenReturn(order);

      ResponseEntity<Order> orderResponseEntity = orderRestController.saveOrder(order, bindingResult);

      assertThat(orderResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
   }
   */

   @Test
   @DisplayName("saveOrder")
   public void saveOrder_shouldReturnOrder_whenPostRequest() throws Exception {
      given(orderService.saveOrder(order)).willReturn(order);

      MvcResult result = mockMvc.perform(post("/api/client/orders")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(mapper.writeValueAsString(order)))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.customer", is(order.getCustomer())))
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(201);
   }

   @Test
   @DisplayName("getAllOrders")
   public void getAllOrders_shouldReturnAllOrders_whenGetRequest() throws Exception {
      given(orderService.getAllOrders()).willReturn(List.of(order));

      MvcResult result = mockMvc.perform(get("/api/client/orders")
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$", hasSize(1)))
              .andExpect(jsonPath("$[0].customer", is(order.getCustomer())))
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(200);
   }

   @Test
   @DisplayName("whenOrderWithIdExists_getOrderById")
   public void getOrderById_shouldReturnOrder_whenOrderWithIdExists_whenGetRequest() throws Exception {
      given(orderService.getOrderById(order.getOrderId())).willReturn(order);

      MvcResult result = mockMvc.perform(get("/api/client/orders/" + order.getOrderId().toString())
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("customer", is(order.getCustomer())))
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(200);
   }

   @Test
   @DisplayName("whenOrderWithIdExists_getOrderById_shouldThrowException")
   public void getOrderById_shouldThrowException_whenOrderWithIdDoesNotExist_whenGetRequest() throws Exception {
      Mockito.doThrow(new OrderNotFoundException(ORDER_WITH_ID_NOT_FOUND)).when(orderService).getOrderById(order.getOrderId());

      MvcResult result = mockMvc.perform(delete("/api/client/orders/" + order.getOrderId().toString())
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isNotFound())
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(404);
   }

   @Test
   @DisplayName("whenOrderWithIdExists_updateOrderById")
   public void updateOrderById_shouldReturnOrder_whenOrderWithIdExists_whenPutRequest() throws Exception {
      given(orderService.updateOrderById(order, order.getOrderId())).willReturn(order);

      MvcResult result = mockMvc.perform(put("/api/client/orders/" + order.getOrderId().toString())
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(mapper.writeValueAsString(order)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.customer", is(order.getCustomer())))
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(200);
   }

   @Test
   @DisplayName("whenOrderWithIdExists_updateOrderById_shouldThrowException")
   public void updateOrderById_shouldThrowException_whenOrderWithIdDoesNotExist_whenPutRequest() throws Exception {
      Mockito.doThrow(new OrderNotFoundException(ORDER_WITH_ID_NOT_FOUND)).when(orderService).updateOrderById(order, order.getOrderId());

      MvcResult result = mockMvc.perform(put("/api/client/orders/" + order.getOrderId().toString())
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(mapper.writeValueAsString(order)))
              .andExpect(status().isNotFound())
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(404);
   }

   @Test
   @DisplayName("whenOrderWithIdExists_deleteOrderById")
   public void deleteOrderById_shouldReturnOrder_whenOrderWithIdExists_whenDeleteRequest() throws Exception {
      doNothing().when(orderService).deleteOrderById(order.getOrderId());

      MvcResult result = mockMvc.perform(delete("/api/client/orders/" + order.getOrderId().toString())
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isNoContent())
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(204);
   }

   @Test
   @DisplayName("whenOrderWithIdExists_deleteOrderById_shouldThrowException")
   public void deleteOrderById_shouldThrowException_whenOrderWithIdDoesNotExist_whenDeleteRequest() throws Exception {
      Mockito.doThrow(new OrderNotFoundException(ORDER_WITH_ID_NOT_FOUND)).when(orderService).deleteOrderById(order.getOrderId());

      MvcResult result = mockMvc.perform(delete("/api/client/orders/" + order.getOrderId().toString())
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isNotFound())
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(404);
   }
}