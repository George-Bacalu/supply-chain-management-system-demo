package com.project.repository;

import com.project.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.project.mock.UserMock.getMockUser;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTest {

   User user = getMockUser();

   @Autowired
   private UserRepository userRepositoryTest;

   @Test
   @DisplayName("saveUser")
   public void whenGivenUser_shouldSaveUser() {
      userRepositoryTest.save(user);
   }

   @Test
   @DisplayName("getAllUsers")
   public void shouldReturnAllUsers() {
      assertThat(userRepositoryTest.findAll()).isEqualTo(List.of(user));
   }

   @Test
   @DisplayName("findByEmailBy")
   void whenGivenEmailId_shouldReturnUser() {
      assertThat(userRepositoryTest.findByEmailId("georgebacalu@email.com")).isEqualTo(user);
   }
}