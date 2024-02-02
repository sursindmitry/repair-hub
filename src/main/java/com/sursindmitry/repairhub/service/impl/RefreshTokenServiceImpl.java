package com.sursindmitry.repairhub.service.impl;

import com.sursindmitry.repairhub.config.security.jwt.UserAuthProvider;
import com.sursindmitry.repairhub.database.entity.RefreshToken;
import com.sursindmitry.repairhub.database.entity.User;
import com.sursindmitry.repairhub.database.repository.RefreshTokenRepository;
import com.sursindmitry.repairhub.database.repository.UserRepository;
import com.sursindmitry.repairhub.service.RefreshTokenService;
import com.sursindmitry.repairhub.service.exception.TokenExpirationTimeException;
import com.sursindmitry.repairhub.service.exception.TokenNotFoundException;
import com.sursindmitry.repairhub.web.dto.JwtResponse;
import java.sql.Timestamp;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

  @Value("${spring.application.expiration.token.jwt-token-refresh}")
  private Long jwtTokenRefresh;

  private final RefreshTokenRepository refreshTokenRepository;

  private final UserRepository userRepository;

  private final UserAuthProvider userAuthProvider;


  @Override
  public RefreshToken creteRefreshToken(String username) {

    User user = userRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    RefreshToken refreshToken = user.getRefreshToken();

    if (refreshToken == null) {
      refreshToken = RefreshToken.builder()
          .refreshToken(generateRefreshTokenValue())
          .expirationTime(
              new Timestamp(System.currentTimeMillis() + jwtTokenRefresh * 60 * 60 * 1000))
          .user(user)
          .build();
    } else {
      refreshToken.setExpirationTime(
          new Timestamp(System.currentTimeMillis() + jwtTokenRefresh * 60 * 60 * 1000));
    }

    user.setRefreshToken(refreshToken);

    refreshTokenRepository.save(refreshToken);

    return refreshToken;
  }


  @Override
  public JwtResponse verifyRefreshToken(String tokenRequest) {

    RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(tokenRequest)
        .orElseThrow(() -> new TokenNotFoundException("Token does not exists"));

    if (refreshToken.getExpirationTime().compareTo(new Timestamp(System.currentTimeMillis())) < 0) {
      refreshTokenRepository.delete(refreshToken);
      throw new TokenExpirationTimeException("Refresh token Expired");
    }

    refreshToken.setExpirationTime(
        new Timestamp(System.currentTimeMillis() + jwtTokenRefresh * 60 * 60 * 1000));
    refreshToken.setRefreshToken(generateRefreshTokenValue())
    ;
    User user = refreshToken.getUser();

    String token = userAuthProvider.generateToken(user.getEmail(), user.getAuthorities());

    refreshTokenRepository.save(refreshToken);

    return new JwtResponse(token, refreshToken.getRefreshToken(), user.getEmail());
  }

  private static String generateRefreshTokenValue() {
    return UUID.randomUUID().toString();
  }
}
