package com.collabrium.iam.authentication.application.internal.outboundservices.ports;

import com.collabrium.iam.shared.infrastructure.clients.groups.resources.LeaderResource;

public interface GroupsQueryPort {

  LeaderResource getLeaderById(Long id);
}