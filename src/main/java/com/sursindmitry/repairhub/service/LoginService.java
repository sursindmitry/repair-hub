package com.sursindmitry.repairhub.service;

import com.sursindmitry.repairhub.web.dto.LoginRequest;
import com.sursindmitry.repairhub.web.dto.LoginResponse;

public interface LoginService {
  LoginResponse login(LoginRequest request);
}
