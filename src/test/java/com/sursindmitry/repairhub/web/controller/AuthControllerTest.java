package com.sursindmitry.repairhub.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sursindmitry.repairhub.AbstractIntegrationTest;
import com.sursindmitry.repairhub.database.entity.Role;
import com.sursindmitry.repairhub.database.entity.User;
import com.sursindmitry.repairhub.service.RegisterFacadeService;
import com.sursindmitry.repairhub.service.VerificationService;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest extends AbstractIntegrationTest {

  @MockBean
  private RegisterFacadeService registerFacadeService;

  @MockBean
  private VerificationService verificationService;

  @Autowired
  private MockMvc mvc;

  @Value("${spring.application.base-url}")
  private String baseUrl;

  @Test
  void register() throws Exception {
    User user = User.builder()
        .id(1L)
        .email("test@gmail.com")
        .firstName("FistName")
        .lastName("LastName")
        .password("password")
        .active(false)
        .roles(Collections.singleton(Role.ROLE_USER))
        .build();
    when(registerFacadeService.register(any(User.class))).thenReturn(user);

    ResultActions response = mvc.perform(
        post(this.baseUrl + "/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                  {
                      "email": "test@gmail.com",
                      "firstName": "FistName",
                      "lastName": "LastName",
                      "password": "password"
                  }
                """
            )
    );

    response
        .andExpect(status().isCreated())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpectAll(
            jsonPath("$.email").value("test@gmail.com"),
            jsonPath("$.firstName").value("FistName"),
            jsonPath("$.lastName").value("LastName"),
            jsonPath("$.message").value("Вы зарегистрировались. Подтвердите почту.")

        );
  }


  @Test
  void verification() throws Exception {
    User user = User.builder()
        .id(1L)
        .email("test@gmail.com")
        .firstName("FistName")
        .lastName("LastName")
        .password("password")
        .active(false)
        .roles(Collections.singleton(Role.ROLE_USER))
        .build();

    when(verificationService.verification(any(String.class))).thenReturn(user);

    ResultActions response = mvc.perform(get(this.baseUrl + "/auth/verification")
        .param("token", "token")
    );

    response
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpectAll(
            jsonPath("$.email").value("test@gmail.com"),
            jsonPath("$.message").value("Вы подтвердили почту. Войдите в аккаунт")
        );
  }
}