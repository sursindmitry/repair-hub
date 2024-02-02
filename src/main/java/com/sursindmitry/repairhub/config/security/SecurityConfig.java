package com.sursindmitry.repairhub.config.security;

import com.sursindmitry.repairhub.config.security.jwt.JwtAuthenticationFilter;
import com.sursindmitry.repairhub.config.security.jwt.UserAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
  private final UserAuthProvider userAuthProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable);

    http.exceptionHandling(ex -> {
      ex.authenticationEntryPoint(userAuthenticationEntryPoint);
    });

    http.addFilterBefore(new JwtAuthenticationFilter(userAuthProvider),
        BasicAuthenticationFilter.class);

    http.sessionManagement(session -> {
      session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    });

    http.authorizeHttpRequests((request) -> {
      request.requestMatchers(HttpMethod.POST,
          "/v1/auth/login",
          "/v1/auth/refresh",
          "/v1/auth/register")
          .permitAll();

      request.requestMatchers(HttpMethod.GET,
          "/v1/auth/verification")
          .permitAll();

      request.requestMatchers("/v1/user/**").hasRole("USER");
      request.requestMatchers("/v1/admin/**").hasRole("ADMIN");
      request.anyRequest().authenticated();
    });

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
