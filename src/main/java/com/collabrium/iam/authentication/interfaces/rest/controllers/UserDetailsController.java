package com.collabrium.iam.authentication.interfaces.rest.controllers;

import com.collabrium.iam.authentication.application.internal.queryservices.UserDetailsQueryService;
import com.collabrium.iam.authentication.domain.exceptions.UserNotFoundException;
import com.collabrium.iam.authentication.domain.model.queries.GetAllUsersQuery;
import com.collabrium.iam.authentication.domain.model.queries.GetUserByIdQuery;
import com.collabrium.iam.authentication.interfaces.rest.resources.UserResource;
import com.collabrium.iam.authentication.interfaces.rest.transform.UserResourceFromDetailsDTOAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User Details", description = "User Details Management Endpoints")
public class UserDetailsController {

  private final UserDetailsQueryService userDetailsQueryService;

  public UserDetailsController(
      UserDetailsQueryService userDetailsQueryService
  ) {

    this.userDetailsQueryService = userDetailsQueryService;
  }

  @GetMapping
  public ResponseEntity<List<UserResource>> getAllUsers() {
    var getAllUsersQuery = new GetAllUsersQuery();
    var usersDetails = userDetailsQueryService.handle(getAllUsersQuery);
    var resources = usersDetails.stream()
        .map(UserResourceFromDetailsDTOAssembler::toResourceFromDTO)
        .toList();
    return ResponseEntity.ok(resources);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserResource> getUserById(
      @PathVariable Long userId
  ) {

    var query = new GetUserByIdQuery(userId);

    var dto = userDetailsQueryService.handle(query)
        .orElseThrow(() -> new UserNotFoundException(userId));

    var resource =
        UserResourceFromDetailsDTOAssembler
            .toResourceFromDTO(dto);

    return ResponseEntity.ok(resource);
  }
}