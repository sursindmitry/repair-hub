package com.sursindmitry.repairhub.service;

import com.sursindmitry.repairhub.web.dto.JwtRequest;
import com.sursindmitry.repairhub.web.dto.JwtResponse;

public interface LoginService {
  JwtResponse login(JwtRequest request);
}
