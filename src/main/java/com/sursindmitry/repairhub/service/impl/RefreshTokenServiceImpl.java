package com.sursindmitry.repairhub.service.impl;

import com.sursindmitry.repairhub.config.security.jwt.UserAuthProvider;
import com.sursindmitry.repairhub.database.entity.RefreshToken;
import com.sursindmitry.repairhub.database.entity.User;
import com.sursindmitry.repairhub.database.repository.RefreshTokenRepository;
import com.sursindmitry.repairhub.database.repository.UserRepository;
import com.sursindmitry.repairhub.service.RefreshTokenService;
import com.sursindmitry.repairhub.service.UserService;
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

  private final UserService userService;

  private final UserAuthProvider userAuthProvider;


  @Override
  public RefreshToken createRefreshToken(String username) {

    User user = userService.getUserByEmail(username);

    return createOrUpdateRefreshToken(user);
  }

  private RefreshToken createOrUpdateRefreshToken(User user) {
    RefreshToken refreshToken = user.getRefreshToken();

    if (refreshToken == null) {
      refreshToken = RefreshToken.builder()
          .refreshToken(generateRefreshTokenValue())
          .build();
    }

    refreshToken.setExpirationTime(
        new Timestamp(System.currentTimeMillis() + jwtTokenRefresh * 60 * 60 * 1000));
    user.setRefreshToken(refreshToken);
    refreshTokenRepository.save(refreshToken);

    return refreshToken;
  }

  @Override
  public JwtResponse verifyRefreshToken(String tokenRequest) {
    RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(tokenRequest)
        .orElseThrow(() -> new TokenNotFoundException("Token does not exist"));

    checkTokenExpiration(refreshToken);

    refreshToken.setExpirationTime(
        new Timestamp(System.currentTimeMillis() + jwtTokenRefresh * 60 * 60 * 1000));
    refreshToken.setRefreshToken(generateRefreshTokenValue());

    User user = refreshToken.getUser();
    String token = userAuthProvider.generateToken(user.getEmail(), user.getAuthorities());

    refreshTokenRepository.save(refreshToken);

    return new JwtResponse(token, refreshToken.getRefreshToken(), user.getEmail());
  }

  private void checkTokenExpiration(RefreshToken refreshToken) {
    if (refreshToken.getExpirationTime().compareTo(new Timestamp(System.currentTimeMillis())) < 0) {
      refreshTokenRepository.delete(refreshToken);
      throw new TokenExpirationTimeException("Refresh token Expired");
    }
  }

  private static String generateRefreshTokenValue() {
    return UUID.randomUUID().toString();
  }
}
