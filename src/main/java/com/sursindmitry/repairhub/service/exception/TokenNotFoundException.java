package com.sursindmitry.repairhub.service.exception;

public class TokenNotFoundException extends RuntimeException {
  public TokenNotFoundException(String message) {
    super(message);
  }
}
