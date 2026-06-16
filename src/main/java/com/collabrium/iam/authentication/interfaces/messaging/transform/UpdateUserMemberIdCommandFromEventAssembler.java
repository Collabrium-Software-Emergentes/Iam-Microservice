package com.collabrium.iam.authentication.interfaces.messaging.transform;

import com.collabrium.iam.authentication.domain.model.commands.UpdateUserMemberIdCommand;
import com.collabrium.iam.authentication.domain.model.events.MemberCreatedEvent;

public class UpdateUserMemberIdCommandFromEventAssembler {

  private UpdateUserMemberIdCommandFromEventAssembler() {
  }

  public static UpdateUserMemberIdCommand toCommandFromEvent(
      MemberCreatedEvent event
  ) {

    return new UpdateUserMemberIdCommand(
        event.userId(),
        event.memberId()
    );
  }
}
