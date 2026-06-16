package com.collabrium.iam.shared.infrastructure.clients.groups.resources;

public record LeaderResource(
  Long id,
  String averageSolutionTime,
  Integer solvedRequests
) {
}