package com.collabrium.iam.authentication.interfaces.messaging.listeners;

import com.collabrium.iam.authentication.domain.model.events.MemberCreatedEvent;
import com.collabrium.iam.authentication.domain.services.UserCommandService;
import com.collabrium.iam.authentication.interfaces.messaging.transform.UpdateUserMemberIdCommandFromEventAssembler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.collabrium.iam.shared.infrastructure.configuration.rabbitmq.RabbitMQConfiguration.MEMBER_CREATED_QUEUE;

@Component
public class TasksEventsListeners {

  private final UserCommandService userCommandService;

  public TasksEventsListeners(
      UserCommandService userCommandService
  ) {

    this.userCommandService = userCommandService;
  }

  @RabbitListener(queues = MEMBER_CREATED_QUEUE)
  public void handle(MemberCreatedEvent event) {

    var updateUserMemberIdCommand = UpdateUserMemberIdCommandFromEventAssembler.toCommandFromEvent(event);

    userCommandService.handle(updateUserMemberIdCommand);
  }
}
