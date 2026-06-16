package com.collabrium.iam.authentication.domain.model.events;

public record UserMemberCreatedEvent(
    Long userId
) {
}