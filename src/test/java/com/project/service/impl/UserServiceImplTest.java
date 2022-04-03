package com.project.service.impl;

import com.project.entity.Order;
import com.project.entity.User;
import com.project.repository.UserRepository;
import com.project.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static com.project.constant.UserAuthorityConstants.USERNAME_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

   User user = User.builder().userId(1L).firstName("George").lastName("Bacalu").emailId("georgebacalu@email.com").password("georgebacalu").build();

   @Mock
   private UserRepository userRepositoryTest;

   @InjectMocks
   private UserService userServiceTest;

   @BeforeEach
   public void setUp() {
      userServiceTest = new UserServiceImpl(userRepositoryTest);
   }

   @Test
   @DisplayName("saveUser")
   public void whenSaveUser_shouldSaveUser() {
      userServiceTest.saveUser(user);

      ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
      verify(userRepositoryTest).save(userArgumentCaptor.capture());

      User capturedUser = userArgumentCaptor.getValue();
      assertThat(capturedUser).isEqualTo(user);
   }

   @Test
   @DisplayName("getAllUsers")
   public void shouldReturnAllUsers() {
      userServiceTest.getAllUsers();

      given(userRepositoryTest.findAll()).willReturn(List.of(user));

      verify(userRepositoryTest).findAll();
   }

   @Test
   @DisplayName("loadUserByUsername")
   public void whenGivenUsername_shouldReturnUser_ifFound() {
      String username = "georgebacalu@email.com";
      given(userRepositoryTest.findByEmailId(username)).willReturn(user);
      userServiceTest.loadUserByUsername(username);

      verify(userRepositoryTest).findByEmailId(username);
   }

   @Test
   public void whenGivenUsername_shouldThrowException_ifNotFound() {
      String username = "georgebacalu@email.com";
      given(userRepositoryTest.findByEmailId(username)).willReturn(null);

      assertThatThrownBy(() -> userServiceTest.loadUserByUsername(username))
              .isInstanceOf(UsernameNotFoundException.class)
              .hasMessageContaining(USERNAME_NOT_FOUND);

      verify(userRepositoryTest, never()).findByEmailId(username);
   }
}