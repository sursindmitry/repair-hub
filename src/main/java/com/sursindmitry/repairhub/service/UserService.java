package com.sursindmitry.repairhub.service;

import com.sursindmitry.repairhub.database.entity.User;

public interface UserService {
  User getUserByEmail(String email);
}
