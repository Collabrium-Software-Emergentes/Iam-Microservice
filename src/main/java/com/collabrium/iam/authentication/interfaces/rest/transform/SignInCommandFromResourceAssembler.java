package com.collabrium.iam.authentication.interfaces.rest.transform;

import com.collabrium.iam.authentication.domain.model.commands.SignInCommand;
import com.collabrium.iam.authentication.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {

  private SignInCommandFromResourceAssembler() {
  }

  public static SignInCommand toCommandFromResource(
      SignInResource signInResource
  ) {

    return new SignInCommand(
        signInResource.username(),
        signInResource.password()
    );
  }
}
