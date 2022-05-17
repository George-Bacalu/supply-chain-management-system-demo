package com.project.controller;

import com.project.entity.Order;
import com.project.exception.InvalidOrderException;
import com.project.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.project.constant.ClientConstants.INVALID_ORDER_FORMAT;

@RestController
@RequestMapping(value = "/api/client/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderRestController {

   //TODO: find out the reason why I get the login html page as a response whenever I sent http requests to "/api/client/orders" via Postman

   private final OrderService orderService;

   @GetMapping
   public ResponseEntity<List<Order>> getAllOrders() {
      return ResponseEntity.ok(orderService.getAllOrders());
   }

   @GetMapping(value = "/{id}")
   public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
      return ResponseEntity.ok(orderService.getOrderById(id));
   }

   @Validated
   @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Order> saveOrder(@Valid @RequestBody Order order, BindingResult bindingResult) {
      if(bindingResult.hasErrors()) {
         throw new InvalidOrderException(INVALID_ORDER_FORMAT, order);
      }
      return ResponseEntity.status(HttpStatus.CREATED).body(orderService.saveOrder(order));
   }

   @Validated
   @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Order> updateOrderById(@Valid @RequestBody Order order, @PathVariable Long id, BindingResult bindingResult) {
      if(bindingResult.hasErrors()) {
         throw new InvalidOrderException(INVALID_ORDER_FORMAT, order);
      }
      return ResponseEntity.ok(orderService.updateOrderById(order, id));
   }

   @DeleteMapping(value = "/{id}")
   public ResponseEntity<Map<String, Boolean>> deleteOrderById(@PathVariable Long id) {
      orderService.deleteOrderById(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of("deleted", Boolean.TRUE));
   }
}
