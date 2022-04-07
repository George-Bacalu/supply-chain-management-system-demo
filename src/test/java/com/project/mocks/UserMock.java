package com.project.mocks;

import com.project.entity.User;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserMock {

   public static User getMockedUser() {
      return User.builder().userId(1L).firstName("George").lastName("Bacalu").emailId("georgebacalu@email.com").password("georgebacalu").roles(List.of()).build();
   }
}
