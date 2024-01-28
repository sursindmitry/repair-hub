package com.sursindmitry.repairhub.service.impl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.sursindmitry.repairhub.AbstractIntegrationTest;
import com.sursindmitry.repairhub.database.entity.Role;
import com.sursindmitry.repairhub.database.entity.User;
import com.sursindmitry.repairhub.service.RegisterFacadeService;
import jakarta.transaction.Transactional;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RegisterFacadeServiceImplTest extends AbstractIntegrationTest {

  @Autowired
  private RegisterFacadeService registerFacadeService;

  @Test
  @Transactional
  void register() {
    User user = new User();
    user.setEmail("test@gmail.com");
    user.setPassword("password");
    user.setFirstName("FirstName");
    user.setLastName("LastName");

    User registeredUser = registerFacadeService.register(user);

    assertEquals(user.getId(), registeredUser.getId());
    assertEquals(user.getEmail(), registeredUser.getEmail());
    assertEquals(user.getFirstName(), registeredUser.getFirstName());
    assertEquals(user.getLastName(), registeredUser.getLastName());
    assertEquals(user.getPassword(), registeredUser.getPassword());
    assertEquals(Collections.singleton(Role.ROLE_USER), registeredUser.getRoles());
    assertFalse(registeredUser.isActive());
  }
}