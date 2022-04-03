package com.project.controller;

import com.project.entity.Order;
import com.project.exception.InvalidDataException;
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

import static com.project.constant.OrderConstants.INVALID_RESOURCE_FORMAT;

@RestController
@RequestMapping(value = "/api/client/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderRestController {

   private final OrderService orderService;

   @GetMapping(path = "/")
   public ResponseEntity<List<Order>> getAllOrders() {
      return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders());
   }

   @GetMapping(path = "/{id}")
   public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
      return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderById(id));
   }

   @Validated
   @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Order> saveOrder(@Valid @RequestBody Order order, BindingResult bindingResult) {
      if(bindingResult.hasErrors()) {
         throw new InvalidDataException(INVALID_RESOURCE_FORMAT);
      }
      return ResponseEntity.status(HttpStatus.CREATED).body(orderService.saveOrder(order));
   }

   @Validated
   @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Order> updateOrderById(@Valid @RequestBody Order order, @PathVariable Long id, BindingResult bindingResult) {
      if(bindingResult.hasErrors()) {
         throw new InvalidDataException(INVALID_RESOURCE_FORMAT);
      }
      return ResponseEntity.status(HttpStatus.OK).body(orderService.updateOrderById(order, id));
   }

   @DeleteMapping(path = "/{id}")
   public ResponseEntity<Map<String, Boolean>> deleteOrderById(@PathVariable Long id) {
      orderService.deleteOrderById(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of("deleted", Boolean.TRUE));
   }
}