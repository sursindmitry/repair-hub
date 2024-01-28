package com.sursindmitry.repairhub.service.exception;

public class EmailTokenNotFoundException extends RuntimeException {
  public EmailTokenNotFoundException(String message) {
    super(message);
  }
}
