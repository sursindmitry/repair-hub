package com.sursindmitry.repairhub.service.exception;

public class MustNotBeNullOrEmptyException extends RuntimeException {
  public MustNotBeNullOrEmptyException(String message) {
    super(message);
  }

}
