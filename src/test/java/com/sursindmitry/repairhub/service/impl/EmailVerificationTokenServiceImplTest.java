package com.sursindmitry.repairhub.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sursindmitry.repairhub.AbstractIntegrationTest;
import com.sursindmitry.repairhub.database.entity.EmailVerificationToken;
import com.sursindmitry.repairhub.database.entity.Role;
import com.sursindmitry.repairhub.database.entity.User;
import com.sursindmitry.repairhub.database.repository.UserRepository;
import com.sursindmitry.repairhub.service.EmailVerificationTokenService;
import jakarta.transaction.Transactional;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class EmailVerificationTokenServiceImplTest extends AbstractIntegrationTest {

  @Autowired
  private EmailVerificationTokenService emailVerificationTokenService;

  @Autowired
  private UserRepository userRepository;

  @Test
  @Transactional
  void saveToken() {
    User user = new User();
    user.setEmail("test@gmail.com");
    user.setPassword("password");
    user.setFirstName("FistName");
    user.setLastName("LastName");
    user.setRoles(Collections.singleton(Role.ROLE_USER));
    user.setActive(false);
    userRepository.save(user);

    EmailVerificationToken emailVerificationToken = emailVerificationTokenService.saveToken(user);

    assertEquals(emailVerificationToken.getUser(), user);
  }
}