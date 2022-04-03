package com.project.controller;

import com.project.entity.Order;
import com.project.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/client/orders")
@RequiredArgsConstructor
public class OrderModelViewController {

   private final OrderService orderService;

   @GetMapping
   public String getAllOrdersView(Model model) {
      model.addAttribute("orders", orderService.getAllOrders());
      return "orders/index";
   }

   @GetMapping("/create-new")
   public String createOrderFormView(Model model) {
      model.addAttribute("order", new Order());
      return "orders/create-order";
   }

   @PostMapping
   public String saveOrderView(@ModelAttribute("order") Order order) {
      orderService.saveOrder(order);
      return "redirect:/client/orders";
   }

   @GetMapping("/update/{id}")
   public String updateOrderFormView(@PathVariable Long id, Model model) {
      model.addAttribute("order", orderService.getOrderById(id));
      return "orders/update-order";
   }

   @PostMapping("/{id}")
   public String updateOrderView(@ModelAttribute("order") Order order, @PathVariable Long id) {
      orderService.updateOrderById(order, id);
      return "redirect:/client/orders";
   }

   @DeleteMapping("/delete/{id}")
   public String deleteOrderView(@PathVariable Long id) {
      orderService.deleteOrderById(id);
      return "redirect:/client/orders";
   }
}
