package com.project.service.impl;

import com.project.entity.User;
import com.project.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static com.project.constant.UserAuthorityConstants.USERNAME_NOT_FOUND;
import static com.project.mocks.UserMock.getMockedUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

   @Mock
   private UserRepository userRepository;
   @InjectMocks
   private UserServiceImpl userService;

   @Captor
   private ArgumentCaptor<User> userCaptor;

   @Test
   @DisplayName("saveUser")
   public void saveUser_shouldReturnUser() {
      given(userRepository.save(getMockedUser())).willReturn(getMockedUser());

      verify(userRepository).save(userCaptor.capture());

      assertThat(userCaptor.getValue()).isEqualTo(getMockedUser());
   }

   @Test
   @DisplayName("getAllUsers")
   public void getAllUsers_shouldReturnAllUsers() {
      userService.getAllUsers();

      given(userRepository.findAll()).willReturn(List.of(getMockedUser()));

      verify(userRepository).findAll();
   }

   @Test
   @DisplayName("whenValidUsername_loadUserByUsername")
   public void loadUserByUsername_shouldReturnUser_whenGivenUsernameIsValid() {
      String username = "georgebacalu@email.com";
      given(userRepository.findUserByEmailAddress(username)).willReturn(getMockedUser());
      userService.loadUserByUsername(username);

      verify(userRepository).findUserByEmailAddress(username);
   }

   @Test
   @DisplayName("whenInvalidUsername_throwException")
   public void loadUserByUsername_shouldThrowException_whenGivenUsernameIsInvalid() {
      String username = "georgebacalu.email.com";
      given(userRepository.findUserByEmailAddress(username)).willReturn(null);

      assertThatThrownBy(() -> userService.loadUserByUsername(username))
              .isInstanceOf(UsernameNotFoundException.class)
              .hasMessageContaining(USERNAME_NOT_FOUND);

      verify(userRepository, never()).findUserByEmailAddress(username);
   }
}