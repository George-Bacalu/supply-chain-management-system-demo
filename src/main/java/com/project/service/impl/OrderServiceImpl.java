package com.project.service.impl;

import com.project.entity.Order;
import com.project.exception.InvalidDataException;
import com.project.exception.ResourceNotFoundException;
import com.project.repository.OrderRepository;
import com.project.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.constant.OrderConstants.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

   private final OrderRepository orderRepository;

   @Override
   public List<Order> getAllOrders() {
      if(orderRepository.findAll().isEmpty()) {
         throw new ResourceNotFoundException(NO_RESOURCE_FOUND);
      }
      return orderRepository.findAll();
   }

   @Override
   public Order getOrderById(Long id) {
      if(id == null || id < 0) {
         throw new InvalidDataException(RESOURCE_WITH_INVALID_ID);
      }
      return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_WITH_ID_NOT_FOUND));
   }

   @Override
   public Order saveOrder(Order order) {
      if(order == null) {
         throw new ResourceNotFoundException(NO_RESOURCE_FOUND);
      }
      Long id = order.getCustomerId();
      if(id == null || id < 0) {
         throw new InvalidDataException(RESOURCE_WITH_INVALID_ID);
      }
      return orderRepository.save(order);
   }

   @Override
   public Order updateOrderById(Order newOrder, Long id) {
      if(newOrder == null) {
         throw new ResourceNotFoundException(NO_RESOURCE_FOUND);
      }
      if(id == null || id < 0) {
         throw new InvalidDataException(RESOURCE_WITH_INVALID_ID);
      }
      Order order = getOrderById(id);
      order.setCustomer(newOrder.getCustomer());
      order.setCustomerId(newOrder.getCustomerId());
      order.setCreatedAt(newOrder.getCreatedAt());
      order.setUpdatedAt(newOrder.getUpdatedAt());
      order.setOrderItems(newOrder.getOrderItems());
      order.setDeliveryAddress(newOrder.getDeliveryAddress());
      return orderRepository.save(order);
   }

   @Override
   public void deleteOrderById(Long id) {
      if(id == null || id < 0) {
         throw new InvalidDataException(RESOURCE_WITH_INVALID_ID);
      }
      Order order = getOrderById(id);
      orderRepository.delete(order);
   }
}
