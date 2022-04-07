package com.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.entity.User;
import com.project.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.project.mocks.UserMock.getMockedUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserRegistrationRestControllerTest {

   User user = getMockedUser();

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private ObjectMapper mapper;

   @Mock
   private UserService userServiceTest;

   @Test
   @DisplayName("getAllUsers")
   public void getAllUsers_shouldReturnAllUsers_whenGetMethod() throws Exception {
      given(userServiceTest.getAllUsers()).willReturn(List.of(user));

      MvcResult result = mockMvc.perform(get("/api/client/orders")
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$", hasSize(1)))
              .andExpect(jsonPath("$[0].emailId", is(user.getEmailId())))
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(200);
   }

   @Test
   @DisplayName("saveUserIsValid")
   public void saveUser_shouldReturnUser_whenPostMethod_ifUsernameFound() throws Exception {
      given(userServiceTest.saveUser(user)).willReturn(user);

      MvcResult result = mockMvc.perform(post("/api/client/orders")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(mapper.writeValueAsString(user)))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.emailId", is(user.getEmailId())))
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(201);
   }

   @Test
   @DisplayName("saveUserIsInvalid")
   public void saveUser_shouldThrowException_whenPostMethod_ifUsernameNotFound() throws Exception {
      given(userServiceTest.saveUser(user)).willReturn(user);

      MvcResult result = mockMvc.perform(post("/api/client/orders")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(mapper.writeValueAsString(user)))
              .andExpect(status().isNotFound())
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(404);
   }
}