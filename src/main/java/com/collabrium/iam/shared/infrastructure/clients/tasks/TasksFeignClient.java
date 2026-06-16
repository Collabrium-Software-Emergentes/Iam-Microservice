package com.collabrium.iam.shared.infrastructure.clients.tasks;

import com.collabrium.iam.shared.infrastructure.clients.tasks.resources.MemberResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "tasks-service")
public interface TasksFeignClient {

  @GetMapping("/api/v1/member/{memberId}")
  MemberResource getMemberById(
      @PathVariable Long memberId
  );
}