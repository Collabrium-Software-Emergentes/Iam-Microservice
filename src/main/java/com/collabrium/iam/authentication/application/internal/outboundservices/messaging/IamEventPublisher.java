package com.collabrium.iam.authentication.application.internal.outboundservices.messaging;

import com.collabrium.iam.authentication.domain.model.events.UserLeaderCreatedEvent;
import com.collabrium.iam.authentication.domain.model.events.UserMemberCreatedEvent;

public interface IamEventPublisher {

  void publishUserLeaderCreated(UserLeaderCreatedEvent event);

  void publishUserMemberCreated(UserMemberCreatedEvent event);
}
