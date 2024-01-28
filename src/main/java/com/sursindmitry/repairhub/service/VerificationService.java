package com.sursindmitry.repairhub.service;

import com.sursindmitry.repairhub.database.entity.User;

public interface VerificationService {
  User verification(String token);
}
