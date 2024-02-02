package com.sursindmitry.repairhub.service;

import com.sursindmitry.repairhub.database.entity.RefreshToken;
import com.sursindmitry.repairhub.web.dto.JwtResponse;

public interface RefreshTokenService {
  RefreshToken createRefreshToken(String username);

  JwtResponse verifyRefreshToken(String refreshToken);
}
