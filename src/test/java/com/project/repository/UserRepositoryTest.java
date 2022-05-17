package com.project.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.project.mocks.UserMock.getMockedUser;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTest {

   @Autowired
   private UserRepository userRepository;

   @Test
   @DisplayName("saveUser")
   public void saveUser_shouldReturnSavedUser() {
      userRepository.save(getMockedUser());
   }

   @Test
   @DisplayName("getAllUsers")
   public void getAllUsers_shouldReturnAllUsers() {
      assertThat(userRepository.findAll().toString()).isEqualTo(List.of(getMockedUser()).toString());
   }

   @Test
   @DisplayName("findUserByEmailAddress")
   void findUserByEmailAddress_shouldReturnUser_whenGivenEmailAddress() {
      assertThat(userRepository.findUserByEmailAddress("georgebacalu@email.com").toString()).isEqualTo(getMockedUser().toString());
   }
}
