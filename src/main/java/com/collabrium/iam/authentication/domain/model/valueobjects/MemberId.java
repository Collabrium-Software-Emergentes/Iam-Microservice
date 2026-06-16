package com.collabrium.iam.authentication.domain.model.valueobjects;

import com.collabrium.iam.authentication.domain.exceptions.InvalidMemberIdException;
import jakarta.persistence.Embeddable;

@Embeddable
public record MemberId(Long value) {

  public MemberId {
    if (value == null || value <= 0) {
      throw new InvalidMemberIdException(value);
    }
  }
}