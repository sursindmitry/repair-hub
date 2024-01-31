package com.sursindmitry.repairhub.service.impl;

import com.sursindmitry.repairhub.config.security.jwt.JwtHelper;
import com.sursindmitry.repairhub.service.LoginService;
import com.sursindmitry.repairhub.web.dto.LoginRequest;
import com.sursindmitry.repairhub.web.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

  private final UserDetailsService userDetailsService;

  private final AuthenticationManager authenticationManager;

  private final JwtHelper helper;

  @Override
  @Transactional
  public LoginResponse login(LoginRequest request) {
    authenticate(request.email(), request.password());

    UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());
    String token = helper.generateToken(userDetails);

    return new LoginResponse(token, userDetails.getUsername());
  }

  private void authenticate(String email, String password) {
    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(email, password);
    try {
      authenticationManager.authenticate(authentication);
    } catch (BadCredentialsException ex) {
      throw new BadCredentialsException("Invalid username or password");
    }
  }
}
