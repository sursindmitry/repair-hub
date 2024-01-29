package com.sursindmitry.repairhub.service.exception;

public class TokenExpirationTimeException extends RuntimeException {
  public TokenExpirationTimeException(String message) {
    super(message);
  }
}
