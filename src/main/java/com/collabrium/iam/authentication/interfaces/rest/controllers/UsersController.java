package com.collabrium.iam.authentication.interfaces.rest.controllers;

import com.collabrium.iam.authentication.domain.model.queries.*;
import com.collabrium.iam.authentication.domain.services.UserQueryService;
import com.collabrium.iam.authentication.interfaces.rest.resources.UserOnlyResource;
import com.collabrium.iam.authentication.interfaces.rest.transform.UserOnlyResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class is a REST controller that exposes the user's resource.
 * It includes the following operations:
 * - GET /api/v1/users: returns all the users
 * - GET /api/v1/users/{userId}: returns the user with the given id
 **/
@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "User Management Endpoints")
public class UsersController {

  private final UserQueryService userQueryService;

  public UsersController(UserQueryService userQueryService) {
    this.userQueryService = userQueryService;
  }

  @GetMapping("/{userId}/domain-profile")
  public ResponseEntity<UserOnlyResource> getUserOnlyById(
      @PathVariable Long userId
  ) {

    var getUserOnlyByIdQuery = new GetUserOnlyByIdQuery(userId);

    var userOptional = userQueryService.handle(getUserOnlyByIdQuery);

    if (userOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    var userOnlyResource = UserOnlyResourceFromEntityAssembler.toResourceFromEntity(userOptional.get());

    return ResponseEntity.ok(userOnlyResource);
  }

  @GetMapping(params = "memberId")
  @Operation(
      summary = "Get user information by member ID",
      description = "Returns basic user information associated with the specified member ID"
  )
  public ResponseEntity<UserOnlyResource> getUserOnlyByMemberId(
      @RequestParam Long memberId
  ) {

    var getUserByMemberIdQuery = new GetUserByMemberIdQuery(memberId);

    var userOptional = userQueryService.handle(getUserByMemberIdQuery);

    if (userOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    var userOnlyResource = UserOnlyResourceFromEntityAssembler.toResourceFromEntity(userOptional.get());

    return ResponseEntity.ok(userOnlyResource);
  }
}
