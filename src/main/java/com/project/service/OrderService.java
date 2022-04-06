package com.project.service;

import com.project.entity.Order;

import java.util.List;

public interface OrderService {

   List<Order> getAllOrders();
   Order getOrderById(Long id);
   Order saveOrder(Order order);
   Order updateOrderById(Order order, Long id);
   void deleteOrderById(Long id);
}
