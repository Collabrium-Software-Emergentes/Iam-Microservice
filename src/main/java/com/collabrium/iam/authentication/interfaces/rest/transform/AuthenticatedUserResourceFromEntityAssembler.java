package com.collabrium.iam.authentication.interfaces.rest.transform;

import com.collabrium.iam.authentication.domain.model.aggregates.User;
import com.collabrium.iam.authentication.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {

  private AuthenticatedUserResourceFromEntityAssembler() {
  }

  public static AuthenticatedUserResource toResourceFromEntity(
      User entity,
      String token
  ) {

    return new AuthenticatedUserResource(
        entity.getId(),
        entity.getUsername(),
        token
    );
  }
}