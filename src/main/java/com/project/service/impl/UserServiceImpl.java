package com.project.service.impl;

import com.project.entity.Role;
import com.project.entity.User;
import com.project.repository.UserRepository;
import com.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

   private final UserRepository userRepository;

   @Autowired
   private BCryptPasswordEncoder passwordEncoder;

   @Override
   public List<User> getAllUsers() {
      return userRepository.findAll();
   }

   @Override
   public User saveUser(User user) {
      Long userId = user.getUserId();
      String firstName = user.getFirstName();
      String lastName = user.getLastName();
      String email = user.getEmailId();
      String password = passwordEncoder.encode(user.getPassword());
      List<Role> roles = user.getRoles();
      return userRepository.save(new User(userId, firstName, lastName, email, password, roles));
   }

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = userRepository.findByEmailId(username);
      if(user == null) {
         throw new UsernameNotFoundException(USERNAME_NOT_FOUND);
      }
      String email = user.getEmailId();
      String password = user.getPassword();
      Collection<? extends GrantedAuthority> roles = mapRolesToAuthorities(user.getRoles());
      return new org.springframework.security.core.userdetails.User(email, password, roles);
   }

   private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
      return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
   }
}
