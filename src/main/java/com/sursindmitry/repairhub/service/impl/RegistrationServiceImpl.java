package com.sursindmitry.repairhub.service.impl;

import com.sursindmitry.repairhub.database.entity.Role;
import com.sursindmitry.repairhub.database.entity.User;
import com.sursindmitry.repairhub.database.repository.UserRepository;
import com.sursindmitry.repairhub.service.RegistrationService;
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
    userRepository.findByEmail(user.getEmail()).ifPresent(existingUser -> {
      throw new UserIsExistingException(
          "User already exists.", existingUser.getEmail());
    });

    user.setActive(false);
    user.setRoles(Collections.singleton(Role.ROLE_USER));

    return userRepository.save(user);
  }
}
