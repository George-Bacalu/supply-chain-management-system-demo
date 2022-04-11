package com.project.controller;

import com.project.entity.Order;
import com.project.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

   @GetMapping("/customers")
   public String getAllOrdersCustomersView(Model model) {
      model.addAttribute("customers", orderService.getAllOrders().stream().map(Order::getCustomer).collect(Collectors.toList()));
      return "orders/customers";
   }

   @GetMapping("/addresses")
   public String getAllOrdersAddressesView(Model model) {
      model.addAttribute("addresses", orderService.getAllOrders().stream().map(Order::getAddress).collect(Collectors.toList()));
      return "orders/addresses";
   }

   @GetMapping("/products")
   public String getAllOrdersProductsView(ModelMap model) {
      model.addAttribute("orders", orderService.getAllOrders());
      model.addAttribute("products", orderService.getAllOrders().stream().map(Order::getProducts).flatMap(List::stream).collect(Collectors.toList()));
      return "orders/products";
   }

   @GetMapping("/create-order/step1")
   public String createOrderFormStep1View(Model model) {
      model.addAttribute("order", new Order());
      return "orders/create-order/step1";
   }

   @GetMapping("/create-order/step2")
   public String createOrderFormStep2View(Model model) {
      model.addAttribute("order", new Order());
      return "orders/create-order/step2";
   }

   @GetMapping("/create-order/step3")
   public String createOrderFormStep3View(Model model) {
      model.addAttribute("order", new Order());
      return "orders/create-order/step3";
   }

   @PostMapping("/create-order/step2")
   public String goToSecondStepOrderView(@ModelAttribute("order") Order order) {
      return "redirect:/client/orders/create-order/step2";
   }

   @PostMapping("/create-order/step3")
   public String goToThirdStepOrderView(@ModelAttribute("order") Order order) {
      return "redirect:/client/orders/create-order/step3";
   }

   //TODO: outsource the logic for the views returned in the get and post methods in the add order section

   /*
   @GetMapping("/create-order/{step}")
   public String createOrderFormView(Model model, @PathVariable String step) {
      model.addAttribute("order", new Order());
      return switch (step) {
         case "step1" -> "orders/create-order/step1";
         case "step2" -> "orders/create-order/step2";
         case "step3" -> "orders/create-order/step3";
         default -> "error";
      };
   }

   @PostMapping("/create-order/{next-step}")
   public String goToNextStepOrderView(@ModelAttribute("order") Order order, @PathVariable("next-step") String step) {
      return switch (step) {
         case "step2" -> "redirect:/client/orders/create-order/step2";
         case "step3" -> "redirect:/client/orders/create-order/step3";
         default -> "error";
      };
   }
   */

   @PostMapping("/save-new-order")
   public String saveOrderView(@ModelAttribute("order") Order order) {
      orderService.saveOrder(order);
      return "redirect:/client/orders";
   }

   @GetMapping("/update-order/{id}")
   public String updateOrderFormView(@PathVariable Long id, Model model) {
      model.addAttribute("order", orderService.getOrderById(id));
      return "orders/update-order";
   }

   @PostMapping("/save-updated-order/{id}")
   public String updateOrderView(@ModelAttribute("order") Order order, @PathVariable Long id) {
      orderService.updateOrderById(order, id);
      return "redirect:/client/orders";
   }

   @GetMapping("/delete-order/{id}")
   public String deleteOrderView(@PathVariable Long id, Model model) {
      model.addAttribute("order", orderService.getOrderById(id));
      return "orders/delete-order";
   }

   @PostMapping("/delete-order/{id}")
   public String deleteOrderView(@PathVariable Long id) {
      orderService.deleteOrderById(id);
      return "redirect:/client/orders";
   }
}
