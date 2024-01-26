package com.sursindmitry.repairhub.service.impl;

import com.sursindmitry.repairhub.database.entity.Role;
import com.sursindmitry.repairhub.database.entity.User;
import com.sursindmitry.repairhub.database.repository.UserRepository;
import com.sursindmitry.repairhub.service.RegistrationService;
import com.sursindmitry.repairhub.service.exception.MustBeNullException;
import com.sursindmitry.repairhub.service.exception.MustNotBeNullOrEmptyException;
import com.sursindmitry.repairhub.service.exception.UserIsExistingException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
  private final UserRepository userRepository;

  @Override
  public User register(User user) {
    validUser(user);

    userRepository.findByEmail(user.getEmail()).ifPresent(existingUser -> {
      throw new UserIsExistingException(
          "User already exists.", existingUser.getEmail());
    });

    user.setActive(false);
    user.setRoles(Collections.singleton(Role.ROLE_USER));

    return userRepository.save(user);
  }

  private void validUser(User user) {
    validId(user.getId());
    validField(user.getEmail(), "Email");
    validField(user.getFirstName(), "First name");
    validField(user.getLastName(), "Last name");
    validField(user.getPassword(), "Password");
  }

  private void validId(Long id) {
    if (id != null) {
      throw new MustBeNullException("User Id must be null");
    }
  }

  private void validField(String value, String fieldName) {
    if (isNullOrEmpty(value)) {
      throw new MustNotBeNullOrEmptyException(fieldName + " must not be null or empty");
    }
  }

  private boolean isNullOrEmpty(String str) {
    return str == null || str.trim().isEmpty();
  }
}
