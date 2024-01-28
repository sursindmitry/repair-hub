package com.sursindmitry.repairhub.service.advice;

import com.sursindmitry.repairhub.service.exception.MessagingLogicException;
import com.sursindmitry.repairhub.service.exception.MustBeNullException;
import com.sursindmitry.repairhub.service.exception.MustNotBeNullOrEmptyException;
import com.sursindmitry.repairhub.service.exception.UserIsExistingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ServiceExceptionHandler {

  @ExceptionHandler(MessagingLogicException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<String> handleMessagingLogicException(
      MessagingLogicException ex) {

    log.error(ex.getMessage());
    return ResponseEntity.badRequest().body(ex.getMessage());
  }

  @ExceptionHandler(MustBeNullException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<String> handleMustBeNullException(
      MustBeNullException ex) {

    log.error(ex.getMessage());
    return ResponseEntity.badRequest().body(ex.getMessage());
  }

  @ExceptionHandler(MustNotBeNullOrEmptyException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<String> handleMustNotBeNullOrEmptyException(
      MustNotBeNullOrEmptyException ex) {

    log.error(ex.getMessage());
    return ResponseEntity.badRequest().body(ex.getMessage());
  }

  @ExceptionHandler(UserIsExistingException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<String> handleUserIsExistingException(
      UserIsExistingException ex) {

    log.error(ex.getMessage(), ex.getEmail());
    return ResponseEntity.badRequest().body(ex.getMessage());
  }

}
