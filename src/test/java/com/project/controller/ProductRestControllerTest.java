package com.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.entity.Product;
import com.project.exception.OrderNotFoundException;
import com.project.service.ProductService;
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
import static com.project.mocks.ProductMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductRestControllerTest {

   //private static BindingResult bindingResult;
   private static final Product product1 = getMockedProduct1();
   private static final Product product2 = getMockedProduct2();
   private static final List<Product> products = getMockedProductList();

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private ObjectMapper mapper;

   @InjectMocks
   private ProductRestController productRestController;
   @Mock
   private ProductService productService;

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
   @DisplayName("saveProduct1")
   public void saveProduct1_shouldReturnProduct1_whenPostRequest() throws Exception {
      given(productService.saveProduct(product1)).willReturn(product1);

      MvcResult result = mockMvc.perform(post("/api/client/products")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(mapper.writeValueAsString(product1)))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.price", is(product1.getPrice())))
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(201);
   }

   @Test
   @DisplayName("saveProduct2")
   public void saveProduct2_shouldReturnProduct2_whenPostRequest() throws Exception {
      given(productService.saveProduct(product2)).willReturn(product2);

      MvcResult result = mockMvc.perform(post("/api/client/products")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(mapper.writeValueAsString(product2)))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.price", is(product2.getPrice())))
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(201);
   }

   @Test
   @DisplayName("getAllOrders")
   public void getAllOrders_shouldReturnAllOrders_whenGetRequest() throws Exception {
      given(productService.getAllProducts()).willReturn(getMockedProductList());

      MvcResult result = mockMvc.perform(get("/api/client/products")
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$", hasSize(2)))
              .andExpect(jsonPath("$[0].price", is(products.get(0).getPrice())))
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(200);
   }

   @Test
   @DisplayName("whenOrderWithIdExists_getOrderById")
   public void getOrderById_shouldReturnOrder_whenOrderWithIdExists_whenGetRequest() throws Exception {
      given(productService.getProductById(product1.getProductId())).willReturn(product1);

      MvcResult result = mockMvc.perform(get("/api/client/products/" + product1.getProductId().toString())
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("price", is(product1.getPrice())))
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(200);
   }

   @Test
   @DisplayName("whenOrderWithIdExists_getOrderById_shouldThrowException")
   public void getOrderById_shouldThrowException_whenOrderWithIdDoesNotExist_whenGetRequest() throws Exception {
      Mockito.doThrow(new OrderNotFoundException(ORDER_WITH_ID_NOT_FOUND)).when((productService).getProductById(product1.getProductId()));

      MvcResult result = mockMvc.perform(get("/api/client/products/" + product1.getProductId().toString())
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isNotFound())
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(404);
   }
}