package com.collabrium.iam.authentication.infrastructure.adapters;

import com.collabrium.iam.authentication.application.internal.outboundservices.ports.GroupsQueryPort;
import com.collabrium.iam.shared.infrastructure.clients.groups.GroupsFeignClient;
import com.collabrium.iam.shared.infrastructure.clients.groups.resources.LeaderResource;
import org.springframework.stereotype.Component;

@Component
public class GroupsServiceAdapter implements GroupsQueryPort {

  private final GroupsFeignClient client;

  public GroupsServiceAdapter(
      GroupsFeignClient client
  ) {
    this.client = client;
  }

  @Override
  public LeaderResource getLeaderById(Long id) {
    return client.getLeaderById(id);
  }
}