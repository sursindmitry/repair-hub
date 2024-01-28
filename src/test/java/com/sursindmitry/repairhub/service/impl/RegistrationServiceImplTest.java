package com.sursindmitry.repairhub.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.sursindmitry.repairhub.AbstractIntegrationTest;
import com.sursindmitry.repairhub.database.entity.Role;
import com.sursindmitry.repairhub.database.entity.User;
import com.sursindmitry.repairhub.database.repository.UserRepository;
import com.sursindmitry.repairhub.service.RegistrationService;
import com.sursindmitry.repairhub.service.exception.MustBeNullException;
import com.sursindmitry.repairhub.service.exception.MustNotBeNullOrEmptyException;
import com.sursindmitry.repairhub.service.exception.UserIsExistingException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RegistrationServiceImplTest extends AbstractIntegrationTest {

  @Autowired
  private RegistrationService registrationService;

  @Autowired
  private UserRepository userRepository;


  @Test
  @Transactional
  void register() {
    User user = new User();
    user.setEmail("test@gmail.com");
    user.setPassword("password");
    user.setFirstName("FirstName");
    user.setLastName("LastName");

    User registeredUser = registrationService.register(user);

    assertEquals(user.getId(), registeredUser.getId());
    assertEquals(user.getEmail(), registeredUser.getEmail());
    assertEquals(user.getFirstName(), registeredUser.getFirstName());
    assertEquals(user.getLastName(), registeredUser.getLastName());
    assertEquals(user.getPassword(), registeredUser.getPassword());
    assertEquals(Collections.singleton(Role.ROLE_USER), registeredUser.getRoles());
    assertFalse(registeredUser.isActive());
  }

  @Test
  @Transactional
  void registerIfUserIdIsNull() {
    User user = new User();
    user.setId(1L);

    assertThrows(MustBeNullException.class, () ->
        registrationService.register(user)
    );
  }

  @Test
  @Transactional
  void registerIfUserEmailIsNull() {
    User user = new User();
    user.setEmail(null);

    assertThrows(MustNotBeNullOrEmptyException.class, () ->
        registrationService.register(user)
    );
  }

  @Test
  @Transactional
  void registerIfUserFirstNameIsNull() {
    User user = new User();
    user.setEmail("test@gmail.com");
    user.setFirstName(null);

    assertThrows(MustNotBeNullOrEmptyException.class, () ->
        registrationService.register(user)
    );
  }

  @Test
  @Transactional
  void registerIfUserLastNameIsNull() {
    User user = new User();
    user.setEmail("test@gmail.com");
    user.setFirstName("FistName");
    user.setLastName(null);

    assertThrows(MustNotBeNullOrEmptyException.class, () ->
        registrationService.register(user)
    );
  }

  @Test
  @Transactional
  void registerIfUserPasswordIsNull() {
    User user = new User();
    user.setEmail("test@gmail.com");
    user.setFirstName("FistName");
    user.setLastName("LastName");
    user.setPassword(null);

    assertThrows(MustNotBeNullOrEmptyException.class, () ->
        registrationService.register(user)
    );
  }

  @Test
  @Transactional
  void registerIfUserIsExisting() {
    userRepository.save(User.builder()
        .email("test@gmail.com")
        .password("password")
        .firstName("FistName")
        .lastName("LastName")
        .roles(Collections.singleton(Role.ROLE_USER))
        .active(false).build());


    User savedUser = new User();
    savedUser.setFirstName("FistName");
    savedUser.setLastName("LastName");
    savedUser.setEmail("test@gmail.com");
    savedUser.setPassword("password");

    assertThrows(UserIsExistingException.class, () ->
        registrationService.register(savedUser)
    );
  }
}