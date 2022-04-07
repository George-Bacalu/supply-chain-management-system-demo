package com.project.repository;

import com.project.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.project.mocks.UserMock.getMockedUser;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTest {

   User user = getMockedUser();

   @Autowired
   private UserRepository userRepositoryTest;

   @BeforeEach
   void setUp() {
      userRepositoryTest.save(user);
   }

   @Test
   @DisplayName("getAllUsersRepository")
   public void getAllUsers_repository_shouldReturnAllUsers() {
      assertThat(userRepositoryTest.findAll().toString()).isEqualTo(List.of(user).toString());
   }

   @Test
   @DisplayName("findByEmailId")
   void findByEmailId_shouldReturnUser_whenGivenEmailId() {
      assertThat(userRepositoryTest.findByEmailId("georgebacalu@email.com").toString()).isEqualTo(user.toString());
   }
}