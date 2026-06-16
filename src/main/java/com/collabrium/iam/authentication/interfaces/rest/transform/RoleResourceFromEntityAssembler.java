package com.collabrium.iam.authentication.interfaces.rest.transform;

import com.collabrium.iam.authentication.domain.model.entities.Role;
import com.collabrium.iam.authentication.interfaces.rest.resources.RoleResource;

public class RoleResourceFromEntityAssembler {

  private RoleResourceFromEntityAssembler() {
  }

  public static RoleResource toResourceFromEntity(
      Role role
  ) {

    return new RoleResource(
        role.getId(),
        role.getStringName()
    );
  }
}
