package com.collabrium.iam.authentication.interfaces.rest.transform;

import com.collabrium.iam.authentication.domain.model.commands.SignUpCommand;
import com.collabrium.iam.authentication.domain.model.entities.Role;
import com.collabrium.iam.authentication.interfaces.rest.resources.SignUpResource;

import java.util.ArrayList;

public class SignUpCommandFromResourceAssembler {

  private SignUpCommandFromResourceAssembler() {
  }

  public static SignUpCommand toCommandFromResource(
      SignUpResource resource
  ) {

    var roles = resource.roles() != null
        ? resource.roles().stream().map(Role::toRoleFromName).toList()
        : new ArrayList<Role>();

    return new SignUpCommand(
        resource.username(),
        resource.name(),
        resource.surname(),
        resource.imgUrl(),
        resource.email(),
        resource.password(),
        roles
    );
  }
}
