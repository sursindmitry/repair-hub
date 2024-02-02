package com.sursindmitry.repairhub.service.advice;

import com.sursindmitry.repairhub.service.exception.EmailTokenNotFoundException;
import com.sursindmitry.repairhub.service.exception.ErrorResponse;
import com.sursindmitry.repairhub.service.exception.MessagingLogicException;
import com.sursindmitry.repairhub.service.exception.MustBeNullException;
import com.sursindmitry.repairhub.service.exception.MustNotBeNullOrEmptyException;
import com.sursindmitry.repairhub.service.exception.TokenExpirationTimeException;
import com.sursindmitry.repairhub.service.exception.TokenNotFoundException;
import com.sursindmitry.repairhub.service.exception.UserIsExistingException;
import com.sursindmitry.repairhub.service.exception.UsernameNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ServiceExceptionHandler {

  @ExceptionHandler(MessagingLogicException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleMessagingLogicException(
      MessagingLogicException ex) {

    log.error(ex.getMessage());
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MustBeNullException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleMustBeNullException(
      MustBeNullException ex) {

    log.error(ex.getMessage());
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MustNotBeNullOrEmptyException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleMustNotBeNullOrEmptyException(
      MustNotBeNullOrEmptyException ex) {

    log.error(ex.getMessage());
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UserIsExistingException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleUserIsExistingException(
      UserIsExistingException ex) {

    log.error(ex.getMessage(), ex.getEmail());
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EmailTokenNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleEmailTokenNotFoundException(
      EmailTokenNotFoundException ex) {

    log.error(ex.getMessage());
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(TokenExpirationTimeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleTokenExpirationTimeException(
      TokenExpirationTimeException ex) {

    log.error(ex.getMessage());
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(TokenNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleTokenNotFoundException(
      TokenNotFoundException ex) {

    log.error(ex.getMessage());
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadCredentialsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleBadCredentialsException(
      BadCredentialsException ex) {

    log.error(ex.getMessage());
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(
      UsernameNotFoundException ex) {

    log.error(ex.getMessage());
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }
}
