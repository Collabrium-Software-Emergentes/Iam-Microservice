package com.collabrium.iam.authentication.domain.exceptions;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(Long userId) {
    super("User with id %d was not found".formatted(userId));
  }
}
