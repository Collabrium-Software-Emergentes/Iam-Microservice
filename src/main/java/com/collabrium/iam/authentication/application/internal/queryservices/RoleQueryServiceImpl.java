package com.collabrium.iam.authentication.application.internal.queryservices;

import com.collabrium.iam.authentication.domain.model.entities.Role;
import com.collabrium.iam.authentication.domain.model.queries.GetAllRolesQuery;
import com.collabrium.iam.authentication.domain.services.RoleQueryService;
import com.collabrium.iam.authentication.infrastructure.persistence.jpa.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * RoleQueryServiceImpl class
 * This class is used to handle the role queries
 */
@Service
public class RoleQueryServiceImpl implements RoleQueryService {

  private final RoleRepository roleRepository;

  /**
   * RoleQueryServiceImpl constructor
   * @param roleRepository the role repository
   */
  public RoleQueryServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  /**
   * Handle the get all-roles query
   * @param query the get all-roles query
   * @return List<Role> the list of roles
   */
  @Override
  public List<Role> handle(GetAllRolesQuery query) {
    return roleRepository.findAll();
  }
}