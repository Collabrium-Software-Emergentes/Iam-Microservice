package com.collabrium.iam.authentication.interfaces.rest.resources;

public record SignInResource(
    String username,
    String password
) {
}