package com.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static com.project.mocks.UserMock.getMockedUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRegistrationRestControllerTest {

   User user = getMockedUser();

   private MockMvc mockMvc;

   @Autowired
   private WebApplicationContext context;

   @Autowired
   private ObjectMapper mapper;

   @Autowired
   private TestRestTemplate template;

   @BeforeEach
   void setUp() {
      mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
   }

   @WithMockUser("/user-1")
   @Test
   @DisplayName("getAllUsers")
   public void shouldReturnAllUsers() throws Exception {
      MvcResult result = mockMvc.perform(get("/api/users")
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(200);
   }

   @WithMockUser("/user-1")
   @Test
   @DisplayName("saveUser")
   public void whenGivenUser_shouldSaveUser() throws Exception {

      MvcResult result = mockMvc.perform(post("/api/users")
                      .content(mapper.writeValueAsString(user))
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andReturn();

      assertThat(result.getResponse().getStatus()).isEqualTo(201);
   }

   @Test
   @DisplayName("getAllUsersRestTemplate")
   public void shouldReturnAllUsersRestTemplate() {
      ResponseEntity<?> response = template.withBasicAuth("george", "george").getForEntity("/api/users", ArrayList.class);

      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
   }
}