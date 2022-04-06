package com.project.service.impl;

import com.project.entity.Order;
import com.project.exception.InvalidDataException;
import com.project.exception.ProductException;
import com.project.exception.ResourceNotFoundException;
import com.project.repository.OrderRepository;
import com.project.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.project.constant.ClientConstants.*;
import static com.project.utils.PriceUtils.getTotalPrice;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

   private final OrderRepository orderRepository;

   @Override
   public List<Order> getAllOrders() {
      return orderRepository.findAll();
   }

   @Override
   public Order getOrderById(Long id) {
      if (id < 0) {
         throw new InvalidDataException(ORDER_WITH_INVALID_ID, id);
      }
      return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ORDER_WITH_ID_NOT_FOUND, id));
   }

   @Override
   public Order saveOrder(Order order) {
      if (order == null) {
         throw new ResourceNotFoundException(NO_ORDER_FOUND);
      }
      if (order.getProducts().isEmpty()) {
         throw new ProductException(INVALID_ORDER_FORMAT, order);
      }
      Order orderToBeSaved = Order.builder()
              .createdAt(LocalDateTime.now())
              .customer(order.getCustomer())
              .address(order.getAddress())
              .products(order.getProducts())
              .totalPrice(getTotalPrice(order.getProducts()))
              .build();
      return orderRepository.save(orderToBeSaved);
   }

   @Override
   public Order updateOrderById(Order order, Long id) {
      if (order == null) {
         throw new ResourceNotFoundException(NO_ORDER_FOUND);
      }
      if (id < 0) {
         throw new InvalidDataException(ORDER_WITH_INVALID_ID, id);
      }
      if (order.getProducts().isEmpty()) {
         throw new ProductException(INVALID_ORDER_FORMAT, order);
      }
      Order orderToBeUpdated = getOrderById(id);
      orderToBeUpdated.setCustomer(order.getCustomer());
      orderToBeUpdated.setUpdatedAt(LocalDateTime.now());
      orderToBeUpdated.setProducts(order.getProducts());
      orderToBeUpdated.setTotalPrice(getTotalPrice(order.getProducts()));
      orderToBeUpdated.setAddress(order.getAddress());
      return orderRepository.save(orderToBeUpdated);
   }

   @Override
   public void deleteOrderById(Long id) {
      if (id == null || id < 0) {
         throw new InvalidDataException(ORDER_WITH_INVALID_ID, id);
      }
      Order order = getOrderById(id);
      orderRepository.delete(order);

   }
}
