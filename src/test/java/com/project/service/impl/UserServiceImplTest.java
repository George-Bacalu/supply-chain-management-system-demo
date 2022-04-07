package com.project.service.impl;

import com.project.entity.User;
import com.project.repository.UserRepository;
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
import static com.project.mocks.UserMock.getMockedUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

   User user = getMockedUser();

   @Mock
   private UserRepository userRepositoryTest;

   @InjectMocks
   private UserServiceImpl userServiceTest;

   @BeforeEach
   public void setUp() {
      userServiceTest = new UserServiceImpl(userRepositoryTest);
   }

   @Test
   @DisplayName("getAllUsersService")
   public void getAllUsers_service_repository_shouldReturnAllUsers() {
      userServiceTest.getAllUsers();

      given(userRepositoryTest.findAll()).willReturn(List.of(user));

      verify(userRepositoryTest).findAll();
   }

   @Test
   @DisplayName("loadUserByUsernameIsValid")
   public void loadUserByUsername_shouldReturnUser_whenGivenUsernameIsValid() {
      String username = "georgebacalu@email.com";
      given(userRepositoryTest.findByEmailId(username)).willReturn(user);
      userServiceTest.loadUserByUsername(username);

      verify(userRepositoryTest).findByEmailId(username);
   }

   @Test
   @DisplayName("loadUserByUsernameIsInValid")
   public void loadUserByUsername_shouldThrowException_whenGivenUsernameIsInValid() {
      String username = "georgebacalu@email.com";
      given(userRepositoryTest.findByEmailId(username)).willReturn(null);

      assertThatThrownBy(() -> userServiceTest.loadUserByUsername(username))
              .isInstanceOf(UsernameNotFoundException.class)
              .hasMessageContaining(USERNAME_NOT_FOUND);

      verify(userRepositoryTest, never()).findByEmailId(username);
   }

   @Test
   @DisplayName("saveUser")
   public void saveUser_shouldReturnUser() {
      ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
      verify(userRepositoryTest).save(userArgumentCaptor.capture());

      User capturedUser = userArgumentCaptor.getValue();
      assertThat(capturedUser).isEqualTo(user);
   }
}