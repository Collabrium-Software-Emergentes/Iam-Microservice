package com.collabrium.iam.authentication.interfaces.rest.exceptions;

import com.collabrium.iam.authentication.domain.exceptions.UserNotFoundException;
import com.collabrium.iam.shared.interfaces.rest.resources.ErrorResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class DomainExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResource> handleUserNotFoundException(
      UserNotFoundException ex
  ) {

    var errorResource = new ErrorResource(
        "USER_NOT_FOUND",
        ex.getMessage(),
        HttpStatus.NOT_FOUND.value(),
        LocalDateTime.now()
    );

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(errorResource);
  }
}