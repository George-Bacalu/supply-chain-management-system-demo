package com.project.service.impl;

import com.project.entity.Authority;
import com.project.entity.User;
import com.project.repository.UserRepository;
import com.project.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.project.constant.UserAuthorityConstants.USERNAME_NOT_FOUND;

@Service
public class UserServiceImpl implements UserService {

   private final UserRepository userRepository;
   private final BCryptPasswordEncoder passwordEncoder;

   @Lazy
   public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
      this.userRepository = userRepository;
      this.passwordEncoder = passwordEncoder;
   }

   @Override
   public List<User> getAllUsers() {
      return userRepository.findAll();
   }

   @Override
   public User saveUser(User user) {
      return userRepository.save(User.builder()
              .userId(user.getUserId())
              .firstName(user.getFirstName())
              .lastName(user.getLastName())
              .emailAddress(user.getEmailAddress())
              .password(passwordEncoder.encode(user.getPassword()))
              .authorities(user.getAuthorities())
              .build());
   }

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = userRepository.findUserByEmailAddress(username);
      if(user == null) {
         throw new UsernameNotFoundException(USERNAME_NOT_FOUND);
      }
      String email = user.getEmailAddress();
      String password = user.getPassword();
      Collection<? extends GrantedAuthority> roles = mapRolesToAuthorities(user.getAuthorities());
      return new org.springframework.security.core.userdetails.User(email, password, roles);
   }

   private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Authority> authorities) {
      return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
   }
}
