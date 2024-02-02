package com.sursindmitry.repairhub.service.impl;

import com.sursindmitry.repairhub.database.entity.User;
import com.sursindmitry.repairhub.database.repository.UserRepository;
import com.sursindmitry.repairhub.service.UserService;
import com.sursindmitry.repairhub.service.exception.UsernameNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
