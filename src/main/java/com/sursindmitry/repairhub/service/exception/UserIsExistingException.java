package com.sursindmitry.repairhub.service.exception;

import lombok.Getter;

@Getter
public class UserIsExistingException extends RuntimeException {
  private String email;

  public UserIsExistingException(String message, String email) {
    super(message);
    this.email = email;
  }
}
