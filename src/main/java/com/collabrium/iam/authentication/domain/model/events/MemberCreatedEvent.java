package com.collabrium.iam.authentication.domain.model.events;

public record MemberCreatedEvent(
    Long userId,
    Long memberId
) {
}