package com.collabrium.iam.authentication.interfaces.rest.controllers;

import com.collabrium.iam.authentication.domain.model.queries.GetAllRolesQuery;
import com.collabrium.iam.authentication.domain.services.RoleQueryService;
import com.collabrium.iam.authentication.interfaces.rest.resources.RoleResource;
import com.collabrium.iam.authentication.interfaces.rest.transform.RoleResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  Role Controller
 *  This controller is responsible for handling all the requests related to roles
 */
@RestController
@RequestMapping(value = "/api/v1/roles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Roles", description = "Role Management Endpoints")
public class RolesController {

  private final RoleQueryService roleQueryService;

  public RolesController(RoleQueryService roleQueryService) {
    this.roleQueryService = roleQueryService;
  }

  /**
   * Get all roles
   * @return List of role resources
   * @see RoleResource
   */
  @GetMapping
  public ResponseEntity<List<RoleResource>> getAllRoles() {

    var getAllRolesQuery = new GetAllRolesQuery();

    var roles = roleQueryService.handle(getAllRolesQuery);

    var roleResources = roles.stream()
        .map(RoleResourceFromEntityAssembler::toResourceFromEntity)
        .toList();

    return ResponseEntity.ok(roleResources);
  }
}
