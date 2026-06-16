package com.collabrium.iam.authentication.interfaces.rest.resources;

import java.util.List;

public record SignUpResource(
    String username,
    String name,
    String surname,
    String imgUrl,
    String email,
    String password,
    List<String> roles
) {
}