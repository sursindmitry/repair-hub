package com.sursindmitry.repairhub.service.impl;

import com.sursindmitry.repairhub.database.entity.EmailVerificationToken;
import com.sursindmitry.repairhub.database.entity.User;
import com.sursindmitry.repairhub.database.repository.EmailVerificationTokenRepository;
import com.sursindmitry.repairhub.service.EmailVerificationTokenService;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailVerificationTokenServiceImpl implements EmailVerificationTokenService {
  @Value("${spring.application.expiration.token.verification-email}")
  private int expirationTime;

  private final EmailVerificationTokenRepository emailVerificationTokenRepository;

  @Override
  public EmailVerificationToken saveToken(User savedUser) {
    String token = UUID.randomUUID().toString();

    return emailVerificationTokenRepository.save(EmailVerificationToken.builder()
        .token(token)
        .user(savedUser)
        .expirationTime(calculateExpiryDate(expirationTime))
        .build());
  }

  private Timestamp calculateExpiryDate(int hour) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.HOUR, hour);
    return new Timestamp(calendar.getTime().getTime());
  }
}
