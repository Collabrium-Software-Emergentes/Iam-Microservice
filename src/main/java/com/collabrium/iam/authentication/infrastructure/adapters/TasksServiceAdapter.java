package com.collabrium.iam.authentication.infrastructure.adapters;

import com.collabrium.iam.authentication.application.internal.outboundservices.ports.TasksQueryPort;
import com.collabrium.iam.shared.infrastructure.clients.tasks.TasksFeignClient;
import com.collabrium.iam.shared.infrastructure.clients.tasks.resources.MemberResource;
import org.springframework.stereotype.Component;

@Component
public class TasksServiceAdapter implements TasksQueryPort {

  private final TasksFeignClient client;

  public TasksServiceAdapter(
      TasksFeignClient client
  ) {

    this.client = client;
  }

  @Override
  public MemberResource getMemberById(Long id) {
    return client.getMemberById(id);
  }
}