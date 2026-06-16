package com.collabrium.iam.authentication.domain.services;

import com.collabrium.iam.authentication.domain.model.entities.Role;
import com.collabrium.iam.authentication.domain.model.queries.GetAllRolesQuery;

import java.util.List;

public interface RoleQueryService {

  List<Role> handle(GetAllRolesQuery query);
}