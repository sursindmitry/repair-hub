package com.sursindmitry.repairhub.service;

import com.sursindmitry.repairhub.database.entity.EmailVerificationToken;
import com.sursindmitry.repairhub.database.entity.User;

public interface EmailVerificationTokenService {
  EmailVerificationToken saveToken(User savedUser);
}
