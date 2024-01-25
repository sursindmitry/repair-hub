package com.sursindmitry.repairhub.service.impl;

import com.sursindmitry.repairhub.database.entity.EmailVerificationToken;
import com.sursindmitry.repairhub.database.entity.User;
import com.sursindmitry.repairhub.service.EmailService;
import com.sursindmitry.repairhub.service.EmailVerificationTokenService;
import com.sursindmitry.repairhub.service.RegisterFacadeService;
import com.sursindmitry.repairhub.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterFacadeServiceImpl implements RegisterFacadeService {

  private final RegistrationService registrationService;
  private final EmailVerificationTokenService emailVerificationTokenService;
  private final EmailService emailService;

  @Override
  public User register(User user) {
    User savedUser = registrationService.register(user);

    EmailVerificationToken emailVerificationToken =
        emailVerificationTokenService.saveToken(savedUser);

    emailService.sendConfirmationEmail(savedUser.getEmail(), savedUser.getFirstName(),
        savedUser.getLastName(), emailVerificationToken.getToken());

    return savedUser;
  }
}
