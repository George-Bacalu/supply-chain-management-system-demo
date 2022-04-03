package com.project.controller;

import com.project.entity.User;
import com.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class UserRegistrationModelViewController {

   private final UserService userService;

   @GetMapping("/registration")
   public String showRegistrationPage(Model model) {
      model.addAttribute("user", new User());
      return "auth/registration";
   }

   @PostMapping("/registration")
   public String registerUserAccount(@ModelAttribute("user") User user) {
      userService.saveUser(user);
      return "redirect:/registration?success";
   }

   @GetMapping("/login")
   public String showLoginPage(Model model) {
      model.addAttribute("user", new User());
      return "auth/login";
   }

   @PostMapping("/login")
   public String loginUser(@ModelAttribute("user") User user) {
      return "redirect:/home";
   }

   @GetMapping("/home")
   public String showHomePage() {
      return "index";
   }

   @GetMapping("/")
   public String redirectToHomePage() {
      return "redirect:/home";
   }
}
