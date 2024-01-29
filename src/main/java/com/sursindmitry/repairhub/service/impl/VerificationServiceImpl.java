package com.sursindmitry.repairhub.service.impl;

import com.sursindmitry.repairhub.database.entity.EmailVerificationToken;
import com.sursindmitry.repairhub.database.entity.User;
import com.sursindmitry.repairhub.database.repository.EmailVerificationTokenRepository;
import com.sursindmitry.repairhub.database.repository.UserRepository;
import com.sursindmitry.repairhub.service.VerificationService;
import com.sursindmitry.repairhub.service.exception.EmailTokenNotFoundException;
import com.sursindmitry.repairhub.service.exception.TokenExpirationTimeException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

  private final EmailVerificationTokenRepository emailVerificationTokenRepository;
  private final UserRepository userRepository;

  @Override
  public User verification(String token) {
    EmailVerificationToken verificationToken =
        emailVerificationTokenRepository.findByToken(token).orElseThrow(
            () -> new EmailTokenNotFoundException("Token not found")
        );

    if (verificationToken.getExpirationTime().after(Timestamp.valueOf(LocalDateTime.now()))) {
      User savedUser = verificationToken.getUser();
      savedUser.setActive(true);

      userRepository.save(savedUser);

      emailVerificationTokenRepository.delete(verificationToken);
      return savedUser;
    } else {
      userRepository.delete(verificationToken.getUser());
      throw new TokenExpirationTimeException("Expiration time has expired. Try register again.");
    }
  }
}
