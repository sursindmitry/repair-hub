package com.sursindmitry.repairhub.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sursindmitry.repairhub.config.security.SecurityConfig;
import com.sursindmitry.repairhub.config.security.UserAuthenticationEntryPoint;
import com.sursindmitry.repairhub.config.security.WebConfig;
import com.sursindmitry.repairhub.config.security.jwt.UserAuthProvider;
import com.sursindmitry.repairhub.database.entity.Role;
import com.sursindmitry.repairhub.database.entity.User;
import com.sursindmitry.repairhub.service.LoginService;
import com.sursindmitry.repairhub.service.RefreshTokenService;
import com.sursindmitry.repairhub.service.RegisterFacadeService;
import com.sursindmitry.repairhub.service.VerificationService;
import com.sursindmitry.repairhub.web.mapper.RegisterMapperImpl;
import com.sursindmitry.repairhub.web.mapper.VerificationMapperImpl;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ContextConfiguration(classes = {WebConfig.class, SecurityConfig.class})
@Import({RegisterMapperImpl.class, VerificationMapperImpl.class})
@WebMvcTest(controllers = AuthController.class)
class AuthControllerTest {

  @MockBean
  private RegisterFacadeService registerFacadeService;

  @MockBean
  private VerificationService verificationService;

  @MockBean
  private UserAuthProvider userAuthProvider;

  @MockBean
  private LoginService loginService;

  @MockBean
  private RefreshTokenService refreshTokenService;

  @MockBean
  private UserAuthenticationEntryPoint userAuthenticationEntryPoint;


  @Autowired
  private MockMvc mvc;


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
        post("/v1/auth/register")
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

    ResultActions response = mvc.perform(get("/v1/auth/verification")
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