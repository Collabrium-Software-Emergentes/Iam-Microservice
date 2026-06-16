package com.collabrium.iam.authentication.domain.model.commands;

public record SignInCommand(
    String username,
    String password
) {
}