package com.collabrium.iam.authentication.interfaces.rest.transform;

import com.collabrium.iam.authentication.application.internal.queryservices.dto.UserDetailsDTO;
import com.collabrium.iam.authentication.interfaces.rest.resources.UserLeaderResource;
import com.collabrium.iam.authentication.interfaces.rest.resources.UserMemberResource;
import com.collabrium.iam.authentication.interfaces.rest.resources.UserResource;

public class UserResourceFromDetailsDTOAssembler {

  private UserResourceFromDetailsDTOAssembler() {
  }

  public static UserResource toResourceFromDTO(
      UserDetailsDTO dto
  ) {

    UserLeaderResource leaderResource = null;
    if (dto.leaderResource() != null) {

      leaderResource = new UserLeaderResource(
          dto.leaderResource().id(),
          dto.leaderResource().averageSolutionTime(),
          dto.leaderResource().solvedRequests()
      );
    }

    UserMemberResource memberResource = null;
    if (dto.memberResource() != null) {

      memberResource = new UserMemberResource(
          dto.memberResource().id(),
          dto.memberResource().groupId()
      );
    }

    return new UserResource(
        dto.id(),
        dto.username(),
        dto.name(),
        dto.surname(),
        dto.imgUrl(),
        dto.email(),
        leaderResource,
        memberResource,
        dto.roles()
    );
  }
}