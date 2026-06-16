package com.collabrium.iam.authentication.domain.exceptions;

public class InvalidLeaderIdException extends RuntimeException {

  public InvalidLeaderIdException(Long value) {
    super(String.format("Invalid LeaderId: '%s'. Value must be a positive number greater than 0.", value));
  }
}