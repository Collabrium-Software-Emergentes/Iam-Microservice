package com.collabrium.iam.authentication.domain.model.events;

public record LeaderCreatedEvent(
    Long userId,
    Long leaderId
) {
}