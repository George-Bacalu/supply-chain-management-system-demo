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
      Order orderToSave = Order.builder()
              .createdAt(LocalDateTime.now())
              .customer(order.getCustomer())
              .address(order.getAddress())
              .products(order.getProducts())
              .totalPrice(getTotalPrice(order.getProducts()))
              .build();
      return orderRepository.save(orderToSave);
   }

   @Override
   public Order updateOrderById(Order newOrder, Long id) {
      if (newOrder == null) {
         throw new ResourceNotFoundException(NO_ORDER_FOUND);
      }
      if (id < 0) {
         throw new InvalidDataException(ORDER_WITH_INVALID_ID, id);
      }
      if (newOrder.getProducts().isEmpty()) {
         throw new ProductException(INVALID_ORDER_FORMAT, newOrder);
      }
      Order order = getOrderById(id);
      order.setCustomer(newOrder.getCustomer());
      order.setUpdatedAt(LocalDateTime.now());
      order.setProducts(newOrder.getProducts());
      order.setTotalPrice(getTotalPrice(newOrder.getProducts()));
      order.setAddress(newOrder.getAddress());
      return orderRepository.save(order);
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
