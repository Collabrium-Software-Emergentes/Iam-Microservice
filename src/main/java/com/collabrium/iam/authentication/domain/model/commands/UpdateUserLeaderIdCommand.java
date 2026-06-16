package com.collabrium.iam.authentication.domain.model.commands;

public record UpdateUserLeaderIdCommand(
    Long userId,
    Long leaderId
) {
}