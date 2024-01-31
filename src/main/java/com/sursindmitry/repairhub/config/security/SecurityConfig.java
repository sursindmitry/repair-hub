package com.sursindmitry.repairhub.config.security;

import com.sursindmitry.repairhub.config.security.jwt.JwtAuthenticationEntryPoint;
import com.sursindmitry.repairhub.config.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
  private final JwtAuthenticationEntryPoint point;

  private final JwtAuthenticationFilter filter;

  private final UserDetailsService userDetailsService;

  public SecurityConfig(JwtAuthenticationEntryPoint point, @Lazy JwtAuthenticationFilter filter,
                        UserDetailsService userDetailsService) {
    this.point = point;
    this.filter = filter;
    this.userDetailsService = userDetailsService;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
      throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.csrf(AbstractHttpConfigurer::disable);

    http.cors(AbstractHttpConfigurer::disable);

    http.authorizeHttpRequests(auth -> auth
        .requestMatchers("/v1/user/**").hasRole("USER")
        .requestMatchers("/v1/admin/**").hasRole("ADMIN")
        .requestMatchers("/v1/auth/**", "/swagger-ui/**", "/v3/**").permitAll());

    http.exceptionHandling(ex -> ex.authenticationEntryPoint(point));

    http.sessionManagement(
        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    );

    http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return daoAuthenticationProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
