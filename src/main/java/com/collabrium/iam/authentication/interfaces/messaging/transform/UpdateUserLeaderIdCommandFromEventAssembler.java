package com.collabrium.iam.authentication.interfaces.messaging.transform;

import com.collabrium.iam.authentication.domain.model.commands.UpdateUserLeaderIdCommand;
import com.collabrium.iam.authentication.domain.model.events.LeaderCreatedEvent;

public class UpdateUserLeaderIdCommandFromEventAssembler {

  private UpdateUserLeaderIdCommandFromEventAssembler() {
  }

  public static UpdateUserLeaderIdCommand toCommandFromEvent(
      LeaderCreatedEvent event
  ) {

    return new UpdateUserLeaderIdCommand(
        event.userId(),
        event.leaderId()
    );
  }
}
