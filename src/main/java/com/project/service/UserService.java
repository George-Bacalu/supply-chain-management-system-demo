package com.project.service;

import com.project.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

   void saveUser(User user);
}
