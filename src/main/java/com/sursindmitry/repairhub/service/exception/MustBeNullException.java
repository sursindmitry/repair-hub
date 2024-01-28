package com.sursindmitry.repairhub.service.exception;

public class MustBeNullException extends RuntimeException {
  public MustBeNullException(String message) {
    super(message);
  }
}
