package com.sursindmitry.repairhub.service.impl;

import com.sursindmitry.repairhub.config.security.jwt.UserAuthProvider;
import com.sursindmitry.repairhub.database.entity.RefreshToken;
import com.sursindmitry.repairhub.database.entity.User;
import com.sursindmitry.repairhub.service.LoginService;
import com.sursindmitry.repairhub.service.RefreshTokenService;
import com.sursindmitry.repairhub.service.UserService;
import com.sursindmitry.repairhub.web.dto.JwtRequest;
import com.sursindmitry.repairhub.web.dto.JwtResponse;
import java.nio.CharBuffer;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

  private final UserService userService;
  private final RefreshTokenService refreshTokenService;
  private final UserAuthProvider userAuthProvider;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public JwtResponse login(JwtRequest request) {
    User user = userService.getUserByEmail(request.email());

    checkUserActivation(user);

    if (passwordEncoder.matches(CharBuffer.wrap(request.password()), user.getPassword())) {
      String token = generateToken(request.email(), user.getAuthorities());

      RefreshToken refreshToken = refreshTokenService.creteRefreshToken(request.email());

      return new JwtResponse(token, refreshToken.getRefreshToken(), request.email());
    }
    throw new BadCredentialsException("Invalid password");
  }

  private static void checkUserActivation(User user) {
    if (!user.isActive()) {
      throw new BadCredentialsException("You haven't confirmed your email");
    }
  }

  private String generateToken(String email, Collection<? extends GrantedAuthority> authorities) {
    return userAuthProvider.generateToken(email, authorities);
  }

}
