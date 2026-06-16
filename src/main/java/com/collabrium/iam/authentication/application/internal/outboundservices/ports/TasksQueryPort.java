package com.collabrium.iam.authentication.application.internal.outboundservices.ports;

import com.collabrium.iam.shared.infrastructure.clients.tasks.resources.MemberResource;

public interface TasksQueryPort {

  MemberResource getMemberById(Long id);
}