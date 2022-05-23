package com.project.controller;

import com.project.entity.Address;
import com.project.entity.Customer;
import com.project.entity.Order;
import com.project.entity.Product;
import com.project.exception.InvalidOrderException;
import com.project.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.project.constant.ClientConstants.INVALID_ORDER_FORMAT;

@Slf4j
@Controller
@RequestMapping("/client/orders")
@RequiredArgsConstructor
public class OrderController {

   //TODO: find a way to display the orders table dynamically considering how thymeleaf works with lists (I need to map the products list data back to a Java list)
   //The error I get whenever I submit the form for placing orders: java.lang.NullPointerException: Cannot invoke "java.util.List.isEmpty()" because the return value of "com.project.entity.Order.getProducts()" is null

   private final Order order = new Order();

   private final OrderService orderService;
   private final List<Order> orders = orderService.getAllOrders();

   @GetMapping
   public String getAllOrdersView(Model model) {
      model.addAttribute("orders", orders);
      return "orders/index";
   }

   @GetMapping("/customers")
   public String getAllOrdersCustomersView(Model model) {
      model.addAttribute("customers", orders.stream().map(Order::getCustomer).collect(Collectors.toList()));
      return "orders/customers";
   }

   @GetMapping("/addresses")
   public String getAllOrdersAddressesView(Model model) {
      model.addAttribute("addresses", orders.stream().map(Order::getAddress).collect(Collectors.toList()));
      return "orders/addresses";
   }

   @GetMapping("/products")
   public String getAllOrdersProductsView(ModelMap model) {
      model.addAttribute("orders", orders);
      model.addAttribute("products", orders.stream().map(Order::getProducts).flatMap(List::stream).collect(Collectors.toList()));
      return "orders/products";
   }

   @GetMapping("/create-order/step1")
   public String createOrderFormStep1View(Model model) {
      List<Product> products = new ArrayList<>();
      model.addAttribute("order", order);
      model.addAttribute("products", products);
      order.setProducts(products);
      return "orders/create-order/step1";
   }

   @GetMapping("/create-order/step2")
   public String createOrderFormStep2View(Model model) {
      Customer customer = new Customer();
      model.addAttribute("order", order);
      model.addAttribute("customer", customer);
      order.setCustomer(customer);
      return "orders/create-order/step2";
   }

   @GetMapping("/create-order/step3")
   public String createOrderFormStep3View(Model model) {
      Address address = new Address();
      model.addAttribute("order", order);
      model.addAttribute("address", address);
      order.setAddress(address);
      return "orders/create-order/step3";
   }

   @PostMapping("/create-order/step2")
   public String goToSecondStepOrderView(@ModelAttribute("order") Order order, BindingResult bindingResult) {
      if(bindingResult.hasErrors()) {
         throw new InvalidOrderException(INVALID_ORDER_FORMAT, order);
      }
      return "redirect:/client/orders/create-order/step2";
   }

   @PostMapping("/create-order/step3")
   public String goToThirdStepOrderView(@ModelAttribute("order") Order order, BindingResult bindingResult) {
      if(bindingResult.hasErrors()) {
         throw new InvalidOrderException(INVALID_ORDER_FORMAT, order);
      }
      return "redirect:/client/orders/create-order/step3";
   }

   //TODO: outsource the logic for the views returned in the get and post methods in the add order section

   @PostMapping("/save-new-order")
   public String saveOrderView(@ModelAttribute("order") Order order, BindingResult bindingResult) {
      try {
         if(bindingResult.hasErrors()) {
            throw new InvalidOrderException(INVALID_ORDER_FORMAT, order);
         }
         orderService.saveOrder(order);
      } catch(Exception ex) {
         log.info("Encounter error " + ex.getMessage() + " when trying to save your order");
         return "redirect:/client/orders?saved=false";
      }
      return "redirect:/client/orders?saved=true";
   }

   @GetMapping("/update-order/{id}")
   public String updateOrderFormView(@PathVariable Long id, Model model) {
      model.addAttribute("order", orderService.getOrderById(id));
      return "orders/update-order";
   }

   @PostMapping("/save-updated-order/{id}")
   public String updateOrderView(@ModelAttribute("order") Order order, @PathVariable Long id, BindingResult bindingResult) {
      try {
         if(bindingResult.hasErrors()) {
            throw new InvalidOrderException(INVALID_ORDER_FORMAT, order);
         }
         orderService.updateOrderById(order, id);
      } catch(Exception ex) {
         log.info("Encounter error " + ex.getMessage() + " when trying to update your order");
         return "redirect:/client/orders?updated=false";
      }
      return "redirect:/client/orders?updated=true";
   }

   @GetMapping("/delete-order/{id}")
   public String deleteOrderView(@PathVariable Long id, Model model) {
      model.addAttribute("order", orderService.getOrderById(id));
      return "orders/delete-order";
   }

   @PostMapping("/delete-order/{id}")
   public String deleteOrderView(@ModelAttribute("order") Order order, @PathVariable Long id, BindingResult bindingResult) {
      try {
         if(bindingResult.hasErrors()) {
            throw new InvalidOrderException(INVALID_ORDER_FORMAT, order);
         }
         orderService.deleteOrderById(id);
      } catch(Exception ex) {
         log.info("Encounter error " + ex.getMessage() + " when trying to delete your order");
         return "redirect:/client/orders?deleted=false";
      }
      return "redirect:/client/orders?deleted=true";
   }
}
