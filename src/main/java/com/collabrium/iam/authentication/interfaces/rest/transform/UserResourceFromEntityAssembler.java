package com.collabrium.iam.authentication.interfaces.rest.transform;

import com.collabrium.iam.authentication.domain.model.aggregates.User;
import com.collabrium.iam.authentication.domain.model.entities.Role;
import com.collabrium.iam.authentication.interfaces.rest.resources.UserLeaderResource;
import com.collabrium.iam.authentication.interfaces.rest.resources.UserMemberResource;
import com.collabrium.iam.authentication.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {

  private UserResourceFromEntityAssembler() {
  }

  public static UserResource toResourceFromEntity(
      User user
  ) {

    var roles = user.getRoles().stream()
        .map(Role::getStringName)
        .toList();

    final String LEADER_ROLE = "ROLE_LEADER";
    final String MEMBER_ROLE = "ROLE_MEMBER";

    UserLeaderResource leaderResource = roles.contains(LEADER_ROLE) ?
        new UserLeaderResource(
            10L,
            "19:00:00",
            0) : null;

    UserMemberResource memberResource = roles.contains(MEMBER_ROLE) ?
        new UserMemberResource(11L,
            0L) : null;

    return new UserResource(
        user.getId(),
        user.getUsername(),
        user.getName(),
        user.getSurname(),
        user.getImgUrl(),
        user.getEmail(),
        leaderResource,
        memberResource,
        roles
    );
  }
}
