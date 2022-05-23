package com.project.controller;

import com.project.entity.User;
import com.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.project.constant.UserAuthorityConstants.USERNAME_NOT_FOUND;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserRegistrationRestController {

   private final UserService userService;

   @GetMapping
   public ResponseEntity<List<User>> getAllUsers() {
      return ResponseEntity.ok(userService.getAllUsers());
   }

   @Validated
   @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<User> saveUser(@Valid @RequestBody User user, BindingResult bindingResult) {
      if(bindingResult.hasErrors()) {
         throw new UsernameNotFoundException(USERNAME_NOT_FOUND);
      }
      return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
   }
}
