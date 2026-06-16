package com.collabrium.iam.authentication.domain.model.commands;

import com.collabrium.iam.authentication.domain.model.entities.Role;

import java.util.List;

public record SignUpCommand(
    String username,
    String name,
    String surname,
    String imgUrl,
    String email,
    String password,
    List<Role> roles
) {
}