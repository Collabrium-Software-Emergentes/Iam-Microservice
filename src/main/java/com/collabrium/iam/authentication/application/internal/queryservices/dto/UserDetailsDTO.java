package com.collabrium.iam.authentication.application.internal.queryservices.dto;

import com.collabrium.iam.shared.infrastructure.clients.groups.resources.LeaderResource;
import com.collabrium.iam.shared.infrastructure.clients.tasks.resources.MemberResource;

import java.util.List;

public record UserDetailsDTO(
    Long id,
    String username,
    String name,
    String surname,
    String imgUrl,
    String email,
    List<String> roles,
    LeaderResource leaderResource,
    MemberResource memberResource
) {
}