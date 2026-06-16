package com.collabrium.iam.authentication.domain.exceptions;

public class InvalidMemberIdException extends RuntimeException {

  public InvalidMemberIdException(Long value) {
    super(String.format("Invalid MemberId: '%s'. Value must be a positive number greater than 0.", value));
  }
}
