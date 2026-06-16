package com.collabrium.iam.authentication.domain.services;

import com.collabrium.iam.authentication.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {

  void handle(SeedRolesCommand command);
}