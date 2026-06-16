package com.collabrium.iam.shared.infrastructure.clients.groups;

import com.collabrium.iam.shared.infrastructure.clients.groups.resources.LeaderResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "groups-service")
public interface GroupsFeignClient {

  @GetMapping("/api/v1/leader/{leaderId}")
  LeaderResource getLeaderById(
    @PathVariable Long leaderId
  );
}