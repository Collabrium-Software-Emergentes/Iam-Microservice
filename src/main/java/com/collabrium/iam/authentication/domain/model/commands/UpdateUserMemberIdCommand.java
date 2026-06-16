package com.collabrium.iam.authentication.domain.model.commands;

public record UpdateUserMemberIdCommand(
    Long userId,
    Long memberId
) {
}