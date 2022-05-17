package com.project.mocks;

import com.project.entity.Authority;
import com.project.entity.User;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.project.constant.UserAuthorityConstants.ROLE_ADMIN;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserMock {

   public static User getMockedUser() {
      return User.builder()
              .userId(1L)
              .firstName("George")
              .lastName("Bacalu")
              .emailAddress("georgebacalu@email.com")
              .password("georgebacalu")
              .authorities(List.of(Authority.builder().authorityId(1L).name(ROLE_ADMIN).build())).build();
   }
}
