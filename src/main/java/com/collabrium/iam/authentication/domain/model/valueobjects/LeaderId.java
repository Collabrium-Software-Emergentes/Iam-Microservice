package com.collabrium.iam.authentication.domain.model.valueobjects;

import com.collabrium.iam.authentication.domain.exceptions.InvalidLeaderIdException;
import jakarta.persistence.Embeddable;

@Embeddable
public record LeaderId(Long value) {

  public LeaderId {
    if (value == null || value <= 0) {
      throw new InvalidLeaderIdException(value);
    }
  }
}