package com.collabrium.iam.authentication.interfaces.rest.transform;

import com.collabrium.iam.authentication.domain.model.aggregates.User;
import com.collabrium.iam.authentication.interfaces.rest.resources.UserOnlyResource;

public class UserOnlyResourceFromEntityAssembler {

  private UserOnlyResourceFromEntityAssembler() {
  }

  public static UserOnlyResource toResourceFromEntity(
      User entity
  ) {

    Long leaderIdValue = entity.getLeaderId() != null
        ? entity.getLeaderId().value()
        : null;

    Long memberIdValue = entity.getMemberId() != null
        ? entity.getMemberId().value()
        : null;

    return new UserOnlyResource(
        entity.getId(),
        entity.getUsername(),
        entity.getName(),
        entity.getSurname(),
        entity.getImgUrl(),
        entity.getEmail(),
        leaderIdValue,
        memberIdValue
    );
  }
}