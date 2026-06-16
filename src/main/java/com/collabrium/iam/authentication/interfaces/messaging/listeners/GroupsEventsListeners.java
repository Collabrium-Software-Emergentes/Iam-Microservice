package com.collabrium.iam.authentication.interfaces.messaging.listeners;

import com.collabrium.iam.authentication.domain.model.events.LeaderCreatedEvent;
import com.collabrium.iam.authentication.domain.services.UserCommandService;
import com.collabrium.iam.authentication.interfaces.messaging.transform.UpdateUserLeaderIdCommandFromEventAssembler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.collabrium.iam.shared.infrastructure.configuration.rabbitmq.RabbitMQConfiguration.LEADER_CREATED_QUEUE;

@Component
public class GroupsEventsListeners {

  private final UserCommandService userCommandService;

  public GroupsEventsListeners(
      UserCommandService userCommandService
  ) {

    this.userCommandService = userCommandService;
  }

  @RabbitListener(queues = LEADER_CREATED_QUEUE)
  public void handle(LeaderCreatedEvent event) {

    var updateUserLeaderIdCommand = UpdateUserLeaderIdCommandFromEventAssembler.toCommandFromEvent(event);

    userCommandService.handle(updateUserLeaderIdCommand);
  }
}