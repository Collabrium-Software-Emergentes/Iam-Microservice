package com.collabrium.iam.authentication.interfaces.rest.resources;

public record AuthenticatedUserResource(
    Long id,
    String username,
    String token
) {
}